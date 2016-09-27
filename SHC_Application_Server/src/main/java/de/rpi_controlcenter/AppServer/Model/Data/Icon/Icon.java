package de.rpi_controlcenter.AppServer.Model.Data.Icon;

import de.rpi_controlcenter.AppServer.Model.Data.Element.AbstractElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotEmpty;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;

import java.util.HashSet;
import java.util.Set;

/**
 * Icon
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class Icon extends AbstractElement {

    /**
     * Liste mit den verfügbaren Auflösungen
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateSize(min = 1, errorCode = 10009, message = "Es muss mindestens eine Auflösung vorhanden sein")
    private Set<Integer> availableSize = new HashSet<>();

    /**
     * Basis Dateiname
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateNotEmpty(errorCode = 10007, message = "die Zeichenkette darf nicht leer sein")
    private String baseFileName = "";

    /**
     * Basispfad
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateNotEmpty(errorCode = 10007, message = "die Zeichenkette darf nicht leer sein")
    private String basePath = "";

    /**
     * @param id ID
     * @param name Name
     */
    public Icon(String id, String name) {

        setId(id);
        setName(name);
    }

    /**
     * gibt die Liste mit den verfügbaren Auflösungen zurück
     *
     * @return Liste mit den verfügbaren Auflösungen
     */
    public Set<Integer> getAvailableSize() {
        return availableSize;
    }

    /**
     * gibt den Basisdateinamen zurück
     *
     * @return Basisdateinamen
     */
    public String getBaseFileName() {
        return baseFileName;
    }

    /**
     * setzt den Basisdateinamen
     *
     * @param baseFileName Basisdateinamen
     */
    public void setBaseFileName(String baseFileName) {
        this.baseFileName = baseFileName;
    }

    /**
     * gibt den Basispfad zurück
     *
     * @return Basispfad
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * setzt den Basispfad
     *
     * @param basePath Basispfad
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
