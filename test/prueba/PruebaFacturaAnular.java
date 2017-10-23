package prueba;

 

import org.springframework.transaction.support.TransactionSynchronizationManager;

import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.negocio.demeter.administrativo.NegocioDocumentoFiscal;
import cpc.negocio.demeter.administrativo.NegocioFactura;

public class PruebaFacturaAnular {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  
		NegocioFactura   negocio = NegocioFactura.getInstance();
		
		DocumentoFiscal documento = negocio.buscar(6179);
		
		TransactionSynchronizationManager.bindResource("obj", new AuditorUsuario());
		negocio.anular(documento);
		
		
	}

}
