package prueba;

 

import org.springframework.transaction.support.TransactionSynchronizationManager;

import cpc.modelo.demeter.administrativo.Recibo;
import cpc.negocio.demeter.administrativo.NegocioRecibo;

public class PruebaReciboAnular {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		NegocioRecibo negocio = NegocioRecibo.getInstance();
		 
		Recibo recibo = negocio.getPersistenciaRecibo().buscarId(new Long(2555));
		TransactionSynchronizationManager.bindResource("obj", new AuditorUsuario());
		negocio.anular(recibo);
		
		
	}

}
