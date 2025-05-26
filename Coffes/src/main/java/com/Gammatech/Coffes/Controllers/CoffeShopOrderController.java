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

import com.Gammatech.Coffes.Entities.Orders;
import com.Gammatech.Coffes.Service.ServiceOrders;



@RestController
public class CoffeShopOrderController {
	
	/**
	 * 
	 * 
	 * */
	
	private final List<Orders> orders = new ArrayList<>();
	
	private final ServiceOrders serviceOrders;
	
	public CoffeShopOrderController(ServiceOrders serviceOrder){
		this.serviceOrders = serviceOrder;
	}
	
	@GetMapping("/orders")
	public List<Orders> getOrders() {
		return serviceOrders.getListaOrders();
	}
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<Orders> getOrderById(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceOrders.getOrderById(id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	@GetMapping("/orders/client/{id}")
	public ResponseEntity<List<Orders>> getOrdersByClientId(@PathVariable long id) {
		try {
			return ResponseEntity.status(200).body(serviceOrders.getOrdersByClientId(id));
		} catch (EmptyStackException e) {
			return ResponseEntity.status(404).body(null);
		}
	}


	@PostMapping(value = "/orders", consumes = "application/json")
	public ResponseEntity<Orders> addOrder(@RequestBody Orders order) {
		try {
			orders.add(serviceOrders.addOrder(order));
			return ResponseEntity.status(201).body(serviceOrders.addOrder(order));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	@PutMapping("/orders/{id}")
	public ResponseEntity<Orders> putOrder(@PathVariable long id, @RequestBody Orders order) {
		try {
			order.setId(id);
			orders.set((int)id -1, serviceOrders.putOrder(order));
			return ResponseEntity.status(200).body(serviceOrders.putOrder(order));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Orders> deleteOrder(@PathVariable long id) {
		try {
			orders.remove( (int) serviceOrders.deleteOrder(id) -1);
			return ResponseEntity.status(HttpStatus.OK).body(null);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
		catch (EmptyStackException e) {
			return ResponseEntity.status(204).body(null);
		}
		

	}
	
	@PatchMapping("/orders/{id}")
	public ResponseEntity<Orders> patchOrder(@PathVariable long id, @RequestBody Orders order) {
		try {
			Orders nOrder = serviceOrders.patchOrder(id, order);
			orders.set((int)id -1, nOrder);
			return ResponseEntity.status(200).body(nOrder);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(null);
		}
	}
	
	
	
	
}
