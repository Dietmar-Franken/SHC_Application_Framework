package de.rpi_controlcenter.AppServer.Controller.REST.Application;

import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Editor.SettingsEditor;
import de.rpi_controlcenter.Util.Logger.LoggerUtil;
import de.rpi_controlcenter.Util.Settings.IntegerSetting;
import de.rpi_controlcenter.Util.Settings.StringSetting;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

/**
 * Anwendungs REST Server
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ApplicationRestServer {

    private static Logger logger = LoggerUtil.getLogger(ApplicationRestServer.class);

    /**
     * HTTP Server
     */
    private HttpServer server;

    /**
     * startet den REST Server
     *
     * @throws IOException
     */
    public void startServer() throws IOException {

        if(server == null) {

            ResourceConfig rc = new ResourceConfig();
            rc.packages("de.rpi_controlcenter.AppServer.Controller.REST.Application.Handler");

            //Einstellungen lesen
            Lock lock = AppServer.getInstance().getSettings().getReadWriteLock().readLock();
            lock.lock();
            StringSetting applicationServerAddress = AppServer.getInstance().getSettings().getStringSetting(SettingsEditor.APPLICATION_SERVER_ADDRESS);
            IntegerSetting applicationServerPort = AppServer.getInstance().getSettings().getIntegerSetting(SettingsEditor.APPLICATION_SERVER_PORT);
            StringSetting applicationServerCertificatePassword = AppServer.getInstance().getSettings().getStringSetting(SettingsEditor.APPLICATION_SERVER_CERTIFICATE_PASSWORD);

            String serverAddressString = "http://" + applicationServerAddress.getValue() + ":" + applicationServerPort.getValue();
            lock.unlock();

            server = GrizzlyHttpServerFactory.createHttpServer(
                    URI.create(serverAddressString),      //Server Adresse
                    rc,                                   //ResourceConfig
                    false,                                //Verschlüsseln
                    null                                  //SSL Engine Konfiguration
            );

            server.start();
            if(server.isStarted()) {

                logger.info("Application REST Server unter der Adresse " + serverAddressString + " gestartet");
            }
        }
    }

    /**
     * gibt an ob der Server läuft
     *
     * @return Server läuft
     */
    public boolean isRunning() {

        if(server != null) {

            return server.isStarted();
        }
        return false;
    }

    /**
     * beendet den REST Server
     */
    public void stopServer() {

        if(server != null) {

            server.shutdownNow();
            server = null;
        }
    }
}
