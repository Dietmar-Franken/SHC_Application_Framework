package de.rpi_controlcenter.AppServer.Model.Data.User;

import com.google.gson.annotations.SerializedName;

/**
 * Berechtigungen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public enum Permission {

    @SerializedName("ENTER_ACP")
    ENTER_ACP,
    @SerializedName("MANAGE_USERS")
    MANAGE_USERS,
    @SerializedName("MANAGE_KNOWN_DEVICES")
    MANAGE_KNOWN_DEVICES,
    @SerializedName("MANAGE_SETTINGS")
    MANAGE_SETTINGS,
    @SerializedName("MANAGE_ELEMENTS")
    MANAGE_ELEMENTS,
    @SerializedName("MANAGE_ROOMS")
    MANAGE_ROOMS,

    @SerializedName("SWITCH_ELEMENTS")
    SWITCH_ELEMENTS
}
