package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.Cheque;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaDebito;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerIva;
import cpc.persistencia.demeter.implementacion.administrativo.PerCheque;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaDebito;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoDocumento;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerCostoBancario;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioNotaDebito implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1402328880761887673L;
	private static NegocioNotaDebito 	negocio;
	private PerNotaDebito				persistencia;
	private PerCheque					perCheque;
	private NotaDebito					nota;
	private List<NotaDebito>			notas;
	
	private NegocioNotaDebito(){
		/*SessionDao dao = SessionDao.getInstance();
		dao.test();
		dao.newDaoGenerico(new PerFactura());*/ 
		persistencia = new PerNotaDebito(); 
		perCheque = new PerCheque();
	}

	public  static synchronized NegocioNotaDebito getInstance() {
		if (negocio == null)
			negocio = new NegocioNotaDebito();
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
	
	public List<IProducto> getCostoBancario() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerCostoBancario().getAllCostoBancarioActivo();
		
		return servicios; 
	}
	
	public List<IProducto> getCostoBancarioNoCheque() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerCostoBancario().getAllCostoBancarioActivoNoCheque();
		
		return servicios; 
	}
	public IProducto getCostoBancario(String descripcion) throws ExcFiltroExcepcion{
		
		descripcion = "%"+descripcion+"%";
		return new PerCostoBancario().getAllCostoBancarioActivo(descripcion);
	
	}	
	public IProducto getReversoRecibo() throws ExcFiltroExcepcion{
		List<IProducto> servicios = new PerCostoBancario().getReversoRecibo();
		
		return servicios.get(0); 
	}
	
	public void guardar(NotaDebito docu) throws Exception{
		for (DetalleDocumentoFiscal item :docu.getDetalles())
			item.setDocumento(docu);
			docu.setDireccionFiscal(docu.getFactura().getDireccionFiscal());	
		persistencia.guardar(docu, docu.getId(), getControlSede());
	}
	
	public void anular(NotaDebito nota) throws ExcFiltroExcepcion{
		persistencia.anular(nota);
	}
	
	public void eliminar(){

	}
	
	
	public List<NotaDebito> getTodos() {
		try {
			notas= persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return notas;
	}

	public NotaDebito  NotaNueva() throws ExcDatosNoValido{
		nota = new NotaDebito();
		try{
			ControlSede control = getControlSede();
			nota.setFecha(control.getFechaCierreFactura());
			nota.setSerie(control.getSerie());
			nota.setPorcDescuento(new Double(0));
			nota.setEstado(new PerEstadoDocumentoFiscal().getEstadoNuevaFactura());
			nota.setTipoDocumento(new PerTipoDocumento().getNotaDedito());
		}catch (Exception e){
			throw new ExcDatosNoValido("Existen datos invalidos para la sede");
		}
		return nota;
	}
	
	public IProducto enriqueserProducto(IProducto producto){
		if (producto instanceof Labor) {
			return new PerLabor().getDato((Labor)producto);
		}else if  (producto instanceof ArticuloVenta)  {
			return new PerArticuloVenta().getDato(producto);
		}else 
			return new PerCostoBancario().getDato(producto);
	}


	public NotaDebito getNota() {
		return nota;
	}

	public void setNota(NotaDebito nota) {

		if (nota.getId() != null)
			this.nota = persistencia.getDatos(nota);
		else
			this.nota=nota;
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
	
	public NotaDebito refrescarNota(NotaDebito nota){
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

	public List<DocumentoFiscal> getFacturasCanceladas(){
		return new PerFactura().getAllCanceladas();
	}
	
	public List<DocumentoFiscal> getFacturasCanceladasCheque(){
		return new PerFactura().getAllCanceladasCheque();
	                        
	}
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	
	
	public Impuesto getImpuestoExcento(){
		return new PerIva().getExcento();
	}
	
	
	public List<Cheque> getCheques(DocumentoFiscal factura) throws ExcFiltroExcepcion
	{
		return perCheque.getAll(factura);
		 
	}
	
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
	
	
	
}
