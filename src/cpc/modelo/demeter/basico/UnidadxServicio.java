package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


//@Audited @Entity
@Table(name="viw_unidadesxservicio")
public class UnidadxServicio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3621651460384660230L;
	private Labor				servicio;
	private UnidadMedida		unidad;
	
	public UnidadxServicio() {
		super();
	}
	
	public UnidadxServicio(Labor servicio, UnidadMedida unidad) {
		super();
		this.servicio = servicio;
		this.unidad = unidad;
	}
	
	
	@ManyToOne
	@JoinColumn(name="int_idservicio")
	public Labor getServicio() {
		return servicio;
	}
	public void setServicio(Labor servicio) {
		this.servicio = servicio;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idumedida")
	public UnidadMedida getUnidad() {
		return unidad;
	}
	public void setUnidad(UnidadMedida unidad) {
		this.unidad = unidad;
	}
	
	public boolean equals(Object objeto){
		try{
			UnidadxServicio cuenta = (UnidadxServicio) objeto;
			if (cuenta.getUnidad().equals(getUnidad()) && cuenta.getServicio().equals(getServicio()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}


}
