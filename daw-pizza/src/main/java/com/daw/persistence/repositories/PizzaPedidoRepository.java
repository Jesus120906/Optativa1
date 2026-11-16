package com.daw.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.PizzaPedidoEntity;

public interface PizzaPedidoRepository extends JpaRepository<PizzaPedidoEntity, Long> {
	
    List<PizzaPedidoEntity> findByPedidoId(Long pedidoId);
    
    Optional<PizzaPedidoEntity> findByIdAndPedidoId(Long id, Long pedidoId);

}
