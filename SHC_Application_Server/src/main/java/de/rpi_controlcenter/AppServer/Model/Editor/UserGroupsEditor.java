package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.User.UserGroup;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Verwaltung der Benutzergruppen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class UserGroupsEditor extends AbstractDatabaseEditor<UserGroup> {

    private static final String DATABASE_KEY = "shc:user:userGroups";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        List<UserGroup> data = getData();
        List<String> userGroupList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String userGroupJson : userGroupList) {

            data.add(gson.fromJson(userGroupJson, UserGroup.class));
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
        List<UserGroup> data = getData();

        pipeline.del(DATABASE_KEY);
        for(UserGroup userGroup : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(userGroup));
        }
        pipeline.sync();
    }
}
