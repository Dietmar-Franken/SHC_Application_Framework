## Fehlercodes

### Allgemeine Fehler

| Code  | Beschreibung                                                   |
|:-----:|:---------------------------------------------------------------|
| 10000 | Element ist Null, sollte aber nicht
| 10001 | Ungültige ID (Element) 
| 10002 | Ungültiger Name (Element) 
| 10003 | Ungültige IP Adresse (Allgemein) 
| 10004 | Ungültiger Port (Allgemein) 
| 10005 | Ungültiger Timeout (Allgemein) 
| 10006 | Ungültiger Hash (Allgemein) 
| 10007 | Ungültige länge der Zeichenkette (Allgemein) 
| 10008 | Ungültige Identifizierung (Allgemein) 
| 10009 | Ungültige Datenfeld größe (Allgemein) 

### Benutzer Fehler

| Code  | Beschreibung                                                   |
|:-----:|:---------------------------------------------------------------|
| 10100 | Ungültiges Passwort (User) 
| 10101 | Es wurde keine Benutzergruppe zugeordnet (User) 
| 10102 | kein Token (Authentication)
| 10103 | ungültiger Token (Authentication)
| 10104 | kein Benutzer angemeldet (Authentication)
| 10105 | der Benutzer besitzt nicht die nötigen Rechte (Authentication)

### Automatisierungs Fehler

| Code  | Beschreibung                                                   |
|:-----:|:---------------------------------------------------------------|
| 10200 | es muss mindestens ein Feiertag eingetragen werden (Condition) 
| 10201 | es muss mindestens ein Sensor überwacht werden werden (Condition) 
| 10202 | es muss mindestens ein schaltbares Element überwacht werden (Condition) 
| 10203 | Ungültiges Limit (Condition, AutomationOperation) 
| 10204 | es muss mindestens ein Sensorwert vorhanden sein (VirtualSensorValue) 
| 10205 | Ungültiger Sensorwert (SensorValue) 
| 10206 | Ungültiger Offset (SensorValue) 
| 10207 | es muss mindestens ein schaltbares Element vorhanden sein (AutomationOperation) 
| 10208 | es muss mindestens ein Sensorwert vorhanden sein (AutomationOperation) 
| 10209 | Ungültige Zeitangabe (AutomationOperation) 


###Raum Fehler

| Code  | Beschreibung                                                   |
|:-----:|:---------------------------------------------------------------|
| 10300 | Ungültiger Intervall (Button) 


###REST Fehler

| Code  | Beschreibung                                                   |
|:-----:|:---------------------------------------------------------------|
| 10500 | ungültiges JSON
| 10501 | Daten ungültig
| 10502 | der Datensatz exitiert bereits
| 10503 | der Datensatz konnte nicht gefunden werden
| 10504 | der Name ist bereits vergeben
| 10505 | ein Systemelement kann nicht gelöscht werden