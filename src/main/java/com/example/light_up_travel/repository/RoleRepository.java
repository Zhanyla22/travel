package com.example.light_up_travel.repository;

import java.util.Optional;

import com.example.light_up_travel.model.ERole;
import com.example.light_up_travel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}