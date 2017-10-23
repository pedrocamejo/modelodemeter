package cpc.negocio.demeter.mantenimiento;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.DetalleDevolucionArticulo;
import cpc.modelo.demeter.mantenimiento.DevolucionArticulo;
import cpc.modelo.demeter.mantenimiento.MovimientoArticulo;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerDevolucionArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMovimientoArticulo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoMovientoArticulo;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioDevolucionArticulo {
	private static NegocioDevolucionArticulo negocio;
	private PerDevolucionArticulo peristencia;
	private List<DetalleDevolucionArticulo> detalleDevolucionArticuloArticulos;
    private DevolucionArticulo devolucionArticulo;

	private NegocioDevolucionArticulo() {
		/*
		 * SessionDao dao = SessionDao.getInstance(); dao.test();
		 * dao.newDaoGenerico(new PerFactura());
		 */
		peristencia = new PerDevolucionArticulo();
	}

	public static NegocioDevolucionArticulo getInstance() {
		if (negocio == null)
			negocio = new NegocioDevolucionArticulo();
		return negocio;

	}

	public PerDevolucionArticulo getPersistencia() {
		return peristencia;
	}

	public void setPersistencia(
			PerDevolucionArticulo perDevolucionArticulo) {
		this.peristencia = perDevolucionArticulo;
	}

	public List<DevolucionArticulo> getTodos() throws ExcFiltroExcepcion {
		return peristencia.getAll();
	}

	public List<DetalleDevolucionArticulo> getDetalleDevolucionArticulos() {
		return detalleDevolucionArticuloArticulos;
	}

	public void setDetalleDevolucionArticulos(
			List<DetalleDevolucionArticulo> detalleDevolucionArticulos) {
		this.detalleDevolucionArticuloArticulos = detalleDevolucionArticulos;
	}

	public DevolucionArticulo getDevolucionArticulo() {
		return devolucionArticulo;
	}

	public void setDevolucionArticulo(DevolucionArticulo devolucionArticulo ) {
		this.devolucionArticulo= devolucionArticulo;
	}
	
	public List<ArticuloVenta> getArticulosVentas() throws ExcFiltroExcepcion{
		return new PerArticuloVenta().getAll() ;
	}
	
	
	public List<Almacen> getAlmacenesOrigen() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAll();
	}
	public List<Almacen> getAlmacenesDestino() throws ExcFiltroExcepcion{
		return new PerAlmacen().getAll();
	}
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	public void guardar(DevolucionArticulo devolucionArticulo) throws Exception{
		
		for ( DetalleDevolucionArticulo detalleDevolucionArticulo: devolucionArticulo.getDetalleDevolucionArticulos()) {
				detalleDevolucionArticulo.setMovimiento(devolucionArticulo);
			}
			  devolucionArticulo.setTipoMovimiento(new PerTipoMovientoArticulo().getDevolucion());
			  devolucionArticulo.setSede(getControlSede().getSede()); 
		getPersistencia().guardarDevolucion(devolucionArticulo,devolucionArticulo.getId(),getControlSede());
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
	
	
	public List<ClienteAdministrativo> getDestinatarios() throws ExcFiltroExcepcion{
		return new PerClienteAdministrativo().getcritaeriaAll();
	}
	
	
	public List<MovimientoArticulo> getMovimientoArticulos() throws ExcFiltroExcepcion{
		Criterion salidaInterna = Restrictions.eq("tipoMovimiento", new PerTipoMovientoArticulo().getSalidaInterna());
		Criterion salidaExterna = Restrictions.eq("tipoMovimiento", new PerTipoMovientoArticulo().getSalidaExterna());
		Criterion estado = Restrictions.eq("estado", true);
		List<Criterion> a = Arrays.asList(new  Criterion[] { Restrictions.or(salidaInterna,salidaExterna),estado });
		return new PerMovimientoArticulo().getcritaeriaAll(a);
	}
}