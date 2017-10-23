package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.TrasladoActivo;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.ActivoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoImpropio;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaImpropia;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerTrasladoActivo;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioReporteActivo implements Serializable{

	private static final long serialVersionUID = -6937957103381578940L;
	private static NegocioReporteActivo negocio;
	
	private NegocioReporteActivo(){
		
	}
	
	public static synchronized NegocioReporteActivo getInstance(){
		if (negocio == null)
			negocio = new NegocioReporteActivo();
		return negocio;
		
	}
	
	public List<ActivoAlmacen> obtenerAlmacenesActivos(){
		return new PerActivoAlmacen().getTodos();
	}
	
	public List<TrasladoActivo> obtenerMovimientosPorFecha( Date fechaIncial, Date fechaFinal){
		List<TrasladoActivo> lista = new ArrayList<TrasladoActivo>();
		PerTrasladoActivo persistencia = new PerTrasladoActivo();
		for (TrasladoActivo item : persistencia.obtenerTodosPorFecha(fechaIncial, fechaFinal)){
			lista.add(persistencia.getDatos(item));
		}
		return lista;
	}
	
	public UnidadAdministrativa obtenerUnidadAdministrativa(){
		PerControlSede perControl = new PerControlSede();
		UnidadAdministrativa unidadAdministrativa = null;
		try {
			for (ControlSede item : perControl.getAll())
				unidadAdministrativa = item.getUnidadAdministrativa();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return unidadAdministrativa;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerTrasladosPorFecha(Date fechaIncial, Date fechaFinal){
		List<Object[]> lista = null; 
		try {
			lista = new PerTrasladoActivo().obtenerListaPorFecha(fechaIncial, fechaFinal);
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> obtenerMovimientosPorActivo(Activo activoInicial, Activo activoFinal){
		List<Object[]> lista = null; 
		try {
			lista = new PerTrasladoActivo().obtenerListaPorActivo(activoInicial, activoFinal);
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<Activo> obtenerActivos(){
		return new PerActivo().getTodosConAlmacen();
	}
	
	 public Object[] getTodaMaquinariaEimplementoSede() throws ExcFiltroExcepcion{
		 Object[] maquinarias = new Object[3];
		 maquinarias[0]=  new PerActivoAlmacen().getTodos();
		 maquinarias[1]=  new PerMaquinariaImpropia().getAll();
		 maquinarias[2]=  new PerImplementoImpropio().getAll();
		 return maquinarias;
	 }
	 /*
	 public Object[] getMaquinariaEimplementoCampo() throws ExcFiltroExcepcion{
		 Object[] maquinarias = new Object[3];
		 maquinarias[0]=  new PerMaquinariaUnidad().getMaquinariasEnCampo();
		 //maquinarias[1]=  new PerImplementoUnidad().getImplementosEnCampo();
		 //maquinarias[2]=  new PerImplementoImpropio().getAll();
		 return maquinarias;
	 }*/
	 public List<Object> getMaquinariaEimplementoCampo2() throws ExcFiltroExcepcion{
		 
		 return new PerMaquinariaUnidad().getMaquinariasEnCampo2();
	 }
	 
}
