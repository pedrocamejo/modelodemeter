package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class TipoEscuela implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3204416552515356670L;
	private Integer 	id;
	private String		descripcion;
	
	
	@SequenceGenerator(name="tipoEscuela_Gen", sequenceName="basico.tbl_tipoescuela_idtipoescuela_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="tipoEscuela_Gen")
	@Column(name="idtipoescuela", unique=true, nullable=false)
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
			TipoEscuela cuenta = (TipoEscuela) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
}
