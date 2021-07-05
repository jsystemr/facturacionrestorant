/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;

/**
 *
 * @author jramos
 */
@Entity
@Table(name = "correlativossar")
public class Correlativossar2 implements Serializable  {

    @Id
    private Integer id;
    private String fechacreacion;
    private Date fechaini;
    private Date fechafin;
    private int rangoini;
    private int rangofin;
    private String formatonumero;
    private String cai;
    private int alernfac;

    public Correlativossar2() {
    }

    public Correlativossar2(Integer id) {
        this.id = id;
    }

    public Correlativossar2(Integer id, String fechacreacion, int rangoini, int rangofin, String formatonumero, String cai,int ale) {
        this.id = id;
        this.fechacreacion = fechacreacion;
        this.rangoini = rangoini;
        this.rangofin = rangofin;
        this.formatonumero = formatonumero;
        this.cai = cai;
        this.alernfac=ale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        String oldFechacreacion = this.fechacreacion;
        this.fechacreacion = fechacreacion;
    }

    public Date getFechaini() {
        return fechaini;
    }

    public void setFechaini(Date fechaini) {
        Date oldFechaini = this.fechaini;
        this.fechaini = fechaini;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        Date oldFechafin = this.fechafin;
        this.fechafin = fechafin;
    }

    public int getRangoini() {
        return rangoini;
    }

    public void setRangoini(int rangoini) {
        int oldRangoini = this.rangoini;
        this.rangoini = rangoini;
    }

    public int getRangofin() {
        return rangofin;
    }

    public void setRangofin(int rangofin) {
        int oldRangofin = this.rangofin;
        this.rangofin = rangofin;

    }

    public String getFormatonumero() {
        return formatonumero;
    }

    public void setFormatonumero(String formatonumero) {
        String oldFormatonumero = this.formatonumero;
        this.formatonumero = formatonumero;
       
    }

    public String getCai() {
        return cai;
    }

    public void setCai(String cai) {
        String oldCai = this.cai;
        this.cai = cai;
     
    }

    public int getAlernfac() {
        return alernfac;
    }

    public void setAlernfac(int alernfac) {
        this.alernfac = alernfac;
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
        if (!(object instanceof Correlativossar)) {
            return false;
        }
        Correlativossar other = (Correlativossar) object;
        if ((this.id == null && other.getId() != null) || (this.id != null && !this.id.equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.facturacionjava.Correlativossar[ id=" + id + " ]";
    }

}
