package com.proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    private String mensaje;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime fechaProgramada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNotificacion estado;

    @Column(nullable = true, length = 50)
    private String tipo;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;


    public enum EstadoNotificacion {
        PENDIENTE, ENVIADA, FALLIDA
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public String getMensaje() {return mensaje;}

    public void setMensaje(String mensaje) {this.mensaje = mensaje;}

    public LocalDateTime getFechaProgramada() {return fechaProgramada;}

    public void setFechaProgramada(LocalDateTime fechaProgramada) {this.fechaProgramada = fechaProgramada;}

    public EstadoNotificacion getEstado() {return estado;}

    public void setEstado(EstadoNotificacion estado) {this.estado = estado;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
