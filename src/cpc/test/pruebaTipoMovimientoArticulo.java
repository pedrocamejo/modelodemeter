package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.Consumible;
import cpc.modelo.demeter.mantenimiento.TipoMovimientoArticulo;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.persistencia.DaoGenerico;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaTipoMovimientoArticulo {
	public static void main (String[] args) throws ExcFiltroExcepcion{
		DaoGenerico<TipoAlmacen, Integer> as = new DaoGenerico<TipoAlmacen, Integer>(TipoAlmacen.class);
	List<TipoAlmacen> a = as.getAll();
		System.out.println("OK");
		
	}
}
