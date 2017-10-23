package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import java.io.Serializable;

import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEstadoFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioEstadoFuncional implements Serializable{

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioEstadoFuncional 	negocio;
	private PerEstadoFuncional				perEstadoFuncional;
	private List<EstadoFuncional>			    estados;
	private EstadoFuncional                     estado;
	
	private NegocioEstadoFuncional(){
		perEstadoFuncional = new PerEstadoFuncional(); 
	}

	public  static synchronized NegocioEstadoFuncional getInstance() {
		if (negocio == null)
			negocio = new NegocioEstadoFuncional();
		return negocio;
	}

	public List<EstadoFuncional> getTodos() throws ExcFiltroExcepcion{
		List<EstadoFuncional> estados = null;
		try {
			estados = perEstadoFuncional.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return estados;
	}

	public static NegocioEstadoFuncional getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioEstadoFuncional negocio) {
		NegocioEstadoFuncional.negocio = negocio;
	}

	public PerEstadoFuncional getPerEstadoFuncinal() {
		return perEstadoFuncional;
	}

	public void setPerFallaRecepcionSilo(PerEstadoFuncional perFallaRecepcionSilo) {
		this.perEstadoFuncional = perFallaRecepcionSilo;
	}
	public List<EstadoFuncional> get() {
		return estados;
	}

	public void setEstadoFuncional(List<EstadoFuncional> estados) {
		this.estados = estados;
	}
	public EstadoFuncional getEstadoFuncional(Integer indice) {
		return new PerEstadoFuncional().buscarId(indice);
	}
	
	public void setEstadoFuncional(EstadoFuncional estado) {
		this.estado = estado;
	}
	
	public EstadoFuncional getEstadoFuncional() {
		return estado;
	}
	
	
	public void guardar() throws Exception{
		perEstadoFuncional.guardar(estado, estado.getId());
	}
	
	public void eliminar() throws Exception{
		perEstadoFuncional.borrar(estado);
	}
}
