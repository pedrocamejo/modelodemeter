package cpc.persistencia.sigesp.implementacion;

import cpc.modelo.sigesp.basico.UbicacionGeografica;
import cpc.modelo.sigesp.indice.ParroquiaPK;
import cpc.persistencia.DaoGenerico;


public class PerUbicacionGeografica extends DaoGenerico<UbicacionGeografica, ParroquiaPK>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5542381137006900144L;

	public PerUbicacionGeografica() {
		super(UbicacionGeografica.class);
	}

}
