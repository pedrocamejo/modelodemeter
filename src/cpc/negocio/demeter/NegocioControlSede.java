package cpc.negocio.demeter;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioControlSede implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3458018816884229575L;
	private static NegocioControlSede 	negocio;
	private PerControlSede				persistenciaControlSede;
	private ControlSede					controlSede;
	
	
	private NegocioControlSede(){

		persistenciaControlSede = new PerControlSede(); 
	}

	public  static synchronized NegocioControlSede getInstance() {
		if (negocio == null)
			negocio = new NegocioControlSede();
		return negocio;
	}
	
	public void guardar() throws Exception{		
		persistenciaControlSede.guardar(controlSede, controlSede.getId());
		
	}
	
	public void eliminar() throws Exception{
		persistenciaControlSede.borrar(controlSede);
	}
	
	public List<ControlSede> getTodos() {
		List<ControlSede> controlSede = null;
		try {
			controlSede = persistenciaControlSede.getAll();
		} catch (ExcFiltroExcepcion e) {
			
			e.printStackTrace();
		}
		return controlSede;
	}

	public PerControlSede getPersistenciaControlSede() {
		return persistenciaControlSede;
	}

	public void setPersistenciaControlSede(PerControlSede persistenciaControlSede) {
		this.persistenciaControlSede = persistenciaControlSede;
	}

	public ControlSede getControlSede() {
		return controlSede;
	}

	public void setControlSede(ControlSede controlSede) {
		this.controlSede = controlSede;
	}

	
	
}
