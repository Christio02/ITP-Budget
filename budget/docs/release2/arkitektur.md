# Arkitektur
Vi har implementert MVC arkitektur (full tre-lags arkitektur) i prosjektet. Der kjernelogikken
og UI-et har blitt avskilt. Vi har også valgt å implementere en egen modul for filskriving, slik at
det er persistering av data mellom kjøring av appen, ved og ha et egnet bibliotek for dette (Jackson).
Vi har laget et plantUML diagram for å vise arkitekturen til prosjektet vårt:

![plantUML-diagram](plantUML-diagram.svg)