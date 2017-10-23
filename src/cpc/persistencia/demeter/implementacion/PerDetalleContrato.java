package cpc.persistencia.demeter.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerDetalleContrato extends DaoGenerico<DetalleContrato, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -903194176174411624L;


	public PerDetalleContrato() {
		super(DetalleContrato.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleContrato> getAll(Contrato contrato){
		Transaction tx = null;
		
		List<DetalleContrato> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DetalleContrato.class)
				.add(Restrictions.eq("contrato",contrato))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
}
