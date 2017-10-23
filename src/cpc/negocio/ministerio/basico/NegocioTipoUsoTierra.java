package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoUsoTierra;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoUsoTierra;

public class NegocioTipoUsoTierra extends NegocioGenerico<TipoUsoTierra, PerTipoUsoTierra, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoUsoTierra 				negocio;
	
	private NegocioTipoUsoTierra(){
		setPersistencia(new PerTipoUsoTierra());
	}
	
	public  static synchronized NegocioTipoUsoTierra getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoUsoTierra();
		return negocio;
	}

}
