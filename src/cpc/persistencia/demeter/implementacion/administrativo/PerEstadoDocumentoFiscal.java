package cpc.persistencia.demeter.implementacion.administrativo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerEstadoDocumentoFiscal extends DaoGenerico<EstadoDocumentoFiscal, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -157738949676444354L;

	public PerEstadoDocumentoFiscal() {
		super(EstadoDocumentoFiscal.class);
	}
	
	public EstadoDocumentoFiscal getEstadoNuevaFactura(){
		return buscarId(1);
	}

	public EstadoDocumentoFiscal getActivo(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoDocumentoFiscal criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (EstadoDocumentoFiscal) em.createCriteria(EstadoDocumentoFiscal.class)
				.add(Restrictions.eq("id",1)) //estado Activo 
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;

	}
	
	
	public EstadoDocumentoFiscal getAnulado(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		EstadoDocumentoFiscal criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (EstadoDocumentoFiscal) em.createCriteria(EstadoDocumentoFiscal.class)
				.add(Restrictions.eq("anulado",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;

	}
	
}
