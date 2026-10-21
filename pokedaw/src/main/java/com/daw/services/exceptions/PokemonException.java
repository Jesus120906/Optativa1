package com.daw.services.exceptions;

public class PokemonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PokemonException(String message) {
		super(message);
	}
}
