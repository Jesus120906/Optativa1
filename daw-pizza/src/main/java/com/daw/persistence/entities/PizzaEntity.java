package com.daw.persistence.entities;

import jakarta.persistence.Column;
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

	@Column(nullable = false, length = 30)
	private String nombre;
	
	@Column(nullable = false, length = 150)
	private String descripcion;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double precio;
    
    @Column(nullable = false)
    private Boolean vegetariana;
    
    @Column(nullable = false)
    private Boolean vegana;
    
    @Column(nullable = false)
    private Boolean disponible;
    
	public PizzaEntity() {
		super();
		this.disponible = true;
		this.vegana = false;
		this.vegetariana = false;
	}
    
    

}