package de.rpi_controlcenter.AppServer.Model.Database;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    /**
     * gibt das Lockobjekt zurück
     *
     * @return Lockobjekt
     */
    ReentrantReadWriteLock getReadWriteLock();
}
