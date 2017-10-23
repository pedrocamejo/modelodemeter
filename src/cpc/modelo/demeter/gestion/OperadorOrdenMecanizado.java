package cpc.modelo.demeter.gestion;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.ministerio.dimension.UbicacionSector;

public class OperadorOrdenMecanizado {
	private Trabajador				operador;
	private MaquinariaUnidad		maquinaria;
	private UbicacionSector			sector;
	private OrdenTrabajoMecanizado	ordenTrabajo;
	
	
	public Trabajador getOperador() {
		return operador;
	}
	public void setOperador(Trabajador operador) {
		this.operador = operador;
	}
	public UbicacionSector getSector() {
		return sector;
	}
	public void setSector(UbicacionSector sector) {
		this.sector = sector;
	}
	public OrdenTrabajoMecanizado getOrdenTrabajo() {
		return ordenTrabajo;
	}
	public void setOrdenTrabajo(OrdenTrabajoMecanizado ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
	public MaquinariaUnidad getMaquinaria() {
		return maquinaria;
	}
	public void setMaquinaria(MaquinariaUnidad maquinaria) {
		this.maquinaria = maquinaria;
	}
	
	
}
