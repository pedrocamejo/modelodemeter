package cpc.persistencia.demeter.implementacion.mantenimiento;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.sigesp.basico.Activo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerMaquinaria extends DaoGenerico<Maquinaria, Long>{

	
	private static final long serialVersionUID = -3250167924835147970L;

	public PerMaquinaria() {
		super(Maquinaria.class);
	}
	
	
	public Maquinaria getPorActivo(Activo activo){
		Transaction tx = null;
		Maquinaria resultado= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			resultado = (Maquinaria) em.createCriteria(Maquinaria.class).add(Restrictions.eq("activo",activo)).uniqueResult();				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return resultado;
		
	}
}
