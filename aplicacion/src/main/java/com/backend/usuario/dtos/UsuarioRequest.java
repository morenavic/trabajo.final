package com.backend.usuario.dtos;

import com.backend.usuario.models.Estado;
import com.backend.usuario.models.Rol;


import java.time.LocalDateTime;

public class UsuarioRequest {

    private Integer idUsuario;
    private String nombreCompleto;
    private CarreraRequest carrera;
    private String email;
    private boolean emailVerificado;
    private String password;
    private String tokenVerificacion;
    private Rol rol; // Una vez asignado no se modifica
    private Estado estado;
    private String imagenPerfil;
    private LocalDateTime fechaAlta; // Una vez asignado no se modifica
    private LocalDateTime fechaBaja;

    public UsuarioRequest() {
    }

    public UsuarioRequest(Integer idUsuario, String nombreCompleto, CarreraRequest carrera, String email, boolean emailVerificado, String password, String tokenVerificacion, Rol rol, Estado estado, String imagenPerfil, LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public CarreraRequest getCarrera() {
        return carrera;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerificado() {
        return emailVerificado;
    }

    public String getPassword() {
        return password;
    }

    public String getTokenVerificacion() {
        return tokenVerificacion;
    }

    public Rol getRol() {
        return rol;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }
}

