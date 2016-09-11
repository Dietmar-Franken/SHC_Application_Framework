package de.rpi_controlcenter.Util.Json.Serializer;

import com.google.gson.*;
import de.rpi_controlcenter.Util.DateTime.DatabaseDateTimeUtil;

import java.lang.reflect.Type;
import java.time.LocalTime;

/**
 * Serialisiert Datumsobjekte
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 * @license http://opensource.org/licenses/gpl-license.php GNU Public License
 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        String ldtStr = jsonElement.getAsString();
        return DatabaseDateTimeUtil.parseTimeFromDatabase(ldtStr);
    }

    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {

        String ldtStr = DatabaseDateTimeUtil.getDatabaseTimeStr(localTime);
        return new JsonPrimitive(ldtStr);
    }
}
