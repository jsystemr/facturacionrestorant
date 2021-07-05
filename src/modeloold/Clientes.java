/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloold;

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
@Table(name = "clientes")//, catalog = "facturacion", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByCodcli", query = "SELECT c FROM Clientes c WHERE c.codcli = :codcli")
    , @NamedQuery(name = "Clientes.findByNomcli", query = "SELECT c FROM Clientes c WHERE c.nomcli = :nomcli")
    , @NamedQuery(name = "Clientes.findByDircli", query = "SELECT c FROM Clientes c WHERE c.dircli = :dircli")
    , @NamedQuery(name = "Clientes.findByCelcli", query = "SELECT c FROM Clientes c WHERE c.celcli = :celcli")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codcli", nullable = false, length = 15)
    private String codcli;
    @Basic(optional = false)
    @Column(name = "nomcli", nullable = false, length = 55)
    private String nomcli;
    @Column(name = "dircli", length = 250)
    private String dircli;
    @Column(name = "celcli", length = 15)
    private String celcli;

    public Clientes() {
    }

    public Clientes(String codcli) {
        this.codcli = codcli;
    }

    public Clientes(String codcli, String nomcli) {
        this.codcli = codcli;
        this.nomcli = nomcli;
    }

    public String getCodcli() {
        return codcli;
    }

    public void setCodcli(String codcli) {
        this.codcli = codcli;
    }

    public String getNomcli() {
        return nomcli;
    }

    public void setNomcli(String nomcli) {
        this.nomcli = nomcli;
    }

    public String getDircli() {
        return dircli;
    }

    public void setDircli(String dircli) {
        this.dircli = dircli;
    }

    public String getCelcli() {
        return celcli;
    }

    public void setCelcli(String celcli) {
        this.celcli = celcli;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codcli != null ? codcli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.codcli == null && other.codcli != null) || (this.codcli != null && !this.codcli.equals(other.codcli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modeloold.Clientes[ codcli=" + codcli + " ]";
    }
    
}
