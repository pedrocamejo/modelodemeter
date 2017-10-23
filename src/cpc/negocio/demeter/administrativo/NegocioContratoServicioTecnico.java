package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoServicioTecnico;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.PlanFinanciamiento;
import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.mantenimiento.DetalleDevolucionArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.DevolucionArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.administrativo.PerContratoServicioTecnico;
import cpc.persistencia.demeter.implementacion.administrativo.PerPlanFinanciamiento;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoContrato;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.gestion.PerDetalleSolicitud;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerDevolucionArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSalidaExternaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSolicitudServicioTecnico;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioContratoServicioTecnico implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2289543935603267277L;
	private static NegocioContratoServicioTecnico 	negocio;	
	private PerContratoServicioTecnico				perContrato;
	private ContratoServicioTecnico					contrato;
	
	
	private NegocioContratoServicioTecnico(){		
		perContrato				= new PerContratoServicioTecnico();
	}

	public  static synchronized NegocioContratoServicioTecnico getInstance() {
		if (negocio == null)
			negocio = new NegocioContratoServicioTecnico();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{		
		return new PerCliente().getAll(); 
	}
	
	public Cliente getCliente(Cliente cliente){
		return new PerCliente().getDatos(cliente);
	}
	
	public List<ContratoServicioTecnico> getContratos() throws ExcFiltroExcepcion{				
		List<ContratoServicioTecnico> contratos = perContrato.getAll();
		return contratos; 
	}	
	
	
	public List<DetalleContrato> getDetalleSolicitud(SolicitudServicioTecnico solicitud) throws ExcFiltroExcepcion{
		List<DetalleSolicitud> detalles = new PerDetalleSolicitud().getTodasErriquesidas(solicitud);
		List<DetalleContrato> detallesContrato = new ArrayList<DetalleContrato>();
		DetalleContrato detalle;
		Labor prodcuto;
		ArticuloVenta producto2;
		Productor prodcutor = solicitud.getProductor();
		for (DetalleSolicitud item : detalles) {
		if (item.getProducto().getClass().equals(Labor.class)){
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
		
		
		/*
		if (item.getProducto().getClass().equals(ArticuloVenta.class)){
			detalle = new DetalleContrato();
			detalle.setProducto(item.getProducto());
		producto2 = (ArticuloVenta) item.getProducto();
			producto2 = new PerArticuloVenta().getDato(producto2);
			detalle.setCantidad(1.0);
			detalle.setPrecioUnidad(producto2.getPrecioBase());
		
			try{
			
				for (UnidadSolicitada item2 : item.getSolicitado()) {
			
					
							if (solicitud.getServicio().getManejaPases())
							detalle.setCantidad(item2.getCantidad()*item.getPases());
							else	
								detalle.setCantidad(item2.getCantidad());
				
						}
				
				
			
			//	if (detalle.getCantidad().equals(1.0) && item.getSolicitado().size()==1)
		//
			//		detalle.setCantidad(item.getSolicitado().get(0).getCantidad())			;
					
			}catch (Exception e) {
				// TODO: handle exception
			}
			detalle.setSubtotal(detalle.getPrecioUnidad()*detalle.getCantidad());
			detallesContrato.add(detalle);
		}
		*/
		
		
		}
		 
		List<SalidaExternaArticulo> m = getSalidaExternaArticulos(solicitud); 
		List<DevolucionArticulo> n = getDevolucionArticulos(solicitud);
		if (m!=null){
		List<DetalleContrato> z = getDetalleArticulos(m,n);
		detallesContrato.addAll(z);}
		return detallesContrato; 
	}
	
	
	public  List<DetalleContrato> getDetalleArticulos(List<SalidaExternaArticulo> salidaExternaArticulo , List<DevolucionArticulo> devolucionArticulo){
		List<DetalleSalidaExternaArticulo> salidas = new ArrayList<DetalleSalidaExternaArticulo>();
		for (SalidaExternaArticulo SalidaExterna : salidaExternaArticulo) {
			salidas.addAll(SalidaExterna.getDetalleSalidaExternaArticulos());
		}
		List<DetalleDevolucionArticulo> devoluciones = new ArrayList<DetalleDevolucionArticulo>();
		for (DevolucionArticulo DevolucionArticulos : devolucionArticulo) {
			devoluciones.addAll(DevolucionArticulos.getDetalleDevolucionArticulos());
		}
		
				
	 List<DetalleContrato> detalleContratos = new ArrayList<DetalleContrato>();
	 int a = 0;
	 for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo:salidas) {
		ArticuloVenta articulo = detalleSalidaExternaArticulo.getArticuloVenta();
	 DetalleContrato detalle = new DetalleContrato();
	 detalle.setProducto(articulo);
	 detalle.setCantidad(0.0);
	 detalle.setPrecioUnidad(articulo.getPrecioBase());
	
	 detalle.setUnidadCobro(new PerUnidadMedida().getUnidades().get(0));
	 /*
	 int a = 0;
	 if (detalleContratos ==null){
		 detalleContratos = Arrays.asList(detalle);
	 } else
	 */
	/* for (DetalleContrato detalleContrato : detalleContratos) {
		if (detalleContrato.getProducto().equals(detalle)){
		a++;	
		}
		 if (a==1)
				detalleContratos.add(detalle);
				}*/
	// boolean añadido = detalleContratos.contains(detalle.getProducto());
	// if (!añadido)
	 if (a==0){
	 detalleContratos.add(detalle);
	 a++;
	 } else {
		 List<DetalleContrato> detalleContratos2 = detalleContratos;
		 boolean existe = false;
		for (DetalleContrato detalleContrato : detalleContratos2) {
			
			if(detalleContrato.getProducto().equals(detalle.getProducto())){
				existe=true;
			}
			
		}
		if (!existe){
			detalleContratos.add(detalle);
		}
	 } 
	 
	}
	
	
	 for (DetalleContrato detalleContrato : detalleContratos) {
		 for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo : salidas) {
		     if (detalleContrato.getProducto().getDescripcion()==detalleSalidaExternaArticulo.getArticuloVenta().getDescripcion())
		    	detalleContrato.setCantidad(detalleContrato.getCantidad()+detalleSalidaExternaArticulo.getCantidad()) 	;		    	 
		 }
		 detalleContrato.setSubtotal(detalleContrato.getCantidad()*((ArticuloVenta)detalleContrato.getProducto()).getPrecioBase());
	 }
		 
		 for (DetalleContrato detalleContrato2 : detalleContratos) {
			 for (DetalleDevolucionArticulo detalleDevolucionArticulo : devoluciones) {
				 String h = detalleContrato2.getProducto().getDescripcion();
				 String j = detalleDevolucionArticulo.getArticuloVenta().getDescripcion();
			 //    if (detalleContrato2.getProducto().getDescripcion()==detalleDevolucionArticulo.getArticuloVenta().getDescripcion())
				 
				 boolean r = h.equals(j);
				 boolean r1 = h==j;
				    if (r)
			    	detalleContrato2.setCantidad(detalleContrato2.getCantidad()-detalleDevolucionArticulo.getCantidad()) 	;			     
			 }
			 detalleContrato2.setSubtotal(detalleContrato2.getCantidad()*((ArticuloVenta)detalleContrato2.getProducto()).getPrecioBase());
	}
	 
		return detalleContratos;
		
		
		
	}
	
	
 public List<SalidaExternaArticulo> getSalidaExternaArticulos(SolicitudServicioTecnico solicitudServicioTecnico){
	 return new PerSalidaExternaArticulo().getAllSolicitud(solicitudServicioTecnico);
 }
	
	
public List<DevolucionArticulo> getDevolucionArticulos(SolicitudServicioTecnico solicitudServicioTecnico){
	List<SalidaExternaArticulo> a = new PerSalidaExternaArticulo().getAllSolicitud(solicitudServicioTecnico);
	return new PerDevolucionArticulo().getAllSalidas(a);
}	
	
	
	/*
	public List<Servicio> getServicios(SolicitudServicioTecnico solicitud) {
		List<Servicio> productos = new ArrayList<Servicio>(); 
		productos.add(solicitud.getServicio());
		return productos;
	}*/
	
	public List<SolicitudServicioTecnico> getSolicitudes() {
	//corejir luego	return new PerSolicitudMecanizado().getAllSinPrestar(); 
		return new PerSolicitudServicioTecnico().getSegunEstado(new PerEstadoSolicitud().getServicioTecnicoDespachada());
	}
/*
	public List<IProducto> getServicios(Servicio servicio) {
		List<IProducto> labores = new  PerLabor().getAllLabor(servicio);
		List<IProducto> articulos = new  PerArticuloVenta().getAllArticulo();
		labores.addAll(articulos);
		return labores;
	}
	*/
	
	public List<UnidadProductiva> getDirecciones(Cliente cliente) throws ExcFiltroExcepcion {	
		return  new PerUnidadProductiva().getTodos(cliente);
	}
	
	
	public List<PlanFinanciamiento> getPlanesFinanciamientos() throws ExcFiltroExcepcion {
		return new PerPlanFinanciamiento().getAll();
	}
	
	public void guardar(ContratoServicioTecnico contrato)  throws Exception, SQLException{		
		perContrato.guardar(contrato, getControlSede());
	}
	
	public void eliminar(){
		
	}
	
	public void anular(ContratoServicioTecnico contrato) throws Exception{
		perContrato.anularContrato(contrato);
	}


	public void procesar(ContratoServicioTecnico contrato) throws Exception{
		perContrato.procesarContrato(contrato);
	}
	
	
	public ContratoServicioTecnico getContrato() {
		return contrato;
	}
	
	public ContratoServicioTecnico getDetallesDeContrato(ContratoServicioTecnico contrato){
		return this.perContrato.getDetalle(contrato);
	}

	public void setContrato(ContratoServicioTecnico  contrato) {
		this.contrato = contrato;
	}
	
	public List<TipoContrato> getTiposContrato() throws ExcFiltroExcepcion{
		return new PerTipoContrato().getAll();
	}
	
	public Contrato getCuotasContrato(Contrato cont){
		return new PerCuotaAPagarCliente().getCuotasAPagar(cont);
		
	}

	public List<IProducto> getProductos() throws ExcFiltroExcepcion{
		TipoServicio tipo = new PerTipoServicio().getTipoServicioTecnico();
		List<IProducto> servicios = new PerLabor().getAllProductos(tipo);
		List<IProducto> a = new PerArticuloVenta().getAllArticulo();
		System.out.println(a.size() +"  "+servicios.size());
		servicios.addAll(a);
		System.out.println(servicios.size());
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
	
	public TipoContrato getServicioGravado() throws ExcFiltroExcepcion{
		List<Criterion> a = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 1)});
	return	new PerTipoContrato().getcritaeriaUno(a);
	}
	
	
}