package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.Sustitucion;
import cpc.modelo.demeter.mantenimiento.RegistroFalla;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerSustitucion;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSustitucion implements Serializable{
	
	
	private static final long serialVersionUID = 5307953852788352132L;
	private static NegocioSustitucion 	negocio;
	private PerSustitucion				persistencia;
	@SuppressWarnings("unused")
	private List<Sustitucion>			sustituciones;
	private Sustitucion				    sustitucion;
	
	private NegocioSustitucion(){
		persistencia = new PerSustitucion(); 
	}

	public  static synchronized NegocioSustitucion getInstance() {
		if (negocio == null)
			negocio = new NegocioSustitucion();
		return negocio;
	}

	public List<Sustitucion> getTodos() throws ExcFiltroExcepcion{
		List<Sustitucion> sustitucion = null;
		try {//prueba 
		//	sustitucion = persistencia.getAll();
			sustitucion = persistencia.getAllSustitucion();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return sustitucion;
	}
	
	public List<Sustitucion> getTodosProject() throws ExcFiltroExcepcion{
		List<Sustitucion> sustitucion = null;
		try {//prueba 
		//	sustitucion = persistencia.getAll();
			sustitucion = persistencia.getAllSustitucionProject();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return sustitucion;
	}

	public Sustitucion getSustitucionProject(Sustitucion sustitucion){
		sustitucion = persistencia.getSustitucionProject(sustitucion);
				return sustitucion;
		
	}
	
	public static NegocioSustitucion getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioSustitucion negocio) {
		NegocioSustitucion.negocio = negocio;
	}	
	
	public List<Sustitucion> getSustituciones() throws ExcFiltroExcepcion {		
		return getTodos();
	}

	public void setEstadoFuncional(List<Sustitucion> sustituciones) {
		this.sustituciones = sustituciones;
	}
	public Sustitucion getSustitucion (Integer indice) {
		return persistencia.buscarId(indice);
	}
	
	public void setSustitucion(Sustitucion sustitucion) {
		this.sustitucion = sustitucion;
	}
	public void guardar(List<DetalleMaquinariaOrdenTrabajo> listaDetalle, RegistroFalla registroFalla) throws ExcFiltroExcepcion{
		persistencia.guardar(sustitucion,listaDetalle,registroFalla);
	}
	
	public void eliminar() throws Exception{
		persistencia.borrar(sustitucion);
	}
	
	public List<UnidadFuncional> obteneUnidadesFuncionales() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	
	public List<MaquinariaUnidad> obtenerMaquinariasPorUnidadFuncional(UnidadFuncional und) throws ExcFiltroExcepcion{
		return new PerMaquinariaUnidad().getMaquinaria(und);
	}
	
	public List<Trabajador> obtenerOperadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getAll();
	}
	
	public List<DetalleMaquinariaOrdenTrabajo> obtenerOrdenesPorMaquinaria(MaquinariaUnidad ma) throws ExcFiltroExcepcion{
		return new PerDetalleOrdenTrabajo().getAllPorMaquinaria(ma, new PerOrdenTrabajoMecanizado().getActivas());
	}
	
	public List<DetalleMaquinariaOrdenTrabajo> obtenerOrdenesPorOperador(Trabajador operador) throws ExcFiltroExcepcion{
		return new PerDetalleOrdenTrabajo().getAllPorOperador(operador,new PerOrdenTrabajoMecanizado().getActivas());
	}
	
	public List<DetalleMaquinariaOrdenTrabajo> obtenerOrdenesPorMaquinariaYOperador(MaquinariaUnidad ma,Trabajador operador) throws ExcFiltroExcepcion{
		return new PerDetalleOrdenTrabajo().getAllPorMaquinariaYOperador(ma,operador,new PerOrdenTrabajoMecanizado().getActivasRecientes());
	}
	
	
}
