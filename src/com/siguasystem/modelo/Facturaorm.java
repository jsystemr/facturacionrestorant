/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "factura")
public class Facturaorm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "idfactura")
    @Id
    private Integer idfactura;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "efectivo", precision = 10, scale = 2)
    private BigDecimal efectivo;
    @Column(name = "notas", length = 255)
    private String notas;
    @Column(name = "nrotarjeta")
    private Integer nrotarjeta;
    @Column(name = "totalfac", precision = 15, scale = 2)
    private BigDecimal totalfac;
    @JoinColumn(name = "idorden", referencedColumnName = "idtiporden")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tiporden idorden;
    @JoinColumn(name = "tipofact", referencedColumnName = "idTipoFactura")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipofactura tipofact;
    @JoinColumn(name = "cliente", referencedColumnName = "idCliente", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cliente cliente;
    @JoinColumn(name = "empresa", referencedColumnName = "idEmpresa", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Empresa empresa;
    @JoinColumn(name = "estadofact", referencedColumnName = "idestadofact", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Estadofact estadofact;
    @JoinColumn(name = "usuario", referencedColumnName = "login", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Usuario usuario;

    public Facturaorm() {
    }

    public Integer getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getNrotarjeta() {
        return nrotarjeta;
    }

    public void setNrotarjeta(Integer nrotarjeta) {
        this.nrotarjeta = nrotarjeta;
    }

    public BigDecimal getTotalfac() {
        return totalfac;
    }

    public void setTotalfac(BigDecimal totalfac) {
        this.totalfac = totalfac;
    }

    public Tiporden getIdorden() {
        return idorden;
    }

    public void setIdorden(Tiporden idorden) {
        this.idorden = idorden;
    }

    public Tipofactura getTipofact() {
        return tipofact;
    }

    public void setTipofact(Tipofactura tipofact) {
        this.tipofact = tipofact;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Estadofact getEstadofact() {
        return estadofact;
    }

    public void setEstadofact(Estadofact estadofact) {
        this.estadofact = estadofact;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return " Factura=" + getIdfactura() + " Fecha=" + getFecha() + " Cliente=" + getCliente() + " Total=" + getTotalfac();
    }

}
