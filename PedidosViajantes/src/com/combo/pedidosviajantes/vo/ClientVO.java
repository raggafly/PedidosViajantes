package com.combo.pedidosviajantes.vo;

public class ClientVO {
	String codCompany;
	String name;
	String address;
	String phone;
	String town;
	String codPostal ;
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getCodCompany() {
		return codCompany;
	}
	public void setCodCompany(String codCompany) {
		this.codCompany = codCompany;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	
	
}
