package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.PlanFinanciamiento;
import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.administrativo.PerContratoMecanizado;
import cpc.persistencia.demeter.implementacion.administrativo.PerPlanFinanciamiento;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoContrato;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerSolicitudMecanizado;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioContratoMecanizado implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2289543935603267277L;
	private static NegocioContratoMecanizado 	negocio;	
	private PerContratoMecanizado				perContrato;
	private ContratoMecanizado					contrato;
	
	
	private NegocioContratoMecanizado(){		
		perContrato				= new PerContratoMecanizado();
	}

	public  static synchronized NegocioContratoMecanizado getInstance() {
		if (negocio == null)
			negocio = new NegocioContratoMecanizado();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{		
		return new PerCliente().getAll(); 
	}
	
	public List<Cliente> getClientesProject() throws ExcFiltroExcepcion{		
		return new PerCliente().getAllProject(); 
	}
	
	public Cliente getClienteProject(Cliente cliente){
		cliente =new PerCliente().getclienteproject(cliente);
		return cliente;
	}
	
	public Cliente getCliente(Cliente cliente){
		return new PerCliente().getDatos(cliente);
	}
	
	public List<ContratoMecanizado> getContratos() throws ExcFiltroExcepcion{				
		List<ContratoMecanizado> contratos = perContrato.getAll();
		return contratos; 
	}	
	
	public List<ContratoMecanizado> getContratosProject() throws ExcFiltroExcepcion{				
		List<ContratoMecanizado> contratos = perContrato.getAllProject();
		return contratos; 
	}	
	public ContratoMecanizado getContratoProject(ContratoMecanizado contratoMecanizado) throws ExcFiltroExcepcion{				
		contratoMecanizado = perContrato.getContratoMecanizadoProject(contratoMecanizado);
		return contratoMecanizado; 
	}	
	
	
	
	public List<DetalleContrato> getDetalleSolicitud(SolicitudMecanizado solicitud) throws ExcFiltroExcepcion{
		List<DetalleSolicitud> detalles = new PerDetalleSolicitud().getTodasErriquesidas(solicitud);
		List<DetalleContrato> detallesContrato = new ArrayList<DetalleContrato>();
		DetalleContrato detalle;
		Labor prodcuto;
		Productor prodcutor = solicitud.getProductor();
		for (DetalleSolicitud item : detalles) {
			detalle = new DetalleContrato();
			detalle.setProducto(item.getProducto());
			prodcuto = (Labor) item.getProducto();
			prodcuto = new PerLabor().getDato(prodcuto);
			detalle.setCantidad(1.0);
			detalle.setPrecioUnidad(prodcuto.getPrecioBase(prodcutor.getTipo()));
		
			try{
				List<UnidadMedida> unidadesValidas = new PerUnidadMedida().getAllHijas(prodcuto.getMedidaCobro().getTipo());
				for (UnidadSolicitada item2 : item.getSolicitado()) {
					for (UnidadMedida itemUni :unidadesValidas)
						if (item2.getUnidad().equals(itemUni)){
							if (solicitud.getServicio().getManejaPases())
							detalle.setCantidad(item2.getCantidad()*item.getPases());
							else	
								detalle.setCantidad(item2.getCantidad());
				
						}
				}
				
			
			/*	if (detalle.getCantidad().equals(1.0) && item.getSolicitado().size()==1)
		
					detalle.setCantidad(item.getSolicitado().get(0).getCantidad())
					;
					*/
			}catch (Exception e) {
				// TODO: handle exception
			}
			detalle.setSubtotal(detalle.getPrecioUnidad()*detalle.getCantidad());
			detallesContrato.add(detalle);
		}
		 
		return detallesContrato; 
	}
	
	public List<Servicio> getServicios(SolicitudMecanizado solicitud) {
		List<Servicio> productos = new ArrayList<Servicio>(); 
		productos.add(solicitud.getServicio());
		return productos;
	}
	
	public List<SolicitudMecanizado> getSolicitudes() {
	//corejir luego	return new PerSolicitudMecanizado().getAllSinPrestar(); 
		return new PerSolicitudMecanizado().getSegunEstado(new PerEstadoSolicitud().getaprobada());
	}

	public List<IProducto> getServicios(Servicio servicio) {
		return new PerLabor().getAllLabor(servicio);
	}
	
	
	public List<UnidadProductiva> getDirecciones(Cliente cliente) throws ExcFiltroExcepcion {	
		return  new PerUnidadProductiva().getTodos(cliente);
	}
	
	
	public List<PlanFinanciamiento> getPlanesFinanciamientos() throws ExcFiltroExcepcion {
		return new PerPlanFinanciamiento().getAll();
	}
	
	public void guardar(ContratoMecanizado contrato)  throws Exception, SQLException{		
		perContrato.guardar(contrato, getControlSede());
	}
	
	public void eliminar(){
		
	}
	
	public void anular(ContratoMecanizado contrato) throws Exception{
		perContrato.anularContrato(contrato);
	}


	public void procesar(ContratoMecanizado contrato) throws Exception{
		perContrato.procesarContrato(contrato);
	}
	
	
	public ContratoMecanizado getContrato() {
		return contrato;
	}
	
	public ContratoMecanizado getDetallesDeContrato(ContratoMecanizado contrato){
		return this.perContrato.getDetalle(contrato);
	}

	public void setContrato(ContratoMecanizado contrato) {
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
	
	
}