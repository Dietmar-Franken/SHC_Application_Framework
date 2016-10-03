package de.rpi_controlcenter.AppServer.Model.Database;

import de.rpi_controlcenter.AppServer.Model.Data.Element.Element;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
     * Lock objekt
     */
    private volatile ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

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
     * gibt eine Liste mit den Elementen der IDs zurück
     *
     * @param sensorIds Liste mit IDs
     * @return Liste mit Elementen
     */
    public Set<T> getSublist(Set<String> sensorIds) {

        Set<T> subList = new HashSet<T>();
        for(String id : sensorIds) {

            Optional<T> element = getById(id);
            if(element.isPresent()) {

                subList.add(element.get());
            }
        }
        return subList;
    }

    /**
     * gibt eine Liste mit den Elementen der IDs zurück (gefiltert nach einem Typ)
     *
     * @param identifiers Liste mit IDs
     * @param type Typ filtern
     * @return Liste mit Elementen
     */
    public Set<T> getSublist(Set<String> identifiers, Class type) {

        Set<T> subList = new HashSet<T>();
        for(String id : identifiers) {

            Optional<T> element = getById(id);
            if(element.isPresent() && type.isInstance(element)) {

                subList.add(element.get());
            }
        }
        return subList;
    }

    /**
     * gibt das Lockobjekt zurück
     *
     * @return Lockobjekt
     */
    @Override
    public ReentrantReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
