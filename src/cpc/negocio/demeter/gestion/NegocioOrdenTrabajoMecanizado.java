package cpc.negocio.demeter.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.PlanServicio;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.OperadorOrdenMecanizado;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.TrabajoRealizado;
import cpc.modelo.demeter.gestion.TrabajoRealizadoMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.mantenimiento.MomentoFalla;
import cpc.modelo.demeter.mantenimiento.TipoFalla;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerSede;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.administrativo.PerContratoMecanizado;
import cpc.persistencia.demeter.implementacion.basico.PerCicloProductivo;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerPlanServicio;
import cpc.persistencia.demeter.implementacion.basico.PerRubro;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerControlUnidadFuncional;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerSolicitudMecanizado;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerTrabajoMecanizado;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMomentoFalla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoFalla;
import cpc.persistencia.ministerio.basico.PerInstitucionCrediticia;
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerSector;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioOrdenTrabajoMecanizado extends NegocioGenerico<OrdenTrabajoMecanizado, PerOrdenTrabajoMecanizado, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioOrdenTrabajoMecanizado 		negocio;
	
	private NegocioOrdenTrabajoMecanizado(){
		setPersistencia(new PerOrdenTrabajoMecanizado());
	}
	
	public  static synchronized NegocioOrdenTrabajoMecanizado getInstance() {
		if (negocio == null)
			negocio = new NegocioOrdenTrabajoMecanizado();
		return negocio;
	}

	public OrdenTrabajoMecanizado setOrdenTrabajo(OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		if (orden!= null){
			orden = getPersistencia().getDatos(orden);
			orden.setDetalles(new PerDetalleOrdenTrabajo().getTodos(orden));
			Productor cliente = new PerProductor().getDatos(orden.getProductor().getId());
			orden.setProductor(cliente);
			orden.setTrabajosRealizadosMecanizado(actualizarTrabajos(orden.getTrabajosRealizados()));
		}
		//super.setModelo(orden);
		return orden;
	}
	
	
	public OrdenTrabajoMecanizado  getNuevaOrdenTrabajo() throws ExcDatosNoValido{
		OrdenTrabajoMecanizado nuevo = new OrdenTrabajoMecanizado();
		try{
			nuevo.setFecha(new Date());
			nuevo.setTipo(new PerTipoTrabajo().getTipoMecanizado());
			nuevo.setEstado(new PerEstadoOrdenTrabajo().getInicio());
			nuevo.setSede(getSede());
			nuevo.setCerrada(false);
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la sede");
		}
		return nuevo;
	}
	
	public List<Trabajador> getOperadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getOperadores();
	}
	
	public List<InstitucionCrediticia> getFinanciamientos() throws ExcFiltroExcepcion{
		return new PerInstitucionCrediticia().getAll();
	}
	
	public List<CicloProductivo> getCiclosProductivos() throws ExcFiltroExcepcion{
		return new PerCicloProductivo().getAll();
	}
	
	public List<OperadorOrdenMecanizado> getListadoOperador(Date inicio, Date fin, UbicacionSector sector) throws ExcFiltroExcepcion{
		if (sector!=null)
		return getPersistencia().getListadoOperador(inicio, fin, sector);
		else return getPersistencia().getListadoOperador(inicio, fin);
	}
	
	public List<UbicacionSector> getSectores() throws ExcFiltroExcepcion{
		return new PerSector().getAll();
	}
	
	public List<PlanServicio> getPlanesServicios() throws ExcFiltroExcepcion{
		return new PerPlanServicio().getAll();
	}
	
	public List<SolicitudMecanizado> getSolicitudes() throws ExcFiltroExcepcion{
		//corejir luego		return new PerSolicitudMecanizado().getAllSinPrestar();
		return new PerSolicitudMecanizado().getSegunEstado(new PerEstadoSolicitud().getaprobada());
	}
	
	public List<ImplementoUnidad> getImplementos(UnidadFuncional unidad) throws ExcFiltroExcepcion {
		//return new PerActivoAlmacen().getTodosImplementos(unidad);
		return new PerImplementoUnidad().getImplementos(unidad);
	}
	
	public List<MaquinariaUnidad> getMaquinarias(UnidadFuncional unidad) throws ExcFiltroExcepcion {
		//return new PerActivoAlmacen().getTodosMaquinarias(unidad);
		return new PerMaquinariaUnidad().getMaquinaria(unidad);
	}
	
	public List<DetalleContrato> getContratos() throws ExcFiltroExcepcion{
		return new PerContratoMecanizado().getContratosServicio();
	}
	
	public List<DetalleContrato> getContratosFiltrados() throws ExcFiltroExcepcion{
		return new PerContratoMecanizado().getContratosServicioSinOrden();
	}
	
	
	public DetalleContrato getDetalleContrato(OrdenTrabajoMecanizado orden){
		if (orden.getContrato() != null){
			orden.setContrato(new  PerContratoMecanizado().getEnriqueser(orden.getContrato()));
			for (DetalleContrato item: orden.getContrato().getDetallesContrato()){
				if (orden.getLaborOrden().getLabor().equals(item.getProducto())){
					return item;
				}
			}
		}
		return null;
	}
	
	public List<UnidadFuncional> getUnidadesEjecutoras() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	
	public List<Productor> getProductores() throws ExcFiltroExcepcion{
		return new PerProductor().getAll();
	}
	
	public List<Labor> getLabores(SolicitudMecanizado solicitud) throws ExcFiltroExcepcion{
		List<Labor> labores = new ArrayList<Labor>();
		SolicitudMecanizado solicitud2 = new PerSolicitudMecanizado().getDatos(solicitud);
		for (DetalleSolicitud detalle :solicitud2.getDetalles()){
			labores.add((Labor)detalle.getProducto());
		}
		return labores;
	}
	

	public List<Labor> getLabores(){
		TipoServicio tipo = new PerTipoServicio().getTipoMecanizado();
		return new PerLabor().getAll(tipo);
	}
	
	public List<Rubro> getRubros() throws ExcFiltroExcepcion{
		return new PerRubro().getAll();
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
	
	public LaborOrdenServicio getDetalleLabor(DetalleContrato detalle) throws ExcFiltroExcepcion{
		LaborOrdenServicio laborOS = new LaborOrdenServicio();
		Servicio servicio = ((Labor)detalle.getProducto()).getServicio();
		if (servicio.getTipoUnidadTrabajo() == null)
			throw new ExcFiltroExcepcion("Problemas con la unidad de Trabajo del servicio");
		laborOS.setLabor((Labor) detalle.getProducto());
		laborOS.setFisico(detalle.getCantidad());
		laborOS.setPases(1);
		laborOS.setCantidad(1);
		laborOS.setCantidadVisible(servicio.getManejaCantidades());
		laborOS.setPasesVisible(servicio.getManejaPases());
		List<UnidadMedida> medidas = new PerUnidadMedida().getAll(laborOS.getLabor().getMedidaGestion());
		for(UnidadMedida item :medidas){
			if (item.getTipo().equals(servicio.getTipoUnidadTrabajo())){
				laborOS.setUnidad(item);
			}
		}
		if (laborOS.getUnidad() == null)
			throw new ExcFiltroExcepcion("No existe relacion entre unidad de trabajo y de gestion");
		return laborOS;
	}
	
	public LaborOrdenServicio getDetalleLabor(SolicitudMecanizado solicitud, Labor labor) throws ExcFiltroExcepcion{
		LaborOrdenServicio laborOS = getDetalleLabor(labor);
		solicitud = new PerSolicitudMecanizado().getDatos(solicitud);
	//	List<DetalleSolicitud> detalles = solicitud.getDetalles();
		/*
		for (DetalleSolicitud detalleSolicitud : detalles) {
			List<UnidadSolicitada> unidades = detalleSolicitud.getSolicitado();
			String a = labor.getDescripcion();
			String b = detalleSolicitud.getProducto().getDescripcion();
		if (a.equals(b)) {
			if (labor.getServicio().getManejaCantidades())
				laborOS.setCantidad(detalleSolicitud.getCantidad());
			if (labor.getServicio().getManejaPases()){
				Double c = detalleSolicitud.getPases();
				laborOS.setPases(c);
		     	laborOS.setPasesVisible(labor.getServicio().getManejaPases());
		      	} 
			for (UnidadSolicitada unidad: unidades){
	     		if(laborOS.getUnidad().equals(unidad.getUnidad())){
					laborOS.setFisico(unidad.getCantidad());
				}
		
		}
			
			
			break;	
			
		}
			
			
			
			
		}*/
		
		
		for(DetalleSolicitud item: solicitud.getDetalles()){
			for(UnidadSolicitada unidad: item.getSolicitado()){
				if(laborOS.getUnidad().equals(unidad.getUnidad())){
					laborOS.setFisico(unidad.getCantidad());
				}
			if (labor.equals((Labor)item.getProducto())){
				if (labor.getServicio().getManejaCantidades())
					laborOS.setCantidad(item.getCantidad());
			
				if (labor.getServicio().getManejaPases()){
				Double a = null;
				a = item.getPases();
				laborOS.setPasesVisible(labor.getServicio().getManejaPases());
				laborOS.setPases(a);
				}
				
				}
			}
		
		}
		return laborOS;
	}
	
	public List<Labor> getLabores(ContratoMecanizado contrato) throws ExcFiltroExcepcion{
		List<Labor> labores = new ArrayList<Labor>();
		contrato = new PerContratoMecanizado().getDetalle(contrato);
		for(DetalleContrato item: contrato.getDetallesContrato()){
			labores.add((Labor)item.getProducto());
		}
		return labores;
	}
	
	public List<UnidadMedida> getUnidadesMedida(UnidadMedida unidad) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(unidad); 
	}
	
	public List<OrdenTrabajoMecanizado> getOrdenesPendientes() throws ExcFiltroExcepcion{
		return getPersistencia().getAllMenos(new PerEstadoOrdenTrabajo().getTerminado());
	}

	public List<OrdenTrabajoMecanizado> getOrdenesTrabajoPorMaquinaria(MaquinariaUnidad maquinaria, OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		if (orden == null)
			return getPersistencia().getOrdenesTrabajoPorMaquinaria(maquinaria, orden);
		else
			return getPersistencia().getOrdenesTrabajoPorMaquinaria(maquinaria, orden);
	}	
	
	public List<OrdenTrabajoMecanizado> getOrdenesTrabajoPorImplemento(ImplementoUnidad implemento, OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		if (orden == null)
			return getPersistencia().getOrdenesTrabajoPorImplemento(implemento, orden);
		else
			return getPersistencia().getOrdenesTrabajoPorImplemento(implemento, orden);
	}	
	
	public List<TrabajoRealizadoMecanizado> actualizarTrabajos(List<TrabajoRealizado> trabajos) throws ExcFiltroExcepcion{
		List<TrabajoRealizadoMecanizado> trabajosMecanizado = null;
		if (trabajos != null){
			trabajosMecanizado = new ArrayList<TrabajoRealizadoMecanizado>();
			TrabajoRealizadoMecanizado trabajo;
			for (TrabajoRealizado item : trabajos) {
				trabajo = new PerTrabajoMecanizado().getDatos(item);
				trabajosMecanizado.add(trabajo);
			}
		}
		return trabajosMecanizado;
	}
	
	public List<OrdenTrabajoMecanizado> getOrdenesCerrado() throws ExcFiltroExcepcion{
		return getPersistencia().getAll(new PerEstadoOrdenTrabajo().getTerminado());
	}
	
	public List<TipoFalla> getFallas() throws ExcFiltroExcepcion{
		return new PerTipoFalla().getAll();
	}

	public List<MomentoFalla> getMomentosFallas() throws ExcFiltroExcepcion{
		return new PerMomentoFalla().getAll();
	}

	public List<Trabajador> getTecnicosCampos() throws ExcFiltroExcepcion{
		return new PerTrabajador().getTecnicosCampo();
	}
	
	public List<Servicio> getServicios(){
		TipoServicio tipo = new PerTipoServicio().getTipoMecanizado();
		return new PerServicio().getAll(tipo);
	}
	
	public Productor enriqueserProductor(Productor productor) throws ExcFiltroExcepcion{
		return new PerProductor().getDatos(productor.getId());
	}
	
	public void guardar(OrdenTrabajoMecanizado dato) throws ExcFiltroExcepcion {
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
		
	public void cerrar(OrdenTrabajoMecanizado dato) throws ExcFiltroExcepcion {
		if (dato.getDetalles() != null)
			for (DetalleOrdenTrabajo item :dato.getDetalles()){
				item.setOrden(dato);
			}
		getPersistencia().cerrar(dato, dato.getId());
	}
	
	public void anular(OrdenTrabajoMecanizado dato, boolean anularSolicitud) throws ExcFiltroExcepcion {
		getPersistencia().anular(dato, dato.getId(), anularSolicitud);
	}
	
	public ContratoMecanizado getEnriqueserCtto(Contrato ctto) throws ExcFiltroExcepcion {
		return new PerContratoMecanizado().getEnriqueser(ctto);
	}
	
	private Sede getSede(){
		String  idSede= (String) SpringUtil.getBean("idsede");
		String  idEmpresa= (String) SpringUtil.getBean("idEmpresa");
		return new PerSede().buscarId(new SedePK(idEmpresa, idSede));
	}
	
	public List<OrdenTrabajoMecanizado> getTodosProject() throws ExcFiltroExcepcion {
		return new PerOrdenTrabajoMecanizado().getTodosProject();
	}
	
	public OrdenTrabajoMecanizado getOrdenMecanizadoProject(OrdenTrabajoMecanizado ordenTrabajoMecanizado) throws ExcFiltroExcepcion {
		ordenTrabajoMecanizado =new PerOrdenTrabajoMecanizado().getOrdenTrabajoMecanizadoProject(ordenTrabajoMecanizado);
		return ordenTrabajoMecanizado;
	}
	
	public DetalleContrato getDetalleContratoProject(DetalleContrato detalleContrato){
		detalleContrato = new PerContratoMecanizado().getDetalleProject(detalleContrato);
		return detalleContrato;
	}
	public List<Object[]> getReporteConsolidado(Date inicio, Date fin){
		List<Object[]> listaReporte = new PerOrdenTrabajoMecanizado().getReporteConsolidado(inicio,fin);
		
		return listaReporte;
		
	}
}
