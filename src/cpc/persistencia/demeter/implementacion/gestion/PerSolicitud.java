package cpc.persistencia.demeter.implementacion.gestion;

import cpc.modelo.demeter.gestion.Solicitud;
import cpc.persistencia.DaoGenerico;


public class PerSolicitud extends DaoGenerico<Solicitud, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2207919662316083813L;



	public PerSolicitud() {
		super(Solicitud.class);		
	}
	

}
