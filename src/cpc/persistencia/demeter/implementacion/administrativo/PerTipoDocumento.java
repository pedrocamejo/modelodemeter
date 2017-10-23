package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoDocumento extends DaoGenerico<TipoDocumentoFiscal, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerTipoDocumento() {
		super(TipoDocumentoFiscal.class);
	}
	
	public TipoDocumentoFiscal getTipoFactura(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoDocumentoFiscal criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoDocumentoFiscal) em.createCriteria(TipoDocumentoFiscal.class)
				.add(Restrictions.eq("tipoFactura",Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoDocumentoFiscal getNotaCredito(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoDocumentoFiscal criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (TipoDocumentoFiscal) em.createCriteria(TipoDocumentoFiscal.class)
				.add(Restrictions.eq("tipoFactura", Boolean.FALSE))
				.add(Restrictions.eq("haber", Boolean.TRUE))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public TipoDocumentoFiscal getNotaDedito(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		TipoDocumentoFiscal criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (TipoDocumentoFiscal) em.createCriteria(TipoDocumentoFiscal.class)
				.add(Restrictions.eq("tipoFactura",Boolean.FALSE))
				.add(Restrictions.eq("haber", Boolean.FALSE))
				
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoDocumentoFiscal> getDocumentoDebitoFactura(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<TipoDocumentoFiscal> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<TipoDocumentoFiscal>) em.createCriteria(TipoDocumentoFiscal.class)
				.add(Restrictions.eq("haber", Boolean.FALSE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
}
