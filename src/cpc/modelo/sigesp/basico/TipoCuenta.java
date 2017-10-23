package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;

@Audited @Entity
@Table(name="scb_tipocuenta", schema="sigesp")
public class TipoCuenta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2743736567696733456L;
	private String 	id;
	private String  descripcion;
	
	@Id
	@Column(name="codtipcta")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="nomtipcta")
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
		TipoCuenta cuenta = (TipoCuenta) objeto;
		if (cuenta.getId().equals(id))
			return true;
		else
			return false;
	}

}
