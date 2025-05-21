package com.backend.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;

@Entity
@Table(name = "carreras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarreraJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrera;

    @Column(name = "nombre_carrera", nullable = false, unique = true, length = 100)
    private String nombreCarrera;

    public CarreraJpaEntity(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }
}
