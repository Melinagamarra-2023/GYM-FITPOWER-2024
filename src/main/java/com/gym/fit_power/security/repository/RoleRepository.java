package com.gym.fit_power.security.repository;

import com.gym.fit_power.security.model.ERole;
import com.gym.fit_power.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName (ERole roName);
}
