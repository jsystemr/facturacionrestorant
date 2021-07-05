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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tipofactura")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipofactura.findAll", query = "SELECT t FROM Tipofactura t")
    , @NamedQuery(name = "Tipofactura.findByIdTipoFactura", query = "SELECT t FROM Tipofactura t WHERE t.idTipoFactura = :idTipoFactura")
    , @NamedQuery(name = "Tipofactura.findByDescripcion", query = "SELECT t FROM Tipofactura t WHERE t.descripcion = :descripcion")})
public class Tipofactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoFactura", nullable = false)
    private Integer idTipoFactura;
    @Column(name = "descripcion", length = 55)
    private String descripcion;
    @OneToMany(mappedBy = "tipofact", fetch = FetchType.LAZY)
    private List<Factura> facturaList;

    public Tipofactura() {
    }

    public Tipofactura(Integer idTipoFactura) {
        this.idTipoFactura = idTipoFactura;
    }

    public Integer getIdTipoFactura() {
        return idTipoFactura;
    }

    public void setIdTipoFactura(Integer idTipoFactura) {
        this.idTipoFactura = idTipoFactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idTipoFactura != null ? idTipoFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipofactura)) {
            return false;
        }
        Tipofactura other = (Tipofactura) object;
        if ((this.idTipoFactura == null && other.idTipoFactura != null) || (this.idTipoFactura != null && !this.idTipoFactura.equals(other.idTipoFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    
}
