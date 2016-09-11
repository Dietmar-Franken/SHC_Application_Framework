package de.rpi_controlcenter.AppServer.Model.Data.User;

import de.rpi_controlcenter.AppServer.Model.Data.Element.AbstractElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidatePattern;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Benutzer
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class User extends AbstractElement {

    /**
     * Passwort
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[\\w -_.:!?&%$§#+]{3,20}$", errorCode = 10100, message = "Ungültiges Passwort")
    private String password = "";

    /**
     * Gründer? (kann nicht gelöscht werden und hat immer alle Berechtigungen)
     */
    private boolean originator = false;

    /**
     * Lokalisierung
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private Locale locale = Locale.GERMAN;

    /**
     * Benutzergruppen zu denen der Benutzer gehört
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10101, message = "Es wurde keine Benutzergruppe zugeordnet")
    private Set<UserGroup> userGroups = new HashSet<>();

    /**
     * @param id ID
     * @param name Benutzername
     */
    public User(String id, String name) {

        setId(id);
        setName(name);
    }

    /**
     * @param id ID
     * @param name Benutzername
     * @param originator Gründer
     */
    public User(String id, String name, boolean originator) {

        setId(id);
        setName(name);
        this.originator = originator;
    }

    /**
     * gibt an ob der Benutzer ein Gründer ist
     *
     * @return Gründer
     */
    public boolean isOriginator() {
        return originator;
    }

    /**
     * gibt eine Liste mit allen Benutzergruppen zurück zu denen der Benutzer gehört
     *
     * @return Liste mit Benutzergruppen
     */
    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    /**
     * gibt den Passwort Hash zurück
     *
     * @return Passwort Hash
     */
    public String getPassword() {
        return password;
    }

    /**
     * setzt den Passwort Hash
     *
     * @param password Passwort Hash
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * gibt die Lokalisierung des Benutzers zurück
     *
     * @return Lokalisierung
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * setzt die Lokalisierung des Benutzers
     *
     * @param locale Lokalisierung
     */
    public void setLocale(Locale locale) {

        this.locale = locale;
    }

    /**
     * prüft die Berechtigungen des Benutzers
     *
     * @param permission Berechtigung
     * @return true wenn Berechtigung vorhanden
     */
    public boolean checkPermission(Permission permission) {

        //der Gründer hat immer alle Berechtigungen
        if(isOriginator()) {

            return true;
        }

        //Gruppenrechte prüfen
        for(UserGroup userGroup : userGroups) {

            if(userGroup.checkPermission(permission)) {

                return true;
            }
        }

        return false;
    }
}
