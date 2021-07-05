/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo.pedidos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joramos
 */
@Entity
@Table(name = "pedidos")//, catalog = "springmvc", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidos.findAll", query = "SELECT p FROM Pedidos p")
    , @NamedQuery(name = "Pedidos.findById", query = "SELECT p FROM Pedidos p WHERE p.id = :id")
    , @NamedQuery(name = "Pedidos.findByCorreo", query = "SELECT p FROM Pedidos p WHERE p.correo = :correo")
    , @NamedQuery(name = "Pedidos.findByEstado", query = "SELECT p FROM Pedidos p WHERE p.estado = :estado")
    , @NamedQuery(name = "Pedidos.findByNombrecliente", query = "SELECT p FROM Pedidos p WHERE p.nombrecliente = :nombrecliente")
    , @NamedQuery(name = "Pedidos.findByRnt", query = "SELECT p FROM Pedidos p WHERE p.rnt = :rnt")
    , @NamedQuery(name = "Pedidos.findByTelefono", query = "SELECT p FROM Pedidos p WHERE p.telefono = :telefono")})
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "correo", length = 255)
    private String correo;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "nombrecliente", length = 255)
    private String nombrecliente;
    @Lob
    @Column(name = "productos", length = 65535)
    private String productos;
    @Column(name = "rnt", length = 255)
    private String rnt;
    @Column(name = "telefono", length = 255)
    private String telefono;
    @Column(name = "latitud", length = 45)
    private String latitud;
    @Column(name = "longitud", length = 45)
    private String longitud;
    
    public Pedidos() {
    }

    public Pedidos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getRnt() {
        return rnt;
    }

    public void setRnt(String rnt) {
        this.rnt = rnt;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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
        if (!(object instanceof Pedidos)) {
            return false;
        }
        Pedidos other = (Pedidos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.modelo.pedidos.Pedidos[ id=" + id + " ]";
    }

    
}
