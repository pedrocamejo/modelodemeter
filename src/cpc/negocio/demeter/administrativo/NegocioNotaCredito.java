package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerIva;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaCredito;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoDocumento;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioNotaCredito implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1558131660340608473L;
	private static NegocioNotaCredito 	negocio;
	private PerNotaCredito				persistencia;
	private NotaCredito					nota;
	private List<NotaCredito>			notas;
	
	private NegocioNotaCredito(){
		persistencia = new PerNotaCredito(); 
	}

	public  static synchronized NegocioNotaCredito getInstance() {
		if (negocio == null)
			negocio = new NegocioNotaCredito();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{
		PerCliente perCliente = new PerCliente();
		List<Cliente> clientes = perCliente.getAdministrativos();
		return clientes; 
	}
	
	public List<IProducto> getProductos() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerLabor().getAllLabor();
		servicios.addAll(new PerArticuloVenta().getAllArticulo());
		return servicios; 
	}

	public void guardar(NotaCredito docu) throws Exception{
		for (DetalleDocumentoFiscal item :docu.getDetalles()){
			item.setDocumento(docu);
		}
		docu.setDireccionFiscal(docu.getFactura().getDireccionFiscal());
		if(docu.getFactura().getMontoTotal().equals(docu.getMontoTotal())){
			if(docu.getFactura().getContrato() != null){
				docu.getFactura().getContrato().setFacturado(false);
			}
		}
		persistencia.guardar(docu, docu.getId(), getControlSede());
	}
	
	public void anular(NotaCredito nota) throws ExcFiltroExcepcion{
		persistencia.anular(nota);
	}
	
	public void eliminar(){

	}
	
	
	public List<NotaCredito> getTodos() {
		try {
			notas= persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return notas;
	}

	public NotaCredito  NotaNueva() throws ExcDatosNoValido{
		nota = new NotaCredito();
		try{
			ControlSede control = getControlSede();
			nota.setFecha(control.getFechaCierreFactura());
			nota.setSerie(control.getSerie());
			nota.setPorcDescuento(new Double(0));
			nota.setEstado(new PerEstadoDocumentoFiscal().getEstadoNuevaFactura());
			nota.setTipoDocumento(new PerTipoDocumento().getNotaCredito());
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la sede");
		}
		return nota;
	}
	


	public NotaCredito getNota() {
		return nota;
	}

	public void setNota(NotaCredito nota) {

		if (nota.getId() != null)
			this.nota = persistencia.getDatos(nota);
		else
			this.nota=nota;
	}
	
	public IProducto enriqueserProducto(IProducto producto){
		if (producto instanceof Labor) {
			return new PerLabor().getDato((Labor)producto);
		}else{
			return new PerArticuloVenta().getDato(producto);
		}
	}
	
	public Cliente getCliente(Cliente cliente){
		PerCliente persistencia = new PerCliente();
		Cliente client = persistencia.getDatos(cliente);
		return client;
	}

	public List<DocumentoFiscal> getFacturas() throws ExcFiltroExcepcion {
		PerFactura perFactura = new PerFactura();
		List<DocumentoFiscal> facturas = perFactura.getAll();
		//perCliente.cerrar();
		return facturas; 
	}
	//getFacturasActivasProject()
	
	public List<DocumentoFiscal> getFacturasActivasProject() throws ExcFiltroExcepcion {
		PerFactura perFactura = new PerFactura();
		List<DocumentoFiscal> facturas = perFactura.getAllActivasProject();
		//perCliente.cerrar();
		return facturas; 
	}
	public DocumentoFiscal getFacturaProject(DocumentoFiscal docu) throws ExcFiltroExcepcion {
		PerFactura perFactura = new PerFactura();
		DocumentoFiscal factura = perFactura.getDocumento(docu);
		//perCliente.cerrar();
		return factura; 
	}
	public void buscar(Integer id){
		nota = persistencia.buscarId(id);
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
		impuestoFactura.setPorcentaje(Math.abs(impuesto.getPorcentaje()));
		impuestoFactura.addBase(Math.abs(monto));
		impuestoFactura.actualizarMonto();
	}

	public void actualizarImpuestoFactura( List<ImpuestoDocumentoFiscal> impuestosFacturas, List<ImpuestoDocumentoFiscal> impuestosFactura) throws ExcFiltroExcepcion{
		for(ImpuestoDocumentoFiscal impuestoFactura : impuestosFactura){
			actualizarImpuestoFactura(impuestosFacturas, impuestoFactura.getImpuesto(), impuestoFactura.getBase());
		 }
	}
	
	public NotaCredito refrescarNota(NotaCredito nota){
		this.nota = persistencia.getDatos(nota);
		return this.nota;
	}
	
	public void actualizarImpuestoFactura( Impuesto impuesto, double monto) throws ExcFiltroExcepcion{
		 actualizarImpuestoFactura(nota.getImpuestos(), impuesto, monto);
	}
	
	public List<ImpuestoDocumentoFiscal> InicializarImpuestosNota() throws ExcFiltroExcepcion{
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
	
	
	public double getTotalImpuestoFactura() throws ExcFiltroExcepcion{
		return getTotalImpuestoFactura(nota.getImpuestos());
	}
	
	public double getTotalImpuestoFactura(List<ImpuestoDocumentoFiscal> impuestosFacturas) throws ExcFiltroExcepcion{
		double monto = 0;
		for(ImpuestoDocumentoFiscal impuestoFactura : impuestosFacturas){
			 monto += impuestoFactura.getMonto();
		 }
		 
		 return monto; 
	}
	
	public List<IProducto> getServiciosFactura(Integer id){
		return new PerFactura().getServiciosFacturados(id);
	}
	
	public List<DocumentoFiscal> getFacturasActivas(){
		return new PerFactura().getAllActivas();
	}
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	public Impuesto getImpuesto(String descripcion, double porcentaje){
		return new PerIva().getImpuesto(descripcion, porcentaje);
		
	}
	public Boolean ValidadTotales(NotaCredito notaCredito){
		Double montototalcreditofac = new PerNotaCredito().getTotalSaldoNotasCreditos(notaCredito.getFactura());
		       Double creditofactura = notaCredito.getMontoTotal();
		       Double montofactura = notaCredito.getFactura().getMontoTotal();
		       if ((Math.abs(montototalcreditofac)+creditofactura)<=montofactura )
		    	   return true ;
		       
		       else return false;
	}
	
	public List<NotaCredito> getNotaCreditosConSaldo(Cliente cliente)
	{
		return persistencia.getAll(cliente);
	}
		       
}
