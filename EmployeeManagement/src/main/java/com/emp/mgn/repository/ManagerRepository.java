package com.emp.mgn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.mgn.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	Optional<Manager> findByUsername(String username);

}
