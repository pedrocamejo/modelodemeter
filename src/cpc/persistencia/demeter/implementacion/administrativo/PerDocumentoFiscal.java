package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
 

import cpc.modelo.demeter.administrativo.DocumentoFiscal; 
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerDocumentoFiscal  extends DaoGenerico<DocumentoFiscal, Integer>{ 
	
	
	 
	private static final long serialVersionUID = 5292758999150804483L;
	 

	public PerDocumentoFiscal( ) {
 		super(DocumentoFiscal.class);
		// TODO Auto-generated constructor stub
	}


	public List<DocumentoFiscal> getAll(Cliente cliente) {
		// TODO Auto-generated method stub
		List<DocumentoFiscal> lista = new ArrayList<DocumentoFiscal>();
		Session session = SessionDao.getInstance().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
			lista = session.createCriteria(DocumentoFiscal.class)
						.add(Restrictions.eq("beneficiario",cliente))
						.add(Restrictions.eq("cancelada",false))
						.add(Restrictions.eq("estado.id",1))
						.add(Restrictions.gt("montoSaldo",new Double(0)))
						.addOrder(Order.desc("fecha")).list();
		transaction.commit();
		return lista;
	}


	public void pagar(DocumentoFiscal documentoFiscal) throws Exception {
		// TODO Auto-generated method stub
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
				for (ReciboDocumentoFiscal  ite: documentoFiscal.getRecibos())
				{
					if(ite.getId() == null )
					{
						em.save(ite);
						em.saveOrUpdate(ite.getRecibo());
					}
				}
				em.saveOrUpdate(documentoFiscal);
			em.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new Exception("Error al intentar Alamcenar. "+ e.getMessage(), e.getCause());
		}
		
	}
	
	
 

}
