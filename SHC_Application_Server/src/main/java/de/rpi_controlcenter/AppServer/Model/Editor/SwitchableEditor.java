package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.Switchable;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * schaltbare Elemente Editor
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SwitchableEditor extends AbstractDatabaseEditor<Switchable> {

    private static final String DATABASE_KEY = "shc:automation:switchables";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        JsonParser jp = new JsonParser();
        List<Switchable> data = getData();
        List<String> switchableList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String switchableJson : switchableList) {

            JsonObject jo = jp.parse(switchableJson).getAsJsonObject();
            Class clazz = getTypeClass(jo.get("type").getAsString());
            Switchable sensorValue = (Switchable) gson.fromJson(switchableJson, clazz);
            data.add(sensorValue);
        }
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    @Override
    public void dump() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Pipeline pipeline = db.pipelined();
        Gson gson = AppServer.getInstance().getGson();
        List<Switchable> data = getData();

        pipeline.del(DATABASE_KEY);
        for(Switchable sensorValue : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(sensorValue));
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

        AutomationElement.Type type = AutomationElement.Type.valueOf(typeStr);
        switch(type) {

            case SWITCHABLE_AVM_SOCKET:

                return AvmSocket.class;
            case SWITCHABLE_EDIMAX_SOCKET:

                return EdimaxSocket.class;
            case SWITCHABLE_FRITZ_BOX_REBOOT_RECONNECT:

                return FritzBoxRebootReconnect.class;
            case SWITCHABLE_FRITZ_BOX_WLAN:

                return FritzBoxWirelessLan.class;
            case SWITCHABLE_OUTPUT:

                return Output.class;
            case SWITCHABLE_RADIO_SOCKET:

                return RadioSocket.class;
            case SWITCHABLE_REBOOT_SHUTDOWN:

                return RebootShutdown.class;
            case SWITCHABLE_SCRIPT_DOUBLE:

                return ScriptDouble.class;
            case SWITCHABLE_SCRIPT_SINGLE:

                return ScriptSingle.class;
            case SWITCHABLE_VIRTUAL_SOCKET:

                return VirtualSocket.class;
            case SWITCHABLE_WAKE_ON_LAN:

                return WakeOnLan.class;
            default:
                throw new IllegalStateException("Ungültiger Typ");
        }
    }
}
