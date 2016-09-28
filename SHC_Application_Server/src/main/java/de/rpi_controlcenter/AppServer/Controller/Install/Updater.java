package de.rpi_controlcenter.AppServer.Controller.Install;

import de.rpi_controlcenter.Util.Logger.LoggerUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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
        int newApiLevel = 1;
        switch(installedApiLevel) {

            case 2:
                newApiLevel = updateToApiLevel_2();
            case 3:
                //newApiLevel = updateToApiLevel_3();
        }

        return newApiLevel; //neues API Level nach dem Update
    }

    private static int updateToApiLevel_2() {

        logger.info("Update auf API Level 2");
        //Update Funktion für API Level 2

        return 2;
    }

    private static int updateToApiLevel_3() {

        logger.info("Update auf API Level 3");
        //Update Funktion für API Level 3

        return 3;
    }

    /**
     * kopiert die Icon Dateien aus der Jar Datei in das Dateisystem
     *
     * @param jarFilePath Jar Datei
     */
    private static void copyIconsToFileSystem(Path jarFilePath) {

        try {

            //Zielordner erzeugen falls nicht vorhanden
            Path destinationPath = Paths.get("Icons");
            if(!Files.exists(destinationPath)) {

                Files.createDirectories(destinationPath);
            }

            //Icon Dateien suchen
            JarFile jarFile = new JarFile(jarFilePath.toFile());
            Enumeration<JarEntry> entries = jarFile.entries();
            List<JarEntry> iconEntries = new ArrayList<>();

            while (entries.hasMoreElements()) {

                JarEntry entry = entries.nextElement();
                if(entry.getName().toLowerCase().startsWith("icons/")) {
                    iconEntries.add(entry);
                }
            }

            //Dateien kopieren
            for(JarEntry entry : iconEntries) {

                String fileName = entry.getName().toLowerCase();
                if(!fileName.equals("icons/") && fileName.endsWith("/")) {

                    String iconName = fileName.replace("icons/", "").replace("/", "");

                    //Icon Ordner erzeugen
                    Path iconDirectory = destinationPath.resolve(iconName);
                    if(!Files.exists(iconDirectory)) {

                        Files.createDirectories(iconDirectory);
                    }

                    //Icon Dateien in den Ordner kopieren
                    for(JarEntry iconFile : iconEntries) {

                        String iconFileName = iconFile.getName().toLowerCase();
                        if(iconFileName.startsWith(fileName) && iconFileName.endsWith(".png")) {

                            Path out = destinationPath.resolve(iconFileName.replace("icons/", "")).toAbsolutePath();
                            if(!Files.exists(out)) {

                                InputStream in = jarFile.getInputStream(iconFile);
                                Files.copy(in, out, StandardCopyOption.REPLACE_EXISTING);
                            }
                        }
                    }
                }
            }
            logger.info("DIe Icons wurden erfolgreich aktualisiert");
        } catch (IOException ex) {

            logger.warning("Die Icons konnten nicht aktualisiert werden");
        }
    }
}
