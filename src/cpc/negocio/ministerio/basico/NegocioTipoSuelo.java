package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoSuelo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoSuelo;

public class NegocioTipoSuelo extends NegocioGenerico<TipoSuelo, PerTipoSuelo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoSuelo 				negocio;
	
	private NegocioTipoSuelo(){
		setPersistencia(new PerTipoSuelo());
	}
	
	public  static synchronized NegocioTipoSuelo getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoSuelo();
		return negocio;
	}

}
