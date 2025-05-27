/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Gammatech.Coffes.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffes.Entities.Clients;

/**
 *
 * @author Usuario
 */
@Repository
public interface RepoClient extends JpaRepository<Clients, Long> {


} 