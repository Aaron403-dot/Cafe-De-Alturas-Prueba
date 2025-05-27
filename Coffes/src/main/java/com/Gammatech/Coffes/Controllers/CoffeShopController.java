/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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

import com.Gammatech.Coffes.Entities.Coffe;
import com.Gammatech.Coffes.Res.PageResponseCoffe;
import com.Gammatech.Coffes.Service.ServiceCoffe;



/**
 *
 * @author Aaron del Cristo Suarez Suarez
 */

@RestController
public class CoffeShopController {	

	private ServiceCoffe serviceCoffe;


	public CoffeShopController(ServiceCoffe serviceCoffe) {
		this.serviceCoffe = serviceCoffe;
	}

	@GetMapping("/coffes")
	public ResponseEntity<PageResponseCoffe> getListaCoffe(@RequestParam(defaultValue = "0") int page,
            					@RequestParam(defaultValue = "2") int size) {	
		Page<Coffe> coffePage = serviceCoffe.getListaCoffe(page, size);
        PageResponseCoffe pageResponseCoffe = new PageResponseCoffe(
                coffePage.getContent(),
                (int) coffePage.getTotalElements(),
                coffePage.getTotalPages(),
                coffePage.getNumber()
        );	
		return ResponseEntity.status(HttpStatus.OK).body(pageResponseCoffe);
	}

	@GetMapping("/coffes/{id}")
	public ResponseEntity<Coffe> getCoffeById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceCoffe.getCoffeById(id).orElse(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	@PostMapping("/coffes")
	public ResponseEntity<Coffe> postCoffe(@RequestBody Coffe entity) {
		try {
			Coffe coffes=serviceCoffe.addCoffe(entity);
			return ResponseEntity.status(201).body(coffes);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		
	}
	
	@PutMapping("/coffes/{id}")
	public ResponseEntity<Coffe> putCoffe(@PathVariable int id, @RequestBody Coffe entity) {
		try {
			return ResponseEntity.status(200).body(serviceCoffe.putCoffe(entity));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	@DeleteMapping("/coffes/{id}")
	public ResponseEntity<Coffe> deleteCoffe(@PathVariable long id) {
		try {
			Optional<Coffe> coffe = serviceCoffe.deleteCoffe(id);
			return ResponseEntity.status(HttpStatus.OK).body(coffe.orElse(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	@PatchMapping("/coffes/{id}")
	public ResponseEntity<Coffe> patchCoffe(@PathVariable int id, @RequestBody Coffe entity) {
		try {
			Coffe nCoffe = serviceCoffe.patchCoffe(id, entity);
			return ResponseEntity.status(200).body(nCoffe);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
}
