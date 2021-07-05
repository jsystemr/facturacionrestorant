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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joramos
 */
@Entity
@Table(name = "productochan")//, catalog = "springmvc", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productochan.findAll", query = "SELECT p FROM Productochan p")
    , @NamedQuery(name = "Productochan.findById", query = "SELECT p FROM Productochan p WHERE p.id = :id")
    , @NamedQuery(name = "Productochan.findByBodega", query = "SELECT p FROM Productochan p WHERE p.bodega = :bodega")
    , @NamedQuery(name = "Productochan.findByCantidad", query = "SELECT p FROM Productochan p WHERE p.cantidad = :cantidad")
    , @NamedQuery(name = "Productochan.findByCategoria", query = "SELECT p FROM Productochan p WHERE p.categoria = :categoria")
    , @NamedQuery(name = "Productochan.findByComentarios", query = "SELECT p FROM Productochan p WHERE p.comentarios = :comentarios")
    , @NamedQuery(name = "Productochan.findByDescripcion", query = "SELECT p FROM Productochan p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Productochan.findByEstado", query = "SELECT p FROM Productochan p WHERE p.estado = :estado")
    , @NamedQuery(name = "Productochan.findByFoto", query = "SELECT p FROM Productochan p WHERE p.foto = :foto")
    , @NamedQuery(name = "Productochan.findByIdproducto", query = "SELECT p FROM Productochan p WHERE p.idproducto = :idproducto")
    , @NamedQuery(name = "Productochan.findByIp", query = "SELECT p FROM Productochan p WHERE p.ip = :ip")
    , @NamedQuery(name = "Productochan.findByIsvval", query = "SELECT p FROM Productochan p WHERE p.isvval = :isvval")
    , @NamedQuery(name = "Productochan.findByNombre", query = "SELECT p FROM Productochan p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Productochan.findByPrecio", query = "SELECT p FROM Productochan p WHERE p.precio = :precio")
    , @NamedQuery(name = "Productochan.findBySelecc", query = "SELECT p FROM Productochan p WHERE p.selecc = :selecc")})
public class Productochan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "bodega", length = 255)
    private String bodega;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Column(name = "categoria", length = 255)
    private String categoria;
    @Column(name = "comentarios", length = 255)
    private String comentarios;
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "estado", nullable = false)
    private boolean estado;
    @Column(name = "foto", length = 255)
    private String foto;
    @Column(name = "idproducto", length = 255)
    private String idproducto;
    @Column(name = "ip", length = 255)
    private String ip;
    @Basic(optional = false)
    @Column(name = "isvval", nullable = false)
    private double isvval;
    @Column(name = "nombre", length = 255)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "selecc", length = 255)
    private String selecc;

    public Productochan() {
    }

    public Productochan(Long id) {
        this.id = id;
    }

    public Productochan(Long id, int cantidad, boolean estado, double isvval, double precio) {
        this.id = id;
        this.cantidad = cantidad;
        this.estado = estado;
        this.isvval = isvval;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getIsvval() {
        return isvval;
    }

    public void setIsvval(double isvval) {
        this.isvval = isvval;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getSelecc() {
        return selecc;
    }

    public void setSelecc(String selecc) {
        this.selecc = selecc;
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
        if (!(object instanceof Productochan)) {
            return false;
        }
        Productochan other = (Productochan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.modelo.pedidos.Productochan[ id=" + id + " ]";
    }
    
}
