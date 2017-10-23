package cpc.modelo.demeter.gestion;

import java.util.List;

import javax.persistence.Basic;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.basico.UnidadMedida;
import cva.pc.demeter.utilidades.Tiempo;

@Audited @Entity
@Table(name="tbl_dem_trabajo_orden_servicio_mecanizado", schema="gestion")
@PrimaryKeyJoinColumn(name="int_idtrabajoorden")
public class TrabajoRealizadoMecanizado extends TrabajoRealizado{
	/**
	 * 
	 */
	private static final long 		serialVersionUID = 2254073474601617983L;
	private UnidadMedida			unidadTrabajoRealizado;
	private UnidadMedida			unidadProduccionRealizada;
	private Double					cantidadTrabajo;
	private Double					cantidadProduccion;
	private List<DetalleTrabajoRealizadoMecanizado>	detalles;
	private Trabajador				supervisor;
	
	@ManyToOne
	@JoinColumn(name="int_idunidadtrabajo")
	public UnidadMedida getUnidadTrabajoRealizado() {
		return unidadTrabajoRealizado;
	}
	public void setUnidadTrabajoRealizado(UnidadMedida unidadTrabajoRealizado) {
		this.unidadTrabajoRealizado = unidadTrabajoRealizado;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadproduccion")
	@Basic(optional=true)
	public UnidadMedida getUnidadProduccionRealizada() {
		return unidadProduccionRealizada;
	}
	public void setUnidadProduccionRealizada(UnidadMedida unidadProduccionRealizada) {
		this.unidadProduccionRealizada = unidadProduccionRealizada;
	}
	
	@Column(name="dbl_cantidadtrabajo")
	public Double getCantidadTrabajo() {
		return cantidadTrabajo;
	}
	public void setCantidadTrabajo(Double cantidadTrabajo) {
		this.cantidadTrabajo = cantidadTrabajo;
	}
	
	@Column(name="dbl_cantidadproduccion")
	@Basic(optional=true)
	public Double getCantidadProduccion() {
		return cantidadProduccion;
	}
	public void setCantidadProduccion(Double cantidadProduccion) {
		this.cantidadProduccion = cantidadProduccion;
	}
	
	@OneToMany(mappedBy="trabajo", targetEntity=DetalleTrabajoRealizadoMecanizado.class)
	public List<DetalleTrabajoRealizadoMecanizado> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleTrabajoRealizadoMecanizado> detalles) {
		this.detalles = detalles;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idsupervisor")
	public Trabajador getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Trabajador supervisor) {
		this.supervisor = supervisor;
	}
	
	@Transient
	public OrdenTrabajoMecanizado getOrdenTrabajoMecanizado() {
		return (OrdenTrabajoMecanizado) getOrdenTrabajo();
	}
	@Transient
	public void setOrdenTrabajoMecanizado(OrdenTrabajoMecanizado ordenTrabajo) {
		setOrdenTrabajo(ordenTrabajo);
	}
	
	@SuppressWarnings("deprecation")
	@Transient
	public double horasTrabajadas(){
		Tiempo tiempoFinal = new Tiempo();
		tiempoFinal.setHora(getHoraFinal().getHours());
		tiempoFinal.setMinuto(getHoraFinal().getMinutes());
		tiempoFinal.setSegundo(getHoraFinal().getSeconds());
		Tiempo tiempoInicial = new Tiempo();
		tiempoInicial.setHora(getHoraInicio().getHours());
		tiempoInicial.setMinuto(getHoraInicio().getMinutes());
		tiempoInicial.setSegundo(getHoraInicio().getSeconds());
		tiempoFinal = Tiempo.restar(tiempoFinal, tiempoInicial);
		return tiempoFinal.getHoraMinutos();
	}
	
	@Transient
	public String getNroControlOrden(){
		if (getOrdenTrabajo() == null)
			return "";
		return getOrdenTrabajo().getNroControl();
	}

	@Transient
	public String getCantidadLaborada(){
		return cantidadTrabajo.toString();
	}
	
	@Transient
	public String getProductor(){
		if (getOrdenTrabajoMecanizado() == null)
			return "";
		return getOrdenTrabajoMecanizado().getProductor().getNombres();
	}
	
	@Transient
	public String getStrLabor(){
		if (getLabor() == null)
			return "";
		return getLabor().getDescripcion();
	}
	
	@Transient
	public String getStrFecha(){
		return getFecha().toString();
	}
	
	@Transient
	public double getTotalHorometro(){
		double valor = 0;
		for (DetalleTrabajoRealizadoMecanizado item : getDetalles()) {
			valor += (item.getHorometroFinal()-item.getHorometroInicio());
		}	
		return valor;
	}
	
	@Transient
	public double getHorometroEfectivo(){
		double valor = 0;
		for (DetalleTrabajoRealizadoMecanizado item : getDetalles()) {
			valor += (item.getHorometroEfectivoFinal()-item.getHorometroEfectivoInicio());
		}	
		return valor;
	}

}
