/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffees.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa un café en la cafetería.
 * @author Aaron
 */

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column
    private String image;

    public Coffee() {
    	
    }

    public Coffee(Long id, String name, String description, double price, int stock, String image) {
        this.id = id + 1l;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    /**
     * Obtiene el identificador del café.
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del café.
     * @return Nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la descripción del café.
     * @return Descripción
     */
    public String getDescription() {
        return description;
    }

    /**
     * Obtiene el precio del café.
     * @return Precio
     */
    public double getPrice() {
        return price;
    }

    /**
     * Obtiene el stock disponible del café.
     * @return Stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Obtiene la URL de la imagen del café.
     * @return URL de la imagen
     */
    public String getImage() {
        return image;
    }

    /**
     * Establece el identificador del café.
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Establece el nombre del café.
     * @param name Nombre
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece la descripción del café.
     * @param description Descripción
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Establece el precio del café.
     * @param price Precio
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Establece el stock disponible del café.
     * @param stock Stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Establece la URL de la imagen del café.
     * @param image URL de la imagen
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Devuelve una representación en String del café.
     * @return String con los datos del café
     */
    @Override
    public String toString() {
        return "Coffe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                '}';
    }
}
