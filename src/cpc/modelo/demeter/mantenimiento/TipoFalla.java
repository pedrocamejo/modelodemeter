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
@Table(name="tbl_dem_tipofalla", schema = "mantenimiento")
public class TipoFalla implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Integer 	id;
	private String 		descripcion;
	
	public TipoFalla(){
	}
	
	@Column(name="seq_ser_tipofalla")
	@SequenceGenerator(name="TipoFalla_Gen", sequenceName="mantenimiento.tbl_dem_tipofalla_seq_ser_tipofalla_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="TipoFalla_Gen")
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
	
	public boolean equals(Object objeto){
		try{
			TipoFalla cuenta = (TipoFalla) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return descripcion.toUpperCase();
	}
	
}