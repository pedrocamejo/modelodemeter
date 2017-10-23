package cpc.persistencia.sigesp.implementacion;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Tipo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

@SuppressWarnings("serial")
public class PerTipo extends DaoGenerico<Tipo, String>{

	public PerTipo() {
		super(Tipo.class);
	}

	@SuppressWarnings("unchecked")
	public List<Tipo> getPorCategoria(Categoria categoria){
		Session em;
		Transaction tx = null;
		List<Tipo> lista = null;
				
		em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		try {
			lista = (List<Tipo>) em.createCriteria(Tipo.class)
				.add(Restrictions.eq("categoria", categoria))
				.addOrder(Order.asc("codigoTipo"))
				.list();
			
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return lista;
	}
}
