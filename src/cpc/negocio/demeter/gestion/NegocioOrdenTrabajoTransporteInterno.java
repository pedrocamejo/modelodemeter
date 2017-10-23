package cpc.negocio.demeter.gestion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.CotizacionTransporte;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.OrdenTrabajoTransporteInterno;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.demeter.transporte.UbicacionTransporte;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerSede;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.administrativo.PerCotizacionTransporte;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.demeter.implementacion.gestion.PerControlUnidadFuncional;
import cpc.persistencia.demeter.implementacion.gestion.PerImplementoUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerMaquinariaUnidad;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoTransporte;
import cpc.persistencia.demeter.implementacion.gestion.PerOrdenTrabajoTransporteInterno;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoTrabajo;
import cpc.persistencia.demeter.implementacion.transporte.PerUbicacionTransporte;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioOrdenTrabajoTransporteInterno extends NegocioGenerico<OrdenTrabajoTransporteInterno, PerOrdenTrabajoTransporteInterno, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4313150339055514965L;
	private static NegocioOrdenTrabajoTransporteInterno negocio;
	
	private  NegocioOrdenTrabajoTransporteInterno(){
		setPersistencia(new PerOrdenTrabajoTransporteInterno());
	}

	public  static synchronized NegocioOrdenTrabajoTransporteInterno getInstance() {
		if (negocio == null)
			negocio = new NegocioOrdenTrabajoTransporteInterno();
		return negocio;
	}

	public List<Trabajador> getOperadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getConductores();
	}
	
	public List<MaquinariaUnidad> getMaquinarias(UnidadFuncional unidad) throws ExcFiltroExcepcion {
	
		return new PerMaquinariaUnidad().getMaquinaria(unidad);
	}
	public List<ImplementoUnidad> getImplementos(UnidadFuncional unidad) throws ExcFiltroExcepcion {
	
		return new PerImplementoUnidad().getImplementos(unidad);
	}
	
	public List<CotizacionTransporte> getCotizacionTransporte()
	{
		return new PerCotizacionTransporte().getAllCotizacionTransporte();
	}
	
	public void guardar(OrdenTrabajoTransporteInterno ordenTrabajoTransporte) throws Exception
	{	
		
		ordenTrabajoTransporte.setSede(getSede());
		new PerOrdenTrabajoTransporteInterno().guardarOrden(ordenTrabajoTransporte, ordenTrabajoTransporte.getId());
	}
	
	public void anular(OrdenTrabajoTransporteInterno ordenTrabajoTransporte) throws ExcFiltroExcepcion
	{
		new PerOrdenTrabajoTransporteInterno().anular(ordenTrabajoTransporte, ordenTrabajoTransporte.getId(), false);
	}
	public void cerrar(OrdenTrabajoTransporteInterno ordenTrabajoTransporte) throws ExcFiltroExcepcion
	{
		new PerOrdenTrabajoTransporteInterno().cerrar(ordenTrabajoTransporte, ordenTrabajoTransporte.getId());
	}
	
	public List<MaquinariaUnidad> getMaquinariasOperativas() throws ExcFiltroExcepcion {
	List<Criterion> a= new ArrayList<Criterion>();
	a.add(Restrictions.eq("operativo", Boolean.TRUE));
		//	.add(Restrictions.eq("operativo", Boolean.TRUE))
		return new PerMaquinariaUnidad().getcritaeriaAll(a);
		
	}
	
	public List<ImplementoUnidad> getImplementosOperativos() throws ExcFiltroExcepcion {
		List<Criterion> a= new ArrayList<Criterion>();
		a.add(Restrictions.eq("operativo", Boolean.TRUE));	
		//a.add(Restrictions.eq("activo.categoria", "00000003"));
		return new PerImplementoUnidad().getcritaeriaAll(a);
	}
	
	
	public List<IProducto> getServiciosTransporteActivos() {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return  new PerLabor().getAllLaborACTIVAS(new PerTipoServicio().getTipoTransporte());
	}
	

	public List<UbicacionTransporte> getUbicacionTransportes() throws ExcFiltroExcepcion{
		return new PerUbicacionTransporte().getcritaeriaAll();
	}
	public IProducto enriqueserProducto(IProducto producto){
		if (producto instanceof Labor) {
			return new PerLabor().getDato((Labor)producto);
		}else{
			return new PerArticuloVenta().getDato((ArticuloVenta) producto);
		}
	}
	
	public List<UnidadFuncional> getUnidadesFuncionales() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	private Sede getSede(){
		String  idSede= (String) SpringUtil.getBean("idsede");
		String  idEmpresa= (String) SpringUtil.getBean("idEmpresa");
		return new PerSede().buscarId(new SedePK(idEmpresa, idSede));
	}

	public List<CotizacionTransporte> getCotizacionestransporteActivas() throws ExcFiltroExcepcion{				
		List<CotizacionTransporte> contratos = new PerCotizacionTransporte().getCotizacionesTransporteActivas();
		return contratos; 
	}
	public List<IProducto> getLabores(Servicio servicio) {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return new PerLabor().getAllLabor(servicio);
	}
	public List<IProducto> getServiciosTransporte() {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte());
	}
	
	public OrdenTrabajoTransporteInterno inizializar(OrdenTrabajoTransporteInterno orden) {
		//List<IProducto> articulos = new PerArticuloVenta().getAllArticuloActivo();
		//articulos.addAll(new PerLabor().getAllProductos(new PerTipoServicio().getTipoTransporte()));
		
		return new PerOrdenTrabajoTransporteInterno().inizializar(orden);
	}
}
