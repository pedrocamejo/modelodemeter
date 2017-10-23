package cpc.persistencia.demeter.implementacion.basico;

import java.util.List;
import cpc.modelo.demeter.basico.Producto;
import cpc.persistencia.DaoGenerico;


public class PerProducto extends DaoGenerico<Producto, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerProducto() {
		super(Producto.class);
	}
	
	
	public List<Producto> getAll(){
		return null;
	}
	
		
}
