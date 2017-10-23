package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.mantenimiento.TipoFalla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoFalla;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTipoFalla implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioTipoFalla 	negocio;
	private PerTipoFalla				perTipoFalla;
	private List<TipoFalla>			    fallas;
	private TipoFalla				    falla;
	
	private NegocioTipoFalla(){
		perTipoFalla = new PerTipoFalla(); 
	}

	public  static synchronized NegocioTipoFalla getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoFalla();
		return negocio;
	}

	public List<TipoFalla> getTodos() throws ExcFiltroExcepcion{
		List<TipoFalla> fallas = null;
		try {
			fallas = perTipoFalla.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return fallas;
	}

	public static NegocioTipoFalla getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioTipoFalla negocio) {
		NegocioTipoFalla.negocio = negocio;
	}

	public PerTipoFalla getPerFalla() {
		return perTipoFalla;
	}

	public void setPerTipoFallas(PerTipoFalla perTipoFalla) {
		this.perTipoFalla = perTipoFalla;
	}
	public List<TipoFalla> getTipoFallas() {
		return fallas;
	}

	public void setTipoFallas(List<TipoFalla> fallas) {
		this.fallas = fallas;
	}
	public TipoFalla getTipoFalla(Integer indice) {
		return perTipoFalla.buscarId(indice);
	}
	
	public void setTipoFalla(TipoFalla falla) {
		this.falla = falla;
	}
	public void guardar() throws Exception{
		perTipoFalla.guardar(falla, falla.getId());
	}
	
	public void eliminar() throws Exception{
		perTipoFalla.borrar(falla);
	}
}
