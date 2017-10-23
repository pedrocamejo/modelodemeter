package cpc.negocio.sigesp;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.sigesp.basico.Cargo;
import cpc.persistencia.sigesp.implementacion.PerCargo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCargo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3522271158206042426L;
	private static NegocioCargo 	negocio;
	private PerCargo				perCargo;
	private List<Cargo>			    cargos;
	private Cargo				    cargo;
	
	private NegocioCargo(){
		perCargo = new PerCargo(); 
	}

	public  static synchronized NegocioCargo getInstance() {
		if (negocio == null)
			negocio = new NegocioCargo();
		return negocio;
	}

	public List<Cargo> getTodos() throws ExcFiltroExcepcion{
		List<Cargo> cargos = null;
		try {
			cargos = perCargo.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return cargos;
	}

	public static NegocioCargo getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioCargo negocio) {
		NegocioCargo.negocio = negocio;
	}

	public PerCargo getPerCargo() {
		return perCargo;
	}

	public void setPerCargo(PerCargo perCargo) {
		this.perCargo = perCargo;
	}
	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargo(List<Cargo> cargos) {
		this.cargos = cargos;
	}
	public Cargo getCargo(Integer indice) {
		return perCargo.buscarId(indice);
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public void guardar()  throws Exception{
		this.perCargo.guardar(cargo, cargo.getId());
	}
	
	public void eliminar() throws Exception{
		this.perCargo.borrar(cargo);
	}
}
