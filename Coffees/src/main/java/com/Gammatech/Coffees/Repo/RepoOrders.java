/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffees.Entities.Orders;

/**
 *
 * Repositorio para la entidad Orders.
 * @author Aaron
 */
@Repository
public interface RepoOrders extends JpaRepository<Orders, Long> {

    public List<Orders> findByClientId(long clientId);

    public Optional<Orders> findById(long id);


}