package de.rpi_controlcenter.AppServer.Model.Database;

/**
 * Datenbank Verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface DatabaseEditor {

    /**
     * lädt die Elemente aus der Datenbank
     */
    void load();

    /**
     * speichert die Elemente in der Datenbank
     */
    void dump();
}
