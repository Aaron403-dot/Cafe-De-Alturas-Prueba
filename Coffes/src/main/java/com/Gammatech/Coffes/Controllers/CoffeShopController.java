/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Controllers;

import java.util.ArrayList;
import java.util.EmptyStackException;
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

import com.Gammatech.Coffes.Entities.Coffe;
import com.Gammatech.Coffes.Service.ServiceCoffe;



/**
 *
 * @author Aaron del Cristo Suarez Suarez
 */

@RestController
public class CoffeShopController {
	
	private static List<Coffe> coffes = new ArrayList<>();
	

	private ServiceCoffe serviceCoffe;


	public CoffeShopController(ServiceCoffe serviceCoffe) {
		this.serviceCoffe = serviceCoffe;
	}

	@GetMapping("/coffes")
	public ResponseEntity<List<Coffe>> getListaCoffe() {	
		return ResponseEntity.status(200).body(serviceCoffe.getListaCoffe());
	}

	@GetMapping("/coffes/{id}")
	public ResponseEntity<Coffe> getCoffeById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceCoffe.getCoffeById(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	@PostMapping("/coffes")
	public ResponseEntity<Coffe> postMethodName(@RequestBody Coffe entity) {
		try {
			coffes.add(serviceCoffe.addCoffe(entity));
			return ResponseEntity.status(201).body(entity);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		
	}
	
	@PutMapping("/coffes/{id}")
	public ResponseEntity<Coffe> putMethodName(@PathVariable int id, @RequestBody Coffe entity) {
		try {
			entity.setId((long)id);
			coffes.set(id -1, serviceCoffe.putCoffe(entity));
			return ResponseEntity.status(200).body(entity);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	@DeleteMapping("/coffes/{id}")
	public ResponseEntity<Coffe> deleteMethodName(@PathVariable long id) {
		try {
			coffes.remove((int)serviceCoffe.deleteCoffe(id) -1);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	@PatchMapping("/coffes/{id}")
	public ResponseEntity<Coffe> patchMethodName(@PathVariable int id, @RequestBody Coffe entity) {
		try {
			Coffe nCoffe = serviceCoffe.patchCoffe(id, entity);
			coffes.set(id -1, nCoffe);
			return ResponseEntity.status(200).body(nCoffe);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
}
