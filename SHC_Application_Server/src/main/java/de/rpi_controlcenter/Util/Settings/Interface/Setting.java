package de.rpi_controlcenter.Util.Settings.Interface;

import com.google.gson.annotations.SerializedName;

/**
 * Einstellung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2015, Oliver Kleditzsch
 */
public interface Setting {

    /**
     * Einstellungstypen
     */
    enum Type {

        @SerializedName("STRING")
        STRING,
        @SerializedName("INTEGER")
        INTEGER,
        @SerializedName("DOUBLE")
        DOUBLE,
        @SerializedName("BOOLEAN")
        BOOLEAN,
        @SerializedName("LIST")
        LIST
    }

    /**
     * gibt den Namen der Einstellung zurück
     *
     * @return Name
     */
    String getName();

    /**
     * setzt den Namen der Einstellung
     *
     * @param name Name
     */
    void setName(String name);

    /**
     * gibt den Typ der Einstellung zurück
     *
     * @return Typ
     */
    Type getType();
}
