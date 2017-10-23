package cpc.persistencia.demeter.implementacion;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerIva extends DaoGenerico<Impuesto, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926953112909321935L;

	public PerIva() {
		super(Impuesto.class);
		// TODO Auto-generated constructor stub
	}
	
	public Impuesto getExcento(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Impuesto criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (Impuesto) em.createCriteria(Impuesto.class)
				.add(Restrictions.eq("porcentaje",0.00))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	public Impuesto getImpuesto(String descripcion, double porcentaje){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Impuesto criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (Impuesto) em.createCriteria(Impuesto.class)
				.add(Restrictions.eq("porcentaje",porcentaje))
				.add(Restrictions.eq("descripcion",descripcion))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
}
