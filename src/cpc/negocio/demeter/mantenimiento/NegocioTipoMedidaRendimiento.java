package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import java.io.Serializable;
import cpc.modelo.demeter.mantenimiento.TipoMedidaRendimiento;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMedidaRendimiento;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTipoMedidaRendimiento implements Serializable{

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioTipoMedidaRendimiento 	negocio;
	private PerTipoMedidaRendimiento				perTipoMedidaRendimiento;
	private List<TipoMedidaRendimiento>			     tipos;
	private TipoMedidaRendimiento                       tipo;
	
	private NegocioTipoMedidaRendimiento(){
		perTipoMedidaRendimiento = new PerTipoMedidaRendimiento(); 
	}

	public  static synchronized NegocioTipoMedidaRendimiento getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoMedidaRendimiento();
		return negocio;
	}

	public List<TipoMedidaRendimiento> getTodos() throws ExcFiltroExcepcion{
		List<TipoMedidaRendimiento> tipos = null;
		try {
			tipos = perTipoMedidaRendimiento.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return tipos;
	}

	public static NegocioTipoMedidaRendimiento getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioTipoMedidaRendimiento negocio) {
		NegocioTipoMedidaRendimiento.negocio = negocio;
	}

	public PerTipoMedidaRendimiento getPerTipoMedidaRendimiento() {
		return perTipoMedidaRendimiento;
	}

	public void setPerTipoMedidaRendimiento(PerTipoMedidaRendimiento perTipoMedidaRendimiento) {
		this.perTipoMedidaRendimiento = perTipoMedidaRendimiento;
	}
	public List<TipoMedidaRendimiento> get() {
		return tipos;
	}

	public void setTipoMedidaRendimiento(List<TipoMedidaRendimiento> tipos) {
		this.tipos = tipos;
	}
	public TipoMedidaRendimiento getTipoMedidaRendimiento(Integer indice) {
		return new PerTipoMedidaRendimiento().buscarId(indice);
	}
	
	public void setTipoMedidaRendimiento(TipoMedidaRendimiento tipo) {
		this.tipo = tipo;
	}
	
	public TipoMedidaRendimiento getTipoMedidaRendimiento() {
		return tipo;
	}
	
	
	public void guardar() throws Exception{
		perTipoMedidaRendimiento.guardar(tipo, tipo.getId());
	}
	
	public void eliminar() throws Exception{
		perTipoMedidaRendimiento.borrar(tipo);
	}
}
