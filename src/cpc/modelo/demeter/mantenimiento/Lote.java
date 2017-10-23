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
	@Table(name="tbl_dem_lote", schema = "mantenimiento")
	public class Lote implements Serializable{

	private static final long serialVersionUID = 4228223117483236021L;
	private Integer 	id;
	private String 		descripcion;
	
	public Lote(){
		
	}
	
	public Lote(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	@Column(name="seq_idlote")
	@SequenceGenerator(name="Lote_Gen", sequenceName="mantenimiento.tbl_dem_lote_seq_idlote_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="Lote_Gen")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_deslote")
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			Lote cuenta = (Lote) objeto;
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