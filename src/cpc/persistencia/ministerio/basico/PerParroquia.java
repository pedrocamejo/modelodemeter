package cpc.persistencia.ministerio.basico;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerParroquia extends DaoGenerico<UbicacionParroquia, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerParroquia() {
		super(UbicacionParroquia.class);
	}

	@SuppressWarnings("unchecked")
	public List<UbicacionParroquia> getTodos(UbicacionMunicipio municipio) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionParroquia> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionParroquia>) em.createCriteria(UbicacionParroquia.class)
				.add(Restrictions.eq("municipio",municipio))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  
	
}
