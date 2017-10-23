package cpc.persistencia.demeter.implementacion.basico;


import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.persistencia.DaoGenerico;

public class PerPrecioProducto extends DaoGenerico<PrecioProducto, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerPrecioProducto() {
		super(PrecioProducto.class);
	}
	
	
}
