package cpc.modelo.demeter.interfaz;

import java.util.List;

import cpc.modelo.demeter.basico.Labor;


public interface IServicioGestion {
	public Labor getServicio();
	public String getStrServicio();
	public double getPrecioServicio();
	public double getCantidadServicio();
	public double getSubtotalServicio();	
	@SuppressWarnings("rawtypes")
	public List   getComposicion();
}
