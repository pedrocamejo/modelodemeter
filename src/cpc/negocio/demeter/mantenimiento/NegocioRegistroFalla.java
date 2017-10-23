package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.mantenimiento.MomentoFalla;
import cpc.modelo.demeter.mantenimiento.ObjetoMantenimiento;
import cpc.modelo.demeter.mantenimiento.RegistroFalla;
import cpc.modelo.demeter.mantenimiento.TipoFalla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMomentoFalla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerObjetoManetenimiento;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerRegistroFalla;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoFalla;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioRegistroFalla {
	private static NegocioRegistroFalla     	negocio;
	private PerRegistroFalla				    persistencia;
	private List<RegistroFalla>			        registrosFalla;
	private RegistroFalla					    registroFalla;
	
	private NegocioRegistroFalla(){
		persistencia = new PerRegistroFalla(); 
	}

	public  static synchronized NegocioRegistroFalla getInstance() {
		if (negocio == null)
			negocio = new NegocioRegistroFalla();
		return negocio;
	}

	public List<RegistroFalla> getTodos() throws ExcFiltroExcepcion{
		List<RegistroFalla> registro = null;
		try {
			registro = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return registro;
	}

	public static NegocioRegistroFalla getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioRegistroFalla negocio) {
		NegocioRegistroFalla.negocio = negocio;
	}

	public PerRegistroFalla getPerRegistroFalla() {
		return persistencia;
	}

	public void setPerRegistroFalla(PerRegistroFalla perRegistroFalla) {
		this.persistencia = perRegistroFalla;
	}

	
	public List<RegistroFalla> getRegistrosFalla() throws ExcFiltroExcepcion {
		this.registrosFalla = this.persistencia.getAll();
		return registrosFalla;
	}

	public void setRegistrofalla(List<RegistroFalla> registrosFalla) {
		this.registrosFalla = registrosFalla;
	}	
	
	public RegistroFalla getRegistroFalla(Long indice) {
		return persistencia.buscarId(indice);
	}
	
		
	public RegistroFalla getRegistroFalla() {
		return registroFalla;
	}

	public void setRegistroFalla(RegistroFalla registroFalla) {
		this.registroFalla = registroFalla;
	}

	public void guardar() throws Exception{
		persistencia.guardar(registroFalla, registroFalla.getId());
	}
	
	public void eliminar() throws Exception{
		persistencia.borrar(registroFalla);
	}
	
	public List<Trabajador> getTrabajadores() throws ExcFiltroExcepcion{
		return new PerTrabajador().getAll();
	}

	public List< ObjetoMantenimiento> getObjetosMantenimiento() throws ExcFiltroExcepcion{
		return new PerObjetoManetenimiento().getAll();
	}

	public List<TipoFalla> getTipoFalla() throws ExcFiltroExcepcion{
		return new PerTipoFalla().getAll();
	}
	
	public List<MomentoFalla> getMomentoFalla() throws ExcFiltroExcepcion{
		return new PerMomentoFalla().getAll();
	}
	
}
