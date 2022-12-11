package com.example.puntoequilibrio.dto;

import java.io.Serializable;

public class UsuarioDto implements Serializable {

    public String ruc;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String empresa;
    public int dni;
    public String rol;
    public String correo;
    public Boolean habilitado;
    public String uidUser;
    public String photoUser;


    public UsuarioDto(){}

    public UsuarioDto(String ruc, String nombre, String apellidoPaterno, String apellidoMaterno, int dni, String rol, Boolean habilitado,String correo,String empresa,String uidUser,String photoUser) {
        this.ruc = ruc;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.dni = dni;
        this.rol = rol;
        this.habilitado = habilitado;
        this.correo = correo;
        this.empresa=empresa;
        this.uidUser=uidUser;
        this.photoUser=photoUser;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }
}
