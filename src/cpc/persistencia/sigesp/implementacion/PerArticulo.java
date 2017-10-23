package cpc.persistencia.sigesp.implementacion;

import cpc.modelo.sigesp.basico.Articulo;
import cpc.modelo.sigesp.indice.ArticuloPK;
import cpc.persistencia.DaoGenerico;


public class PerArticulo extends DaoGenerico<Articulo, ArticuloPK>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3329687014269410817L;

	public PerArticulo() {
		super(Articulo.class);
	}
	
	

}
