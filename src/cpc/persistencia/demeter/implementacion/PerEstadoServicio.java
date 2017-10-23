package cpc.persistencia.demeter.implementacion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.solicitud.EstadoServicio;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerEstadoServicio extends DaoGenerico<EstadoServicio, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4100762013397381272L;

	public PerEstadoServicio() {
		super(EstadoServicio.class);
		// TODO Auto-generated constructor stub
	}

	public EstadoServicio getEstadoPlanificado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoServicio criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (EstadoServicio) em.createCriteria(EstadoServicio.class)
				.add(Restrictions.eq("id",EstadoServicio.APROBADO_PLANIFICADO))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	/*public TipoFormaPago getTipoEfectivo(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoFormaPago criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoFormaPago) em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.eq("usaCuenta",Boolean.FALSE))
				.add(Restrictions.eq("usaBanco",Boolean.FALSE))
				.add(Restrictions.eq("usaDocumento",Boolean.FALSE))
				.add(Restrictions.eq("nota",Boolean.FALSE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoFormaPago getTipoNota(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoFormaPago criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoFormaPago) em.createCriteria(TipoFormaPago.class)
				.add(Restrictions.eq("nota",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}*/
	
}
