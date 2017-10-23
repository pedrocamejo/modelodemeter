package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.zkoss.zkplus.spring.SpringUtil;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.EntradaActivo;
import cpc.modelo.demeter.gestion.MotivoTransferenciaActivo;
import cpc.modelo.demeter.gestion.Movimiento;
import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.gestion.PerMotivoTransferenciaActivo;
import cpc.persistencia.demeter.implementacion.gestion.PerRecepcionInicialActivo;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoMovimiento;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioRecepcionInicial implements Serializable{

	private static final long serialVersionUID = 4134986481998184751L;
	private static NegocioRecepcionInicial negocio;
	private PerRecepcionInicialActivo 		persistencia;
	private Movimiento 						recepcionIncial;
	
	public NegocioRecepcionInicial(){
		persistencia = new PerRecepcionInicialActivo();
	}
	
	public static synchronized NegocioRecepcionInicial getInstance(){
		if (negocio == null)
			negocio = new NegocioRecepcionInicial();
		return negocio;
	}
	
	public List<Movimiento> getTodas(){
		return persistencia.getTodas();
	}
	
	public ControlSede obtenerControlSede(UnidadAdministrativa unidadAdministrativa){
		return new PerControlSede().getPorUnidadAdministrativa(unidadAdministrativa);
	}
	
	public UnidadAdministrativa obtenerUnidadAdministrativa(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl).getUnidadAdministrativa();
	}
	
	public List<UnidadAdministrativa> obtenerUnidadesAdministrativas(){
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
	
	public TipoMovimiento obtenerTipoRecepcionInicial() {
		PerTipoMovimiento perTipo = new PerTipoMovimiento();
		return perTipo.getTipoEntradaInicial();
	}
	
	public List<Activo> obtenerActivos() {
		return persistencia.getActivos();
	}
	
	public List<Almacen> obtenerAlmacenesPorUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa,   List<TipoAlmacen> tipoalmacen) throws ExcFiltroExcepcion{
	  
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
	
	public List<Activo> obtenerActivosPorModelo (Modelo modelo){
		return new PerActivo().getPorModeloSinAlmacen(modelo);
	}
	
	
	public List<Activo> obtenerActivosDeEntrada(){
		List<Activo> lista = new ArrayList<Activo>();
		for (EntradaActivo item : recepcionIncial.getEntradas()){
			lista.add(item.getActivo());
		}
		return lista;
	}
	
	public List<Modelo> obtenerModelos(){
		try {
			return new PerModelo().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Modelo> obtenerModelosConActivos(){
		return new PerModelo().getModeloConActivos();
	}
	
	public Movimiento buscar(Integer id){
		setRecepcionIncial(persistencia.buscarId(id));
		return getRecepcionIncial();
	}
	
	public Movimiento nuevaRecepcionIncial (String usuario) {
		if (getModoFuncionamientoGlobal()){
			recepcionIncial = new Movimiento(new Date(), negocio.obtenerTipoRecepcionInicial(), usuario);
		} else {
			recepcionIncial = new Movimiento(new Date(), negocio.obtenerTipoRecepcionInicial(), 
                    negocio.obtenerUnidadAdministrativa(), usuario);
		}
		
		return recepcionIncial;
	}
	
	public void guardar (ControlSede sede) throws Exception{
		if (recepcionIncial.getEntradas() != null)
			for (EntradaActivo item : recepcionIncial.getEntradas())
				item.setMovimiento(recepcionIncial);
		persistencia.guardar(recepcionIncial, recepcionIncial.getIdmovimiento(), sede);
	}
	
	public void anular (Movimiento recepcion) throws HibernateException{
		persistencia.anular(recepcion);
	}
	
	public static NegocioRecepcionInicial getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioRecepcionInicial negocio) {
		NegocioRecepcionInicial.negocio = negocio;
	}
	

	public PerRecepcionInicialActivo getPersistencia() {
		return persistencia;
	}
	
	public void setPersistencia(PerRecepcionInicialActivo persistencia) {
		this.persistencia = persistencia;
	}
	
	
	public Movimiento getRecepcionIncial() {
		return recepcionIncial;
	}
	
	public void setRecepcionIncial(Movimiento recepcionIncial) {
		if (recepcionIncial.getIdmovimiento() != null)
			this.recepcionIncial = persistencia.getDatos(recepcionIncial);
		else
			this.recepcionIncial = recepcionIncial;
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
