package cpc.tester.ministerio;

import java.util.List;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.basico.TipoVialidad;
import cpc.negocio.ministerio.basico.NegocioTipoVialidad;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;





public class pruebaTipoVialidad {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */
	public static void main(String[] args) throws ExcFiltroExcepcion {
	//List<IProducto> labores = new PerLabor().getAllLaborACTIVAS(new PerTipoServicio().getTipoTransporte());
		List<Labor> labores = new PerLabor().getAll(new PerTipoServicio().getTipoTransporte());
	
	
System.out.println(labores.size());
		/*NegocioTipoVialidad negocio = NegocioTipoVialidad.getInstance();
		try{
			List<TipoVialidad> vias = negocio.getTodos();
			for (TipoVialidad tipoVialidad : vias) {
				System.out.println(tipoVialidad.getDenotacion());
			} 
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//NegocioRecibo negocio2 = NegocioRecibo.getInstance();*/
	}


	
}
