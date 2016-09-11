package de.rpi_controlcenter.Util.Validation.Validator;

import de.rpi_controlcenter.Util.Validation.Annotation.ValidateEmpty;
import de.rpi_controlcenter.Util.Validation.Interface.AbstractValidator;
import de.rpi_controlcenter.Util.Validation.ValidationError;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * prüft ob ein String leer ist
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class EmptyValidator extends AbstractValidator {

    /**
     * validiert das Feld
     * wenn der Validator nicht für das feld zurifft wird true zurück gegeben
     *
     * @param object         Objekt
     * @param field          Feld
     * @param resourceBundle Sprachpaket
     * @return valide
     */
    @Override
    public boolean validate(Object object, Field field, Optional<ResourceBundle> resourceBundle) throws IllegalAccessException {

        //prüfen on Annotation vorhanden
        if(field.isAnnotationPresent(ValidateEmpty.class)) {

            //Datentypen Filtern
            Class type = field.getType();
            if(type.isAssignableFrom(String.class)) {

                ValidateEmpty annotation = field.getAnnotation(ValidateEmpty.class);
                field.setAccessible(true);
                String value = (String) field.get(object);

                //Prüfbedingung
                if(!value.isEmpty()) {

                    //nicht Valid
                    String message = annotation.message();
                    if(resourceBundle.isPresent() && resourceBundle.get().getString(annotation.message()).length() > 0) {

                        //mit Sprachvariablen
                        message = resourceBundle.get().getString(annotation.message());
                    }
                    message = String.format(message, field.getName());
                    setValidationError(new ValidationError(field.getName(), annotation.errorCode(), message));
                    return false;
                }
            }
        }
        return true;
    }
}
