package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.UnidadMedida;

@Audited @Entity
@Table(name="tbl_dem_labor_orden_servicio_mecanizado", schema="gestion")
public class LaborOrdenServicio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2325856868981829741L;
	private Integer					id;
	private Labor					labor;
	private UnidadMedida			unidad;	
	private double					cantidad=1;
	private double					pases =1;
	private double					fisico;
	private boolean					cantidadVisible;
	private boolean					pasesVisible;
	private OrdenTrabajoMecanizado	orden;
	
	
	@Id
	@Column(name="seq_idlaborordenmecanizado")
	@SequenceGenerator(name="seqLaborOrdenMecanizado", sequenceName=" gestion.tbl_dem_labor_orden_servicio_mec_seq_idlaborordenmecanizado_seq", allocationSize=1)
	@GeneratedValue(generator="seqLaborOrdenMecanizado")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idlabor")
	public Labor getLabor() {
		return labor;
	}
	public void setLabor(Labor labor) {
		this.labor = labor;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidad")
	public UnidadMedida getUnidad() {
		return unidad;
	}
	public void setUnidad(UnidadMedida unidad) {
		this.unidad = unidad;
	}
	
	@Column(name="dbl_cantidad")
	@Basic(optional=true)
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	@Column(name="dbl_pase")
	@Basic(optional=true)
	public double getPases() {
		return pases;
	}
	public void setPases(double pases) {
		this.pases = pases;
	}
	
	@Column(name="dbl_fisico")
	@Basic(optional=false)
	public double getFisico() {
		return fisico;
	}
	public void setFisico(double fisico) {
		this.fisico = fisico;
	}
	
	@OneToOne
	@JoinColumn(name="int_idordenservicio")
	public OrdenTrabajoMecanizado getOrden() {
		return orden;
	}
	public void setOrden(OrdenTrabajoMecanizado orden) {
		this.orden = orden;
	}
	
	@Transient
	public double getCalculo(){
		return  fisico*cantidad*pases;
	}
	
	@Transient
	public boolean isCantidadVisible() {
		return cantidadVisible;
	}
	public void setCantidadVisible(boolean cantidadVisible) {
		this.cantidadVisible = cantidadVisible;
		if (cantidadVisible)
			cantidad=1;
	}
	
	@Transient
	public boolean isPasesVisible() {
		return pasesVisible;
	}
	public void setPasesVisible(boolean pasesVisible) {
		this.pasesVisible = pasesVisible;
		if (pasesVisible)
			pases=1;
	}

	
	
}
