package de.rpi_controlcenter.AppServer.Model.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Virtual.*;
import de.rpi_controlcenter.AppServer.Model.Database.AbstractDatabaseEditor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Sensorwerte Verwaltung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class SensorValueEditor extends AbstractDatabaseEditor<SensorValue> {

    private static final String DATABASE_KEY = "shc:automation:sensors";

    /**
     * lädt die Elemente aus der Datenbank
     */
    @Override
    public void load() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Gson gson = AppServer.getInstance().getGson();
        JsonParser jp = new JsonParser();
        List<SensorValue> data = getData();
        List<String> sensorValueList = db.lrange(DATABASE_KEY, 0, -1);

        data.clear();
        for(String sensorValueJson : sensorValueList) {

            JsonObject jo = jp.parse(sensorValueJson).getAsJsonObject();
            Class clazz = getTypeClass(jo.get("type").getAsString());
            SensorValue sensorValue = (SensorValue) gson.fromJson(sensorValueJson, clazz);
            data.add(sensorValue);
        }
    }

    /**
     * sucht nach eine Element mit dem übergebenen Hash
     *
     * @param id Hash
     * @return Element
     */
    public Optional<SensorValue> getByIdentifier(String id) {

        List<SensorValue> data = getData();
        for(SensorValue element : data) {

            if(element.getIdentifier().equalsIgnoreCase(id)) {

                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    /**
     * gibt eine Subliste mit allen Objekten des Typs zurück
     *
     * @param clazz Typ
     * @return Liste mit den Sensorwerten
     */
    public List<SensorValue> filterByType(Class<? extends SensorValue> clazz) {

        List<SensorValue> data = getData();
        List<SensorValue> filtered = new ArrayList<>();
        for(SensorValue element : data) {

            if(clazz.isInstance(element)) {

                filtered.add(element);
            }
        }
        return filtered;
    }

    /**
     * speichert die Elemente in der Datenbank
     */
    @Override
    public void dump() {

        Jedis db = AppServer.getInstance().getDatabaseConnection();
        Pipeline pipeline = db.pipelined();
        Gson gson = AppServer.getInstance().getGson();
        List<SensorValue> data = getData();

        pipeline.del(DATABASE_KEY);
        for(SensorValue sensorValue : data) {

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

            case SENSORVALUE_ACTUAL_POWER:

                return ActualPowerValue.class;
            case SENSORVALUE_AIR_PRESSURE:

                return AirPressureValue.class;
            case SENSORVALUE_ALTITUDE:

                return AltitudeValue.class;
            case SENSORVALUE_BATTERY_LEVEL:

                return BatteryLevelValue.class;
            case SENSORVALUE_CURRENT:

                return CurrentValue.class;
            case SENSORVALUE_DISTANCE:

                return DistanceValue.class;
            case SENSORVALUE_DURATION:

                return DurationValue.class;
            case SENSORVALUE_ENERGY:

                return EnergyValue.class;
            case SENSORVALUE_GAS_AMOUNT:

                return GasAmountValue.class;
            case CONDITION_HUMIDITY:

                return HumidityValue.class;
            case SENSORVALUE_INPUT:

                return InputValue.class;
            case SENSORVALUE_LIGHT_INTENSITY:

                return LightIntensityValue.class;
            case SENSORVALUE_LIVE_BIT:

                return LiveBitValue.class;
            case SENSORVALUE_MOISTURE:

                return MoistureValue.class;
            case SENSORVALUE_STRING:

                return StringValue.class;
            case SENSORVALUE_TEMPERATURE:

                return TemperatureValue.class;
            case SENSORVALUE_USER_AT_HOME:

                return UserAtHomeValue.class;
            case SENSORVALUE_VOLTAGE:

                return VoltageValue.class;
            case SENSORVALUE_WATER_AMOUNT:

                return WaterAmountValue.class;
            case VIRTUALSENSORVALUE_ACTUAL_POWER:

                return VirtualActualPowerValue.class;
            case VIRTUALSENSORVALUE_ENERGY:

                return VirtualEnergyValue.class;
            case VIRTUALSENSORVALUE_GAS_AMOUNT:

                return VirtualGasAmountValue.class;
            case VIRTUALSENSORVALUE_LIGHT_INTENSITY:

                return VirtualLightIntensityValue.class;
            case VIRTUALSENSORVALUE_TEMPERATURE:

                return VirtualTemperatureValue.class;
            case VIRTUALSENSORVALUE_WATER_AMOUNT:

                return VirtualWaterAmountValue.class;
            default:
                throw new IllegalStateException("Ungültiger Typ");
        }
    }
}
