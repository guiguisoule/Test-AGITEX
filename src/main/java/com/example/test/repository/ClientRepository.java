package com.example.test.repository;

import com.example.test.model.Client;
import com.example.test.model.MoyenneSalaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
