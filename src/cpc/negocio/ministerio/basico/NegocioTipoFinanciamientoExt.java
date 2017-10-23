package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.gestion.TipoFinanciamientoCrediticio;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerTipoFinanciamientoExt;

public class NegocioTipoFinanciamientoExt extends NegocioGenerico<TipoFinanciamientoCrediticio, PerTipoFinanciamientoExt, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTipoFinanciamientoExt 				negocio;
	
	private NegocioTipoFinanciamientoExt(){
		setPersistencia(new PerTipoFinanciamientoExt());
	}
	
	public  static synchronized NegocioTipoFinanciamientoExt getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoFinanciamientoExt();
		return negocio;
	}

}
