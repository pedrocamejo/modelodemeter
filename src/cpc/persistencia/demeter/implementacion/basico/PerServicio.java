package cpc.persistencia.demeter.implementacion.basico;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerServicio extends DaoGenerico<Servicio, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerServicio() {
		super(Servicio.class);
	}
	
	
	@SuppressWarnings("unused")
	public Servicio getDato(Servicio servicio) throws ExcFiltroExcepcion{
		if (servicio == null) return null;
		List<UnidadMedida> hijos = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
	
		Servicio docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (Servicio) em.load(Servicio.class, servicio.getId());
			for (Labor item: docu.getLabores()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("No se puede cargar el dato");
		}	
		return docu;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Servicio> getAll(TipoServicio tipo){
		Transaction tx = null;
		List<Servicio> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(Servicio.class)
				.add(Restrictions.eq("tipoServicio",tipo))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
}
