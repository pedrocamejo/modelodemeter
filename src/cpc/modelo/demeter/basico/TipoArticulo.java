package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipoarticulo", schema="basico")
public class TipoArticulo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178033955303643056L;
	private Integer		id;
	private String		descripcion;
	
	
	@Id
	@Column(name="seq_idtipoarticulo")
	@SequenceGenerator(name="GenTipoArticulo", sequenceName="basico.tbl_dem_tipoarticulo_seq_idtipoarticulo_seq", allocationSize=1)
	@GeneratedValue(generator="GenTipoArticulo")
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
			TipoArticulo cuenta = (TipoArticulo) objeto;
			if (cuenta.getId().equals(this.getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}
