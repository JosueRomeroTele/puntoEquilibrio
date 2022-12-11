package com.example.puntoequilibrio.dto;

public class PuntoEquilibrioDto {

    String referencia;
    Integer cantidadMes;
    double participacion;
    double precio;
    double costoVariable;
    double margenDistribucion;

    double margenPonderado;
    Integer ptoEquilibrioCantidad;
    double ptEquilibrioMonto;
    double costoVariableTotal;
    String uidUser;

    public PuntoEquilibrioDto(){}

    public PuntoEquilibrioDto(String referencia, Integer cantidadMes, double participacion, double precio, double costoVariable, double margenDistribucion, double margenPonderado, Integer ptoEquilibrioCantidad, double ptEquilibrioMonto, double costoVariableTotal,String uidUser) {
        this.referencia = referencia;
        this.cantidadMes = cantidadMes;
        this.participacion = participacion;
        this.precio = precio;
        this.costoVariable = costoVariable;
        this.margenDistribucion = margenDistribucion;
        this.margenPonderado = margenPonderado;
        this.ptoEquilibrioCantidad = ptoEquilibrioCantidad;
        this.ptEquilibrioMonto = ptEquilibrioMonto;
        this.costoVariableTotal = costoVariableTotal;
        this.uidUser = uidUser;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getCantidadMes() {
        return cantidadMes;
    }

    public void setCantidadMes(Integer cantidadMes) {
        this.cantidadMes = cantidadMes;
    }

    public double getParticipacion() {
        return participacion;
    }

    public void setParticipacion(double participacion) {
        this.participacion = participacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCostoVariable() {
        return costoVariable;
    }

    public void setCostoVariable(double costoVariable) {
        this.costoVariable = costoVariable;
    }

    public double getMargenDistribucion() {
        return margenDistribucion;
    }

    public void setMargenDistribucion(double margenDistribucion) {
        this.margenDistribucion = margenDistribucion;
    }

    public double getMargenPonderado() {
        return margenPonderado;
    }

    public void setMargenPonderado(double margenPonderado) {
        this.margenPonderado = margenPonderado;
    }

    public Integer getPtoEquilibrioCantidad() {
        return ptoEquilibrioCantidad;
    }

    public void setPtoEquilibrioCantidad(Integer ptoEquilibrioCantidad) {
        this.ptoEquilibrioCantidad = ptoEquilibrioCantidad;
    }

    public double getPtEquilibrioMonto() {
        return ptEquilibrioMonto;
    }

    public void setPtEquilibrioMonto(double ptEquilibrioMonto) {
        this.ptEquilibrioMonto = ptEquilibrioMonto;
    }

    public double getCostoVariableTotal() {
        return costoVariableTotal;
    }

    public void setCostoVariableTotal(double costoVariableTotal) {
        this.costoVariableTotal = costoVariableTotal;
    }
}
