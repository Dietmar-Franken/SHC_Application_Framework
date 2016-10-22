package de.rpi_controlcenter.AppServer.Model.Editor;

import de.rpi_controlcenter.AppServer.Model.Data.Session.Session;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Sitzungen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SessionEditor {

    /**
     * Liste aller Sitzungen
     */
    private volatile List<Session> sessions = new ArrayList();;

    /**
     * bekannte Challanges
     */
    private volatile Set<String> challanges = new HashSet<>();;

    /**
     * Lock objekt
     */
    private volatile ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    /**
     * löscht abgelaufene Sitzungen
     */
    public void cleanOldSessions() {

        for(Session session : sessions) {

            if(session.isExpired()) {

                sessions.remove(session);
            }
        }
    }

    /**
     * gibt die Liste der bekannten Challenges zurück
     *
     * @return Challenges
     */
    public Set<String> getChallenges() {

        return this.challanges;
    }

    /**
     * erstellt eine neue Benutzersitzung
     *
     * @param user Benutzer
     * @return Sitzungs ID
     */
    public String login(User user) {

        if(user != null) {

            //neue Session erstellen
            String sessionId = AbstractDatabaseEditor.createId();
            Session session = new Session(sessionId, user.getId());
            session.updateExpire();
            sessions.add(session);
            return session.getSessionId();
        }
        return null;
    }

    /**
     * beendet eine Sitzung
     *
     * @param sessionId Sitzungs ID
     * @return true wenn erfolgreich
     */
    public boolean logout(String sessionId) {

        return removeSession(sessionId);
    }

    /**
     * gibt eine Sitzung zurück
     * (Das Session Objekt sollte nicht dauerhaft zwischengespeichert werden)
     *
     * @param sessionId Sitzungs ID
     * @return Sitzung
     */
    public Optional<Session> getSessionById(String sessionId) {

        Optional<Session> session = sessions.stream().filter(s -> s.getSessionId().equals(sessionId)).findFirst();
        if(session.isPresent()) {

            if(!session.get().isExpired()) {

                session.get().updateExpire();
                return session;
            } else {

                removeSession(session.get().getSessionId());
            }
        }
        return Optional.empty();
    }

    /**
     * beendet eine Sitzung
     *
     * @param sessionId Sitzungs ID
     * @return true wenn erfolgreich
     */
    private boolean removeSession(String sessionId) {

        Optional<Session> session = sessions.stream().filter(s -> s.getSessionId().equals(sessionId)).findFirst();
        if(session.isPresent()) {

            sessions.remove(session.get());
            return true;
        }
        return false;
    }

    /**
     * erzeugt einen Token
     *
     * @return Token
     */
    public String createToken() {

        StringBuilder sb = new StringBuilder();
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom secureRandom = new SecureRandom();

        for(int i = 0; i < 40; i++) {

            sb.append(possibleChars.charAt(secureRandom.nextInt(possibleChars.length())));
        }
        return sb.toString();
    }

    /**
     * gibt das Lockobjekt zurück
     *
     * @return Lockobjekt
     */
    public ReentrantReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    /**
     * gibt ein Lock Objekt zum erlangen des Leselock zurück
     *
     * @return Lock Objekt
     */
    public ReentrantReadWriteLock.ReadLock readLock() {
        return readWriteLock.readLock();
    }

    /**
     * gibt ein Lock Objekt zum erlangen des Schreiblock zurück
     *
     * @return Lock Objekt
     */
    public ReentrantReadWriteLock.WriteLock writeLock() {
        return readWriteLock.writeLock();
    }
}
