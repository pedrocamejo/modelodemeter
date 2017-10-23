package cpc.persistencia.demeter.implementacion.basico;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.InteresMora;
import cpc.modelo.demeter.basico.PrecioProducto;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerInteresMora extends DaoGenerico<InteresMora, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5649285453859595020L;

	public PerInteresMora() {
		super(InteresMora.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getAllArticulo(){
		Transaction tx = null;
		List<IProducto> salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = em.createCriteria(ArticuloVenta.class)
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	

	public InteresMora getInteres(Integer id){
		if (id == null) return null;
		return buscarId(id);
	}
	
	@SuppressWarnings("unused")
	public InteresMora getDato(IProducto interes){
		if (interes == null) return null;
		List<UnidadMedida> hijos = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
	
		InteresMora docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (InteresMora) em.load(InteresMora.class, interes.getId());
			for (PrecioProducto index : docu.getPrecios()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	
	
	
}
