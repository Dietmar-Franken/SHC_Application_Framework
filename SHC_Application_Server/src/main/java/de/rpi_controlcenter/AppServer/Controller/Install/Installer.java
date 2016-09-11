package de.rpi_controlcenter.AppServer.Controller.Install;

import de.rpi_controlcenter.AppServer.Model.Data.SwitchServer.SwitchServer;
import de.rpi_controlcenter.AppServer.Model.Data.User.Permission;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Data.User.UserGroup;
import de.rpi_controlcenter.AppServer.Model.Editor.SwitchServerEditor;
import de.rpi_controlcenter.AppServer.Model.Editor.UserGroupsEditor;
import de.rpi_controlcenter.AppServer.Model.Editor.UsersEditor;
import de.rpi_controlcenter.Util.Logger.LoggerUtil;

import java.util.Locale;
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

        logger.info("Installation erfolgreich beendet");
        return true;
    }
}
