package com.Gammatech.Coffes.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Gammatech.Coffes.Entities.Users;

@Repository
public interface RepoUsers extends JpaRepository<Users, String> {


}