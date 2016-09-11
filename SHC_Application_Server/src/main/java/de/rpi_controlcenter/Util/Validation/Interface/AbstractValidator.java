package de.rpi_controlcenter.Util.Validation.Interface;

import de.rpi_controlcenter.Util.Validation.ValidationError;

import java.util.Optional;

/**
 * Standard Implementierung Validator
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractValidator implements Validator {

    /**
     * Validierungsfehler
     */
    private ValidationError validationError = null;

    /**
     * setzt das Fehlerobjekt
     *
     * @param validationError Fehlerobjekt
     */
    protected void setValidationError(ValidationError validationError) {
        this.validationError = validationError;
    }

    /**
     * wenn Validierung Fehlgeschlagen gibt die Funktion das Fehlerobjekt zurück
     *
     * @return Fehlgeschlagen
     */
    public Optional<ValidationError> getValidationError() {
        return Optional.ofNullable(validationError);
    }
}
