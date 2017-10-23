package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.UnidadArrime;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;


@Audited @Entity
@Table(name="tbl_dem_orden_transporte_detalle", schema="gestion")
@PrimaryKeyJoinColumn(name="int_idordenservicio")
public class DetalleOrdenTransporteProduccion implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6329126942994584357L;
	private Integer						id;
	private OrdenTransporteProduccion	orden;
	
	private UbicacionDireccion			origen;
	private UnidadArrime				destino;
	private Date						fechaSalida;
	private Double						tiempoEspera;
	private Double						distancia;
	private Boolean						efectivo;
	private FallaRecepcionSilo			falla;
	
	@Id
	@Column(name="seq_iddetalletransporte")
	@SequenceGenerator(name="SeqDetalleTransporte", sequenceName="gestion.tbl_dem_orden_transporte_detalle_seq_iddetalletransporte_seq", initialValue=1)
	@GeneratedValue(generator="SeqDetalleTransporte")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="int_origen")
	public UbicacionDireccion getOrigen() {
		return origen;
	}
	public void setOrigen(UbicacionDireccion origen) {
		this.origen = origen;
	}
	
	@ManyToOne
	@JoinColumn(name="int_destino")
	public UnidadArrime getDestino() {
		return destino;
	}
	public void setDestino(UnidadArrime destino) {
		this.destino = destino;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_fecha")
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
	
	@Column(name="dbl_distancia")
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	
	@Column(name="bol_exitoso")
	public Boolean getEfectivo() {
		return efectivo;
	}
	public void setEfectivo(Boolean efectivo) {
		this.efectivo = efectivo;
	}
	
	@ManyToOne
	@JoinColumn(name="int_causafalla")
	public FallaRecepcionSilo getFalla() {
		return falla;
	}
	public void setFalla(FallaRecepcionSilo falla) {
		this.falla = falla;
	} 
	
	@ManyToOne
	@JoinColumn(name="int_idordenservicio")
	public OrdenTransporteProduccion getOrden() {
		return orden;
	}
	public void setOrden(OrdenTransporteProduccion orden) {
		this.orden = orden;
	}

	public boolean equals(Object objeto){
		try{
			DetalleOrdenTrabajo cuenta = (DetalleOrdenTrabajo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	@Transient
	public String getStrDestino(){
		if (destino == null)
			return null;
		return destino.getDescripcion();
	}

	@Transient
	public String getStrOrigen(){
		if (origen == null)
			return null;
		return origen.toString();
	}
	
	@Transient
	public String getStrFalla(){
		if (falla == null)
			return null;
		return falla.getDescripcion();
	}
	
	@Transient
	public String getStrOrden(){
		return orden.getNroControl();
	}
	
	@Transient
	public String getStrFecha(){
		if (fechaSalida == null)
			return null;
		return fechaSalida.toString();
	}
	
}
