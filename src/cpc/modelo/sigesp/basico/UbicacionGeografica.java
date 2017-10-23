package cpc.modelo.sigesp.basico;



import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.indice.ParroquiaPK;


@Audited @Entity

@Table(name="sigesp_parroquia", schema = "sigesp")
@IdClass(ParroquiaPK.class)
public class UbicacionGeografica implements Serializable {
	
	private static final long serialVersionUID = 1758455238010003479L;
	
	private	String		codigoMunicipio; 
	private String 		pais;
	private String 		codigoEstado;
	private String		codigoParroquia;
	private String		descripcion;
	private Municipio	municipio;
	
	
	@Column(name="denpar")	
	private String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Id
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}
	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
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
	
	@Id
	public String getCodigoParroquia() {
		return codigoParroquia;
	}
	public void setCodigoParroquia(String codigoParroquia) {
		this.codigoParroquia = codigoParroquia;
	}

	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({
	    @JoinColumn(name="codpai", referencedColumnName="codpai", insertable=false, updatable=false),
	    @JoinColumn(name="codest", referencedColumnName="codest", insertable=false, updatable=false),
	    @JoinColumn(name="codmun", referencedColumnName="codmun", insertable=false, updatable=false ),
	})	
	public Municipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
	@Transient
	public String getDescripcionEstado(){
		return getMunicipio().getEstado().getDescripcion();
	}
	
	@Transient
	public String getDescripcionParroquia(){
		 return getDescripcion();
	}
	
	@Transient
	public String getDescripcionMunicipio(){
		return getMunicipio().getDescripcion();
	}
	
	@Transient
	public String getDescripcionUbicacion(){
		return getDescripcionEstado() + ", "+ getDescripcionMunicipio()+ ", "+ getDescripcion();
	}
	
	public String toString(){
		return getDescripcionUbicacion();
	}
	
}
