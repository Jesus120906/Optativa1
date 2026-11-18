package com.daw.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El nombre del cliente no puede estar vacío")
	@Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
	@Column(nullable = false, length = 30)
	private String nombre;

	@NotBlank(message = "La dirección no puede estar vacía")
	@Size(max = 100, message = "La dirección no puede superar los 100 caracteres")
	@Column(nullable = false, length = 100)
	private String direccion;

	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "El formato del email no es válido")
	@Size(max = 50, message = "El email no puede superar los 50 caracteres")
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@NotBlank(message = "El teléfono no puede estar vacío")
	@Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
	@Column(nullable = false, length = 20, unique = true)
	private String telefono;

}