package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.collections.functors.IfClosure;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Cotizacion;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.PlanFinanciamiento;
import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.modelo.demeter.administrativo.TipoCotizacion;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.administrativo.PerContratoMecanizado;
import cpc.persistencia.demeter.implementacion.administrativo.PerCotizacion;
import cpc.persistencia.demeter.implementacion.administrativo.PerPlanFinanciamiento;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoCotizacion;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerSolicitudMecanizado;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCotizacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2289543935603267277L;
	private static NegocioCotizacion 	negocio;	
	private PerCotizacion				perCotizacion;
	private PerTipoCotizacion			perTipoCotizacion;
	private Cotizacion  				contrato;
	
	
	private NegocioCotizacion(){		
		perCotizacion				= new PerCotizacion();
		perTipoCotizacion = new PerTipoCotizacion();
	}

	public  static synchronized NegocioCotizacion getInstance() {
		if (negocio == null)
			negocio = new NegocioCotizacion();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{		
		return new PerCliente().getAll(); 
	}
	
	public List<ClienteAdministrativo> getClientesAdministrativos() throws ExcFiltroExcepcion{		
		return new PerClienteAdministrativo().getClienteAdministrativosProject();
	}
	
	
	public Cliente getCliente(Cliente cliente){
		return new PerCliente().getDatos(cliente);
	}

	public List<Cotizacion> getCotizacionesExistencia() throws ExcFiltroExcepcion{				
		List<Cotizacion> contratos = perCotizacion.getAllExistencia();
		return contratos; 
	}
	public List<Cotizacion> getCotizacionesExpedeinte() throws ExcFiltroExcepcion{				
		List<Cotizacion> contratos = perCotizacion.getAllExpediente();
		return contratos; 
	}

	public List<Cotizacion> getCotizacionesExpedeinteMantenimiento() throws ExcFiltroExcepcion{				
		TipoCotizacion tipoMantenimiento = perTipoCotizacion.getMantenimiento();
		List<Cotizacion> contratos = perCotizacion.getAllExpediente(tipoMantenimiento);
		return contratos; 
	}
	
	public List<Cotizacion> getCotizacionesExpedienteEquipos() throws ExcFiltroExcepcion{				
		TipoCotizacion tipoEquipo = perTipoCotizacion.getEquipo();
		List<Cotizacion> contratos = perCotizacion.getAllExpediente(tipoEquipo);
		return contratos; 
	}
	
	
	public List<Cotizacion> getCotizacionesExpedeinteVialidad() throws ExcFiltroExcepcion{				
		TipoCotizacion tipoVialidad = perTipoCotizacion.getVialidad();
		List<Cotizacion> contratos = perCotizacion.getAllExpediente(tipoVialidad);
		return contratos; 
	}

	public List<Cotizacion> getCotizacionesExpedienteTransporte() throws ExcFiltroExcepcion{				
		TipoCotizacion tipoTransporte = perTipoCotizacion.getTransporte();
		List<Cotizacion> contratos = perCotizacion.getAllExpediente(tipoTransporte);
		return contratos; 
	}
	
	
	public List<ArticuloVenta> getArticulos() {
		return new PerArticuloVenta().getAllArticuloVenta();
	}
	public List<IProducto> getArticulosServicios() {
		List<IProducto> articulos = new PerArticuloVenta().getAllArticulo();
		articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoServicioTecnico()));
		
		return articulos;
	}
	
	
	//Productos por Vialidad pero no toy seguro si deben de ser estos jajajajajja :-D 
	public List<IProducto> getArticulosServicioVialidad() {
		List<IProducto> articulos = new PerArticuloVenta().getAllArticulo();
		articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoLineaAmarilla()));
		return articulos;
	}

	public List<IProducto> getArticulosServicioTransporte() {
		List<IProducto> articulos = new PerArticuloVenta().getAllArticulo();
		articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		return articulos;
	}
	
	public List<IProducto> getArticulosServiciosActivos() {
		List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		articulos.addAll(new PerLabor().getAllLaborACTIVAS(new PerTipoServicio().getTipoServicioTecnico()));
		return articulos;
	}
	
	public List<Almacen> getAlmacenesSalidaExterna() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesSalidaExterna();
	}
	
	
	public List<PlanFinanciamiento> getPlanesFinanciamientos() throws ExcFiltroExcepcion {
		return new PerPlanFinanciamiento().getAll();
	}
	
	public void guardar(Cotizacion contrato)  throws Exception, SQLException{		
		contrato.setTipoContrato(new PerTipoContrato().getcritaeriaUno(Arrays.asList(new  Criterion[] { Restrictions.eq("id", 1)})));	
		contrato.setFacturado(false);
		if (contrato.getExpediente()==false)
		contrato.getSalidaExternaArticulo().setTipoMovimiento(new PerTipoMovientoArticulo().getSalidaExterna());
		perCotizacion.guardar(contrato, getControlSede());
	}
	
	public void eliminar(Cotizacion cotizacion) throws Exception{
		perCotizacion.borrar(cotizacion);
	}

	
	public Cotizacion getContrato() {
		return contrato;
	}
	
	public Cotizacion getDetallesDeContrato(Cotizacion  contrato){
		return this.perCotizacion.getDetalle(contrato);
	}

	public void setContrato(Cotizacion contrato) {
		this.contrato = contrato;
	}
	
	public List<TipoContrato> getTiposContrato() throws ExcFiltroExcepcion{
		return new PerTipoContrato().getAll();
	}
	
	public Contrato getCuotasContrato(Contrato cont){
		return new PerCuotaAPagarCliente().getCuotasAPagar(cont);
		
	}

	public List<IProducto> getProductos() throws ExcFiltroExcepcion{
		TipoServicio tipo = new PerTipoServicio().getTipoMecanizado();
		List<IProducto> servicios = new PerLabor().getAllProductos(tipo);
		return servicios; 
	}
	
	public IProducto enriqueserProducto(IProducto producto){
		if (producto instanceof Labor) {
			return new PerLabor().getDato((Labor)producto);
		}else{
			return new PerArticuloVenta().getDato((ArticuloVenta) producto);
		}
	}
	
	public ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public boolean ValidarExistencia(ArticuloVenta articuloVenta, Almacen almacenOrigen, Double cantidad){
		 ArticuloAlmacenCantidad existencia = new PerArticuloAlmacenCantidad().VerificarExistencia(articuloVenta,almacenOrigen );
		 if (existencia==null)
			 return false;
		 if (existencia.getCantidad()<cantidad)
			 return false;
		 
		 else return true;
	}
	public void anular(Cotizacion cotizacion) throws Exception{
		perCotizacion.anular(cotizacion);
	}
	
	public int getHijosActivos(Cotizacion cotizacion) throws Exception{
	return	perCotizacion.getHijosActivos(cotizacion);
	}
	public ClienteAdministrativo getClienteAdministrativo(ClienteAdministrativo clienteAdministrativo){
		
		clienteAdministrativo = new PerClienteAdministrativo().getClienteAdministrativoProject(clienteAdministrativo);
		return clienteAdministrativo; 
	}
	

	public TipoCotizacion getMantenimiento()
	{
		return perTipoCotizacion.getMantenimiento();
	}
	
	public TipoCotizacion getVialidad()
	{
		return perTipoCotizacion.getVialidad();
	
	}
	public TipoCotizacion getEquipos()
	{
		return perTipoCotizacion.getEquipo();
	
	}
	
	public TipoCotizacion getTransporte()
	{
		return perTipoCotizacion.getTransporte();
	
	}
	
}