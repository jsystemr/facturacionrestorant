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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jramos
 */
@Embeddable
public class DetallefacturaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "nrolinea", nullable = false)
    private int nrolinea;
    @Basic(optional = false)
    @Column(name = "idFactura", nullable = false)
    private int idFactura;
    @Basic(optional = false)
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public DetallefacturaPK() {
    }
 public DetallefacturaPK(int idFactura, Date fecha) {
        this.idFactura = idFactura;
        this.fecha = fecha;
    }
    
    public DetallefacturaPK(int nrolinea, int idFactura, Date fecha) {
        this.nrolinea = nrolinea;
        this.idFactura = idFactura;
        this.fecha = fecha;
    }

    public int getNrolinea() {
        return nrolinea;
    }

    public void setNrolinea(int nrolinea) {
        this.nrolinea = nrolinea;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nrolinea;
        hash += (int) idFactura;
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallefacturaPK)) {
            return false;
        }
        DetallefacturaPK other = (DetallefacturaPK) object;
        if (this.nrolinea != other.nrolinea) {
            return false;
        }
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
        return "com.siguasystem.modelo.DetallefacturaPK[ nrolinea=" + nrolinea + ", idFactura=" + idFactura + ", fecha=" + fecha + " ]";
    }
    
}
