package com.proyecto.Model;

import jakarta.validation.constraints.*;

public class registerDTO {

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String dni;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 2, message = "La contraseña debe tener al menos 2 caracteres")
    private String password;

    @NotEmpty
    private String confirmpassword;

    public @NotEmpty String getNombre() {
        return nombre;
    }

    public void setNombre(@NotEmpty String nombre) {
        this.nombre = nombre;
    }

    public @NotEmpty String getDni() {
        return dni;
    }

    public void setDni(@NotEmpty String dni) {
        this.dni = dni;
    }

    public @NotEmpty @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email String email) {
        this.email = email;
    }

    public @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
        this.password = password;
    }

    public @NotEmpty String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(@NotEmpty String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
