package de.rpi_controlcenter.AppServer.Model.Database;

import de.rpi_controlcenter.AppServer.Model.Data.Element.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Datenbank Verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractDatabaseEditor<T extends Element> implements DatabaseEditor {

    /**
     * Liste aller Elemente
     */
    private List<T> data = new ArrayList<T>();

    /**
     * lädt die Elemente aus der Datenbank
     */
    public abstract void load();

    /**
     * erzeugt einen neuen Hash zur eindeutigen Identifizierung
     *
     * @return neue eindeutige Identifizierung
     */
    public static String createId() {

        long nanoTime = System.nanoTime();

        MessageDigest md = null;
        try {

            md = MessageDigest.getInstance("SHA1");

            md.reset();
            byte[] buffer = (Long.toString(nanoTime) + "-" + Integer.toString((int) Math.random() * 100_000)).getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException("es konnte kein Hash erstellt werden", e);
        }
    }

    /**
     * gibt die Liste mit allen Elementen zurück
     *
     * @return Liste mit Elementen
     */
    public List<T> getData() {

        return data;
    }

    /**
     * sucht nach eine Element mit dem übergebenen Hash
     *
     * @param id Hash
     * @return Element
     */
    public Optional<T> getById(String id) {

        for(T element : data) {

            if(element.getId().equalsIgnoreCase(id)) {

                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    /**
     * sucht nach eine Element mit dem übergebenen Namen
     *
     * @param name Name
     * @return Element
     */
    public Optional<T> getByName(String name) {

        for(T element : data) {

            if(element.getName().equalsIgnoreCase(name)) {

                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    public abstract void dump();
}
