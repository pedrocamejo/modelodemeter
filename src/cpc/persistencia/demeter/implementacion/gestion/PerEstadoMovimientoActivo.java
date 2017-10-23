package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.gestion.EstadoMovimientoActivo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEstadoMovimientoActivo extends DaoGenerico<EstadoMovimientoActivo, Integer>{

	private static final long serialVersionUID = 422011405644585347L;

	public PerEstadoMovimientoActivo() {
		super(EstadoMovimientoActivo.class);
	}
	
	public EstadoMovimientoActivo getProcesado(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		EstadoMovimientoActivo criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (EstadoMovimientoActivo) em.createCriteria(EstadoMovimientoActivo.class)
			.add(Restrictions.eq("anulado", false))
			.add(Restrictions.like("descripcion", "PROCESADO"))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public EstadoMovimientoActivo getAnulado(){
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		EstadoMovimientoActivo criterio = null;
		tx = em.beginTransaction();
		try{
			criterio = (EstadoMovimientoActivo) em.createCriteria(EstadoMovimientoActivo.class)
			.add(Restrictions.eq("anulado", true))
			.add(Restrictions.like("descripcion", "ANULADO"))
			.uniqueResult();
		tx.commit();	
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}
