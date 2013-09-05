package com.combo.pedidosviajantes.vo;

public class ArticuloVO {
String descripcion;
Double precio;
String cantidad;
Double dto;
int idBorrar;
public int getIdBorrar() {
	return idBorrar;
}
public void setIdBorrar(int idBorrar) {
	this.idBorrar = idBorrar;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
@Override
public String toString() {
	return "ArticuloVO [descripcion=" + descripcion + ", precio=" + precio
			+ ", cantidad=" + cantidad + ", dto=" + dto + "]";
}
public Double getPrecio() {
	return precio;
}
public void setPrecio(Double precio) {
	this.precio = precio;
}
public String getCantidad() {
	return cantidad;
}
public void setCantidad(String cantidad) {
	this.cantidad = cantidad;
}
public Double getDto() {
	return dto;
}
public void setDto(Double dto) {
	this.dto = dto;
}
}
