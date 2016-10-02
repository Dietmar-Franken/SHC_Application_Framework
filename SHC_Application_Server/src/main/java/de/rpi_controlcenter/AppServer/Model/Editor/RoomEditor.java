package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Room.Room;
import de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements.ButtonElement;
import de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements.SensorElement;
import de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements.SeparatorElement;
import de.rpi_controlcenter.AppServer.Model.Data.Room.RoomElements.VirtualSensorElement;
import de.rpi_controlcenter.AppServer.Model.Data.Room.ViewElement;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.*;

/**
 * Raumverwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class RoomEditor extends AbstractDatabaseEditor<Room> {

    private static final String DATABASE_KEY = "shc:room:rooms";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        JsonParser jp = new JsonParser();
        List<Room> data = getData();
        List<String> roomsList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String roomJson : roomsList) {

            JsonObject jo = jp.parse(roomJson).getAsJsonObject();
            Class clazz = getTypeClass(jo.get("type").getAsString());
            Room operationValue = (Room) gson.fromJson(roomJson, clazz);
            data.add(operationValue);
        }
    }

    /**
     * gibt eine sortierte Liste aller Räume zurück
     *
     * @return sortierte Liste aller Räume
     */
    public SortedSet<Room> listRoomsSorted() {

        SortedSet<Room> rooms = new TreeSet<>((entry1, entry2) -> {

            if(entry1.getOrderId() > entry2.getOrderId()){
                return 1;
            } else if(entry1.getOrderId() < entry2.getOrderId()) {
                return -1;
            }
            return 0;
        });
        List<Room> data = getData();
        rooms.addAll(data);
        return rooms;
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    @Override
    public void dump() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Pipeline pipeline = db.pipelined();
        Gson gson = AppServer.getInstance().getGson();
        List<Room> data = getData();

        pipeline.del(DATABASE_KEY);
        for(Room room : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(room));
        }
        pipeline.sync();
    }

    /**
     * gibt das Class Objekt zum Typ zurück
     *
     * @param typeStr Typ String
     * @return Class Objekt
     */
    public Class getTypeClass(String typeStr) {

        ViewElement.Type type = ViewElement.Type.valueOf(typeStr);
        switch (type) {

            case ROOM:

                return Room.class;
            case BUTTON:

                return ButtonElement.class;
            case SENSOR:

                return SensorElement.class;
            case SEPARATOR:

                return SeparatorElement.class;
            case VIRTUAL_SENSOR:

                return VirtualSensorElement.class;
            default:
                throw new IllegalStateException("Ungültiger Typ");
        }
    }
}
