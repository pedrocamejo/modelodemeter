package cpc.persistencia.demeter.implementacion.gestion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.OrdenTrabajo;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerDetalleOrdenTrabajo extends DaoGenerico<DetalleOrdenTrabajo, Long>{

	
	private static final long serialVersionUID = 9163448561567355812L;

	public PerDetalleOrdenTrabajo() {
		super(DetalleOrdenTrabajo.class);		
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleOrdenTrabajo> getTodos(OrdenTrabajo orden){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DetalleOrdenTrabajo> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<DetalleOrdenTrabajo>) em.createCriteria(DetalleOrdenTrabajo.class)
				.add(Restrictions.eq("orden", orden))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleMaquinariaOrdenTrabajo> getAllPorMaquinaria(MaquinariaUnidad ma, List<OrdenTrabajoMecanizado> ordenesActivas) {
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DetalleMaquinariaOrdenTrabajo> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<DetalleMaquinariaOrdenTrabajo>) em.createCriteria(DetalleMaquinariaOrdenTrabajo.class)
				.add(Restrictions.eq("maquinaria", ma)).add(Restrictions.in("ordenTrabajo", ordenesActivas))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<DetalleMaquinariaOrdenTrabajo> getAllPorOperador(Trabajador operador, List<OrdenTrabajoMecanizado> activas) {
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DetalleMaquinariaOrdenTrabajo> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<DetalleMaquinariaOrdenTrabajo>) em.createCriteria(DetalleMaquinariaOrdenTrabajo.class)
				.add(Restrictions.eq("operador", operador)).add(Restrictions.in("ordenTrabajo", activas))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DetalleMaquinariaOrdenTrabajo> getAllPorMaquinariaYOperador(MaquinariaUnidad ma, Trabajador operador, List<OrdenTrabajoMecanizado> activas) {
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DetalleMaquinariaOrdenTrabajo> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<DetalleMaquinariaOrdenTrabajo>) em.createCriteria(DetalleMaquinariaOrdenTrabajo.class)
			    .add(Restrictions.eq("maquinaria", ma))
				.add(Restrictions.eq("operador", operador))
				.add(Restrictions.in("ordenTrabajo", activas))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
}
