package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

import java.util.HashSet;
import java.util.Set;

/**
 * Funksteckdose
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class RadioSocket extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_RADIO_SOCKET;

    /**
     * liste der Schaltserverhashes über die der Befehl gesendet werden soll
     */
    private Set<String> switchServers = new HashSet<>();

    /**
     * 433MHz Protokoll
     */
    private String protocol = "";

    /**
     * Systemcode
     */
    private String systemCode = "";

    /**
     * Gerätecode
     */
    private String deviceCode = "";

    /**
     * sende Weiderholungen
     */
    private int continues = 1;

    /**
     * ID oder Systemcode beim senden verwenden
     */
    private boolean useID = false;

    /**
     * @param id ID
     * @param name Name
     */
    public RadioSocket(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Liste der Schaltserverhashes zurück
     *
     * @return Liste der Schaltserverhashes
     */
    public Set<String> getSwitchServers() {
        return switchServers;
    }

    /**
     * gibt das 433MHz Protokoll zurück
     *
     * @return 433MHz Potokoll
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * setzt das 433MHz Potokoll
     *
     * @param protocol 433MHz Potokoll
     */
    public void setProtocol(String protocol) {

        Preconditions.checkNotNull(protocol);
        Preconditions.checkArgument(protocol.length() >= 3, "Ungültiges Protokoll %s", protocol);
        this.protocol = protocol;
    }

    /**
     * gibt den Systemcode zurück
     *
     * @return Systemcode
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * setzt den Systemcode
     *
     * @param systemCode Systemcode
     */
    public void setSystemCode(String systemCode) {

        Preconditions.checkNotNull(systemCode);
        Preconditions.checkArgument(systemCode.length() >= 1, "Ungültiger System Code %s", systemCode);
        this.systemCode = systemCode;
    }

    /**
     * gibt den Gerätecode zurück
     *
     * @return Gerätecode
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * setzt den Gerätecode
     *
     * @param deviceCode Gerätecode
     */
    public void setDeviceCode(String deviceCode) {

        Preconditions.checkNotNull(deviceCode);
        Preconditions.checkArgument(deviceCode.length() >= 1, "Ungültiger Geräte Code %s", deviceCode);
        this.deviceCode = deviceCode;
    }

    /**
     * gibt die Anzahl der Sendewiederholungen zurück
     *
     * @return Anzahl der Sendewiederholungen
     */
    public int getContinues() {
        return continues;
    }

    /**
     * setzt die Anzahl der Sendewiederholungen
     *
     * @param continues Anzahl der Sendewiederholungen
     */
    public void setContinues(int continues) {

        Preconditions.checkNotNull(continues);
        Preconditions.checkArgument(continues >= 1 && continues <= 20, "Ungültige Anzah der Wiederholungen %i", continues);
        this.continues = continues;
    }

    /**
     * gibt an ob die ID statt des Systemcodes verwendet werden soll
     *
     * @return ID wenn true
     */
    public boolean isUseID() {
        return useID;
    }

    /**
     * aktiviert die Verwendung von ID beim senden
     *
     * @param useID ID wenn true
     */
    public void setUseID(boolean useID) {
        this.useID = useID;
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
