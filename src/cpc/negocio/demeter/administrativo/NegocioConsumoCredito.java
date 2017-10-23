package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ConsumoCredito;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.administrativo.NotaDebito;
import cpc.modelo.demeter.basico.TipoConsumo;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerTipoConsumo;
import cpc.persistencia.demeter.implementacion.administrativo.PerConsumoCredito;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaCredito;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaDebito;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioConsumoCredito implements Serializable{
	
	
	private static final long 			serialVersionUID = 6468752439320318542L;
	private static NegocioConsumoCredito negocio;
	private PerConsumoCredito			persistenciaConsumo;
	private ConsumoCredito				consumoCredito;
	private List<ConsumoCredito>		consumosCreditos;
	
	private NegocioConsumoCredito(){
		persistenciaConsumo = new PerConsumoCredito(); 
	}

	public  static synchronized NegocioConsumoCredito getInstance() {
		if (negocio == null)
			negocio = new NegocioConsumoCredito();
		return negocio;
	}
	
	public ConsumoCredito getConsumoCredito() {
		return consumoCredito;
	}
	public void setConsumoCredito(ConsumoCredito consumo) {
			this.consumoCredito = consumo;
	}
	
	public List<DocumentoFiscal> getFacturas() throws ExcFiltroExcepcion{
		PerFactura perFactura = new PerFactura();
		List<DocumentoFiscal> facturas = perFactura.getAll();
		//perCliente.cerrar();
		return facturas; 
	}
	
	/*public void anular(ConsumoCredito debito){
		persistenciaConsumo.anular(debito);
	}*/
	
	
	public void guardar(ConsumoCredito debitoInterno) throws Exception{
		
		persistenciaConsumo.guardar(debitoInterno, debitoInterno.getId(), getControlSede());
	}
	
	public void anular(ConsumoCredito debitoInterno) throws Exception{
		
		persistenciaConsumo.anular(debitoInterno);
	}
	
	public List<NotaCredito> getNotasSaldo() throws ExcFiltroExcepcion{
		return new PerNotaCredito().getNotasConSaldo();
	}
	
	public List<DocumentoFiscal> getFacturasConSaldo(){
		return new PerFactura().getDocumentoConSaldo();
	}
	
	public List<NotaDebito> getNotasDebitosConSaldo(Cliente cliente){
		return new PerNotaDebito().getNotasSaldos(cliente);
	}
	
	
	public List<TipoConsumo> getTiposConsumo() throws ExcFiltroExcepcion{
		return new PerTipoConsumo().getAll();
	}
	
	public List<ConsumoCredito> getTodos() {
		try {
			consumosCreditos= persistenciaConsumo.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return consumosCreditos;
	}
	public void setRecibos() {
		try {
			consumosCreditos = persistenciaConsumo.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PerConsumoCredito getPersistenciaRecibo() {
		return persistenciaConsumo;
	}

	public void setPersistenciaRecibo(PerConsumoCredito persistenciaRecibo) {
		this.persistenciaConsumo = persistenciaRecibo;
	}

	public List<ConsumoCredito> getRecibos() {
		return consumosCreditos;
	}

	public List<DocumentoFiscal> getDocumentosConSaldo(){
		return new PerFactura().getDocumentoConSaldo();
	}
	
	public ConsumoCredito  DebitoNuevo() throws ExcDatosNoValido{
		consumoCredito = new ConsumoCredito();
		try{
			ControlSede sede = getControlSede();
			consumoCredito.setFecha(sede.getFechaCierreFactura());
			consumoCredito.setSede(sede.getSede());
		}catch (Exception e){
			throw new ExcDatosNoValido("No existen datos para las sede");
		}
		return consumoCredito;
	}
	
	public List<DocumentoFiscal>  facturasConSaldo(Cliente cliente) {
		return new PerFactura().getFacturaSaldo(cliente);
	}
	
	
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
}
