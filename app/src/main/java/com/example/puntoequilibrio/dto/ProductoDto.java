package com.example.puntoequilibrio.dto;

import java.io.Serializable;

public class ProductoDto implements Serializable {

    public String referencia;
    public String imagenProducto;
    public int cantidad;
    public double precio;
    public double costoVariable;
    public String idProducto;
    public String uid_user;

    public ProductoDto(){

    }

    public ProductoDto(String idProducto, String referencia, String imagenProducto, int cantidad, double precio, double costoVariable,String uid_user) {
        this.idProducto= idProducto;
        this.referencia = referencia;
        this.imagenProducto = imagenProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.costoVariable = costoVariable;
        this.uid_user=uid_user;
    }

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
}
