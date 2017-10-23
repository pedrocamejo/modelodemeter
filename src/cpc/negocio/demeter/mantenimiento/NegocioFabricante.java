package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import java.io.Serializable;
import cpc.modelo.demeter.mantenimiento.Fabricante;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerFabricante;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioFabricante implements Serializable{

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioFabricante 	negocio;
	private PerFabricante				perFabricante;
	private List<Fabricante>			fabricantes;
	private Fabricante                  fabricante;
	
	private NegocioFabricante(){
		perFabricante = new PerFabricante(); 
	}

	public  static synchronized NegocioFabricante getInstance() {
		if (negocio == null)
			negocio = new NegocioFabricante();
		return negocio;
	}

	public List<Fabricante> getTodos() throws ExcFiltroExcepcion{
		List<Fabricante> fabricantes = null;
		try {
			fabricantes = perFabricante.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return fabricantes;
	}

	public static NegocioFabricante getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioFabricante negocio) {
		NegocioFabricante.negocio = negocio;
	}

	public PerFabricante getPerFabricante() {
		return perFabricante;
	}

	public void setPerFabricante(PerFabricante perFabricante) {
		this.perFabricante = perFabricante;
	}
	public List<Fabricante> get() {
		return fabricantes;
	}

	public void setFabricante(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}
	public Fabricante getFabricante(Integer indice) {
		return new PerFabricante().buscarId(indice);
	}
	
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	public Fabricante getFabricante() {
		return fabricante;
	}
	
	
	public void guardar() throws Exception{
		perFabricante.guardar(fabricante, fabricante.getId());
	}
	
	public void eliminar() throws Exception{
		perFabricante.borrar(fabricante);
	}
}
