package com.backend.usuario.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioRequest {

    private String nombreCompleto;
    private String carrera; // El nombre de la carrera que viene del formulario de registro
    private String email;
    private String password;
}

