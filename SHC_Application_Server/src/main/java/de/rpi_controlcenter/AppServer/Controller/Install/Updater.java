package de.rpi_controlcenter.AppServer.Controller.Install;

import de.rpi_controlcenter.Util.Logger.LoggerUtil;

import java.util.logging.Logger;

/**
 * Aktualisiert die Anwendung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class Updater {

    private static Logger logger = LoggerUtil.getLogger(Updater.class);

    /**
     * bekommt das Installierte API Level übergeben und führt dann für jedes Level zwischen dem installierten und dem neuen die Update Funktion aus
     *
     * @param installedApiLevel Instaliertes API Level
     * @return neues API Level
     */
    public static int update(int installedApiLevel) {

        //Update Routiene mit dem nächsten API Level starten
        installedApiLevel++;
        switch(installedApiLevel) {

            case 2:
                updateToApiLevel_2();
            case 3:
                //updateToApiLevel_3();
        }

        return 2; //neues API Level nach dem Update
    }

    private static void updateToApiLevel_2() {

        logger.info("Update auf API Level 2");
        //Update Funktion für API Level 2
    }

    private static void updateToApiLevel_3() {

        logger.info("Update auf API Level 3");
        //Update Funktion für API Level 3
    }
}
