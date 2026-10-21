package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.PokemonEntity;
import com.daw.persistence.entities.enums.Tipo;

public interface PokemonRepository extends JpaRepository<PokemonEntity, Long> {

	List<PokemonEntity> findByNumeroPokedex(int numeroPokedex);

	List<PokemonEntity> findByFechaCapturaBetween(LocalDate inicio, LocalDate fin);

	List<PokemonEntity> findByTipo1OrTipo2(Tipo tipo1, Tipo tipo2);
}
