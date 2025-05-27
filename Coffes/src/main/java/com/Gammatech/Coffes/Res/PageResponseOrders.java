/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Res;

import java.util.List;

import com.Gammatech.Coffes.Entities.Orders;

/**
 *
 * @author Usuario
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

    public List<Orders> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
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
