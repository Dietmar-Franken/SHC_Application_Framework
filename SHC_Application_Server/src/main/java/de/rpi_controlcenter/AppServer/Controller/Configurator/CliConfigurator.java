package de.rpi_controlcenter.AppServer.Controller.Configurator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Editor.SettingsEditor;
import de.rpi_controlcenter.Util.Cli.CliUtil;
import de.rpi_controlcenter.Util.Logger.LoggerUtil;
import de.rpi_controlcenter.Util.Settings.IntegerSetting;
import de.rpi_controlcenter.Util.Settings.StringSetting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Kommandozeilen Einstellungen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class CliConfigurator {

    private static Logger logger = LoggerUtil.getLogger(CliConfigurator.class);

    /**
     * bearbeitet die Datenbank Konfiguration
     */
    public static void startDatabaseConfig() {

        System.out.println("Datenbank Einstellungen");

        Path dbConfigFile = Paths.get("db.config.json");

        //Einstellungen laden
        JsonElement json = null;
        try {

            json = new JsonParser().parse(Files.newBufferedReader(dbConfigFile));
        } catch (IOException e) {

            json = new JsonParser().parse("{\"host\":\"127.0.0.1\",\"port\":6379,\"timeout\":1000,\"pass\":\"\",\"db\":0}");
        }

        JsonObject config = json.getAsJsonObject();

        try {

            //IP Adresse
            Optional<String> ip = CliUtil.inputIpAddressOption("Redis Server IP Adresse", config.get("host").getAsString(), 5);
            if(ip.isPresent()) {

                config.addProperty("host", ip.get());
            }

            //Port
            Optional<Integer> port = CliUtil.inputIntegerOption("Redis Server Port", config.get("port").getAsInt(), 0, 65535, 5);
            if(port.isPresent()) {

                config.addProperty("port", port.get());
            }

            //Timeout
            Optional<Integer> timeout = CliUtil.inputIntegerOption("Redis Server Timeout [in ms]", config.get("timeout").getAsInt(), 500, 10000, 5);
            if(timeout.isPresent()) {

                config.addProperty("timeout", timeout.get());
            }

            //Passwort
            Optional<String> pass = CliUtil.inputIpAddressOption("Redis Server Passwort", config.get("pass").getAsString(), 5);
            if(pass.isPresent()) {

                config.addProperty("pass", pass.get());
            }

            //Datenbank Index
            Optional<Integer> db = CliUtil.inputIntegerOption("Redis Server Datenbankindex", config.get("db").getAsInt(), 0, 100, 5);
            if(db.isPresent()) {

                config.addProperty("db", db.get());
            }
        } catch (IOException e) {

            //Debug Ausgabe
            LoggerUtil.serveException(logger, e);
        }

        //Einstellungen speichern
        BufferedWriter out;
        try {

            out = Files.newBufferedWriter(dbConfigFile, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            out.write(config.toString());
            out.close();
            logger.info("Einstellungen erfolgreich gespeichert");
        } catch (IOException e) {

            System.err.println("Die EInstellungen konnten nicht gespeichert werden");

            //Debug Ausgabe
            LoggerUtil.serveException(logger, e);
        }
    }

    /**
     * bearbeitet die Anwendungseinstellungen
     */
    public static void startApplicationConfig() {

        SettingsEditor settings = AppServer.getInstance().getSettings();

        try {

            StringSetting appServerAddress = settings.getStringSetting(SettingsEditor.APPLICATION_SERVER_ADDRESS);
            Optional<String> address = CliUtil.inputIpAddressOption("Applikationsserver Adresse", appServerAddress.getValue(), 5);
            if(address.isPresent()) {

                appServerAddress.setValue(address.get());
            }

            IntegerSetting appServerPort = settings.getIntegerSetting(SettingsEditor.APPLICATION_SERVER_PORT);
            Optional<Integer> port = CliUtil.inputIntegerOption("Applikationsserver Port", appServerPort.getValue(), 0, 65535, 5);
            if(port.isPresent()) {

                appServerPort.setValue(port.get());
            }

            StringSetting certificatePassword = settings.getStringSetting(SettingsEditor.APPLICATION_SERVER_CERTIFICATE_PASSWORD);
            Optional<String> password = CliUtil.inputStringOption("Passwort des SSL Zertifikates: ", certificatePassword.getValue());
            if(password.isPresent()) {

                certificatePassword.setValue(password.get());
            }

            StringSetting sensorServerAddress = settings.getStringSetting(SettingsEditor.SENSOR_SERVER_ADDRESS);
            Optional<String> address1 = CliUtil.inputIpAddressOption("Sensorserver Adresse", sensorServerAddress.getValue(), 5);
            if(address1.isPresent()) {

                sensorServerAddress.setValue(address1.get());
            }

            IntegerSetting sensorServerPort = settings.getIntegerSetting(SettingsEditor.SENSOR_SERVER_PORT);
            Optional<Integer> port1 = CliUtil.inputIntegerOption("Sensorserver Port", sensorServerPort.getValue(), 0, 65535, 5);
            if(port1.isPresent()) {

                sensorServerPort.setValue(port1.get());
            }

            IntegerSetting serverStateLed = settings.getIntegerSetting(SettingsEditor.STATE_LED_PIN);
            Optional<Integer> pin = CliUtil.inputIntegerOption("Pin Nummer [wiringpi] für die Status LED", serverStateLed.getValue(), 0, 35, 5);
            if(pin.isPresent()) {

                serverStateLed.setValue(pin.get());
            }

            //Einstellungen speichern
            settings.dump();

            logger.info("Einstellungen erfolgreich gespeichert");
        } catch (IOException e) {

            System.err.println("Die EInstellungen konnten nicht gespeichert werden");

            //Debug Ausgabe
            LoggerUtil.serveException(logger, e);
        }
    }
}
