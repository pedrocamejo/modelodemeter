package cpc.persistencia.demeter.implementacion;
 
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.demeter.administrativo.CuentasTipoServicio;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerCuentasTipoServicio extends DaoGenerico<CuentasTipoServicio, Integer>{

	private static final long serialVersionUID = 5490954702964454697L;

	public PerCuentasTipoServicio() {
		super(CuentasTipoServicio.class);
	}
	
	public void guardar(CuentasTipoServicio objeto)  throws Exception{
	   	 if (objeto.getId() == null)
	   		nuevo( objeto);
	   	 else
	   		 super.guardar(objeto, objeto.getId());
	   	
	}
	
	public void nuevo( CuentasTipoServicio objeto)  throws Exception {
		Transaction tx = null;
		Session em ;
		try{
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			em.save(objeto);
		    em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new Exception();
		}
  }
	
	@SuppressWarnings("unchecked")
	public List<CuentasTipoServicio> getBySede(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<CuentasTipoServicio> lista = (List<CuentasTipoServicio>) em.getNamedQuery("CuentasTipoServicio.findAll").list();			
		tx.commit();		
		return lista;
		
	}
	
	


}
