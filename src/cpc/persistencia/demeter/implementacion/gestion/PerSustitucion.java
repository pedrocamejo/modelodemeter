package cpc.persistencia.demeter.implementacion.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.Sustitucion;
import cpc.modelo.demeter.gestion.SustitucionOrden;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.demeter.mantenimiento.RegistroFalla;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMaquinaria;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




public class PerSustitucion extends DaoGenerico<Sustitucion, Integer>{

	private static final long serialVersionUID = 8418744213101138516L;

	public PerSustitucion() {
		super(Sustitucion.class);
	}
	
	@SuppressWarnings("unused") 
	public Sustitucion getDatos(Sustitucion entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Sustitucion sus = null;
     	tx = em.beginTransaction();
     	try{
     		sus = (Sustitucion) em.load(Sustitucion.class, entrada.getId());
			for (SustitucionOrden item: sus.getSustitucionesOrden()){
			}			
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return sus;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void guardar(Sustitucion sustitucion, List<DetalleMaquinariaOrdenTrabajo> detalles, RegistroFalla registroFalla) throws ExcFiltroExcepcion {
		
		Maquinaria maquinaMant = new PerMaquinaria().getPorActivo(sustitucion.getMaquinaAnterior().getActivo());
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();				
		try{
			for (DetalleMaquinariaOrdenTrabajo dmot : detalles){
				dmot.setOperativa(false);			
				em.update(dmot);		    			
			}
			
		
			for (SustitucionOrden sd : sustitucion.getSustitucionesOrden()){
		
				DetalleMaquinariaOrdenTrabajo dmot2 = new DetalleMaquinariaOrdenTrabajo();
				dmot2.setImplemento(sustitucion.getImplementoActual());
				dmot2.setMaquinaria(sustitucion.getMaquinaActual());
				dmot2.setOperador(sustitucion.getOperadorActual());
				dmot2.setOrdenTrabajo(sd.getOrdenTrabajo());
				dmot2.setOperativa(true);
				em.save(dmot2);
			
			}		
			
			
			em.save(sustitucion);
			
			if (registroFalla!=null && !sustitucion.getMaquinaActual().equals(sustitucion.getMaquinaAnterior())){						
				
				if (maquinaMant==null){
					maquinaMant = new Maquinaria();
					String nombre = sustitucion.getMaquinaAnterior().getActivo().getNombre().trim()+" "+sustitucion.getMaquinaAnterior().getActivo().getMarca().getDescripcion().trim()+ " "+sustitucion.getMaquinaAnterior().getActivo().getModelo().getDescripcionModelo().trim();
					maquinaMant.setSerie(sustitucion.getMaquinaAnterior().getIdEjemplarActivo());
					maquinaMant.setNombre(nombre);
					maquinaMant.setActivo(sustitucion.getMaquinaAnterior().getActivo());					
					maquinaMant.setModelo(sustitucion.getMaquinaAnterior().getActivo().getModelo());
					maquinaMant.setTipo(sustitucion.getMaquinaAnterior().getActivo().getTipo());
				
             		em.save(maquinaMant);					
					
				}
				registroFalla.setObjetoMantenimiento(maquinaMant);	
			
				em.save(registroFalla);
				
			}
			
			
			//em.flush();
		    tx.commit();
		    
		    
		}catch (Exception e) {
			e.printStackTrace();
			sustitucion.setId(null);
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al almacenar la Sustitucion");
		}    
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Sustitucion> getAllSustitucion() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<Sustitucion> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Sustitucion.class)
				.addOrder(Order.desc("id"))
						.list();
			for (Sustitucion sustitucion : criterio) {
				Hibernate.initialize(sustitucion.getSustitucionesOrden());	
			}
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	public List<Sustitucion> getAllSustitucionProject() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<Object> criterio= null;
		List<Sustitucion> sustituciones=new ArrayList<Sustitucion>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			 ProjectionList  projections = Projections.projectionList() ;
			 
				
			 
			 projections.add(Projections.property("id"));
			 projections.add(Projections.property("fechaRegistro"));
			 projections.add(Projections.property("operadorAnterior"));
			 projections.add(Projections.property("operadorActual"));
			 projections.add(Projections.property("maquinaAnterior"));
			 projections.add(Projections.property("maquinaActual"));
			 projections.add(Projections.property("causa"));
			 
			criterio = em.createCriteria(Sustitucion.class)
				.addOrder(Order.desc("id"))
				.setProjection(projections)
						.list();
			for (Object object : criterio) {
				
				Sustitucion sustitucion = new Sustitucion();
				Object[] objects=(Object[]) object;
				sustitucion.setId((Integer) objects[0]);
				sustitucion.setFechaRegistro((Date) objects[1]);
				sustitucion.setOperadorAnterior((Trabajador) objects[2]);
				sustitucion.setOperadorActual((Trabajador) objects[3]);
				sustitucion.setMaquinaAnterior((MaquinariaUnidad) objects[4]);
				sustitucion.setMaquinaActual((MaquinariaUnidad) objects[5]);
				sustitucion.setCausa((String) objects[6]);
				sustituciones.add(sustitucion);
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return sustituciones;
	}
	
	

	public Sustitucion  getSustitucionProject(Sustitucion sustitucion){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		sustitucion =(Sustitucion) em.get(sustitucion.getClass(), sustitucion.getId());
		for (SustitucionOrden item: sustitucion.getSustitucionesOrden()){
		}	
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return sustitucion;
	}
	
	
	
}
