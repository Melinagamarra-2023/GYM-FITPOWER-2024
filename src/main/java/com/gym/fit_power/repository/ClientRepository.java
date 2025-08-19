package com.gym.fit_power.repository;

import com.gym.fit_power.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByCuit(String cuit);
    
    @Query("SELECT DISTINCT c FROM Client c " +
           "LEFT JOIN FETCH c.assignedGym " +
           "LEFT JOIN FETCH c.assignedTrainer " +
           "LEFT JOIN FETCH c.assignedNutritionist")
    List<Client> findAllWithRelations();
}
