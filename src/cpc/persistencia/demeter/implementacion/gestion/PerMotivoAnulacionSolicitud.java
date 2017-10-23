package cpc.persistencia.demeter.implementacion.gestion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.AnulacionSolicitud;
import cpc.modelo.demeter.gestion.MotivoAnulacionSolicitud;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerMotivoAnulacionSolicitud extends DaoGenerico<MotivoAnulacionSolicitud, Integer>{

	private static final long serialVersionUID = -7919804930923683604L;

	public PerMotivoAnulacionSolicitud() {
		super(MotivoAnulacionSolicitud.class);
	}

	 @SuppressWarnings("unchecked")
	public boolean usado(MotivoAnulacionSolicitud motivo){
		 Session em;
			Transaction tx = null;
			List<AnulacionSolicitud> lista = null;
		    boolean control= false; 
			em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			try {
				lista = em.createCriteria(AnulacionSolicitud.class)
					.add(Restrictions.eq("motivoanulacionsolicitud",motivo))
					.list();
				if (lista.size()==0) control= false; else control =true;
				tx.commit();
			}catch (Exception e){
				e.printStackTrace();
				tx.rollback();
			}
			
			return control;
		}
}
		 
		 
		 
	