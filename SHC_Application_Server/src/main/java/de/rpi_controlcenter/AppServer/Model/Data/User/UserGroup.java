package de.rpi_controlcenter.AppServer.Model.Data.User;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Element.AbstractElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Benutzergruppe
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class UserGroup extends AbstractElement {

    /**
     * Beschreibung der Gruppe
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private String descripion = "";

    /**
     * Systemgruppe (kann nicht gelöscht werden)
     */
    private boolean systemGroup = false;

    /**
     * liste mit den Berechtigungen der Gruppe
     */
    private Set<Permission> permissions = new HashSet<>();

    /**
     * @param id ID
     * @param name Name
     */
    public UserGroup(String id, String name) {

        setId(id);
        setName(name);
    }

    /**
     * @param id ID
     * @param name Name
     * @param systemGroup Systemgruppe
     */
    public UserGroup(String id, String name, boolean systemGroup) {

        setId(id);
        setName(name);
        this.systemGroup = systemGroup;
    }

    /**
     * gibt die Gruppenbeschreibung zurück
     *
     * @return Gruppenbeschreibung
     */
    public String getDescripion() {
        return descripion;
    }

    /**
     * setzt die Gruppenbeschreibung
     *
     * @param descripion Gruppenbeschreibung
     */
    public void setDescripion(String descripion) {

        Preconditions.checkNotNull(descripion);
        this.descripion = descripion;
    }

    /**
     * gibt an ob die Gruppe eine Systemgruppe ist
     *
     * @return prüft ob die Gruppe eine Systemgruppe ist
     */
    public boolean isSystemGroup() {
        return systemGroup;
    }

    /**
     * gibt die Liste der Berechtigungen zurück
     *
     * @return Liste der Berechtigungen zurück
     */
    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    /**
     * fügt eine Berechtigung hinzu
     *
     * @param permission Berechtigung
     */
    public void addPermission(Permission permission) {

        Preconditions.checkNotNull(permission);
        permissions.add(permission);
    }

    /**
     * löscht eine Berechtigung
     *
     * @param permission Berechtigung
     * @return eroflg
     */
    public boolean removePermission(Permission permission) {
        return permissions.remove(permission);
    }

    /**
     * prüft die Berechtigungen der Benutzergruppe
     *
     * @param permission Berechtigung
     * @return true wenn die Berechtigung vorhanden ist
     */
    public boolean checkPermission(Permission permission) {

        return permissions.contains(permission);
    }
}
