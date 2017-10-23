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
@Table(name="tbl_dem_fabricante", schema = "mantenimiento")
public class Fabricante implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Integer 	id;
	private String 		descripcion;
	
	public Fabricante(){
	}
	
	@Column(name="seq_ser_fabricante")
	@SequenceGenerator(name="Fabricante_Gen", sequenceName="mantenimiento.tbl_dem_fabricante_seq_ser_fabricante_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="Fabricante_Gen")
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
			Fabricante cuenta = (Fabricante) objeto;
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
		return descripcion.toUpperCase().trim();
	}
}