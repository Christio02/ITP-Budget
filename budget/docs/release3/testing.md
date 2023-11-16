# Testing


For denne releasen har vi introdusert spotbugs, checkstyle og jacoco for å
sjekke testgraden vår, og vi ligger godt over 80% testdekning for modulene, som vi synes er passende dekningsgrad
da vi mener ikke alt (som f.eks alle konstruktørene) er like relevant å teste.

Testene våre for utility blir utført i ui mappen, da det er her logikken
utføres.

Vi har en noe konsoll printing for testene som kommer ut underveis i kjøringen. 
Som er brukt i debugging, som vi ikke har fått fjernet. 

Vi har samlet test resultatene for core, ui og utility i en egen mappe "test-results", slik at det er enkelt å finne. 