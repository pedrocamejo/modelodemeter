package cpc.modelo.sigesp.basico;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.indice.UnidadAdministrativaPK;

import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="spg_ministerio_ua", schema="sigesp")

public class UnidadCentralizada implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1252746989175146857L;
	private  UnidadCentralizadaPK  id;
	private	 String nombre;
	
	
	
	@EmbeddedId
	public UnidadCentralizadaPK getId() {
		return id;
	}

	public void setId(UnidadCentralizadaPK id) {
		this.id = id;
	}
	
	@Column(name="denuac",insertable=false,updatable=false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return getNombre();
	}
	
	

}
