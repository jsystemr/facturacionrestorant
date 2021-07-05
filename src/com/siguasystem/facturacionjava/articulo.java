/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import java.math.BigDecimal;

/**
 *
 * @author juan
 */
public class articulo {

    private int id;
    private String codigo;
    private String nombre;
    private double cantidad;
    private BigDecimal precio;
    private boolean isv;
    private double conversion;
    private String comentarios;
    private String estado;
    private Double subtotal;
    private Double descuento;
    private Double valisv15;

    public articulo() {
    }

    public articulo(String codigo, String nombre, double cantidad, BigDecimal precio, boolean isv) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.isv = isv;
    }

    public Double getSubtotal() {
        /* if (this.isIsv()) {
            this.subtotal = ((getCantidad() * getPrecio()) - getDescuento()) + (getValisv());
        } else {
            this.subtotal = (getCantidad() * getPrecio()) - getDescuento();
        }*/
        return subtotal;
    }

    public void setSubtotal(Double s) {
        this.subtotal = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return this.precio = precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isIsv() {
        return isv;
    }

    public void setIsv(boolean isv) {
        this.isv = isv;
    }

    public double getConversion() {
        return conversion;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getValisv() {
        if (isIsv()) {
            return this.valisv15;//(this.cantidad * this.precio) * 0.15;
        } else {
            return 0.0;
        }
    }

    public Double getValisv2() {
        if (isIsv()) {
            return this.valisv15;
        } else {
            return 0.0;
        }
    }

    public void setValisv(Double valisv) {
        this.valisv15 = valisv;
    }

}
