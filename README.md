# AppPI
  AppPI ermöglicht die Interaktion mit verschiedenen APIs über HTTP-Anfragen. Die App bietet sowohl vorgefertigte Konstrukte um mit vorgeschlagenen APIs zu interagierten, als auch eine benutzerdefinierte Möglichkeit, eine API Abfrage zu starten. API keys werden auf den Gerät gespeichert, wobei Einsicht auf diese Authentifizierung, z.B. den Fingerabdruck, benötigt.	

## Gruppenmitglieder
  - Martin Jäger
  - Johannes Planegger

## Topics
  - Sensor: Fingerabdruck zum Abrufen gespeicherter API keys
  - Data centricity: Server mit Daten
  - Special gestures: Swipe für Preset-swap

## APIs
https://free-apis.github.io/#/browse

## Funktionsweise - App:
  - Die App bietet zwei vorgefertigte API Interface Presets (YT und Currency Exchange) zur Interaktion mit der jeweiligen API.
  - Es ist durch Gestures möglich zwischen einzelnen Seiten hin- und her-, zu wischen.
  - Das "App - Overlay" besteht aus:
    - einem Info "I" button, der den Nutzer auf eine About-Page umleitet.
    - einem Create "+" button, der den Nutzer auf den Fragment Builder umleitet.
  - Es ist möglich erstellte Fragmente mit dem "x" button (rechts oben) zu löschen und aus der DB zu entfernen.

## Funktionsweise - API Calls:
  - Die erstellten API Interfaces ermöglichen die Übergabe von queries bestehend aus (key, value) tupel.
  - Das explizite Setzen der Header ist nicht möglich (könnte in der Zukunft ausgebaut werden).
  - Es ist möglich den return der API calls auf ein gewisses Attribut zu beschränken (im Builder).
  - Die erstellten API Interface Presets und die API Keys werden in einer SQLite DB gespeichert und bleiben somit nach App Neustart erhalten.
  - Es ist möglich API Interface Presets zu erstellen die keinen Key benötigen.
  - Die Keys werden standardmäßig als query übergeben.
