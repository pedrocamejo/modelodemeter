package cpc.test;


import cpc.modelo.demeter.administrativo.LibroVentaDetalle;
import cpc.negocio.demeter.administrativo.NegocioLibroVenta;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;





public class pruebaLibroVenta {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */
	public static void main(String[] args) throws ExcFiltroExcepcion {
		try{
			NegocioLibroVenta negocio = NegocioLibroVenta.getInstance();
			negocio.nuevoLibro(2010, 4);
		
			for(LibroVentaDetalle dicu: negocio.getLibro().getDetalles()){
				System.out.println("factura "+dicu.getId()+"  "+dicu.getDocumento().getStrNroDocumento());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	


	
}
