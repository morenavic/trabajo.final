package com.backend.usuario.models;

import com.backend.usuario.exceptions.DatosIncompletosException;
import com.backend.usuario.exceptions.DatosInvalidosException;
import com.backend.usuario.exceptions.PasswordInseguraException;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Usuario {

    private Integer idUsuario;
    private String nombreCompleto;
    private Carrera carrera;
    private String email;
    private boolean emailVerificado;
    private String password;
    private String tokenVerificacion;
    private final Rol rol; // Una vez asignado no se modifica
    private Estado estado;
    private String imagenPerfil;
    private final LocalDateTime fechaAlta; // Una vez asignado no se modifica
    private LocalDateTime fechaBaja;

    private Usuario(String nombreCompleto, Carrera carrera, String email, String password) {
        this.idUsuario = null; // Es un ID de base de datos autogenerado
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
        this.email = email;
        this.emailVerificado = false; // Siempre falso al crear
        this.password = password;
        this.tokenVerificacion = null; // Siempre nulo al crear
        this.rol = Rol.Usuario; // Siempre Rol.Usuario
        this.estado = Estado.Pendiente; // Siempre Pendiente al crear
        this.imagenPerfil = null; // Se inicializa como null, el usuario la agregará después
        this.fechaAlta = LocalDateTime.now(); // Siempre la fecha actual
        this.fechaBaja = null; // Siempre null al crear
    }

    public Usuario(Integer idUsuario, String nombreCompleto, Carrera carrera, String email, boolean emailVerificado, String password, String tokenVerificacion, Rol rol, Estado estado, String imagenPerfil, LocalDateTime fechaAlta, LocalDateTime fechaBaja) {
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

    public static Usuario instancia(String nombreCompleto, Carrera carrera, String email, String password)  throws DatosIncompletosException, DatosInvalidosException, PasswordInseguraException{

        // Validación de nombre nulo o vacío
        if(nombreCompleto == null || nombreCompleto.trim().isBlank()){
            throw new DatosIncompletosException("El nombre completo es obligatorio.");
        }
        // Validación de caracteres especiales en el nombre
        Pattern specialCharacterPattern = Pattern.compile("[^\\p{L}\\s]"); // Permite letras y espacios
        if (specialCharacterPattern.matcher(nombreCompleto).find()) {
            throw new DatosInvalidosException("El nombre completo no debe contener caracteres especiales.");
        }

        // Validación de números en el nombre
        Pattern numberPattern = Pattern.compile("[0-9]");
        if (numberPattern.matcher(nombreCompleto).find()) {
            throw new DatosInvalidosException("El nombre completo no debe contener números.");
        }

        // Validación de email nulo o vacío
        if(email == null || email.trim().isBlank()){
            throw new DatosIncompletosException("El email es obligatorio.");
        }

        // Validación de email sin @, sin dominio o sin usuario
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").
                matcher(email).
                matches()) {
            throw new DatosInvalidosException("El email tiene un formato inválido.");
        }

        // Validación de contraseña nula o vacía
        if(password == null || password.trim().isBlank()){
            throw new DatosIncompletosException("La contraseña es obligatoria.");
        }

        // Validación de contraseña corta (menor a 8 caracteres)
        if(password.length() < 8){
            throw new DatosIncompletosException("La contraseña debe contener como mínimo 8 caracteres.");
        }

        // Validación de seguridad de la contraseña
        if (esPasswordInsegura(password)) {
            throw new PasswordInseguraException("La contraseña no cumple con los criterios de seguridad.");
        }

        return new Usuario(nombreCompleto, carrera, email, password);
    }

    //Nuevo metodo para rehidratar un usuario existente desde la base de datos
    //Este se usará cuando el usuario ya tiene un ID.
    public static Usuario instanciaExistente(
            Integer idUsuario, String nombreCompleto, Carrera carrera, String email, String password,
            Rol rol, Estado estado, boolean emailVerificado, String tokenVerificacion,
            String imagenPerfil, LocalDateTime fechaAlta, LocalDateTime fechaBaja){

        //Al rehidratar, el ID NO debe ser nulo.
        if (idUsuario == null) {
            throw new DatosIncompletosException("El ID de usuario es obligatorio para rehidratar una instancia existente.");
        }

        //Construir la instancia con los datos tal como vienen de la base de datos
        return new Usuario(
                idUsuario,
                nombreCompleto,
                carrera,
                email,
                emailVerificado,
                password,
                tokenVerificacion,
                rol,
                estado,
                imagenPerfil,
                fechaAlta,
                fechaBaja
        );
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {return nombreCompleto;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {    }

    public String getEmail() {
        return email;
    }

    public boolean getEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
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

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setTokenVerificacion(String tokenVerificacion) {
        this.tokenVerificacion = tokenVerificacion;
    }

    public void setImagenPerfil(String imagenPerfil) {
        // Aquí podrías añadir validaciones si la URL debe tener un formato específico
        this.imagenPerfil = imagenPerfil;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    private static boolean esPasswordInsegura(String password) {
        // Si contiene espacios, ya es insegura
        if (password.contains(" ")) {
            return true;
        }
        // Validaciones individuales
        boolean tieneMayuscula = password.matches(".*[A-Z].*");
        boolean tieneMinuscula = password.matches(".*[a-z].*");
        boolean tieneNumero = password.matches(".*\\d.*");
        boolean tieneEspecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
        // Si falta alguno de los requerimientos → es insegura
        if (!tieneMayuscula || !tieneMinuscula || !tieneNumero || !tieneEspecial) {
            return true;
        }
        // Si pasó todos los filtros → es segura
        return false;
    }


}
