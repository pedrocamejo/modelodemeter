package cpc.test;

import java.util.List;

import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.basico.CostoBancario;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.negocio.demeter.administrativo.NegocioNotaDebito;
import cpc.negocio.demeter.basico.NegocioCostoBancario;
import cpc.negocio.sigesp.NegocioAlmacen;
import cpc.persistencia.demeter.implementacion.administrativo.PerRecibo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaCostosBancarios {
	public static void main (String[] args) throws ExcFiltroExcepcion{
		NegocioNotaDebito negocio = NegocioNotaDebito.getInstance();
	/* List<CostoBancario> a = negocio.getTodos();
	for (CostoBancario costosBancarios : a) {
		System.out.println(costosBancarios.getDescripcion() );
		System.out.println(costosBancarios.getPrecio() );
		System.out.println(costosBancarios.getIvaProducto() );
		System.out.println(costosBancarios.getImpuesto().getDescripcion() );
	}
		*/
		IProducto a = negocio.getReversoRecibo();
		System.out.println(a.getDescripcion());
		 List<Recibo> recibo = new PerRecibo().getAllActivosFactura(); 
		 System.out.println(recibo.size());
	}
}
