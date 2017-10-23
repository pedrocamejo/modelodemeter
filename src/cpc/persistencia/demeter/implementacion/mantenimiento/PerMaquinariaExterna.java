package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.mantenimiento.Garantia;
import cpc.modelo.demeter.mantenimiento.MaquinariaExterna;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerMaquinariaExterna extends DaoGenerico<MaquinariaExterna,Integer> {

	public PerMaquinariaExterna() {
		super(MaquinariaExterna.class);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean perteneceplaca(String placa )
	{ 
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        	tx = em.beginTransaction();
       		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("placa",placa)).list();
            tx.commit();
	        return (entities.size() == 0? false:true);  
	}

	public Boolean perteneceserialcarroceria(String serialcarroceria )
	{ 
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
       
        	tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("serialcarroceria",serialcarroceria)).list();
        	
	        tx.commit();

	        return (entities.size() == 0? false:true);  
	}
	public Boolean perteneceserialMotor(String serialMotor )
	{
	 
	
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
       
        Transaction tx = null;
       
        	tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("serialMotor",serialMotor)).list();
        	
	        tx.commit();

	        return (entities.size() == 0? false:true);  
	}

	public Boolean perteneceserie(String serie ) {
		// TODO Auto-generated method stub 
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
       
        Transaction tx = null;
       
        	tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("serie",serie)).list();
        	
	        tx.commit();

	        return (entities.size() == 0? false:true);  
	}
	
	public List<MaquinariaExterna> getMaquinariaConGarantia( ) {
		// TODO Auto-generated method stub 
		
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        	tx = em.beginTransaction();
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.isNotNull("garantia")).list();
            tx.commit();
	        return entities;
	}
	

	public List<MaquinariaExterna> getMaquinariaSinGarantia() {
		// TODO Auto-generated method stub 
		
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
       
        Transaction tx = null;
       
        	tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.isNull("garantia")).list();
        	
	        tx.commit();

	        return entities;
	}

	@SuppressWarnings("unchecked")
	public Boolean perteneceserialcarroceria(String serialcarroceria , Integer idMaquinaria) {
		 
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        	tx = em.beginTransaction();
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("serialcarroceria",serialcarroceria))
        				.add(Restrictions.ne("id",idMaquinaria)).list();        	
	        tx.commit();
	        return (entities.size() == 0? false:true);  
	}

	@SuppressWarnings("unchecked")
	public Boolean perteneceserialMotor(String serialMotor, Integer idMaquinaria ) {
		 
		
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
       
        Transaction tx = null;
       
        	tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("serialMotor",serialMotor))
        					.add(Restrictions.ne("id",idMaquinaria)).list();
            tx.commit();

	        return (entities.size() == 0? false:true);  
	}

	
	@SuppressWarnings("unchecked")
	public Boolean perteneceplaca(String placa, Integer idMaquinaria) {
		 
		
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
       
        Transaction tx = null;
       
        	tx = em.beginTransaction();
        	
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.eq("placa",placa))
        				.add(Restrictions.ne("id",idMaquinaria)).list();
        	
	        tx.commit();

	        return (entities.size() == 0? false:true);  
	}

	@SuppressWarnings("unchecked")
	public List<MaquinariaExterna> getMaqnaConGarantiaEstatus( List<Integer> estatus) {
		// TODO Auto-generated method stub 
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        	tx = em.beginTransaction();
        		entities =	em.createCriteria(Garantia.class).setProjection(Projections.property("maquinaria")).add(Restrictions.in("estatus",estatus)).list();  
	        tx.commit();
	        return entities;
	}

	public List<MaquinariaExterna> getMaquinariaSinCertificado() {
		// TODO Auto-generated method stub
		List<MaquinariaExterna> entities;

		Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        	tx = em.beginTransaction();
        	entities =  em.createQuery(" select m from " +
											" MaquinariaExterna m " +
											" where m.garantia is not null " +
											" and m.garantia.estatus <> 0"+
											" and m not in (select c.maquinariaExterna from CertificadoOrigen c)")
											.list(); //	estatus; // 0 creada  1Generada 
						        	
            tx.commit();
	        return entities;
	}

	public List<MaquinariaExterna> getMaquinariaSinPropietario() {
		// TODO Auto-generated method stub
		List<MaquinariaExterna> entities;
	    Session em =SessionDao.getInstance().getCurrentSession( );
        Transaction tx = null;
        	tx = em.beginTransaction();
        		entities =  em.createCriteria(MaquinariaExterna.class).add(Restrictions.isNull("propietario")).list();
            tx.commit();
	        return entities;  
	}
	


}
