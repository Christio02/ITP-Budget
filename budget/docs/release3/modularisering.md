## Modularisering
Som beskrevet i root folderen har vi i denne testen modularisert prosjektet vårt i tre moduler. Core, ui og utility.
Core inneholder all logikken til appen, ui inneholder all grafisk brukergrensesnitt og utility inneholder hjelpeklasser som brukes i både core og ui.

Modulariseringen er også lagt inn i en mappe "budget", som skal være en "parent" mappe for alle modulene våre.
Untatt springrest-modulen, som trenger SpingBoot selv som parent for at vi skulle få den til å funke.

---