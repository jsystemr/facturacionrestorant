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
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jramos
 */
@Embeddable
public class FacturaPK implements  Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idFactura", nullable = false)
    private int idFactura;
    @Basic(optional = false)
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;


    public FacturaPK() {
    }

    public FacturaPK(int idFactura, Date fecha,Integer id) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.id=id;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (int) idFactura;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaPK)) {
            return false;
        }
        FacturaPK other = (FacturaPK) object;
        if (this.idFactura != other.idFactura) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.FacturaPK[ idFactura=" + idFactura + ", fecha=" + fecha + " ]";
    }

    
}
