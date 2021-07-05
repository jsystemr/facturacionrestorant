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
@Table(name = "correlativossar")//, catalog = "bdrestaurante", schema = "")
@NamedQueries({
    @NamedQuery(name = "Correlativossar.findAll", query = "SELECT c FROM Correlativossar c")
    , @NamedQuery(name = "Correlativossar.findById", query = "SELECT c FROM Correlativossar c WHERE c.id = :id")
    , @NamedQuery(name = "Correlativossar.findByFechacreacion", query = "SELECT c FROM Correlativossar c WHERE c.fechacreacion = :fechacreacion")
    , @NamedQuery(name = "Correlativossar.findByFechaini", query = "SELECT c FROM Correlativossar c WHERE c.fechaini = :fechaini")
    , @NamedQuery(name = "Correlativossar.findByFechafin", query = "SELECT c FROM Correlativossar c WHERE c.fechafin = :fechafin")
    , @NamedQuery(name = "Correlativossar.findByRangoini", query = "SELECT c FROM Correlativossar c WHERE c.rangoini = :rangoini")
    , @NamedQuery(name = "Correlativossar.findByRangofin", query = "SELECT c FROM Correlativossar c WHERE c.rangofin = :rangofin")
    , @NamedQuery(name = "Correlativossar.findByFormatonumero", query = "SELECT c FROM Correlativossar c WHERE c.formatonumero = :formatonumero")
    , @NamedQuery(name = "Correlativossar.findByCai", query = "SELECT c FROM Correlativossar c WHERE c.cai = :cai")})
public class Correlativossar implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    @Column(name = "fechaini")
    @Temporal(TemporalType.DATE)
    private Date fechaini;
    @Column(name = "fechafin")
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    @Basic(optional = false)
    @Column(name = "rangoini")
    private int rangoini;
    @Basic(optional = false)
    @Column(name = "rangofin")
    private int rangofin;
    @Basic(optional = false)
    @Column(name = "formatonumero")
    private String formatonumero;
    @Basic(optional = false)
    @Column(name = "cai")
    private String cai;
    @Basic(optional = false)
    @Column(name = "alernfac")
    private int alernfac;
    @Basic(optional = false)
    @Column(name = "estado")
    private int estado;

    public Correlativossar() {
    }

    public Correlativossar(Integer id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Correlativossar(Integer id, Date fechacreacion, int rangoini, int rangofin, String formatonumero, String cai) {
        this.id = id;
        this.fechacreacion = fechacreacion;
        this.rangoini = rangoini;
        this.rangofin = rangofin;
        this.formatonumero = formatonumero;
        this.cai = cai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        Date oldFechacreacion = this.fechacreacion;
        this.fechacreacion = fechacreacion;
        changeSupport.firePropertyChange("fechacreacion", oldFechacreacion, fechacreacion);
    }

    public Date getFechaini() {
        return fechaini;
    }

    public void setFechaini(Date fechaini) {
        Date oldFechaini = this.fechaini;
        this.fechaini = fechaini;
        changeSupport.firePropertyChange("fechaini", oldFechaini, fechaini);
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        Date oldFechafin = this.fechafin;
        this.fechafin = fechafin;
        changeSupport.firePropertyChange("fechafin", oldFechafin, fechafin);
    }

    public int getRangoini() {
        return rangoini;
    }

    public void setRangoini(int rangoini) {
        int oldRangoini = this.rangoini;
        this.rangoini = rangoini;
        changeSupport.firePropertyChange("rangoini", oldRangoini, rangoini);
    }

    public int getRangofin() {
        return rangofin;
    }

    public void setRangofin(int rangofin) {
        int oldRangofin = this.rangofin;
        this.rangofin = rangofin;
        changeSupport.firePropertyChange("rangofin", oldRangofin, rangofin);
    }

    public String getFormatonumero() {
        return formatonumero;
    }

    public void setFormatonumero(String formatonumero) {
        String oldFormatonumero = this.formatonumero;
        this.formatonumero = formatonumero;
        changeSupport.firePropertyChange("formatonumero", oldFormatonumero, formatonumero);
    }

    public String getCai() {
        return cai;
    }

    public void setCai(String cai) {
        String oldCai = this.cai;
        this.cai = cai;
        changeSupport.firePropertyChange("cai", oldCai, cai);
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.siguasystem.facturacionjava.Correlativossar[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
