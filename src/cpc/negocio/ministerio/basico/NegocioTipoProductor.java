package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoProductor;

public class NegocioTipoProductor extends NegocioGenerico<TipoProductor, PerTipoProductor, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoProductor 				negocio;
	
	private NegocioTipoProductor(){
		setPersistencia(new PerTipoProductor());
	}
	
	public  static synchronized NegocioTipoProductor getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoProductor();
		return negocio;
	}

}
