package com.example.puntoequilibrio.dto;

import java.io.Serializable;

public class GastosDto implements Serializable {

        String gasto;
        String monto;
        String uid_Gastos;
        String uid_user;

    public GastosDto(){}

    public GastosDto(String gasto, String monto,String uid_Gastos,String uid_user) {
        this.gasto = gasto;
        this.monto = monto;
        this.uid_Gastos=uid_Gastos;
        this.uid_user = uid_user;
    }

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getUid_Gastos() {
        return uid_Gastos;
    }

    public void setUid_Gastos(String uid_Gastos) {
        this.uid_Gastos = uid_Gastos;
    }

    public String getGasto() {
        return gasto;
    }

    public void setGasto(String gasto) {
        this.gasto = gasto;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
