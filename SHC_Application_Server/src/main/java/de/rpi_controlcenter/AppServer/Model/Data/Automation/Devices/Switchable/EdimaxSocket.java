package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;
import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

/**
 * Edimax Steckdose
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class EdimaxSocket extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_EDIMAX_SOCKET;

    enum SocketType {

        @SerializedName("SP_1101W")
        SP_1101W,
        @SerializedName("SP_2101W")
        SP_2101W
    }

    /**
     * IP Adresse
     */
    private String ipAddress;

    /**
     * Benutzername
     */
    private String username;

    /**
     * Passwort
     */
    private String password;

    /**
     * Typ
     */
    private SocketType socketType = SocketType.SP_1101W;

    /**
     * Sensorwerte der Steckdose
     */
    private String powerSensorId = "";
    private String energySensorId = "";

    /**
     * @param id ID
     * @param name Name
     */
    public EdimaxSocket(String id, String name) {
        super(id, name);
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
     * gibt den Benutzernamen zurück
     *
     * @return Benutzernamen
     */
    public String getUsername() {
        return username;
    }

    /**
     * setzt den Benutzernamen
     *
     * @param username Benutzernamen
     */
    public void setUsername(String username) {

        Preconditions.checkNotNull(username);
        Preconditions.checkArgument(username.length() >= 3, "Ungültiger Benutzernmame %s", username);
        this.username = username;
    }

    /**
     * gibt das Passwort zurück
     *
     * @return Passwort
     */
    public String getPassword() {
        return password;
    }

    /**
     * setzt das Passwort
     *
     * @param password Passwort
     */
    public void setPassword(String password) {

        Preconditions.checkNotNull(password);
        Preconditions.checkArgument(password.length() >= 3, "Ungültiges Passwort %s", password);
        this.password = password;
    }

    /**
     * gibt den Steckdosentyp zurück
     *
     * @return Steckdosentyp
     */
    public SocketType getSocketType() {
        return socketType;
    }

    /**
     * setzt den Steckdosentyp
     *
     * @param socketType Steckdosentyp
     */
    public void setSocketType(SocketType socketType) {

        Preconditions.checkNotNull(socketType);
        this.socketType = socketType;
    }

    /**
     * gibt den Hash des Verbrauchs Sensorwertes zurück
     *
     * @return Hash vom Sensorwert
     */
    public String getPowerSensorId() {
        return powerSensorId;
    }

    /**
     * setzt den Hash des Verbrauchs Sensorwertes
     *
     * @param powerSensorId Hash vom Sensorwert
     */
    public void setPowerSensorId(String powerSensorId) {

        Preconditions.checkNotNull(powerSensorId);
        Preconditions.checkArgument(powerSensorId.matches("^[a-fA-F0-9]{40}$"), "Ungültige ID %s (SAH-1 Prüfsumme erwartet)", powerSensorId);
        this.powerSensorId = powerSensorId;
    }

    /**
     * gibt den Hash des Energie Sensorwertes zurück
     *
     * @return Hash vom Sensorwert
     */
    public String getEnergySensorId() {
        return energySensorId;
    }

    /**
     * setzt den Hash des Energie Sensorwertes
     *
     * @param energySensorId Hash vom Sensorwert
     */
    public void setEnergySensorId(String energySensorId) {

        Preconditions.checkNotNull(energySensorId);
        Preconditions.checkArgument(energySensorId.matches("^[a-fA-F0-9]{40}$"), "Ungültige ID %s (SAH-1 Prüfsumme erwartet)", energySensorId);
        this.energySensorId = energySensorId;
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
