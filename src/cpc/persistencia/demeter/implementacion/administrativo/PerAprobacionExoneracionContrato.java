package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.AprobacionExoneracionContrato;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DetalleExoneracionContrato;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerAprobacionExoneracionContrato extends DaoGenerico<AprobacionExoneracionContrato, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3105501652004103292L;
	
	public PerAprobacionExoneracionContrato() {
		super(AprobacionExoneracionContrato.class);
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("unchecked")
	public List<DetalleExoneracionContrato> getDetallesAprobacion(Date inicio,Date fin){
		List<DetalleExoneracionContrato> criterio= null;	
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();		
		try{
		criterio = em.createCriteria(DetalleExoneracionContrato.class)
				.createAlias("aprobacion", "aprobacion")
		    .add(Restrictions.between("aprobacion.fechaRecepcion", inicio, fin))
		    //.setResultTransformer(Transformers.aliasToBean(DetalleDocumentoFiscal.class))
		    .list();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}	

		return criterio;
	}

}
