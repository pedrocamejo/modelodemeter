package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.FamiliaCCNU;
import cpc.modelo.sigesp.basico.SegmentoCCNU;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerSegmentoCCNU extends DaoGenerico<SegmentoCCNU, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8563766923571944683L;


	public PerSegmentoCCNU() {
		super(SegmentoCCNU.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<SegmentoCCNU> getAllUso(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<SegmentoCCNU> criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (List<SegmentoCCNU>) em.createCriteria(SegmentoCCNU.class)
			.add(Restrictions.eq("uso", true))
			.list();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unused")
	public List<FamiliaCCNU> getFamilias(SegmentoCCNU segmento){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		SegmentoCCNU docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (SegmentoCCNU) em.load(SegmentoCCNU.class, segmento.getId());
			for ( FamiliaCCNU item: docu.getFamilias()){em.evict(item);
			}
			tx.commit();
			return  docu.getFamilias();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
     	return null;
	}	
	
	@SuppressWarnings("unchecked")
	public List<SegmentoCCNU> getAllArticulosUso(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<SegmentoCCNU> criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (List<SegmentoCCNU>) em.createCriteria(SegmentoCCNU.class)
			.add(Restrictions.eq("uso", true))
			.add(Restrictions.eq("articulo", true))
			.list();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<SegmentoCCNU> getAllArticulo(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<SegmentoCCNU> criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (List<SegmentoCCNU>) em.createCriteria(SegmentoCCNU.class)
			.add(Restrictions.eq("articulo", true))
			.list();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SegmentoCCNU> getAllServicio(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		List<SegmentoCCNU> criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (List<SegmentoCCNU>) em.createCriteria(SegmentoCCNU.class)
			.add(Restrictions.ne("articulo", true))
			.list();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
}
