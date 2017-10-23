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
@Table(name="tbl_dem_sistemas", schema="mantenimiento")
public class Sistema implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Integer 	id;
	private String 		descripcion;
	
	public Sistema(){
	}
	
	@SequenceGenerator(name="SeqSistemaBien", sequenceName="bien_produccion.tbl_dem_sistemas_seq_ser_sistema_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="SeqSistemaBien")
	@Column(name="seq_ser_sistema")
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
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			Sistema cuenta = (Sistema) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}