package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DebitoInterno;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerDebitoInterno;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioDebitoInterno implements Serializable{
	
	/**
	 * 
	 */
	private static final long 			serialVersionUID = 6468752439320318542L;
	private static NegocioDebitoInterno negocio;
	private PerDebitoInterno			persistenciaDebito;
	private DebitoInterno				debitoInterno;
	private List<DebitoInterno>			debitosInternos;
	
	private NegocioDebitoInterno(){
		persistenciaDebito = new PerDebitoInterno(); 
	}

	public  static synchronized NegocioDebitoInterno getInstance() {
		if (negocio == null)
			negocio = new NegocioDebitoInterno();
		return negocio;
	}
	
	public DebitoInterno getDebitoInterno() {
		return debitoInterno;
	}
	public void setDebitoInterno(DebitoInterno recibo) {
			this.debitoInterno = recibo;
	}
	
	public List<DocumentoFiscal> getFacturas() throws ExcFiltroExcepcion{
		PerFactura perFactura = new PerFactura();
		List<DocumentoFiscal> facturas = perFactura.getAll();
		//perCliente.cerrar();
		return facturas; 
	}
	
	public void anular(DebitoInterno debito){
		persistenciaDebito.anular(debito);
	}
	
	
	public void guardar(DebitoInterno debitoInterno) throws Exception{
		persistenciaDebito.guardar(debitoInterno, debitoInterno.getId(), getControlSede());
	}
	
	public void eliminar(){

	}
	
	public List<DebitoInterno> getTodos() {
		try {
			debitosInternos= persistenciaDebito.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return debitosInternos;
	}

	public void setRecibos() {
		try {
			debitosInternos = persistenciaDebito.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PerDebitoInterno getPersistenciaRecibo() {
		return persistenciaDebito;
	}

	public void setPersistenciaRecibo(PerDebitoInterno persistenciaRecibo) {
		this.persistenciaDebito = persistenciaRecibo;
	}

	public List<DebitoInterno> getRecibos() {
		return debitosInternos;
	}


	
	public List<DocumentoFiscal> getDocumentosConSaldo(){
		return new PerFactura().getDocumentoConSaldo();
	}
	
	public DebitoInterno  DebitoNuevo() throws ExcDatosNoValido{
		debitoInterno = new DebitoInterno();
		
		try{
			ControlSede sede = getControlSede();
			debitoInterno.setFecha(sede.getFechaCierreFactura());
			debitoInterno.setSede(sede.getSede());
		}catch (Exception e){
			throw new ExcDatosNoValido("No existen datos para las sede");
		}
		return debitoInterno;
	}
	
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	
	
}
