package cpc.persistencia.demeter.implementacion.gestion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.mantenimiento.MaquinariaExterna;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerCliente extends DaoGenerico<Cliente, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8047011068840072133L;

	public PerCliente() {
		super(Cliente.class);
		// TODO Auto-generated constructor stub
	}
	
	
	

	public Boolean perteneceidentidadLegal(String identidadLegal)
	{
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession();
       
        Transaction tx = null;

        tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(Cliente.class).add(Restrictions.eq("identidadLegal",identidadLegal)).list();
        	
	        tx.commit();

	        return (entities.size() == 0? false:true);  
	}

}
