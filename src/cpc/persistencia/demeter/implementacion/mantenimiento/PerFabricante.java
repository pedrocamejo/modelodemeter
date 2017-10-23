package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.Fabricante;
import cpc.persistencia.DaoGenerico;

public class PerFabricante extends DaoGenerico<Fabricante, Integer>{

	
	private static final long serialVersionUID = 3410227421083988304L;

	public PerFabricante() {
		super(Fabricante.class);
	}
}