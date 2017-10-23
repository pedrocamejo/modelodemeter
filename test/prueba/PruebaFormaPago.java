package prueba;

import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoCheque;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PruebaFormaPago {

	
	
	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		FormaPago formaPago = new FormaPago();
		
		FormaPagoCheque fpache = new FormaPagoCheque();
		
		System.out.println(fpache.getClass());
		
		System.out.println( ( (FormaPago)fpache).getId());
		
		
	
	}
}
