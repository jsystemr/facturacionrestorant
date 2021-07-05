/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "categoriaproducto")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriaproducto.findAll", query = "SELECT c FROM Categoriaproducto c")
    , @NamedQuery(name = "Categoriaproducto.findByIdCategoria", query = "SELECT c FROM Categoriaproducto c WHERE c.idCategoria = :idCategoria")
    , @NamedQuery(name = "Categoriaproducto.findByDescrip", query = "SELECT c FROM Categoriaproducto c WHERE c.descrip = :descrip")})
public class Categoriaproducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idCategoria", nullable = false, length = 8)
    private String idCategoria;
    @Column(name = "descrip", length = 100)
    private String descrip;

    public Categoriaproducto() {
    }

    public Categoriaproducto(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriaproducto)) {
            return false;
        }
        Categoriaproducto other = (Categoriaproducto) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descrip;
    }
    
}
