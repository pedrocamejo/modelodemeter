package cpc.persistencia.demeter.implementacion.gestion;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import cpc.modelo.demeter.gestion.EstadoMaquinariaImpropia;
import cpc.modelo.demeter.gestion.MaquinariaImpropia;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerMaquinariaImpropia extends DaoGenerico<MaquinariaImpropia, Integer>{

	private static final long serialVersionUID = 7747636335829559227L;

	public PerMaquinariaImpropia() {
		super(MaquinariaImpropia.class);
	}

	public MaquinariaImpropia getMaquinaria(String serial) {
		// TODO Auto-generated method stub
		MaquinariaImpropia maquinaria; 
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		maquinaria = (MaquinariaImpropia) em.createCriteria(MaquinariaImpropia.class)
			.add(Restrictions.eq("serialChasis",serial)).uniqueResult();
		tx.commit();
		return maquinaria;
	}

	public MaquinariaImpropia getMaquinariaOtroSerial(String serial) {
		// TODO Auto-generated method stub
		MaquinariaImpropia maquinaria; 
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		maquinaria = (MaquinariaImpropia) em.createCriteria(MaquinariaImpropia.class)
			.add(Restrictions.eq("serialOtro",serial)).uniqueResult();
		tx.commit();
		return maquinaria;
	}
	
	public void guardar(MaquinariaImpropia maquinaria ) throws Exception {
		Session em = SessionDao.getInstance().getCurrentSession();
		try {
			Transaction tx  = em.beginTransaction();
				if(maquinaria.getId() == null)
				{
					em.save(maquinaria);
				}
				else
				{
					em.saveOrUpdate(maquinaria);
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
