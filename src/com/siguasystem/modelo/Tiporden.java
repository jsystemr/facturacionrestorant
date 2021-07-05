/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jramos
 */

@Entity
@Table(name = "tiporden")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiporden.findAll", query = "SELECT t FROM Tiporden t")
    , @NamedQuery(name = "Tiporden.findByIdtiporden", query = "SELECT t FROM Tiporden t WHERE t.idtiporden = :idtiporden")
    , @NamedQuery(name = "Tiporden.findByDescri", query = "SELECT t FROM Tiporden t WHERE t.descri = :descri")})

public class Tiporden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtiporden", nullable = false)
    private Integer idtiporden;
    @Column(name = "descri", length = 45)
    private String descri;
    @OneToMany(mappedBy = "idorden", fetch = FetchType.LAZY)
    private List<Factura> facturaList;

    public Tiporden() {
    }

    public Tiporden(Integer idtiporden) {
        this.idtiporden = idtiporden;
    }

    public Integer getIdtiporden() {
        return idtiporden;
    }

    public void setIdtiporden(Integer idtiporden) {
        this.idtiporden = idtiporden;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtiporden != null ? idtiporden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiporden)) {
            return false;
        }
        Tiporden other = (Tiporden) object;
        if ((this.idtiporden == null && other.idtiporden != null) || (this.idtiporden != null && !this.idtiporden.equals(other.idtiporden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descri;
    }
    
}
