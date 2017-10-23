package cpc.persistencia.demeter.implementacion;


import cpc.modelo.sigesp.basico.TipoCuenta;
import cpc.persistencia.DaoGenerico;



public class PerTipoCuentaBancaria extends DaoGenerico<TipoCuenta, String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112098275726135022L;

	public PerTipoCuentaBancaria() {
		super(TipoCuenta.class);
		// TODO Auto-generated constructor stub
	}
}
