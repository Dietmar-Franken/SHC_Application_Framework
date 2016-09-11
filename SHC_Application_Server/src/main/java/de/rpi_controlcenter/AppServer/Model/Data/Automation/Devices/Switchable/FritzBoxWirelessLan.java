package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

/**
 * Fritz!BOx WLan Funktionen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class FritzBoxWirelessLan extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_FRITZ_BOX_WLAN;

    enum Wlan {

        @SerializedName("_2GHZ")
        _2GHZ,
        @SerializedName("_5GHZ")
        _5GHZ,
        @SerializedName("GUEST")
        GUEST
    }

    /**
     * Funktion
     */
    private Wlan wlan = Wlan.GUEST;

    /**
     * @param id ID
     * @param name Name
     */
    public FritzBoxWirelessLan(String id, String name) {
        super(id, name);
    }

    /**
     * gibt das Wlan zurück
     *
     * @return Wlan
     */
    public Wlan getWlan() {
        return wlan;
    }

    /**
     * setzt das Wlan Funktion
     *
     * @param wlan Wlan
     */
    public void setWlan(Wlan wlan) {

        Preconditions.checkNotNull(wlan);
        this.wlan = wlan;
    }

    /**
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    @Override
    public Type getType() {
        return type;
    }
}
