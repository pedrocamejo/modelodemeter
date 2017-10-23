package cpc.modelo.sigesp.basico;



import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import cpc.modelo.sigesp.indice.MunicipioPK;


@Audited @Entity
@Table(name="sigesp_municipio", schema="sigesp")
/*@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumns({
@PrimaryKeyJoinColumn(name="codpai", referencedColumnName="codpai"),
@PrimaryKeyJoinColumn(name="codest", referencedColumnName="codest")})*/
@IdClass(MunicipioPK.class)
public class Municipio  implements Serializable{

	private static final long serialVersionUID = 1558466359436011916L;

	private String 						pais;
	private String 						codigoEstado;
	private String 						codigoMunicipio;
	private String 						descripcion;
	private List<UbicacionGeografica> 	parroquias;
	private EstadoGeografico			estado;
	
	@Column(name="denmun")
	public String getDescripcion() {
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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="municipio", targetEntity=UbicacionGeografica.class)
	public List<UbicacionGeografica> getParroquias(){
		return parroquias;
	}
	public void setParroquias(List<UbicacionGeografica> parroquias) {
		this.parroquias = parroquias;
	}

	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({
	    @JoinColumn(name="codpai", referencedColumnName="codpai", insertable=false, updatable=false),
	    @JoinColumn(name="codest", referencedColumnName="codest", insertable=false, updatable=false),
	})	
	public EstadoGeografico getEstado() {
		return estado;
	}
	public void setEstado(EstadoGeografico estado) {
		this.estado = estado;
	}
	
}
