package cpc.persistencia.demeter.implementacion.administrativo;

import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.persistencia.DaoGenerico;


public class PerTipoContrato extends DaoGenerico<TipoContrato, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3846851985188866789L;

	public PerTipoContrato() {
		super(TipoContrato.class);
	}	
	
	
}
