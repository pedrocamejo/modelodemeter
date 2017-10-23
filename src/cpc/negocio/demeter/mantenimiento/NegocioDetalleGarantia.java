package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.mantenimiento.DetalleGarantina;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerDetalleGarantia;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioDetalleGarantia implements Serializable {
	
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioDetalleGarantia 	negocio;
	
	private PerDetalleGarantia 				perDetalleGarantia;
	
	public NegocioDetalleGarantia() {
		// TODO Auto-generated constructor stub
    	perDetalleGarantia = new PerDetalleGarantia();
    }
	

	public  static synchronized NegocioDetalleGarantia getInstance() {
		if (negocio == null)
			negocio = new NegocioDetalleGarantia();
		return negocio;
	}

	public List<DetalleGarantina> all() throws ExcFiltroExcepcion
	{
		return perDetalleGarantia.getAll();
	}

}
