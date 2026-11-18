package com.daw.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pizza_pedido")
@Getter
@Setter
@NoArgsConstructor
public class PizzaPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.5", inclusive = true, message = "La cantidad mínima debe ser 0.5")
    @Column(nullable = false, columnDefinition = "DECIMAL(2,1)")
    private Double cantidad;
    
    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double precio;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pizza_id", nullable = false)
    private PizzaEntity pizza;

}