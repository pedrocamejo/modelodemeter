package cpc.persistencia.demeter.implementacion.gestion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.Solicitud;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerDetalleSolicitud extends DaoGenerico<DetalleSolicitud, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9163448561567355812L;

	public PerDetalleSolicitud() {
		super(DetalleSolicitud.class);		
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleSolicitud> getTodos(Solicitud solicitud){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DetalleSolicitud> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<DetalleSolicitud>) em.createCriteria(DetalleSolicitud.class)
				.add(Restrictions.eq("solicitud", solicitud))
				.list();
			for (DetalleSolicitud detalleSolicitud :  criterio) {
				
				System.out.println(detalleSolicitud.getProducto().getPrecioBase(detalleSolicitud.getSolicitud().getProductor().getTipo() ));
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public List<DetalleSolicitud> getTodasErriquesidas(Solicitud solicitud){
		List<DetalleSolicitud> solicitudes = getTodos(solicitud);
		List<UnidadSolicitada> unidades;
		for (DetalleSolicitud item : solicitudes) {
			unidades =enriqueser(item);
			item.setSolicitado(unidades);
		}
	
		return solicitudes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UnidadSolicitada> enriqueser(DetalleSolicitud solicitud){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UnidadSolicitada> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UnidadSolicitada>) em.createCriteria(UnidadSolicitada.class)
				.add(Restrictions.eq("detalleSolicitud", solicitud))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

}
