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
}
