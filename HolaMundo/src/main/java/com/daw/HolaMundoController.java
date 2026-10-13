package com.daw;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/holamundo")
public class HolaMundoController {
	
		@GetMapping
	public String HolaMundo() {
		
		return "Hola";
		
	}
		
		/*
		 * El RequestParam se utiliza cuando tengamos cualquier método en el que tengamos 
		 * que mandar parametros al servidor 
		 * 
		 * Ejemplo:
		 * 
		 * 		localhost:8080/books/buscarPorTitulo?titulo=....
		 */
		@GetMapping("saludo")
		public String HolaMundo(@RequestParam(required = false) String nombre) {
			
			return "Hola " + nombre;
			
		}
		
		/*
		 * El PathVariable se utiliza exclusivamente para IDs.
		 */
		
		@GetMapping("/books/{id}")
		public String libros(@PathVariable long id) {
			
			return "Aquí tienes el libre con id " +id;
			
		}
	

}
