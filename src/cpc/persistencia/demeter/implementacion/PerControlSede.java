package cpc.persistencia.demeter.implementacion;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.sigesp.basico.Sede;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerControlSede extends DaoGenerico<ControlSede, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1209109220377343802L;


	public PerControlSede() {
		super(ControlSede.class);
	}

	public ControlSede getControlSede(SedePK idSede){

		Transaction tx = null;
		Sede sede = new PerSede().buscarId(idSede);
		Session em =SessionDao.getInstance().getCurrentSession();
		ControlSede criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (ControlSede) em.createCriteria(ControlSede.class)
				.add(Restrictions.eq("sede",sede))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	
	public ControlSede getControlSede() throws ExcFiltroExcepcion{

		return getAll().get(0);
	}
	
	public ControlSede getPorUnidadAdministrativa( UnidadAdministrativa unidadAdministrativa){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		ControlSede criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (ControlSede) em.createCriteria(ControlSede.class)
				.add(Restrictions.eq("unidadAdministrativa",unidadAdministrativa))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
		
	}

	
}
