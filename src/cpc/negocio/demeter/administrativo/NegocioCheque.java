package cpc.negocio.demeter.administrativo;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;


import cpc.modelo.demeter.administrativo.Cheque;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.sigesp.basico.Banco;
import cpc.persistencia.demeter.implementacion.PerBanco;
import cpc.persistencia.demeter.implementacion.administrativo.PerCheque; 
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFormaPago;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaDebito;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCheque {

	
	private static NegocioCheque negocio;
	private PerCheque        perCheque;
	private PerFactura       perFactura;
	private PerNotaDebito    perNota;
	private PerRecibo		 perRecibo;
	private PerFormaPago	perFormaPago;
	private PerBanco		perBanco;

	
	private NegocioCheque(){
        perCheque = new PerCheque();
        perFactura = new PerFactura();
		perRecibo = new PerRecibo();
		perFormaPago = new PerFormaPago();
		perBanco = new PerBanco();
	}

	public  static synchronized NegocioCheque getInstance() {
		if (negocio == null)
			negocio = new NegocioCheque();
		return negocio;
	}
	
	public List<DocumentoFiscal> facturas() throws ExcFiltroExcepcion
	{
		return perFactura.getAll();
	}

	public void Guardar(Cheque cheque) throws Exception
	{
		
		perCheque.guardar(cheque,cheque.getId());
	}
	
	
	public void eliminar(Cheque cheque) throws Exception
	{
		perCheque.borrar(cheque);
	}
	
	public List<Cheque> getcheques() throws ExcFiltroExcepcion
	{
		return perCheque.getAll();
	}
	
	public List<Banco> getBancos() throws ExcFiltroExcepcion
	{
		return perBanco.getAll();
	}
	
	public Boolean getnroChequeUsado(String nroCheque,String nroCuenta) throws ExcFiltroExcepcion
	{
		return perCheque.getnroChequeUsado(nroCheque,nroCuenta);
	}
	
	
	public List<Recibo> getRecibos(Cheque cheque)
	{
		return perRecibo.getAll(cheque);
	}
	
	public List<Cheque> getchequesNoUsados() throws ExcFiltroExcepcion
	{
		return perCheque.getChequesNoUsados();
	}
	
}
