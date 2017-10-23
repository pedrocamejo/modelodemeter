package cpc.persistencia.demeter.implementacion.basico;

import cpc.modelo.demeter.basico.TipoProducto;
import cpc.persistencia.DaoGenerico;


public class PerTipoProducto extends DaoGenerico<TipoProducto, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerTipoProducto() {
		super(TipoProducto.class);
	}
	

	
}
