package de.rpi_controlcenter.AppServer.Model.Data.Device;

import de.rpi_controlcenter.Util.Validation.Annotation.ValidateLength;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidatePattern;

import java.time.LocalDateTime;

/**
 * Gerät eines Benutzers
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ClientDevice {

    /**
     * ClientData Hash
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[a-fA-F0-9]{40}$", errorCode = 10006, message = "Ungültiger Clent Hash")
    private String clientHash;

    /**
     * Gerätekennung
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateLength(min = 10, max = 255, errorCode = 10007, message = "Ungültiger UserAgent %s")
    private String userAgent;

    /**
     * Zutritt erlaubt
     */
    private boolean allowed = false;

    /**
     * letzter Login
     */
    private LocalDateTime lastLogin;

    /**
     * @param clientHash Client Hash
     * @param userAgent Client Kennung
     */
    public ClientDevice(String clientHash, String userAgent) {

        setClientHash(clientHash);
        setUserAgent(userAgent);
    }

    /**
     * gibt den ClientData Hash zurück
     *
     * @return ClientData Hash
     */
    public String getClientHash() {
        return clientHash;
    }

    /**
     * setzt den ClientData Hasn
     *
     * @param clientHash ClientData Hash
     */
    public void setClientHash(String clientHash) {

        this.clientHash = clientHash;
    }

    /**
     * gibt die Gerätekennung zurück
     *
     * @return Gerätekennung
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * setzt die Gerätekennung
     *
     * @param userAgent Gerätekennung
     */
    public void setUserAgent(String userAgent) {

        this.userAgent = userAgent;
    }

    /**
     * gibt an ob für das Gerät der Zutritt erlaubt ist
     *
     * @return true wenn erlaubt
     */
    public boolean isAllowed() {
        return allowed;
    }

    /**
     * setzt die Zutrittsbeschränkung für das Gerät
     *
     * @param allowed true wenn erlaubt
     */
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    /**
     * gibt die zeit des letzten Logions zurück
     *
     * @return Letzter Login
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    /**
     * setzt die Zeit des letzten Logins
     *
     * @param lastLogin Letzter Login
     */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
