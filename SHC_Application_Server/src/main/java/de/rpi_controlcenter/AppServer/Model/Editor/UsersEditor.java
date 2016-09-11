package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Verwaltung der Benutzer
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class UsersEditor extends AbstractDatabaseEditor<User> {

    private static final String DATABASE_KEY = "shc:user:users";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        List<User> data = getData();
        List<String> userList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String userJson : userList) {

            data.add(gson.fromJson(userJson, User.class));
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
        List<User> data = getData();

        pipeline.del(DATABASE_KEY);
        for(User user : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(user));
        }
        pipeline.sync();
    }
}
