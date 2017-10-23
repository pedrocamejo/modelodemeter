package cpc.negocio.demeter.gestion;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.sigesp.basico.ActivoAlmacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;

public class NegocioActivoAlmacen implements Serializable{

	private static final long serialVersionUID = -6894053374057692747L;
	private static NegocioActivoAlmacen negocio;
	private PerActivoAlmacen 			persistencia;
	private ActivoAlmacen 				activoAlmacen;
	
	public NegocioActivoAlmacen (){
		persistencia = new PerActivoAlmacen();
	}

	public static synchronized NegocioActivoAlmacen getInstance(){
		if (negocio == null)
			negocio = new NegocioActivoAlmacen();
		return negocio;
	}

	public static NegocioActivoAlmacen getNegocio() {
		return negocio;
	}
	
	public List<ActivoAlmacen> obtenerTodos(){
		return persistencia.getTodos();
	}
	
	public List<ActivoAlmacen> obtenerTodosSinAlmacenOperativo(	List<TipoAlmacen> a){
	
		return persistencia.getTodosSinAlmacenOperativo(a);
	}
	
	public void guardar() throws Exception {
		persistencia.guardar(activoAlmacen, activoAlmacen.getId());
	}

	public static void setNegocio(NegocioActivoAlmacen negocio) {
		NegocioActivoAlmacen.negocio = negocio;
	}

	public PerActivoAlmacen getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(PerActivoAlmacen persistencia) {
		this.persistencia = persistencia;
	}

	public ActivoAlmacen getActivoAlmacen() {
		return activoAlmacen;
	}

	public void setActivoAlmacen(ActivoAlmacen activoAlmacen) {
		this.activoAlmacen = activoAlmacen;
	}
}
