package de.rpi_controlcenter.AppServer.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.rpi_controlcenter.AppServer.Controller.Configurator.CliConfigurator;
import de.rpi_controlcenter.AppServer.Controller.Install.Installer;
import de.rpi_controlcenter.AppServer.Controller.Install.Updater;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
    private SettingsEditor settings;

    /**
     * Benutzergruppen Verwaltung
     */
    private UserGroupsEditor userGroups;

    /**
     * Benutzer Verwaltung
     */
    private UsersEditor users;

    /**
     * Session Verwaltung
     */
    private SessionEditor sessions;

    /**
     * Client Geräte verwaltung
     */
    private DeviceEditor devices;

    /**
     * Schaltserver verwaltung
     */
    private SwitchServerEditor switchServers;

    /**
     * Sensorwerte Verwaltung
     */
    private SensorValueEditor sensorValues;

    /**
     * schaltbare Elemente verwaltung
     */
    private SwitchableEditor switchables;

    /**
     * Bedingungen verwaltung
     */
    private ConditionEditor conditions;

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

        settings.dump();
        userGroups.dump();
        users.dump();
        devices.dump();
        switchServers.dump();
        sensorValues.dump();
        switchables.dump();
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
