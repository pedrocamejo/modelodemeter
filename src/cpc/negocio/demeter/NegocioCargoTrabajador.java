package cpc.negocio.demeter;

import cpc.modelo.demeter.basico.CargoTrabajador;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerCargoTrabajador;

public class NegocioCargoTrabajador extends NegocioGenerico<CargoTrabajador, PerCargoTrabajador, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -873463696223498841L;
	private static NegocioCargoTrabajador 				negocio;
	
	private NegocioCargoTrabajador(){
		setPersistencia(new PerCargoTrabajador());
	}
	
	public  static synchronized NegocioCargoTrabajador getInstance() {
		if (negocio == null)
			negocio = new NegocioCargoTrabajador();
		return negocio;
	}

	
}
