package cpc.persistencia.ministerio.basico;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.ProductorNatural;
import cpc.modelo.ministerio.gestion.UnidadProductiva;

import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerProductorNatural extends DaoGenerico<ProductorNatural, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerProductorNatural() {
		super(ProductorNatural.class);
	}

	public ProductorNatural getDatos(ProductorNatural entrada){
		if (entrada == null)
			return null;
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ProductorNatural docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (ProductorNatural) em.get(ProductorNatural.class, entrada.getId());
			for (InstitucionCrediticia item: docu.getFinanciamientos()){em.evict(item);
			}
			for (UnidadProductiva item: docu.getUnidadesproduccion()){em.evict(item);
			}
			for (Telefono item: docu.getTelefonos()){em.evict(item);
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	public ProductorNatural getDatos(Productor entrada){
		if (entrada == null)
			return null;
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		ProductorNatural docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (ProductorNatural) em.get(ProductorNatural.class, entrada.getId());
			for (InstitucionCrediticia item: docu.getFinanciamientos()){em.evict(item);
			}
			for (UnidadProductiva item: docu.getUnidadesproduccion()){em.evict(item);
			}
			for (Telefono item: docu.getTelefonos()){em.evict(item);
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public void guardar(ProductorNatural objeto, Integer indice){
	
		List<Telefono> telefonosActCliente = new ArrayList<Telefono>(); 
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null)		   		
		   		em.save(objeto);
		   	else{		   		
		   		em.update(objeto);
		   		telefonosActCliente = em.createCriteria(Telefono.class).add(Restrictions.eq("cliente",objeto)).list();
		   	}	
		   	
		   	for (Telefono item : telefonosActCliente){
		   		if (!objeto.getTelefonos().contains(item))
		   			em.delete(item);
		   	}		
		    em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			
		}
	}
	
}
