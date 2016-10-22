package de.rpi_controlcenter.AppServer.Model.Serializer;

import com.google.gson.*;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.User.User;
import de.rpi_controlcenter.AppServer.Model.Data.User.UserGroup;
import de.rpi_controlcenter.AppServer.Model.Editor.UserGroupsEditor;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

/**
 * description
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class UserSerializer implements JsonSerializer<User>, JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jo = jsonElement.getAsJsonObject();

        String id = jo.get("id").getAsString();
        String name = jo.get("name").getAsString();
        boolean originator = jo.get("originator").getAsBoolean();
        User user = new User(id, name, originator);
        user.setPassword(jo.get("passwordHash").getAsString());
        user.setLocale(new Locale(jo.get("locale").getAsString()));

        JsonArray array = jo.get("userGroups").getAsJsonArray();
        UserGroupsEditor userGroupsEditor = AppServer.getInstance().getUserGroups();
        Lock lock = userGroupsEditor.readLock();
        lock.lock();
        try {

            for(int i = 0; i < array.size(); i++) {

                String userGroupHash = array.get(i).getAsString();
                Optional<UserGroup> userGroup = userGroupsEditor.getById(userGroupHash);
                if(userGroup.isPresent()) {

                    user.getUserGroups().add(userGroup.get());
                }
            }
        } finally {

            lock.unlock();
        }

        return user;
    }

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(user.getId()));
        result.add("name", new JsonPrimitive(user.getName()));
        result.add("passwordHash", new JsonPrimitive(user.getPassword()));
        result.add("originator", new JsonPrimitive(user.isOriginator()));
        result.add("locale", new JsonPrimitive(user.getLocale().toString()));

        JsonArray array = new JsonArray();
        for(UserGroup userGroup : user.getUserGroups()) {

            array.add(new JsonPrimitive(userGroup.getId()));
        }
        result.add("userGroups", array);

        return result;
    }
}
