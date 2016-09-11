package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.SwitchServer.SwitchServer;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Schaltserver verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SwitchServerEditor extends AbstractDatabaseEditor<SwitchServer> {

    private static final String DATABASE_KEY = "shc:switchServer:switchServers";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        List<SwitchServer> data = getData();
        List<String> switchServerList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String switschServerJson : switchServerList) {

            data.add(gson.fromJson(switschServerJson, SwitchServer.class));
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
        List<SwitchServer> data = getData();

        pipeline.del(DATABASE_KEY);
        for(SwitchServer switchServer : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(switchServer));
        }
        pipeline.sync();
    }
}
