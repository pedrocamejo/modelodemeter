package cpc.modelo.demeter.gestion.pk;

import java.io.Serializable;
  
public class MaquinariaImpropiapk implements Serializable{
	
	private Integer 	id ;
	private String 		sede;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getSede() {
		return sede;
	}
	
	public void setSede(String sede) {
		this.sede = sede;
	}
	
}
