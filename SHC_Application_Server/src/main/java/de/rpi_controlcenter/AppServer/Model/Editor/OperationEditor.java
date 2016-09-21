package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.Operation;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Verwaltung der Automatisierungsoperationen
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class OperationEditor extends AbstractDatabaseEditor<Operation> {

    private static final String DATABASE_KEY = "shc:automation:operations";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        JsonParser jp = new JsonParser();
        List<Operation> data = getData();
        List<String> operationList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String operationJson : operationList) {

            JsonObject jo = jp.parse(operationJson).getAsJsonObject();
            Class clazz = getTypeClass(jo.get("type").getAsString());
            Operation operationValue = (Operation) gson.fromJson(operationJson, clazz);
            data.add(operationValue);
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
        List<Operation> data = getData();

        pipeline.del(DATABASE_KEY);
        for(Operation operationValue : data) {

            pipeline.lpush(DATABASE_KEY, gson.toJson(operationValue));
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

            case OPERATION_FILE_CREATE:

                return FileCreateOperation.class;
            case OPERATION_FILE_DELETE:

                return FileDeleteOperation.class;
            case OPERATION_HUMIDITY_CLIMB_OVER:

                return HumidityClimbOverOperation.class;
            case OPERATION_HUMIDITY_FALLS_BELOW:

                return HumidityFallsBelowOperation.class;
            case OPERATION_INPUT_HIGH:

                return InputHighOperation.class;
            case OPERATION_INPUT_LOW:

                return InputLowOperation.class;
            case OPERATION_LIGHT_INTENSITY_CLIMB_OVER:

                return LightIntensityClimbOverOperation.class;
            case OPERATION_LIGHT_INTENSITY_FALLS_BELOW:

                return LightIntensityFallsBelowOperation.class;
            case OPERATION_MOISTURE_CLIMB_OVER:

                return MoistureClimbOverOperation.class;
            case OPERATION_MOISTURE_FALLS_BELOW:

                return MoistureFallsBelowOperation.class;
            case OPERATION_SIMPLE_TIME:

                return SimpleTimeOperation.class;
            case OPERATION_SUNRISE:

                return SunriseOperation.class;
            case OPERATION_SUNSET:

                return SunsetOperation.class;
            case OPERATION_TEMPERATURE_CLIMB_OVER:

                return TemperatureClimbOverOperation.class;
            case OPERATION_TEMPERATURE_FALLS_BELOW:

                return TemperatureFallsBelowOperation.class;
            case OPERATION_TIME:

                return TimeOperation.class;
            case OPERATION_USER_COME_HOME:

                return UserComeHomeOperation.class;
            case OPERATION_USER_LEAVE_HOME:

                return UserLeaveHomeOperation.class;
            default:
                throw new IllegalStateException("Ungültiger Typ");
        }
    }
}
