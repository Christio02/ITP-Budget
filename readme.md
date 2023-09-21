# Group gr2340 repository 
 
Vi har ulike mapper som viser til forskjellige deler av prosjektet. I docsen har vi dokumentasjon og beskrivelse av releasene. 

- I src folderen har vi to folders, "main" og "test". "Main" er hoveddelen av prosjektet og det er her
koden til applikasjonen befinner seg. 
- Vi har igjen delt main inn i resources og java, hvor resources viser til fxml filen som definerer hvordan applikasjonen ser ut. 
- I java-folderen har vi delt inn i core og ui, hvor "core" viser til backend-delen, mens "ui" er frontend delen som starter applikasjonen og kobler "core" med fxml'en (i "kontrolleren").
- Vi har også en utility package som inneholder en klasse som har kode til å endre scene (for å opprettholde code-reuse), i tillegg til en fil- og leseklasse
- Link til Eclipse che: https://che.stud.ntnu.no/christgh-stud-ntnu-no/gr2340/3100/

### Java- og Maven versjon
Vi bruker java 17.0.5 og maven 3.11.0 for å kjøre prosjektet.'

### Hvordan kjøre prosjektet
Man må åpne "gr2340" directory og så kjøre "mvn clean install" i terminalen. Dette fordi filskrivingen 
bare fungerer når man er inne i riktig directory"
Deretter kan man kjøre applikasjonen som vanlig

#### Link til readme.md om appen: [readme.md](src/main/java/readme.md)

