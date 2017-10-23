package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.modelo.sigesp.indice.UnidadAdministrativaPK;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerUnidadAdministrativa extends DaoGenerico<UnidadAdministrativa, UnidadAdministrativaPK>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3681879609266543690L;

	public PerUnidadAdministrativa() {
		super(UnidadAdministrativa.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadAdministrativa> getTodos() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<UnidadAdministrativa> unidadesAdministrativas = null;
		
		tx = em.beginTransaction();
		try{
			unidadesAdministrativas = em.createCriteria(UnidadAdministrativa.class)
				.addOrder(Order.asc("id"))
				.list();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return unidadesAdministrativas;
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadAdministrativa> getUnidadesTraslado() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<UnidadAdministrativa> unidadesAdministrativas = null;
		
		tx = em.beginTransaction();
		try{
			unidadesAdministrativas = em.createQuery("SELECT u FROM UnidadAdministrativa u "+
					"WHERE u.id NOT IN (SELECT upk.id FROM ControlSede c  JOIN c.unidadAdministrativa upk) " +
					"ORDER BY u.id")
				.list();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return unidadesAdministrativas;
	}

}
