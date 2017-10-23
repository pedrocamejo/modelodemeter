package cpc.persistencia.demeter.implementacion.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.basico.ClaseUnidadArrime;
import cpc.modelo.demeter.basico.TipoUnidadArrime;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerClaseUnidadArrime extends DaoGenerico<ClaseUnidadArrime, Integer>{



	/**
	 * 
	 */
	private static final long serialVersionUID = -5474860796413460355L;

	public PerClaseUnidadArrime() {
		super(ClaseUnidadArrime.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ClaseUnidadArrime> getClasesCriterio(TipoUnidadArrime tipo){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		
		List<ClaseUnidadArrime> clases = null;
		tx = em.beginTransaction();
		try{
			clases = em.createCriteria(ClaseUnidadArrime.class)
				.add(Restrictions.eq("tipo", tipo))
				.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return clases;
	}
	
	@SuppressWarnings("unchecked")
	public List<ClaseUnidadArrime> getClasesHQL(TipoUnidadArrime tipo) throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<ClaseUnidadArrime> clases = null;
		try{
			clases = em.createQuery("SELECT  d FROM TipoUnidadArrime d Inner join d.tipo t  where t.id = :id order by d.id desc")
			.setInteger("id", tipo.getId())
			.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return clases;
	}
	
}
