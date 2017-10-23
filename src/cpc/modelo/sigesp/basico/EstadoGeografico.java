package cpc.modelo.sigesp.basico;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cpc.modelo.sigesp.indice.EstadoGeograficoPK;


@Audited @Entity

@Table(name="sigesp_estados", schema="sigesp")
@IdClass(EstadoGeograficoPK.class)
public class EstadoGeografico implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3455085135908567880L;

	//private EstadoPK  id;
	
	private String 			 pais;
	private String 			 codigoEstado;
	private String 			 descripcion;
	private List<Municipio>  municipios;
	
	
	@Column(name="desest")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Id
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	@Id
	public String getCodigoEstado() {
		return codigoEstado;
	}
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="estado", targetEntity=Municipio.class)
	public List<Municipio> getMunicipios() {
		return municipios;
	}
	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}
	
	/*@EmbeddedId
	public EstadoPK getId() {
		return id;
	}
	public void setId(EstadoPK id) {
		this.id = id;
	}*/	
}