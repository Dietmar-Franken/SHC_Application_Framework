package de.rpi_controlcenter.AppServer.Model.Data.Room;

import de.rpi_controlcenter.AppServer.Model.Data.Element.AbstractElement;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Data.User.UserGroup;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidatePattern;

import java.util.*;

/**
 * Standard Anzeige Element
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractViewElement extends AbstractElement implements ViewElement {

    /**
     * erlaubte Benutzergruppen
     */
    private Set<String> allowedUserGroups = new HashSet<>();

    /**
     * Sichtbarkeit
     */
    private boolean visible = true;

    /**
     * Icon
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[a-fA-F0-9]{40}$", errorCode = 10001, message = "Ungültige ID")
    private String iconId = "";

    /**
     * fügt eine Benutzergruppe als erlaubte Benutzergruppe hinzu
     *
     * @param userGroupId Benutzergruppe
     */
    @Override
    public void addAllowedUserGroup(String userGroupId) {

        allowedUserGroups.add(userGroupId);
    }

    /**
     * entfernt die Benutzergruppe
     *
     * @param userGroupId Benutzergruppe
     * @return true wenn erfolgreich gelöscht
     */
    @Override
    public boolean removeAllowedUserGroup(String userGroupId) {

        return allowedUserGroups.remove(userGroupId);
    }

    /**
     * entfernt alle Benutzergruppen
     */
    @Override
    public void clearAllowedUserGroups() {

        allowedUserGroups.clear();
    }

    /**
     * gibt eine Liste mit allen erlaubten Benutzergruppen zurück
     *
     * @return erlaubte Benutzergruppen
     */
    @Override
    public Set<String> listAllowedUserGroups() {

        return Collections.unmodifiableSet(allowedUserGroups);
    }

    /**
     * prüft ob der übergebene Benutzer für dieses Element berechtigt ist
     *
     * @param user Benutzer
     * @return true wenn Berechtigt
     */
    @Override
    public boolean isUserEntitled(User user) {

        //wenn nicht eingeschränkt, alle erlauben
        if(allowedUserGroups.size() == 0) {

            return true;
        }

        //Gruppenzugehörigkeit prüfen
        Set<UserGroup> userGroups = user.getUserGroups();
        for(UserGroup userGroup : userGroups) {

            if(allowedUserGroups.contains(userGroup.getId())) {

                return true;
            }
        }
        return false;
    }

    /**
     * gibt an ob das Element sichtbar ist
     *
     * @return Sichtbarkeit
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * setzt die Sichtbarkeit des Elements
     *
     * @param visible Sichtbarkeit
     */
    @Override
    public void setVisible(boolean visible) {

        this.visible = visible;
    }

    /**
     * gibt das Icon zurück
     *
     * @return Icon
     */
    @Override
    public String getIcon() {
        return iconId;
    }

    /**
     * setzt das Icon
     *
     * @param iconId Icon
     */
    @Override
    public void setIcon(String iconId) {

        this.iconId = iconId;
    }
}
