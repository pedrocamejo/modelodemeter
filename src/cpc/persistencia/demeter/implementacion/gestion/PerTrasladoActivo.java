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
//import cpc.modelo.demeter.gestion.ActaAutorizacion;
import cpc.modelo.demeter.gestion.DetalleMovimiento;
import cpc.modelo.demeter.gestion.EntradaActivo;
import cpc.modelo.demeter.gestion.EstadoMovimientoActivo;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.Movimiento;
import cpc.modelo.demeter.gestion.PrestamoActivo;
import cpc.modelo.demeter.gestion.SalidaActivo;
import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.demeter.gestion.TrasladoActivo;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.ActivoAlmacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerTrasladoActivo extends DaoGenerico<TrasladoActivo, Integer>{

	
	private static final long serialVersionUID = 4633771749001262820L;

	public PerTrasladoActivo() {
		super(TrasladoActivo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrasladoActivo> obtenerTodosPorFecha (Date fechaInicial, Date fechaFinal){
		Transaction tx = null;
		List<TrasladoActivo> entradas = null;
		EstadoMovimientoActivo procesado = new PerEstadoMovimientoActivo().getProcesado();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			entradas = (List<TrasladoActivo>) em.createCriteria(TrasladoActivo.class)
				.add(Restrictions.between("fecha", fechaInicial, fechaFinal))
				.add(Restrictions.eq("estado", procesado))
				.addOrder(Order.asc("tipomovimiento"))
				.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return entradas;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrasladoActivo> getEntradas (){
		Transaction tx = null;
		List<TrasladoActivo> entradas = null;
		TipoMovimiento tipoMovimiento = new PerTipoMovimiento().getTipoEntrada();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			entradas = (List<TrasladoActivo>) em.createCriteria(TrasladoActivo.class)
				.add(Restrictions.eq("tipomovimiento", tipoMovimiento))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return entradas;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrasladoActivo> getSalidas (){
		Transaction tx = null;
		List<TrasladoActivo> entradas = null;
		TipoMovimiento tipoMovimiento = new PerTipoMovimiento().getTipoSalida();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			entradas = (List<TrasladoActivo>) em.createCriteria(TrasladoActivo.class)
				.add(Restrictions.eq("tipomovimiento", tipoMovimiento))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return entradas;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrasladoActivo> getPrestamos (){
		Transaction tx = null;
		List<TrasladoActivo> entradas = null;
		TipoMovimiento tipoMovimiento = new PerTipoMovimiento().getTipoPrestamo();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			entradas = (List<TrasladoActivo>) em.createCriteria(TrasladoActivo.class)
				.add(Restrictions.eq("tipomovimiento", tipoMovimiento))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return entradas;
	}
	
	@SuppressWarnings("unused") 
	public TrasladoActivo getDatos(TrasladoActivo entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		TrasladoActivo docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (TrasladoActivo) em.load(TrasladoActivo.class, entrada.getIdmovimiento());
			if(docu.getEntradas() != null)
				for (DetalleMovimiento item: docu.getEntradas()){
				}
			if(docu.getSalidas() != null)
				for (DetalleMovimiento item: docu.getSalidas()){
				}
			if(docu.getPrestamos() != null)
				for (DetalleMovimiento item: docu.getPrestamos()){
				}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List obtenerListaPorFecha(Date fechaInicial, Date fechaFinal) throws ExcFiltroExcepcion{
		EstadoMovimientoActivo procesado 	= new PerEstadoMovimientoActivo().getProcesado();
		TipoMovimiento entrada				= new PerTipoMovimiento().getTipoEntrada();
		TipoMovimiento salida 			 	= new PerTipoMovimiento().getTipoSalida();
		TipoMovimiento prestamo 		 	= new PerTipoMovimiento().getTipoPrestamo();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Object[]> facturas = null;
		List<Object[]> salidas 	= null;
		List<Object[]> prestamos 	= null;
		try{
			facturas = em.createQuery(
					"SELECT  ta.id, ta.fecha, tm.idtipomovimiento, tm.descripcion AS tipoMovimiento,"+
					" act.serial, act.chapa, act.nombre, "+
					"m.descripcion AS marca, mo.descripcionModelo AS modelo, 'No aplica' AS unidad, mot.descripcion AS estadoActivo, en.observaciones, " +
					"ta.numeroControl AS control, ta.prefijo, act.id as idActivo "+
					"FROM TrasladoActivo ta "+
					"JOIN ta.tipomovimiento tm "+
					"JOIN ta.entradas en "+
					"JOIN en.activo act "+
					"JOIN en.motivo mot "+
					"JOIN act.marca m " +
					"JOIN act.modelo mo " +
					"WHERE ta.estado = :estadoActivo"+
					"  AND ta.tipomovimiento = :tipo " +
					"  AND ta.fecha BETWEEN :fechai AND :fechah Order By ta.id")
					.setEntity("estadoActivo", procesado)
					.setEntity("tipo", entrada)
					.setDate("fechai", fechaInicial)
					.setDate("fechah", fechaFinal)
					.list();
			salidas = em.createQuery(
					"SELECT  ta.id, ta.fecha, tm.idtipomovimiento, tm.descripcion AS tipoMovimiento,"+
					" act.serial, act.chapa, act.nombre, "+
					"m.descripcion AS marca, mo.descripcionModelo AS modelo, ua.nombre AS unidad, mot.descripcion AS estadoActivo, sa.observaciones, "+
					"ta.numeroControl AS control, ta.prefijo, act.id as idActivo "+
					"FROM TrasladoActivo ta "+
					"JOIN ta.tipomovimiento tm "+
					"JOIN ta.salidas sa "+
					"JOIN sa.activo act "+
					"JOIN sa.motivo mot "+
					"JOIN sa.unidadAdministrativa ua " +
					"JOIN act.marca m " +
					"JOIN act.modelo mo " +
					"WHERE ta.estado = :estadoActivo " +
					"  AND ta.tipomovimiento = :tipo " +
					"  AND ta.fecha BETWEEN :fechai AND :fechah Order By ta.id")
					.setEntity("estadoActivo", procesado)
					.setEntity("tipo", salida)
					.setDate("fechai", fechaInicial)
					.setDate("fechah", fechaFinal)
					.list();
			prestamos = em.createQuery(
					"SELECT  ta.id, ta.fecha, tm.idtipomovimiento, tm.descripcion AS tipoMovimiento,"+
					" act.serial, act.chapa, act.nombre, "+
					"m.descripcion AS marca, mo.descripcionModelo AS modelo, ej.nombres AS unidad, mot.descripcion AS estadoActivo, sa.observaciones, "+
					"ta.numeroControl AS control, ta.prefijo, act.id as idActivo "+
					"FROM TrasladoActivo ta "+
					"JOIN ta.tipomovimiento tm "+
					"JOIN ta.prestamos sa "+
					"JOIN sa.activo act "+
					"JOIN sa.motivo mot "+
					"JOIN sa.enteJuridico ej " +
					"JOIN act.marca m " +
					"JOIN act.modelo mo " +
					"WHERE ta.estado = :estadoActivo " +
					"  AND ta.tipomovimiento = :tipo " +
					"  AND ta.fecha BETWEEN :fechai AND :fechah Order By ta.id")
					.setEntity("estadoActivo", procesado)
					.setEntity("tipo", prestamo)
					.setDate("fechai", fechaInicial)
					.setDate("fechah", fechaFinal)
					.list();
			facturas.addAll(salidas);
			facturas.addAll(prestamos);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return facturas;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List obtenerListaPorActivo(Activo activoInicial, Activo activoFinal) throws ExcFiltroExcepcion{
		EstadoMovimientoActivo procesado 	= new PerEstadoMovimientoActivo().getProcesado();
		TipoMovimiento entrada				= new PerTipoMovimiento().getTipoEntrada();
		TipoMovimiento salida 			 	= new PerTipoMovimiento().getTipoSalida();
		TipoMovimiento prestamo 		 	= new PerTipoMovimiento().getTipoPrestamo();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Object[]> facturas = null;
		List<Object[]> salidas 	= null;
		List<Object[]> prestamos 	= null;
		try{
			facturas = em.createQuery(
					"SELECT  ta.id, ta.fecha, tm.idtipomovimiento, tm.descripcion AS tipoMovimiento,"+
					" act.serial, act.chapa, act.nombre, "+
					"m.descripcion AS marca, mo.descripcionModelo AS modelo, 'No aplica' AS unidad, mot.descripcion AS estadoActivo, en.observaciones, " +
					"ta.numeroControl AS control, ta.prefijo, act.id as idActivo "+
					"FROM TrasladoActivo ta "+
					"JOIN ta.tipomovimiento tm "+
					"JOIN ta.entradas en "+
					"JOIN en.activo act "+
					"JOIN en.motivo mot "+
					"JOIN act.marca m " +
					"JOIN act.modelo mo " +
					"WHERE ta.estado = :estadoActivo"+
					"  AND ta.tipomovimiento = :tipo " +
					"  AND en.activo BETWEEN :activoi AND :activoh Order By act.id, ta.id")
					.setEntity("estadoActivo", procesado)
					.setEntity("tipo", entrada)
					.setEntity("activoi", activoInicial)
					.setEntity("activoh", activoFinal)
					.list();
			salidas = em.createQuery(
					"SELECT  ta.id, ta.fecha, tm.idtipomovimiento, tm.descripcion AS tipoMovimiento,"+
					" act.serial, act.chapa, act.nombre, "+
					"m.descripcion AS marca, mo.descripcionModelo AS modelo, ua.nombre AS unidad, mot.descripcion AS estadoActivo, sa.observaciones, "+
					"ta.numeroControl AS control, ta.prefijo, act.id as idActivo "+
					"FROM TrasladoActivo ta "+
					"JOIN ta.tipomovimiento tm "+
					"JOIN ta.salidas sa "+
					"JOIN sa.activo act "+
					"JOIN sa.motivo mot "+
					"JOIN sa.unidadAdministrativa ua " +
					"JOIN act.marca m " +
					"JOIN act.modelo mo " +
					"WHERE ta.estado = :estadoActivo " +
					"  AND ta.tipomovimiento = :tipo " +
					"  AND sa.activo BETWEEN :activoi AND :activoh Order By act.id, ta.id")
					.setEntity("estadoActivo", procesado)
					.setEntity("tipo", salida)
					.setEntity("activoi", activoInicial)
					.setEntity("activoh", activoFinal)
					.list();
			prestamos = em.createQuery(
					"SELECT  ta.id, ta.fecha, tm.idtipomovimiento, tm.descripcion AS tipoMovimiento,"+
					" act.serial, act.chapa, act.nombre, "+
					"m.descripcion AS marca, mo.descripcionModelo AS modelo, ej.nombres AS unidad, mot.descripcion AS estadoActivo, sa.observaciones, "+
					"ta.numeroControl AS control, ta.prefijo, act.id as idActivo "+
					"FROM TrasladoActivo ta "+
					"JOIN ta.tipomovimiento tm "+
					"JOIN ta.prestamos sa "+
					"JOIN sa.activo act "+
					"JOIN sa.motivo mot "+
					"JOIN sa.enteJuridico ej " +
					"JOIN act.marca m " +
					"JOIN act.modelo mo " +
					"WHERE ta.estado = :estadoActivo " +
					"  AND ta.tipomovimiento = :tipo " +
					"  AND sa.activo BETWEEN :activoi AND :activoh Order By act.id, ta.id")
					.setEntity("estadoActivo", procesado)
					.setEntity("tipo", prestamo)
					.setEntity("activoi", activoInicial)
					.setEntity("activoh", activoFinal)
					.list();
			facturas.addAll(salidas);
			facturas.addAll(prestamos);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return facturas;
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
	
	public void guardar (TrasladoActivo trasladoActivo, Integer indice, ControlSede sede)  throws Exception{
		if (indice == null)
	   		 //em.persist(objeto);
	   		nuevo(trasladoActivo, sede);
	   	 else
	   		 //em.merge(objeto);
	   		 super.guardar(trasladoActivo, indice);		
	}
	
	private void nuevo(TrasladoActivo trasladoActivo, ControlSede sede) throws ExcFiltroExcepcion{
		PerActivoAlmacen perActivoAlmacen = new PerActivoAlmacen();
		PerMaquinariaUnidad perMaquinariaUnidad = new PerMaquinariaUnidad();
		PerImplementoUnidad perImplementoUnidad = new PerImplementoUnidad();
		TipoAlmacen tipoOperativo = new PerTipoAlmacen().getTipoOperativo();
		TipoMovimiento tipoEntrada = new PerTipoMovimiento().getTipoEntrada();
		TipoMovimiento tipoSalida  = new PerTipoMovimiento().getTipoSalida(); 
		TipoMovimiento tipoPrestamo = new PerTipoMovimiento().getTipoPrestamo();
		ActivoAlmacen activoAlmacen;
		MaquinariaUnidad maquinariaUnidad;
		ImplementoUnidad implementoUnidad;
		String usuario;
		Date fecha;
		Transaction tx = null;
		Session em;
		try{
			
			if (trasladoActivo.getTipomovimiento().equals(tipoEntrada) ){
				sede.incrementarControlRecepcionActivo();
				trasladoActivo.setNumeroControl(sede.getControlRecepcionActivo());
				trasladoActivo.setPrefijo(sede.getPrefijoRecepcionActivo());
			}
			
			if (trasladoActivo.getTipomovimiento().equals(tipoSalida) || trasladoActivo.getTipomovimiento().equals(tipoPrestamo)){
				sede.incrementarControlSalidaActivo();
				trasladoActivo.setNumeroControl(sede.getControlSalidaActivo());
				trasladoActivo.setPrefijo(sede.getPrefijoSalidaActivo());
			}
			
			trasladoActivo.setEstado(new PerEstadoMovimientoActivo().getProcesado());
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			em.save(trasladoActivo);
			em.update(sede);
			em.flush();
			tx.commit();
			
			usuario = trasladoActivo.getUsuario();
			fecha = trasladoActivo.getFecha();
			
			if (trasladoActivo.getTipomovimiento().equals(tipoEntrada) && trasladoActivo.getEntradas() != null){
				for (EntradaActivo item : trasladoActivo.getEntradas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo()); 
					if ( activoAlmacen != null){
						activoAlmacen.setAlmacen(item.getAlmacen());
						activoAlmacen.setFechaActualizacion(fecha);
						activoAlmacen.setUsuario(usuario);
						activoAlmacen.setTrasladado(false);
						activoAlmacen.setDesincorporado(false);
						activoAlmacen.setObservacion(item.getObservaciones());
						activoAlmacen.setMotivo(item.getMotivo());
						perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
					} else {
						activoAlmacen = new ActivoAlmacen(item.getActivo(), item.getAlmacen(),
								false, fecha, fecha, usuario, item.getObservaciones(), item.getMotivo(), false);
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
								implementoUnidad.setOperativo(true);
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
			
			if (trasladoActivo.getTipomovimiento().equals(tipoSalida) && trasladoActivo.getSalidas() != null) {
				for (SalidaActivo item : trasladoActivo.getSalidas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo());
					activoAlmacen.setFechaActualizacion(fecha);
					activoAlmacen.setUsuario(usuario);
					activoAlmacen.setTrasladado(true);
					activoAlmacen.setObservacion(item.getObservaciones());
					perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
				}
			}
			
			if (trasladoActivo.getTipomovimiento().equals(tipoPrestamo) && trasladoActivo.getPrestamos() != null) {
				for (PrestamoActivo item : trasladoActivo.getPrestamos()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo());
					activoAlmacen.setFechaActualizacion(fecha);
					activoAlmacen.setUsuario(usuario);
					activoAlmacen.setTrasladado(true);
					activoAlmacen.setObservacion(item.getObservaciones());
					perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error al almacenar");
		}
	}
	
	public void anular(TrasladoActivo trasladoActivo) throws HibernateException{
		PerActivoAlmacen perActivoAlmacen = new PerActivoAlmacen();
		PerMaquinariaUnidad perMaquinariaUnidad = new PerMaquinariaUnidad();
		PerImplementoUnidad perImplementoUnidad = new PerImplementoUnidad();
		TipoAlmacen tipoOperativo = new PerTipoAlmacen().getTipoOperativo();
		TipoMovimiento tipoEntrada = new PerTipoMovimiento().getTipoEntrada();
		TipoMovimiento tipoSalida = new PerTipoMovimiento().getTipoSalida();
		TipoMovimiento tipoPrestamo = new PerTipoMovimiento().getTipoPrestamo();
	    ActivoAlmacen activoAlmacen;
	    MaquinariaUnidad maquinariaUnidad;
		ImplementoUnidad implementoUnidad;
	    String usuario;
	    Date fecha;
		Transaction tx = null;
		Session 	em;
		try {
			trasladoActivo.setEstado(new PerEstadoMovimientoActivo().getAnulado());
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			em.update(trasladoActivo);
			em.flush();
			tx.commit();
			
			usuario = trasladoActivo.getAnuladoPor();
			fecha 	= trasladoActivo.getFechaAnulacion();
			
			if (trasladoActivo.getTipomovimiento().equals(tipoEntrada) && trasladoActivo.getEntradas() != null){
				for (EntradaActivo item : trasladoActivo.getEntradas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo()); 
					if ( activoAlmacen != null){
						activoAlmacen.setFechaActualizacion(fecha);
						activoAlmacen.setUsuario(usuario);
						activoAlmacen.setTrasladado(true);
						perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
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
			}
			
			if (trasladoActivo.getTipomovimiento().equals(tipoSalida) && trasladoActivo.getSalidas() != null){
				for (SalidaActivo item : trasladoActivo.getSalidas()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo());
					activoAlmacen.setFechaActualizacion(fecha);
					activoAlmacen.setUsuario(usuario);
					activoAlmacen.setTrasladado(false);
					perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
				}
			}
			
			if (trasladoActivo.getTipomovimiento().equals(tipoPrestamo) && trasladoActivo.getPrestamos() != null){
				for (PrestamoActivo item : trasladoActivo.getPrestamos()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo());
					activoAlmacen.setFechaActualizacion(fecha);
					activoAlmacen.setUsuario(usuario);
					activoAlmacen.setTrasladado(false);
					perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
}
