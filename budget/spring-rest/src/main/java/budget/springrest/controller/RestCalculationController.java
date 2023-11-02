package budget.springrest.controller;

import budget.core.Calculation;
import budget.springrest.repository.CalculationRepositorylList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/budget")
public class RestCalculationController {

    // dependency injection we need to use that
    /*
    The framework is going to handle it
     */
    private final CalculationRepositorylList repository;


    public RestCalculationController(CalculationRepositorylList repository) { // uses constructor injection
        this.repository = repository;
    }

    // GET http://localhost:8080/budget (returns all budgets)
    @GetMapping
    public ArrayList<Calculation> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/name(Mitt budsjett)
    @GetMapping("/{name}")
    public Calculation findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    // POST http://localhost:8080/budget (do not need /create)
    // need to return correct response code
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Calculation create(@RequestBody Calculation calculation) {
        return this.repository.create(calculation);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{name}")
    public void update(@RequestBody Calculation calc) {
        this.repository.update(calc);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        this.repository.delete(name);
    }



}
