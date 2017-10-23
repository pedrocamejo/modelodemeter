package cpc.negocio.demeter.basico;

import cpc.modelo.demeter.basico.TipoArticulo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerTipoArticulo;

public class NegocioTipoArticulo extends NegocioGenerico<TipoArticulo, PerTipoArticulo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoArticulo 				negocio;
	
	private NegocioTipoArticulo(){
		setPersistencia(new PerTipoArticulo());
	}
	
	public  static synchronized NegocioTipoArticulo getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoArticulo();
		return negocio;
	}

	
}
