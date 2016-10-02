package de.rpi_controlcenter.AppServer.Controller.REST.Application;

import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Editor.SettingsEditor;
import de.rpi_controlcenter.Util.Settings.BooleanSetting;
import de.rpi_controlcenter.Util.Settings.IntegerSetting;
import de.rpi_controlcenter.Util.Settings.StringSetting;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Anwendungs REST Server
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ApplicationRestServer {

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

            StringSetting applicationServerAddress = AppServer.getInstance().getSettings().getStringSetting(SettingsEditor.APPLICATION_SERVER_ADDRESS);
            IntegerSetting applicationServerPort = AppServer.getInstance().getSettings().getIntegerSetting(SettingsEditor.APPLICATION_SERVER_PORT);
            StringSetting applicationServerCertificatePassword = AppServer.getInstance().getSettings().getStringSetting(SettingsEditor.APPLICATION_SERVER_CERTIFICATE_PASSWORD);
            String serverAddressString = "http://" + applicationServerAddress.getValue() + ":" + applicationServerPort.getValue();

            server = GrizzlyHttpServerFactory.createHttpServer(
                    URI.create(serverAddressString),      //Server Adresse
                    rc,                                   //ResourceConfig
                    false,                                //Verschlüsseln
                    null                                  //SSL Engine Konfiguration
            );

            server.start();
        }
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
