package de.rpi_controlcenter.Util.Validation.Annotation;

/**
 * prüfen ob der String eine gültige IP Adresse enthält
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public @interface ValidateIpAddress {

    int errorCode() default 0;
    String message() default "";
}
