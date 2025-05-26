package com.Gammatech.Coffes.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Gammatech.Coffes.Entities.Clients;
import com.Gammatech.Coffes.Service.ServiceClients;

@RestController
public class CoffeShopClientController {
	
	private final ServiceClients serviceClients;
	
	public CoffeShopClientController(ServiceClients serviceClients) {
		this.serviceClients = serviceClients;
	}
	
	@GetMapping("/clients")
	public ResponseEntity<List<Clients>> getListaClients() {
		return ResponseEntity.status(200).body(serviceClients.getListaClients());
	}
	
	@GetMapping("/clients/{id}")
	public ResponseEntity<Clients> getClientById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceClients.getClientById(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	@PostMapping("/clients")
	public ResponseEntity<Clients> addClient(@RequestBody Clients client) {
		try {
			return ResponseEntity.status(201).body(serviceClients.addClient(client));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	@PutMapping("/clients/{id}")
	public ResponseEntity<Clients> putClient(@PathVariable long id, @RequestBody Clients client) {
		try {
			client.setId(id);
			return ResponseEntity.status(200).body(serviceClients.putClient(client));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	@DeleteMapping("/clients/{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable long id) {
		try {
			serviceClients.deleteClient(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).build();
		}
	}
	
	@PatchMapping("/clients/{id}")
	public ResponseEntity<Clients> patchClient(@PathVariable long id, @RequestBody Clients client) {
		try {
			return ResponseEntity.status(200).body(serviceClients.patchClient(id, client));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
}
