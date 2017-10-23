package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import cpc.modelo.sigesp.basico.UnidadCentralizada;
import cpc.modelo.sigesp.basico.UnidadCentralizadaPK;

import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerUnidadCentralizada extends DaoGenerico<UnidadCentralizada, UnidadCentralizadaPK>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3681879609266543690L;

	public PerUnidadCentralizada() {
		super(UnidadCentralizada.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadCentralizada> getTodos() {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<UnidadCentralizada> unidadesCentralizadas = null;
		
		tx = em.beginTransaction();
		try{
			unidadesCentralizadas = em.createCriteria(UnidadCentralizada.class)
				.addOrder(Order.asc("id"))
				.list();
			tx.commit();
		} catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return unidadesCentralizadas;
	}
/*	
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
*/
}