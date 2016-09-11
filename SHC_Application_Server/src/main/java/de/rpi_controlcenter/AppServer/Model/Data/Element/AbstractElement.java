package de.rpi_controlcenter.AppServer.Model.Data.Element;

import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidatePattern;

/**
 * SHC Element (Datenobjekt)
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class AbstractElement implements Element {

    /**
     * Eindeutige Identifizierung
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[a-fA-F0-9]{40}$", errorCode = 10001, message = "Ungültige ID")
    private String id = "";

    /**
     * Name des Elements
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[\\w -_.:!?&%$§#+]{3,20}$", errorCode = 10001, message = "Ungültiger Name")
    private String name = "";

    /**
     * gibt die eindeutige Identifizierung des Elements zurück
     *
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * setzt die die eindeutige Identifizierung des Elements
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * gibt den Namen des Elements zurück
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen des Elements
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gibt den Namen des Elements zurück
     *
     * @return Name
     */
    @Override
    public String toString() {
        return getName();
    }
}
