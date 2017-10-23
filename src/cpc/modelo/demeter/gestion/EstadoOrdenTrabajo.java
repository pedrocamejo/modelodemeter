package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_estadoordentrabajo", schema="gestion")
public class EstadoOrdenTrabajo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6093098914734851573L;
	private Integer 	id;
	private String 		descripcion;
	private boolean		activa;
	private boolean		detenida;
	private boolean		finalizada;
	
	
	@Id
	@Column(name="seq_idestado")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_activo")
	public boolean isActiva() {
		return activa;
	}
	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	
	@Column(name="bol_detenido")
	public boolean isDetenida() {
		return detenida;
	}
	public void setDetenida(boolean detenida) {
		this.detenida = detenida;
	}
	
	@Column(name="bol_finalizada")
	public boolean isFinalizada() {
		return finalizada;
	}
	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}
	
	public boolean equals(Object objeto){
		try{
			EstadoOrdenTrabajo cuenta = (EstadoOrdenTrabajo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
