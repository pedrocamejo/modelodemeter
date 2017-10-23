package cpc.negocio.demeter.mantenimiento;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.CicloProductivo;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.PlanServicio;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.AnulacionSolicitud;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.MotivoAnulacionSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerSede;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerCicloProductivo;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerPlanServicio;
import cpc.persistencia.demeter.implementacion.basico.PerRubro;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerAnulacionSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerControlUnidadFuncional;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoSolicitud;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSolicitudServicioTecnico;
import cpc.persistencia.ministerio.basico.PerInstitucionCrediticia;
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioSolicitudServicioTecnico extends NegocioGenerico<SolicitudServicioTecnico, PerSolicitudServicioTecnico, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioSolicitudServicioTecnico 				negocio;
	
	private NegocioSolicitudServicioTecnico(){
		setPersistencia(new PerSolicitudServicioTecnico());
	}
	
	public  static synchronized NegocioSolicitudServicioTecnico getInstance() {
		if (negocio == null)
			negocio = new NegocioSolicitudServicioTecnico();
		return negocio;
	}

	public void setSolicitud(SolicitudServicioTecnico solicitud) throws ExcFiltroExcepcion{
		if (solicitud!= null){
			solicitud.setDetalles(new PerDetalleSolicitud().getTodasErriquesidas(solicitud));
			Productor cliente = solicitud.getProductor();
			if (cliente != null){
				List<UnidadProductiva> unidades = new PerUnidadProductiva().getTodos(cliente);
				cliente.setUnidadesproduccion(unidades);
			}
		}
		super.setModelo(solicitud);
		
	}

	
	public List<Trabajador> getTrabajadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getComunitaria();
	}

	public List<CicloProductivo> getCiclosProductivos() throws ExcFiltroExcepcion{
		return new PerCicloProductivo().getAll();
	}
	
	public List<PlanServicio> getPlanesServicios() throws ExcFiltroExcepcion{
		return new PerPlanServicio().getAll();
	}
	
	public List<UnidadFuncional> getUnidadesEjecutoras() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	
	public List<Productor> getProductores() throws ExcFiltroExcepcion{
		return new PerProductor().getAll();
	}
	
	public List<Labor> getLabores(){
		TipoServicio tipo = new PerTipoServicio().getTipoMecanizado();
		return new PerLabor().getAll(tipo);
	}

	public List<IProducto> getLaboresActivas(Servicio servicio){
		List<IProducto> z = new PerArticuloVenta().getAllArticuloActivo() ;
		List<IProducto> x = new PerLabor().getAllLaborActivas(servicio);
		z.addAll(x);
		return z;
		
	}
	
	public List<IProducto> getLabores(Servicio servicio){
		List<IProducto> z = new PerArticuloVenta().getAllArticulo() ;
		List<IProducto> x = new PerLabor().getAllLabor(servicio);
		z.addAll(x);
		return z;
		
	}
	
	public List<Rubro> getRubros() throws ExcFiltroExcepcion{
		return new PerRubro().getAll();
	}
	
	public List<UnidadMedida> getUnidadesMedida(Labor labor) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(labor.getMedidaGestion()); 
	}
	public List<UnidadMedida> getUnidadesMedida(ArticuloVenta articuloVenta) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getUnidades(); 
	}
	
	public List<UnidadMedida> getUnidadesMedida(UnidadMedida unidad) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(unidad); 
	}

	
	public List<InstitucionCrediticia> getFinanciamientos() throws ExcFiltroExcepcion{
		return new PerInstitucionCrediticia().getAll();
	}
	
	/*public List<SolicitudMecanizado> getSolicitudesPendientes(){
		return getPersistencia().getAllSinPrestar();
	}
*/
	public List<Servicio> getServicios(){
		TipoServicio tipo = new PerTipoServicio().getTipoServicioTecnico();
		return new PerServicio().getAll(tipo);
	}
	
	/*public List<SolicitudMecanizado> getSolicitudesPendientes(Productor productor){
		return getPersistencia().getAllSinPrestar(productor);
	}*/
	
	public List<MotivoAnulacionSolicitud> getmotivosanulacion(){
		return new PerAnulacionSolicitud().getMotivos(new PerTipoSolicitud().getTipoMecanizado());
	}
	
	
	public Productor enriqueserProdcutor(Productor productor) throws ExcFiltroExcepcion{
		return new PerProductor().getDatos(productor.getId());
	}
	
	public void guardar(SolicitudServicioTecnico dato) throws ExcFiltroExcepcion {
		if (dato.getUnidadEjecutora() == null)
			throw new ExcFiltroExcepcion("No se definio Unidad Ejecutora");
		if (dato.getDetalles() != null)
			for (DetalleSolicitud item :dato.getDetalles()){
				item.setSolicitud(dato);
				for (UnidadSolicitada itemInterno : item.getSolicitado()) {
					itemInterno.setDetalleSolicitud(item);
				}
			}
		ControlUnidadFuncional control = new PerControlUnidadFuncional().getControl(dato.getUnidadEjecutora());
		if (control == null)
			throw new ExcFiltroExcepcion("problema con control");
		getPersistencia().guardar(dato, dato.getId(), control);
	}
	
	public SolicitudServicioTecnico  getNuevaSolicitud() throws ExcDatosNoValido{
		setModelo(new SolicitudServicioTecnico());
		try{
			getModelo().setFecha(new Date());
			getModelo().setTipoSolicitud(new PerTipoSolicitud().getTipoServicioTecnico());
			getModelo().setSede(getSede());
			getModelo().setAprobada(true);
			getModelo().setPlanificada(false);
			getModelo().setPrestada(false);
		getModelo().setEstadosolicitud(new PerEstadoSolicitud().getServicoTecnicoRecibida());
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la sede");
		}
		return getModelo();
	}
	
	private Sede getSede(){
		String  idSede= (String) SpringUtil.getBean("idsede");
		String  idEmpresa= (String) SpringUtil.getBean("idEmpresa");
		return new PerSede().buscarId(new SedePK(idEmpresa, idSede));
	}
	
	
	public void anular(SolicitudServicioTecnico docu,AnulacionSolicitud anulacion) throws HibernateException, ExcFiltroExcepcion{
		  getPersistencia().anular(docu, anulacion);
	}
	
	
	public int getHijosActivos( int id){
		      return getPersistencia().getHijosActivos(id);
	}
	
	public EstadoSolicitud getEstadoSolicitud(String estado){
		return new PerEstadoSolicitud().getSegunEstado(estado,new PerTipoSolicitud().getTipoServicioTecnico());
	
	}
	
	
	
	/*
	public List<SolicitudMecanizado> getSolicitudesFecha(String inicio, String fin ){
		return getPersistencia().getAllFechas(inicio,fin);
	}
	*/
	
	public List<Modelo> obtenerModelos(){
		try {
			return new PerModelo().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
			return null;
		}
	}
}



