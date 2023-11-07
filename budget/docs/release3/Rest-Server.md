# Rest server implementation

- Rest serveren er implementert med Spring Boot og Spring Web i modulen som heter `spring-rest`
- Serveren er konfigurert til å kjøre på port 8080
- Default addressen til serveren er http://localhost:8080/budget her vil en liste med alle objektene vises
- Men brukeren kan sende en GET request til et spesifikt objekt ved å sende en request til http://localhost:8080/budget/{name}


### Rest serveren er implementert med følgende klasser:
- `RestCalculationController.java` - Controller klassen som håndterer alle requestene som kommer inn til serveren
- `CalculationRepositoryList.java` - En klasse som inneholder en liste med alle objektene som skal vises på serveren og definerer alle endpoints.
I tillegg så lagrer den til fil etter hver request
- `DataSingleton.java` - En klasse som sender HTTP requests til serveren. Denne ligger tett opp med Javafx controllerne som kaller denne klassens metoder når brukeren interagerer med GUIet.

## Endepunkter:

Pakke: budget.springrest.controller

Klasse: RestCalculationController

Denne klassen håndterer alle forespørsler til REST-serveren. Den bruker avhengighetsinjeksjon til å injisere et CalculationRepositoryList-objekt, som brukes til å samhandle med dataene.


- GET /budget: Returnerer en liste over alle budsjetter.
- GET /budget/{name}: Returnerer budsjettet med det spesifiserte navnet.
- POST /budget: Oppretter et nytt budsjett.
- PUT /budget/{name}: Oppdaterer budsjettet med det spesifiserte navnet.
- DELETE /budget/{name}: Sletter budsjettet med det spesifiserte navnet.
Eksempel på forespørsler:

### HENT alle budsjetter
GET http://localhost:8080/budget

### HENT budsjett med navn "Mitt budsjett"
GET http://localhost:8080/budget/Mitt-budsjett

### OPPRETTE et nytt budsjett
POST http://localhost:8080/budget
```
{
"categoriesList" : [ {
"categoryName" : "Food",
"amount" : 3000,
"budgetHistory" : [ 3000 ]
}, {
"categoryName" : "Transportation",
"amount" : 0,
"budgetHistory" : [ ]
}, {
"categoryName" : "Entertainment",
"amount" : 0,
"budgetHistory" : [ ]
}, {
"categoryName" : "Clothing",
"amount" : 0,
"budgetHistory" : [ ]
}, {
"categoryName" : "Other",
"amount" : 0,
"budgetHistory" : [ ]
} ],
"name" : "Mitt-budsjett",
"totalSum" : 3000
}
```

### OPPDATERE budsjettet med navn "Mitt budsjett"
PUT http://localhost:8080/budget/Mitt-budsjett
```
{
"categoriesList" : [ {
"categoryName" : "Food",
"amount" : 3000,
"budgetHistory" : [ 3000 ]
}, {
"categoryName" : "Transportation",
"amount" : 1000,
"budgetHistory" : [ 1000 ]
}, {
"categoryName" : "Entertainment",
"amount" : 0,
"budgetHistory" : [ ]
}, {
"categoryName" : "Clothing",
"amount" : 0,
"budgetHistory" : [ ]
}, {
"categoryName" : "Other",
"amount" : 0,
"budgetHistory" : [ ]
} ],
"name" : "Mitt-budsjett",
"totalSum" : 4000
}
```

### SLETT budsjettet med navn "Mitt budsjett"
DELETE http://localhost:8080/budget/Mitt-budsjett


## Responskoder:

Serveren returnerer følgende responskoder for forskjellige operasjoner:

- GET: 200 OK hvis forespørselen var vellykket.
- POST: 201 Created hvis budsjettet ble opprettet.
- PUT: 204 No Content hvis budsjettet ble oppdatert.
- DELETE: 204 No Content hvis budsjettet ble slettet.
- FEIL: 400 Bad Request hvis forespørselen var ugyldig.
- FEIL: 404 Not Found hvis budsjettet ikke ble funnet.
- FEIL: 500 Internal Server Error hvis det oppstod en uventet feil.


Eksempel på responser:

### HENT alle budsjetter
HTTP/1.1 200 OK
Content-Type: application/json

```
[ {
  "categoriesList" : [ {
    "categoryName" : "Food",
    "amount" : 3000,
    "budgetHistory" : [ 3000 ]
  }, {
    "categoryName" : "Transportation",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Entertainment",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Clothing",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Other",
    "amount" : 0,
    "budgetHistory" : [ ]
  } ],
  "name" : "Test",
  "totalSum" : 3000
}, {
  "categoriesList" : [ {
    "categoryName" : "Food",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Transportation",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Entertainment",
    "amount" : 23000,
    "budgetHistory" : [ 23000 ]
  }, {
    "categoryName" : "Clothing",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Other",
    "amount" : 5000,
    "budgetHistory" : [ 2000, 3000 ]
  } ],
  "name" : "hello",
  "totalSum" : 28000
} ]
```


### OPPRETTE et nytt budsjett
HTTP/1.1 201 Created
Content-Type: application/json

```
{
  "categoriesList" : [ {
    "categoryName" : "Food",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Transportation",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Entertainment",
    "amount" : 23000,
    "budgetHistory" : [ 23000 ]
  }, {
    "categoryName" : "Clothing",
    "amount" : 0,
    "budgetHistory" : [ ]
  }, {
    "categoryName" : "Other",
    "amount" : 5000,
    "budgetHistory" : [ 2000, 3000 ]
  } ],
  "name" : "hello",
  "totalSum" : 28000
}
```

