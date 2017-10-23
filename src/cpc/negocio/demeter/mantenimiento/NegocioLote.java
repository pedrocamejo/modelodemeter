package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import java.io.Serializable;
import cpc.modelo.demeter.mantenimiento.Lote;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerLote;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioLote implements Serializable{

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioLote 	negocio;
	private PerLote perLote;
	private List<Lote>			   lotes;
	private Lote                    lote;
	
	private NegocioLote(){
		perLote = new PerLote(); 
	}

	public  static synchronized NegocioLote getInstance() {
		if (negocio == null)
			negocio = new NegocioLote();
		return negocio;
	}

	public List<Lote> getTodos() throws ExcFiltroExcepcion{
		List<Lote> lotes = null;
		try {
			lotes = perLote.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lotes;
	}

	public static NegocioLote getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioLote negocio) {
		NegocioLote.negocio = negocio;
	}

	public PerLote getPerLote() {
		return perLote;
	}

	public void setPerLote(PerLote perLote) {
		this.perLote = perLote;
	}
	public List<Lote> get() {
		return lotes;
	}

	public void setFallaRecepcionSilo(List<Lote> lotes) {
		this.lotes = lotes;
	}
	public Lote getLote(Integer indice) {
		return new PerLote().buscarId(indice);
	}
	
	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	public Lote getLote() {
		return lote;
	}
	
	
	public void guardar() throws Exception{
		perLote.guardar(lote, lote.getId());
	}
	
	public void eliminar() throws Exception{
		perLote.borrar(lote);
	}
}
