package de.rpi_controlcenter.Util.Json.Serializer;

import com.google.gson.*;
import de.rpi_controlcenter.Util.DateTime.DatabaseDateTimeUtil;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Serialisiert Datumsobjekte
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 * @license http://opensource.org/licenses/gpl-license.php GNU Public License
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        String ldtStr = jsonElement.getAsString();
        return DatabaseDateTimeUtil.parseDateTimeFromDatabase(ldtStr);
    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        String ldtStr = DatabaseDateTimeUtil.getDatabaseDateTimeStr(localDateTime);
        return new JsonPrimitive(ldtStr);
    }
}
