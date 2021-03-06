package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractSingleSwitchable;

/**
 * WAKE On Lan
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class WakeOnLan extends AbstractSingleSwitchable {

    private Type type = Type.SWITCHABLE_WAKE_ON_LAN;

    /**
     * MAC Adresse
     */
    private String mac;

    /**
     * IP Adresse
     */
    private String ipAddress;

    /**
     * @param id ID
     * @param name Name
     */
    public WakeOnLan(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die MAC Adresse zurück
     *
     * @return MAC Adresse
     */
    public String getMac() {
        return mac;
    }

    /**
     * setzt die MAC Adresse
     *
     * @param mac MAC Adresse
     */
    public void setMac(String mac) {

        Preconditions.checkNotNull(mac);
        Preconditions.checkArgument(mac.matches("^[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}:[a-fA-F0-9]{2}$"), "Ungültige MAC Adresse %s", mac);
        this.mac = mac;
    }

    /**
     * gibt die IP Adresse zurück
     *
     * @return IP Adresse
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * setzt die IP Adresse
     *
     * @param ipAddress IP Adresse
     */
    public void setIpAddress(String ipAddress) {

        Preconditions.checkNotNull(ipAddress);
        Preconditions.checkArgument(InetAddresses.isInetAddress(ipAddress), "Ungültige IP Adresse %s", ipAddress);
        this.ipAddress = ipAddress;
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
