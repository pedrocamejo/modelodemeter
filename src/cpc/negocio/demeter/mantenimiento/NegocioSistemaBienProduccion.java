package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.mantenimiento.Sistema;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSistemaBienProduccion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSistemaBienProduccion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8500779802505612218L;
	private static NegocioSistemaBienProduccion 	negocio;
	private PerSistemaBienProduccion				perSistema;
	private List<Sistema>				sistemas;
	private Sistema					sistema;
	
	private NegocioSistemaBienProduccion(){
		perSistema = new PerSistemaBienProduccion(); 
	}

	public  static synchronized NegocioSistemaBienProduccion getInstance() {
		if (negocio == null)
			negocio = new NegocioSistemaBienProduccion();
		return negocio;
	}

	public List<Sistema> getTodos() throws ExcFiltroExcepcion{
		List<Sistema> sistemas = null;
		try {
			sistemas = perSistema.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return sistemas;
	}

	public static NegocioSistemaBienProduccion getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioSistemaBienProduccion negocio) {
		NegocioSistemaBienProduccion.negocio = negocio;
	}

	public PerSistemaBienProduccion getPerSistemaBienProduccion() {
		return perSistema;
	}

	public void setPerSistemaBienProduccion(PerSistemaBienProduccion perSistema) {
		this.perSistema = perSistema;
	}
	public List<Sistema> getSistemaBienProduccion() {
		return sistemas;
	}

	public void setSistemaBienProduccion(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}
	public Sistema getSistemaBienProduccion(Integer indice) {
		return perSistema.buscarId(indice);
	}
	
	public void setSistemaBienProduccion(Sistema sistema) {
		this.sistema = sistema;
	}
	public void guardar() throws Exception{
		perSistema.guardar(sistema, sistema.getId());
	}
	
	public void eliminar() throws Exception{
		perSistema.borrar(sistema);
	}
}
