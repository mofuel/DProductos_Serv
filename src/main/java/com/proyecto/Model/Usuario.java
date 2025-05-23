package com.proyecto.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;


    private String dni;


    private String correo;


    private String password;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public Usuario(){}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDni() {return dni;}

    public void setDni(String dni) {this.dni = dni;}

    public String getCorreo() {return correo;}

    public void setCorreo(String correo) {this.correo = correo;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
