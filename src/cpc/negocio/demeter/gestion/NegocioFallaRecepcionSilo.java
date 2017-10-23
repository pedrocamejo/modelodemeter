package cpc.negocio.demeter.gestion;

import java.util.List;

import java.io.Serializable;
import cpc.modelo.demeter.gestion.FallaRecepcionSilo;
import cpc.persistencia.demeter.implementacion.gestion.PerFallaRecepcionSilo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioFallaRecepcionSilo implements Serializable{

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioFallaRecepcionSilo 	negocio;
	private PerFallaRecepcionSilo				perFallaRecepcionSilo;
	private List<FallaRecepcionSilo>			    fallas;
	private FallaRecepcionSilo                       falla;
	
	private NegocioFallaRecepcionSilo(){
		perFallaRecepcionSilo = new PerFallaRecepcionSilo(); 
	}

	public  static synchronized NegocioFallaRecepcionSilo getInstance() {
		if (negocio == null)
			negocio = new NegocioFallaRecepcionSilo();
		return negocio;
	}

	public List<FallaRecepcionSilo> getTodos() throws ExcFiltroExcepcion{
		List<FallaRecepcionSilo> fallas = null;
		try {
			fallas = perFallaRecepcionSilo.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return fallas;
	}

	public static NegocioFallaRecepcionSilo getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioFallaRecepcionSilo negocio) {
		NegocioFallaRecepcionSilo.negocio = negocio;
	}

	public PerFallaRecepcionSilo getPerFallaRecepcionSilo() {
		return perFallaRecepcionSilo;
	}

	public void setPerFallaRecepcionSilo(PerFallaRecepcionSilo perFallaRecepcionSilo) {
		this.perFallaRecepcionSilo = perFallaRecepcionSilo;
	}
	public List<FallaRecepcionSilo> get() {
		return fallas;
	}

	public void setFallaRecepcionSilo(List<FallaRecepcionSilo> fallas) {
		this.fallas = fallas;
	}
	public FallaRecepcionSilo getFallaRecepcionSilo(Integer indice) {
		return new PerFallaRecepcionSilo().buscarId(indice);
	}
	
	public void setFallaRecepcionSilo(FallaRecepcionSilo falla) {
		this.falla = falla;
	}
	
	public FallaRecepcionSilo getFallaRecepcionSilo() {
		return falla;
	}
	
	
	public void guardar() throws Exception{
		perFallaRecepcionSilo.guardar(falla, falla.getId());
	}
	
	public void eliminar() throws Exception{
		perFallaRecepcionSilo.borrar(falla);
	}
}
