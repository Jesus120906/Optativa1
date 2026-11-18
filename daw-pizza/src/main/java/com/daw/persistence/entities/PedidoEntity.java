package com.daw.persistence.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.daw.persistence.entities.enums.MetodoPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
    
    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, columnDefinition = "DECIMAL(6,2)")
    private Double total;
	
	@Enumerated(EnumType.STRING)
	private MetodoPedido metodo;
	
	@Size(max = 200, message = "Las notas no pueden superar los 200 caracteres")
    @Column(length = 200)
    private String notas;
    
    @OneToMany(mappedBy = "pedido",
    		cascade = CascadeType.ALL,
    		orphanRemoval = true)
    private List<PizzaPedidoEntity> pizzas;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

	public PedidoEntity() {
		super();
		this.pizzas = new ArrayList<PizzaPedidoEntity>();
		this.total = 0.0;
	}
	
    public void addPizza(PizzaPedidoEntity pizza) {
    	pizzas.add(pizza);
        pizza.setPedido(this);
    }

    public void removePizza(PizzaPedidoEntity pizza) {
    	pizzas.remove(pizza);
        pizza.setPedido(null);
    }
    
	
    

}