package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.persistence.entities.PokemonEntity;
import com.daw.persistence.entities.enums.Capturado;
import com.daw.persistence.entities.enums.Tipo;
import com.daw.persistence.repositories.PokemonRepository;
import com.daw.services.exceptions.PokemonException;
import com.daw.services.exceptions.PokemonNotFoundException;

@Service
public class PokemonService {

	private final PokemonRepository pokemonRepository;

	public PokemonService(PokemonRepository pokemonRepository) {
		this.pokemonRepository = pokemonRepository;
	}

	public List<PokemonEntity> findAll() {
		return this.pokemonRepository.findAll();
	}

	public PokemonEntity findById(long id) {
		return this.pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFoundException(id));
	}

	public PokemonEntity create(PokemonEntity pokemon) {
		this.validarDatos(pokemon);
		this.normalizarTipos(pokemon);

		pokemon.setId(0);

		if (pokemon.getCapturado() == null) {
			pokemon.setCapturado(Capturado.POKEBALL);
		}
		if (pokemon.getFechaCaptura() == null) {
			pokemon.setFechaCaptura(LocalDate.now());
		}
		return this.pokemonRepository.save(pokemon);
	}

	public PokemonEntity update(long id, PokemonEntity pokemon) {
		PokemonEntity existente = this.findById(id);

		if (pokemon.getFechaCaptura() != null && !pokemon.getFechaCaptura().equals(existente.getFechaCaptura())) {
			throw new PokemonException("No se permite modificar la fecha de captura");
		}

		this.validarDatos(pokemon);
		this.normalizarTipos(pokemon);

		existente.setNumeroPokedex(pokemon.getNumeroPokedex());
		existente.setNombre(pokemon.getNombre());
		existente.setTipo1(pokemon.getTipo1());
		existente.setTipo2(pokemon.getTipo2());
		if (pokemon.getCapturado() != null) {
			existente.setCapturado(pokemon.getCapturado());
		}
		return this.pokemonRepository.save(existente);
	}

	public void delete(long id) {
		PokemonEntity existente = this.findById(id);
		this.pokemonRepository.delete(existente);
	}

	public List<PokemonEntity> findByNumeroPokedex(int numeroPokedex) {
		return this.pokemonRepository.findByNumeroPokedex(numeroPokedex);
	}

	public List<PokemonEntity> findByFechaCaptura(LocalDate inicio, LocalDate fin) {
		if (inicio == null || fin == null) {
			throw new PokemonException("Debes indicar la fecha de inicio y la de fin");
		}
		if (inicio.isAfter(fin)) {
			throw new PokemonException("La fecha de inicio no puede ser posterior a la de fin");
		}
		return this.pokemonRepository.findByFechaCapturaBetween(inicio, fin);
	}

	public List<PokemonEntity> findByTipo(Tipo tipo) {
		return this.pokemonRepository.findByTipo1OrTipo2(tipo, tipo);
	}


	private void validarDatos(PokemonEntity pokemon) {
		if (pokemon.getNumeroPokedex() < 1) {
			throw new PokemonException("El número de la pokédex es obligatorio y debe ser mayor que 0");
		}
		if (pokemon.getNombre() == null || pokemon.getNombre().isBlank()) {
			throw new PokemonException("El nombre es obligatorio");
		}
		if (pokemon.getNombre().length() > 30) {
			throw new PokemonException("El nombre no puede superar los 30 caracteres");
		}
	}

	
	private void normalizarTipos(PokemonEntity pokemon) {
		Tipo tipo1 = pokemon.getTipo1();
		Tipo tipo2 = pokemon.getTipo2();

		if (tipo1 == null) {
			throw new PokemonException("El tipo1 es obligatorio");
		}
		if (tipo1 == Tipo.AGUA) {
			throw new PokemonException("El tipo1 no puede ser NINGUNO");
		}
		if (tipo2 == null || tipo2 == tipo1) {
			pokemon.setTipo2(Tipo.AGUA);
		}
	}
}
