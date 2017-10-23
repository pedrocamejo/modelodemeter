package cpc.persistencia.sigesp.implementacion;

import cpc.modelo.sigesp.basico.Cargo;
import cpc.persistencia.DaoGenerico;


public class PerCargo extends DaoGenerico<Cargo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5929544850809557318L;

	public PerCargo() {
		super(Cargo.class);
	}
}