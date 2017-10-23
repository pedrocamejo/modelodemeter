package cpc.negocio.demeter.administrativo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;


import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.basico.ConceptoPago;
import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.modelo.sigesp.indice.BancoPK;
import cpc.persistencia.demeter.implementacion.PerBanco;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuentaBancaria;
import cpc.persistencia.demeter.implementacion.administrativo.PerContrato;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaCredito;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaDebito;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoFormaPago;
import cpc.persistencia.demeter.implementacion.basico.PerConceptoPago;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Real;

public class NegocioRecibo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6468752439320318542L;
	private static NegocioRecibo negocio;
	private PerRecibo		persistenciaRecibo;
	private PerEstadoDocumentoFiscal perEstadoDocumentoFiscal;
	private PerNotaCredito	perNotaCredito;
	private PerNotaDebito	perNotaDebito;
	private PerFactura 		perFactura;
	
	private Recibo			recibo;
	private List<Recibo>	recibos;
	
	private NegocioRecibo(){

		persistenciaRecibo = new PerRecibo();
		setPerNotaCredito(new PerNotaCredito());
		setPerNotaDebito(new PerNotaDebito());
		setPerFactura(new PerFactura());
		perEstadoDocumentoFiscal = new PerEstadoDocumentoFiscal();
		
	}

	public  static synchronized NegocioRecibo getInstance() {
		if (negocio == null)
			negocio = new NegocioRecibo();
		return negocio;
	}
	
	public Recibo getRecibo() {
		return recibo;
	}
	
	
	
	public void setRecibo(Recibo recibo) {
		if (recibo.getId() != null)
			this.recibo = persistenciaRecibo.getDatos(recibo);
		else
			this.recibo = recibo;
	}
	
	public List<Banco> getBancos() throws ExcFiltroExcepcion{
		PerBanco perBanco = new PerBanco();
		List<Banco> bancos = perBanco.getAll();
		//perBanco.cerrar();
		return bancos; 
	}

	public List<DocumentoFiscal> getFacturas() throws ExcFiltroExcepcion{
		PerFactura perFactura = new PerFactura();
		List<DocumentoFiscal> facturas = perFactura.getAll();
		//perCliente.cerrar();
		return facturas; 
	}
	
	 
	//para anular una factura no tiene que haber facturas activas :-D todas tienen que esta anulada 
	public void anular(Recibo recibo) throws Exception
	{
		//Validar facturas si hay una activa Error :-D
		EstadoDocumentoFiscal activo = perEstadoDocumentoFiscal.getActivo();
		for(ReciboDocumentoFiscal documentoFiscal :recibo.getDocumentosFiscales())
		{
			if(documentoFiscal.getDocumentoFiscal().getEstado().equals(activo))
			{
				 throw new  Exception("Existen Documentos Fiscales Activos Asociados al Recibo ");
			}
		}
		
		//desactivo las forma de pago 
		for(FormaPago item: recibo.getFormaspago())
		{
	    	item.setMonto(new Double(0));
	    	item.setEstado(false);
	    }
		
		recibo.setSaldo(0.0);
		recibo.setMonto(0.0);
		recibo.setAnulado(true);
		
		
		persistenciaRecibo.anular(recibo);
	}
	
	public List<TipoFormaPago> getTiposPago() throws ExcFiltroExcepcion{
		PerTipoFormaPago perFormasPago = new PerTipoFormaPago();
		List<TipoFormaPago> formasPago = perFormasPago.getAll();
		//perFormasPago.cerrar();
		return formasPago;
	}
	
	public void guardar(Recibo recibo) throws Exception{
		recibo.setMonto(Real.redondeoMoneda(recibo.getMonto()));
		persistenciaRecibo.guardar(recibo,recibo.getId(), getControlSede());
	}
	
	public void eliminar(){
	}
	public List<Recibo> getTodos() {
		try {
			recibos= persistenciaRecibo.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recibos;
	}
	
	public void setRecibos() {
		try {
			recibos = persistenciaRecibo.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PerRecibo getPersistenciaRecibo() {
		return persistenciaRecibo;
	}

	public void setPersistenciaRecibo(PerRecibo persistenciaRecibo) {
		this.persistenciaRecibo = persistenciaRecibo;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}


	public double getTotal() {
		double monto = 0;
		for (FormaPago item :recibo.getFormaspago())
			monto += item.getMonto();
		return monto;
	}
	
	public List<DocumentoFiscal> getDocumentosConSaldo(){
		return new PerFactura().getDocumentoConSaldo();
	}
	
	public List<Contrato> getContratos(){
		return new PerContrato().getContratosParaAbono();
	}
	
	
	public List<CuentaBancaria> getCuentasBancarias() throws ExcFiltroExcepcion{
		return new PerCuentaBancaria().getAll();
	}
	
	public List<ConceptoPago> getConceptosPago() throws ExcFiltroExcepcion{
		return new PerConceptoPago().getAll();
	}
	
	public Recibo  ReciboNuevo() throws ExcDatosNoValido{
		recibo = new Recibo();
		try{
			ControlSede control = getControlSede();
			recibo.setFecha(control.getFechaCierreFactura());
			recibo.setSede(control.getSede());
		}catch (Exception e){
			throw new ExcDatosNoValido("No existen datos para las sede");
		}
		return recibo;
	}
	
	
	public List<Recibo> getRecibos(Date fecha){
		return getPersistenciaRecibo().getTodos(fecha);
	}
	
	public List<Recibo> getRecibos(Date inicio,Date fin){
		return getPersistenciaRecibo().getTodos(inicio,fin);
	}
	
	
	
	public Banco  getBancoNulo() throws ExcDatosNoValido{
		return new PerBanco().buscarId(new BancoPK("---"));
	}
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	
	public   List<Recibo> getRecibos(Cliente cliente)
	{
		return persistenciaRecibo.getall(cliente);
	}
	
	public   List<Recibo> getRecibos(DocumentoFiscal factura)
	{
		return persistenciaRecibo.getall(factura);
	}
	
	
	public List<Recibo> getTodosProject() {
		try {
			recibos= persistenciaRecibo.getAllproject();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recibos;
	}
	
	public Recibo getReciboProject(Recibo recibo){ 
		recibo= persistenciaRecibo.getRecibo(recibo);
		return recibo;
	}

	public PerNotaCredito getPerNotaCredito() {
		return perNotaCredito;
	}

	public void setPerNotaCredito(PerNotaCredito perNotaCredito) {
		this.perNotaCredito = perNotaCredito;
	}

	public PerNotaDebito getPerNotaDebito() {
		return perNotaDebito;
	}

	public void setPerNotaDebito(PerNotaDebito perNotaDebito) {
		this.perNotaDebito = perNotaDebito;
	}

	public PerFactura getPerFactura() {
		return perFactura;
	}

	public void setPerFactura(PerFactura perFactura) {
		this.perFactura = perFactura;
	}

	public List<Recibo> getRecibos(Integer mes, Integer year) {
		// TODO Auto-generated method stub
		return persistenciaRecibo.getAll(mes,year);
	}
	
	
}

