package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.TipoFalla;
import cpc.persistencia.DaoGenerico;

public class PerTipoFalla extends DaoGenerico<TipoFalla, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410227421083988304L;

	public PerTipoFalla() {
		super(TipoFalla.class);
	}
}