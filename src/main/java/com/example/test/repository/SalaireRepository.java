package com.example.test.repository;

import com.example.test.model.MoyenneSalaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaireRepository extends JpaRepository<MoyenneSalaire, String> {

    @Query(nativeQuery = true, value = "SELECT profession, AVG(salaire) as moyenne_salaire FROM client GROUP BY profession")
    List<MoyenneSalaire> findMoyenneSalaireByProfession();
}
