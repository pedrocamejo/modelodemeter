package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.negocio.demeter.mantenimiento.NegocioMaquinaria;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class pruebaMaquinaria {

	/**
	 * @param args
	 * @throws ExcFiltroExcepcion 
	 */

	public static void main(String[] args) throws ExcFiltroExcepcion {

		try {
			
		NegocioMaquinaria negocio = NegocioMaquinaria.getInstance();	
		
	try {
		
		
		List<Maquinaria> ordenes = negocio.getTodos();
		for (Maquinaria orden : ordenes) {
			System.out.println("Id: "+orden.getId()
							+", Categoria: "+orden.getTipo().getCategoria().getDescripcionCategoria()
							+", Tipo: "+orden.getTipo().getDescripcionTipo()
							   );
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	} catch(Exception e){}
}
}