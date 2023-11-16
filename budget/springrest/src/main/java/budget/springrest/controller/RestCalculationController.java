package budget.springrest.controller;

import budget.core.Calculation;
import budget.springrest.repository.CalculationRepositoryList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/budget")
public class RestCalculationController {

    // dependency injection we need to use that
    /*
    The framework is going to handle it
     */
    private final CalculationRepositoryList repository;


    public RestCalculationController(CalculationRepositoryList repository) { // uses constructor injection
        this.repository = new CalculationRepositoryList(repository);
    }

    // GET http://localhost:8080/budget (returns all budgets)
    @GetMapping
    public ArrayList<Calculation> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/name(Mitt budsjett)
    @GetMapping("/{name}")
    public ResponseEntity<Calculation> findByName(@PathVariable String name) {
        Calculation calcFromServer = repository.findByName(name);
        if (calcFromServer != null) {
            return ResponseEntity.ok(calcFromServer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> clearBudgets() {
        repository.clearBudgets();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Calculation> create(@RequestBody Calculation calculation) {
        Calculation createdCalculation = repository.create(calculation);
        return new ResponseEntity<>(createdCalculation, HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestBody Calculation calculation) {
        repository.update(calculation);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        repository.delete(name);
        return ResponseEntity.noContent().build();
    }

}
