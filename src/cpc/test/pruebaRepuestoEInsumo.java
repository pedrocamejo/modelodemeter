package cpc.test;

import java.util.List;

import cpc.modelo.sigesp.basico.SegmentoCCNU;
import cpc.negocio.demeter.mantenimiento.NegocioRepuestoEInsumo;

public class pruebaRepuestoEInsumo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NegocioRepuestoEInsumo negocio = NegocioRepuestoEInsumo.getInstance();
		try{
			//List<Articulo> articulos = negocio.getArticulos();
			List<SegmentoCCNU> productos = negocio.getSegmentosCCNU();
			System.out.println("**********************Articulos***********************");
			/*for(Articulo art : articulos){
				System.out.println("Articulo :"+art.getDescripcion());
			}*/
			System.out.println("**********************Producto CCNU***********************");
			for(SegmentoCCNU art : productos){
				System.out.println("Producto "+art.getDescripcion());
			}
		}catch (Exception e){
			
		}
		
	}

}
