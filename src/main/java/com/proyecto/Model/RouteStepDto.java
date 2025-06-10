package com.proyecto.Model;

public class RouteStepDto {

    private Integer stepIndex;  // orden del paso en la ruta
    private Integer rowIdx;     // fila del paso
    private Integer colIdx;     // columna del paso

    public RouteStepDto() {
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public Integer getRowIdx() {
        return rowIdx;
    }

    public void setRowIdx(Integer rowIdx) {
        this.rowIdx = rowIdx;
    }

    public Integer getColIdx() {
        return colIdx;
    }

    public void setColIdx(Integer colIdx) {
        this.colIdx = colIdx;
    }
}
