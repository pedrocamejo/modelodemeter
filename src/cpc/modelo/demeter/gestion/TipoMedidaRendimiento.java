package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import cpc.modelo.demeter.basico.TipoUnidadMedida;


public class TipoMedidaRendimiento implements Serializable{

	
	private static final long serialVersionUID = 1152014901291124387L;
	private Integer 			id;
	private String 				descripcion;
	private TipoUnidadMedida	medidaRelacionada;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public TipoUnidadMedida getMedidaRelacionada() {
		return medidaRelacionada;
	}
	public void setMedidaRelacionada(TipoUnidadMedida medidaRelacionada) {
		this.medidaRelacionada = medidaRelacionada;
	}
	public boolean equals(Object objeto){
		try{
			TipoMedidaRendimiento cuenta = (TipoMedidaRendimiento) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
