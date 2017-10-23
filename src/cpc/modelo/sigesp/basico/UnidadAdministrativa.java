package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.sigesp.indice.UnidadAdministrativaPK;


@Audited @Entity
@Table(name="spg_unidadadministrativa", schema="sigesp")
//@IdClass(UnidadAdministrativaPK.class)
public class UnidadAdministrativa implements Serializable{

	private static final long serialVersionUID = 8654251968389655401L;
	/*private String		empresa;
	private String		sede;*/
	private UnidadAdministrativaPK	id;
	private String					nombre;
	
	public UnidadAdministrativa() {
		super();
	}
	
	/*public UnidadAdministrativa(String empresa, String sede, String nombre) {
		super();
		this.empresa = empresa;
		this.sede = sede;
		this.nombre = nombre;
	}
	
	@Id
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	@Id
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}*/
	
	@EmbeddedId
	public UnidadAdministrativaPK getId() {
		return id;
	}

	public void setId(UnidadAdministrativaPK id) {
		this.id = id;
	}
	
	@Column(name="denuniadm",insertable=false,updatable=false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return getNombre();
	}
	
	@Transient
	public String getEmpresa(){
		return this.id.getEmpresa();
	}
	
	@Transient
	public String getSede(){
		return this.id.getSede();
	}

}
