package de.rpi_controlcenter.Util.Validation.Validator;

import de.rpi_controlcenter.Util.Validation.Annotation.ValidateSize;
import de.rpi_controlcenter.Util.Validation.Interface.AbstractValidator;
import de.rpi_controlcenter.Util.Validation.ValidationError;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * prüft ob sich die Größe in einem bestimmten Intervall befindet
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SizeValidator extends AbstractValidator {

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
        if(field.isAnnotationPresent(ValidateSize.class)) {

            //Datentypen Filtern
            Class type = field.getType();
            if(Arrays.asList(type.getInterfaces()).contains(Collection.class)) {

                ValidateSize annotation = field.getAnnotation(ValidateSize.class);
                int min = annotation.min();
                int max = annotation.max();
                field.setAccessible(true);
                Collection value = (Collection) field.get(object);

                //Prüfbedingung
                if(value.size() < min || value.size() > max) {

                    //nicht Valid
                    String message = annotation.message();
                    if(resourceBundle.isPresent() && resourceBundle.get().getString(annotation.message()).length() > 0) {

                        //mit Sprachvariablen
                        message = resourceBundle.get().getString(annotation.message());
                    }
                    message = String.format(message, NumberFormat.getInstance().format(min), NumberFormat.getInstance().format(max));
                    setValidationError(new ValidationError(field.getName(), annotation.errorCode(), message));
                    return false;
                }
            }
        }
        return true;
    }
}
