package com.daw.persistence.entities;

import java.time.LocalDateTime;

import com.daw.persistence.entities.enums.MetodoPedido;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "pedido")
@Getter
@Setter
public class PedidoEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha;
    private Double total;
	
	@Enumerated(EnumType.STRING)
	private MetodoPedido metodo;
    private String notas;

}
