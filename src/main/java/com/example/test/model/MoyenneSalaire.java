package com.example.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MoyenneSalaire {
    @Id
    String profession;
    long moyenneSalaire;
}
