package cpc.test;

import cpc.negocio.demeter.administrativo.NegocioConsumoCredito;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;





public class pruebaConsumoCredito {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */
	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		NegocioConsumoCredito persistencia = NegocioConsumoCredito.getInstance();
		persistencia.getTodos();

		/*persistencia.setFactura(documento);
		documento =persistencia.getFactura();
		for(DetalleDocumentoFiscal detalle: documento.getDetalles()){
			System.out.printf("%s %.2f %.2f %.2f\n",detalle.getServicio().getDescripcion(),detalle.getCantidad(), detalle.getPrecio(), detalle.getAlicuota().getPorcentaje());
		}*/
		
	}


	
}
