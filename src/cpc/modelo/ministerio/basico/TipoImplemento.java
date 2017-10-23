package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class TipoImplemento implements Serializable{

	private static final long serialVersionUID = -7132558397637632492L;
	private Integer 	id;
	private String		descripcion;
	
	
	@SequenceGenerator(name="tipoImplemento_Gen", sequenceName="basico.tbl_tipoimplemento_idtipoimplemento_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="tipoImplemento_Gen")
	@Column(name="idtipoimplemento", unique=true, nullable=false)
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
			TipoImplemento cuenta = (TipoImplemento) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
