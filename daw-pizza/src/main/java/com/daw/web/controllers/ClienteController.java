package com.daw.web.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.ClienteEntity;
import com.daw.services.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<ClienteEntity>> list() {
		return ResponseEntity.ok(this.clienteService.findAll());
	}

	@GetMapping("/{idCliente}")
	public ResponseEntity<ClienteEntity> findById(@PathVariable long idCliente) {
		return ResponseEntity.ok(this.clienteService.findById(idCliente));
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ClienteEntity cliente) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteService.create(cliente));
	}

	@PutMapping("/{idCliente}")
	public ResponseEntity<?> update(@PathVariable int idCliente, @Valid @RequestBody ClienteEntity cliente) {
		return ResponseEntity.ok(this.clienteService.update(idCliente, cliente));
	}

	@DeleteMapping("/{idCliente}")
	public ResponseEntity<?> delete(@PathVariable int idCliente) {
		this.clienteService.deleteById(idCliente);
		return ResponseEntity.ok().build();
	}
}