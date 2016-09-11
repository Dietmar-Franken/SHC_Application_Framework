package de.rpi_controlcenter.AppServer.Model.Data.Session;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidatePattern;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Benutzersitzung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class Session {

    /**
     * Session ID
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[a-fA-F0-9]{40}$", errorCode = 10001, message = "Ungültige Session ID")
    private String sessionId;

    /**
     * Session Timeout
     */
    private LocalDateTime sessionTimeout;

    /**
     * Angemeldeter Benutzer
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[a-fA-F0-9]{40}$", errorCode = 10001, message = "Ungültige Benutzer ID")
    private String userId;

    /**
     * Session Daten
     */
    private Map<String, String> sessionData = new HashMap<>();

    /**
     * @param sessionId ID der Sitzung
     */
    public Session(String sessionId) {

        Preconditions.checkNotNull(sessionId);
        Preconditions.checkArgument(sessionId.matches("^[a-fA-F0-9]{32}$"), "Ungültige Session ID %s (MD5 Prüfsumme erwartet)", sessionId);
        this.sessionId = sessionId;
        updateExpire();
    }

    /**
     * @param sessionId ID der Sitzung
     * @param userId Benutzer der Sitzung
     */
    public Session(String sessionId, String userId) {

        Preconditions.checkNotNull(sessionId);
        Preconditions.checkArgument(sessionId.matches("^[a-fA-F0-9]{40}$"), "Ungültige Session ID %s (SAH-1 Prüfsumme erwartet)", sessionId);
        Preconditions.checkNotNull(userId);
        Preconditions.checkArgument(userId.matches("^[a-fA-F0-9]{40}$"), "Ungültige Benutzer ID %s (SAH-1 Prüfsumme erwartet)", userId);
        this.sessionId = sessionId;
        this.userId = userId;
        updateExpire();
    }

    /**
     * gibt die Session ID zurück
     *
     * @return Session ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * gibt den Benutzer Hash des mit der Session verbundenen Benutzers zurück
     *
     * @return Benutzer Hash
     */
    public Optional<String> getUser() {
        return Optional.ofNullable(userId);
    }

    /**
     * gibt die Sessiondaten zurück
     *
     * @return Sessiondaten
     */
    public Map<String, String> getSessionData() {
        return sessionData;
    }

    /**
     * verlängert die Sitzungsdauer wieder um 600 Sekunden
     *
     * @return true wenn erfolgreich (false wenn Session schon abgelaufe ist)
     */
    public boolean updateExpire() {

        if(!isExpired()) {

            sessionTimeout = LocalDateTime.now().plusSeconds(600);
            return true;
        }
        return false;
    }

    /**
     * prüft ob die Session abgelaufen ist
     *
     * @return true wenn abgelaufen
     */
    public boolean isExpired() {

        return sessionTimeout != null && (sessionTimeout.isBefore(LocalDateTime.now()) || sessionTimeout.isEqual(LocalDateTime.now()));
    }
}
