package com.pin.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pin.springjwt.models.Role;
import com.pin.springjwt.models.ERole;

@Repository
public interface RoleResposity extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
