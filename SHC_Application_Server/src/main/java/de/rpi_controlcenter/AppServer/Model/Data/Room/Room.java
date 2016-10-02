package de.rpi_controlcenter.AppServer.Model.Data.Room;

import java.util.*;

/**
 * Raum
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class Room extends AbstractViewElement {

    /**
     * liste aller Raumelemente
     */
    private Map<Integer, RoomElement> roomElements = new HashMap<>();

    /**
     * Sortierungs ID
     */
    private int orderId = 1;

    private Type type = Type.ROOM;

    /**
     * gibt die Sortierung zurück
     *
     * @return Sortierung
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * setzt die Sortierung
     *
     * @param orderId Sortierung
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * fügt dem Raum ein neues Element hinzu
     *
     * @param roomElement Raum Element
     * @return true bei Erfolg
     */
    public boolean addRoomElement(RoomElement roomElement) {

        if(!roomElements.containsValue(roomElement)) {

            //höchste Sortingungs ID ermitteln
            int max = 0;
            for (Integer orderId : roomElements.keySet()) {

                if(max < orderId) {

                    max = orderId;
                }
            }

            int orderId = max++;
            addRoomElement(roomElement, orderId);
            return true;
        }
        return false;
    }

    /**
     * fügt dem Raum ein neues Element hinzu
     *
     * @param roomElement Raum Element
     * @param orderId         Sortierungs ID
     * @return true bei Erfolg
     */
    public boolean addRoomElement(RoomElement roomElement, int orderId) {

        if(!roomElements.containsValue(roomElement)) {

            roomElements.put(orderId, roomElement);
            return true;
        }
        return false;
    }

    /**
     * entfernt ein Raum Element
     *
     * @param roomElement Raum Element
     * @return true bei Erfolg
     */
    public boolean removeRoomElement(RoomElement roomElement) {

        if(roomElements.containsValue(roomElement)) {

            for(Integer orderId : roomElements.keySet()) {

                ViewElement knownViewElement = roomElements.get(orderId);
                if(roomElement.getId().equals(knownViewElement.getId())) {

                    roomElements.remove(orderId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * setzt für ein Raum Element eine neue Sortierungs ID
     *
     * @param roomElement Raum Element
     * @param orderId         Sortierungs ID
     * @return true bei Erfolg
     */
    public boolean updateOrderId(RoomElement roomElement, int orderId) {

        if(roomElements.containsValue(roomElement)) {

            for(Integer knownOrderId : roomElements.keySet()) {

                ViewElement knownRoomElement = roomElements.get(knownOrderId);
                if(roomElement.getId().equals(knownRoomElement.getId())) {

                    roomElements.remove(orderId);
                    roomElements.put(orderId, roomElement);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * prüft ob ein Raum Element im Raum vorhanden ist
     *
     * @param roomElement Raum Element
     * @return true wenn vorhanden
     */
    public boolean containsRoomElement(RoomElement roomElement) {

        return roomElements.containsValue(roomElement);
    }

    /**
     * gibt eine Liste mit allen Raum Elementen und deren Sortierungen zurück
     *
     * @return Raum Elemente
     */
    public Map<Integer, RoomElement> getRoomElements() {
        return Collections.unmodifiableMap(roomElements);
    }

    /**
     * gibt eine nach Sortierungs ID sortierte Liste der Raum Elemente und deren Sortierungen zurück
     *
     * @return Raum Elemente
     */
    public Map<Integer, RoomElement> getRoomElementsOrderedByOrderId() {

        SortedSet<Map.Entry<Integer, RoomElement>> sortedSet = new TreeSet<>((entry1, entry2) -> {

            if(entry1.getKey() < entry2.getKey()) {
                return 1;
            } else if(entry1.getKey() > entry2.getKey()) {
                return  -1;
            }
            return 0;
        });
        sortedSet.addAll(roomElements.entrySet());

        Map<Integer, RoomElement> output = new HashMap<>();
        for(Map.Entry<Integer, RoomElement> entry : sortedSet) {

            output.put(entry.getKey(), entry.getValue());
        }
        return output;
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
