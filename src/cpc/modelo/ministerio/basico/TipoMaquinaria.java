package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class TipoMaquinaria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4781527927074722441L;
	private Integer 	id;
	private String		descripcion;
	
	
	@SequenceGenerator(name="tipoMaquinaria_Gen", sequenceName="basico.tbl_tipomaquinaria_idtipomaquinaria_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="tipoMaquinaria_Gen")
	@Column(name="idtipomaquinaria", unique=true, nullable=false)
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
			TipoMaquinaria cuenta = (TipoMaquinaria) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
