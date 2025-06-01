/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Entities;

import java.util.Optional;

import com.Gammatech.Coffees.Service.ServiceCoffee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

/**
 * Entidad que representa un café simplificado dentro de una orden.
 * @author Aaron
 */
@Entity
public class CoffeeSimplyfied {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double price;

    @Column
    private int quantity;

    @JsonIgnore
    private transient Coffee coffeCompleto;

    @Transient
    @ManyToOne
    private Orders orders;
    /**
     * Constructor vacío para JPA.
     * Es necesario para que JPA pueda crear instancias de esta entidad.
     */
    public CoffeeSimplyfied() {
    }
    /**
     * Constructor para crear un café simplificado con ID, precio y cantidad.
     *
     * @param id Identificador del café
     * @param precio Precio del café
     * @param cantidad Cantidad de este café en la orden
     */
    public CoffeeSimplyfied(long id, double precio, int cantidad) {
        this.id = id;
        this.price = precio;
        this.quantity = cantidad;
    }

    /**
     * Obtiene el identificador del café simplificado.
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador del café simplificado.
     *
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene el precio del café simplificado.
     *
     * @return Precio
     */
    public double getPrice() {
        return price;
    }

    /**
     * Establece el precio del café simplificado.
     *
     * @param precio Precio
     */
    public void setPrice(double precio) {
        this.price = precio;
    }

    /**
     * Obtiene la cantidad de este café en la orden.
     *
     * @return Cantidad
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Establece la cantidad de este café en la orden.
     *
     * @param cantidad Cantidad
     */
    public void setQuantity(int cantidad) {
        this.quantity = cantidad;
    }

    /**
     * Obtiene el objeto Coffe completo asociado.
     *
     * @return Coffe completo
     */
    @JsonIgnore
    public Coffee getCoffeCompleto() {
        return coffeCompleto;
    }

    /**
     * Establece el objeto Coffe completo asociado.
     *
     * @param coffeCompleto Coffe completo
     */
    public void setCoffeCompleto(Coffee coffeCompleto) {
        this.coffeCompleto = coffeCompleto;
    }

    /**
     * Valida si el café existe en el sistema.
     *
     * @param serviceCoffe Servicio de cafés
     * @return true si es válido, false en caso contrario
     */
    public boolean validarCoffe(ServiceCoffee serviceCoffe) {
        try {
            Optional<Coffee> coffe = serviceCoffe.getCoffeById(this.id);
            return coffe != null && coffe.get().getId() == this.id;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Lanza excepción si el café no es válido.
     *
     * @param serviceCoffe Servicio de cafés
     */
    public void asegurarCoffeValido(ServiceCoffee serviceCoffe) {
        if (!validarCoffe(serviceCoffe)) {
            throw new IllegalArgumentException("El café con ID " + this.id + " no existe o no es válido");
        }
    }

    /**
     * Obtiene la orden asociada.
     *
     * @return Orden
     */
    public Orders getOrders() {
        return orders;
    }

    /**
     * Establece la orden asociada.
     *
     * @param orders Orden
     */
    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
