package cpc.persistencia.demeter.implementacion.administrativo;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.TipoCotizacion;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoCotizacion  extends DaoGenerico<TipoCotizacion, Integer>{


	public PerTipoCotizacion() {
		super(TipoCotizacion.class);
	}
 
	public TipoCotizacion getMantenimiento()
	{
		TipoCotizacion cotizacion;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		cotizacion  = (TipoCotizacion) em.createCriteria(TipoCotizacion.class).add(Restrictions.eq("id",TipoCotizacion.MANTENIMIENTO)).uniqueResult();
		tx.commit();
		return cotizacion;
	}
	 
	

	public TipoCotizacion getVialidad()
	{
		TipoCotizacion cotizacion;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		cotizacion  = (TipoCotizacion) em.createCriteria(TipoCotizacion.class).add(Restrictions.eq("id",TipoCotizacion.VIALIDAD)).uniqueResult();
		tx.commit();
		return cotizacion;
	}

	public TipoCotizacion getEquipo()
	{
		TipoCotizacion cotizacion;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		cotizacion  = (TipoCotizacion) em.createCriteria(TipoCotizacion.class).add(Restrictions.eq("id",TipoCotizacion.EQUIPO)).uniqueResult();
		tx.commit();
		return cotizacion;
	}
	 
	public TipoCotizacion getTransporte()
	{
		TipoCotizacion cotizacion;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		cotizacion  = (TipoCotizacion) em.createCriteria(TipoCotizacion.class).add(Restrictions.eq("id",TipoCotizacion.TRANSPORTE)).uniqueResult();
		tx.commit();
		return cotizacion;
	}
	 
	 
	 
}
