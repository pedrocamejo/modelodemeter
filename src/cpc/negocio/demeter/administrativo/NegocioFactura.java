package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.InteresMora;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerDetalleContrato;
import cpc.persistencia.demeter.implementacion.PerIva;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.administrativo.PerContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoDocumento;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerInteresMora;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioFactura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8699398314535599289L;
	private static NegocioFactura 	negocio;
	private PerFactura				persistenciafactura;
	private DocumentoFiscal			factura;
	private List<DocumentoFiscal>	facturas;
	
	private NegocioFactura(){
		persistenciafactura = new PerFactura(); 
	}

	public  static synchronized NegocioFactura getInstance() {
		if (negocio == null)
			negocio = new NegocioFactura();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{
		PerCliente perCliente = new PerCliente();
		List<Cliente> clientes = perCliente.getAdministrativos();
		return clientes; 
	}
	public List<Cliente> getClientesProject() throws ExcFiltroExcepcion{
		PerCliente perCliente = new PerCliente();
		List<Cliente> clientes = perCliente.getAdministrativosproject();
		return clientes; 
	}
	
	public List<Contrato> getContratosPre() throws ExcFiltroExcepcion{
		return new PerContrato().getAllPorFirmarSinFactura(); 
	}

	public List<Contrato> getContratosPost() throws ExcFiltroExcepcion{
		return new PerContrato().getAllTerminadosSinFactura(); 
	}
	
	public double getAdelantos(Contrato ctto) throws ExcFiltroExcepcion{
		return new PerRecibo().getAnticipos(ctto);
	}
	
	
	public List<DetalleDocumentoFiscal> getDetalleContrato(Contrato contrato, boolean post) throws ExcFiltroExcepcion{
		List<DetalleContrato> detalles = new PerDetalleContrato().getAll(contrato);
		List<DetalleDocumentoFiscal> detallesFactura = new ArrayList<DetalleDocumentoFiscal>();
		DetalleDocumentoFiscal detalle;
		for (DetalleContrato item : detalles) {
			detalle = new DetalleDocumentoFiscal();
			detalle.setServicio(item.getProducto());
			if (post)
				if (item.getCantidadReal() == null)
					detalle.setCantidad(item.getCantidad());
				else	
					detalle.setCantidad(item.getCantidadReal());
			else
				detalle.setCantidad(item.getCantidad());
			detalle.setPrecioUnitario(item.getPrecioUnidad());
			detalle.setAlicuota(item.getProducto().getImpuesto());
			detallesFactura.add(detalle);
		}
		return detallesFactura; 
	}
	
	
	public List<IProducto> getProductos() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerLabor().getAllLabor();
		servicios.addAll(new PerArticuloVenta().getAllArticulo());
		return servicios; 
	}
	
	
	public List<IProducto> getProductosActivos() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerLabor().getAllLaborACTIVAS();
		servicios.addAll(new PerArticuloVenta().getAllArticuloActivo());
		return servicios; 
	}
	public List<IProducto> getProductosnomecanizados() throws ExcFiltroExcepcion{
		TipoServicio Tipo = new PerTipoServicio().getTipoMecanizado();
		List<IProducto> servicios = new PerLabor().getnoTipo(Tipo);
		servicios.addAll(new PerArticuloVenta().getAllArticuloActivo());
		return servicios; 
	}
	//ok
	public List<IProducto> getTodosProductos() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerLabor().getAllLabor();
		servicios.addAll(new PerArticuloVenta().getAllArticulo());
		servicios.addAll(new PerInteresMora().getAll());
		return servicios; 
	}
	
	public ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	public void guardar(DocumentoFiscal docu) throws Exception{
		if (docu.getDetalles() != null){
			for (DetalleDocumentoFiscal item :docu.getDetalles())
				item.setDocumento(docu);
			persistenciafactura.guardar(docu, docu.getId(), getControlSede());
		}else
			persistenciafactura.guardarVieja(docu);
	}
	
	public IProducto enriqueserProducto(IProducto producto){
		if (producto instanceof Labor) {
			return new PerLabor().getDato((Labor)producto);
		}else{
			return new PerArticuloVenta().getDato(producto);
		}
	}
	
	public void eliminar(){

	}
	
	public ClienteAdministrativo getExpedienteAdministrativo(Cliente cliente) throws ExcFiltroExcepcion {
		return new PerClienteAdministrativo().getExpediente(cliente);
	}
	
	public List<DocumentoFiscal> getTodos() throws ExcFiltroExcepcion{
		return persistenciafactura.getAll();
	}
	
	public List<DocumentoFiscal> getTodosProject() throws ExcFiltroExcepcion{
		return persistenciafactura.getAllProject();
	}
	public DocumentoFiscal getDocumento(DocumentoFiscal docu) throws ExcFiltroExcepcion{
		return persistenciafactura.getDocumento(docu);
	}
	
	
	public List<DocumentoFiscal> getTodosfecha(String inicio,String termino) {
		try {
			facturas= persistenciafactura.getAllfechas(inicio, termino);
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return facturas;
	}
	
	public InteresMora getInteresMora(Integer id) throws ExcFiltroExcepcion{
		return new PerInteresMora().getInteres(id);
	}
	
	
	public Date getFechaInico() throws ExcFiltroExcepcion{
		return getControlSede().getFechaInicio();
	}
	
	public List<DocumentoFiscal> getTodosPost() {
		try {
			facturas= persistenciafactura.getAllPost();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return facturas;
	}
	
	public List<DocumentoFiscal> getTodosPostfecha(String inicio,String termino) {
		try {
			facturas= persistenciafactura.getAllPostfechas(inicio, termino);
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return facturas;
	}

	public List<DocumentoFiscal> getTodosPre() {
		try {
			facturas= persistenciafactura.getAllPre();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return facturas;
	}
	
	public List<DocumentoFiscal> getTodosprefecha(String inicio,String termino) {
		try {
			facturas= persistenciafactura.getAllPrefechas(inicio, termino);
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return facturas;
	}
	
	public DocumentoFiscal  facturaNueva() throws ExcDatosNoValido{
		factura = new DocumentoFiscal();
		try{
			ControlSede control = getControlSede();
			factura.setFecha(control.getFechaCierreFactura());
			factura.setSerie(control.getSerie());
			factura.setTipoDocumento(new PerTipoDocumento().getTipoFactura());
			factura.setPorcDescuento(control.getPorcentajeDescuento());
			factura.setEstado(new PerEstadoDocumentoFiscal().getEstadoNuevaFactura());
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la Sede");
		}
		return factura;
	}
	
	public PerFactura getPersistenciafactura() {
		return persistenciafactura;
	}

	public void setPersistenciafactura(PerFactura persistenciafactura) {
		this.persistenciafactura = persistenciafactura;
	}

	public DocumentoFiscal getFactura() {
		return factura;
	}

	public void setFactura(DocumentoFiscal factura) {
		if (factura.getId() != null)
			this.factura = persistenciafactura.getDatos(factura);
		else
			this.factura  = factura;
	}
	

	
	public Cliente getCliente(Cliente cliente){
		PerCliente persistencia = new PerCliente();
	
		
		Cliente client = persistencia.getDatos(cliente);
		return client;
	}
	
	public Cliente getClienteproject(Cliente cliente){
		PerCliente persistencia = new PerCliente();
		cliente =persistencia.getclienteproject(cliente);	
		
	
		return cliente;
	}
	
	

	public List<DocumentoFiscal> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<DocumentoFiscal> facturas) {
		this.facturas = facturas;
	}
	
	public List<DocumentoFiscal> getDocumentosConSaldo(){
		return persistenciafactura.getDocumentoConSaldo();
	}
	
	public List<ImpuestoDocumentoFiscal> getImpuestos(){
		return persistenciafactura.getImpuestosDocumento(factura);
	}
	
	public void actualizaPersistenciaFacturarImpuestosDocumento(){
		factura.setImpuestos(persistenciafactura.getImpuestosDocumento(factura));
	}
	
	public DocumentoFiscal buscar(Integer id){
		return  persistenciafactura.buscarId(id);
	}

	
	public DocumentoFiscal buscar(Integer nroControl, String serie){
		return  persistenciafactura.buscar(nroControl,serie);
	}
	
	
	public List<Impuesto> getTodosImpuestos() throws ExcFiltroExcepcion{
		return new PerIva().getAll();
	}
	

	private ImpuestoDocumentoFiscal buscarImpuesto(List<ImpuestoDocumentoFiscal> impuestosFactura, Impuesto impuesto){
		for(ImpuestoDocumentoFiscal impuestoFactura : impuestosFactura){
			 if (impuestoFactura.getImpuesto().getId() == impuesto.getId())
				 return  impuestoFactura;
		 }
		return null;
	}
	
	public void actualizarImpuestoFactura( List<ImpuestoDocumentoFiscal> impuestosFactura, Impuesto impuesto, double monto) throws ExcFiltroExcepcion{
		ImpuestoDocumentoFiscal impuestoFactura = buscarImpuesto(impuestosFactura,impuesto);
		impuestoFactura.setPorcentaje(impuesto.getPorcentaje());
		impuestoFactura.addBase(monto);
		impuestoFactura.actualizarMonto();
	}

	public void actualizarImpuestoFactura( List<ImpuestoDocumentoFiscal> impuestosFacturas, List<ImpuestoDocumentoFiscal> impuestosFactura) throws ExcFiltroExcepcion{
		for(ImpuestoDocumentoFiscal impuestoFactura : impuestosFactura){
			actualizarImpuestoFactura(impuestosFacturas, impuestoFactura.getImpuesto(), impuestoFactura.getBase());
		 }
	}
	
	
	public void actualizarImpuestoFactura( Impuesto impuesto, double monto) throws ExcFiltroExcepcion{
		 actualizarImpuestoFactura(factura.getImpuestos(), impuesto, monto);
	}
	
	public List<ImpuestoDocumentoFiscal> InicializarImpuestosFactura() throws ExcFiltroExcepcion{
		 List<Impuesto> impuestos = new PerIva().getAll();
		 List<ImpuestoDocumentoFiscal> impuestosFacturas = new ArrayList<ImpuestoDocumentoFiscal>();
		 ImpuestoDocumentoFiscal impuestoFactura;
		 for(Impuesto impuesto : impuestos){
			 impuestoFactura = new ImpuestoDocumentoFiscal();
			 impuestoFactura.setImpuesto(impuesto);
			 impuestosFacturas.add(impuestoFactura);
			 impuestoFactura.setPorcentaje(impuesto.getPorcentaje());
			 impuestoFactura.setBase(0);
			 impuestoFactura.setMonto(0);
		 }
		 return impuestosFacturas; 
	}
	
	public List<ImpuestoDocumentoFiscal> InicializarImpuestosFactura(List<ImpuestoDocumentoFiscal> impuestosFactura) throws ExcFiltroExcepcion{
		for(ImpuestoDocumentoFiscal impuestoFactura : impuestosFactura){
			 impuestoFactura.setBase(0);
			 impuestoFactura.setMonto(0);
		 }
		return impuestosFactura;
	}
	
	public void anular(DocumentoFiscal factura){
		//preparar la factura y sus recibos
		// hay que devolver el monto aplicado del recibo 
		// hay que anular el contrato 
		if (factura.getContrato()!= null)
		{
			factura.getContrato().setFacturado(false);
		}
		factura.setMontoBase(new Double(0));
		factura.setMontoSaldo(new Double(0));
		factura.setMontoTotal(new Double(0));
		factura.setEstado(new PerEstadoDocumentoFiscal().getAnulado());
		// guardar contrato no facturado 
		for(ImpuestoDocumentoFiscal impuesto: factura.getImpuestos())
		{
			impuesto.setBase(0);
			impuesto.setMonto(0);
		}
		//recibos
		for(ReciboDocumentoFiscal recibo :factura.getRecibos())
		{
			Double montoRecibo = recibo.getRecibo().getSaldo();
			Double montoAplicado = recibo.getMonto(); // monto aplicado a la factura 
			
			recibo.getRecibo().setSaldo(montoAplicado+ montoRecibo); //reintegro el dinero 
			recibo.setMonto(0.0);
		}
		persistenciafactura.anular(factura);
	}
	
	public double getTotalImpuestoFactura() throws ExcFiltroExcepcion{
		return getTotalImpuestoFactura(factura.getImpuestos());
	}
	
	public double getTotalImpuestoFactura(List<ImpuestoDocumentoFiscal> impuestosFacturas) throws ExcFiltroExcepcion{
		double monto = 0.0;
		for(ImpuestoDocumentoFiscal impuestoFactura : impuestosFacturas){
			 monto += impuestoFactura.getMonto();
		 }
		 return monto; 
	}
	
	public List<IProducto> getServiciosFactura(Integer id){
		return persistenciafactura.getServiciosFacturados(id);
	}
	
	public List<DocumentoFiscal> getAllActivas(){
		return persistenciafactura.getAllActivas();
	}
	
	
	public List<DetalleDocumentoFiscal> getServicosFacturados(Date inicio, Date fin){
		List<DetalleDocumentoFiscal> suma = persistenciafactura.getServiciosFacturadosDebito(inicio, fin);
		
		
		List<DetalleDocumentoFiscal> resta = persistenciafactura.getServiciosCredito(inicio, fin);
		
		for (int i = 0; i < suma.size(); i++) {
			DetalleDocumentoFiscal detalle = suma.get(i);
			
			for (DetalleDocumentoFiscal detallecredito : resta) {
				boolean condicion1 = detalle.getServicio().equals(detallecredito.getServicio());
				boolean condicion2 = detalle.getPrecioUnitario()==detallecredito.getPrecioUnitario();
				 if (condicion1&&condicion2){
					  double cantidado = suma.get(i).getCantidad();
					  double cantidadC = detallecredito.getCantidad();
					  suma.get(i).setCantidad(cantidado-cantidadC);
					 
				 }
			}
		}
		
		return suma;
	}
	
	
	public List<Object> getServicosFacturados2(Date inicio, Date fin){
		List<Object> sumas = persistenciafactura.getServiciosFacturadosDebito2(inicio, fin);
		
		
		List<Object> restas = persistenciafactura.getServiciosCredito2(inicio, fin);
		
		for (int i = 0; i < sumas.size(); i++) {
			 Object[] suma = (Object[])  sumas.get(i);
			
			for (Object detallecredito : restas) {
				Object[] resta = (Object[])  detallecredito;
				Labor laborsuma = (Labor) suma[1];
				Labor laborresta = (Labor) resta[1];
				
				Double preciosuma = (Double) suma[3];
				Double precioresta =(Double) resta[3];
				
				boolean condicion1 = laborsuma.equals(laborresta);
				if (condicion1){ 
					System.out.println("misma labor");
					
				}
				boolean condicion2 = preciosuma.equals(precioresta);
				if (condicion2){ 
					System.out.println("mismo precio");
					
				}
				 if (condicion1&&condicion2){
					  double cantidado = (Double) suma[0];
					  double cantidadC = (Double) resta[0];
					  //sumas.get(i).setCantidad(cantidado-cantidadC);
					  cantidado=cantidado-cantidadC;
					  
					  double totalfo = (Double) suma[5];
					  double totalno = (Double) resta[5];
					  //sumas.get(i).setCantidad(cantidado-cantidadC);
					  totalfo=totalfo-Math.abs(totalno);
					  
					  double saldofo = (Double) suma[6];
					  double saldono = (Double) resta[6];
					  //sumas.get(i).setCantidad(cantidado-cantidadC);
					  saldofo=saldofo-Math.abs(saldono);
					  
					   suma[0]=cantidado;
					   suma[5]=totalfo;
					   suma[6]=saldofo;
					   
				 }
			}
		}
		
		return sumas;
	}
	public List<DetalleDocumentoFiscal> getProductosFacturados(Date inicio, Date fin){
		return persistenciafactura.getProductosFacturados(inicio, fin);
	}
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	
	public Integer getHijosActivos(Integer id){
		return persistenciafactura.getHijosActivos(id);
	}
	public List<Object> getProductosFacturados2(Date inicio, Date fin){
		List<Object> sumas = persistenciafactura.getProductosFacturadosDebito2(inicio, fin);
		
		
		List<Object> restas = persistenciafactura.getProductosFacturadosCredito2(inicio, fin);
		
		for (int i = 0; i < sumas.size(); i++) {
			 Object[] suma = (Object[])  sumas.get(i);
			
			for (Object detallecredito : restas) {
				Object[] resta = (Object[])  detallecredito;
				ArticuloVenta laborsuma = (ArticuloVenta) suma[1];
				ArticuloVenta laborresta = (ArticuloVenta) resta[1];
				
				Double preciosuma = (Double) suma[3];
				Double precioresta =(Double) resta[3];
				
				boolean condicion1 = laborsuma.equals(laborresta);
				if (condicion1){ 
					System.out.println("misma labor");
					
				}
				boolean condicion2 = preciosuma.equals(precioresta);
				if (condicion2){ 
					System.out.println("mismo precio");
					
				}
				 if (condicion1&&condicion2){
					  double cantidado = (Double) suma[0];
					  double cantidadC = (Double) resta[0];
					  //sumas.get(i).setCantidad(cantidado-cantidadC);
					  cantidado=cantidado-cantidadC;
					  
					  double totalfo = (Double) suma[5];
					  double totalno = (Double) resta[5];
					  //sumas.get(i).setCantidad(cantidado-cantidadC);
					  totalfo=totalfo-Math.abs(totalno);
					  
					  double saldofo = (Double) suma[6];
					  double saldono = (Double) resta[6];
					  //sumas.get(i).setCantidad(cantidado-cantidadC);
					  saldofo=saldofo-Math.abs(saldono);
					  
					   suma[0]=cantidado;
					   suma[5]=totalfo;
					   suma[6]=saldofo;
					   
				 }
			}
		}
		
		return sumas;
	}
	
	public List<Object> getProductosSinDebito(Date inicio, Date fin){
		List<Object> sumas = persistenciafactura.getProductosFacturadosDebito2(inicio, fin);
		
		//creamos de nuevo el objeto restas pero con un boolean adicional
		List<Object> restas = persistenciafactura.getProductosFacturadosCredito2(inicio, fin);
	/*	List<Object> restasO = persistenciafactura.getProductosFacturadosCredito2(inicio, fin);
		for (int i = 0; i < restasO.size(); i++) {
			 Object[] restaaux = (Object[])  restasO.get(i);
			 Object[] restaboolean = new Object[]{
				 restaaux[0],
				 restaaux[1],
				 restaaux[2],
				 restaaux[3],
				 restaaux[4],
				 restaaux[5],
				 restaaux[6],
				 false
				 
			 };
 			 
			 
		}*/
		Iterator<Object> i = restas.iterator();
		while (i.hasNext()) {
			 Object[] resta = (Object[])  i.next();
			
			for (Object detallecredito : sumas) {
				Object[] suma = (Object[])  detallecredito;
				ArticuloVenta laborsuma = (ArticuloVenta) suma[1];
				ArticuloVenta laborresta = (ArticuloVenta) resta[1];
				
				Double preciosuma = (Double) suma[3];
				Double precioresta =(Double) resta[3];
				
				boolean condicion1 = laborsuma.equals(laborresta);
				if (condicion1){ 
					System.out.println("misma labor");
					
				}
				boolean condicion2 = preciosuma.equals(precioresta);
				if (condicion2){ 
					System.out.println("mismo precio");
					
				}
				 if (condicion1&&condicion2){
					try {
						i.remove();
					} catch (Exception e) {
					e.printStackTrace();	
					}
					   
				 }
			}
		}
		
		return restas;
	}
	
	public List<Object> getServiciosSinDebito(Date inicio, Date fin){
		List<Object> sumas = persistenciafactura.getServiciosFacturadosDebito2(inicio, fin);
		
		//creamos de nuevo el objeto restas pero con un boolean adicional
		List<Object> restas = persistenciafactura.getServiciosCredito2(inicio, fin);
	/*	List<Object> restasO = persistenciafactura.getProductosFacturadosCredito2(inicio, fin);
		for (int i = 0; i < restasO.size(); i++) {
			 Object[] restaaux = (Object[])  restasO.get(i);
			 Object[] restaboolean = new Object[]{
				 restaaux[0],
				 restaaux[1],
				 restaaux[2],
				 restaaux[3],
				 restaaux[4],
				 restaaux[5],
				 restaaux[6],
				 false
				 
			 };
 			 
			 
		}*/
		Iterator<Object> i = restas.iterator();
		while (i.hasNext()) {
			 Object[] resta = (Object[])  i.next();
			
			for (Object detallecredito : sumas) {
				Object[] suma = (Object[])  detallecredito;
				Labor laborsuma = (Labor) suma[1];
				Labor laborresta = (Labor) resta[1];
				
				Double preciosuma = (Double) suma[3];
				Double precioresta =(Double) resta[3];
				
				boolean condicion1 = laborsuma.equals(laborresta);
				if (condicion1){ 
					System.out.println("misma labor");
					
				}
				boolean condicion2 = preciosuma.equals(precioresta);
				if (condicion2){ 
					System.out.println("mismo precio");
					
				}
				 if (condicion1&&condicion2){
					try {
						i.remove();
					} catch (Exception e) {
					e.printStackTrace();	
					}
					   
				 }
			}
		}
		
		return restas;
	}
	public Impuesto getImpuestoExento(){
		return new PerIva().getExcento();
		
	};
	public Impuesto getIvaElectronico(){
		return new PerIva().getImpuesto("IVA", new Double(10));
		
	};
	public Impuesto getIva(){
		return new PerIva().getImpuesto("IVA", new Double(12));
	};
	public Double getSaldoAFavorReciboElectronico(Cliente cliente){
		Double saldofavor = new Double(0);
		List<Recibo> recibos = new PerRecibo().getRecibosPagoElectronico(cliente);
		for (Recibo recibo : recibos) {
			saldofavor=recibo.getSaldo()+saldofavor;
		}
		return saldofavor;
	}

	public Impuesto getIva9() {
		return new PerIva().getImpuesto("IVA", new Double(9));
	}

	public Impuesto getIva7() {
		return new PerIva().getImpuesto("IVA", new Double(7));
	}
}