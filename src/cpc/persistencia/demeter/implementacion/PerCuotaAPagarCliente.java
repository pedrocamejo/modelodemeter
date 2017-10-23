package cpc.persistencia.demeter.implementacion;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.CuotasAPagarCliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerCuotaAPagarCliente extends DaoGenerico<CuotasAPagarCliente, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2085418376742064046L;

	public PerCuotaAPagarCliente() {
		super(PerCuotaAPagarCliente.class);
		
	}
	
	public Contrato getCuotasAPagar(Contrato entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	Contrato c = (Contrato) em.load(Contrato.class, entrada.getId());
     	try{			
     		
			for (@SuppressWarnings("unused") CuotasAPagarCliente item: c.getCuotasAPagarCliente()){			
				
			}			
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	public void eliminarCuotasApagar(Contrato contrato) throws Exception{		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	
     	try{    		
			for (CuotasAPagarCliente item: contrato.getCuotasAPagarCliente()){			
				em.delete(item);
			}			
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();			
			tx.rollback();			 
			throw e;
		}	
				
	}
	//correjir
	public void eliminarCuotasApagar(Contrato contrato,Session em,Transaction tx) throws Exception{		
		    		
			for (CuotasAPagarCliente item: contrato.getCuotasAPagarCliente()){			
				em.delete(item);
			}			
			
     	}		
	
	
}
