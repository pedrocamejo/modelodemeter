package cpc.modelo.ministerio.gestion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.gestion.PrestamoActivo;

@Audited @Entity
@Table(name="tbl_dem_productorjuridico", schema="ministerio")
@PrimaryKeyJoinColumn(name="int_idproductor")
public class ProductorJuridico extends Productor{
	
	
	private static final long serialVersionUID = 2865965472734621041L;
	private List<Representante>		representantes;
	private Boolean					publico; 
	private String 					paginaWeb;
	private String 					email;
	private List<PrestamoActivo> 	activos;
	
	public ProductorJuridico(){
		
	}
	
	public ProductorJuridico(Productor padre){
		if (padre != null){
			setId(padre.getId()) ;
			setTipo(padre.getTipo());
			setIdentidadLegal(padre.getIdentidadLegal());		
			setNombres(padre.getNombres());			
			setActivo(padre.isActivo());
			setTelefonos(padre.getTelefonos());
			setDireccion(padre.getDireccion());
			setUnidadAsociado(padre.getUnidadAsociado());
			setOrganizado(padre.isOrganizado());
			setFechaIngreso(padre.getFechaIngreso());
			setFechaNacimiento(padre.getFechaNacimiento());  	
			setFinanciamientos(padre.getFinanciamientos());
			setOrganizacion(padre.getOrganizacion());	
			setUnidadesproduccion(padre.getUnidadesproduccion());
		}
	}
	@NotAudited
	@ManyToMany(cascade = CascadeType.ALL, mappedBy="productores", targetEntity=Representante.class,fetch=FetchType.EAGER)
	public List<Representante> getRepresentantes() {
		return representantes;
	}
	public void setRepresentantes(List<Representante> representantes) {
		this.representantes = representantes;
	}

	@Column(name="bol_publico")
	public Boolean getPublico() {
		return publico;
	}

	public void setPublico(Boolean publico) {
		this.publico = publico;
	}

	@Column(name="str_pagina")
	@Basic(optional=true)
	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	
	@Column(name="str_email")
	@Basic(optional=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToMany(mappedBy="enteJuridico", targetEntity= PrestamoActivo.class)
	public List<PrestamoActivo> getActivos() {
		return activos;
	}

	public void setActivos(List<PrestamoActivo> activos) {
		this.activos = activos;
	}

	@Transient
	public void addRepresentante(Representante representante){
		boolean valido = true;
		if (representantes== null)
			representantes = new ArrayList<Representante>();
		else{
			for (Representante item : representantes) {
				if (item != null)
					if (item.getId() == representante.getId()){
						valido =false;
						break;
					}
			}
		}
		if (valido){
			representantes.add(representante);
		}
	}
	
	
	

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductorJuridico other = (ProductorJuridico) obj;
		if (activos == null) {
			if (other.activos != null)
				return false;
		} else if (!activos.equals(other.activos))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (paginaWeb == null) {
			if (other.paginaWeb != null)
				return false;
		} else if (!paginaWeb.equals(other.paginaWeb))
			return false;
		if (publico == null) {
			if (other.publico != null)
				return false;
		} else if (!publico.equals(other.publico))
			return false;
		if (representantes == null) {
			if (other.representantes != null)
				return false;
		} else if (!representantes.equals(other.representantes))
			return false;
		return true;
	}

	public String toString() {
		return getNombres();
	}

}
