package com.daw.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pizza")
@Getter
@Setter
public class PizzaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
    private Double precio;
    private Boolean vegetariana;
    private Boolean vegana;
    private Boolean disponible;
    
	public PizzaEntity() {
		super();
		this.disponible = true;
		this.vegana = false;
		this.vegetariana = false;
	}
    
    

}
