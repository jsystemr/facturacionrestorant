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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jramos
 */
@Entity
@Table(name = "detallefactura")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detallefactura.findAll", query = "SELECT d FROM Detallefactura d")
    , @NamedQuery(name = "Detallefactura.findByNrolinea", query = "SELECT d FROM Detallefactura d WHERE d.detallefacturaPK.nrolinea = :nrolinea")
    , @NamedQuery(name = "Detallefactura.findByNomproducto", query = "SELECT d FROM Detallefactura d WHERE d.nomproducto = :nomproducto")
    , @NamedQuery(name = "Detallefactura.findByCantidad", query = "SELECT d FROM Detallefactura d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "Detallefactura.findByPrecio", query = "SELECT d FROM Detallefactura d WHERE d.precio = :precio")
    , @NamedQuery(name = "Detallefactura.findByComentarios", query = "SELECT d FROM Detallefactura d WHERE d.comentarios = :comentarios")
    , @NamedQuery(name = "Detallefactura.findByIdFactura", query = "SELECT d FROM Detallefactura d WHERE d.detallefacturaPK.idFactura = :idFactura")
    , @NamedQuery(name = "Detallefactura.findByFecha", query = "SELECT d FROM Detallefactura d WHERE d.detallefacturaPK.fecha = :fecha")
    , @NamedQuery(name = "Detallefactura.findByProducto", query = "SELECT d FROM Detallefactura d WHERE d.producto = :producto")
    , @NamedQuery(name = "Detallefactura.findByStadodet", query = "SELECT d FROM Detallefactura d WHERE d.stadodet = :stadodet")
    , @NamedQuery(name = "Detallefactura.findByDescuento", query = "SELECT d FROM Detallefactura d WHERE d.descuento = :descuento")
    , @NamedQuery(name = "Detallefactura.findByIsv", query = "SELECT d FROM Detallefactura d WHERE d.isv = :isv")})
public class Detallefactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetallefacturaPK detallefacturaPK;
    @Column(name = "nomproducto", length = 100)
    private String nomproducto;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
    @Column(name = "comentarios", length = 500)
    private String comentarios;
    @Basic(optional = false)
    @Column(name = "producto", nullable = false, length = 8)
    private String producto;
    @Column(name = "stadodet")
    private Integer stadodet;
    @Column(name = "descuento", precision = 10, scale = 2)
    private BigDecimal descuento;
    @Column(name = "isv", precision = 10, scale = 2)
    private BigDecimal isv;

    public Detallefactura() {
    }

    public Detallefactura(DetallefacturaPK detallefacturaPK) {
        this.detallefacturaPK = detallefacturaPK;
    }

    public Detallefactura(DetallefacturaPK detallefacturaPK, String producto) {
        this.detallefacturaPK = detallefacturaPK;
        this.producto = producto;
    }

    public Detallefactura(int nrolinea, int idFactura, Date fecha) {
        this.detallefacturaPK = new DetallefacturaPK(nrolinea, idFactura, fecha);
    }

    public DetallefacturaPK getDetallefacturaPK() {
        return detallefacturaPK;
    }

    public void setDetallefacturaPK(DetallefacturaPK detallefacturaPK) {
        this.detallefacturaPK = detallefacturaPK;
    }

    public String getNomproducto() {
        return nomproducto;
    }

    public void setNomproducto(String nomproducto) {
        this.nomproducto = nomproducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getStadodet() {
        return stadodet;
    }

    public void setStadodet(Integer stadodet) {
        this.stadodet = stadodet;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getIsv() {
        return isv;
    }

    public void setIsv(BigDecimal isv) {
        this.isv = isv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detallefacturaPK != null ? detallefacturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detallefactura)) {
            return false;
        }
        Detallefactura other = (Detallefactura) object;
        if ((this.detallefacturaPK == null && other.detallefacturaPK != null) || (this.detallefacturaPK != null && !this.detallefacturaPK.equals(other.detallefacturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.modelo.Detallefactura[ detallefacturaPK=" + detallefacturaPK + " ]";
    }
    
}
