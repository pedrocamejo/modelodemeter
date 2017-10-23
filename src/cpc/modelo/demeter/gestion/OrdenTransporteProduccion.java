package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.UnidadArrime;
import cpc.modelo.ministerio.basico.Usos;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.gestion.Productor;

@Audited @Entity
@Table(name="tbl_dem_orden_transporte_produccion", schema="gestion")
@PrimaryKeyJoinColumn(name="int_idordenservicio")
public class OrdenTransporteProduccion extends OrdenTrabajo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -711865891672848670L;
	private Rubro					rubro;
	private UnidadArrime			unidadArrimeSolicitada;
	private UbicacionDireccion		origen;
	private Productor				productor;
	private Date					fechaSalida;
	private Double					tiempoEspera;
	private Double					totalRecorrido;
	private OrdenTrabajoMecanizado	ordenTrabajoMecanizado;	
	private Double					produccionReal;
	private Integer					cantidadViajes;
	private Double					cantidadKilometros;
	private Double					kilometrajeInicio;
	private Double					kilometrajeFinal;
	private Date					fechaEntradaArrime;
	private Double 					pesoAcondicionado;
	private	Double					gradosHumedad;
	private Usos					usoPersona;
	private UnidadArrime     		unidadArrimeDestino;

	 
	
	
	@ManyToOne
	@JoinColumn(name="int_idrubro")
	public Rubro getRubro() {
		return rubro;
	}
	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idsilo")
	public UnidadArrime getUnidadArrimeSolicitada() {
		return unidadArrimeSolicitada;
	}
	public void setUnidadArrimeSolicitada(UnidadArrime unidadArrimeSolicitada) {
		this.unidadArrimeSolicitada = unidadArrimeSolicitada;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadproductiva")
	public UbicacionDireccion getOrigen() {
		return origen;
	}
	public void setOrigen(UbicacionDireccion origen) {
		this.origen = origen;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idproductor")
	public Productor getProductor() {
		return productor;
	}
	public void setProductor(Productor productor) {
		this.productor = productor;
	}
	
	@Column(name="dbl_totalrecorrido")
	public Double getTotalRecorrido() {
		return totalRecorrido;
	}
	public void setTotalRecorrido(Double totalRecorrido) {
		this.totalRecorrido = totalRecorrido;
	}
	
	@Column(name="dbl_produccionreal")
	public Double getProduccionReal() {
		return produccionReal;
	}
	public void setProduccionReal(Double produccionReal) {
		this.produccionReal = produccionReal;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idordenproduccion")
	public OrdenTrabajoMecanizado getOrdenTrabajoMecanizado() {
		return ordenTrabajoMecanizado;
	}
	public void setOrdenTrabajoMecanizado(
			OrdenTrabajoMecanizado ordenTrabajoMecanizado) {
		this.ordenTrabajoMecanizado = ordenTrabajoMecanizado;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_inicio")
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	@Column(name="int_diasespera")
	public Double getTiempoEspera() {
		return tiempoEspera;
	}
	public void setTiempoEspera(Double tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}
	
	@Column(name="int_cantviajes")
	public Integer getCantidadViajes() {
		return cantidadViajes;
	}
	public void setCantidadViajes(Integer cantidadViajes) {
		this.cantidadViajes = cantidadViajes;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechentrarrime")
	public Date getFechaEntradaArrime() {
		return fechaEntradaArrime;
	}
	public void setFechaEntradaArrime(Date fechaEntradaArrime) {
		this.fechaEntradaArrime = fechaEntradaArrime;
	}
	
	@Column(name="dbl_cantkilometraje")
	public Double getCantidadKilometros() {
		return cantidadKilometros;
	}
	public void setCantidadKilometros(Double cantidadKilometros) {
		this.cantidadKilometros = cantidadKilometros;
	}
	@Column(name="dbl_pesoacond")
	public Double getPesoAcondicionado() {
		return pesoAcondicionado;
	}
	public void setPesoAcondicionado(Double pesoAcondicionado) {
		this.pesoAcondicionado = pesoAcondicionado;
	}
	
	@Column(name="dbl_gradohumedad")
	public Double getGradosHumedad() {
		return gradosHumedad;
	}
	public void setGradosHumedad(Double gradosHumedad) {
		this.gradosHumedad = gradosHumedad;
	}
	
	/*@Column(name="bol_usopersona")
	public Boolean getUsoPersona() {
		return usoPersona;
	}*/
	
	@ManyToOne
	@JoinColumn(name="int_usopersona")
	public Usos getUsoPersona() {
		return usoPersona;
	}
	public void setUsoPersona(Usos usoPersona) {
		this.usoPersona = usoPersona;
	}

	@Column(name="dbl_kilometrajeinicio")
	public Double getKilometrajeInicio() {
		return kilometrajeInicio;
	}
	public void setKilometrajeInicio(Double kilometrajeInicio) {
		this.kilometrajeInicio = kilometrajeInicio;
	}
	
	@Column(name="dbl_kilometrajefinal")
	public Double getKilometrajeFinal() {
		return kilometrajeFinal;
	}
	public void setKilometrajeFinal(Double kilometrajeFinal) {
		this.kilometrajeFinal = kilometrajeFinal;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idsilodestino")
	public UnidadArrime getUnidadArrimeDestino() {
		return unidadArrimeDestino;
	}
	public void setUnidadArrimeDestino(UnidadArrime unidadArrimeDestino) {
		this.unidadArrimeDestino = unidadArrimeDestino;
	}
	
	@Transient
	public String getStrSilo() {
		if (unidadArrimeSolicitada == null)
			return null;
		return unidadArrimeSolicitada.getDescripcion();
	}
	
	@Transient
	public String getStrRubro() {
		if (rubro == null)
			return null;
		return rubro.getDescripcion();
	}
	
	@Transient
	public String getStrProductor() {
		if (productor == null)
			return null;
		return productor.getNombres();
	}
	
	public String toString(){
		return getNroControl();
	}
}
