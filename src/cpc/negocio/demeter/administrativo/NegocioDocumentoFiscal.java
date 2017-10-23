package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.demeter.implementacion.administrativo.PerDocumentoFiscal;

public class NegocioDocumentoFiscal implements  Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2796830989155200014L;
	private static NegocioDocumentoFiscal 		negocio;
	private PerDocumentoFiscal					perDocumentoFiscal;
	
	
	private NegocioDocumentoFiscal(){

		setPerDocumentoFiscal(new PerDocumentoFiscal()); 
	}

	public  static synchronized NegocioDocumentoFiscal getInstance() {
		if (negocio == null)
			negocio = new NegocioDocumentoFiscal();
		return negocio;
	}

	public PerDocumentoFiscal getPerDocumentoFiscal() {
		return perDocumentoFiscal;
	}

	public void setPerDocumentoFiscal(PerDocumentoFiscal perDocumentoFiscal) {
		this.perDocumentoFiscal = perDocumentoFiscal;
	}

	public List<DocumentoFiscal> getDocumentosFiscales(Cliente cliente)
	{
		return perDocumentoFiscal.getAll(cliente);
	}
	
	public void pagarDocumentoFiscal(DocumentoFiscal  documentoFiscal) throws Exception
	{
		Double montoPagado = new Double(0);
		for(ReciboDocumentoFiscal recibo : documentoFiscal.getRecibos())
		{
			recibo.setDocumentoFiscal(documentoFiscal);
		}
		
		if(documentoFiscal.getMontoSaldo() <= 0)
		{
			documentoFiscal.setCancelada(true);
		}
		perDocumentoFiscal.pagar(documentoFiscal);	
	}
	
}
