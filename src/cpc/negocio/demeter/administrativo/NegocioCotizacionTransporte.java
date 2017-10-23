package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Cotizacion;
import cpc.modelo.demeter.administrativo.CotizacionTransporte;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.PlanFinanciamiento;
import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.transporte.UbicacionTransporte;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.administrativo.PerCotizacion;
import cpc.persistencia.demeter.implementacion.administrativo.PerCotizacionTransporte;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerPlanFinanciamiento;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoContrato;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.demeter.implementacion.transporte.PerUbicacionTransporte;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cpc.persistencia.ministerio.basico.PerSector;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioCotizacionTransporte implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2289543935603267277L;
	private static NegocioCotizacionTransporte 	negocio;	
	private PerCotizacionTransporte				perCotizacion;
	private CotizacionTransporte  				contrato;
	
	
	private NegocioCotizacionTransporte(){		
		perCotizacion				= new PerCotizacionTransporte();
	}

	public  static synchronized  NegocioCotizacionTransporte getInstance() {
		if (negocio == null)
			negocio = new NegocioCotizacionTransporte();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{		
		return new PerCliente().getAll(); 
	}
	public List<ClienteAdministrativo> getClientesAdministrativos() throws ExcFiltroExcepcion{		
	//	return new PerClienteAdministrativo().getAll();
		return new PerClienteAdministrativo().getClienteAdministrativosProject();
	}
	
	
	public Cliente getCliente(Cliente cliente){
		return new PerCliente().getDatos(cliente);
	}
	/*
	public List<Cotizacion> getCotizaciones() throws ExcFiltroExcepcion{				
		List<Cotizacion> contratos = perCotizacion.getAll();
		return contratos; 
	}*/	
	public List<CotizacionTransporte> getCotizacionestransporte() throws ExcFiltroExcepcion{				
		List<CotizacionTransporte> contratos = perCotizacion.getAllCotizacionTransporte();
		return contratos; 
	}
	
	
	public List<ArticuloVenta> getArticulos() {
		return new PerArticuloVenta().getAllArticuloVenta();
	}
	public List<IProducto> getServiciosTransporte() {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte());
	}
	
	public List<IProducto> getServiciosTransporteActivos() {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return  new PerLabor().getAllLaborACTIVAS(new PerTipoServicio().getTipoTransporte());
	}
	public List<IProducto> getLabores(Servicio servicio) {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return new PerLabor().getAllLabor(servicio);
	}
	

	public List<Almacen> getAlmacenesSalidaExterna() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesSalidaExterna();
	}
	
	
	
	
	
	public List<PlanFinanciamiento> getPlanesFinanciamientos() throws ExcFiltroExcepcion {
		return new PerPlanFinanciamiento().getAll();
	}
	
	public void guardar(CotizacionTransporte contrato)  throws Exception, SQLException{		
		contrato.setTipoContrato(new PerTipoContrato().getcritaeriaUno(Arrays.asList(new  Criterion[] { Restrictions.eq("id", 1)})));	
		contrato.setFacturado(false);
	
		perCotizacion.guardar(contrato, getControlSede());
	}
	
	public void eliminar(){
		
	}

	
	public CotizacionTransporte getContrato() {
		return contrato;
	}
	
	public CotizacionTransporte getDetallesDeContrato(CotizacionTransporte  contrato){
		return this.perCotizacion.getDetalle(contrato);
	}

	public void setContrato(CotizacionTransporte contrato) {
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
	public void anular(CotizacionTransporte cotizacion) throws Exception{
		perCotizacion.anular(cotizacion);
	}
	
	public int getHijosActivos(CotizacionTransporte cotizacion) throws Exception{
	return	perCotizacion.getHijosActivos(cotizacion);
	}
	public ClienteAdministrativo getClienteAdministrativo(ClienteAdministrativo clienteAdministrativo){
		
		clienteAdministrativo = new PerClienteAdministrativo().getClienteAdministrativoProject(clienteAdministrativo);
		return clienteAdministrativo; 
	}
	public List<UbicacionTransporte> getUbicacionesTransportes() throws ExcFiltroExcepcion{
		return new PerUbicacionTransporte().getAll();
	}
	
	
	public List<UbicacionTransporte> getUbicacionTransportes() throws ExcFiltroExcepcion{
		return new PerUbicacionTransporte().getcritaeriaAll();
	}
	
	public EstadoContrato getEstadoActivo(){
		return new PerEstadoContrato().getActivo();
	}
	
	public EstadoContrato getEstadoRechazado(){
		return new PerEstadoContrato().getRechazado();
	}
	public void procesar(CotizacionTransporte cotizacionTransporte){
		
		new PerCotizacionTransporte().procesar(cotizacionTransporte);
		
	}
}