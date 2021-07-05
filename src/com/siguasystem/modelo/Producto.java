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
import javax.persistence.Entity;
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
@Table(name = "producto")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByCosto", query = "SELECT p FROM Producto p WHERE p.costo = :costo")
    , @NamedQuery(name = "Producto.findByCategoria", query = "SELECT p FROM Producto p WHERE p.categoria = :categoria")
    , @NamedQuery(name = "Producto.findByCantidadexis", query = "SELECT p FROM Producto p WHERE p.cantidadexis = :cantidadexis")
    , @NamedQuery(name = "Producto.findByImgruta", query = "SELECT p FROM Producto p WHERE p.imgruta = :imgruta")
    , @NamedQuery(name = "Producto.findByImpuesto", query = "SELECT p FROM Producto p WHERE p.impuesto = :impuesto")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idProducto", nullable = false, length = 8)
    private String idProducto;
    @Column(name = "nombre", length = 100)
    private String nombre;
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio", precision = 10, scale = 2)
    private Double precio;
    @Column(name = "costo", precision = 10, scale = 2)
    private Float costo;
    @Basic(optional = false)
    @Column(name = "categoria", nullable = false, length = 8)
    private String categoria;
    @Basic(optional = false)
    @Column(name = "cantidadexis", nullable = false)
    private int cantidadexis;
    @Basic(optional = false)
    @Column(name = "imgruta", nullable = false, length = 100)
    private String imgruta;
    @Basic(optional = false)
    @Column(name = "impuesto", nullable = false, precision = 5, scale = 2)
    private BigDecimal impuesto;

    public Producto() {
    }

    public Producto(String idProducto) {
        this.idProducto = idProducto;
    }
    
    public Producto(String idProducto, String categoria, int cantidadexis, String imgruta, BigDecimal impuesto) {
        this.idProducto = idProducto;
        this.categoria = categoria;
        this.cantidadexis = cantidadexis;
        this.imgruta = imgruta;
        this.impuesto = impuesto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidadexis() {
        return cantidadexis;
    }

    public void setCantidadexis(int cantidadexis) {
        this.cantidadexis = cantidadexis;
    }

    public String getImgruta() {
        return imgruta;
    }

    public void setImgruta(String imgruta) {
        this.imgruta = imgruta;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID:"+idProducto+":Prodcuto:"+nombre+":Precio:"+precio;
    }


}
