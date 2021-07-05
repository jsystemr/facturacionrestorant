package com.siguasystem.facturacionjava;

public class Pedidos {

	private Long id;
	
	private String nombrecliente;
	private String correo;
	private String rnt;
	private String telefono;
	private String productos;
	private boolean estado;
		
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long idped) {
		this.id = idped;
	}
	public String getNombrecliente() {
		return nombrecliente;
	}
	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getRnt() {
		return rnt;
	}
	public void setRnt(String rnt) {
		this.rnt = rnt;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getProductos() {
		return productos;
	}
	public void setProductos(String productos) {
		this.productos = productos;
	}
	
	

}
