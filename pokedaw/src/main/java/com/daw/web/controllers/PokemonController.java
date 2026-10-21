package com.daw.web.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.PokemonEntity;
import com.daw.persistence.entities.enums.Tipo;
import com.daw.services.PokemonService;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

	private final PokemonService pokemonService;

	public PokemonController(PokemonService pokemonService) {
		this.pokemonService = pokemonService;
	}

	@GetMapping
	public ResponseEntity<List<PokemonEntity>> list() {
		return ResponseEntity.ok(this.pokemonService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PokemonEntity> findById(@PathVariable long id) {
		return ResponseEntity.ok(this.pokemonService.findById(id));
	}

	@PostMapping
	public ResponseEntity<PokemonEntity> create(@RequestBody PokemonEntity pokemon) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.pokemonService.create(pokemon));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PokemonEntity> update(@PathVariable long id, @RequestBody PokemonEntity pokemon) {
		return ResponseEntity.ok(this.pokemonService.update(id, pokemon));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		this.pokemonService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// GET /pokemon/pokedex?numero=25
	@GetMapping("/pokedex")
	public ResponseEntity<List<PokemonEntity>> findByPokedex(@RequestParam int numero) {
		return ResponseEntity.ok(this.pokemonService.findByNumeroPokedex(numero));
	}

	// GET /pokemon/fecha?inicio=2024-01-01&fin=2024-12-31
	@GetMapping("/fecha")
	public ResponseEntity<List<PokemonEntity>> findByFecha(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
		return ResponseEntity.ok(this.pokemonService.findByFechaCaptura(inicio, fin));
	}

	// GET /pokemon/tipo?tipo=FUEGO  (busca en tipo1 o tipo2)
	@GetMapping("/tipo")
	public ResponseEntity<List<PokemonEntity>> findByTipo(@RequestParam Tipo tipo) {
		return ResponseEntity.ok(this.pokemonService.findByTipo(tipo));
	}
}
