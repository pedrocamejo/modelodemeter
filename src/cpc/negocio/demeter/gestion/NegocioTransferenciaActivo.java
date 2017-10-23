package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.zkplus.spring.SpringUtil;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.MotivoTransferenciaActivo;
import cpc.modelo.demeter.gestion.Movimiento;
import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.demeter.gestion.TransferenciaActivo;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.gestion.PerMotivoTransferenciaActivo;
import cpc.persistencia.demeter.implementacion.gestion.PerTipoMovimiento;
import cpc.persistencia.demeter.implementacion.gestion.PerTransferenciaActivo;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTransferenciaActivo implements Serializable{

	private static final long serialVersionUID = -6951371805170294420L;
	private static NegocioTransferenciaActivo 	negocio;
	private PerTransferenciaActivo				persistencia;
	private Movimiento							movimiento;
	
	public NegocioTransferenciaActivo(){
		persistencia = new PerTransferenciaActivo();
	}
	
	public static synchronized NegocioTransferenciaActivo getInstance() {
		if (negocio == null)
			negocio = new NegocioTransferenciaActivo();
		return negocio;
	}
	
	public List<Movimiento> getTransferencias() {
		return persistencia.getTodas();
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
	
	public ControlSede obtenerControlSede(UnidadAdministrativa unidadAdministrativa){
		return new PerControlSede().getPorUnidadAdministrativa(unidadAdministrativa);
	}
	
	public TipoMovimiento obtenerTipoTransferencia() {
		PerTipoMovimiento perTipo = new PerTipoMovimiento();
		return perTipo.getTipoTransferencia();
	}
	
	public List<Activo> obtenerActivos() {
		return persistencia.getActivos();
	}
	
	public List<Activo> obtenerActivosPorAlmacen (UnidadAdministrativa unidadAdministrativa){
		return persistencia.getActivosPorUnidadAdministrativa(unidadAdministrativa);
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
	
	public List<Almacen> obtenerAlmacenesPorUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa,  List<TipoAlmacen> tipoalmacen) throws ExcFiltroExcepcion{
		 
		return new PerAlmacen().getAlmacenesPorUnidadAdministrativa(unidadAdministrativa,tipoalmacen);
	}
	
	public List<MotivoTransferenciaActivo> getMotivosTransferenciasActivos(){
		List<MotivoTransferenciaActivo> movimientos = null;
		try {
			movimientos = new PerMotivoTransferenciaActivo().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return movimientos;
	}
	
	public List<Modelo> obtenerModelos(){
		try {
			return new PerModelo().getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Movimiento buscar(Integer id){
		setMovimiento(persistencia.buscarId(id));
		return getMovimiento();
	}
	
	public Movimiento nuevaTransferencia (String usuario) {
		if (getModoFuncionamientoGlobal())
			movimiento = new Movimiento(new Date(), negocio.obtenerTipoTransferencia(),usuario);
		else
			movimiento = new Movimiento(new Date(), negocio.obtenerTipoTransferencia(),
					negocio.obtenerUnidadAdministrativa(), usuario);
		return movimiento;
	}
	
	public Almacen obtenerAlmacenAnterior(Activo activo){
		return new PerActivoAlmacen().getPorActivo(activo).getAlmacen();
	}
	
	public void guardar(ControlSede sede) throws Exception {
		if (movimiento.getTransferencias() != null){
			for (TransferenciaActivo item : movimiento.getTransferencias())
				item.setMovimiento(movimiento);
		}	
		persistencia.guardar(movimiento, movimiento.getIdmovimiento(),sede);
	}
	
	public static NegocioTransferenciaActivo getNegocio() {
		return negocio;
	}
	public static void setNegocio(NegocioTransferenciaActivo negocio) {
		NegocioTransferenciaActivo.negocio = negocio;
	}
	
	
	public PerTransferenciaActivo getPersistencia() {
		return persistencia;
	}
	public void setPersistencia(PerTransferenciaActivo persistencia) {
		this.persistencia = persistencia;
	}
	
	
	public Movimiento getMovimiento() {
		return movimiento;	
	}
	
	public void setMovimiento(Movimiento movimiento) {
		if (movimiento.getIdmovimiento() != null)
			this.movimiento = persistencia.getDatos(movimiento);
		else
			this.movimiento = movimiento;
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
