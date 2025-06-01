package com.Gammatech.Coffees.Res;

import java.util.List;

import com.Gammatech.Coffees.Entities.Clients;

/**
 * Clase de respuesta para paginación de clientes.
 * Contiene la lista de clientes y metadatos de la página.
 */
public class PageResponseClients {
    private List<Clients> clients;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    /**
     * Constructor de la clase PageResponseClients.
     * @param content Lista de clientes
     * @param totalElements Número total de elementos
     * @param totalPages Número total de páginas
     * @param currentPage Página actual
     */
    public PageResponseClients(List<Clients> content, int totalElements, int totalPages, int currentPage) {
        this.clients = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * Obtiene la lista de clientes de la página.
     * @return Lista de clientes
     */
    public List<Clients> getClients() {
        return clients;
    }

    /**
     * Establece la lista de clientes de la página.
     * @param clients Lista de clientes
     */
    public void setClients(List<Clients> clients) {
        this.clients = clients;
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
