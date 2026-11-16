package com.daw.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.daw.persistence.entities.PedidoEntity;
import com.daw.persistence.repositories.PedidoRepository;
import com.daw.services.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class PedidoService {
	
	private final PedidoRepository pedidoRepository;
	
	public List<PedidoEntity> findAll(){
		return this.pedidoRepository.findAll();
	}
	
	public PedidoEntity findById(long idPedido) {
		if (!this.pedidoRepository.existsById(idPedido)) {
			throw new ResourceNotFoundException("Pedido no encontrado con ID: " + idPedido);
		}

		return this.pedidoRepository.findById(idPedido).get();
	}
	
	public PedidoEntity create(@Valid PedidoEntity pedido) {
//		ClienteEntity cliente = this.clienteRepository.findById(pedido.getCliente().getId())
//				.orElseThrow(() -> new ResourceNotFoundException("No se puede crear el pedido. Cliente no encontrado con ID: " + pedido.getCliente().getId()));
		
		pedido.setId(0L);
		pedido.setFecha(LocalDateTime.now());
		pedido.setTotal(0.0);
//		pedido.setCliente(cliente);
		
		return this.pedidoRepository.save(pedido);
	}

	public PedidoEntity update(long idPedido, @Valid PedidoEntity pedido) {
		PedidoEntity pedidoBD = this.findById(idPedido);
		
//		ClienteEntity cliente = this.clienteRepository.findById(pedido.getCliente().getId())
//				.orElseThrow(() -> new ResourceNotFoundException("No se puede crear el pedido. Cliente no encontrado con ID: " + pedido.getCliente().getId()));
		
		
		pedidoBD.setMetodo(pedido.getMetodo());
		pedidoBD.setNotas(pedido.getNotas());
//        pedido.setCliente(cliente);

		return this.pedidoRepository.save(pedidoBD);
	}

	public void deleteById(long idPedido) {
		if (!this.pedidoRepository.existsById(idPedido)) {
			throw new ResourceNotFoundException("No se puede eliminar. Pedido no encontrado con ID: " + idPedido);
		}

		this.pedidoRepository.deleteById(idPedido);
	}
}
