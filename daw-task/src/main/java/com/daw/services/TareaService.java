package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.persistence.entities.TareaEntity;
import com.daw.persistence.entities.enums.Estado;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TareaService {
	
	private final TareaRepository tareaRepository;
	
	public List<TareaEntity> findAll() {
		return this.tareaRepository.findAll();
	}
	
	public TareaEntity findById(long idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException(String.format("La tarea con id %d no existe. ", idTarea));
		}
		
		return this.tareaRepository.findById(idTarea).get();
	}
	
	public TareaEntity create(TareaEntity tareaEntity) {
		if(tareaEntity.getFechaCreacion() != null) {
			throw new TareaException("La fecha de creación no se puede modificar. ");
		}
		if(tareaEntity.getEstado() != null) {
			throw new TareaException("El estado no se puede modificar. ");
		}
		if(tareaEntity.getHorasEstimadas() <= 0) {
			throw new TareaException("Las hora extimada tiene que ser mayor a 0.");
		}
		
		
		// La tarea debe llevar un id que no existe
		tareaEntity.setId(0L);
		tareaEntity.setEstado(Estado.PENDIENTE);
		tareaEntity.setFechaCreacion(LocalDate.now());
		tareaEntity.setHorasEstimadas(1);
		
		
		return this.tareaRepository.save(tareaEntity);
	}
	
	public TareaEntity update(long idTarea, TareaEntity tareaEntity) {
		if(tareaEntity.getId() != idTarea) {
			throw new TareaException("El id del body y el id del path no coinciden. ");
		}
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException(String.format("La tarea con id %d no existe. ", idTarea));
		}
		if(tareaEntity.getFechaCreacion() != null) {
			throw new TareaException("La fecha de creación no se puede modificar. ");
		}
		if(tareaEntity.getEstado() != null) {
			throw new TareaException("El estado no se puede modificar. ");
		}
		
		TareaEntity tareaBD = this.findById(idTarea);
		
		
		tareaBD.setTitulo(tareaEntity.getTitulo());
		tareaBD.setPrioridad(tareaEntity.getPrioridad());
		tareaBD.setHorasEstimadas(tareaEntity.getHorasEstimadas());
		
		return this.tareaRepository.save(tareaBD);
	}
	
	public void deleteById(long idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException(String.format("La tarea con id %d no existe. ", idTarea));
		}
		
		this.tareaRepository.deleteById(idTarea);
	}

	public TareaEntity iniciar(long idTarea) {
		//Aquí traigo la tarea de la BD y compruebo que el ID exista
		TareaEntity tarea = this.findById(idTarea);
		
		if(!tarea.getEstado().equals(Estado.PENDIENTE)) {
			throw new TareaException("No se puede iniciar una tarea en progreso o completada");
		}
				
		
		tarea.setEstado(Estado.EN_PROGRESO);
		return this.tareaRepository.save(tarea);
	}
	
	
	
	public TareaEntity revisar(long idTarea) {
		//Aquí traigo la tarea de la BD y compruebo que el ID exista
		TareaEntity tarea = this.findById(idTarea);
		
		if(!tarea.getEstado().equals(Estado.EN_PROGRESO)) {
			throw new TareaException("No se puede iniciar una tarea en progreso o completada");
		}
				
		
		tarea.setEstado(Estado.EN_REVISION);
		return this.tareaRepository.save(tarea);
	}
	
	
	public TareaEntity completar(long idTarea) {
		//Aquí traigo la tarea de la BD y compruebo que el ID exista
		TareaEntity tarea = this.findById(idTarea);
		
		if(!tarea.getEstado().equals(Estado.EN_REVISION)) {
			throw new TareaException("No se puede iniciar una tarea en progreso o completada");
		}
				
		
		tarea.setEstado(Estado.COMPLETADA);
		return this.tareaRepository.save(tarea);
	}
	
	
}