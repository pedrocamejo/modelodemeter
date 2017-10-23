package cpc.persistencia.demeter.implementacion.basico;

import cpc.modelo.demeter.basico.TipoArticulo;
import cpc.persistencia.DaoGenerico;

public class PerTipoArticulo extends DaoGenerico<TipoArticulo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerTipoArticulo() {
		super(TipoArticulo.class);
	}
	
}
