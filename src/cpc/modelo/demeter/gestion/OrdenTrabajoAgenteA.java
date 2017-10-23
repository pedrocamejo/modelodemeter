package cpc.modelo.demeter.gestion;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.Herramienta;
import cpc.modelo.demeter.mantenimiento.RepuestoEInsumo;


public class OrdenTrabajoAgenteA extends OrdenTrabajo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3933700651995262420L;
	private List<RepuestoEInsumo>	repuestos;
	private List<RepuestoEInsumo>	consumos;
	private List<Herramienta>		Herramientas;
	
	
	public List<RepuestoEInsumo> getRepuestos() {
		return repuestos;
	}
	public void setRepuestos(List<RepuestoEInsumo> repuestos) {
		this.repuestos = repuestos;
	}
	public List<RepuestoEInsumo> getConsumos() {
		return consumos;
	}
	public void setConsumos(List<RepuestoEInsumo> consumos) {
		this.consumos = consumos;
	}
	public List<Herramienta> getHerramientas() {
		return Herramientas;
	}
	public void setHerramientas(List<Herramienta> herramientas) {
		Herramientas = herramientas;
	}
	public boolean equals(Object objeto){
		try{
			OrdenTrabajoAgenteA cuenta = (OrdenTrabajoAgenteA) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
