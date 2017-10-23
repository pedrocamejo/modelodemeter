package cpc.modelo.ministerio.dimension;

import java.io.Serializable;

public class UnidadAdjunta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -250857945004910434L;
	private Integer		id;
	private String		nombre;
	private boolean		satelital;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isSatelital() {
		return satelital;
	}
	public void setSatelital(boolean satelital) {
		this.satelital = satelital;
	}
	
	public boolean equals(Object objeto){
		try{
			UnidadAdjunta cuenta = (UnidadAdjunta) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
