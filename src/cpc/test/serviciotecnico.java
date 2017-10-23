package cpc.test;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.RepuestoEquivalente;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.persistencia.DaoGenerico;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class serviciotecnico {
	public static void main (String[] args) throws ExcFiltroExcepcion{
		
		DaoGenerico<SolicitudServicioTecnico, Long> as = new DaoGenerico<SolicitudServicioTecnico,Long>(SolicitudServicioTecnico.class);
		 List<SolicitudServicioTecnico> a = as.getAll();
		 SolicitudServicioTecnico d = a.get(0);
	
		System.out.println(a.size());
		
		
	}
	
}
