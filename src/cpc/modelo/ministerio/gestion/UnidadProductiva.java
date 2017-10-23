package cpc.modelo.ministerio.gestion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.modelo.demeter.basico.TipoVerificacionSuelo;
import cpc.modelo.ministerio.basico.CoordenadaGeografica;
import cpc.modelo.ministerio.basico.TipoSuelo;
import cpc.modelo.ministerio.basico.TipoTenenciaTierra;
import cpc.modelo.ministerio.basico.TipoUsoTierra;
import cpc.modelo.ministerio.basico.TipoVialidad;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cva.pc.demeter.excepciones.ExcAgregacionInvalida;

@Audited @Entity
@Table(name="tbl_dem_unidadproductiva", schema="ministerio")
public class UnidadProductiva implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8690015860350393960L;
	private Integer						id;
	private UbicacionDireccion			ubicacion;
	private String						nombre;
	private SectorAgricola				sectorAgricola;
	private Double						superficieTotal;
	
	private TipoTenenciaTierra			condicionTenenciaTierra;
	private TipoVialidad				tipoVialidad;
	private TipoUsoTierra				tipoUsoTierra;
	private TipoSuelo					tipoSuelo;

	private Productor					productor;
	
	private Double 						distanciaEmpresa;
	private Double                      superficieActualmenteAprovechable;
	private Double                      superficiePotencialmenteAprovechable;
	private Double                      superficieBajoReservaForestal;
	private Integer                     numeroDeLotes;
	private TipoVerificacionSuelo       tipoVerificacionSuelo;
	
	private List<UnidadProductivaTipoRiego> tiposRiego;
	
	@Id
	@Column(name="seq_idunidadproductiva")
	@SequenceGenerator(name="SeqUnidadProductiva", sequenceName="ministerio.tbl_dem_unidadproductiva_seq_idunidadproductiva_seq")
	@GeneratedValue(generator="SeqUnidadProductiva")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(UbicacionDireccion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	
	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	
	@ManyToOne
	@JoinColumn(name="int_idsectoragricola")
	public SectorAgricola getSectorAgricola() {
		return sectorAgricola;
	}
	public void setSectorAgricola(SectorAgricola sectorAgricola) {
		this.sectorAgricola = sectorAgricola;
	}
	
	
	@Column(name="dbl_superficie")
	public Double getSuperficieTotal() {
		return superficieTotal;
	}
	public void setSuperficieTotal(Double superficieTotal) {
		this.superficieTotal = superficieTotal;
	}
	

	
	@ManyToOne
	@JoinColumn(name="int_idtipotenencia")
	@Basic(optional=true)
	public TipoTenenciaTierra getCondicionTenenciaTierra() {
		return condicionTenenciaTierra;
	}
	public void setCondicionTenenciaTierra(TipoTenenciaTierra condicionTenenciaTierra) {
		this.condicionTenenciaTierra = condicionTenenciaTierra;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipovialidad")
	@Basic(optional=true)
	public TipoVialidad getTipoVialidad() {
		return tipoVialidad;
	}
	public void setTipoVialidad(TipoVialidad tipoVialidad) {
		this.tipoVialidad = tipoVialidad;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipousotierra")
	@Basic(optional=true)
	public TipoUsoTierra getTipoUsoTierra() {
		return tipoUsoTierra;
	}
	public void setTipoUsoTierra(TipoUsoTierra tipoUsoTierra) {
		this.tipoUsoTierra = tipoUsoTierra;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtiposuelo")
	@Basic(optional=true)
	public TipoSuelo getTipoSuelo() {
		return tipoSuelo;
	}
	public void setTipoSuelo(TipoSuelo tipoSuelo) {
		this.tipoSuelo = tipoSuelo;
	}
	


	@ManyToOne
	@JoinColumn(name="int_idproductor")
	@Basic(optional=true)
	public Productor getProductor() {
		return productor;
	}
	public void setProductor(Productor productor) {
		this.productor = productor;
	}
	
	@Column(name="dbl_distanciaempresa")
	public Double getDistanciaEmpresa() {
		return distanciaEmpresa;
	}
	public void setDistanciaEmpresa(Double distanciaEmpresa) {
		this.distanciaEmpresa = distanciaEmpresa;
	}
	
	@Column(name="dbl_supeactuapro")
	public Double getSuperficieActualmenteAprovechable() {
		return superficieActualmenteAprovechable;
	}
	public void setSuperficieActualmenteAprovechable(
			Double superficieActualmenteAprovechable) {
		this.superficieActualmenteAprovechable = superficieActualmenteAprovechable;
	}
	
	@Column(name="dbl_supepoteapro")
	public Double getSuperficiePotencialmenteAprovechable() {
		return superficiePotencialmenteAprovechable;
	}
	public void setSuperficiePotencialmenteAprovechable(
			Double superficiePotencialmenteAprovechable) {
		this.superficiePotencialmenteAprovechable = superficiePotencialmenteAprovechable;
	}
	
	@Column(name="dbl_superesefore")
	public Double getSuperficieBajoReservaForestal() {
		return superficieBajoReservaForestal;
	}
	public void setSuperficieBajoReservaForestal(
			Double superficieBajoReservaForestal) {
		this.superficieBajoReservaForestal = superficieBajoReservaForestal;
	}
	
	@Column(name="int_nrolotes")
	public Integer getNumeroDeLotes() {
		return numeroDeLotes;
	}
	public void setNumeroDeLotes(Integer numeroDeLotes) {
		this.numeroDeLotes = numeroDeLotes;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipoverisuelo")
    @Basic(optional=true)
	public TipoVerificacionSuelo getTipoVerificacionSuelo() {
		return tipoVerificacionSuelo;
	}
	public void setTipoVerificacionSuelo(TipoVerificacionSuelo tipoVerificacionSuelo) {
		this.tipoVerificacionSuelo = tipoVerificacionSuelo;
	}
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy="unidadProductiva", targetEntity= UnidadProductivaTipoRiego.class, fetch = FetchType.EAGER)
	public List<UnidadProductivaTipoRiego> getTiposRiego() {
		return tiposRiego;
	}
	public void setTiposRiego(List<UnidadProductivaTipoRiego> tiposRiego) {
		this.tiposRiego = tiposRiego;
	}
	
	@Transient
	public String getReferencia() {
		if (ubicacion != null)
			return ubicacion.getReferencia();
		else
			return null;
	}
	
	@Transient
	public String getDireccion(){
		if (ubicacion != null)
			return ubicacion.toString();
		else
			return null;
	}
	
	
	@Transient
	public String getDescripcion() {
		if (ubicacion != null)
			return ubicacion.getDescripcion();
		else
			return null;
	}
	
	
	@Transient
	public UbicacionSector getSector() {
		if (ubicacion != null)
			return ubicacion.getSector();
		else
			return null;
	}
	
	@Transient
	public String getStrUbicacion() {
		if (ubicacion == null)
			return "";
		return ubicacion.toString();
	}
	
	@Transient
	public String getStrSectorAgricola() {
		if (sectorAgricola == null)
			return "";
		return sectorAgricola.getNombre();
	}
	
	@Transient
	public List<CoordenadaGeografica> getCoordenadasGeograficas() {
		if (ubicacion == null)
			return null;
		else
			return ubicacion.getCoordenadasGeograficas();
	}
	
	@Transient
	public void setCoordenadasGeograficas(List<CoordenadaGeografica> coordenadasGeograficas) throws ExcAgregacionInvalida {
		if (ubicacion == null)
			throw new ExcAgregacionInvalida();
		ubicacion.setCoordenadasGeograficas(coordenadasGeograficas);
	}
	
	@Transient
	public String getStrPais(){
		return ubicacion.getStrPais();
	}
	
	@Transient
	public String getStrEstado(){
		return ubicacion.getStrEstado();
	}
	
	@Transient
	public String getStrMunicipio(){
		return ubicacion.getStrMunicipio();
	}
	
	@Transient
	public String getStrParroquia(){
		return ubicacion.getStrParroquia();
	}
	@Transient
	public String getStrSector(){
		return ubicacion.getStrSector();
	}
	
	@Transient
	public String getStrTipoUsoTierra(){
		if (tipoUsoTierra == null)
			return "";
		return tipoUsoTierra.getNombre();
	}
	
	
	@Transient
	public String getStrProductores(){
		if (productor == null)
			return "";
		return productor.getNombres();
	}
	/*
	@Transient
	public String getStrCodigo(){
		
		return id.toString();
	}
	*/
	public String toString(){
		return ubicacion.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((condicionTenenciaTierra == null) ? 0
						: condicionTenenciaTierra.hashCode());
		result = prime
				* result
				+ ((distanciaEmpresa == null) ? 0 : distanciaEmpresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((numeroDeLotes == null) ? 0 : numeroDeLotes.hashCode());
		result = prime * result
				+ ((productor == null) ? 0 : productor.hashCode());
		result = prime * result
				+ ((sectorAgricola == null) ? 0 : sectorAgricola.hashCode());
		result = prime
				* result
				+ ((superficieActualmenteAprovechable == null) ? 0
						: superficieActualmenteAprovechable.hashCode());
		result = prime
				* result
				+ ((superficieBajoReservaForestal == null) ? 0
						: superficieBajoReservaForestal.hashCode());
		result = prime
				* result
				+ ((superficiePotencialmenteAprovechable == null) ? 0
						: superficiePotencialmenteAprovechable.hashCode());
		result = prime * result
				+ ((superficieTotal == null) ? 0 : superficieTotal.hashCode());
		result = prime * result
				+ ((tipoSuelo == null) ? 0 : tipoSuelo.hashCode());
		result = prime * result
				+ ((tipoUsoTierra == null) ? 0 : tipoUsoTierra.hashCode());
		result = prime
				* result
				+ ((tipoVerificacionSuelo == null) ? 0 : tipoVerificacionSuelo
						.hashCode());
		result = prime * result
				+ ((tipoVialidad == null) ? 0 : tipoVialidad.hashCode());
		result = prime * result
				+ ((tiposRiego == null) ? 0 : tiposRiego.hashCode());
		result = prime * result
				+ ((ubicacion == null) ? 0 : ubicacion.hashCode());
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
		UnidadProductiva other = (UnidadProductiva) obj;
		if (condicionTenenciaTierra == null) {
			if (other.condicionTenenciaTierra != null)
				return false;
		} else if (!condicionTenenciaTierra
				.equals(other.condicionTenenciaTierra))
			return false;
		if (distanciaEmpresa == null) {
			if (other.distanciaEmpresa != null)
				return false;
		} else if (!distanciaEmpresa.equals(other.distanciaEmpresa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroDeLotes == null) {
			if (other.numeroDeLotes != null)
				return false;
		} else if (!numeroDeLotes.equals(other.numeroDeLotes))
			return false;
		if (productor == null) {
			if (other.productor != null)
				return false;
		}//esta fallando por algo 
	/*	else if (!productor.equals(other.productor))
			return false;*/
		else if (!productor.getIdentidadLegal().equals(other.productor.getIdentidadLegal()))
			return false;
	
		if (sectorAgricola == null) {
			if (other.sectorAgricola != null)
				return false;
		} else if (!sectorAgricola.equals(other.sectorAgricola))
			return false;
		if (superficieActualmenteAprovechable == null) {
			if (other.superficieActualmenteAprovechable != null)
				return false;
		} else if (!superficieActualmenteAprovechable
				.equals(other.superficieActualmenteAprovechable))
			return false;
		if (superficieBajoReservaForestal == null) {
			if (other.superficieBajoReservaForestal != null)
				return false;
		} else if (!superficieBajoReservaForestal
				.equals(other.superficieBajoReservaForestal))
			return false;
		if (superficiePotencialmenteAprovechable == null) {
			if (other.superficiePotencialmenteAprovechable != null)
				return false;
		} else if (!superficiePotencialmenteAprovechable
				.equals(other.superficiePotencialmenteAprovechable))
			return false;
		if (superficieTotal == null) {
			if (other.superficieTotal != null)
				return false;
		} else if (!superficieTotal.equals(other.superficieTotal))
			return false;
		if (tipoSuelo == null) {
			if (other.tipoSuelo != null)
				return false;
		} else if (!tipoSuelo.equals(other.tipoSuelo))
			return false;
		if (tipoUsoTierra == null) {
			if (other.tipoUsoTierra != null)
				return false;
		} else if (!tipoUsoTierra.equals(other.tipoUsoTierra))
			return false;
		if (tipoVerificacionSuelo == null) {
			if (other.tipoVerificacionSuelo != null)
				return false;
		} else if (!tipoVerificacionSuelo.equals(other.tipoVerificacionSuelo))
			return false;
		if (tipoVialidad == null) {
			if (other.tipoVialidad != null)
				return false;
		} else if (!tipoVialidad.equals(other.tipoVialidad))
			return false;
		if (tiposRiego == null) {
			if (other.tiposRiego != null)
				return false;
		} else{
			//if (!tiposRiego.equals(other.tiposRiego))	return false;
			int largo1 = tiposRiego.size();
			int largo2 = other.tiposRiego.size();
			if (largo1!=largo2)	return false;
			
			for (int i = 0; i < tiposRiego.size(); i++) {
				UnidadProductivaTipoRiego a = tiposRiego.get(i);
				UnidadProductivaTipoRiego b = other.tiposRiego.get(i);
				boolean c = a.equals(b);
				
			if (!c)
				return false;
			}
		}
		if (ubicacion == null) {
			if (other.ubicacion != null)
				return false;
		} else if (!ubicacion.equals(other.ubicacion))
			return false;
		return true;
	}
	
	

}
