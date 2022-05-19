package com.usuarios.api.dto;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "phone")
@Embeddable
public class Phone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPhone;
	private long number;
	private long citiCode;
	private long countryCode;
	
	@ManyToOne
    @JoinColumn(name = "id_usuario")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Usuario usuario;
	
	public long getIdPhone() {
		return idPhone;
	}
	public void setIdPhone(long idPhone) {
		this.idPhone = idPhone;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public long getCitiCode() {
		return citiCode;
	}
	public void setCitiCode(long citiCode) {
		this.citiCode = citiCode;
	}
	public long getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(long countryCode) {
		this.countryCode = countryCode;
	}
	public void agregarUsuario(Usuario user)
	{
	this.usuario=user;
	}
	
	
}
