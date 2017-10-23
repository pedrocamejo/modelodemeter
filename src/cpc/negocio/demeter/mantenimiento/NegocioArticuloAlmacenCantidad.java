package cpc.negocio.demeter.mantenimiento;

import java.util.List;


import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioArticuloAlmacenCantidad {
	private static NegocioArticuloAlmacenCantidad negocio;
	private PerArticuloAlmacenCantidad perArticuloAlmacenCantidad;


	private NegocioArticuloAlmacenCantidad() {
		/*
		 * SessionDao dao = SessionDao.getInstance(); dao.test();
		 * dao.newDaoGenerico(new PerFactura());
		 */
		perArticuloAlmacenCantidad = new PerArticuloAlmacenCantidad();
	}

	public static NegocioArticuloAlmacenCantidad getInstance() {
		if (negocio == null)
			negocio = new NegocioArticuloAlmacenCantidad();
		return negocio;

	}

	public PerArticuloAlmacenCantidad getPerArticuloAlmacenCantidad() {
		return perArticuloAlmacenCantidad;
	}

	public void setPerArticuloAlmacenCantidad(
			PerArticuloAlmacenCantidad perArticuloAlmacenCantidad) {
		this.perArticuloAlmacenCantidad = perArticuloAlmacenCantidad;
	}

	public List<ArticuloAlmacenCantidad> getTodos() throws ExcFiltroExcepcion {
		return perArticuloAlmacenCantidad.getAll();
	}

}