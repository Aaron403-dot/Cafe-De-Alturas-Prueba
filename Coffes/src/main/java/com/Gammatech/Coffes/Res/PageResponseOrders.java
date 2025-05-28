/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Res;

import java.util.List;

import com.Gammatech.Coffes.Entities.Orders;

/**
 * Clase de respuesta para paginación de pedidos.
 * Contiene la lista de pedidos y metadatos de la página.
 */
public class PageResponseOrders {


    private List<Orders> orders;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    //cambia todos los campos a los que se le pasan por parametro
    public PageResponseOrders(List<Orders> content, int totalElements, int totalPages, int currentPage) {
        this.orders = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * Obtiene la lista de pedidos de la página.
     * @return Lista de pedidos
     */
    public List<Orders> getOrders() {
        return orders;
    }
    
    /**
     * Establece la lista de pedidos de la página.
     * @param orders Lista de pedidos
     */
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    /**
     * Obtiene el número total de elementos.
     * @return Total de elementos
     */
    public int getTotalElements() {
        return totalElements;
    }

    /**
     * Establece el número total de elementos.
     * @param totalElements Total de elementos
     */
    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Obtiene el número total de páginas.
     * @return Total de páginas
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Establece el número total de páginas.
     * @param totalPages Total de páginas
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Obtiene el número de la página actual.
     * @return Página actual
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Establece el número de la página actual.
     * @param currentPage Página actual
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
