package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.CotizacionTransporte;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.transporte.UbicacionTransporte;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cva.pc.demeter.utilidades.Fecha;

@Audited @Entity
@Table(name="tbl_dem_orden_servicio_transporte", schema="gestion")
@PrimaryKeyJoinColumn(name="int_idordenservicio")
public class OrdenTrabajoTransporte extends OrdenTrabajo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5103637608311863938L;
	private Date							inicioServicio;
	private Date							finServicio;
	private CotizacionTransporte			cotizacionTransporte;
	private UbicacionTransporte 			ubicacionFinal;
	private UbicacionTransporte 			ubicacionInico;
	private double							kilometraje;
	//private UnidadFuncional					unidadFuncional;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_inicio")
	public Date getInicioServicio() {
		return inicioServicio;
	}
	public void setInicioServicio(Date inicioServicio) {
		this.inicioServicio = inicioServicio;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fin")
	public Date getFinServicio() {
		return finServicio;
	}
	public void setFinServicio(Date finServicio) {
		this.finServicio = finServicio;
	}
	

	@ManyToOne 
	@JoinColumn(name="int_idcotizacion")
	public CotizacionTransporte getCotizacionTransporte() {
		return cotizacionTransporte;
	}
	public void setCotizacionTransporte(CotizacionTransporte cotizacionTransporte) {
		this.cotizacionTransporte=cotizacionTransporte;
	}
	@ManyToOne
	@JoinColumn(name ="int_iddireccionfinal" )
	public UbicacionTransporte getUbicacionFinal() {
		return ubicacionFinal;
	}
	public void setUbicacionFinal(UbicacionTransporte ubicacionFinal) {
		this.ubicacionFinal = ubicacionFinal;
	}
	
//	@ManyToOne
//	@JoinColumn(name ="int_idunidadfuncional" )
//	public UnidadFuncional getUnidadFuncional(){
//		return unidadFuncional;
//	}
//	
//	public void setUnidadFuncional(UnidadFuncional unidadFuncional){
//		this.unidadFuncional = unidadFuncional;
//	}
	@ManyToOne
	@JoinColumn(name ="int_iddireccioninicio" )
	public UbicacionTransporte getUbicacionInico() {
		return ubicacionInico;
	}
	public void setUbicacionInico(UbicacionTransporte ubicacionInico) {
		this.ubicacionInico = ubicacionInico;
	}
	@Column(name="dbl_kilometraje")
	public double getKilometraje() {
		return kilometraje;
	}
	public void setKilometraje(double kilometraje) {
		this.kilometraje = kilometraje;
	}
	
	@Transient
	public String getStrProductor(){
		if (this.cotizacionTransporte!=null)
			return cotizacionTransporte.getNombrePagador();
		else return "";
		
	}
	@Transient
	public String getStrFechaInicio(){
		return Fecha.obtenerFecha(inicioServicio);
	}
	
}