package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.Falla;
import cpc.persistencia.DaoGenerico;

public class PerFallaBienProduccion extends DaoGenerico<Falla, Integer>{

	
	private static final long serialVersionUID = 3410227421083988304L;

	public PerFallaBienProduccion() {
		super(Falla.class);
	}
}