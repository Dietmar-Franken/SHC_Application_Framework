package de.rpi_controlcenter.Util.DateTime;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.SortedSet;

/**
 * Berechnet die nächste Ausführungszeit
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class CornjobCalculator {

    public static LocalDateTime calculateNextRunTime(SortedSet<Integer> minute, SortedSet<Integer> hour, SortedSet<Integer> day, SortedSet<Integer> weekday, SortedSet<Integer> month) {

        //Daten Initalisieren
        LocalDateTime now = LocalDateTime.now();
        int nextMinute, currentMinute = nextMinute = now.getMinute();
        int nextHour, currentHour = nextHour = now.getHour();
        int nextWeekday, currentWeekday = nextWeekday = now.getDayOfWeek().getValue();
        int nextDay, currentDay = nextDay = now.getDayOfMonth();
        int nextMonth, currentMonth = nextMonth = now.getMonthValue();
        int nextYear = now.getYear();

        //Prüfen welche Daten immer ausgeführt werden
        boolean everyMinute = minute.size() == 0;
        boolean everyHour = hour.size() == 0;
        boolean everyDay = day.size() == 0;
        boolean everyWeekday = weekday.size() == 0;
        boolean everyMonth = month.size() == 0;

        //Rest Variablen deklarieren
        int restHour = 0;
        int restDay = 0;
        int restMonth = 0;
        int restYear = 0;

        //Minute errechnen
        if(everyMinute) {

            //jede Minute ausführen
            nextMinute++;

            if(nextMinute > 59) {

                nextMinute = 0;
                restHour = 1;
            }
        } else {

            //nächste Minute aus der Liste ermitteln
            if(nextRunBit(minute, currentMinute) != -1) {

                nextMinute = nextRunBit(minute, currentMinute);
            } else {

                nextMinute = firstRunTime(minute);
                restHour = 1;
            }

            if(nextMinute <= currentMinute) {

                restHour = 1;
            }
        }

        //Stunde berechnen
        if(restHour > 0 || !runTimeExists(hour, currentHour)) {

            if(everyHour) {

                //jede Stunde ausführen
                nextHour++;

                if(nextHour > 23) {

                    nextHour = 0;
                    restDay = 1;
                }
            } else {

                //nächste Stunde aus der Liste ermitteln
                if(nextRunBit(hour, currentHour) != -1) {

                    nextHour = nextRunBit(hour, currentHour);
                } else {

                    nextHour = firstRunTime(hour);
                    restDay = 1;
                }

                if(nextHour <= currentHour) {

                    restDay = 1;
                }
                nextMinute = firstRunTime(minute);
            }
        }

        //Tag berechnen
        if(restDay > 0 || (weekday.size() == 0 && !runTimeExists(day, currentDay)) || (day.size() == 0 && !runTimeExists(weekday, currentWeekday))) {

            if(everyWeekday) {

                //Tag berechnen
                if(everyDay) {

                    //jeder Tag
                    nextDay++;

                    if(nextDay > YearMonth.now().lengthOfMonth()) {

                        nextDay = 1;
                        restMonth = 1;
                    }
                } else {

                    //nächster Tag aus der Liste ermitteln
                    if(nextRunBit(day, currentDay) != -1) {

                        nextDay = nextRunBit(day, currentDay);
                    } else {

                        nextDay = firstRunTime(day);
                        restMonth = 1;
                    }

                    if(nextDay <= currentDay) {

                        restMonth = 1;
                    }
                }
            } else {

                //Wochentag berechnen
                if(nextRunBit(weekday, currentWeekday) != -1) {

                    nextWeekday = nextRunBit(weekday, currentWeekday);
                } else {

                    nextWeekday = firstRunTime(weekday);
                }

                nextDay = currentDay + (nextWeekday - currentWeekday);
                if(nextDay <= currentDay) {

                    nextDay += 7;
                }
                if(nextDay > YearMonth.now().lengthOfMonth()) {

                    restMonth = 1;
                }
            }
            nextMinute = firstRunTime(minute);
            nextHour = firstRunTime(hour);
            if(nextDay == currentDay && nextHour < currentHour) {

                restMonth = 1;
            }
        }

        //Monat berechnen
        if(restMonth > 0 || !runTimeExists(month, currentMonth)) {

            if(everyMonth) {

                //jeden Monat
                nextMonth++;

                if(nextMonth > 12) {

                    nextMonth = 1;
                    restYear = 1;
                }
            } else {

                if(nextRunBit(month, currentMonth) != -1) {

                    nextMonth = nextRunBit(month, currentMonth);
                } else {

                    nextMonth = firstRunTime(month);
                    restYear = 1;
                }

                if(nextMonth < currentMonth) {

                    restYear = 1;
                }
            }

            nextMinute = firstRunTime(minute);
            nextHour = firstRunTime(hour);

            if(everyWeekday) {

                nextDay = firstRunTime(day);
                if(nextDay == 0) nextDay = 1;
            } else {

                nextWeekday = firstRunTime(weekday);
                int newWeekday = LocalDateTime.of(nextYear, nextMonth, 1, nextHour, nextMinute, 0).getDayOfWeek().getValue();
                nextDay = 1 + (nextWeekday - newWeekday);
                if(nextWeekday < newWeekday) {

                    nextDay += 7;
                }
            }

            if(nextMonth == currentMonth && nextDay == currentDay && nextHour < currentHour) {

                restYear = 1;
            }
        }

        //Jahr berechnen
        if(restYear > 0) {

            nextYear++;
            nextMinute = firstRunTime(minute);
            nextHour = firstRunTime(hour);
            nextMonth = firstRunTime(month);
            if(nextMonth == 0) nextMonth = 1;
            if(everyWeekday) {

                nextDay = firstRunTime(day);
                if(nextDay == 0) nextDay = 1;
            } else {

                nextWeekday = firstRunTime(weekday);
                int newWeekday = LocalDateTime.of(nextYear, nextMonth, 1, nextHour, nextMinute, 0).getDayOfWeek().getValue();
                nextDay = 1 + (nextWeekday - newWeekday);
                if(nextWeekday < newWeekday) {

                    nextDay += 7;
                }
            }
        }

        return LocalDateTime.of(nextYear, nextMonth, nextDay, nextHour, nextMinute);
    }

    private static int nextRunBit(SortedSet<Integer> data, int current) {

        //Randbetrachtung jeder Ausführungszeitpunkt
        if(data.size() == 0) {

            return current;
        }

        //nächste EInheit ermitteln
        for(int next : data) {

            if(next > current) {

                return next;
            }
        }
        return -1;
    }

    private static int firstRunTime(SortedSet<Integer> data) {

        //Randbetrachtung jeder Ausführungszeitpunkt
        if(data.size() == 0) {

            return 0;
        }

        //nächste EInheit ermitteln
        return data.first();
    }

    private static boolean runTimeExists(SortedSet<Integer> data, int bit) {

        //Randbetrachtung jeder Ausführungszeitpunkt
        if(data.size() == 0) {

            return true;
        }

        //nächste Einheit ermitteln
        return data.contains(bit);
    }
}
