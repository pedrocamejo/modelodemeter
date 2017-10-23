package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_ciclos")
public class TipoCiclo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6621919254446480816L;
	private Integer 	id;
	private String 		descripcion;
	
	public TipoCiclo(){
		
	}
	
	@Id
	@Column(name="seq_idciclo")
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
			TipoCiclo cuenta = (TipoCiclo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 

}
