package cpc.persistencia.demeter.implementacion;

import cpc.modelo.demeter.basico.EspecilidadCargo;
import cpc.persistencia.DaoGenerico;

public class PerEspecialidadCargo extends DaoGenerico<EspecilidadCargo, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -571247574800272013L;

	public PerEspecialidadCargo() {
		super(EspecilidadCargo.class);
	}
}