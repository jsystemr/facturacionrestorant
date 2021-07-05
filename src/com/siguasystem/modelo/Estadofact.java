/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "estadofact")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadofact.findAll", query = "SELECT e FROM Estadofact e")
    , @NamedQuery(name = "Estadofact.findByIdestadofact", query = "SELECT e FROM Estadofact e WHERE e.idestadofact = :idestadofact")
    , @NamedQuery(name = "Estadofact.findByDescrip", query = "SELECT e FROM Estadofact e WHERE e.descrip = :descrip")})
public class Estadofact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idestadofact", nullable = false)
    private Integer idestadofact;
    @Column(name = "descrip", length = 45)
    private String descrip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadofact", fetch = FetchType.LAZY)
    private List<Factura> facturaList;

    public Estadofact() {
    }

    public Estadofact(Integer idestadofact) {
        this.idestadofact = idestadofact;
    }

    public Integer getIdestadofact() {
        return idestadofact;
    }

    public void setIdestadofact(Integer idestadofact) {
        this.idestadofact = idestadofact;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
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
        hash += (idestadofact != null ? idestadofact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadofact)) {
            return false;
        }
        Estadofact other = (Estadofact) object;
        if ((this.idestadofact == null && other.idestadofact != null) || (this.idestadofact != null && !this.idestadofact.equals(other.idestadofact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descrip;
    }
    
}
