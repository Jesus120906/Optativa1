package com.daw.services.exceptions;

public class PokemonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PokemonNotFoundException(long id) {
		super("No existe ningún pokemon con id " + id);
	}
}
