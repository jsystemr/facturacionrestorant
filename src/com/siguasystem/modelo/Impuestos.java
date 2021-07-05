/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author jramos
 */
@Entity
@Table(name = "impuestos")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Impuestos.findAll", query = "SELECT i FROM Impuestos i")
    , @NamedQuery(name = "Impuestos.findById", query = "SELECT i FROM Impuestos i WHERE i.id = :id")
    , @NamedQuery(name = "Impuestos.findByImpuesto", query = "SELECT i FROM Impuestos i WHERE i.impuesto = :impuesto")
    , @NamedQuery(name = "Impuestos.findByDescripcion", query = "SELECT i FROM Impuestos i WHERE i.descripcion = :descripcion")})
public class Impuestos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "impuesto", precision = 15, scale = 2)
    private BigDecimal impuesto;
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    public Impuestos() {
    }

    public Impuestos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Impuestos)) {
            return false;
        }
        Impuestos other = (Impuestos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  ""+impuesto;
    }
    
}
