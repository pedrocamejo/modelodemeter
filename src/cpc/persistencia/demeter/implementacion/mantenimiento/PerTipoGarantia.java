package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.mantenimiento.TipoGarantia;  
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerTipoGarantia extends DaoGenerico<TipoGarantia,Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerTipoGarantia() {
		super(TipoGarantia.class);
		// TODO Auto-generated constructor stub
	}

	public TipoGarantia get(Modelo modelo ) {
		// TODO Auto-generated method stub
		
		Session em =SessionDao.getInstance().getCurrentSession( );
		Transaction tx = em.beginTransaction();
	    List lista =  em.createCriteria(TipoGarantia.class).
						add(Restrictions.eq("modelo",modelo)).list();
	   tx.commit();
	   if(lista.size() == 0)
	   {
		   return null;
	   }
	   return (TipoGarantia) lista.get(0);
	}

	public String getUrl(Modelo modelo) {
		// TODO Auto-generated method stub
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<TipoGarantia> lista = em.createCriteria(TipoGarantia.class).add(Restrictions.isNotNull("urlReporte")).add(Restrictions.eq("modelo",modelo)).list();
		tx.commit();
	   return (String) (lista.size() != 0 ? lista.get(0).getUrlReporte():null);
		
	}
	
	public List<Modelo> getModelos()
	{
		Session em =SessionDao.getInstance().getCurrentSession( );
		Transaction tx = em.beginTransaction();

		List<Modelo> lista =  em.createQuery("select tp.modelo from TipoGarantia tp ").list();
		tx.commit();
	   return lista;
		
		
	}
	
}
