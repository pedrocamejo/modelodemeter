package cpc.persistencia.demeter.implementacion.gestion;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.gestion.DetalleMovimiento;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.Movimiento;
import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.demeter.gestion.TransferenciaActivo;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.ActivoAlmacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerTransferenciaActivo extends DaoGenerico<Movimiento, Integer>{

	
	private static final long serialVersionUID = 4417107181328960409L;

	public PerTransferenciaActivo() {
		super(Movimiento.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Movimiento> getTodas(){
		Transaction tx = null;
		List<Movimiento> transferencias = null;
		TipoMovimiento tipoMovimiento = new PerTipoMovimiento().getTipoTransferencia();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			transferencias = (List<Movimiento>) em.createCriteria(Movimiento.class)
				.add(Restrictions.eq("tipomovimiento", tipoMovimiento))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return transferencias;
	}
	
	@SuppressWarnings("unused") 
	public Movimiento getDatos(Movimiento entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Movimiento docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (Movimiento) em.load(Movimiento.class, entrada.getIdmovimiento());
			if(docu.getTransferencias() != null)
			for (DetalleMovimiento item: docu.getTransferencias()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
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
				.add(Restrictions.isNotNull("almacen"))
				.addOrder(Order.asc("codigoActivo"))
				.addOrder(Order.asc("idEjemplarActivo"))
				.addOrder(Order.asc("serial"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Activo> getActivosPorUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa){
		List<Activo> activos 		= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		try{
			activos = em.createQuery(
					"SELECT actalm.activo "+
					"FROM ActivoAlmacen actalm "+
					"JOIN actalm.almacen alm "+
					"WHERE alm.unidadAdministrativa = :unidad "+
					"  AND actalm.trasladado = :esTrasladado " +
					"  AND actalm.desincorporado = :esdesincorporado " +
					"Order By actalm.activo")
					.setEntity("unidad", unidadAdministrativa)
					.setBoolean("esTrasladado", Boolean.FALSE)
					.setBoolean("esdesincorporado", Boolean.FALSE)
					.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return activos;
	}
	
	public void guardar (Movimiento transferencia, Integer indice, ControlSede sede)   throws Exception{
		if (indice == null)
	   		 //em.persist(objeto);
	   		nuevo(transferencia, sede);
	   	 else
	   		 //em.merge(objeto);
	   		 super.guardar(transferencia, indice);
		
		
	}
	
	private void nuevo( Movimiento transferencia, ControlSede sede) throws HibernateException, ExcFiltroExcepcion{
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
		Session em;
		try{
			//ControlSede control = new PerControlSede().getControlSede();
			sede.incrementarControlTransferenciaActivo();
			
			transferencia.setNumeroControl(sede.getControlTransferenciaActivo());
			transferencia.setPrefijo(sede.getPrefijoTransferenciaActivo());
			transferencia.setEstado(new PerEstadoMovimientoActivo().getProcesado());
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			em.save(transferencia);
			em.update(sede);
			em.flush();
			tx.commit();
			
			usuario = transferencia.getUsuario();
			fecha = transferencia.getFecha();
			
			if (transferencia.getTransferencias() != null) {
				for (TransferenciaActivo item : transferencia.getTransferencias()){
					activoAlmacen = perActivoAlmacen.getPorActivo(item.getActivo());
					activoAlmacen.setAlmacen(item.getAlmacenActual());
					activoAlmacen.setFechaActualizacion(fecha);
					activoAlmacen.setUsuario(usuario);
					activoAlmacen.setObservacion(item.getObservaciones());
					activoAlmacen.setMotivo(item.getMotivo());
					perActivoAlmacen.guardar(activoAlmacen, activoAlmacen.getId());
					
					
					if (item.getActivo().getCategoria().isMaquinaria()){
						maquinariaUnidad = perMaquinariaUnidad.getPorMaquinariaYUnidadFuncional(item.getAlmacenActual().getUnidadFuncional(),
								item.getActivo());
						if (maquinariaUnidad != null){
							if (item.getAlmacenActual().getTipoAlmacen().equals(tipoOperativo)){
								maquinariaUnidad.setOperativo(true);
							} else{
								maquinariaUnidad.setOperativo(false);
							}
						} else {
							maquinariaUnidad = new MaquinariaUnidad();
							maquinariaUnidad.setActivo(item.getActivo());
							if (item.getAlmacenActual().getTipoAlmacen().equals(tipoOperativo))
								maquinariaUnidad.setOperativo(true);
							else
								maquinariaUnidad.setOperativo(false);
							maquinariaUnidad.setUnidad(item.getAlmacenActual().getUnidadFuncional());
						}
						perMaquinariaUnidad.guardar(maquinariaUnidad, maquinariaUnidad.getId());
					} else if (item.getActivo().getCategoria().isImplemento()){
						implementoUnidad = perImplementoUnidad.getPorImplementoYUnidadFuncional(item.getAlmacenActual().getUnidadFuncional(),
								item.getActivo());
						if (implementoUnidad != null){
							if (item.getAlmacenActual().getTipoAlmacen().equals(tipoOperativo))
								implementoUnidad.setOperativo(true);
							else
								implementoUnidad.setOperativo(false);
						} else {
							implementoUnidad = new ImplementoUnidad();
							implementoUnidad.setActivo(item.getActivo());
							if (item.getAlmacenActual().getTipoAlmacen().equals(tipoOperativo))
								implementoUnidad.setOperativo(true);
							else
								implementoUnidad.setOperativo(false);
							implementoUnidad.setUnidad(item.getAlmacenActual().getUnidadFuncional());
						}
						perImplementoUnidad.guardar(implementoUnidad, implementoUnidad.getId());
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error al almacenar");
		}
	}

}