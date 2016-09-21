package de.rpi_controlcenter.AppServer.Controller.Automation.Operation;

import de.rpi_controlcenter.AppServer.Controller.AppServer;
import de.rpi_controlcenter.AppServer.Controller.Automation.Condition.ConditionController;
import de.rpi_controlcenter.AppServer.Controller.Util.DateTime.SunriseSunsetUtil;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.Condition;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.SensorValue;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.*;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Operations.Interface.Operation;
import de.rpi_controlcenter.Util.DateTime.CornjobCalculator;
import de.rpi_controlcenter.Util.DateTime.DateTimeUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Prüft ob die Automatisierungsoperationen zur Ausführung bereit sind
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class OperationController {

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(FileCreateOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        Path file = Paths.get(operation.getFilePath());
        if(Files.exists(file) && !operation.isState()) {

            //neuer Status
            operation.setState(true);
        } else {

            //keine änderungen, Status speichern
            operation.setState(Files.exists(file));
            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(FileDeleteOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        Path file = Paths.get(operation.getFilePath());
        if(Files.exists(file) && operation.isState()) {

            //neuer Status
            operation.setState(false);
        } else {

            //keine änderungen, Status speichern
            operation.setState(Files.exists(file));
            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(HumidityClimbOverOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), HumidityValue.class);
        for(SensorValue sensorValue : sensorValues) {

            HumidityValue humidityValue = (HumidityValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(humidityValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(humidityValue.getId()) < operation.getLimit() && humidityValue.getHumidity() >= operation.getLimit()) {

                    succsess = true;
                }
                state.put(humidityValue.getId(), humidityValue.getHumidity());
            } else {

                //Sensor unbekannt
                state.put(humidityValue.getId(), humidityValue.getHumidity());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(HumidityFallsBelowOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), HumidityValue.class);
        for(SensorValue sensorValue : sensorValues) {

            HumidityValue humidityValue = (HumidityValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(humidityValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(humidityValue.getId()) > operation.getLimit() && humidityValue.getHumidity() <= operation.getLimit()) {

                    succsess = true;
                }
                state.put(humidityValue.getId(), humidityValue.getHumidity());
            } else {

                //Sensor unbekannt
                state.put(humidityValue.getId(), humidityValue.getHumidity());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(InputHighOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), InputValue.class);
        for(SensorValue sensorValue : sensorValues) {

            InputValue inputValue = (InputValue) sensorValue;
            Map<String, Boolean> state = operation.getStateMap();
            if(state.containsKey(inputValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(inputValue.getId()) != inputValue.getState() && inputValue.getState()) {

                    succsess = true;
                }
                state.put(inputValue.getId(), inputValue.getState());
            } else {

                //Sensor unbekannt
                state.put(inputValue.getId(), inputValue.getState());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(InputLowOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), InputValue.class);
        for(SensorValue sensorValue : sensorValues) {

            InputValue inputValue = (InputValue) sensorValue;
            Map<String, Boolean> state = operation.getStateMap();
            if(state.containsKey(inputValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(inputValue.getId()) != inputValue.getState() && !inputValue.getState()) {

                    succsess = true;
                }
                state.put(inputValue.getId(), inputValue.getState());
            } else {

                //Sensor unbekannt
                state.put(inputValue.getId(), inputValue.getState());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(LightIntensityClimbOverOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), LightIntensityValue.class);
        for(SensorValue sensorValue : sensorValues) {

            LightIntensityValue lightIntensityValueValue = (LightIntensityValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(lightIntensityValueValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(lightIntensityValueValue.getId()) > operation.getLimit() && lightIntensityValueValue.getLightIntensity() <= operation.getLimit()) {

                    succsess = true;
                }
                state.put(lightIntensityValueValue.getId(), lightIntensityValueValue.getLightIntensity());
            } else {

                //Sensor unbekannt
                state.put(lightIntensityValueValue.getId(), lightIntensityValueValue.getLightIntensity());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(LightIntensityFallsBelowOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), LightIntensityValue.class);
        for(SensorValue sensorValue : sensorValues) {

            LightIntensityValue lightIntensityValueValue = (LightIntensityValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(lightIntensityValueValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(lightIntensityValueValue.getId()) < operation.getLimit() && lightIntensityValueValue.getLightIntensity() >= operation.getLimit()) {

                    succsess = true;
                }
                state.put(lightIntensityValueValue.getId(), lightIntensityValueValue.getLightIntensity());
            } else {

                //Sensor unbekannt
                state.put(lightIntensityValueValue.getId(), lightIntensityValueValue.getLightIntensity());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(MoistureClimbOverOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), MoistureValue.class);
        for(SensorValue sensorValue : sensorValues) {

            MoistureValue moistureValue = (MoistureValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(moistureValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(moistureValue.getId()) > operation.getLimit() && moistureValue.getMoisture() <= operation.getLimit()) {

                    succsess = true;
                }
                state.put(moistureValue.getId(), moistureValue.getMoisture());
            } else {

                //Sensor unbekannt
                state.put(moistureValue.getId(), moistureValue.getMoisture());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(MoistureFallsBelowOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), MoistureValue.class);
        for(SensorValue sensorValue : sensorValues) {

            MoistureValue moistureValue = (MoistureValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(moistureValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(moistureValue.getId()) < operation.getLimit() && moistureValue.getMoisture() >= operation.getLimit()) {

                    succsess = true;
                }
                state.put(moistureValue.getId(), moistureValue.getMoisture());
            } else {

                //Sensor unbekannt
                state.put(moistureValue.getId(), moistureValue.getMoisture());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(SunriseOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        //Ereignis prüfen
        boolean succsess = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime sunriseTime = SunriseSunsetUtil.getTodaySunriseTime();
        LocalTime now = LocalTime.now();
        if(now.format(formatter).equalsIgnoreCase(sunriseTime.format(formatter))) {

            //aktuelle Zeit ist die Zeit des Sonnenaufganges
            succsess = true;
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(SunsetOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        //Ereignis prüfen
        boolean succsess = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime sunsetTime = SunriseSunsetUtil.getTodaySunsetTime();
        LocalTime now = LocalTime.now();
        if(now.format(formatter).equalsIgnoreCase(sunsetTime.format(formatter))) {

            //aktuelle Zeit ist die Zeit des Sonnenaufganges
            succsess = true;
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(TemperatureClimbOverOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), TemperatureValue.class);
        for(SensorValue sensorValue : sensorValues) {

            TemperatureValue temperatureValue = (TemperatureValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(temperatureValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(temperatureValue.getId()) > operation.getLimit() && temperatureValue.getTemperatureWithOffset() <= operation.getLimit()) {

                    succsess = true;
                }
                state.put(temperatureValue.getId(), temperatureValue.getTemperatureWithOffset());
            } else {

                //Sensor unbekannt
                state.put(temperatureValue.getId(), temperatureValue.getTemperatureWithOffset());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(TemperatureFallsBelowOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getSensors(), TemperatureValue.class);
        for(SensorValue sensorValue : sensorValues) {

            TemperatureValue temperatureValue = (TemperatureValue) sensorValue;
            Map<String, Double> state = operation.getStateMap();
            if(state.containsKey(temperatureValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(temperatureValue.getId()) < operation.getLimit() && temperatureValue.getTemperatureWithOffset() >= operation.getLimit()) {

                    succsess = true;
                }
                state.put(temperatureValue.getId(), temperatureValue.getTemperatureWithOffset());
            } else {

                //Sensor unbekannt
                state.put(temperatureValue.getId(), temperatureValue.getTemperatureWithOffset());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(UserComeHomeOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getUsers(), UserAtHomeValue.class);
        for(SensorValue sensorValue : sensorValues) {

            UserAtHomeValue userAtHomeValue = (UserAtHomeValue) sensorValue;
            Map<String, Boolean> state = operation.getStateMap();
            if(state.containsKey(userAtHomeValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(userAtHomeValue.getId()) != userAtHomeValue.isAtHome() && userAtHomeValue.isAtHome()) {

                    succsess = true;
                }
                state.put(userAtHomeValue.getId(), userAtHomeValue.isAtHome());
            } else {

                //Sensor unbekannt
                state.put(userAtHomeValue.getId(), userAtHomeValue.isAtHome());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(UserLeaveHomeOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        Set<SensorValue> sensorValues = AppServer.getInstance().getSensorValues().getSublist(operation.getUsers(), UserAtHomeValue.class);
        for(SensorValue sensorValue : sensorValues) {

            UserAtHomeValue userAtHomeValue = (UserAtHomeValue) sensorValue;
            Map<String, Boolean> state = operation.getStateMap();
            if(state.containsKey(userAtHomeValue.getId())) {

                //Sensorwert ist bekannt
                if(state.get(userAtHomeValue.getId()) != userAtHomeValue.isAtHome() && !userAtHomeValue.isAtHome()) {

                    succsess = true;
                }
                state.put(userAtHomeValue.getId(), userAtHomeValue.isAtHome());
            } else {

                //Sensor unbekannt
                state.put(userAtHomeValue.getId(), userAtHomeValue.isAtHome());
            }
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(TimeOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;
        LocalDateTime nextExecutionTime = operation.getNextExecutionTime();
        if(DateTimeUtil.isPast(nextExecutionTime) || nextExecutionTime.isEqual(LocalDateTime.now())) {

            succsess = true;
            //nächste Ausführungszeit berechnen
            operation.setNextExecutionTime(
                    CornjobCalculator.calculateNextRunTime(
                            operation.getMinute(),
                            operation.getHour(),
                            operation.getDay(),
                            operation.getWeekday(),
                            operation.getMonth()
                    )
            );
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft ob das Ereignis eingetreten ist
     *
     * @param operation Operation
     * @return Ergebnis
     */
    public static boolean isSatisfies(SimpleTimeOperation operation) {

        //Vorbedingungen prüfen
        if(!preconditions(operation)) {

            return false;
        }

        boolean succsess = false;

        //Eriegnis prüfen
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime nextExecutionTime = operation.getNextExecutionTime();
        if(DateTimeUtil.isPast(nextExecutionTime) || nextExecutionTime.isEqual(LocalDateTime.now())) {

            succsess = true;

            //Wochentage vorbereiten
            SortedSet<Integer> weekdays = new TreeSet<>();
            if(operation.getWeekdays() == SimpleTimeOperation.Weekdays.WEEKDAY) {

                weekdays.addAll(Arrays.asList(1, 2, 3, 4, 5));
            } else if(operation.getWeekdays() == SimpleTimeOperation.Weekdays.WEEKEND) {

                weekdays.addAll(Arrays.asList(6, 7));
            } else {

                weekdays.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
            }

            //nächste Ausführungszeit berechnen
            operation.setNextExecutionTime(
                    CornjobCalculator.calculateNextRunTime(
                            new TreeSet<>(Arrays.asList(operation.getMinute())),
                            new TreeSet<>(Arrays.asList(operation.getHour())),
                            new TreeSet<>(),
                            weekdays,
                            new TreeSet<>()
                    )
            );
        }

        //Erfolg prüfen
        if(!succsess) {

            return false;
        }

        //Bedingungen prüfen
        if(!postconditions(operation)) {

            return false;
        }

        operation.setLastExecuteTime(LocalDateTime.now());
        operation.setBlockingTimeEnd(LocalDateTime.now().plusSeconds(operation.getBlockingTime()));
        return true;
    }

    /**
     * prüft die allgemeinen Bedingungen
     *
     * @param operation Operation
     * @return Ergebnis
     */
    private static boolean preconditions(Operation operation) {

        //prüfen ob deaktiviert
        if (operation.isDisabled()) {

            return false;
        }

        //prüfen ob sperrzeit aktiv
        if(operation.getBlockingTimeEnd() != null && DateTimeUtil.isFuture(operation.getBlockingTimeEnd())) {

            return false;
        }

        //Blockierungszeit zurücksetzen
        if(operation.getBlockingTimeEnd() != null && DateTimeUtil.isPast(operation.getBlockingTimeEnd())) {

            operation.setBlockingTimeEnd(null);
        }

        return true;
    }

    /**
     * prüft die Bedingungen
     *
     * @param operation Operation
     * @return Ergebnis
     */
    private static boolean postconditions(Operation operation) {

        //Bedingungen prüfen
        Set<String> conditions = operation.getConditions();
        for (String conditionId : conditions) {

            Optional<Condition> conditionOptional = AppServer.getInstance().getConditions().getById(conditionId);
            if(conditionOptional.isPresent()) {

                Condition condition = conditionOptional.get();
                if(!ConditionController.isSatisfies(condition)) {

                    return false;
                }
            }
        }
        return true;
    }
}
