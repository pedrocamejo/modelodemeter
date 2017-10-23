package cpc.negocio.demeter.administrativo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.zkoss.zkplus.spring.SpringUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioAsiento;
import cpc.modelo.demeter.administrativo.CierreDiarioCuentaAdelanto;
import cpc.modelo.demeter.administrativo.CierreDiarioCuentaCobrar;
import cpc.modelo.demeter.administrativo.CierreDiarioCuentaPagar;
import cpc.modelo.demeter.administrativo.CierreDiarioDocumento;
import cpc.modelo.demeter.administrativo.CierreDiarioImpuesto;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ReciboNotaCargo;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cpc.persistencia.demeter.implementacion.administrativo.PerAuxiliarAsientoImpuesto;
import cpc.persistencia.demeter.implementacion.administrativo.PerAuxiliarAsientoIngreso;
import cpc.persistencia.demeter.implementacion.administrativo.PerCierreDiario;
import cpc.persistencia.demeter.implementacion.administrativo.PerDeposito;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;

public class NegocioCierreDiario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3339265526949885509L;
	private static NegocioCierreDiario 	negocio;
	private PerCierreDiario				persistenciaCierre;
	private PerRecibo					perRecibo;
	
	private CierreDiario				cierreDiario;
	private List<CierreDiario>			cierresDiarios;
	
	private NegocioCierreDiario(){
		persistenciaCierre = new PerCierreDiario(); 
		perRecibo = new PerRecibo();
	}

	public  static synchronized NegocioCierreDiario getInstance() {
		if (negocio == null)
			negocio = new NegocioCierreDiario();
		return negocio;
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{
		PerCliente perCliente = new PerCliente();
		List<Cliente> clientes = perCliente.getAdministrativos();
		return clientes; 
	}
	
	public void SetCierre(CierreDiario cierre){
		cierreDiario = getPersistenciaCierre().getDatos(cierre);
	}
	
	public CierreDiario getCierre(Serializable id)
	{
		return persistenciaCierre.buscarId(id);
	}

	
	public void guardar(CierreDiario cierre, SedePK sede) throws Exception{
		persistenciaCierre.guardar(cierre, sede, generarAsientoDiario(cierre), getControlSede());
	}
	
	public List<Labor> getServicios() throws ExcFiltroExcepcion{
		PerLabor perServicio = new PerLabor();
		List<Labor> servicios = perServicio.getAll();
		return servicios; 
	}

	public PerCierreDiario getPersistenciaCierre() {
		return persistenciaCierre;
	}

	public void setPersistenciaCierre(PerCierreDiario persistenciaCierre) {
		this.persistenciaCierre = persistenciaCierre;
	}

	public CierreDiario getCierreDiario() {
		return cierreDiario;
	}


	public List<CierreDiario> getCierresDiarios() {
		return cierresDiarios;
	}

	public void setCierresDiarios(List<CierreDiario> cierresDiarios) {
		this.cierresDiarios = cierresDiarios;
	}
	
	public List<CierreDiario> getTodos() throws ExcFiltroExcepcion{
		return persistenciaCierre.getAll();
	}

	public List<CierreDiarioImpuesto> getImpuestos(Date fecha) {
		return persistenciaCierre.getImpuestosCierre(fecha);
	}
	
	public List<CierreDiarioDocumento> getDocumentos(Date fecha){
		return persistenciaCierre.getFacturasCierre(fecha);
	}
	
	public List<CierreDiarioCuentaPagar> getCuentasPagas(Date fecha){
		List<CierreDiarioCuentaPagar> cuentas = new ArrayList<CierreDiarioCuentaPagar>();
		/*** carga toda las notas de debito y le pone le incremento true ***/
		cuentas.addAll(persistenciaCierre.getCuentasPagar(fecha));
		/** carga todo las nota de spago y le pone false el incremento **/
		cuentas.addAll(persistenciaCierre.getCuentasPagadas(fecha));
		return cuentas;
	}
	
	public List<CierreDiarioCuentaAdelanto> getCuentasAdelantos(Date fecha){
		return  persistenciaCierre.getCuentasAdelantos(fecha);
	}
	
	
	
	
	public List<CierreDiarioCuentaCobrar> getCuentasCobrar(Date fecha){
		List<CierreDiarioCuentaCobrar> cuentas = new ArrayList<CierreDiarioCuentaCobrar>();
		/*carga las nota de debito convertiendolo a el tipo cieerreDiarioCuentaCobrar con el incremento true */
		cuentas.addAll(persistenciaCierre.getCuentasCobrar(fecha));
		/*carga los Recibos convertiendolo a el tipo cieerreDiarioCuentaCobrar con el incremento flase */
		cuentas.addAll(persistenciaCierre.getCuentasCobradas(fecha));
		return cuentas;
	}

	public List<FormaPago> getFormaPago(Date fecha){
		List<FormaPago> salida = new PerFormaPago().getPagosCaja(fecha);
		return salida;
	}
	
	public CierreDiario nuevoCierre()  throws ExcDatosNoValido{
		Date fecha = detertarFecha();
		cierreDiario = new CierreDiario();
		cierreDiario.setDatosSede(getControlSede());
		return nuevoCierre(fecha);
	}
	
	public CierreDiario nuevoCierre(Date fecha)  throws ExcDatosNoValido{
		if (cierreDiario == null)
			cierreDiario = new CierreDiario();
		cierreDiario.setCuentasCobrar(getCuentasCobrar(fecha)); // nota de debito incrementa y recibo no incrementa 
//		cierreDiario.setCuentasPagadas(getCuentasPagas(fecha)); 
		cierreDiario.setCuentasPagadas(getCuentasPagas(fecha)); // nota de debito incrementa y nota de pago no imcrementa
		cierreDiario.setCuentasAdelantos(getCuentasAdelantos(fecha)); // todo los recibos 
		cierreDiario.setDocumentos(getDocumentos(fecha)); // todo los documentos fiscales sin descriminacion 
		cierreDiario.setImpuestos(getImpuestos(fecha));  // todo los impuestos de los documentos fiscales sin discriminar dada la fecha 
		cierreDiario.setFecha(fecha); //fecha XD! 
		
		double monto=0;
		for (CierreDiarioImpuesto cuenta: cierreDiario.getImpuestos()){
			cuenta.setCierreDiario(cierreDiario);
			monto += cuenta.getMonto();
		}
		cierreDiario.setImpuestoFacturado(monto);
		
		monto=0;
		for (CierreDiarioCuentaCobrar cuenta: cierreDiario.getCuentasCobrar()){
			cuenta.setCierreDiario(cierreDiario);
			monto += cuenta.getMonto();
		}
		cierreDiario.setCuentasPorCobrar(monto);
		
		monto=0;
		for (CierreDiarioCuentaPagar cuenta: cierreDiario.getCuentasPagadas()){
			cuenta.setCierreDiario(cierreDiario);
			monto += cuenta.getMonto();
		}
		cierreDiario.setCuentasPorPagar(monto);
		
		@SuppressWarnings("unused")
		double montoImpuesto=0;
		double montoIngreso=0;
		double montoDescuento=0;
		for (CierreDiarioDocumento cuenta: cierreDiario.getDocumentos()){
			cuenta.setCierreDiario(cierreDiario);
			montoImpuesto += cuenta.getDocumento().getTotalImpuesto();
			montoIngreso += cuenta.getDocumento().getMontoBase();
			montoDescuento += cuenta.getDocumento().getMontoDescuento();
		}
		cierreDiario.setIngresoFacturado(montoIngreso);
		cierreDiario.setMontoDescuentos(montoDescuento);
		
		double montoCaja=0;
		monto = 0;
		for (FormaPago cuenta: getFormaPago(fecha)){
			if (cuenta.getTipoFormaPago().isUsaCuenta())
				monto += monto;
			else
				montoCaja += cuenta.getMonto();
		}
		cierreDiario.setMontoRecibos(montoCaja);
		return cierreDiario;
	}
	
	
	public void generarAsientos() throws ExcDatosNoValido{
		List<CierreDiarioAsiento> asientos = new ArrayList<CierreDiarioAsiento>();
		/*
		 * Facturas y notas
		 */
		ControlSede sede  = cierreDiario.getDatosSede();
		CierreDiarioAsiento asiento = new CierreDiarioAsiento();
		asiento.setCuenta(new PerCuentaContable().buscarId(sede.getCuentaContableIngresosSede()));
		asiento.setFecha(cierreDiario.getFecha());
		asiento.setDebe(0);
		asiento.setHaber(cierreDiario.getIngresoFacturado());
		asiento.setCierreDiario(cierreDiario);
		asientos.add(asiento);
		
		asiento = new CierreDiarioAsiento();
		asiento.setCuenta(new PerCuentaContable().buscarId(sede.getCuentaContableImpuestoSede()));
		asiento.setFecha(cierreDiario.getFecha());
		asiento.setDebe(0);
		asiento.setHaber(cierreDiario.getImpuestoFacturado());
		asiento.setCierreDiario(cierreDiario);
		asientos.add(asiento);
		
		asiento = new CierreDiarioAsiento();
		asiento.setCuenta(new PerCuentaContable().buscarId(sede.getCuentaContableDescuentoSede()));
		asiento.setFecha(cierreDiario.getFecha());
		asiento.setDebe(cierreDiario.getMontoDescuentos());
		asiento.setHaber(0);
		asiento.setCierreDiario(cierreDiario);
		asientos.add(asiento);
		
		for (CierreDiarioCuentaCobrar cuenta: cierreDiario.getCuentasCobrar()){
			asiento = new CierreDiarioAsiento();
			asiento.setCuenta(new PerCuentaContable().buscarId(cuenta.getCliente().getCuentaCobro()));
			asiento.setFecha(cierreDiario.getFecha());
			asiento.setDebe(cuenta.getMonto());
			asiento.setHaber(0);
			asiento.setCierreDiario(cierreDiario);
			asientos.add(asiento);
		}

		/*
		 * Pagos 
		 */
		
		asiento = new CierreDiarioAsiento();
		asiento.setCuenta(new PerCuentaContable().buscarId(sede.getCuentaContableCaja()));
		asiento.setFecha(cierreDiario.getFecha());
		asiento.setDebe(cierreDiario.getMontoRecibos());
		asiento.setHaber(0);
		asiento.setCierreDiario(cierreDiario);
		asientos.add(asiento);
		
		/*
		 * Asiento de Bancos
		 */
		
		for (CierreDiarioCuentaPagar cuenta: cierreDiario.getCuentasPagadas()){
			asiento = new CierreDiarioAsiento();
			asiento.setCuenta(new PerCuentaContable().buscarId(cuenta.getCliente().getCuentaCobro()));
			asiento.setFecha(cierreDiario.getFecha());
			asiento.setDebe(0);
			asiento.setHaber(cuenta.getMonto());
			asiento.setCierreDiario(cierreDiario);
			asientos.add(asiento);
		}
		
		/*
		 * Depositos
		 */
		
		asiento = new CierreDiarioAsiento();
		asiento.setCuenta(new PerCuentaContable().buscarId(sede.getCuentaContableCaja()));
		asiento.setFecha(cierreDiario.getFecha());
		asiento.setDebe(cierreDiario.getMontoRecibos());
		asiento.setHaber(0);
		asiento.setCierreDiario(cierreDiario);
		asientos.add(asiento);
		
	}
	
	
	public Date detertarFecha() throws ExcDatosNoValido{
		Fecha fecha;
		ControlSede control = getControlSede();
		if (control.getFechaCierreFactura() == null)
			throw new ExcDatosNoValido("Fecha de cierre no definida");
		fecha = new Fecha(control.getFechaCierreFactura());
		return fecha.getFecha();
	}
	
	
	public Date incrementarFecha() throws ExcDatosNoValido{
		Fecha fecha;
		ControlSede control = getControlSede();
		if (control.getFechaCierreFactura() == null)
			throw new ExcDatosNoValido("Fecha de cierre no definida");
		fecha = new Fecha(control.getFechaCierreFactura());
		fecha.agregarDias(1);
		return fecha.getFecha();
	}
	
	private ControlSede getControlSede(){
		try{
			Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
			return new PerControlSede().buscarId(IdControl);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}				
	
	public void setCierreDiario(CierreDiario cierreD) {
		if (cierreD.getId() != null)
			this.cierreDiario = persistenciaCierre.getDatos(cierreD);
		else
			this.cierreDiario  = cierreD;
	}
	
	public ControlSede actualizarControl() throws ExcFiltroExcepcion{
		return new PerControlSede().getControlSede();
	}
	
	public List<CierreDiarioAsiento> generarAsientoDiario(CierreDiario cierre){
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		ControlSede sede  = cierreDiario.getDatosSede();
		//-------------------------------------Ingresos
		asiento.addAll(new PerAuxiliarAsientoIngreso().getDetalleIngresos(cierre));
		//-------------------------------------impuestos
		asiento.addAll(new PerAuxiliarAsientoImpuesto().getDetalleIngresos(cierre, sede.getCuentaContableImpuestoSede()));
		//--------------------------------Cuentas por Cobrar y Pagar
		asiento.addAll(getPersistenciaCierre().getCuentasxCobrarYPagar(cierre, sede.getCuentaCLienteCobro(), sede.getCuentaCLientePago()));
		//----------------------------------Ingresos a Caja
		asiento.addAll(new PerFormaPago().getDetalleIngresos(cierre, sede.getCuentaContableCaja()));
		//--------------------------------Ingresos a Bancos
		asiento.addAll(new PerDeposito().getDetalleIngresos(cierre));
		asiento.addAll(new PerDeposito().getDetalleIngresos(cierre, sede.getCuentaContableCaja()));
		//***********************Monto Adelantos y Cobro
		asiento.addAll(getPersistenciaCierre().getCuentasCobradasYAdelantos(cierre, sede.getCuentaCLienteCobro(), sede.getCuentaContableAdelanto()));
		asiento.addAll(getPersistenciaCierre().getCuentasCobradasNotas(cierre, sede.getCuentaCLienteCobro()));
		asiento.addAll(getPersistenciaCierre().getCuentasPagadas(cierre, sede.getCuentaCLientePago()));
		
		for (CierreDiarioAsiento item: asiento)
			item.setSede(sede.getSede());
		return asiento;
	}
	
	public List<Deposito> getDepositos(Date fecha){
		return new PerDeposito().getDepositos(fecha);
	}
	
	
	


	public OutputStream cierreDiarioToXml(List<CierreDiario> cierres) throws ParserConfigurationException, TransformerException
	{
		Document dc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root = dc.createElement("cierresDiario");  
		dc.appendChild(root);
		for(CierreDiario cierreDiario : cierres)
		{
			Date fecha = cierreDiario.getFecha();
			Element elementCierre = dc.createElement("cierreDiario");  
			root.appendChild(elementCierre);

			elementCierre.setAttribute("sede-nombre",cierreDiario.getDatosSede().getSede().getNombre());
			elementCierre.setAttribute("sede-id",cierreDiario.getDatosSede().getSede().getId().getId());
			elementCierre.setAttribute("id",cierreDiario.getId().toString());
	//		elementCierre.setAttribute("fecha",Fecha.obtenerFechaAnoMesDia(cierreDiario.getFecha()));
			elementCierre.setAttribute("ingresoFacturado",new Double(cierreDiario.getIngresoFacturado()).toString());
			elementCierre.setAttribute("ImpuestoFacturado",new Double(cierreDiario.getImpuestoFacturado()).toString());
			elementCierre.setAttribute("cuentasPorCobrar",new Double(cierreDiario.getCuentasPorCobrar()).toString());
			elementCierre.setAttribute("montoAdelantos",new Double(cierreDiario.getMontoAdelantos()).toString());
			elementCierre.setAttribute("montoRecibos",new Double(cierreDiario.getMontoRecibos()).toString()); // 199/08/26
			elementCierre.setAttribute("montoDescuentos",new Double(cierreDiario.getMontoDescuentos()).toString());		
			elementCierre.setAttribute("cuentasPorPagar",new Double(cierreDiario.getCuentasPorPagar()).toString());		
			elementCierre.setAttribute("montoDepositado",new Double(cierreDiario.getMontoDepositado()).toString());		
			elementCierre.setAttribute("Observacion",cierreDiario.getObservacion());		

			/* recibos depositados */

		
			List<Recibo> recibos = perRecibo.getAll(cierreDiario.getFecha().getDate(),cierreDiario.getFecha().getMonth()+1,cierreDiario.getFecha().getYear()+1900);
			Element elementRecibos = dc.createElement("Recibos");
			elementCierre.appendChild(elementRecibos);
			for(Recibo recibo:  recibos)
			{
				Element elementRecibo = dc.createElement("Recibo");
				elementRecibo.setAttribute("id",recibo.getId().toString());
		//		elementRecibo.setAttribute("fecha",Fecha.obtenerFechaAnoMesDia(recibo.getFecha()));
				elementRecibo.setAttribute("concepto",recibo.getId().toString());
				elementRecibo.setAttribute("control",recibo.getControl());
				elementRecibo.setAttribute("cliente-identidadLegal",recibo.getCliente().getIdentidadLegal());
				elementRecibo.setAttribute("monto",recibo.getMonto().toString());
				elementRecibo.setAttribute("saldo",recibo.getSaldo().toString());
				elementRecibo.setAttribute("anulado",new Boolean(recibo.isAnulado()).toString());
				elementRecibos.appendChild(elementRecibo);
			}

		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dc);
			
		 
        StreamResult consoleResult =  new StreamResult(new ByteArrayOutputStream());
        transformer.transform(source, consoleResult);
        System.out.println(consoleResult.getOutputStream());

        return consoleResult.getOutputStream();
	}
	
}
