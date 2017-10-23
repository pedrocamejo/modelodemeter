package cpc.test;


import java.util.ArrayList;
import java.util.List;

import cpc.modelo.ministerio.basico.TipoRiego;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.modelo.ministerio.gestion.UnidadProductivaTipoRiego;
import cpc.negocio.ministerio.basico.NegocioTipoRiego;
import cpc.negocio.ministerio.basico.NegocioUnidadProductiva;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaUnidadProductiva {
	
	public static void main (String[] args) throws ExcFiltroExcepcion{
		NegocioUnidadProductiva negocio = NegocioUnidadProductiva.getInstance();
		NegocioTipoRiego		negocio2 = NegocioTipoRiego.getInstance();
		
		for (TipoRiego tr: negocio2.getTodos()){
			System.out.println("* "+tr.getId() +" -" +tr);
		}
		
		for (UnidadProductiva up : negocio.getTodos()){
			System.out.println("> "+up.getId() +" -" +up.getProductor());
		}
		
		UnidadProductiva up = negocio.buscarId(new Integer(2));
		List<UnidadProductivaTipoRiego> lista = new ArrayList<UnidadProductivaTipoRiego>();
		lista.add(new UnidadProductivaTipoRiego(negocio2.buscarId(7),up,new Double(10)));
		lista.add(new UnidadProductivaTipoRiego(negocio2.buscarId(5),up,new Double(10)));
		lista.add(new UnidadProductivaTipoRiego(negocio2.buscarId(6),up,new Double(10)));
		up.setTiposRiego(lista);
		
		negocio.setModelo(up);
		up = negocio.getTiposRiegoUnidadProductiva(up);
		try {
			negocio.guardar();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

}
