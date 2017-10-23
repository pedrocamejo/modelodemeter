package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.UnidadMedida;

@Audited @Entity
@Table(name="tbl_dem_detalle_orden_servicio", schema= "gestion")
public class  DetalleOrdenTrabajo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7057057109468579812L;
	private Integer			id;
	private Labor			labor;
	private UnidadMedida	unidadTrabajo;
	private Double	 		cantidadSolicitada;
	private Double 			cantidadEjecutada;
	
	private OrdenTrabajo	orden;
	 
	@Id
	@Column(name="seq_iddetalleoreden")
	@SequenceGenerator(name="seqDetalleOrdenTrabajo", sequenceName="gestion.tbl_dem_detalle_orden_servicio_seq_iddetalleoreden_seq", allocationSize=1)
	@GeneratedValue(generator="seqDetalleOrdenTrabajo")
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
	
	@Column(name="dbl_cantidadsolicitada")
	public Double getCantidadSolicitada() {
		return cantidadSolicitada;
	}
	public void setCantidadSolicitada(Double cantidadSolicitada) {
		this.cantidadSolicitada = cantidadSolicitada;
	}
	
	@Column(name="dbl_cantidadejecutada")
	@Basic(optional= true)
	public Double getCantidadEjecutada() {
		return cantidadEjecutada;
	}
	public void setCantidadEjecutada(Double cantidadEjecutada) {
		this.cantidadEjecutada = cantidadEjecutada;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadmedida")
	public UnidadMedida getUnidadTrabajo() {
		return unidadTrabajo;
	}
	public void setUnidadTrabajo(UnidadMedida unidadTrabajo) {
		this.unidadTrabajo = unidadTrabajo;
	}

	@ManyToOne
	@JoinColumn(name="int_idordenservicio")
	public OrdenTrabajo getOrden() {
		return orden;
	}
	public void setOrden(OrdenTrabajo orden) {
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
	public String getStrUnidadTrabajo(){
		if (this.labor==null)
		return "";
		else return this.labor.getMedidaGestion().getDescripcion();
	}

}
