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
 * @author joramos
 */
@Entity
@Table(name = "configuracion")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
    , @NamedQuery(name = "Configuracion.findByConfigid", query = "SELECT c FROM Configuracion c WHERE c.configid = :configid")
    , @NamedQuery(name = "Configuracion.findByConfigdesc", query = "SELECT c FROM Configuracion c WHERE c.configdesc = :configdesc")
    , @NamedQuery(name = "Configuracion.findByConfigval", query = "SELECT c FROM Configuracion c WHERE c.configval = :configval")
    , @NamedQuery(name = "Configuracion.findByConfigstado", query = "SELECT c FROM Configuracion c WHERE c.configstado = :configstado")
    , @NamedQuery(name = "Configuracion.findByConfigfreg", query = "SELECT c FROM Configuracion c WHERE c.configfreg = :configfreg")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "configid", nullable = false)
    private Integer configid;
    @Basic(optional = false)
    @Column(name = "configdesc", nullable = false, length = 85)
    private String configdesc;
    @Basic(optional = false)
    @Column(name = "configval", nullable = false, length = 200)
    private String configval;
    @Basic(optional = false)
    @Column(name = "configstado", nullable = false)
    private int configstado;
    @Basic(optional = false)
    @Column(name = "configfreg", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date configfreg;

    public Configuracion() {
    }

    public Configuracion(Integer configid) {
        this.configid = configid;
    }

    public Configuracion(Integer configid, String configdesc, String configval, int configstado, Date configfreg) {
        this.configid = configid;
        this.configdesc = configdesc;
        this.configval = configval;
        this.configstado = configstado;
        this.configfreg = configfreg;
    }

    public Integer getConfigid() {
        return configid;
    }

    public void setConfigid(Integer configid) {
        this.configid = configid;
    }

    public String getConfigdesc() {
        return configdesc;
    }

    public void setConfigdesc(String configdesc) {
        this.configdesc = configdesc;
    }

    public String getConfigval() {
        return configval;
    }

    public void setConfigval(String configval) {
        this.configval = configval;
    }

    public int getConfigstado() {
        return configstado;
    }

    public void setConfigstado(int configstado) {
        this.configstado = configstado;
    }

    public Date getConfigfreg() {
        return configfreg;
    }

    public void setConfigfreg(Date configfreg) {
        this.configfreg = configfreg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configid != null ? configid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.configid == null && other.configid != null) || (this.configid != null && !this.configid.equals(other.configid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.modelo.Configuracion[ configid=" + configid + " ]";
    }
    
}
