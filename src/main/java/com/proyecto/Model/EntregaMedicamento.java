package com.proyecto.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas_medicamentos")
public class EntregaMedicamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "horario_id", nullable = false)
    private HorarioMedicacion horarioMedicacion;

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEntrega;


    private Boolean entregado = false;


    private Boolean confirmado = false;


    private String observaciones;

    public EntregaMedicamento(){}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public HorarioMedicacion getHorarioMedicacion() {return horarioMedicacion;}

    public void setHorarioMedicacion(HorarioMedicacion horarioMedicacion) {this.horarioMedicacion = horarioMedicacion;}

    public LocalDateTime getFechaEntrega() {return fechaEntrega;}

    public void setFechaEntrega(LocalDateTime fechaEntrega) {this.fechaEntrega = fechaEntrega;}

    public Boolean getEntregado() {return entregado;}

    public void setEntregado(Boolean entregado) {this.entregado = entregado;}

    public Boolean getConfirmado() {return confirmado;}

    public void setConfirmado(Boolean confirmado) {this.confirmado = confirmado;}

    public String getObservaciones() {return observaciones;}

    public void setObservaciones(String observaciones) {this.observaciones = observaciones;}
}
