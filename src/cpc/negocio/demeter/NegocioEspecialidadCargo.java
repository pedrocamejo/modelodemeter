package cpc.negocio.demeter;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.basico.EspecilidadCargo;
import cpc.persistencia.demeter.implementacion.PerEspecialidadCargo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioEspecialidadCargo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 192698714218235539L;
	private static NegocioEspecialidadCargo 	negocio;
	private PerEspecialidadCargo				perEspecialidad;
	private List<EspecilidadCargo>				especialidades;
	private EspecilidadCargo					especialidad;
	
	private NegocioEspecialidadCargo(){
		perEspecialidad = new PerEspecialidadCargo(); 
	}

	public  static synchronized NegocioEspecialidadCargo getInstance() {
		if (negocio == null)
			negocio = new NegocioEspecialidadCargo();
		return negocio;
	}

	public List<EspecilidadCargo> getTodos() throws ExcFiltroExcepcion{
		List<EspecilidadCargo> condiciones = null;
		try {
			condiciones = perEspecialidad.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return condiciones;
	}

	public static NegocioEspecialidadCargo getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioEspecialidadCargo negocio) {
		NegocioEspecialidadCargo.negocio = negocio;
	}

	public PerEspecialidadCargo getPerEspecialidadCargo() {
		return perEspecialidad;
	}

	public void setPerEspecialidadCargo(PerEspecialidadCargo perEspecialidadCargo) {
		this.perEspecialidad = perEspecialidadCargo;
	}
	
	public List<EspecilidadCargo> getEspecialidadCargo() {
		return especialidades;
	}

	public void setEspecialidadCargo(List<EspecilidadCargo> especialidades) {
		this.especialidades = especialidades;
	}
	
	public EspecilidadCargo getEspecialidadCargo(Integer indice) {
		return perEspecialidad.buscarId(indice);
	}
	
	public void setEspecialidadCargo(EspecilidadCargo especialidad) {
		this.especialidad = especialidad;
	}
	
	public void guardar()  throws Exception{
		perEspecialidad.guardar(especialidad, especialidad.getId());
	}
	
	public void eliminar() throws Exception{
		perEspecialidad.borrar(especialidad);
	}
}