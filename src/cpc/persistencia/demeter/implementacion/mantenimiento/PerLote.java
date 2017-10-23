package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.Lote;
import cpc.persistencia.DaoGenerico;

public class PerLote extends DaoGenerico<Lote, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410227421083988304L;

	public PerLote() {
		super(Lote.class);
	}
}