package cpc.persistencia.demeter.implementacion.transporte;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.transporte.Destino;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerDestino extends DaoGenerico<Destino,Integer> {

	public PerDestino( ) {
		super(Destino.class);
		// TODO Auto-generated constructor stub
	}

	public List<Destino> getAll(UbicacionSector sector) throws ExcFiltroExcepcion {
		List<Destino> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createCriteria(Destino.class).add(Restrictions.eq("sector",sector)).list();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}
	
	
	
}


