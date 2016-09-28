package de.rpi_controlcenter.AppServer.Controller.Install;

import de.rpi_controlcenter.AppServer.Model.Data.SwitchServer.SwitchServer;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Data.User.UserGroup;
import de.rpi_controlcenter.AppServer.Model.Editor.IconEditor;
import de.rpi_controlcenter.AppServer.Model.Editor.SwitchServerEditor;
import de.rpi_controlcenter.AppServer.Model.Editor.UserGroupsEditor;
import de.rpi_controlcenter.AppServer.Model.Editor.UsersEditor;
import de.rpi_controlcenter.Util.Logger.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * Installiert die Anwendung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class Installer {

    private static Logger logger = LoggerUtil.getLogger(Installer.class);

    /**
     * Installiert die Anwendung
     */
    public static boolean install() {

        logger.info("start der Installation");

        //Benutzergruppen
        UserGroup adminGroup = new UserGroup(UsersEditor.createId(), "Administratoren", true);
        adminGroup.setDescripion("Administratoren können das SHC Verwalten");
        for(Permission permission :Permission.values()) {

            adminGroup.addPermission(permission);
        }

        UserGroup userGroup = new UserGroup(UsersEditor.createId(), "Benutzer", true);
        userGroup.setDescripion("Benutzer können die Funktionen des SHC nutzen");
        userGroup.addPermission(Permission.SWITCH_ELEMENTS);

        UserGroupsEditor userGroupsEditor = new UserGroupsEditor();
        userGroupsEditor.getData().add(adminGroup);
        userGroupsEditor.getData().add(userGroup);
        userGroupsEditor.dump();

        //Benutzer
        User adminUser = new User(UsersEditor.createId(), "admin", true);
        adminUser.setPassword("admin");
        adminUser.setLocale(Locale.GERMAN);
        adminUser.getUserGroups().add(adminGroup);

        UsersEditor usersEditor = new UsersEditor();
        usersEditor.getData().add(adminUser);
        usersEditor.dump();

        //Schaltserver
        SwitchServer switchServer = new SwitchServer(SwitchServerEditor.createId(), "Lokal", SwitchServer.Type.LOCALE, "localhost", 0, true);

        SwitchServerEditor switchServerEditor = new SwitchServerEditor();
        switchServerEditor.getData().add(switchServer);
        switchServerEditor.dump();

        //Icons aus der JarDatei in den Icons Ordner im Dateisystem kopieren
        Path jarFile = Paths.get(Installer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if(jarFile.getFileName().toString().toLowerCase().endsWith(".jar")) {

            copyIconsToFileSystem(jarFile);
            IconEditor iconEditor = new IconEditor();
            iconEditor.updateIconsFromFileSystem();
            iconEditor.dump();
        }

        logger.info("Installation erfolgreich beendet");
        return true;
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
            logger.info("DIe Icons wurden erfolgreich installiert");
        } catch (IOException ex) {

            logger.warning("Die Icons konnten nicht installiert werden");
        }
    }
}
