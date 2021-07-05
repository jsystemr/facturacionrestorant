/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloold;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jramos
 */
@Entity
@Table(name = "producto")//, catalog = "facturacion", schema = "")
@XmlRootElement

public class Productoold implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codpro", nullable = false, length = 8)
    private String idProducto;
    @Column(name = "nompro", length = 100)
    private String nombre;
    @Column(name = "descripro", length = 200)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preciopro", precision = 10, scale = 2)
    private Double precio;
    @Column(name = "coduni", precision = 10, scale = 2)
    private Integer coduni;
    @Basic(optional = false)
    @Column(name = "idcatpro", nullable = false, length = 8)
    private String categoria;
    @Basic(optional = false)
    @Column(name = "stockpro", nullable = false)
    private int cantidadexis;
    @Basic(optional = false)
    @Column(name = "fechulticompro", nullable = false, length = 100)
    private String fecha;

    public Productoold() {
    }

    public Productoold(String idProducto) {
        this.idProducto = idProducto;
    }

    public Productoold(String idProducto, String categoria, int cantidadexis, String imgruta, BigDecimal impuesto) {
        this.idProducto = idProducto;
        this.categoria = categoria;
        this.cantidadexis = cantidadexis;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidadexis() {
        return cantidadexis;
    }

    public void setCantidadexis(int cantidadexis) {
        this.cantidadexis = cantidadexis;
    }

    public Integer getCoduni() {
        return coduni;
    }

    public void setCoduni(Integer coduni) {
        this.coduni = coduni;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productoold)) {
            return false;
        }
        Productoold other = (Productoold) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID:"+idProducto+":Prodcuto:"+nombre+":Precio:"+precio;
    }


}
