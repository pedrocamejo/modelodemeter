package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.gestion.TipoActaAutorizacion;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoActaAutorizacion extends DaoGenerico<TipoActaAutorizacion, Integer>{

	private static final long serialVersionUID = 853009667471324925L;

	public PerTipoActaAutorizacion() {
		super(TipoActaAutorizacion.class);
	}
	
	public TipoActaAutorizacion getTipoDespacho(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoActaAutorizacion criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoActaAutorizacion) em.createCriteria(TipoActaAutorizacion.class)
			.add(Restrictions.eq("despacho", true))
			.add(Restrictions.eq("prestamo", false))
			.add(Restrictions.eq("entrada", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoActaAutorizacion getTipoMovimientoEntrada(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoActaAutorizacion criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoActaAutorizacion) em.createCriteria(TipoActaAutorizacion.class)
			.add(Restrictions.eq("despacho", false))
			.add(Restrictions.eq("prestamo", false))
			.add(Restrictions.eq("entrada", true))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoActaAutorizacion getTipoPrestamo(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoActaAutorizacion criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoActaAutorizacion) em.createCriteria(TipoActaAutorizacion.class)
			.add(Restrictions.eq("despacho", false))
			.add(Restrictions.eq("prestamo", true))
			.add(Restrictions.eq("entrada", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoActaAutorizacion getTipoMovimientoSalida(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoActaAutorizacion criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoActaAutorizacion) em.createCriteria(TipoActaAutorizacion.class)
			.add(Restrictions.eq("despacho", false))
			.add(Restrictions.eq("prestamo", false))
			.add(Restrictions.eq("entrada", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}
