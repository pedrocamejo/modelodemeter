package cpc.persistencia.demeter.implementacion;


import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;




public class PerCuentaContable extends DaoGenerico<CuentaContable, String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5650264809149891153L;

	public PerCuentaContable() {
		super(CuentaContable.class);

	}

	public void guardar(CuentaContable objeto, String indice, boolean nuevo) {
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
     	tx = em.beginTransaction();
	   	 if (nuevo == true)
	   		 em.save(objeto);
	   	 else
	   		 em.update(objeto);
	     em.flush();
	   	tx.commit();
	   }
	
}
