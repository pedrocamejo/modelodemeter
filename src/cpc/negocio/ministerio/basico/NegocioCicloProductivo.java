package cpc.negocio.ministerio.basico;

import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.basico.PerCicloProductivo;


public class NegocioCicloProductivo extends NegocioGenerico<CicloProductivo, PerCicloProductivo, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioCicloProductivo 				negocio;
	
	private NegocioCicloProductivo(){
		setPersistencia(new PerCicloProductivo());
	}
	
	public  static synchronized NegocioCicloProductivo getInstance() {
		if (negocio == null)
			negocio = new NegocioCicloProductivo();
		return negocio;
	}


}
