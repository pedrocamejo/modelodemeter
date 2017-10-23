package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerControlUnidadFuncional extends DaoGenerico<ControlUnidadFuncional, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9163448561567355812L;

	public PerControlUnidadFuncional() {
		super(ControlUnidadFuncional.class);		
	}
	
	public ControlUnidadFuncional getControl(UnidadFuncional unidad){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		ControlUnidadFuncional criterio = null;
		tx = em.beginTransaction();
		try{
			criterio  = (ControlUnidadFuncional) em.createQuery("select c from ControlUnidadFuncional c where c.unidad = :u ").setParameter("u",unidad).uniqueResult();
			//criterio = (ControlUnidadFuncional) em.createCriteria(ControlUnidadFuncional.class)
		    //.add(Restrictions.eq("unidad", unidad))//.list().get(0);
			//.uniqueResult();// hay q arreglar en la semana
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public ControlUnidadFuncional getControl(UnidadFuncional unidad,Transaction tx){
	
		Session em = SessionDao.getInstance().getCurrentSession();
		ControlUnidadFuncional criterio = null;
		
		try{
			criterio  = (ControlUnidadFuncional) em.createQuery("select c from ControlUnidadFuncional c where c.unidad = :u ").setParameter("u",unidad).uniqueResult();
			//criterio = (ControlUnidadFuncional) em.createCriteria(ControlUnidadFuncional.class)
		    //.add(Restrictions.eq("unidad", unidad))//.list().get(0);
			//.uniqueResult();// hay q arreglar en la semana
	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}


}
