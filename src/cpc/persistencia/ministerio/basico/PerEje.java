package cpc.persistencia.ministerio.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.dimension.UbicacionEje;
import cpc.modelo.ministerio.dimension.UbicacionPais;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerEje extends DaoGenerico<UbicacionEje, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerEje() {
		super(UbicacionEje.class);

	}

	@SuppressWarnings("unchecked")
	public List<UbicacionEje> getTodos(UbicacionPais pais) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionEje> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionEje>) em.createCriteria(UbicacionEje.class)
				.add(Restrictions.eq("pais",pais))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    } 
}
