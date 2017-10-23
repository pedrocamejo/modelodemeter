package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.mantenimiento.Falla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerFallaBienProduccion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioFallaBienProduccion implements Serializable{
	
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioFallaBienProduccion 	negocio;
	private PerFallaBienProduccion				perFallaBienProduccion;
	private Falla				    falla;
	
	private NegocioFallaBienProduccion(){
		perFallaBienProduccion = new PerFallaBienProduccion(); 
	}

	public  static synchronized NegocioFallaBienProduccion getInstance() {
		if (negocio == null)
			negocio = new NegocioFallaBienProduccion();
		return negocio;
	}

	public List<Falla> getTodos() throws ExcFiltroExcepcion{
		List<Falla> fallas = null;
		try {
			fallas = perFallaBienProduccion.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return fallas;
	}

	public static NegocioFallaBienProduccion getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioFallaBienProduccion negocio) {
		NegocioFallaBienProduccion.negocio = negocio;
	}

	public PerFallaBienProduccion getPerFallaBienProduccion() {
		return perFallaBienProduccion;
	}

	public void setPerTipoFallas(PerFallaBienProduccion perTipoFalla) {
		this.perFallaBienProduccion = perTipoFalla;
	}	

	
	public Falla getFallaBienProduccion(Integer indice) {
		return perFallaBienProduccion.buscarId(indice);
	}
	
	public void setFallaBienProduccion(Falla falla) {
		this.falla = falla;
	}
	public void guardar() throws Exception{
		perFallaBienProduccion.guardar(falla, falla.getId());
	}
	
	public void eliminar() throws Exception{
		perFallaBienProduccion.borrar(falla);
	}
}
