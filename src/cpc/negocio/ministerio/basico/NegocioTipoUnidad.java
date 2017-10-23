package cpc.negocio.ministerio.basico;


import cpc.modelo.ministerio.dimension.TipoUnidad;
import cpc.negocio.ministerio.NegocioGenerico;

import cpc.persistencia.ministerio.basico.PerTipoUnidad;

public class NegocioTipoUnidad extends NegocioGenerico<TipoUnidad, PerTipoUnidad, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoUnidad 				negocio;
	
	private NegocioTipoUnidad(){
		setPersistencia(new PerTipoUnidad());
	}
	
	public  static synchronized NegocioTipoUnidad getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoUnidad();
		return negocio;
	}

}
