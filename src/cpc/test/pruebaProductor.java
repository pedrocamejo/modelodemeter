package cpc.test;

import java.util.ArrayList;
import java.util.List;

import cpc.modelo.demeter.basico.CodigoArea;
import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.negocio.ministerio.basico.NegocioProductor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaProductor {
	
	public static void main (String[] args) throws ExcFiltroExcepcion{
		NegocioProductor negocio = NegocioProductor.getInstance();
		
		for (Productor prod: negocio.getTodos()){
			System.out.println(prod.getId() + " - " + prod);
		}
		
		Productor prod = negocio.buscarId(252);
		List<Telefono> telefonos = new ArrayList<Telefono>();
		telefonos.add(new Telefono(new CodigoArea(1,1,"0900",""), "0000001"));
		//telefonos.add(new Telefono(new CodigoArea(1,1,"0700",""), "0000002"));
		
		prod.setTelefonos(telefonos);
		
		negocio.setModelo(prod);
		try {
			negocio.guardar(prod.getId());
		} catch (Exception e) {					
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
	}

}
