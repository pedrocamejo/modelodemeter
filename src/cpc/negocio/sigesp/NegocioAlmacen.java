package cpc.negocio.sigesp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerUnidadAdministrativa;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioAlmacen implements Serializable{
	private static final long 	serialVersionUID = -7487511459361053379L;
	private static NegocioAlmacen 	negocio;
	private PerAlmacen				persistencia;
	private Almacen					almacen;
	
	
	private NegocioAlmacen(){
		persistencia = new PerAlmacen();
	}
	
	public static synchronized NegocioAlmacen getInstance(){
		if (negocio == null)
			negocio = new NegocioAlmacen();
		return negocio;
	}
	
	public List<UnidadAdministrativa> getUnidadesAdministrativas(){
		PerUnidadAdministrativa perUnidadAdministrativa = new PerUnidadAdministrativa();
		return perUnidadAdministrativa.getTodos();
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
	
	public List<TipoAlmacen> getTiposAlmacen (){
		PerTipoAlmacen perTipoAlmacen = new PerTipoAlmacen();
		return perTipoAlmacen.getAll();
	}
	
	public List<UnidadFuncional> getUnidadesEjecutoras() throws ExcFiltroExcepcion{
		return new PerUnidadFuncional().getAll();
	}
	
	public List<UbicacionDireccion> getUbicaciones() throws ExcFiltroExcepcion{
		PerDireccion perUbicacion = new PerDireccion();
		return perUbicacion.getAll();
	}
	
	public List<Trabajador> getTrabajadores(){
		PerTrabajador perTrabajador = new PerTrabajador();
		return perTrabajador.getTodos();
	}
	
	public List<Almacen> getAlmacenes() throws ExcFiltroExcepcion{
		PerAlmacen perAlmacen = new PerAlmacen();
		return perAlmacen.getAll();
	}
	
	public UnidadAdministrativa obtenerUnidadAdministrativa(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl).getUnidadAdministrativa();
	}
	
	public Almacen getAlmacen (Integer indice) {
		return persistencia.buscarId(indice);
	}
	
	public void guardar()  throws Exception{
		persistencia.guardar(almacen, almacen.getId());
	}
	
	public static NegocioAlmacen getNegocio() {
		return negocio;
	}
	public static void setNegocio(NegocioAlmacen negocio) {
		NegocioAlmacen.negocio = negocio;
	}
	public PerAlmacen getPersistencia() {
		return persistencia;
	}
	public void setPersistencia(PerAlmacen persistencia) {
		this.persistencia = persistencia;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	
	public Boolean getModoFuncionamientoGlobal(){
		Boolean  funcion= (Boolean) SpringUtil.getBean("funcionamientoActivos");
		return funcion;
	}
	

}
