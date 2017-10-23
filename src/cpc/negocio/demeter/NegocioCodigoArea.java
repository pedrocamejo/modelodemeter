package cpc.negocio.demeter;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerCodigoArea;

public class NegocioCodigoArea extends NegocioGenerico<CodigoArea, PerCodigoArea, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6551033639007707920L;
	private static NegocioCodigoArea 	negocio;
	
	private NegocioCodigoArea(){
		setPersistencia(new PerCodigoArea());
	}
	
	public  static synchronized NegocioCodigoArea getInstance() {
		if (negocio == null)
			negocio = new NegocioCodigoArea();
		return negocio;
	}
	

}
