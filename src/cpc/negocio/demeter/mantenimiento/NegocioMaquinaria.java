package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.demeter.mantenimiento.TipoMedidaRendimiento;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMaquinaria;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMedidaRendimiento;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMaquinaria implements Serializable{
	
	
	private static final long serialVersionUID = 4190003627084637924L;
	private static NegocioMaquinaria 	negocio;
	private PerMaquinaria				perMaquinaria;
	private List<Maquinaria>			maquinarias;
	private Maquinaria					maquinaria;
	
	private NegocioMaquinaria(){
		perMaquinaria = new PerMaquinaria(); 
	}

	public  static synchronized NegocioMaquinaria getInstance() {
		if (negocio == null)
			negocio = new NegocioMaquinaria();
		return negocio;
	}

	public List<Maquinaria> getTodos() throws ExcFiltroExcepcion{
		List<Maquinaria> maquinarias = null;
		try {
			maquinarias = perMaquinaria.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return maquinarias;
	}

	public static NegocioMaquinaria getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioMaquinaria negocio) {
		NegocioMaquinaria.negocio = negocio;
	}

	public PerMaquinaria getPerMaquinaria() {
		return perMaquinaria;
	}

	public void setPerMaquinarias(PerMaquinaria perMaquinaria) {
		this.perMaquinaria = perMaquinaria;
	}
	public List<Maquinaria> getMaquinarias() {
		return maquinarias;
	}

	public void setMaquinarias(List<Maquinaria> maquinarias) {
		this.maquinarias = maquinarias;
	}
	public Maquinaria getMaquinaria(Long indice) {
		return perMaquinaria.buscarId(indice);
	}
	
	public void setMaquinaria(Maquinaria maquinaria) {
		this.maquinaria = maquinaria;
	}
	public void guardar() throws Exception{
		perMaquinaria.guardar(maquinaria, maquinaria.getId());
	}
	
	public void eliminar() throws Exception{
		perMaquinaria.borrar(maquinaria);
	}
	
	public List<Trabajador> getOperadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getOperadores();
	}
	
	public List<TipoMedidaRendimiento> getTipoMedidasRendimiento() throws ExcFiltroExcepcion{
		return new PerTipoMedidaRendimiento().getAll();
	}
}
