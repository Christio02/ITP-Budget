# Group gr2340 repository 
 
Prosjektet er modularisert i de ulike logikkene våre, core, ui og utility. I docs mappen har vi dokumentasjon og beskrivelse for hver release. 

- Core modulen inneholder all logikken til appen, og testfilene til denne logikken.
- ui modulen inneholder all grafisk brukergrensesnitt, og testfilene til denne.
- utility modulen inneholder hjelpeklasser som brukes i både core og ui, og springrest, som filskriving og fillagring, og testfilene til denne.
  -  I tillegg benyttes en spesialisert Objectmapper
- springrest modulen inneholder en egen server som lagrer dataen til en webserver, og benytter utility modulens filskrivings/lesings metoder

**Link til Eclipse che:** https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2340/gr2340?new

### Hvordan kjøre prosjektet
Man må åpne "budget" directory og så kjøre "mvn clean install" i terminalen. Dette fordi filskrivingen
bare fungerer når man er inne i riktig directory"
Du må derretter cd inn i _ui_ mappen, for så å kjøre "mvn javafx:run" for å kjøre applikasjonen.



### Versjoner
Vi bruker java 17.0.5 og maven 3.11.0 for å kjøre prosjektet.  
Vi bruker også versjon 4.7.3.6 av spotbugs, versjon0.8.7 av Jacoco og versjon 10.3.4 av checkstyle.


#### Link til readme.md om appen: [readme.md](budget/aboutApp.md)

