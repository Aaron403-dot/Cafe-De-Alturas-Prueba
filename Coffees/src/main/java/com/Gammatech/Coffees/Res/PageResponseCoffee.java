package com.Gammatech.Coffees.Res;

import java.util.List;

import com.Gammatech.Coffees.Entities.Coffee;

/**
 * Clase de respuesta para paginación de cafés.
 * Contiene la lista de cafés y metadatos de la página.
 */
public class PageResponseCoffee {
    private List<Coffee> coffes;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    public PageResponseCoffee(List<Coffee> content, int totalElements, int totalPages, int currentPage) {
        this.coffes = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /**
     * Obtiene la lista de cafés de la página.
     * @return Lista de cafés
     */
    public List<Coffee> getCoffes() {
        return coffes;
    }

    /**
     * Establece la lista de cafés de la página.
     * @param coffes Lista de cafés
     */
    public void setCoffes(List<Coffee> coffes) {
        this.coffes = coffes;
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
