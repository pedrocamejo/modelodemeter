package cpc.negocio.ministerio.basico;

import cpc.modelo.ministerio.basico.GradoInstruccion;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerGradoInstruccion;

public class NegocioGradoInstruccion extends NegocioGenerico<GradoInstruccion, PerGradoInstruccion, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioGradoInstruccion 				negocio;
	
	private NegocioGradoInstruccion(){
		setPersistencia(new PerGradoInstruccion());
	}
	
	public  static synchronized NegocioGradoInstruccion getInstance() {
		if (negocio == null)
			negocio = new NegocioGradoInstruccion();
		return negocio;
	}

}
