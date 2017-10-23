package cpc.persistencia.demeter.implementacion.mantenimiento;

import cpc.modelo.demeter.mantenimiento.ObjetoMantenimiento;
import cpc.persistencia.DaoGenerico;

public class PerObjetoManetenimiento extends DaoGenerico<ObjetoMantenimiento, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3386881121609034062L;

	public PerObjetoManetenimiento() {
		super(ObjetoMantenimiento.class);
	}
}
