package cpc.persistencia.demeter.implementacion.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.DetalleMovimiento;
import cpc.modelo.demeter.gestion.EntradaActivo;
import cpc.modelo.demeter.gestion.EstadoMovimientoActivo;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.Movimiento;
import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.ActivoAlmacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerRecepcionInicialActivo extends DaoGenerico<Movimiento, Integer>{

	private static final long serialVersionUID = -9016337511163665149L;

	public PerRecepcionInicialActivo() {
		super(Movimiento.class);
	}

	@SuppressWarnings("unchecked")
	public List<Movimiento> getTodas(){
		Transaction tx = null;
		List<Movimiento> Recepciones = null;
		TipoMovimiento tipoMovimiento = new PerTipoMovimiento().getTipoEntradaInicial();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			Recepciones = (List<Movimiento>) em.createCriteria(Movimiento.class)
				.add(Restrictions.eq("tipomovimiento", tipoMovimiento))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return Recepciones;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getActivos(){
		Session em;
		Transaction tx = null;
		List<Activo> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Activo>) em.createCriteria(Activo.class)
				.add(Restrictions.isNull("almacen"))
				.addOrder(Order.asc("id"))
				.addOrder(Order.asc("serial"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
	
	@SuppressWarnings("unused") 
	public Movimiento getDatos(Movimiento entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Movimiento docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (Movimiento) em.load(Movimiento.class, entrada.getIdmovimiento());
			if(docu.getEntradas() != null)
			for (DetalleMovimiento item: docu.getEntradas()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<Movimiento> getSalidasConActivosEnRecepcion( List<Activo> activos){
		EstadoMovimientoActivo procesado = new PerEstadoMovimientoActivo().getProcesado();
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<Movimiento> lista = new ArrayList<Movimiento>();
		tx = em.beginTransaction();
		try{
			lista = em.createQuery("SELECT DISTINCT m FROM SalidaActivo dt JOIN dt.movimiento m " +
					"WHERE dt.activo IN (:activos) " +
					"  AND m.estado = :estadoMovimiento")
				.setParameterList("activos", activos)
				.setParameter("estadoMovimiento", procesado)
				.list();
			tx.commit();
			
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return lista;
	}
	
	public void guardar (Movimiento recepcion, Integer indice, ControlSede sede) throws Exception{
		if (indice == null)
	   		nuevo( recepcion, sede);
	   	 else
	   		 super.guardar(recepcion, indice);
	}
	
	public void nuevo (Movimiento recepcion, ControlSede sede) throws HibernateException, ExcFiltroExcepcion{
		PerActivoAlmacen perActivoAlmacen = new PerActivoAlmacen();
		PerMaquinariaUnidad perMaquinariaUnidad = new PerMaquinariaUnidad();
		PerImplementoUnidad perImplementoUnidad = new PerImplementoUnidad();
		TipoAlmacen tipoOperativo = new PerTipoAlmacen().getTipoOperativo();
	    ActivoAlmacen activoAlmacen;
	    MaquinariaUnidad maquinariaUnidad;
		ImplementoUnidad implementoUnidad;
	    String usuario;
		Date fecha;
		Transaction tx = null;
		Session 	em;
		try {
			sede.incrementarControlRecepcionActivo();
			
			recepcion.setNumeroControl(sede.getControlRecepcionActivo());
			recepcion.setPrefijo(sede.getPrefijoRecepcionActivo());
			recepcion.setEstado(new PerEstadoMovimientoActivo().getProcesado());
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			em.save(recepcion);
			em.update(sede);
			em.flush();
			tx.commit();
			
			usuario = recepcion.getUsuario();
			fecha 	= recepcion.getFecha();
			
			if (recepcion.getEntradas() != null){
				for (EntradaActivo item : recepcion.getEntradas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo()); 
					if ( activoAlmacen != null){
						activoAlmacen.setAlmacen(item.getAlmacen());
						activoAlmacen.setFechaActualizacion(fecha);
						activoAlmacen.setUsuario(usuario);
						activoAlmacen.setTrasladado(false);
						activoAlmacen.setDesincorporado(false);
						activoAlmacen.setMotivo(item.getMotivo());
						activoAlmacen.setObservacion(item.getObservaciones());
						perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
					} else {
						activoAlmacen = new ActivoAlmacen(item.getActivo(), item.getAlmacen(),
								false, fecha, fecha, usuario, item.getObservaciones(),item.getMotivo(), false);
						perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
					}
					
					if (item.getActivo().getCategoria().isMaquinaria()){
						maquinariaUnidad = perMaquinariaUnidad.getPorMaquinariaYUnidadFuncional(item.getAlmacen().getUnidadFuncional(),
								item.getActivo());
						if (maquinariaUnidad != null){
							if (item.getAlmacen().getTipoAlmacen().equals(tipoOperativo))
								maquinariaUnidad.setOperativo(true);
							else
								maquinariaUnidad.setOperativo(false);
						} else {
							maquinariaUnidad = new MaquinariaUnidad();
							maquinariaUnidad.setActivo(item.getActivo());
							if (item.getAlmacen().getTipoAlmacen().equals(tipoOperativo))
								maquinariaUnidad.setOperativo(true);
							else
								maquinariaUnidad.setOperativo(false);
							maquinariaUnidad.setUnidad(item.getAlmacen().getUnidadFuncional());
						}
						perMaquinariaUnidad.guardar(maquinariaUnidad, maquinariaUnidad.getId());
					} else if (item.getActivo().getCategoria().isImplemento()){
						implementoUnidad = perImplementoUnidad.getPorImplementoYUnidadFuncional(item.getAlmacen().getUnidadFuncional(),
								item.getActivo());
						if (implementoUnidad != null){
							if (item.getAlmacen().getTipoAlmacen().equals(tipoOperativo))
								implementoUnidad.setOperativo(true);
							else
								implementoUnidad.setOperativo(false);
						} else {
							implementoUnidad = new ImplementoUnidad();
							implementoUnidad.setActivo(item.getActivo());
							if (item.getAlmacen().getTipoAlmacen().equals(tipoOperativo))
								implementoUnidad.setOperativo(true);
							else
								implementoUnidad.setOperativo(true);
							implementoUnidad.setUnidad(item.getAlmacen().getUnidadFuncional());
						}
						perImplementoUnidad.guardar(implementoUnidad, implementoUnidad.getId());
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error Almacenando");
		}
		
	}
	
	public void anular(Movimiento recepcion) throws HibernateException{
		PerActivoAlmacen perActivoAlmacen = new PerActivoAlmacen();
		PerMaquinariaUnidad perMaquinariaUnidad = new PerMaquinariaUnidad();
		PerImplementoUnidad perImplementoUnidad = new PerImplementoUnidad();
		//TipoAlmacen tipoOperativo = new PerTipoAlmacen().getTipoOperativo();
	    ActivoAlmacen 			activoAlmacen;
	    List<ActivoAlmacen> 	activosAlmacen = new ArrayList<ActivoAlmacen>();
	    MaquinariaUnidad 		maquinariaUnidad;
	    List<MaquinariaUnidad> 	maquinariasUnidades = new ArrayList<MaquinariaUnidad>();
		ImplementoUnidad 		implementoUnidad;
		List<ImplementoUnidad> 	implementosUnidades = new ArrayList<ImplementoUnidad>();
		Transaction tx = null;
		Session 	em;
		try {
			recepcion.setEstado(new PerEstadoMovimientoActivo().getAnulado());
			recepcion.setFechaAnulacion(new Date());
			if (recepcion.getEntradas() != null){
				for (EntradaActivo item : recepcion.getEntradas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo());
					activosAlmacen.add(activoAlmacen);
					if (item.getActivo().getCategoria().isMaquinaria()){
						maquinariaUnidad = perMaquinariaUnidad.getPorMaquinariaYUnidadFuncional(item.getAlmacen().getUnidadFuncional(),
								item.getActivo());
						if (maquinariaUnidad != null)
							maquinariasUnidades.add(maquinariaUnidad);
					} else if (item.getActivo().getCategoria().isImplemento()){
						implementoUnidad = perImplementoUnidad.getPorImplementoYUnidadFuncional(item.getAlmacen().getUnidadFuncional(),
								item.getActivo());
						if (implementoUnidad != null)
						implementosUnidades.add(implementoUnidad);
					}
				}
			}
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			for (EntradaActivo item :recepcion.getEntradas()){
				em.delete(item);
			}
			recepcion.setEntradas(null);
			for (MaquinariaUnidad item :maquinariasUnidades){
				em.delete(item);
			}
			for (ImplementoUnidad item :implementosUnidades){
				em.delete(item);
			}
			for (ActivoAlmacen item :activosAlmacen){
				em.delete(item);
			}
			em.update(recepcion);
			em.flush();
			tx.commit();
			
			
			/*if (recepcion.getEntradas() != null){
				for (EntradaActivo item : recepcion.getEntradas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo()); 
					if ( activoAlmacen != null){
						perActivoAlmacen.borrar(activoAlmacen);
					}
					
					if (item.getActivo().getCategoria().isMaquinaria()){
						maquinariaUnidad = perMaquinariaUnidad.getPorMaquinariaYUnidadFuncional(item.getAlmacen().getUnidadFuncional(),
								item.getActivo());
						if (maquinariaUnidad != null && item.getAlmacen().getTipoAlmacen().equals(tipoOperativo)){
							maquinariaUnidad.setOperativo(false);
						}
						perMaquinariaUnidad.guardar(maquinariaUnidad, maquinariaUnidad.getId());
					} else if (item.getActivo().getCategoria().isImplemento()){
						implementoUnidad = perImplementoUnidad.getPorImplementoYUnidadFuncional(item.getAlmacen().getUnidadFuncional(),
								item.getActivo());
						if (implementoUnidad != null && item.getAlmacen().getTipoAlmacen().equals(tipoOperativo)){
							implementoUnidad.setOperativo(false);
						}
						perImplementoUnidad.guardar(implementoUnidad, implementoUnidad.getId());
					}
				}
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new HibernateException(e.getCause());
		}
	}
}
