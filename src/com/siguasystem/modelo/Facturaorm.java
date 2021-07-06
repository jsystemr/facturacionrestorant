/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    private static final long serialVersionUID = 1L;
    @Column(name = "idfactura")
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Tiporden idorden;
    @JoinColumn(name = "tipofact", referencedColumnName = "idTipoFactura")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipofactura tipofact;
    @JoinColumn(name = "cliente", referencedColumnName = "idCliente", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;
    @JoinColumn(name = "empresa", referencedColumnName = "idEmpresa", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa empresa;
    @JoinColumn(name = "estadofact", referencedColumnName = "idestadofact", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estadofact estadofact;
    @JoinColumn(name = "usuario", referencedColumnName = "login", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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

    public Facturaorm(Integer id) {
        this.id = id;
    }

    public Facturaorm(Integer id, int idFactura, String cliente, String empresa, int estadofact, String usuario) {
        this.id = id;
        
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturaorm)) {
            return false;
        }
        Facturaorm other = (Facturaorm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


}
