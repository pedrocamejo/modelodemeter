package cpc.modelo.ministerio.dimension;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.TipoDocumentoTierra;
import cpc.modelo.ministerio.basico.CoordenadaGeografica;
import cpc.modelo.ministerio.basico.LinderoDireccion;
import cpc.modelo.ministerio.basico.TipoUbicacion;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.SuperficieUnidad;




@Audited @Entity
@Table(name="tbl_dem_direccion", schema = "ministerio")
public class UbicacionDireccion implements Serializable {
	
	private static final long serialVersionUID = 1758455238010003479L;
	
	private Integer						id;
	private String						descripcion;
	private String						referencia;
	private UbicacionSector				sector;
	private TipoUbicacion				tipoUbicacion;
	private TipoDocumentoTierra			tipoDocumentoTierra;
	private String						documentoLegal;
	private List<CoordenadaGeografica>	coordenadasGeograficas;
	private List<LinderoDireccion>		linderos;
	private List<SuperficieUnidad>		superficies;
	private List<Cliente>				propietarios;
	private Double						superficie;
	
	

	@Id
	@Column(name="seq_iddireccion")
	@SequenceGenerator(name="seqDireccion", sequenceName="ministerio.tbl_dem_direccion_seq_iddireccion_seq", allocationSize=1)
	@GeneratedValue(generator="seqDireccion")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="str_referencia")	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@ManyToOne
	@JoinColumn(name="int_idsector")
	public UbicacionSector getSector() {
		return sector;
	}

	public void setSector(UbicacionSector sector) {
		this.sector = sector;
	}

	@ManyToOne
	@JoinColumn(name="int_idtipoubicacion")
	public TipoUbicacion getTipoUbicacion() {
		return tipoUbicacion;
	}
	public void setTipoUbicacion(TipoUbicacion tipoUbicacion) {
		this.tipoUbicacion = tipoUbicacion;
	}
	
	@Column(name="str_documentolegal")
	public String getDocumentoLegal() {
		return documentoLegal;
	}
	public void setDocumentoLegal(String documentoLegal) {
		this.documentoLegal = documentoLegal;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="direccion", targetEntity=CoordenadaGeografica.class)
	public List<CoordenadaGeografica> getCoordenadasGeograficas() {
		return coordenadasGeograficas;
	}
	public void setCoordenadasGeograficas(
			List<CoordenadaGeografica> coordenadasGeograficas) {
		this.coordenadasGeograficas = coordenadasGeograficas;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="direccion", targetEntity=LinderoDireccion.class)
	public List<LinderoDireccion> getLinderos() {
		return linderos;
	}
	public void setLinderos(List<LinderoDireccion> linderos) {
		this.linderos = linderos;
	}
	
	@OneToMany(mappedBy="unidadProductiva", targetEntity=SuperficieUnidad.class)
	public List<SuperficieUnidad> getSuperficies() {
		return superficies;
	}
	public void setSuperficies(List<SuperficieUnidad> superficies) {
		this.superficies = superficies;
	}
	
	
	@ManyToMany
	@JoinTable(
		      name="tbl_dem_direccionpropietario", schema="ministerio", 
		      joinColumns= {@JoinColumn(name="int_iddireccion", referencedColumnName="seq_iddireccion")},
		      inverseJoinColumns={@JoinColumn(name="int_idcliente", referencedColumnName="seq_idcliente")}
	)
	@Basic(optional=true)
	public List<Cliente> getPropietarios() {
		return propietarios;
	}
	public void setPropietarios(List<Cliente> propietarios) {
		this.propietarios = propietarios;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipodocumentotierra")
	public TipoDocumentoTierra getTipoDocumentoTierra() {
		return tipoDocumentoTierra;
	}
	public void setTipoDocumentoTierra(TipoDocumentoTierra tipoDocumentoTierra) {
		this.tipoDocumentoTierra = tipoDocumentoTierra;
	}
	
	@Column(name="dbl_superficie")
	@Basic
	public Double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}
	
	@Transient
	public String getSuperficiesString(){
		return this.superficie + " HAS.";
	}
	
	@Transient
	public void addPropietario(Cliente propietario){
		if (propietarios == null)
			propietarios = new ArrayList<Cliente>();
		propietarios.add(propietario);
	}
	
	
	
	public String toString(){
		StringBuilder cadena = new StringBuilder();
		cadena.append("Sector ");
		cadena.append(sector.getNombre());
		cadena.append(", parroquia ");
		cadena.append(sector.getParroquia().getNombre());
		cadena.append(", municipio ");
		cadena.append(sector.getParroquia().getMunicipio().getNombre());
		cadena.append(", Estado ");
		cadena.append(sector.getParroquia().getMunicipio().getEstado().getNombre());
		cadena.append(", ");
		cadena.append(descripcion);
		return cadena.toString();
	}
	


	@Transient
	public String getStrPais(){
		if (sector == null)
			return null;
		return sector.getStrPais();
	}
	
	@Transient
	public String getStrEstado(){
		if (sector == null)
			return null;
		return sector.getStrEstado();
	}
	
	@Transient
	public String getStrMunicipio(){
		if (sector == null)
			return null;
		return sector.getStrMunicipio();
	}
	
	@Transient
	public String getStrParroquia(){
		if (sector == null)
			return null;
		return sector.getStrParroquia();
	}
	@Transient
	public String getStrSector(){
		if (sector == null)
			return null;
		return sector.getNombre();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((coordenadasGeograficas == null) ? 0
						: coordenadasGeograficas.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((documentoLegal == null) ? 0 : documentoLegal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((linderos == null) ? 0 : linderos.hashCode());
		result = prime * result
				+ ((propietarios == null) ? 0 : propietarios.hashCode());
		result = prime * result
				+ ((referencia == null) ? 0 : referencia.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
		result = prime * result
				+ ((superficie == null) ? 0 : superficie.hashCode());
		result = prime * result
				+ ((superficies == null) ? 0 : superficies.hashCode());
		result = prime
				* result
				+ ((tipoDocumentoTierra == null) ? 0 : tipoDocumentoTierra
						.hashCode());
		result = prime * result
				+ ((tipoUbicacion == null) ? 0 : tipoUbicacion.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UbicacionDireccion other = (UbicacionDireccion) obj;
		if (coordenadasGeograficas == null) {
			if (other.coordenadasGeograficas != null)
				return false;
		} else if (!coordenadasGeograficas.equals(other.coordenadasGeograficas))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (documentoLegal == null) {
			if (other.documentoLegal != null)
				return false;
		} else if (!documentoLegal.equals(other.documentoLegal))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linderos == null) {
			if (other.linderos != null)
				return false;
		} else if (!linderos.equals(other.linderos))
			return false;
		if (propietarios == null) {
			if (other.propietarios != null)
				return false;
		} else if (!propietarios.equals(other.propietarios))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		if (superficie == null) {
			if (other.superficie != null)
				return false;
		} else if (!superficie.equals(other.superficie))
			return false;
		if (superficies == null) {
			if (other.superficies != null)
				return false;
		} else if (!superficies.equals(other.superficies))
			return false;
		if (tipoDocumentoTierra == null) {
			if (other.tipoDocumentoTierra != null)
				return false;
		} else if (!tipoDocumentoTierra.equals(other.tipoDocumentoTierra))
			return false;
		if (tipoUbicacion == null) {
			if (other.tipoUbicacion != null)
				return false;
		} else if (!tipoUbicacion.equals(other.tipoUbicacion))
			return false;
		return true;
	}
	@Transient
	public String getStrCodigo(){
		
		return id.toString();
	}

	
	

}
