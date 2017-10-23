package cpc.persistencia.ministerio.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.dimension.UbicacionEje;
import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionPais;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerEstado extends DaoGenerico<UbicacionEstado, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerEstado() {
		super(UbicacionEstado.class);

	}

	@SuppressWarnings("unchecked")
	public List<UbicacionEstado> getTodos(UbicacionPais pais) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionEstado> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionEstado>) em.createCriteria(UbicacionEstado.class)
				.add(Restrictions.eq("pais",pais))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  
	
	@SuppressWarnings("unchecked")
	public List<UbicacionEstado> getTodos(UbicacionEje eje) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionEstado> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionEstado>) em.createCriteria(UbicacionEstado.class)
				.add(Restrictions.eq("eje",eje))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  
}
