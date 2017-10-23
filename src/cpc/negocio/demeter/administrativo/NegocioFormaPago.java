package cpc.negocio.demeter.administrativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.Cheque;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoCheque;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.FormaPagoPunto;
import cpc.modelo.demeter.administrativo.FormaPagoRetencion;
import cpc.modelo.demeter.administrativo.FormaPagoTransferencia;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerBanco;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoFormaPago;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioFormaPago  extends NegocioGenerico<FormaPago, PerFormaPago, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioFormaPago 		negocio;
	
	private PerTipoFormaPago 				perTipoFormaPago;
	private PerFormaPago					perFormaPago;
	
	
	private NegocioFormaPago(){
		
		perTipoFormaPago  = new PerTipoFormaPago();
		perFormaPago = new  PerFormaPago();
		setPersistencia(new PerFormaPago());
	}
	
	public  static synchronized NegocioFormaPago getInstance() {
		if (negocio == null)
			negocio = new NegocioFormaPago();
		return negocio;
	}
	
	 
	public List<FormaPago> getTodos() {
		List<FormaPago> controlSede = null;
		try {
			controlSede = getPersistencia().getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controlSede;
		 
	}

	
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
		
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	/*
	public void validar_deposito() throws ExcDatosNoValido{
		getPersistencia().validar_Cuenta(getModelo());
	}
	 */
	public List<Banco> getBancos(){
		return new PerBanco().getSoloBancos();
	}
	
	public List<FormaPago> getAllPagos(){
		return getPersistencia().getAllNoEfectivo();
	}
	
	public void setPago(FormaPago pago){
		setModelo(pago);
	}
	
	public CuentaBancaria getCuentaValida(FormaPago pago) throws ExcEntradaInconsistente {
		return getPersistencia().getCuentaValida((FormaPagoDeposito) pago);
	}

	public List<FormaPago> getAllPagos(Date  inicio,Date fin){
		return getPersistencia().getAllFecha(inicio,fin);
	}
	
	public List<FormaPago> getformaPago(Recibo recibo)
	{
		return getPersistencia().getformaPago(recibo);
	}
	
	public Boolean getformaPagoUsadaTranferencia(String documento)
	{
		return getPersistencia().getDocumentoUsadoTransferencia(documento);
	}
	
	public Boolean getformaPagoUsada(String documento)
	{
		return getPersistencia().getDocumentoUsado(documento);
	}
	
	public TipoFormaPago getTipoFormaDeposito()
	{
		return  perTipoFormaPago.buscarId(3);
	}
	
	public TipoFormaPago getTipoFormaCheque()
	{
		return  perTipoFormaPago.buscarId(2);
	}

	public TipoFormaPago getTipoFormaEfectivo()
	{
		return  perTipoFormaPago.buscarId(1);
	}
	
	public TipoFormaPago getTipoFormaTransferencia()
	{
		return  perTipoFormaPago.buscarId(8);
	}

	public TipoFormaPago getTipoFormaRetencionIVA()
	{
		return  perTipoFormaPago.buscarId(5);
	}
	
	public TipoFormaPago getTipoFormaRetencionISLR()
	{
		return  perTipoFormaPago.buscarId(6);
	}
	
	
	public TipoFormaPago getTipoFormaRetencion1x100()
	{
		return  perTipoFormaPago.buscarId(7);
	}
	
	
	public TipoFormaPago getTipoFormaPunto()
	{
		return  perTipoFormaPago.buscarId(9);
	}
	
	
	public List getRecibosFormaPagoNoDepositado()
	{
		List lista = new ArrayList();
		lista.addAll(perTipoFormaPago.getRecibosFormaPagoCheque());
		lista.addAll(perTipoFormaPago.getRecibosFormaPagoEfectivo());
		return lista;
	}

	public	List<FormaPagoRetencion> getRetencionesreporte(Date desde , Date hasta) {
		// TODO Auto-generated method stub
		return perFormaPago.getRetencionesreporte(desde,hasta);
	}
	
	
	public	List<FormaPagoRetencion> getRetencionesreporte( ) {
		// TODO Auto-generated method stub
		return perFormaPago.getRetencionesreporte();
	}
	
	
	
	public List<FormaPagoCheque> getFormadePagosTransaccionesBancarias(Integer mes, Integer ano)
	{
		//cheques depositos transferencias 
		List<FormaPagoCheque> cheques = perFormaPago.getAllCheques(mes,ano);
		
		return cheques;
	}

	public void xmlFormaPago(Integer mes, Integer ano)
	{
		Element xmlDemeter = new Element("demeter");
		Document documento = new Document(xmlDemeter);
		
		ControlSede sede = getControlSede();
		xmlDemeter.setAttribute("id",sede.getId().toString());
		xmlDemeter.setAttribute("sede",sede.getSede().getId().getId());
		xmlDemeter.setAttribute("codempresa",sede.getSede().getId().getCodigoEmpresa());
		xmlDemeter.setAttribute("mes",mes.toString());
		xmlDemeter.setAttribute("ano",ano.toString());
		
		//Datos de las Formas de Pago 
		Element formaPagos = perFormaPago.getFormaPagosXml(mes,ano);
		xmlDemeter.addContent(formaPagos);
		XMLOutputter xmxl = new XMLOutputter();
		System.out.println(xmxl.outputString(documento));

	}

	public List<HashMap<String, Object>> getChequesRecibos(){
		List<HashMap<String, Object>> lista = new PerFormaPago().getAllChequesRecibos();
		return lista;
		
	}
	
	 
	public List<FormaPagoDeposito> getDepocitosEnRecibos(Date inicio,Date fin)
	{
		return perFormaPago.getDepocitosEnRecibos(inicio, fin);
	}
	
	public FormaPagoPunto getFormaPagoPuntoActiva(String nroTajera, String documento )
	{
		return perFormaPago.getFormaPagoPuntoActiva(nroTajera,documento);
	}

    public Object[] getReporteFormasPago(Date inicio, Date fin){
    	Object[] formasdepago = new Object[6];
    	formasdepago[0] =perFormaPago.getChequesEnRecibos(inicio, fin);
    	formasdepago[1] =perFormaPago.getDepocitosEnRecibos(inicio, fin);
    	formasdepago[2] =perFormaPago.getTransferenciasEnRecibos(inicio, fin);
    	formasdepago[3] =perFormaPago.getRetencionesEnRecibos(inicio, fin);
    	formasdepago[4] =perFormaPago.getPuntosEnRecibos(inicio, fin);
    	formasdepago[5] =perFormaPago.getEfectivoEnRecibos(inicio, fin);
		return formasdepago;
    	
    }

}
