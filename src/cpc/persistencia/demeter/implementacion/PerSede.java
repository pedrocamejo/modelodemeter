package cpc.persistencia.demeter.implementacion;

import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.persistencia.DaoGenerico;

public class PerSede extends DaoGenerico<Sede, SedePK>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7582186161851816498L;

	public PerSede() {
		super(Sede.class);
	}

	
}
