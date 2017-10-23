package cpc.negocio.demeter.gestion;


import java.util.Date;
import java.util.List;
import org.zkoss.zkplus.spring.SpringUtil;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.UnidadArrime;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.TipoUnidadMedida;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTransporteProduccion;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.OrdenTransporteProduccion;
import cpc.modelo.demeter.gestion.TrabajoRealizadoMecanizado;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.basico.Usos;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerSede;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerRubro;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.basico.PerUnidadArrime;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerControlUnidadFuncional;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleOrdenTransporteProduccion;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTransporteProduccion;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerTrabajoMecanizado;
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cpc.persistencia.ministerio.basico.PerUsos;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioOrdenTransporteProduccion extends NegocioGenerico<OrdenTransporteProduccion, PerOrdenTransporteProduccion, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioOrdenTransporteProduccion 		negocio;
	
	private NegocioOrdenTransporteProduccion(){
		setPersistencia(new PerOrdenTransporteProduccion());
	}
	
	public  static synchronized NegocioOrdenTransporteProduccion getInstance() {
		if (negocio == null)
			negocio = new NegocioOrdenTransporteProduccion();
		return negocio;
	}

	public void setOrdenTrabajo(OrdenTransporteProduccion orden) throws ExcFiltroExcepcion{
		if (orden!= null){
			orden = getPersistencia().getDatos(orden);
			orden.setDetalles(new PerDetalleOrdenTrabajo().getTodos(orden));
			Productor cliente = new PerProductor().getDatos(orden.getProductor().getId());
			orden.setProductor(cliente);
		}
		super.setModelo(orden);
	}
	
	public OrdenTransporteProduccion  getNuevaOrdenTrabajo() throws ExcDatosNoValido{
		setModelo(new OrdenTransporteProduccion());
		try{
			getModelo().setFecha(new Date());
			getModelo().setTipo(new PerTipoTrabajo().getTipoTransporte());
			getModelo().setEstado(new PerEstadoOrdenTrabajo().getInicio());
			getModelo().setSede(getSede());
			getModelo().setCerrada(false);
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la sede");
		}
		return getModelo();
	}
	
	public List<Trabajador> getConductores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getConductores();
	}
	
	public List<Usos> getUsos() throws ExcFiltroExcepcion{
		return new PerUsos().getAll();
	}
	
	public List<ImplementoUnidad> getImplementos(UnidadFuncional unidad) throws ExcFiltroExcepcion {
		return new PerImplementoUnidad().getImplementos(unidad);
	}
	
	public List<MaquinariaUnidad> getMaquinarias(UnidadFuncional unidad) throws ExcFiltroExcepcion {
		return new PerMaquinariaUnidad().getMaquinaria(unidad);
	}
	
	public List<UnidadFuncional> getUnidadesEjecutoras() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	
	public List<Productor> getProductores() throws ExcFiltroExcepcion{
		return new PerProductor().getAll();
	}
	
	public List<Labor> getLabores(){
		TipoServicio tipo = new PerTipoServicio().getTipoTransporte();
		return new PerLabor().getAll(tipo);
	}
	
	public List<Rubro> getRubros() throws ExcFiltroExcepcion{
		return new PerRubro().getAll();
	}
	
	public List<UnidadArrime> getSilos() throws ExcFiltroExcepcion{
		return new PerUnidadArrime().getAll();
	}
	
	
	public LaborOrdenServicio getDetalleLabor(Labor labor) throws ExcFiltroExcepcion{
		LaborOrdenServicio laborOS = new LaborOrdenServicio();
		Servicio servicio = labor.getServicio();
		if (servicio.getTipoUnidadTrabajo() == null)
			throw new ExcFiltroExcepcion("Problemas con la unidad de Trabajo del servicio");
		laborOS.setLabor(labor);
		laborOS.setCantidadVisible(servicio.getManejaCantidades());
		laborOS.setPasesVisible(servicio.getManejaPases());
		List<UnidadMedida> medidas = new PerUnidadMedida().getAll(labor.getMedidaGestion());
		for(UnidadMedida item :medidas){
			if (item.getTipo().equals(servicio.getTipoUnidadTrabajo())){
				laborOS.setUnidad(item);
			}
		}
		if (laborOS.getUnidad() == null)
			throw new ExcFiltroExcepcion("No existe relacion entre unidad de trabajo y de gestion");
		return laborOS;
	}
	
	public UnidadMedida getUnidadTrabajo(Labor labor) throws ExcFiltroExcepcion{
		List<UnidadMedida> medidas = new PerUnidadMedida().getAll(labor.getMedidaGestion());
		for(UnidadMedida item :medidas){
			if (item.getTipo().equals(labor.getServicio().getTipoUnidadTrabajo())){
				return item;
			}
		}
		return null;
	}
	

	
	public DetalleOrdenTrabajo getDetallesOrden(OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		try{
			List<DetalleOrdenTrabajo> detalles = new PerDetalleOrdenTrabajo().getTodos(orden);
			if (detalles != null && detalles.size()>0)
				return detalles.get(0);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return null;
	}
	
	public Double getProduccionOrden(OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		try{
			List<TrabajoRealizadoMecanizado> detalles = new PerTrabajoMecanizado().getTrabajos(orden);
			if (detalles != null && detalles.size()>0)
				return null;
			double salida = 0;
			for (TrabajoRealizadoMecanizado item: detalles){
				salida += item.getCantidadProduccion(); 
			}
			return salida;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
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
	
	public List<UnidadMedida> getUnidadesMedida(UnidadMedida unidad) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(unidad); 
	}
	
	public List<UnidadMedida> getUnidadesMedida(TipoUnidadMedida unidad) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(unidad); 
	}

	public List<OrdenTransporteProduccion> getOrdenesCerrado() throws ExcFiltroExcepcion{
		return getPersistencia().getAll(new PerEstadoOrdenTrabajo().getTerminado());
	}
	

	public List<Trabajador> getChoferes() throws ExcFiltroExcepcion{
		return new PerTrabajador().getConductores();
	}
	
	public List<OrdenTrabajoMecanizado> getOrdenesProduccion() throws ExcFiltroExcepcion{
		return new PerOrdenTrabajoMecanizado().getOrdenesProduccionCerradas();
	}
	
	public List<Servicio> getServicios(){
		TipoServicio tipo = new PerTipoServicio().getTipoTransporteCosecha();
		return new PerServicio().getAll(tipo);
	}
	
	public Productor enriqueserProductor(Productor productor) throws ExcFiltroExcepcion{
		return new PerProductor().getDatos(productor.getId());
	}
	
	public void guardar(OrdenTransporteProduccion dato) throws ExcFiltroExcepcion {
		if (dato.getUnidadFuncional() == null)
			throw new ExcFiltroExcepcion("No se definio Unidad Ejecutora");
		if (dato.getDetalles() != null)
			for (DetalleOrdenTrabajo item :dato.getDetalles()){
				item.setOrden(dato);
			}
		dato.setSede(getSede());
		ControlUnidadFuncional control = new PerControlUnidadFuncional().getControl(dato.getUnidadFuncional());
		getPersistencia().guardar(dato, dato.getId(), control);
	}
		
	public void cerrar(OrdenTransporteProduccion dato) throws ExcFiltroExcepcion {
		if (dato.getDetalles() != null)
			for (DetalleOrdenTrabajo item :dato.getDetalles()){
				item.setOrden(dato);
			}
		getPersistencia().cerrar(dato, dato.getId());
	}
	
	public void anular(OrdenTransporteProduccion dato) throws ExcFiltroExcepcion {
		getPersistencia().anular(dato, dato.getId());
	}
	
	private Sede getSede(){
		String  idSede= (String) SpringUtil.getBean("idsede");
		String  idEmpresa= (String) SpringUtil.getBean("idEmpresa");
		return new PerSede().buscarId(new SedePK(idEmpresa, idSede));
	}
	
	public UnidadArrime getDestinoReal(OrdenTransporteProduccion orden) throws ExcFiltroExcepcion{
		return new PerDetalleOrdenTransporteProduccion().getActiva(orden).getDestino();
	}
	
	public List<DetalleOrdenTransporteProduccion> getDetallesRutasOrden(OrdenTransporteProduccion orden) throws ExcFiltroExcepcion{
		return new PerDetalleOrdenTransporteProduccion().getTodas(orden);
	}
	
}
