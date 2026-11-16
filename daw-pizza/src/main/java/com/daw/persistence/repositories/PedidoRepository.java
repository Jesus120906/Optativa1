package com.daw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

}
