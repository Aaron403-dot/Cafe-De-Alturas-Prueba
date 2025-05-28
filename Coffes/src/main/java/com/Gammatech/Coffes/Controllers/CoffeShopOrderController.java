package com.Gammatech.Coffes.Controllers;

import java.util.EmptyStackException;
import java.util.List;
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

import com.Gammatech.Coffes.Entities.Orders;
import com.Gammatech.Coffes.Res.PageResponseOrders;
import com.Gammatech.Coffes.Service.ServiceOrders;



/**
 * Controlador para gestionar los pedidos de la cafetería.
 * Proporciona endpoints CRUD para la entidad Orders.
 * @author Aaron
 */
@RestController
public class CoffeShopOrderController {
	
	/**
	 * 
	 * 
	 * */
		
	private final ServiceOrders serviceOrders;
	
	public CoffeShopOrderController(ServiceOrders serviceOrder){
		this.serviceOrders = serviceOrder;
	}
	
	/**
	 * Obtiene una página de pedidos.
	 * @param page Página solicitada
	 * @param size Tamaño de la página
	 * @return Página de pedidos
	 */
	@GetMapping("/orders")
	public ResponseEntity<PageResponseOrders> getOrders(@RequestParam(defaultValue = "0") int page,
            					@RequestParam(defaultValue = "2") int size) {
		Page<Orders> ordersPage = serviceOrders.getListaOrders(page, size);
        PageResponseOrders pageResponseOrders = new PageResponseOrders(
                ordersPage.getContent(),
                (int) ordersPage.getTotalElements(),
                ordersPage.getTotalPages(),
                ordersPage.getNumber()
        );	
		return ResponseEntity.status(HttpStatus.OK).body(pageResponseOrders);
	}
	
	/**
	 * Obtiene un pedido por su ID.
	 * @param id ID del pedido
	 * @return Pedido encontrado o null si no existe
	 */
	@GetMapping("/orders/{id}")
	public ResponseEntity<Optional<Orders>> getOrderById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceOrders.getOrderById(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	/**
	 * Obtiene los pedidos de un cliente por su ID.
	 * @param id ID del cliente
	 * @return Lista de pedidos del cliente
	 */
	@GetMapping("/orders/client/{id}")
	public ResponseEntity<List<Orders>> getOrdersByClientId(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceOrders.getOrdersByClientId(id));
		} catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}


	/**
	 * Crea un nuevo pedido.
	 * @param order Pedido a crear
	 * @return Pedido creado
	 */
	@PostMapping(value = "/orders", consumes = "application/json")
	public ResponseEntity<Orders> addOrder(@RequestBody Orders order) {
		try {
			Orders orders = serviceOrders.save(order);
			return ResponseEntity.status(201).body(orders);
		} catch (IllegalArgumentException e) {
			System.err.println("Error al agregar la orden: " + e.getMessage());
			return ResponseEntity.status(400).body(null);
		}
	}
	
	/**
	 * Actualiza completamente un pedido existente.
	 * @param id ID del pedido
	 * @param order Datos actualizados
	 * @return Pedido actualizado
	 */
	@PutMapping("/orders/{id}")
	public ResponseEntity<Orders> putOrder(@PathVariable long id, @RequestBody Orders order) {
		try {
			return ResponseEntity.status(200).body(serviceOrders.put(order));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	/**
	 * Elimina un pedido por su ID.
	 * @param id ID del pedido
	 * @return Pedido eliminado o null si no existe
	 */
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Orders> deleteOrder(@PathVariable long id) {
		try {
			Optional<Orders> orders = serviceOrders.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(orders.orElse(null));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	/**
	 * Actualiza parcialmente un pedido existente.
	 * @param id ID del pedido
	 * @param order Datos a actualizar
	 * @return Pedido actualizado
	 */
	@PatchMapping("/orders/{id}")
	public ResponseEntity<Orders> patchOrder(@PathVariable long id, @RequestBody Orders order) {
		try {
			Orders nOrder = serviceOrders.patch(id, order);
			return ResponseEntity.status(200).body(nOrder);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	
	
	
}
