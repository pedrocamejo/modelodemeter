package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;


import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEntradaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioEntradaArticulo {
	private static NegocioEntradaArticulo negocio;
	private PerEntradaArticulo peristencia;
	private List<DetalleEntradaArticulo> detalleEntradaArticulos;
    private EntradaArticulo entradaArticulo;

	private NegocioEntradaArticulo() {
		/*
		 * SessionDao dao = SessionDao.getInstance(); dao.test();
		 * dao.newDaoGenerico(new PerFactura());
		 */
		peristencia = new PerEntradaArticulo();
	}

	public static NegocioEntradaArticulo getInstance() {
		if (negocio == null)
			negocio = new NegocioEntradaArticulo();
		return negocio;

	}

	public PerEntradaArticulo getPersistencia() {
		return peristencia;
	}

	public void setPersistencia(
			PerEntradaArticulo perEntradaArticulo) {
		this.peristencia = perEntradaArticulo;
	}

	public List<EntradaArticulo> getTodos() throws ExcFiltroExcepcion {
		return peristencia.getAll();
	}
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public List<DetalleEntradaArticulo> getDetalleEntradaArticulos() {
		return detalleEntradaArticulos;
	}

	public void setDetalleEntradaArticulos(
			List<DetalleEntradaArticulo> detalleEntradaArticulos) {
		this.detalleEntradaArticulos = detalleEntradaArticulos;
	}

	public EntradaArticulo getEntradaArticulo() {
		return entradaArticulo;
	}

	public void setEntradaArticulo(EntradaArticulo entradaArticulo) {
		this.entradaArticulo = entradaArticulo;
	}
	
	public List<ArticuloVenta> getArticulosVentas() throws ExcFiltroExcepcion{
		return new PerArticuloVenta().getAll() ;
	}
	
	
	public List<Almacen> getAlmacenesOrigen() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesServicioTecnico();
	}
	public List<Almacen> getAlmacenesDestino() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesServicioTecnico();
	}
 
	
	public void guardar(EntradaArticulo entradaArticulo) throws Exception{
		entradaArticulo.setTipoMovimiento(new PerTipoMovientoArticulo().getEntradaArticulo());
		
		for (DetalleEntradaArticulo detalleEntradaArticulo2 : entradaArticulo.getDetalleEntradaArticulos()) {
				detalleEntradaArticulo2.setMovimiento(entradaArticulo);
			}
			 entradaArticulo.setSede(getControlSede().getSede());    
		getPersistencia().guardarEntrada(entradaArticulo, entradaArticulo.getId(),getControlSede());
	}
	
	
	
	
}