package cpc.tester.ministerio;

import java.util.List;

import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;





public class pruebaFactura {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */
	public static void main(String[] args) throws ExcFiltroExcepcion {
		NegocioFactura negocio = NegocioFactura.getInstance();
		try{
			List<Cliente> vias = negocio.getClientes();
			for (Cliente tipoVialidad : vias) {
				System.out.println(tipoVialidad.getNombres());
			} 
			List<DocumentoFiscal> fac = negocio.getAllActivas();
			for (DocumentoFiscal tipoVialidad : fac) {
				System.out.println(tipoVialidad.getNroControl()+" "+tipoVialidad.getNroControl());
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//NegocioRecibo negocio2 = NegocioRecibo.getInstance();
	}


	
}
