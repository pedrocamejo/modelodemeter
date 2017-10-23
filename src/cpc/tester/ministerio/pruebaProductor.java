package cpc.tester.ministerio;

import java.util.List;

import cpc.modelo.ministerio.gestion.Productor;
import cpc.negocio.ministerio.basico.NegocioProductor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaProductor {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */
	public static void main(String[] args) throws ExcFiltroExcepcion {
		NegocioProductor negocio = NegocioProductor.getInstance();
		try{
			//List<Productor> vias = negocio.getTodos();
			//for (Productor tipoVialidad : vias) {
/*			//	System.out.println(tipoVialidad.getNombres());
			List<Productor> vias = (List<Productor>) negocio.getTodosProductoresproject();
			System.out.println("ssss");
			for (Productor productor : vias) {
			productor.getStrTelefonos();
			}
		*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//NegocioRecibo negocio2 = NegocioRecibo.getInstance();
	}


	
}
