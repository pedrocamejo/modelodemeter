package cpc.modelo.demeter.basico;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipoproducto", schema="basico")
public class TipoProducto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4049159200796027277L;
	private Integer 	id;
	private String 		descripcion;
	
	public TipoProducto(){
		
	}
	
	
	@Column(name="seq_idtipoproducto")
	@SequenceGenerator(name="seqTipoProducto", sequenceName="basico.tbl_dem_tipoproducto_seq_idtipoproducto_seq", allocationSize =1)
	@Id @GeneratedValue(generator="seqTipoProducto")
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
	
	public String toString() {
		return this.descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoProducto cuenta = (TipoProducto) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 

}
