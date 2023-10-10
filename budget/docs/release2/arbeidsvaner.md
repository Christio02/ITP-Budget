# Arbeidsvaner

## Git
Under denne releasen har vi fokusert mer på arbeidsvanene våre. 
Vi fikk satt opp et sprintboard på GitLaben, og vi har brukt 
dette aktivt under denne sprinten for å holde styr på hvilke 
arbeidsoppgaver vi har å gjøre.

Vi har også laget en mal for hvordan vi skal skrive commits og branches, slik at de kan knyttes
opp mot issuesene de skal løses, og at det er mer oversiktlig hva som er gjort.  

Å endre på commit konvensjonen vår har også gjort at vi er istand til å oversiktlige
headere, men beskrivende bodies. Som gir bedre kontekst for hva som er gjort i commiten.  
Det har også gjort at vi kan kreditere hverandre dersom vi har parprogrammert på ulike issues. 

## Fil lagring
Måten vi lagrer data i appen er med at brukeren velger å trykke lagre, 
så vi bruker mer en dokumentmetafor for lagringen vår. Det skjer derfor ingen lagring
automatisk. 

Christopher:
- Oppdaterte struktur til å ha tester for hver modul
- La til TestFX til dependencies
- Opprettet tester for UI.

Mats:
- Opprettet JSON implementasjon til filskriving for appen
- Delt opp prosjektet i flere moduler
- Opprettet pom.xml filer til de ulike modulene

Eirik:
- Dokumentert arkitektur med et plantUML diagram
- Fikset Eclipse Che slik at det fungerer med JavaFX
-
Fredrik:
- Oppdatert dokumentasjon
- Opprettet tester etter å ha sjekket testdekningsgrad (Jacoco)
-

## Modularisering
Vi har valgt å dele opp prosjektet i flere egnede moduler med avhengigheter mellom dem.

---
## Arkitektur
Vi har implementert MVC arkitektur (full tre-lags arkitektur) i prosjektet. Der kjernelogikken
og UI-et har blitt avskilt. Vi har også valgt å implementere en egen modul for filskriving, slik at
det er persistering av data mellom kjøring av appen, ved og ha et egnet bibliotek for dette (Jackson).

## Kodekvalitet
Vi har valgt å bruke Checkstyle for å sikre at koden vår følger en standard. Vi har valgt å bruke
CheckStyle - IDEA for dette. Vi har også valgt å bruke Jacoco for å sjekke testdekningsgraden vår.
Slik at vi har en god dekningsgrad for alle lagene i prosjektet.

## Dokumentasjon
Vi har oppdatert dokumentasjonen vår med nytt design, og nytt plantUML diagram. Vi har også oppdatert
dokumentasjon for hver release.

## Arbeidsvaner
Vi har valgt å bruke issues og milestones for å organisere arbeidet vårt. Disse diskuterer vi i gruppen
under det faste møtet i uken. Der bestemmer vi oss for hva som skal først prioriteres, og hvem som skal
jobbe med hva. Vi har også valgt å bruke branches for å jobbe med hver vår del av prosjektet. For at branchene
skal være oversiktelige har vi valgt å navngi dem etter hvilken issue de jobber med
eks: "reference-category/description-in-kebab-case". Vi har også valgt å bruke merge requests for å kvalitetssikre
at koden som blir lagt til i master er godkjent av en annen i gruppen. Videre har vi valgt å bruke git tags for å
markere hver release. Vi har også jobbet i par, dette har vi gjort ved å bruke verktøyet "code with me" i IntelliJ.
Dette har vært en god måte å jobbe på, da vi har kunnet hjelpe hverandre med å løse problemer, og lært av hverandre.
Dette har blitt dokumentert i commit meldingene våre med "Co-authored-by:

