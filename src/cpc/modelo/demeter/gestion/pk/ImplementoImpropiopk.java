package cpc.modelo.demeter.gestion.pk;

import java.io.Serializable;

public class ImplementoImpropiopk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5407575687764879427L;
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
	@Override
	public String toString() {
		return "ImplementoImpropiopk [id=" + id + ", sede=" + sede + "]";
	}
	
}
