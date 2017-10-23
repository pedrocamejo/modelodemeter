package cpc.persistencia.sigesp.implementacion;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cpc.modelo.sigesp.basico.Articulo;
import cpc.modelo.sigesp.basico.ProductoCCNU;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerProductoCCNU extends DaoGenerico<ProductoCCNU, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2402764025420281585L;

	public PerProductoCCNU() {
		super(ProductoCCNU.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductoCCNU> getAllProductosCCNUUso(){
		//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<ProductoCCNU> productos = null;
		try{
			productos = em.createQuery("SELECT  d FROM ProductoCCNU d Inner join d.clase c Inner join c.familia f Inner join f.segmentoCCNU s  where s.uso = true order by d.id desc").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return productos;
	}

	@SuppressWarnings("unused")
	public List<Articulo> getArticulos(ProductoCCNU producto){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ProductoCCNU docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (ProductoCCNU) em.load(ProductoCCNU.class, producto.getId());
			for (Articulo item: docu.getArticulos()){em.evict(item);
			}
			tx.commit();
			return  docu.getArticulos();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
     	return null;
	}	
	
}
