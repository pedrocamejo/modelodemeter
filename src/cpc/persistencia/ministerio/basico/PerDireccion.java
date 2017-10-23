package cpc.persistencia.ministerio.basico;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.ministerio.basico.CoordenadaGeografica;
import cpc.modelo.ministerio.basico.LinderoDireccion;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.SuperficieUnidad;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class PerDireccion extends DaoGenerico<UbicacionDireccion, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerDireccion() {
		super(UbicacionDireccion.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UbicacionDireccion> getTodos(UbicacionSector sector) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<UbicacionDireccion> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<UbicacionDireccion>) em.createCriteria(UbicacionDireccion.class)
				.add(Restrictions.eq("sector",sector))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  

	@SuppressWarnings("unchecked")
	public void guardar(UbicacionDireccion objeto, Integer indice){
		
		List<LinderoDireccion> linderosActuales = new ArrayList<LinderoDireccion>();
		List<CoordenadaGeografica> coordenadasActuales = new ArrayList<CoordenadaGeografica>();
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();     	
		   	if (indice == null){		   		
		   		em.save(objeto);
		   	}else{		   		
		   		em.update(objeto);		   	
		   	    linderosActuales = em.createCriteria(LinderoDireccion.class).add(Restrictions.eq("direccion",objeto)).list();	     	
	     	    coordenadasActuales = em.createCriteria(CoordenadaGeografica.class).add(Restrictions.eq("direccion",objeto)).list();
		   	}
		  
			for (LinderoDireccion item : linderosActuales){
		   		if (!objeto.getLinderos().contains(item))
		   			em.delete(item);
		   	}	
			
			
			for (CoordenadaGeografica item : coordenadasActuales){
		   		if (!objeto.getCoordenadasGeograficas().contains(item))
		   			em.delete(item);
		   	}
		   	
		 
			em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			
		}
	}
	
	@SuppressWarnings("unused") 
	public UbicacionDireccion getDatos(UbicacionDireccion entrada){
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		UbicacionDireccion docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (UbicacionDireccion) em.load(UbicacionDireccion.class, entrada.getId());
			for (CoordenadaGeografica item: docu.getCoordenadasGeograficas()){
			}
			for (LinderoDireccion item: docu.getLinderos()){
			}
			for (Cliente item: docu.getPropietarios()){
			}
			for (SuperficieUnidad item: docu.getSuperficies()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
}
