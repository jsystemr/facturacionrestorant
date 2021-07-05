/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;
import java.util.Date;
import java.util.*;

/**
 *
 * @author juank
 */
public class detallefactura {
    private int correla=0;
    private ArrayList<String> cuenta=new ArrayList<String>();
    private long fecha=new Date().getTime();
    //La lista de productos no puede ir se deja por ejemplo
    // ya que por cada detalle existe un producto
    // y no para un detalle vaios productos
     private ArrayList<articulo> larticulos=new ArrayList<articulo>();
    //Esta es la variable correcta
    //por cada linea un producto
  
    public detallefactura(){}
    
  
    
    /**
     * @return the correla
     */
    public int getCorrela() {
        return correla;
    }
    
    
    /**
     * @param correla the correla to set
     */
    public void setCorrela(int correla) {
        this.correla = correla;
    }

    /**
     * @return the fecha
     */
    public long getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the lproductos
     */
      public ArrayList<articulo> getLParticulos() {
        return larticulos;
    }

    
    /**
     * @param lproductos the lproductos to set
     */
    
    public void addProductos(articulo p) {
        contar();
        p.setId(this.correla);
        this.larticulos.add(p);
    }

     public void addPArticulos(articulo a) {
         if (a!=null) {
            contar();
        a.setId(this.correla);
        this.larticulos.add(a); 
         }
        
    }

    private void contar() {
        this.correla++;
        this.setCuenta(""+this.correla);
    }

    /**
     * @return the cuenta
     */
    public ArrayList<String> getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta.add(cuenta);
    }
        
     public double totalarticulos()
    {
        double total=0;
        for (articulo p : this.getLParticulos()) {
            total+=p.getSubtotal();
        }
        return total;
    }
     
     public double totalarticulosISV()
    {
        double total=0;
        for (articulo p : this.getLParticulos()) {
            total+=p.getSubtotal();
        }
        return total;
    }
    
     public void delPArticulos(int a) {
        //a.setId(this.correla);
        this.larticulos.remove(a);
    }
}
