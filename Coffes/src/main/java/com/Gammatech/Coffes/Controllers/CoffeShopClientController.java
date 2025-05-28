package com.Gammatech.Coffes.Controllers;

import java.util.EmptyStackException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Gammatech.Coffes.Entities.Clients;
import com.Gammatech.Coffes.Res.PageResponseClients;
import com.Gammatech.Coffes.Service.ServiceClients;

/**
 * Controlador para gestionar los clientes de la cafetería.
 * Proporciona endpoints CRUD para la entidad Clients.
 * @author Aaron
 */
@RestController
public class CoffeShopClientController {
	
	private final ServiceClients serviceClients;
	
	public CoffeShopClientController(ServiceClients serviceClients) {
		this.serviceClients = serviceClients;
	}
	
	/**
	 * Obtiene una página de clientes.
	 * @param page Página solicitada
	 * @param size Tamaño de la página
	 * @return Página de clientes
	 */
	@GetMapping("/clients")
	public ResponseEntity<PageResponseClients> getListaClients(@RequestParam(defaultValue = "0") int page,
            					@RequestParam(defaultValue = "2") int size) {
		Page<Clients> clientsPage = serviceClients.getListaClients(page, size);
        PageResponseClients pageResponseClients = new PageResponseClients(
                clientsPage.getContent(),
                (int) clientsPage.getTotalElements(),
                clientsPage.getTotalPages(),
                clientsPage.getNumber()
        );	
		return ResponseEntity.status(HttpStatus.OK).body(pageResponseClients);
	}
	
	/**
	 * Obtiene un cliente por su ID.
	 * @param id ID del cliente
	 * @return Cliente encontrado o null si no existe
	 */
	@GetMapping("/clients/{id}")
	public ResponseEntity<Clients> getClientById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceClients.getClientById(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	/**
	 * Crea un nuevo cliente.
	 * @param client Cliente a crear
	 * @return Cliente creado
	 */
	@PostMapping("/clients")
	public ResponseEntity<Clients> addClient(@RequestBody Clients client) {
		try {
			return ResponseEntity.status(201).body(serviceClients.save(client));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	/**
	 * Actualiza completamente un cliente existente.
	 * @param id ID del cliente
	 * @param client Datos actualizados
	 * @return Cliente actualizado
	 */
	@PutMapping("/clients/{id}")
	public ResponseEntity<Clients> putClient(@PathVariable long id, @RequestBody Clients client) {
		try {
			return ResponseEntity.status(200).body(serviceClients.put(client));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	/**
	 * Elimina un cliente por su ID.
	 * @param id ID del cliente
	 * @return Cliente eliminado o null si no existe
	 */
	@DeleteMapping("/clients/{id}")
	public ResponseEntity<Clients> deleteClient(@PathVariable long id) {
		try {
			Optional<Clients> clients = serviceClients.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(clients.orElse(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	/**
	 * Actualiza parcialmente un cliente existente.
	 * @param id ID del cliente
	 * @param client Datos a actualizar
	 * @return Cliente actualizado
	 */
	@PatchMapping("/clients/{id}")
	public ResponseEntity<Clients> patchClient(@PathVariable long id, @RequestBody Clients client) {
		try {
			return ResponseEntity.status(200).body(serviceClients.patch(id, client));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
}
