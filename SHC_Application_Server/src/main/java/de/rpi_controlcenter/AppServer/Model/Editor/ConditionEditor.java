package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.Condition;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Verwaltung der Bedingungen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ConditionEditor extends AbstractDatabaseEditor<Condition> {

    private static final String DATABASE_KEY = "shc:automation:conditions";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        JsonParser jp = new JsonParser();
        List<Condition> data = getData();
        List<String> conditionList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String conditionJson : conditionList) {

            JsonObject jo = jp.parse(conditionJson).getAsJsonObject();
            Class clazz = getTypeClass(jo.get("type").getAsString());
            Condition conditionValue = (Condition) gson.fromJson(conditionJson, clazz);
            data.add(conditionValue);
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
        List<Condition> data = getData();

        pipeline.del(DATABASE_KEY);
        for(Condition conditionValue : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(conditionValue));
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
        switch (type) {

            case CONDITION_CALENDAR_WEEK:

                return CalendarWeekCondition.class;
            case CONDITION_DATE:

                return DateCondition.class;
            case CONDITION_DAY_OF_WEEK:

                return DayOfWeekCondition.class;
            case CONDITION_FILE:

                return FileCondition.class;
            case CONDITION_HOLIDAYS:

                return HolidaysCondition.class;
            case CONDITION_HUMIDITY:

                return HumidityCondition.class;
            case CONDITION_INPUT:

                return InputCondition.class;
            case CONDITION_LIGHT_INTENSITY:

                return LightIntensityCondition.class;
            case CONDITION_MOISTURE:

                return MoistureCondition.class;
            case CONDITION_NOBODY_AT_HOME:

                return NobodyAtHomeCondition.class;
            case CONDITION_DAY:

                return DayCondition.class;
            case CONDITION_NIGHT:

                return NightCondition.class;
            case CONDITION_SWITCHABLE_STATE:

                return SwitchableStateCondition.class;
            case CONDITION_TEMPERATURE:

                return TemperatureCondition.class;
            case CONDITION_TIME:

                return TimeCondition.class;
            case CONDITION_USER_AT_HOME:

                return UserAtHomeCondition.class;
            default:
                throw new IllegalStateException("Ungültiger Typ");
        }
    }
}
