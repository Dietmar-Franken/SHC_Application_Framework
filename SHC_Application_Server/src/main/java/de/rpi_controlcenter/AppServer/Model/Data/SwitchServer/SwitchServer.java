package de.rpi_controlcenter.AppServer.Model.Data.SwitchServer;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Element.AbstractElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateIpAddress;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

/**
 * Schaltserver
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SwitchServer extends AbstractElement {

    public enum Type {

        @SerializedName("RASPBERRY_PI")
        RASPBERRY_PI,
        @SerializedName("MICRO_CONTROLLER")
        MICRO_CONTROLLER,
        @SerializedName("LOCALE")
        LOCALE
    }

    /**
     * IP Adresse
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateIpAddress(errorCode = 1003, message = "Ungültige IP Adresse %s")
    private String ipAddress;

    /**
     * Port
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10004, message = "Ungültiger Port %s")
    @ValidateMax(value = 65535, errorCode = 10004, message = "Ungültiger Port %s")
    private int port;

    /**
     * Timeout in ms
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10005, message = "Ungültiger Timeout %s, der Timeout ist kleiner als %sms")
    @ValidateMax(value = 10_000, errorCode = 10005, message = "Ungültiger Timeout %s, der Timeout ist größer als %sms")
    private int timeout = 500;

    /**
     * Typ
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private Type type;

    /**
     * aktiviert?
     */
    private boolean enabled = true;

    /**
     * senden von 433MHz Befehlen aktiv?
     */
    private boolean send433MHzEnabled = true;

    /**
     * schreiben von GPIOs aktiv?
     */
    private boolean writeGpioEnabled = true;

    /**
     * Scripte aktiv?
     */
    private boolean scriptEnabled = true;

    /**
     * Reboot aktiv?
     */
    private boolean rebootEnabled = true;

    /**
     * Shutdown Aktiv?
     */
    private boolean shutdownEnabled = true;

    /**
     * System Schaltserver (Kann nicht gelöscht werden)
     */
    private boolean isSystemSwitchServer = false;

    /**
     * @param id ID
     * @param name Name
     * @param type Typ
     * @param ipAddress IP Adresse
     * @param port Port
     * @param isSystemSwitchServer System Schaltserver
     */
    public SwitchServer(String id, String name, Type type, String ipAddress, int port, boolean isSystemSwitchServer) {

        setId(id);
        setName(name);
        setIpAddress(ipAddress);
        setPort(port);
        this.isSystemSwitchServer = isSystemSwitchServer;

        //Grundeinstellungen initalisieren
        Preconditions.checkNotNull(type);
        if(type == Type.MICRO_CONTROLLER) {

            scriptEnabled = false;
            rebootEnabled = false;
            shutdownEnabled = false;
        } else if(type == Type.LOCALE) {

            send433MHzEnabled = false;
            writeGpioEnabled = false;
            scriptEnabled = true;
            rebootEnabled = true;
            shutdownEnabled = true;
        }
        this.type = type;
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

        this.ipAddress = ipAddress;
    }

    /**
     * gibt den Port zurück
     *
     * @return Port
     */
    public int getPort() {
        return port;
    }

    /**
     * setzt den Port
     *
     * @param port Port
     */
    public void setPort(int port) {

        this.port = port;
    }

    /**
     * gibt den Timeout in ms zurück
     *
     * @return Timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * setzt den Timeout in ms
     *
     * @param timeout Timeout
     */
    public void setTimeout(int timeout) {

        this.timeout = timeout;
    }

    /**
     * gibt den Typ des Schaltservers an
     *
     * @return Typ
     */
    public Type getType() {
        return type;
    }

    /**
     * setzt den Typ des Schaltservers
     *
     * @param type Typ
     */
    public void setType(Type type) {

        this.type = type;
    }

    /**
     * gibt an ob der Schaltserver aktiviert/deaktiviert ist
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * aktiviert/deaktiviert den Schaltserver
     *
     * @param enabled aktiviert/deaktiviert
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * gibt an ob der Schaltserver 433MHz Befehle senden kann
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isSend433MHzEnabled() {
        return send433MHzEnabled;
    }

    /**
     * aktiviert/deaktiviert das Senden der 433MHz Befehler
     *
     * @param send433MHzEnabled aktiviert/deaktiviert
     */
    public void setSend433MHzEnabled(boolean send433MHzEnabled) {
        this.send433MHzEnabled = send433MHzEnabled;
    }

    /**
     * gibt an ob der Schaltserver GPIOs schreiben kann
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isWriteGpioEnabled() {
        return writeGpioEnabled;
    }

    /**
     * aktiviert/deaktiviert das Schreiben von GPIOs
     *
     * @param writeGpioEnabled aktiviert/deaktiviert
     */
    public void setWriteGpioEnabled(boolean writeGpioEnabled) {
        this.writeGpioEnabled = writeGpioEnabled;
    }

    /**
     * gibt an ob der Schaltserver Scripte ausführen kann
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isScriptEnabled() {
        return scriptEnabled;
    }

    /**
     * aktiviert/deaktiviert das Ausführen von Scripten
     *
     * @param scriptEnabled aktiviert/deaktiviert
     */
    public void setScriptEnabled(boolean scriptEnabled) {
        this.scriptEnabled = scriptEnabled;
    }

    /**
     * gibt an ob der Schaltserver neu gestartet werden kann
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isRebootEnabled() {
        return rebootEnabled;
    }

    /**
     * aktiviert/deaktiviert das Neustarten des Schaltservers
     *
     * @param rebootEnabled aktiviert/deaktiviert
     */
    public void setRebootEnabled(boolean rebootEnabled) {
        this.rebootEnabled = rebootEnabled;
    }

    /**
     * gibt an ob der Schaltserver Heruntergefahren werden kann
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isShutdownEnabled() {
        return shutdownEnabled;
    }

    /**
     * aktiviert/deaktiviert das Herunterfahren des Schaltservers
     *
     * @param shutdownEnabled aktiviert/deaktiviert
     */
    public void setShutdownEnabled(boolean shutdownEnabled) {
        this.shutdownEnabled = shutdownEnabled;
    }

    /**
     * gibt an ob der Schaltserver ein System Schaltserver ist
     *
     * @return Systemschaltserver
     */
    public boolean isSystemSwitchServer() {
        return isSystemSwitchServer;
    }
}
