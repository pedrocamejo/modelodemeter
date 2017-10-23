package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.mantenimiento.Lote; 
import cpc.persistencia.demeter.implementacion.mantenimiento.PerLote; 
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTipoServicio implements Serializable {

	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioTipoServicio 	negocio;
 
 

	public  static synchronized NegocioTipoServicio getInstance() {
		if (negocio == null)
			negocio = new NegocioTipoServicio();
		return negocio;
	}
	
	   
}
