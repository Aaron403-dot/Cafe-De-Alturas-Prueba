package com.Gammatech.Coffes.Res;

import java.util.List;

import com.Gammatech.Coffes.Entities.Clients;

public class PageResponseClients {
    private List<Clients> clients;
    private int totalElements;
    private int totalPages;
    private int currentPage;

    public PageResponseClients(List<Clients> content, int totalElements, int totalPages, int currentPage) {
        this.clients = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Clients> getClients() {
        return clients;
    }

    public void setClients(List<Clients> clients) {
        this.clients = clients;
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
