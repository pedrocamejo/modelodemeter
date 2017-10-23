package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleTransferenciaArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.TransferenciaArticulo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEntradaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTransferenciaArticulo;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTransferenciaArticulo {

	private static NegocioTransferenciaArticulo negocio;
	private PerTransferenciaArticulo peristencia;
	private List<DetalleTransferenciaArticulo> detalleTransferenciaArticulos ;
    private TransferenciaArticulo transferenciaArticulo;

	private NegocioTransferenciaArticulo() {
		/*
		 * SessionDao dao = SessionDao.getInstance(); dao.test();
		 * dao.newDaoGenerico(new PerFactura());
		 */
		peristencia = new PerTransferenciaArticulo();
	}

	public static NegocioTransferenciaArticulo getInstance() {
		if (negocio == null)
			negocio = new NegocioTransferenciaArticulo();
		return negocio;

	}

	public PerTransferenciaArticulo getPersistencia() {
		return peristencia;
	}

	public void setPersistencia(
			PerTransferenciaArticulo perTransferenciaArticulo) {
		this.peristencia = perTransferenciaArticulo;
	}

	public List<TransferenciaArticulo> getTodos() throws ExcFiltroExcepcion {
		return peristencia.getAll();
	}

	public List<DetalleTransferenciaArticulo> getDetalleTransferenciaArticulos() {
		return detalleTransferenciaArticulos;
	}

	public void setDetalleTransferenciaArticulos(
			List<DetalleTransferenciaArticulo> detalleTransferenciaArticulos) {
		this.detalleTransferenciaArticulos = detalleTransferenciaArticulos;
	}

	public TransferenciaArticulo getTransferenciaArticulo() {
		return transferenciaArticulo;
	}

	public void setTransferenciaArticulo (TransferenciaArticulo  transferenciaArticulo) {
		this.transferenciaArticulo = transferenciaArticulo;
	}
	
	public List<ArticuloVenta> getArticulosVentaExistentes() throws ExcFiltroExcepcion{
		return new PerArticuloVenta().getArticulosEnExistencia() ;
	}
	
	
	public List<Almacen> getAlmacenesOrigen() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesServicioTecnico();
	}
	public List<Almacen> getAlmacenesDestino() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesServicioTecnico();
	}
	public List<Trabajador> getTrabajadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getAll();
	}
	
	public void guardar(TransferenciaArticulo transferenciaArticulo) throws Exception{
		transferenciaArticulo.setTipoMovimiento(new PerTipoMovientoArticulo().getDevolucion());
		
		for (DetalleTransferenciaArticulo detalleTransferenciaArticulo2 : transferenciaArticulo.getDetalleTransferenciaArticulos()) {
				detalleTransferenciaArticulo2.setMovimiento(transferenciaArticulo);
			}
			   transferenciaArticulo.setSede(getControlSede().getSede()); 
		getPersistencia().guardarTransferencia(transferenciaArticulo, transferenciaArticulo.getId(),getControlSede());
	}
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	public boolean ValidarExistencia(ArticuloVenta articuloVenta, Almacen almacenOrigen, Double cantidad){
		 ArticuloAlmacenCantidad existencia = new PerArticuloAlmacenCantidad().VerificarExistencia(articuloVenta,almacenOrigen );
		 if (existencia==null)
			 return false;
		 if (existencia.getCantidad()<cantidad)
			 return false;
		 
		 else return true;
	}
}
