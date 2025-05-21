/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Controllers;

import java.util.ArrayList;
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



/**
 *
 * @author Usuario
 */

@RestController
public class CoffeShopController {
	
	private List<Coffe> coffes = new ArrayList<>();
	

	@GetMapping("/Coffes")
	public ResponseEntity<List<Coffe>> getListaCoffe() {
		return ResponseEntity.status(200).body(coffes);
	}
	
	@PostMapping("/Coffes")
	public ResponseEntity<Coffe> postMethodName(@RequestBody Coffe entity) {
		if(entity == null) {
			return ResponseEntity.status(400).build();
		}
		if(entity.getName() == null || entity.getName().isEmpty()) {
			return ResponseEntity.status(400).build();
		}
		if(entity.getPrice() <= 0) {
			return ResponseEntity.status(400).build();
		}
		if(entity.getDescription() == null || entity.getDescription().isEmpty()) {
			return ResponseEntity.status(400).build();
		}
		coffes.add(entity);
		return ResponseEntity.status(201).body(entity);
	}
	
	@PutMapping("/Coffes/{id}")
	public ResponseEntity<Coffe> putMethodName(@PathVariable int id, @RequestBody Coffe entity) {
		if(entity == null) {
			return ResponseEntity.status(400).build();
		}
		coffes.set(id, entity);
		return ResponseEntity.status(200).body(entity);
	}

	@DeleteMapping("/Coffes/{id}")
	public ResponseEntity<Coffe> deleteMethodName(@PathVariable int id) {
		if(coffes.size() == 0) {
			return ResponseEntity.status(204).build();
		}
		if(id < 0 || id >= coffes.size()) {
			return ResponseEntity.status(404).build();
		}
		if(coffes.get(id) == null) {
			return ResponseEntity.status(404).build();
		}
		coffes.remove(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PatchMapping("/Coffes/{id}")
	public ResponseEntity<Coffe> patchMethodName(@PathVariable int id, @RequestBody Coffe entity) {
		if(id < 0 || id >= coffes.size()) {
			return ResponseEntity.status(404).build();
		}
		if(coffes.get(id) == null) {
			return ResponseEntity.status(404).build();
		}
		Coffe nCoffe = coffes.get(id);
		if(entity.getName() != null) {
			nCoffe.setName(entity.getName());
		}
		if(entity.getDescription() != null) {
			nCoffe.setDescription(entity.getDescription());
		}
		if(entity.getPrice() > 0) {
			nCoffe.setPrice(entity.getPrice());
		}
		if(entity.getStock() > 0) {
			nCoffe.setStock(entity.getStock());
		}
		if(entity.getImage() != null) {
			nCoffe.setImage(entity.getImage());
		}
		coffes.set(id, nCoffe);
		return ResponseEntity.status(200).body(nCoffe);
	}
}
