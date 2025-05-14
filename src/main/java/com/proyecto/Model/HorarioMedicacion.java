package com.proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.LocalDate;

@Entity
@Table(name = "horarios_medicacion")
public class HorarioMedicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;



    private LocalTime hora;


    private LocalDate fecha;


    private String frecuencia; // Diario, cada 8h, etc.

    public HorarioMedicacion() {}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Usuario getusuario() {return usuario;}

    public void setusuario(Usuario usuario) {this.usuario = usuario;}

    public LocalTime getHora() {return hora;}

    public void setHora(LocalTime hora) {this.hora = hora;}

    public Medicamento getMedicamento() {return medicamento;}

    public void setMedicamento(Medicamento medicamento) {this.medicamento = medicamento;}

    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public String getFrecuencia() {return frecuencia;}

    public void setFrecuencia(String frecuencia) {this.frecuencia = frecuencia;}
}
