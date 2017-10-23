package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoRiego;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoRiego;

public class NegocioTipoRiego extends NegocioGenerico<TipoRiego, PerTipoRiego, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoRiego 				negocio;
	
	private NegocioTipoRiego(){
		setPersistencia(new PerTipoRiego());
	}
	
	public  static synchronized NegocioTipoRiego getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoRiego();
		return negocio;
	}

}
