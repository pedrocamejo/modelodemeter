package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.Sistema;
import cpc.persistencia.DaoGenerico;

public class PerSistemaBienProduccion extends DaoGenerico<Sistema, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8080359601692686927L;

	public PerSistemaBienProduccion() {
		super(Sistema.class);
	}
}