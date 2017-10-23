package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.mantenimiento.MomentoFalla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMomentoFalla;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMomentoFalla implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 252670537927524124L;
	private static NegocioMomentoFalla 	negocio;
	private PerMomentoFalla				perMomentoFalla;
	private List<MomentoFalla>			momentos;
	private MomentoFalla				momento;
	
	private NegocioMomentoFalla(){
		perMomentoFalla = new PerMomentoFalla(); 
	}

	public  static synchronized NegocioMomentoFalla getInstance() {
		if (negocio == null)
			negocio = new NegocioMomentoFalla();
		return negocio;
	}

	public List<MomentoFalla> getTodos() throws ExcFiltroExcepcion{
		List<MomentoFalla> momentos = null;
		try {
			momentos = perMomentoFalla.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return momentos;
	}

	public static NegocioMomentoFalla getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioMomentoFalla negocio) {
		NegocioMomentoFalla.negocio = negocio;
	}

	public PerMomentoFalla getPerMomentoFallas() {
		return perMomentoFalla;
	}

	public void setPerMomentoFallas(PerMomentoFalla perMomentoFalla) {
		this.perMomentoFalla = perMomentoFalla;
	}
	public List<MomentoFalla> getMomentoFallas() {
		return momentos;
	}

	public void setMomentoFallas(List<MomentoFalla> momentos) {
		this.momentos = momentos;
	}
	public MomentoFalla getMomentoFalla(Integer indice) {
		return perMomentoFalla.buscarId(indice);
	}
	
	public void setMomentoFalla(MomentoFalla momento) {
		this.momento = momento;
	}
	public void guardar() throws Exception{
		perMomentoFalla.guardar(momento, momento.getId());
	}
	
	public void eliminar() throws Exception{
		perMomentoFalla.borrar(momento);
	}
}
