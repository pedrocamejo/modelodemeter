package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.Tipo;
import cpc.modelo.sigesp.basico.TipoArticuloSIGESP;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerTipoArticulo extends DaoGenerico<TipoArticuloSIGESP, String>{

	
	private static final long serialVersionUID = -3247667908304975310L;

	public PerTipoArticulo() {
		super(TipoArticuloSIGESP.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoArticuloSIGESP> getPorDenominacion(String denominacion){
		Session em;
		Transaction tx = null;
		List<TipoArticuloSIGESP> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<TipoArticuloSIGESP>) em.createCriteria(Tipo.class).add(Restrictions.like("descripcion",denominacion)).list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
}
