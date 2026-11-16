package com.daw.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.daw.persistence.entities.ClienteEntity;
import com.daw.persistence.repositories.ClienteRepository;
import com.daw.services.exceptions.BusinessRuleException;
import com.daw.services.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	
	public List<ClienteEntity> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public ClienteEntity findById(long idCliente) {
		if (!this.clienteRepository.existsById(idCliente)) {
			throw new ResourceNotFoundException("Cliente no encontrado con ID: " + idCliente);
		}

		return this.clienteRepository.findById(idCliente).get();
	}
	
	public ClienteEntity create(@Valid ClienteEntity cliente) {
		cliente.setId(null);
		
		if(this.clienteRepository.existsByEmail(cliente.getEmail())) {
			throw new BusinessRuleException("Ya existe un cliente registrado con el email: " + cliente.getEmail());
		}
		if(this.clienteRepository.existsByTelefono(cliente.getTelefono())) {
            throw new BusinessRuleException("Ya existe un cliente registrado con el teléfono: " + cliente.getTelefono());
        }

		return this.clienteRepository.save(cliente);
	}
	
	public ClienteEntity update(int idCliente, @Valid ClienteEntity cliente) {
		ClienteEntity clienteBD = this.findById(idCliente);
		
        // Validar email único si cambió
        if (!cliente.getEmail().equalsIgnoreCase(clienteBD.getEmail()) && clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new BusinessRuleException("Ya existe otro cliente con el email: " + cliente.getEmail());
        }
        // Validar teléfono único si cambió
        if (!cliente.getTelefono().equals(clienteBD.getTelefono()) && clienteRepository.existsByTelefono(cliente.getTelefono())) {
            throw new BusinessRuleException("Ya existe otro cliente con el teléfono: " + cliente.getTelefono());
        }
		
		clienteBD.setNombre(cliente.getNombre());
		clienteBD.setDireccion(cliente.getDireccion());
		clienteBD.setEmail(cliente.getEmail());
		clienteBD.setTelefono(cliente.getTelefono());

		return this.clienteRepository.save(clienteBD);
	}
	
	public void deleteById(long idCliente) {
		if (!this.clienteRepository.existsById(idCliente)) {
			throw new ResourceNotFoundException("No se puede eliminar. Cliente no encontrado con ID: " + idCliente);
		}

		this.clienteRepository.deleteById(idCliente);
	}
	

}
