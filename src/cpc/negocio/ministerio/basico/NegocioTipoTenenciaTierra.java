package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoTenenciaTierra;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoTenenciaTierra;

public class NegocioTipoTenenciaTierra extends NegocioGenerico<TipoTenenciaTierra, PerTipoTenenciaTierra, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoTenenciaTierra 				negocio;
	
	private NegocioTipoTenenciaTierra(){
		setPersistencia(new PerTipoTenenciaTierra());
	}
	
	public  static synchronized NegocioTipoTenenciaTierra getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoTenenciaTierra();
		return negocio;
	}

}
