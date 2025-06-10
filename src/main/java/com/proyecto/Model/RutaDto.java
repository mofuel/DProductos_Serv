package com.proyecto.Model;

import java.util.List;

public class RutaDto {
    private Integer startRow;
    private Integer startCol;
    private Integer endRow;
    private Integer endCol;
    private Usuario usuario;

    private Integer totalDistance;

    private List<RouteStepDto> steps;


    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getStartCol() {
        return startCol;
    }

    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Integer getEndCol() {
        return endCol;
    }

    public void setEndCol(Integer endCol) {
        this.endCol = endCol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Integer totalDistance) {
        this.totalDistance = totalDistance;
    }

    public List<RouteStepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<RouteStepDto> steps) {
        this.steps = steps;
    }
}
