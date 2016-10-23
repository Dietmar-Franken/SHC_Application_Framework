package de.rpi_controlcenter.AppServer.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.rpi_controlcenter.AppServer.Controller.Configurator.CliConfigurator;
import de.rpi_controlcenter.AppServer.Controller.Install.Installer;
import de.rpi_controlcenter.AppServer.Controller.Install.Updater;
import de.rpi_controlcenter.AppServer.Controller.REST.Application.ApplicationRestServer;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Database.DatabaseManager;
import de.rpi_controlcenter.AppServer.Model.Editor.*;
import de.rpi_controlcenter.AppServer.Model.Serializer.UserSerializer;
import de.rpi_controlcenter.Util.Json.Serializer.LocalDateSerializer;
import de.rpi_controlcenter.Util.Json.Serializer.LocalDateTimeSerializer;
import de.rpi_controlcenter.Util.Json.Serializer.LocalTimeSerializer;
import de.rpi_controlcenter.Util.Logger.LoggerUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * startet die Anwendung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class AppServer {

    /**
     * Server Version
     */
    public static final String VERSION = "0.1.0";
    /**
     * Server API LEVEL
     */
    public static final int API_LEVEL = 1;

    /**
     * Singleton Instanz
     */
    private static AppServer instance;

    /**
     * Logger
     */
    private static Logger logger;

    /**
     * Liste der Kommandozeilenparameter
     */
    private static Set<String> arguments = new HashSet<>();

    /**
     * Datenbank Manager
     */
    private static DatabaseManager databaseManager;

    /**
     * Gson Builder
     */
    private GsonBuilder builder;

    /**
     * Einstellungs Verwaltung
     */
    private volatile SettingsEditor settings;

    /**
     * Benutzergruppen Verwaltung
     */
    private volatile UserGroupsEditor userGroups;

    /**
     * Benutzer Verwaltung
     */
    private volatile UsersEditor users;

    /**
     * Session Verwaltung
     */
    private volatile SessionEditor sessions;

    /**
     * Client Geräte verwaltung
     */
    private volatile DeviceEditor devices;

    /**
     * Schaltserver verwaltung
     */
    private volatile SwitchServerEditor switchServers;

    /**
     * Sensorwerte Verwaltung
     */
    private volatile SensorValueEditor sensorValues;

    /**
     * schaltbare Elemente verwaltung
     */
    private volatile SwitchableEditor switchables;

    /**
     * Bedingungen verwaltung
     */
    private volatile ConditionEditor conditions;

    /**
     * Automatisierungsoperationen verwaltung
     */
    private volatile OperationEditor operations;

    /**
     * Icon verwaltung
     */
    private volatile IconEditor icons;

    /**
     * Raum verwaltung
     */
    private volatile RoomEditor rooms;

    /**
     * Eintrittspunkt in die Anwendung
     *
     * @param args CLI Argumente
     */
    public static void main(String[] args) {

        //Kommandozeilen Parameter in ein SET übernehmen
        Collections.addAll(arguments, args);

        //Version anzeigen
        if(arguments.contains("-v") || arguments.contains("--version")) {

            System.out.println("Version: " + VERSION);
            return;
        }

        //Logger Konfigurieren
        if(arguments.contains("-d") || arguments.contains("--debug")) {

            //Standard Log Level
            LoggerUtil.setLogLevel(Level.CONFIG);
            LoggerUtil.setLogFileLevel(Level.CONFIG);
        } else if(arguments.contains("-df") || arguments.contains("--debug-fine")) {

            //Alle Log Daten ausgeben
            LoggerUtil.setLogLevel(Level.FINEST);
            LoggerUtil.setLogFileLevel(Level.FINEST);
        } else {

            //Fehler in Log Datei Schreiben
            LoggerUtil.setLogLevel(Level.OFF);
            LoggerUtil.setLogFileLevel(Level.WARNING);
            LoggerUtil.setLogDir(Paths.get("log"));
        }
        logger = LoggerUtil.getLogger(AppServer.class);

        try {

            //Datenbank Konfiguration
            databaseManager = new DatabaseManager();
            if(arguments.contains("-db") || arguments.contains("--Database") || !Files.exists(databaseManager.getDbConfigFile())) {

                CliConfigurator.startDatabaseConfig();
                return;
            }

            //Anwendung initalisieren
            instance = new AppServer();
            instance.initAppliaction();

            //Einstellungen
            if(arguments.contains("-c") || arguments.contains("--config")) {

                CliConfigurator.startApplicationConfig();
                return;
            }

            //Shutdown Funktion
            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {

                    try {

                        AppServer.getInstance().finalize();
                    } catch (Throwable throwable) {

                        LoggerUtil.serveException(logger, throwable);
                    }
                }
            });

            //Automatisches Speichern der Daten in die Datenbank
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(
                    () -> {

                        //Datenbank speichern
                        AppServer.getInstance().dump();
                    },
                    30, //Startverzögerung
                    30, //Intervall
                    TimeUnit.SECONDS);

            //Anwendug starten
            instance.startAppliaction();
        } catch (Throwable throwable) {

            LoggerUtil.serveException(logger, "Allgemeiner Anwendungsfehler", throwable);
            System.exit(1);
        }
    }

    /**
     * initalisiert die Anwendung
     */
    private void initAppliaction() {

        initGson();
        initDatabase();
        checkInstallOrUpdate();
        initData();
    }

    /**
     * initalisiert die Anwendung
     */
    private void startAppliaction() {

        logger.info("application is rady to start");

        ApplicationRestServer applicationRestServer = new ApplicationRestServer();
        try {

            applicationRestServer.startServer();
        } catch (IOException e) {

            logger.log(Level.SEVERE, "Der Application REST Server konnte nicht gestartet werden", e);
        }

    }

    /**
     * initialisiert die Gson Json API
     */
    private void initGson() {

        builder = new GsonBuilder();

        //Serialisierer und Deserialisierer anmelden
        builder.registerTypeAdapter(User.class, new UserSerializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        builder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
    }

    /**
     * erzeugt ein Gson Objekt aus dem Builder heraus
     *
     * @return Gson Objekt
     */
    public Gson getGson() {

        return builder.create();
    }

    /**
     * baut die Datenbankverbindung auf
     */
    private void initDatabase() {

        if(databaseManager == null) {

            databaseManager = new DatabaseManager();
        }

        try {

            databaseManager.readDatabaseConfigFile();
            databaseManager.connectDatabase();
        } catch (IllegalStateException se) {

            logger.severe("Die Datenbankkonfiguration konnte nicht gelesen werden");
            System.exit(1);
        } catch(JedisConnectionException e) {

            LoggerUtil.serveException(logger, "Verbindung zur Datenbank ist Fehlgeschlagen", e);
            System.exit(1);
        }
    }

    /**
     * gibt die Datenbankverbindung zurück
     *
     * @return Datenbankverbindung
     */
    public Jedis getDatabaseConnection() {
        return databaseManager.getConnection();
    }

    /**
     * Initalisiert die Daten
     */
    private void initData() {

        settings = new SettingsEditor();
        settings.load();

        userGroups = new UserGroupsEditor();
        userGroups.load();

        users = new UsersEditor();
        users.load();

        sessions = new SessionEditor();

        devices = new DeviceEditor();
        devices.load();

        switchServers = new SwitchServerEditor();
        switchServers.load();

        sensorValues = new SensorValueEditor();
        sensorValues.load();

        switchables = new SwitchableEditor();
        switchables.load();

        conditions = new ConditionEditor();
        conditions.load();

        operations = new OperationEditor();
        operations.load();

        icons = new IconEditor();
        icons.load();

        rooms = new RoomEditor();
        rooms.load();
    }

    /**
     * gibt den Datenbankmanager zurück
     *
     * @return Datenbankmanager
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * gibt die Einstellungs Verwaltung zurück
     *
     * @return Einstellungs Editor
     */
    public SettingsEditor getSettings() {
        return settings;
    }

    /**
     * gibt die Benutzergruppen Verwaltung zurück
     *
     * @return Benutzergruppen Editor
     */
    public UserGroupsEditor getUserGroups() {
        return userGroups;
    }

    /**
     * gibt die Benutzer Verwaltung zurück
     *
     * @return Benutzer Editor
     */
    public UsersEditor getUsers() {
        return users;
    }

    /**
     * gibt die Session Verwaltung zurück
     *
     * @return Session Verwaltung
     */
    public SessionEditor getSessions() {
        return sessions;
    }

    /**
     * gibt die Geräte Verwaltung zurück
     *
     * @return Geräte Verwaltung
     */
    public DeviceEditor getDevices() {
        return devices;
    }

    /**
     * gibt die Schaltserver Verwaltung zurück
     *
     * @return Schaltserver Verwaltung
     */
    public SwitchServerEditor getSwitchServers() {
        return switchServers;
    }

    /**
     * gint die Sensorwerte Verwaltung zurück
     *
     * @return Sensorwerte Verwaltung
     */
    public SensorValueEditor getSensorValues() {
        return sensorValues;
    }

    /**
     * gibt die schaltbare Elemente Verwaltung zurück
     *
     * @return schaltbare Elemente
     */
    public SwitchableEditor getSwitchables() {
        return switchables;
    }

    /**
     * gibt die Bedingungs Verwaltung zurück
     *
     * @return Bedingungen
     */
    public ConditionEditor getConditions() {
        return conditions;
    }

    /**
     * gibt die Automatisierungsoperationen Verwaltung zurück
     *
     * @return Automatisierungsoperationen
     */
    public OperationEditor getOperations() {
        return operations;
    }

    /**
     * gibt die Icon Verwaltung rurück
     *
     * @return Icons
     */
    public IconEditor getIcons() {
        return icons;
    }

    /**
     * gibt die Raum Verwaltung rurück
     *
     * @return Räume
     */
    public RoomEditor getRooms() {
        return rooms;
    }

    /**
     * Prüft ob die Anwendung Installiert werden muss oder ein Update ansteht
     */
    private void checkInstallOrUpdate() {

        final String KEY_INSTALLED = "shc:internal:installed";
        final String KEY_API_LEVEL = "shc:internal:apiLevel";

        Jedis db = getDatabaseConnection();
        boolean installed = false;
        if(db.exists(KEY_INSTALLED)) {

            installed = db.get(KEY_INSTALLED).equals("1");
        }
        if(!installed) {

            //Installieren
            if(Installer.install()) {

                db.set(KEY_INSTALLED, "1");
                db.set(KEY_API_LEVEL, Integer.valueOf(API_LEVEL).toString());
            }
            return;
        }
        Integer installedApiLevel = Integer.parseInt(db.get(KEY_API_LEVEL));
        if(API_LEVEL > installedApiLevel) {

            //Updaten
            Integer newApiLevel = Updater.update(installedApiLevel);
            db.set(KEY_API_LEVEL, newApiLevel.toString());
        }
    }

    /**
     * speichert die Editoren
     */
    public void dump() {

        Lock settingsLock = settings.readLock();
        settingsLock.lock();
        try {

            settings.dump();
        } finally {

            settingsLock.unlock();
        }

        Lock userGroupsLock = userGroups.readLock();
        userGroupsLock.lock();
        try {

            userGroups.dump();
        } finally {

            userGroupsLock.unlock();
        }

        Lock userLock = users.readLock();
        userLock.lock();
        try {

            users.dump();
        } finally {

            userLock.unlock();
        }

        Lock devicesLock = devices.readLock();
        devicesLock.lock();
        try {

            devices.dump();
        } finally {

            devicesLock.unlock();
        }

        Lock switchServerLock = switchServers.readLock();
        switchServerLock.lock();
        try {

            switchServers.dump();
        } finally {

            switchServerLock.unlock();
        }

        Lock sensorValuesLock = sensorValues.readLock();
        sensorValuesLock.lock();
        try {

            sensorValues.dump();
        } finally {

            sensorValuesLock.unlock();
        }

        Lock switchablesLock = switchables.readLock();
        switchablesLock.lock();
        try {

            switchables.dump();
        } finally {

            switchablesLock.unlock();
        }

        Lock conditionLock = conditions.readLock();
        conditionLock.lock();
        try {

            conditions.dump();
        } finally {

            conditionLock.unlock();
        }

        Lock operationLock = operations.readLock();
        operationLock.lock();
        try {

            operations.dump();
        } finally {

            operationLock.unlock();
        }

        Lock iconLock = icons.readLock();
        iconLock.lock();
        try {

            icons.dump();
        } finally {

            iconLock.unlock();
        }

        Lock roomLock = rooms.readLock();
        roomLock.lock();
        try {

            rooms.dump();
        } finally {

            roomLock.unlock();
        }
    }

    /**
     * Beendet alle Threads und Verbindungen und danach die Anwendung
     */
    @Override
    public void finalize() throws Throwable {

        super.finalize();

        //Editoren speichern
        dump();

        //Datenbankverbindung aufheben
        if(databaseManager != null && databaseManager.isConnected()) {

            databaseManager.disconnectDatabase();
        }
    }

    /**
     * gibt die Instanz der Haupklasse zurück
     *
     * @return AppServer
     */
    public static AppServer getInstance() {
        return instance;
    }

    /**
     * gibt ein Set mit den übergebenen Kommandozeileparametern zurück
     *
     * @return Kommandozeilen Parameter
     */
    public static Set<String> getArguments() {
        return arguments;
    }
}
