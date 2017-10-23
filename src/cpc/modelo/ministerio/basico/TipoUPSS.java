package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class TipoUPSS implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 592369478716409234L;
	private Integer 	id;
	private String		descripcion;
	
	
	@SequenceGenerator(name="tipoUPSS_Gen", sequenceName="basico.tbl_tipoupss_idupss_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="tipoUPSS_Gen")
	@Column(name="idupss", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean equals(Object objeto){
		try{
			TipoUPSS cuenta = (TipoUPSS) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
