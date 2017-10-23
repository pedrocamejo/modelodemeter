package cpc.negocio.demeter.basico;

import cpc.modelo.demeter.basico.TipoServicio;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;



public class NegocioTipoServicio extends NegocioGenerico<TipoServicio, PerTipoServicio, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoServicio 				negocio;
	
	private NegocioTipoServicio(){
		setPersistencia(new PerTipoServicio());
	}
	
	public  static synchronized NegocioTipoServicio getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoServicio();
		return negocio;
	}

}
