package cpc.persistencia.ministerio.basico;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.ministerio.gestion.Representante;
import cpc.modelo.ministerio.gestion.UnidadProductiva;

import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerProductorJuridico extends DaoGenerico<ProductorJuridico, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerProductorJuridico() {
		super(ProductorJuridico.class);
	}

	public Productor getDatos(ProductorJuridico entrada){
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		System.out.printf("buscado %d %s \n",entrada.getId(),entrada.getNombres());
		ProductorJuridico docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (ProductorJuridico) em.get(ProductorJuridico.class, entrada.getId());
			for (InstitucionCrediticia item: docu.getFinanciamientos()){em.evict(item);
			}
			for (UnidadProductiva item: docu.getUnidadesproduccion()){em.evict(item);
			}
			for (Telefono item: docu.getTelefonos()){em.evict(item);
			}
			for (Representante item: docu.getRepresentantes()){em.evict(item);
			}
			System.out.printf("encontrado %d %s \n",entrada.getId(),entrada.getNombres());
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}

	public ProductorJuridico getDatos(Productor entrada){
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		System.out.printf("buscado %d %s \n",entrada.getId(),entrada.getNombres());
		ProductorJuridico docu = null;
     	tx = em.beginTransaction();
     	try{
     		
			docu = (ProductorJuridico) em.get(ProductorJuridico.class, entrada.getId());
			for (InstitucionCrediticia item: docu.getFinanciamientos()){em.evict(item);
			}
			for (UnidadProductiva item: docu.getUnidadesproduccion()){em.evict(item);
			}
			for (Telefono item: docu.getTelefonos()){em.evict(item);
			}
			for (Representante item: docu.getRepresentantes()){em.evict(item);
			}
			System.out.printf("encontrado %d %s \n",entrada.getId(),entrada.getNombres());
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public void guardar(ProductorJuridico objeto, Integer indice){
		
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
		   	
		   	if (objeto.getRepresentantes()!= null)
			   	for (Representante item : objeto.getRepresentantes()) {
			   		item = (Representante) em.load(Representante.class, item.getId());
			   		item.addProductor(objeto);
			   		if (item.getId() == null)
			   			em.save(item);
			   		else
			   			em.update(item);
				}
		    em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			
		}
	}
	
	
}
