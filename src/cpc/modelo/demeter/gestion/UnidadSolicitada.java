package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.UnidadMedida;


@Audited @Entity
@Table(name="tbl_dem_solicituddetalleunidad", schema="gestion")
public class UnidadSolicitada implements Serializable{

	private static final long serialVersionUID = 8458286765927828475L;
	private Integer				id;
	private UnidadMedida		unidad;
	private Double				cantidad;
	private DetalleSolicitud	detalleSolicitud;
	
	@Column(name="seq_idrenglonunidad")
	@SequenceGenerator(name="seqUnidadSolicitada", sequenceName="gestion.tbl_dem_solicituddetalleunidad_seq_idrenglonunidad_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="seqUnidadSolicitada")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	//@Column(name="int_idrenglon")
	@JoinColumn(name="int_idrenglon")
		public DetalleSolicitud getDetalleSolicitud() {
		return detalleSolicitud;
	}
	public void setDetalleSolicitud(DetalleSolicitud detalleSolicitud) {
		this.detalleSolicitud = detalleSolicitud;
	}
	
	@Transient
	public String getStrUnidad() {
		if (unidad == null)
			return null;
		return unidad.getDescripcion();
	}

	public boolean equals(Object objeto){
		try{
			UnidadSolicitada cuenta = (UnidadSolicitada) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}
