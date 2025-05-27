package com.Gammatech.Coffes.Res;

import java.util.List;

import com.Gammatech.Coffes.Entities.Coffe;

public class PageResponseCoffe {
    private List<Coffe> coffes;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    public PageResponseCoffe(List<Coffe> content, int totalElements, int totalPages, int currentPage) {
        this.coffes = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Coffe> getCoffes() {
        return coffes;
    }

    public void setCoffes(List<Coffe> coffes) {
        this.coffes = coffes;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
