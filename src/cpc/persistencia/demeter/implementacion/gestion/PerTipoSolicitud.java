package cpc.persistencia.demeter.implementacion.gestion;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.basico.TipoSolicitud;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerTipoSolicitud extends DaoGenerico<TipoSolicitud, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9163448561567355812L;

	public PerTipoSolicitud() {
		super(TipoSolicitud.class);		
	}
	
	public TipoSolicitud getTipoMecanizado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (TipoSolicitud) em.createCriteria(TipoSolicitud.class)
				.add(Restrictions.like("nombre", "%MECANIZACION%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoSolicitud getTipoServicioTecnico(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoSolicitud criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (TipoSolicitud) em.createCriteria(TipoSolicitud.class)
				.add(Restrictions.like("nombre", "%SERVICIO%"))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}
