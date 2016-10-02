package de.rpi_controlcenter.AppServer.Model.Data.Room;

import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Element.Element;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;

import java.util.Set;

/**
 * Anzeige Element
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface ViewElement extends Element {

    enum Type {

        @SerializedName("ROOM")
        ROOM,
        @SerializedName("BUTTON")
        BUTTON,
        @SerializedName("SENSOR")
        SENSOR,
        @SerializedName("VIRTUAL_SENSOR")
        VIRTUAL_SENSOR,
        @SerializedName("SEPARATOR")
        SEPARATOR
    }

    /**
     * fügt eine Benutzergruppe als erlaubte Benutzergruppe hinzu
     *
     * @param userGroupId Benutzergruppe
     */
    void addAllowedUserGroup(String userGroupId);

    /**
     * entfernt die Benutzergruppe
     *
     * @param userGroupId Benutzergruppe
     * @return true wenn erfolgreich gelöscht
     */
    boolean removeAllowedUserGroup(String userGroupId);

    /**
     * entfernt alle Benutzergruppen
     */
    void clearAllowedUserGroups();

    /**
     * gibt eine Liste mit allen erlaubten Benutzergruppen zurück
     *
     * @return erlaubte Benutzergruppen
     */
    Set<String> listAllowedUserGroups();

    /**
     * prüft ob der übergebene Benutzer für dieses Element berechtigt ist
     *
     * @param user Benutzer
     * @return true wenn Berechtigt
     */
    boolean isUserEntitled(User user);

    /**
     * gibt an ob das Element sichtbar ist
     *
     * @return Sichtbarkeit
     */
    boolean isVisible();

    /**
     * setzt die Sichtbarkeit des Elements
     *
     * @param visible Sichtbarkeit
     */
    void setVisible(boolean visible);

    /**
     * gibt das Icon zurück
     *
     * @return Icon
     */
    String getIcon();

    /**
     * setzt das Icon
     *
     * @param iconId Icon
     */
    void setIcon(String iconId);

    /**
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    Type getType();
}
