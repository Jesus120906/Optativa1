package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.PokemonEntity;

public interface Pokemonrepository extends JpaRepository<PokemonEntity, Long>{

}
