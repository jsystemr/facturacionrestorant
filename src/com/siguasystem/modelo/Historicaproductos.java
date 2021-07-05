/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jramos
 */
@Entity
@Table(name = "historicaproductos")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historicaproductos.findAll", query = "SELECT h FROM Historicaproductos h")
    , @NamedQuery(name = "Historicaproductos.findByFecha", query = "SELECT h FROM Historicaproductos h WHERE h.fecha = :fecha")
    , @NamedQuery(name = "Historicaproductos.findByProducto", query = "SELECT h FROM Historicaproductos h WHERE h.producto = :producto")
    , @NamedQuery(name = "Historicaproductos.findByCantidad", query = "SELECT h FROM Historicaproductos h WHERE h.cantidad = :cantidad")
    , @NamedQuery(name = "Historicaproductos.findByDescripcion", query = "SELECT h FROM Historicaproductos h WHERE h.descripcion = :descripcion")
    , @NamedQuery(name = "Historicaproductos.findByEstado", query = "SELECT h FROM Historicaproductos h WHERE h.estado = :estado")
    , @NamedQuery(name = "Historicaproductos.findByTipo", query = "SELECT h FROM Historicaproductos h WHERE h.tipo = :tipo")
    , @NamedQuery(name = "Historicaproductos.findById", query = "SELECT h FROM Historicaproductos h WHERE h.id = :id")})
public class Historicaproductos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "producto", nullable = false, length = 25)
    private String producto;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false, length = 45)
    private String cantidad;
    @Column(name = "descripcion", length = 45)
    private String descripcion;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "tipo", length = 25)
    private String tipo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Historicaproductos() {
    }

    public Historicaproductos(Integer id) {
        this.id = id;
    }

    public Historicaproductos(Integer id, Date fecha, String producto, String cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Historicaproductos)) {
            return false;
        }
        Historicaproductos other = (Historicaproductos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.modelo.Historicaproductos[ id=" + id + " ]";
    }
    
}
