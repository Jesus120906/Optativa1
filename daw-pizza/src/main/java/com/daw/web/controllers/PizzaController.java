package com.daw.web.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.PizzaEntity;
import com.daw.services.PizzaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pizzas")
@RequiredArgsConstructor
public class PizzaController {

	private final PizzaService pizzaService;

	@GetMapping
	public ResponseEntity<List<PizzaEntity>> list() {
		return ResponseEntity.ok(this.pizzaService.findAll());
	}

	@GetMapping("/{idPizza}")
	public ResponseEntity<?> findById(@PathVariable long idPizza) {
		return ResponseEntity.ok(this.pizzaService.findById(idPizza));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody PizzaEntity pizza) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.pizzaService.create(pizza));
	}

	@PutMapping("/{idPizza}")
	public ResponseEntity<?> update(@PathVariable long idPizza, @Valid @RequestBody PizzaEntity pizza) {
		return ResponseEntity.ok(this.pizzaService.update(idPizza, pizza));
	}

	@DeleteMapping("/{idPizza}")
	public ResponseEntity<?> delete(@PathVariable long idPizza) {
		this.pizzaService.deleteById(idPizza);
		return ResponseEntity.ok().build();
	}
}
