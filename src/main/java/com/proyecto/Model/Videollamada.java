package com.proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "videollamadas")
public class Videollamada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con Médico
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "duracion") // minutos
    private Integer duracion;

    @Column(name = "estado") // ejemplo: "EN_CURSO", "FINALIZADA"
    private String estado;

    // Getters y setters

    public Long getId() {return id;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public Medico getMedico() {return medico;}

    public void setMedico(Medico medico) {this.medico = medico;}

    public LocalDateTime getFechaInicio() {return fechaInicio;}

    public void setFechaInicio(LocalDateTime fechaInicio) {this.fechaInicio = fechaInicio;}

    public LocalDateTime getFechaFin() {return fechaFin;}

    public void setFechaFin(LocalDateTime fechaFin) {this.fechaFin = fechaFin;}

    public Integer getDuracion() {return duracion;}

    public void setDuracion(Integer duracion) {this.duracion = duracion;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}
}
