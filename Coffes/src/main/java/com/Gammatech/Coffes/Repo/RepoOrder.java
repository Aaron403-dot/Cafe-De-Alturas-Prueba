/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffes.Entities.Orders;

/**
 *
 * @author Usuario
 */
@Repository
public interface RepoOrder extends CrudRepository<Orders, Long> {

    // private final Map<Long, Orders> orders = new HashMap<>();
    
    // public List<Orders> findAll() {
    //     return List.copyOf(orders.values());
    // }

    // public Optional<Orders> findById(Long id) {
    //     return Optional.ofNullable(orders.get(id));
    // }

    // public Orders save(Orders orders) {
    //     this.orders.put(orders.getId(), orders);
    //     return orders;
    // }

    // public void delete(Long id) {
    //     orders.remove(id);
    // }

    // public Orders update(Orders orders) {
    //     this.orders.put(orders.getId(), orders);
    //     return orders;
    // }

    // public List<Orders> findByClientId(long id) {
    //     return orders.values().stream()
    //         .filter(order -> order.getClientId() == id)
    //         .collect(Collectors.toList());
    // }
} 