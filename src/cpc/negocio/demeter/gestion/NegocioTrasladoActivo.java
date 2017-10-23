package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.zkplus.spring.SpringUtil;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.ActaAutorizacion;
import cpc.modelo.demeter.gestion.DetalleActaAutorizacion;
import cpc.modelo.demeter.gestion.DetalleActaSalida;
import cpc.modelo.demeter.gestion.EntradaActivo;
import cpc.modelo.demeter.gestion.MotivoTransferenciaActivo;
import cpc.modelo.demeter.gestion.Movimiento;
import cpc.modelo.demeter.gestion.PrestamoActivo;
import cpc.modelo.demeter.gestion.SalidaActivo;
import cpc.modelo.demeter.gestion.TipoActaAutorizacion;
import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.demeter.gestion.TrasladoActivo;
import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.modelo.sigesp.indice.UnidadAdministrativaPK;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.gestion.PerActaAutorizacion;
import cpc.persistencia.demeter.implementacion.gestion.PerMotivoTransferenciaActivo;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoActaAutorizacion;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoMovimiento;
import cpc.persistencia.demeter.implementacion.gestion.PerTrasladoActivo;
import cpc.persistencia.ministerio.basico.PerProductorJuridico;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerUnidadAdministrativa;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTrasladoActivo implements Serializable{

	private static final long serialVersionUID = -1783046329939107229L;
	private static NegocioTrasladoActivo 	negocio;
	private PerTrasladoActivo 			persistencia;
	private TrasladoActivo 			trasladoActivo;
	
	
	
	public NegocioTrasladoActivo() {
		persistencia = new PerTrasladoActivo();
	}
	
	public static synchronized NegocioTrasladoActivo getInstance() {
		if (negocio == null)
			negocio = new NegocioTrasladoActivo();
		return negocio;
	}
	
	public List<TrasladoActivo> obtenerEntradas(){
		return persistencia.getEntradas();
	}
	
	public List<TrasladoActivo> obtenerSalidas(){
		return persistencia.getSalidas();
	}
	
	public List<TrasladoActivo> obtenerPrestamos(){
		return persistencia.getPrestamos();
	}
	
	public List<ActaAutorizacion> obtenerActasAutorizacion(){
		try {
			return new PerActaAutorizacion().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ActaAutorizacion> obtenerActasEntradas(){
		TipoActaAutorizacion despacho 	= new PerTipoActaAutorizacion().getTipoDespacho();
		TipoActaAutorizacion entrada 	= new PerTipoActaAutorizacion().getTipoMovimientoEntrada();
		List<ActaAutorizacion> actas = new ArrayList<ActaAutorizacion>();
		for (ActaAutorizacion item : new PerActaAutorizacion().getActasAutorizacion()){
			if (item.getTipoActaAutorizacion().equals(despacho) 
					|| item.getTipoActaAutorizacion().equals(entrada))
				actas.add(item);
		}
		return actas;
	}
	
	public List<ActaAutorizacion> obtenerActasSalidas(){
		TipoActaAutorizacion salida 	= new PerTipoActaAutorizacion().getTipoMovimientoSalida();
		TipoActaAutorizacion prestamo 	= new PerTipoActaAutorizacion().getTipoPrestamo();
		List<ActaAutorizacion> actas = new ArrayList<ActaAutorizacion>();
		for (ActaAutorizacion item : new PerActaAutorizacion().getActasAutorizacion()){
			if (item.getTipoActaAutorizacion().equals(salida) 
					|| item.getTipoActaAutorizacion().equals(prestamo))
				actas.add(item);
		}
		return actas;
	}
	
	public ActaAutorizacion obtenerActaAutorizacion(ActaAutorizacion acta){
		return new PerActaAutorizacion().getDatos(acta);
	}
	
	public List<DetalleActaAutorizacion> obtenerDetalleActasRecepcionesPorUnidad(
			UnidadAdministrativa unidad, ActaAutorizacion acta){
		return new PerActaAutorizacion().getDetallesActasPorUnidad(unidad, acta);
	}
	
	public List<DetalleActaSalida> obtenerDetalleActasSalidasPorUnidad(
			UnidadAdministrativa unidad, ActaAutorizacion acta){
		return new PerActaAutorizacion().getDetallesActasSalidasPorUnidad(unidad, acta);
	}
	
	public ControlSede obtenerControlSede(UnidadAdministrativa unidadAdministrativa){
		return new PerControlSede().getPorUnidadAdministrativa(unidadAdministrativa);
	}
	
	public UnidadAdministrativa obtenerUnidadAdministrativa(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl).getUnidadAdministrativa();
	}
	
	public List<UnidadAdministrativa> obtenerSedes(){
		List<UnidadAdministrativa> lista = new ArrayList<UnidadAdministrativa>();
		PerControlSede control = new PerControlSede();
		try {
			for (ControlSede item : control.getAll()){
				lista.add(item.getUnidadAdministrativa());
			}
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public List<UnidadAdministrativaPK> obtenerUnidadesPK(){
		List<UnidadAdministrativaPK> lista = new ArrayList<UnidadAdministrativaPK>();
		PerControlSede control = new PerControlSede();
		try {
			for (ControlSede item : control.getAll()){
				lista.add(item.getUnidadAdministrativa().getId());
			}
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public List<UnidadAdministrativa> obtenerSedesTraslado(){
		PerUnidadAdministrativa perUnidad = new PerUnidadAdministrativa();
		return perUnidad.getUnidadesTraslado();
	}
	
	public List<UnidadAdministrativa> obtenerUnidadesAdministrativas(){
		PerUnidadAdministrativa perUnidad = new PerUnidadAdministrativa();
		return perUnidad.getTodos();
	}
	
	public List<ProductorJuridico> obtenerProductoresJuridicos() throws ExcFiltroExcepcion{
		return new PerProductorJuridico().getAll();
	}
	
	public TipoMovimiento obtenerTipoEntrada() {
		PerTipoMovimiento perTipo = new PerTipoMovimiento();
		return perTipo.getTipoEntrada();
	}
	
	public TipoMovimiento obtenerTipoSalida() {
		PerTipoMovimiento perTipo = new PerTipoMovimiento();
		return perTipo.getTipoSalida();
	}
	
	public TipoMovimiento obtenerTipoPrestamo() {
		PerTipoMovimiento perTipo = new PerTipoMovimiento();
		return perTipo.getTipoPrestamo();
	}
	
	public List<Activo> obtenerActivos() {
		PerActivo perActivo = new PerActivo();
		return perActivo.getTodos();
	}
	
	public List<Activo> obtenerActivosPorModeloConAlmacen(Modelo modelo){
		return new PerActivo().getPorModeloConAlmacen(modelo);
	}
	
	public List<Activo> obtenerActivosPorModeloSinAlmacenOperativo(Modelo modelo){
		return new PerActivo().getPorModeloSinAlmacenOperativo(modelo);
	}
	
	public List<Activo> obtenerActivosPorModelo(Modelo modelo){
		return new PerActivo().getPorModelo(modelo);
	}
	
	public List<Almacen> obtenerAlmacenesPorUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa,	  List<TipoAlmacen> tipoalmacen) throws ExcFiltroExcepcion{
		  
		return new PerAlmacen().getAlmacenesPorUnidadAdministrativa(unidadAdministrativa,tipoalmacen);
	}
	
	public List<Almacen> getAlmacenes() {
		PerAlmacen perAlmacen = new PerAlmacen();
		List<Almacen> almacenes = null;
		try {
			almacenes = perAlmacen.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return almacenes;
	}
	
	public Almacen ObtenerAlmacen(Activo activo){
		return new PerActivoAlmacen().getPorActivo(activo).getAlmacen();
	}
	
	public MotivoTransferenciaActivo ObtenerEstado(Activo activo){
		return new PerActivoAlmacen().getPorActivo(activo).getMotivo();
	}
	
	public List<MotivoTransferenciaActivo> getEstadosActivos(){
		List<MotivoTransferenciaActivo> estadosActivos = null;
		try {
			estadosActivos = new PerMotivoTransferenciaActivo().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return estadosActivos;
	}
	
	public List<Movimiento> obtenerSalidasConActivosDeRecepcion(List<Activo> activos){
		return persistencia.getSalidasConActivosEnRecepcion(activos);
	}
	
	public List<Activo> obtenerActivosDeEntrada(){
		List<Activo> lista = new ArrayList<Activo>();
		for (EntradaActivo item : trasladoActivo.getEntradas()){
			lista.add(item.getActivo());
		}
		return lista;
	}
	
	public List<Modelo> obtenerModelosActivos(){
		try {
			return new PerModelo().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TrasladoActivo buscar(Integer id){
		setTrasladoActivo(persistencia.buscarId(id));
		return getTrasladoActivo();
	}
	
	public TrasladoActivo nuevaEntrada (String usuario){
		if (getModoFuncionamientoGlobal()){
			trasladoActivo = new TrasladoActivo(new Date(), negocio.obtenerTipoEntrada(),usuario);
		} else {
			trasladoActivo = new TrasladoActivo(new Date(), negocio.obtenerTipoEntrada(),
                    negocio.obtenerUnidadAdministrativa(), usuario);
		}
		return trasladoActivo;
	}
	
	public TrasladoActivo nuevaSalida (String usuario){
		if (getModoFuncionamientoGlobal()){
			trasladoActivo = new TrasladoActivo(new Date(), negocio.obtenerTipoSalida(),usuario);
		} else {
			trasladoActivo = new TrasladoActivo(new Date(), negocio.obtenerTipoSalida(),
                    negocio.obtenerUnidadAdministrativa(), usuario);
		}
		return trasladoActivo;
	}
	
	public TrasladoActivo nuevoPrestamo (String usuario){
		if (getModoFuncionamientoGlobal()){
			trasladoActivo = new TrasladoActivo(new Date(), negocio.obtenerTipoPrestamo(),usuario);
		} else {
			trasladoActivo = new TrasladoActivo(new Date(), negocio.obtenerTipoPrestamo(),
                    negocio.obtenerUnidadAdministrativa(), usuario);
		}
		return trasladoActivo;
	}
	
	public void guardar(ControlSede sede) throws Exception {
		if (trasladoActivo.getEntradas() != null){
			for (EntradaActivo item : trasladoActivo.getEntradas())
				item.setMovimiento(trasladoActivo);
		} 
		if (trasladoActivo.getSalidas() != null){
			for (SalidaActivo item : trasladoActivo.getSalidas())
				item.setMovimiento(trasladoActivo);
		}
		
		if (trasladoActivo.getPrestamos() != null){
			for (PrestamoActivo item : trasladoActivo.getPrestamos())
				item.setMovimiento(trasladoActivo);
		}
		persistencia.guardar(trasladoActivo, trasladoActivo.getIdmovimiento(), sede);
	}
	
	public void anular(TrasladoActivo traslado) {
		persistencia.anular(traslado);
	}

	public static NegocioTrasladoActivo getNegocio() {
		return negocio;
	}
	
	public static void setNegocio(NegocioTrasladoActivo negocio) {
		NegocioTrasladoActivo.negocio = negocio;
	}
	
	public PerTrasladoActivo getPersistencia() {
		return persistencia;
	}
	
	public void setPersistencia(PerTrasladoActivo persistencia) {
		this.persistencia = persistencia;
	}
	
	public TrasladoActivo getTrasladoActivo() {
		return trasladoActivo;
	}
	
	public void setTrasladoActivo(TrasladoActivo trasladoActivo) {
		if (trasladoActivo.getIdmovimiento() != null)
			this.trasladoActivo = persistencia.getDatos(trasladoActivo);
		else
			this.trasladoActivo  = trasladoActivo;
	}

	public Boolean getModoFuncionamientoGlobal(){
		Boolean  funcion= (Boolean) SpringUtil.getBean("funcionamientoActivos");
		return funcion;
	}

	public ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	public ControlSede actualizarControlSede(ControlSede control){
		ControlSede controlReal;
		if (getModoFuncionamientoGlobal())
			controlReal = control;
		else
			controlReal= getControlSede();
		return controlReal;
	}
	
}
