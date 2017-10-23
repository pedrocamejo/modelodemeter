package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.Reintegro;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerReintegro;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioReintegro implements Serializable {

	private PerReintegro  perReintegro; 
	private PerControlSede perControlSede;
	
	private static NegocioReintegro negocio;
	public NegocioReintegro() {
		super();
		perReintegro = new PerReintegro();
		perControlSede =new PerControlSede();
	}
	

	public  static synchronized NegocioReintegro getInstance() {
		if (negocio == null)
			negocio = new NegocioReintegro();
		return negocio;
	}
	
	
	public List<Reintegro>  getall() throws ExcFiltroExcepcion
	{
		return perReintegro.getAll();
	}
	
	public void  guardar(Reintegro reintegro) throws Exception
	{
		/// primero Dependiendo el tipo se realizan los calculos si es de tipo nota de credito
		
		if (reintegro.getTipo() == Reintegro.REINTEGRO_CREDITO)
		{
			//se consume todo el Saldo de la Nota de Credito 
			NotaCredito notaCredito = reintegro.getNotaCredito();
			notaCredito.setMontoSaldo(0.0);
			perReintegro.guardar(reintegro,reintegro.getId(),getControlSede());
		}
		else if (reintegro.getTipo() == Reintegro.REINTEGRO_RECIBOS) 
		{
			for(Recibo recibo : reintegro.getRecibos())
			{
				recibo.setSaldo(0.0);
			}
			perReintegro.guardar(reintegro,reintegro.getId(),getControlSede());
		}
		
	}
	

	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	
	
	
	
}
