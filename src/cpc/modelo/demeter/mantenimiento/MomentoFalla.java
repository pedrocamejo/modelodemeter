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
@Table(name="tbl_dem_momentofalla", schema = "mantenimiento")
public class MomentoFalla implements Serializable{

	
	private static final long serialVersionUID = 8618898671594231283L;
	private Integer 	id;
	private String 		descripcion;
	
	public MomentoFalla(){
	}
	
	@Column(name="seq_ser_momenfalla")
	@SequenceGenerator(name="MomentoFalla_Gen", sequenceName="mantenimiento.tbl_dem_momentofalla_seq_ser_momenfalla_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="MomentoFalla_Gen")
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
			MomentoFalla cuenta = (MomentoFalla) objeto;
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