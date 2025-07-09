package com.proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(length = 255)
    private String foto;

    // Getters y Setters

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public LocalTime getHoraInicio() {return horaInicio;}

    public void setHoraInicio(LocalTime horaInicio) {this.horaInicio = horaInicio;}

    public LocalTime getHoraFin() {return horaFin;}

    public void setHoraFin(LocalTime horaFin) {this.horaFin = horaFin;}

    public Boolean getDisponible() {return disponible;}

    public void setDisponible(Boolean disponible) {this.disponible = disponible;}

    public String getFoto() {return foto;}

    public void setFoto(String foto) {this.foto = foto;}
}
