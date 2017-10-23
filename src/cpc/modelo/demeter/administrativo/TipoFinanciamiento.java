package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tipo_financiamiento", schema="administracion")
public class TipoFinanciamiento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2090249206875358632L;
	private 	Integer 	id;
	private 	String	descripcion;
	
	
	public TipoFinanciamiento() {
		super();
	}
	
	public TipoFinanciamiento(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}	
	
	@Id
	@Column(name="seq_idtipofinanciamiento")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	 @Override
	public String toString() {		
		return descripcion.toUpperCase();
	}

	public boolean equals(Object objeto){
		try{
			TipoFinanciamiento cuenta = (TipoFinanciamiento) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
	 
}
