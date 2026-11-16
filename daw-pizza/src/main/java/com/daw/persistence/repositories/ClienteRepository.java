package com.daw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

}
