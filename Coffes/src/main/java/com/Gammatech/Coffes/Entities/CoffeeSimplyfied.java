/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Entities;

import com.Gammatech.Coffes.Service.ServiceCoffe;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Usuario
 */
public class CoffeeSimplyfied {
    private long id;
    private double precio;
    private int cantidad;
    @JsonIgnore
    private transient Coffe coffeCompleto;

    public CoffeeSimplyfied() {
    }

    public CoffeeSimplyfied(long id, double precio, int cantidad) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @JsonIgnore
    public Coffe getCoffeCompleto() {
        return coffeCompleto;
    }

    public void setCoffeCompleto(Coffe coffeCompleto) {
        this.coffeCompleto = coffeCompleto;
    }

    public boolean validarCoffe(ServiceCoffe serviceCoffe) {
        try {
            Coffe coffe = serviceCoffe.getCoffeById(this.id);
            return coffe != null && coffe.getId() == this.id;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void asegurarCoffeValido(ServiceCoffe serviceCoffe) {
        if (!validarCoffe(serviceCoffe)) {
            throw new IllegalArgumentException("El café con ID " + this.id + " no existe o no es válido");
        }
    }
}
