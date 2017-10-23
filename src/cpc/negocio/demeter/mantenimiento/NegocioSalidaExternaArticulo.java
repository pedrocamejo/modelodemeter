package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;


import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumible;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumibleEquivalente;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEntradaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSalidaExternaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSalidaInternaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSolicitudServicioTecnico;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSalidaExternaArticulo {
	private static  NegocioSalidaExternaArticulo negocio;
	private PerSalidaExternaArticulo peristencia;
	private List<DetalleSalidaExternaArticulo> detalleSalidaExternaArticulos;
    private SalidaExternaArticulo salidaExternaArticulo;

	private  NegocioSalidaExternaArticulo() {
		/*
		 * SessionDao dao = SessionDao.getInstance(); dao.test();
		 * dao.newDaoGenerico(new PerFactura());
		 */
		peristencia = new PerSalidaExternaArticulo();
	}

	public static synchronized  NegocioSalidaExternaArticulo getInstance() {
		if (negocio == null)
			negocio = new  NegocioSalidaExternaArticulo();
		return negocio;

	}

	public PerSalidaExternaArticulo getPersistencia() {
		return peristencia;
	}

	public void setPersistencia(
			PerSalidaExternaArticulo perSalidaExternaArticulo) {
		this.peristencia = perSalidaExternaArticulo;
	}

	public List<SalidaExternaArticulo> getTodos() throws ExcFiltroExcepcion {
		return peristencia.getAll();
	}

	public List<DetalleSalidaExternaArticulo> getDetalleEntradaArticulos() {
		return detalleSalidaExternaArticulos;
	}

	public void setDetalleSalidaExternaArticulos(
			List<DetalleSalidaExternaArticulo> detalleSalidaExternaArticulos) {
		this.detalleSalidaExternaArticulos = detalleSalidaExternaArticulos;
	}

	public SalidaExternaArticulo getSalidaExternaArticulo() {
		return salidaExternaArticulo;
	}

	public void setSalidaExternaArticulo(SalidaExternaArticulo salidaExternaArticulo ) {
		this.salidaExternaArticulo = salidaExternaArticulo;
	}
	
	public List<ArticuloVenta> getArticulosVentas() throws ExcFiltroExcepcion{
		return new PerArticuloVenta().getAll() ;
	}
	
	
	public List<Almacen> getAlmacenesSalidaExterna() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesSalidaExterna();
	}
	public List<Almacen> getAlmacenesDestino() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAll();
	}
 
	
	public void guardar(SalidaExternaArticulo salidaExternaArticulo) throws ExcFiltroExcepcion{
		salidaExternaArticulo.setTipoMovimiento(new PerTipoMovientoArticulo().getSalidaExterna());
		if (salidaExternaArticulo.getSolicitudServicioTecnico()!=null)
			salidaExternaArticulo.getSolicitudServicioTecnico().setEstadosolicitud(new PerEstadoSolicitud().getServicioTecnicoDespachada());
		for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo2 : salidaExternaArticulo.getDetalleSalidaExternaArticulos()) {
				detalleSalidaExternaArticulo2.setMovimiento(salidaExternaArticulo);
			}
			salidaExternaArticulo.setTipoMovimiento(new PerTipoMovientoArticulo().getSalidaExterna());
			salidaExternaArticulo.setSede(getControlSede().getSede()); 
		getPersistencia().guardarSalidaExterna(salidaExternaArticulo,salidaExternaArticulo.getId(),getControlSede());
	}
	
	public boolean ValidarExistencia(ArticuloVenta articuloVenta, Almacen almacenOrigen, Double cantidad){
		 ArticuloAlmacenCantidad existencia = new PerArticuloAlmacenCantidad().VerificarExistencia(articuloVenta,almacenOrigen );
		 if (existencia==null)
			 return false;
		 if (existencia.getCantidad()<cantidad)
			 return false;
		 
		 else return true;
	}
	
	public List<ClienteAdministrativo> getDestinatarios() throws ExcFiltroExcepcion{
		return new PerClienteAdministrativo().getcritaeriaAll();
	}
	
	public List<SolicitudServicioTecnico> getSolicitudesServicoTecnicoAprovadasDespachadas() throws ExcFiltroExcepcion{
		EstadoSolicitud a = new PerEstadoSolicitud().getServicioTecnicoAprobada();
		List<SolicitudServicioTecnico> lista = new PerSolicitudServicioTecnico().getSegunEstado(a);
		EstadoSolicitud b = new PerEstadoSolicitud().getServicioTecnicoDespachada();
		lista.addAll( new PerSolicitudServicioTecnico().getSegunEstado(b));
		return lista;
	}
	
	public List<SolicitudServicioTecnico> getSolicitudesServicoTecnico() throws ExcFiltroExcepcion{
		return new PerSolicitudServicioTecnico().getcritaeriaAll();
	}
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
}