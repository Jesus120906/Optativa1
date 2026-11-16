package com.daw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.PizzaEntity;

public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {

}
