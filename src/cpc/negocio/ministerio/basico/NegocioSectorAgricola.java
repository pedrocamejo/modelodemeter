package cpc.negocio.ministerio.basico;

import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.ministerio.basico.PerSectorAgricola;


public class NegocioSectorAgricola extends NegocioGenerico<SectorAgricola, PerSectorAgricola, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioSectorAgricola 				negocio;
	
	private NegocioSectorAgricola(){
		setPersistencia(new PerSectorAgricola());
	}
	
	public  static synchronized NegocioSectorAgricola getInstance() {
		if (negocio == null)
			negocio = new NegocioSectorAgricola();
		return negocio;
	}

	

}
