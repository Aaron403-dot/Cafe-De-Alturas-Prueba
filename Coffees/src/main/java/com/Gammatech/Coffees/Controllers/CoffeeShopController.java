/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Controllers;

import java.util.EmptyStackException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Gammatech.Coffees.Entities.Coffee;
import com.Gammatech.Coffees.Res.PageResponseCoffee;
import com.Gammatech.Coffees.Service.ServiceCoffee;



/**
 * Controlador para gestionar los cafés de la cafetería.
 * Proporciona endpoints CRUD para la entidad Coffe.
 *
 * @author Aaron del Cristo Suarez Suarez
 */

@RestController
public class CoffeeShopController {	

	private final ServiceCoffee serviceCoffe;


	public CoffeeShopController(ServiceCoffee serviceCoffe) {
		this.serviceCoffe = serviceCoffe;
	}

	/**
	 * Obtiene una página de cafés.
	 * @param page Página solicitada
	 * @param size Tamaño de la página
	 * @return Página de cafés
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/coffes")
	public ResponseEntity<PageResponseCoffee> getListaCoffe(@RequestParam(defaultValue = "0") int page,
            					@RequestParam(defaultValue = "2") int size) {	
		Page<Coffee> coffePage = serviceCoffe.getListaCoffe(page, size);
        PageResponseCoffee pageResponseCoffe = new PageResponseCoffee(
                coffePage.getContent(),
                (int) coffePage.getTotalElements(),
                coffePage.getTotalPages(),
                coffePage.getNumber()
        );	
		return ResponseEntity.status(HttpStatus.OK).body(pageResponseCoffe);
	}

	/**
	 * Obtiene un café por su ID.
	 * @param id ID del café
	 * @return Café encontrado o null si no existe
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/coffes/{id}")
	public ResponseEntity<Coffee> getCoffeById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceCoffe.getCoffeById(id).orElse(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	/**
	 * Crea un nuevo café.
	 * @param entity Café a crear
	 * @return Café creado
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/coffes")
	public ResponseEntity<Coffee> postCoffe(@RequestBody Coffee entity) {
		try {
			Coffee coffes=serviceCoffe.save(entity);
			return ResponseEntity.status(201).body(coffes);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		
	}
	
	/**
	 * Actualiza completamente un café existente.
	 * @param id ID del café
	 * @param entity Datos actualizados
	 * @return Café actualizado
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/coffes/{id}")
	public ResponseEntity<Coffee> putCoffe(@PathVariable int id, @RequestBody Coffee entity) {
		try {
			return ResponseEntity.status(200).body(serviceCoffe.put(entity));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	/**
	 * Elimina un café por su ID.
	 * @param id ID del café
	 * @return Café eliminado o null si no existe
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/coffes/{id}")
	public ResponseEntity<Coffee> deleteCoffe(@PathVariable long id) {
		try {
			Optional<Coffee> coffe = serviceCoffe.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(coffe.orElse(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	/**
	 * Actualiza parcialmente un café existente.
	 * @param id ID del café
	 * @param entity Datos a actualizar
	 * @return Café actualizado
	 */
    @PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/coffes/{id}")
	public ResponseEntity<Coffee> patchCoffe(@PathVariable int id, @RequestBody Coffee entity) {
		try {
			Coffee nCoffe = serviceCoffe.patch(id, entity);
			return ResponseEntity.status(200).body(nCoffe);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
}
