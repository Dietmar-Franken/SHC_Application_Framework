package de.rpi_controlcenter.AppServer.Model.Data.Element;

/**
 * SHC Element (Datenobjekt)
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface Element {

    /**
     * gibt die eindeutige Identifizierung des Elements zurück
     *
     * @return ID
     */
    String getId();

    /**
     * setzt die die eindeutige Identifizierung des Elements
     *
     * @param id ID
     */
    void setId(String id);

    /**
     * gibt den Namen des Elements zurück
     *
     * @return Name
     */
    String getName();

    /**
     * setzt den Namen des Elements
     *
     * @param name Name
     */
    void setName(String name);
}
