package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;

@Audited @Entity
@Table(name="scg_cuentas", schema="sigesp")
public class CuentaContable implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 2604019245010204663L;
	
	private String 		id;
	private String		denominacion;
	
	@Id
	@Column(name="sc_cuenta")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="denominacion")
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	public String toString(){
		return id+" : "+denominacion;
	}

	public boolean equals(Object objeto){
		try{
			CuentaContable banco = (CuentaContable) objeto;
			if (banco.getId().trim().equals(id.trim()) )
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
