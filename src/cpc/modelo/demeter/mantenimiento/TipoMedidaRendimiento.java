package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited @Entity
@Table(name="tbl_dem_tipomedidarendimiento", schema = "mantenimiento")
public class TipoMedidaRendimiento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1152014901291124387L;
	private Integer 			id;
	private String 				descripcion;
	private boolean             tipoHora;
	
	@Column(name="seq_idtipomedidarendimiento")
	@SequenceGenerator(name="TipoMedidaRendimiento_Gen", sequenceName="mantenimiento.tbl_dem_tipomedidarendimiento_seq_idtipomedidarendimiento_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="TipoMedidaRendimiento_Gen")

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_destipomedida")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_tipohora")
	public boolean getTipoHora() {
		return tipoHora;
	}
	
	public void setTipoHora(boolean  tipoHora) {
		this.tipoHora = tipoHora;	
		}
		
	public boolean equals(Object objeto){
		try{
			TipoMedidaRendimiento cuenta = (TipoMedidaRendimiento) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
