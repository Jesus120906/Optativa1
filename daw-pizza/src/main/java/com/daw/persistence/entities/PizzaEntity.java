package com.daw.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	@NotBlank(message = "El nombre de la pizza no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
	@Column(nullable = false, length = 30)
	private String nombre;
	
	@Size(max = 150, message = "La descripción no puede superar los 150 caracteres")
	@Column(length = 150)
	private String descripcion;

	@NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "3.90", inclusive = true, message = "El precio debe ser superior a 3.90€")
    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double precio;
    
    @Column(nullable = false)
    private Boolean vegetariana;
    
    @Column(nullable = false)
    private Boolean vegana;

    @Column(nullable = false)
    private Boolean bordes;
    
    @Column(nullable = false)
    private Boolean disponible;
    
	public PizzaEntity() {
		super();
		this.disponible = true;
		this.vegana = false;
		this.vegetariana = false;
	}
    
    

}