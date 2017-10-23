package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.DetalleDeposito;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoCheque;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.FormaPagoEfectivo;
import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.persistencia.demeter.implementacion.PerBanco;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.PerCuentaBancaria;
import cpc.persistencia.demeter.implementacion.administrativo.PerDeposito;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioDeposito implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3718176543691480920L;
	private static NegocioDeposito 		negocio;
	private PerDeposito					persistencia;
	private PerFormaPago 			    perFormaPago;
	private Deposito					deposito;
	
	
	private NegocioDeposito(){

		persistencia = new PerDeposito(); 
		perFormaPago = new PerFormaPago();
	}

	public  static synchronized NegocioDeposito getInstance() {
		if (negocio == null)
			negocio = new NegocioDeposito();
		return negocio;
	}
	
	public void guardar(Deposito deposito, List<FormaPago> formasdepago) throws Exception{
		
		/* cheques Asociados al Deposito */
		
		/* YOO se que esta RARITO pero no Culparme mas bien Disculparme  Att. Rhonal 
		 */
		
		for(FormaPago pago : formasdepago)
		{
			if(pago.getClass().equals(FormaPagoCheque.class))
			{
				DetalleDeposito detalleDeposito = new DetalleDeposito((FormaPagoCheque) pago,deposito);
				deposito.getCheques().add(detalleDeposito);
			}
		}
		persistencia.guardar(deposito,formasdepago);
	}
	
	public void eliminar(Deposito deposito){
		persistencia.borrar(deposito);
	}
	
	public List<Deposito> getTodos() {
		List<Deposito> depositos = null;
		try {
			depositos = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return depositos;
	}
	

	public List<Deposito> getTodos(Date fecha) {
		return persistencia.getDepositos(fecha);
	}

	
	public List<Deposito> getTodos(Date inicio,Date fin) {
		return persistencia.getDepositos(inicio,fin);
	}

	public PerDeposito getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(PerDeposito persistencia) {
		this.persistencia = persistencia;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		if (deposito.getId() != null)
			this.deposito = persistencia.getDatos(deposito);
		else
			this.deposito = deposito;
	}

	public List<CuentaBancaria> getCuentasbancarias() throws ExcFiltroExcepcion{
		return new PerCuentaBancaria().getAll();
	}
	
	public List<FormaPagoCheque> getChequesSinDepositar() throws ExcFiltroExcepcion {
		return perFormaPago.getChequeSinDepositar( );
	}
	
	public List<FormaPagoEfectivo> getEfectivoSinDepositar( ) throws ExcFiltroExcepcion {
	
		return perFormaPago.getEfectivoSinDepositar( );
	}
	
	
	public Deposito buscar(Integer id){
		return persistencia.buscarId(id);
	}
	

	public Deposito buscar(String documento){
		return persistencia.buscar(documento);
	}
	public List<Banco> getbancos() throws ExcFiltroExcepcion{
		return new PerBanco().getAll();
	}
	
	public ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	

	
	public double getEfectivoCierre(Date fecha){
		return perFormaPago.getEfectivoCierre(fecha);
	}
	
	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}

	public Deposito getDepositoValido(Date fecha, FormaPago pago) throws ExcEntradaInconsistente {
		return persistencia.getDepositoValido(fecha, (FormaPagoDeposito) pago);
	}
	
	
	public Deposito enrriquecer(Deposito deposito)
	{
		persistencia.enrriquecer(deposito);
		return deposito;
	}
	
	
}
