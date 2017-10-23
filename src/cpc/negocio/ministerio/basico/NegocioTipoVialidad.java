package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.TipoVialidad;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoVialidad;

public class NegocioTipoVialidad extends NegocioGenerico<TipoVialidad, PerTipoVialidad, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoVialidad 				negocio;
	
	private NegocioTipoVialidad(){
		setPersistencia(new PerTipoVialidad());
	}
	
	public  static synchronized NegocioTipoVialidad getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoVialidad();
		return negocio;
	}

}
