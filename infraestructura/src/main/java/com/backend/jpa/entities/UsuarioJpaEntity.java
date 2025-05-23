package com.backend.jpa.entities;

import com.backend.usuario.models.Estado;
import com.backend.usuario.models.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios") // Nombre de la tabla en la base de datos
public class UsuarioJpaEntity {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Integer idUsuario; // Usar Integer para permitir null en la creación si es autogenerado

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @ManyToOne(fetch = FetchType.LAZY) // Relación Muchos a Uno con Carrera
    @JoinColumn(name = "id_carrera", nullable = false)
    private CarreraJpaEntity carrera; // La entidad JPA de Carrera

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "email_verificado", nullable = false)
    private Boolean emailVerificado; // Usar Boolean para evitar problemas con valores por defecto de boolean en BD

    @Column(name = "password", nullable = false, length = 255) // Generalmente se guarda hasheada
    private String password;

    @Column(name = "token_verificacion", length = 255)
    private String tokenVerificacion;

    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como String en la BD
    @Column(name = "rol", nullable = false, length = 50)
    private Rol rol; // Reutilizamos el enum de la capa core

    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como String en la BD
    @Column(name = "estado", nullable = false, length = 50)
    private Estado estado; // Reutilizamos el enum de la capa core

    @Column(name = "imagen_perfil", length = 255)
    private String imagenPerfil;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDateTime fechaBaja; // Puede ser null

    public UsuarioJpaEntity(String nombreCompleto, CarreraJpaEntity carreraJpaEntity, String email, boolean emailVerificado, String password, String tokenVerificacion, Rol rol, Estado estado, String imagenPerfil, LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
        this.nombreCompleto = nombreCompleto;
        this.carrera = carreraJpaEntity;
        this.email = email;
        this.emailVerificado = emailVerificado;
        this.password = password;
        this.tokenVerificacion = tokenVerificacion;
        this.rol = rol;
        this.estado = estado;
        this.imagenPerfil = imagenPerfil;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }
    //Constructor necesario para actualizar datos del usuario
    public UsuarioJpaEntity(Integer idUsuario, String nombreCompleto, CarreraJpaEntity carreraJpaEntity, String email, boolean emailVerificado, String password, String tokenVerificacion, Rol rol, Estado estado, String imagenPerfil, LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carreraJpaEntity;
        this.email = email;
        this.emailVerificado = emailVerificado;
        this.password = password;
        this.tokenVerificacion = tokenVerificacion;
        this.rol = rol;
        this.estado = estado;
        this.imagenPerfil = imagenPerfil;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

}

