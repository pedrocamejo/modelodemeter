package cpc.negocio.demeter.gestion;

import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.TrabajoRealizadoMecanizado;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Activo;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerTrabajoMecanizado;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioTrabajoMecanizado extends NegocioGenerico<TrabajoRealizadoMecanizado, PerTrabajoMecanizado, Long>{

	private static final long serialVersionUID = 4973876525728276246L;
	private static NegocioTrabajoMecanizado 		negocio;
	
	private NegocioTrabajoMecanizado(){
		setPersistencia(new PerTrabajoMecanizado());
	}
	
	public  static synchronized NegocioTrabajoMecanizado getInstance() {
		if (negocio == null)
			negocio = new NegocioTrabajoMecanizado();
		return negocio;
	}

	public void setTrabajoMecanizado(TrabajoRealizadoMecanizado orden) throws ExcFiltroExcepcion{
		if (orden!= null){
			orden = getPersistencia().getDatos(orden);
		}
		super.setModelo(orden);
	}
	
	public OrdenTrabajoMecanizado detDetalleOrdenTrabajo(OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		if (orden!= null){
			orden = new PerOrdenTrabajoMecanizado().getDatos(orden);
		}
		return orden;
	}

	public TrabajoRealizadoMecanizado  getNuevaOrdenTrabajo() throws ExcDatosNoValido{
		setModelo(new TrabajoRealizadoMecanizado());
		try{
			getModelo().setFecha(new Date());
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la sede");
		}
		return getModelo();
	}
	
	public List<Trabajador> getOperadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getOperadores();
	}
	
	public List<OrdenTrabajoMecanizado> getOrdenesTrabajos() throws ExcFiltroExcepcion{
		return new PerOrdenTrabajoMecanizado().getActivas();
	}
	public List<OrdenTrabajoMecanizado> getOrdenesTrabajosProject() throws ExcFiltroExcepcion{
		return new PerOrdenTrabajoMecanizado().getActivasProject();
	}
	
	public OrdenTrabajoMecanizado getOrdenTrabajoProject(OrdenTrabajoMecanizado ordenTrabajoMecanizado) throws ExcFiltroExcepcion{
		ordenTrabajoMecanizado = new PerOrdenTrabajoMecanizado().getOrdenTrabajoMecanizadoProject(ordenTrabajoMecanizado);
		return  ordenTrabajoMecanizado;
	}
	
	public UnidadMedida getUnidadProduccion(Labor labor) throws ExcFiltroExcepcion{
		List<UnidadMedida> medidas = new PerUnidadMedida().getAll(labor.getMedidaGestion());
		for(UnidadMedida item :medidas){
			if (item.getTipo().equals(labor.getServicio().getTipoUnidadProduccion())){
				return item;
			}
		}
		return null;
	}
	
	public List<Activo> getImplementos(UnidadFuncional unidad) throws ExcFiltroExcepcion {
		return new PerActivoAlmacen().getTodosImplementos(unidad);
	}
	
	public List<Activo> getMaquinarias(UnidadFuncional unidad) throws ExcFiltroExcepcion {
		return new PerActivoAlmacen().getTodosMaquinarias(unidad);
	}
	
	public List<UnidadFuncional> getUnidadesEjecutoras() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	
	
	public List<TrabajoRealizadoMecanizado> getTodosProject() throws ExcFiltroExcepcion{
		return new PerTrabajoMecanizado().getTodosProject();
	}
	
	public TrabajoRealizadoMecanizado getTrabajoRealizadoMecanizadoProject(TrabajoRealizadoMecanizado realizadoMecanizado){
		realizadoMecanizado = new PerTrabajoMecanizado().getTrabajoMecanizadoProject(realizadoMecanizado);
		return realizadoMecanizado;
	}
	}
