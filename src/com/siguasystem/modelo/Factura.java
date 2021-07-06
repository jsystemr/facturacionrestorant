/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.modelo;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jramos
 */
@Entity
@Table(name = "factura")//, catalog = "bdrestaurante", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findByIdFactura", query = "SELECT f FROM Factura f WHERE f.idFactura = :idFactura")
    , @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha")
    , @NamedQuery(name = "Factura.findByHora", query = "SELECT f FROM Factura f WHERE f.hora = :hora")
    , @NamedQuery(name = "Factura.findByEfectivo", query = "SELECT f FROM Factura f WHERE f.efectivo = :efectivo")
    , @NamedQuery(name = "Factura.findByNotas", query = "SELECT f FROM Factura f WHERE f.notas = :notas")
    , @NamedQuery(name = "Factura.findByNrotarjeta", query = "SELECT f FROM Factura f WHERE f.nrotarjeta = :nrotarjeta")
    , @NamedQuery(name = "Factura.findByTotalfac", query = "SELECT f FROM Factura f WHERE f.totalfac = :totalfac")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "efectivo", precision = 10, scale = 2)
    private BigDecimal efectivo;
    @Column(name = "notas", length = 255)
    private String notas;
    @Column(name = "nrotarjeta")
    private Integer nrotarjeta;
    @Column(name = "totalfac", precision = 15, scale = 2)
    private BigDecimal totalfac;
    @JoinColumn(name = "idorden", referencedColumnName = "idtiporden")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tiporden idorden;
    @JoinColumn(name = "tipofact", referencedColumnName = "idTipoFactura")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tipofactura tipofact;
    @JoinColumn(name = "cliente", referencedColumnName = "idCliente", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;
    @JoinColumn(name = "empresa", referencedColumnName = "idEmpresa", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa empresa;
    @JoinColumn(name = "estadofact", referencedColumnName = "idestadofact", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estadofact estadofact;
    @JoinColumn(name = "usuario", referencedColumnName = "login", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;
    @Column(name = "idcorrelativo")
    private Integer idcorrelativo;

    public Factura() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
         Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public Factura(FacturaPK facturaPK) {
        this.fecha = facturaPK.getFecha();
        this.idFactura=facturaPK.getIdFactura();
    }

    public Factura(int idFactura, Date fecha) {
       this.fecha = fecha;
        this.idFactura=idFactura;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getNrotarjeta() {
        return nrotarjeta;
    }

    public void setNrotarjeta(Integer nrotarjeta) {
        this.nrotarjeta = nrotarjeta;
    }

    public BigDecimal getTotalfac() {
        return totalfac;
    }

    public void setTotalfac(BigDecimal totalfac) {
        this.totalfac = totalfac;
    }

    public Tiporden getIdorden() {
        return idorden;
    }

    public void setIdorden(Tiporden idorden) {
        this.idorden = idorden;
    }

    public Tipofactura getTipofact() {
        return tipofact;
    }

    public void setTipofact(Tipofactura tipofact) {
        this.tipofact = tipofact;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Estadofact getEstadofact() {
        return estadofact;
    }

    public void setEstadofact(Estadofact estadofact) {
        this.estadofact = estadofact;
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

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdcorrelativo() {
        return idcorrelativo;
    }

    public void setIdcorrelativo(Integer idcorrelativo) {
        this.idcorrelativo = idcorrelativo;
    }


    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;

        return true;
    }

}
