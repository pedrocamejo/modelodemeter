package cpc.persistencia.demeter.implementacion;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;

import cpc.modelo.demeter.mantenimiento.MaquinariaExterna;
import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;

public class PerCliente extends DaoGenerico<Cliente, Integer>{

	private static final long serialVersionUID = -5065333157640084035L;

	public PerCliente() {
		super(Cliente.class);

	}
	@SuppressWarnings("unchecked" )
	public Cliente getDatos(Cliente entrada){
		Transaction tx = null;
		if (entrada == null) return entrada;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Telefono> criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = em.createCriteria(Telefono.class)
				.add(Restrictions.eq("cliente", entrada))
				.list();
			entrada.setTelefonos(criterio);
		
			tx.commit();
		
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return entrada;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAdministrativos(){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Cliente> clientes = null;
		Transaction tx = em.beginTransaction();
		try{
			clientes = em.getNamedQuery("Cliente.findAdministrativos").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return clientes;
	}
	
	public List<Cliente> getAdministrativosproject(){

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Cliente> criterio =new ArrayList<Cliente>();

		ProjectionList  projections = Projections.projectionList() ;
			 projections.add(Projections.property("cliente.id"),"id");
			 projections.add(Projections.property("cliente.identidadLegal"),"identidadLegal");
			 projections.add(Projections.property("cliente.nombres"),"nombres");
			 projections.add(Projections.property("cliente.direccion"),"direccion");
			 projections.add(Projections.property("cliente.activo"),"activo");
			 
		try{
			criterio = em.createCriteria(Cliente.class,"cliente")
					.createAlias("cliente.clienteAdministrativo", "clienteAdministrativo")
					.setProjection(projections)
					.setResultTransformer(Transformers.aliasToBean(Cliente.class))
					.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return criterio;
	}
	
	
	public List<Cliente> getAllProject(){

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Cliente> criterio =new ArrayList<Cliente>();

		ProjectionList  projections = Projections.projectionList() ;
			 projections.add(Projections.property("cliente.id"),"id");
			 projections.add(Projections.property("cliente.identidadLegal"),"identidadLegal");
			 projections.add(Projections.property("cliente.nombres"),"nombres");
			 projections.add(Projections.property("cliente.direccion"),"direccion");
			 projections.add(Projections.property("cliente.activo"),"activo");
			 
		try{
			criterio = em.createCriteria(Cliente.class,"cliente")
					.setProjection(projections)
					.setResultTransformer(Transformers.aliasToBean(Cliente.class))
					.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	public Cliente getclienteproject(Cliente cliente){
		Session em =SessionDao.getInstance().getCurrentSession();
		;
		Transaction tx = em.beginTransaction();
			
		Cliente criterio= new Cliente();
		try{
			criterio = (Cliente) em.get(cliente.getClass(), cliente.getId());
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return criterio;
	}
	


	public Boolean perteneceidentidadLegal(String identidadLegal)
	{
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession();
       
        Transaction tx = null;

        tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(Cliente.class).add(Restrictions.eq("identidadLegal",identidadLegal)).list();
        	
	        tx.commit();

	        return (entities.size() == 0? false:true);  
	}
	
}
