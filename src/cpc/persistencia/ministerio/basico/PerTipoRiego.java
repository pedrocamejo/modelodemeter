package cpc.persistencia.ministerio.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.basico.TipoRiego;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.modelo.ministerio.gestion.UnidadProductivaTipoRiego;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;





public class PerTipoRiego extends DaoGenerico<TipoRiego, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerTipoRiego() {
		super(TipoRiego.class);

	}

	@SuppressWarnings("unchecked")
	public List<UnidadProductivaTipoRiego> obtenerTiposRiegoPorUnidadProductiva(UnidadProductiva unidadProductiva){
		Transaction tx = null;
		List<UnidadProductivaTipoRiego> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
	
		tx = em.beginTransaction();
		
		try{
			salida = em.createCriteria(UnidadProductivaTipoRiego.class).add(Restrictions.eq("unidadProductiva",unidadProductiva)).list();
			tx.commit();			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
		
	}
	
}
