package cpc.test;


import java.util.Date;
import java.util.List;

import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;





public class pruebaFactura {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */
	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		NegocioFactura persistencia = NegocioFactura.getInstance();
		List<DetalleDocumentoFiscal> dd = NegocioFactura.getInstance().getServicosFacturados(new Date(), new Date());
		dd.size();
		System.out.println(" Cantidad de 89 "+persistencia.getHijosActivos(89));
		System.out.println(" Cantidad de 88 "+persistencia.getHijosActivos(88));
		System.out.println(" Cantidad de 85 "+persistencia.getHijosActivos(85));
		System.out.println(" Cantidad de 84 "+persistencia.getHijosActivos(84));
		List<DocumentoFiscal> documento = persistencia.getAllActivas();
		DocumentoFiscal facturaI = null;
		for (DocumentoFiscal factura : documento) {
			if (factura.getId() == 144){
				facturaI = factura;
				break;
			}
		}
		if (facturaI != null)
			persistencia.setFactura(facturaI);
		for(DetalleDocumentoFiscal detalle: persistencia.getFactura().getDetalles()){
			System.out.printf("%s %.2f %.2f %.2f %s, %s\n",detalle.getServicio().getDescripcion(),detalle.getCantidad(), detalle.getPrecio(), detalle.getAlicuota().getPorcentaje(), detalle.getExcento(), detalle.getDocumento().getBeneficiario().getStrTelefonos());
		}
		
	}


	
}
