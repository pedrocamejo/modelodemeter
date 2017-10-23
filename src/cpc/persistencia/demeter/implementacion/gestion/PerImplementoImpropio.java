package cpc.persistencia.demeter.implementacion.gestion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import cpc.modelo.demeter.gestion.EstadoMaquinariaImpropia;
import cpc.modelo.demeter.gestion.ImplementoImpropio;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerImplementoImpropio extends DaoGenerico<ImplementoImpropio, Integer>{

	private static final long serialVersionUID = 7747636335829559227L;

	public PerImplementoImpropio() {
		super(ImplementoImpropio.class);
	}

	public ImplementoImpropio getImplemento(String serial) {
		// TODO Auto-generated method stub
		ImplementoImpropio implemento; 
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		implemento = (ImplementoImpropio) em.createCriteria(ImplementoImpropio.class)
			.add(Restrictions.eq("serialChasis",serial)).uniqueResult();
		tx.commit();
		return implemento;
	}

	public ImplementoImpropio getImplementoSerial(String serial) {
		// TODO Auto-generated method stub
		ImplementoImpropio implemento; 
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		implemento = (ImplementoImpropio) em.createCriteria(ImplementoImpropio.class)
			.add(Restrictions.eq("serialOtro",serial)).uniqueResult();
		tx.commit();
		return implemento;
	}
	
	public void guardar(ImplementoImpropio implemento ) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		try {
			Transaction tx  = em.beginTransaction();
				if(implemento.getId() == null)
				{
					em.save(implemento);
				}
				else
				{
					em.saveOrUpdate(implemento);
				}
			em.flush();
			tx.commit();
		} catch (ConstraintViolationException e) {
			throw new Exception(
					"Error al intentar Almacenar. Existen valores en el Registro actual que no puede repetirse.",
					e.getCause());

		} 
	}
 

	public List<EstadoMaquinariaImpropia> getEstados() {
		// TODO Auto-generated method stub
		List<EstadoMaquinariaImpropia> estados = new ArrayList<EstadoMaquinariaImpropia>();
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
			estados = em.createCriteria(EstadoMaquinariaImpropia.class).list();
		tx.commit();
		return estados;
	}

}
