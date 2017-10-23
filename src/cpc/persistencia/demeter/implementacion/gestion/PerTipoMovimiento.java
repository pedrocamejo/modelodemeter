package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoMovimiento extends DaoGenerico<TipoMovimiento, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4371923249557396181L;

	public PerTipoMovimiento() {
		super(TipoMovimiento.class);
	}
	
	public TipoMovimiento getTipoSalida(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoMovimiento criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoMovimiento) em.createCriteria(TipoMovimiento.class)
			.add(Restrictions.eq("salida", true))
			.add(Restrictions.eq("traslado", false))
			.add(Restrictions.eq("entradaIncial", false))
			.add(Restrictions.eq("prestamo", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoMovimiento getTipoEntrada(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoMovimiento criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoMovimiento) em.createCriteria(TipoMovimiento.class)
			.add(Restrictions.eq("salida", false))
			.add(Restrictions.eq("traslado", false))
			.add(Restrictions.eq("entradaIncial", false))
			.add(Restrictions.eq("prestamo", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoMovimiento getTipoTransferencia(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoMovimiento criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoMovimiento) em.createCriteria(TipoMovimiento.class)
			.add(Restrictions.eq("salida", false))
			.add(Restrictions.eq("traslado", true))
			.add(Restrictions.eq("entradaIncial", false))
			.add(Restrictions.eq("prestamo", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoMovimiento getTipoEntradaInicial(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoMovimiento criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoMovimiento) em.createCriteria(TipoMovimiento.class)
			.add(Restrictions.eq("salida", false))
			.add(Restrictions.eq("traslado", false))
			.add(Restrictions.eq("entradaIncial", true))
			.add(Restrictions.eq("prestamo", false))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoMovimiento getTipoPrestamo(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		TipoMovimiento criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (TipoMovimiento) em.createCriteria(TipoMovimiento.class)
			.add(Restrictions.eq("salida", false))
			.add(Restrictions.eq("traslado", false))
			.add(Restrictions.eq("entradaIncial", false))
			.add(Restrictions.eq("prestamo", true))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}
