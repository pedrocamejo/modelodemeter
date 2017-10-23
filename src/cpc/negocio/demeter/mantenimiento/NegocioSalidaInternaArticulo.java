package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;


import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.DetalleEntradaArticulo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaInternaArticulo;
import cpc.modelo.demeter.mantenimiento.EntradaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaInternaArticulo;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumible;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumibleEquivalente;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEntradaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSalidaInternaArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioSalidaInternaArticulo {
	private static NegocioSalidaInternaArticulo negocio;
	private PerSalidaInternaArticulo peristencia;
	private List<DetalleSalidaInternaArticulo> detalleSalidaInternaArticulos;
    private SalidaInternaArticulo salidaInternaArticulo;

	private NegocioSalidaInternaArticulo() {
		/*
		 * SessionDao dao = SessionDao.getInstance(); dao.test();
		 * dao.newDaoGenerico(new PerFactura());
		 */
		peristencia = new PerSalidaInternaArticulo();
	}

	public static NegocioSalidaInternaArticulo getInstance() {
		if (negocio == null)
			negocio = new NegocioSalidaInternaArticulo();
		return negocio;

	}

	public PerSalidaInternaArticulo getPersistencia() {
		return peristencia;
	}

	public void setPersistencia(
			PerSalidaInternaArticulo perSalidaInternaArticulo) {
		this.peristencia = perSalidaInternaArticulo;
	}

	public List<SalidaInternaArticulo> getTodos() throws ExcFiltroExcepcion {
		return peristencia.getAll();
	}

	public List<DetalleSalidaInternaArticulo> getDetalleEntradaArticulos() {
		return detalleSalidaInternaArticulos;
	}

	public void setDetalleSalidaInternaArticulos(
			List<DetalleSalidaInternaArticulo> detalleSalidaInternaArticulos) {
		this.detalleSalidaInternaArticulos = detalleSalidaInternaArticulos;
	}

	public SalidaInternaArticulo getSalidaInternaArticulo() {
		return salidaInternaArticulo;
	}

	public void setSalidaInternaArticulo(SalidaInternaArticulo salidaInternaArticulo ) {
		this.salidaInternaArticulo = salidaInternaArticulo;
	}
	
	public List<ArticuloVenta> getArticulosVentas() throws ExcFiltroExcepcion{
		return new PerArticuloVenta().getAll() ;
	}
	
	
	public List<Almacen> getAlmacenesSalidaInterna() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAlmacenesSalidaInterna();
	}
	public List<Almacen> getAlmacenesDestino() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAll();
	}
 
	
	public void guardar(SalidaInternaArticulo salidaInternaArticulo) throws Exception{
	
		salidaInternaArticulo.setTipoMovimiento(new PerTipoMovientoArticulo().getSalidaInterna());
		
		for (DetalleSalidaInternaArticulo detalleSalidaInternaArticulo2 : salidaInternaArticulo.getDetalleSalidaInternaArticulos()) {
				detalleSalidaInternaArticulo2.setMovimiento(salidaInternaArticulo);
			}
			  salidaInternaArticulo.setSede(getControlSede().getSede());   
		getPersistencia().guardarSalidaInterna(salidaInternaArticulo,salidaInternaArticulo.getId(),getControlSede());
	}
	
	public boolean ValidarExistencia(ArticuloVenta articuloVenta, Almacen almacenOrigen, Double cantidad){
		 ArticuloAlmacenCantidad existencia = new PerArticuloAlmacenCantidad().VerificarExistencia(articuloVenta,almacenOrigen );
		 if (existencia==null)
			 return false;
		 if (existencia.getCantidad()<cantidad)
			 return false;
		 
		 else return true;
	}
	
	public List<Trabajador> getTrabajadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getAll();
	}
	
	public List<Activo> getActivos() throws ExcFiltroExcepcion {
		return new PerActivo().getTodosConAlmacen();
	}
	
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
}