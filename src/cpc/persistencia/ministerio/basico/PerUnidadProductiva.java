package cpc.persistencia.ministerio.basico;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cpc.modelo.ministerio.gestion.UnidadProductivaTipoRiego;




public class PerUnidadProductiva extends DaoGenerico<UnidadProductiva, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerUnidadProductiva() {
		super(UnidadProductiva.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadProductiva> getTodos(UbicacionSector sector) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		List<UbicacionDireccion> direcciones = new PerDireccion().getTodos(sector);
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UnidadProductiva> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UnidadProductiva>) em.createCriteria(UnidadProductiva.class)
				.add(Restrictions.in("ubicacion",direcciones))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  

	@SuppressWarnings("unchecked")
	public List<UnidadProductiva> getTodos(Cliente cliente) throws ExcFiltroExcepcion{  
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<UnidadProductiva> direciones = null;
		try{
			direciones = em.createQuery("FROM UnidadProductiva  WHERE productor.id = :id order by id desc")
			.setInteger("id", cliente.getId())
			.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return direciones;
    }  
	
	@SuppressWarnings("unused")
	public UnidadProductiva getDatos(UnidadProductiva entrada){
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		UnidadProductiva unidadP = null;
     	tx = em.beginTransaction();
     	
     	try{     		
			unidadP = (UnidadProductiva) em.get(UnidadProductiva.class, entrada.getId());			
			for (UnidadProductivaTipoRiego item: unidadP.getTiposRiego()){
				
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return unidadP;
	}
	
	

	@SuppressWarnings("unchecked")
	public void guardar(UnidadProductiva objeto, Integer indice) throws Exception {	
		
		List<UnidadProductivaTipoRiego> tiposDeRiegoActuales = new ArrayList<UnidadProductivaTipoRiego>();		
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null)		   		
		   		em.save(objeto);
		   	else{		   		
		   		em.update(objeto);		   	
		   	    tiposDeRiegoActuales =	em.createCriteria(UnidadProductivaTipoRiego.class).add(Restrictions.eq("unidadProductiva",objeto)).list();
		   	}
		   	
		   	for (UnidadProductivaTipoRiego item : tiposDeRiegoActuales){		   		
		   			if (!objeto.getTiposRiego().contains(item))
		   				em.delete(item);
		   	}
		   			
		   	
		    em.flush();
		   	tx.commit();
				
		}catch (Exception e) {
			tx.rollback();			
			throw e;
		}	
		
	}
	
}
