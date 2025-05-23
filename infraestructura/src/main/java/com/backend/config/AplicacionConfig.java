package com.backend.config;

import com.backend.usuario.inputs.IRegistrarUsuarioInput;
import com.backend.usuario.inputs.IVerificarEmailInput;
import com.backend.usuario.mapper.CarreraDtoMapper;
import com.backend.usuario.mapper.UsuarioDtoMapper;
import com.backend.usuario.repositories.IUsuarioRepositorio;
import com.backend.usuario.usecases.RegistrarUsuarioUseCase;
import com.backend.usuario.usecases.VerificarEmailUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AplicacionConfig {

    @Bean
    public IRegistrarUsuarioInput iRegistrarUsuarioInput(IUsuarioRepositorio iUsuarioRepositorio){
        return new RegistrarUsuarioUseCase(iUsuarioRepositorio);
    }

    @Bean
    public IVerificarEmailInput iVerificarEmailInput(IUsuarioRepositorio iUsuarioRepositorio){
        return new VerificarEmailUseCase(iUsuarioRepositorio);
    }

    @Bean
    public CarreraDtoMapper carreraDtoMapper() {
        return new CarreraDtoMapper(); // Asumo que CarreraDtoMapper no tiene dependencias o las tiene inyectadas aquí
    }

    @Bean
    public UsuarioDtoMapper userDtoMapper(CarreraDtoMapper carreraDtoMapper) {
        return new UsuarioDtoMapper(carreraDtoMapper); // Inyectamos el bean de CarreraDtoMapper
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Este Bean le dice a Spring que el endpoint de registro está permitido para todos.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // solo para pruebas con Postman, luego activar
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuarios/registrar").permitAll() // ruta pública
                        .anyRequest().authenticated() // el resto requiere auth
                )
                .httpBasic(withDefaults()) // o formLogin, según lo que se use
                .build();
    }
}
