package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.administrativo.CotizacionTransporte;
import cpc.modelo.demeter.transporte.UbicacionTransporte;
import cva.pc.demeter.utilidades.Fecha;

@Audited @Entity
@Table(name="tbl_dem_orden_servicio_transporteinterno", schema="gestion")
@PrimaryKeyJoinColumn(name="int_idordenservicio")
public class OrdenTrabajoTransporteInterno extends OrdenTrabajo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2095477269821711018L;
	private Date							inicioServicio;
	private Date							finServicio;
	private Date							inicioServicioVerificado;
	private Date							finServicioVerificado;
	private UbicacionTransporte 			ubicacionFinal;
	private UbicacionTransporte 			ubicacionInico;
	private UbicacionTransporte 			ubicacionFinalReal;
	private UbicacionTransporte 			ubicacionInicoReal;
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
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_inicio_verificado")
	public Date getInicioServicioVerificado() {
		return inicioServicioVerificado;
	}
	public void setInicioServicioVerificado(Date inicioServicioVerificado) {
		this.inicioServicioVerificado = inicioServicioVerificado;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="dte_fin_verificado")
	public Date getFinServicioVerificado() {
		return finServicioVerificado;
	}
	public void setFinServicioVerificado(Date finServicioVerificado) {
		this.finServicioVerificado = finServicioVerificado;
	}
	@ManyToOne
	@JoinColumn(name ="int_iddireccionfinalreal" )
	public UbicacionTransporte getUbicacionFinalReal() {
		return ubicacionFinalReal;
	}
	public void setUbicacionFinalReal(UbicacionTransporte ubicacionFinalReal) {
		this.ubicacionFinalReal = ubicacionFinalReal;
	}
	@ManyToOne
	@JoinColumn(name ="int_iddireccioninicioreal" )
	public UbicacionTransporte getUbicacionInicoReal() {
		return ubicacionInicoReal;
	}
	public void setUbicacionInicoReal(UbicacionTransporte ubicacionInicoReal) {
		this.ubicacionInicoReal = ubicacionInicoReal;
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
	public String getStrFechaInicio(){
		return Fecha.obtenerFecha(inicioServicio);
	}
	
}