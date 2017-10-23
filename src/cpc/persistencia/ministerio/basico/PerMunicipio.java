package cpc.persistencia.ministerio.basico;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.dimension.UbicacionEstado;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerMunicipio extends DaoGenerico<UbicacionMunicipio, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerMunicipio() {
		super(UbicacionMunicipio.class);

	}

	@SuppressWarnings("unchecked")
	public List<UbicacionMunicipio> getTodos(UbicacionEstado estado) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionMunicipio> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionMunicipio>) em.createCriteria(UbicacionMunicipio.class)
				.add(Restrictions.eq("estado",estado))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  
}
