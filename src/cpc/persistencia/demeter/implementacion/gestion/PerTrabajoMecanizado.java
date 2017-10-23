package cpc.persistencia.demeter.implementacion.gestion;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleTrabajoRealizadoMecanizado;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.OrdenTrabajo;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.TrabajoRealizado;
import cpc.modelo.demeter.gestion.TrabajoRealizadoMecanizado;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;


public class PerTrabajoMecanizado extends DaoGenerico<TrabajoRealizadoMecanizado, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6835812421168619408L;



	public PerTrabajoMecanizado() {
		super(TrabajoRealizadoMecanizado.class);		
	}
	
	public TrabajoRealizadoMecanizado getDatos(TrabajoRealizadoMecanizado entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		TrabajoRealizadoMecanizado docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (TrabajoRealizadoMecanizado) em.load(TrabajoRealizadoMecanizado.class, entrada.getId());
			for (@SuppressWarnings("unused") DetalleTrabajoRealizadoMecanizado item: docu.getDetalles()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
	public TrabajoRealizadoMecanizado getDatos(TrabajoRealizado entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		TrabajoRealizadoMecanizado docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (TrabajoRealizadoMecanizado) em.get(TrabajoRealizadoMecanizado.class, entrada.getId());
			for (@SuppressWarnings("unused") DetalleTrabajoRealizadoMecanizado item: docu.getDetalles()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
	public void guardar(TrabajoRealizadoMecanizado objeto, Long indice)  throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		em.save(objeto);
		   	}else{
		   		em.update(objeto);
		   	}
		   	for (DetalleTrabajoRealizadoMecanizado item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
			}
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Trabajo Realizado, "+ e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TrabajoRealizadoMecanizado> getTrabajos(OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{  
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<TrabajoRealizadoMecanizado> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (List<TrabajoRealizadoMecanizado>) em.createCriteria(TrabajoRealizadoMecanizado.class)
				.add(Restrictions.eq("ordenTrabajo",orden))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
    }  
	
	public void nuevo( OrdenTrabajoMecanizado docu, ControlUnidadFuncional control, Session em, Transaction tx )  throws Exception {
		control.incrementarSeguimiento();
		docu.setFecha(new Date()); 
		docu.setNroControl(control.getSerie()+ Formateador.rellenarNumero(control.getControlSeguimiento(),"000000"));
		em.save(docu);
		em.save(docu.getLaborOrden());
		em.update(control);
	}

	@SuppressWarnings("unchecked")
	public List<TrabajoRealizadoMecanizado> getTodosProject() throws ExcFiltroExcepcion{
		Transaction tx = null;
	
		List<Object> criterio= null;
		List<TrabajoRealizadoMecanizado>trabajoMecanizados = new ArrayList<TrabajoRealizadoMecanizado>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
	

		
		try{

			
			/*
			
		
		return getOrdenTrabajo().getNroControl();

		

		return fecha;

		
		return getOrdenTrabajoMecanizado().getProductor().getNombres();



		

		return getLabor().getDescripcion();



		return cantidadTrabajo.toString();
	}		


			*/
/*
  ProjectionList proList = Projections.projectionList();
		  proList.add(Projections.property ("tr.id"));
		  proList.add(Projections.property ("tr.fecha"));
		  proList.add(Projections.property ("tr.cantidadTrabajo"));
		  proList.add(Projections.property ("labor.descripcion"));
		
		  proList.add(Projections.property ("orden.nroControl"));
		  
	
		 
		  proList.add(Projections.property("cc.nombres"));
		 
		  
		  
				
			criterio = em.createCriteria(TrabajoRealizadoMecanizado.class,"tr")
			
					.createCriteria ("tr.ordenTrabajo","orden")
					.createCriteria ("tr.ordenTrabajo.productor","cc")
			
					.createAlias("tr.labor", "labor")
					.setProjection(proList).list();
		*/
			
			criterio = em.createQuery("select tr.id,tr.fecha,tr.cantidadTrabajo,labor.descripcion,orden.nroControl,cc.nombres   from TrabajoRealizadoMecanizado tr   join tr.labor as labor  join tr.ordenTrabajo as orden  join orden.productor as cc").list();
			for (Object object : criterio ) {
				TrabajoRealizadoMecanizado trabajo=new TrabajoRealizadoMecanizado(); 
				
				Object[] objects=(Object[]) object;
				
		
						
				trabajo.setId((Long) objects[0]);
				trabajo.setFecha((Date) objects[1]);
				trabajo.setCantidadTrabajo((Double) objects[2]);
				Labor labor = new Labor();
				labor.setDescripcion((String) objects[3]);
				trabajo.setLabor(labor);
				
				OrdenTrabajoMecanizado ordenTrabajo = new OrdenTrabajoMecanizado();
			
				ordenTrabajo.setNroControl((String) objects[4]);
				Productor productor=new Productor();
				productor.setNombres((String) objects[5]);
				ordenTrabajo.setProductor(productor);
				trabajo.setOrdenTrabajo(ordenTrabajo);
				
		trabajoMecanizados.add(trabajo);	
				
		
		
			}
			
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return trabajoMecanizados;
	}
	
	public TrabajoRealizadoMecanizado getTrabajoMecanizadoProject(TrabajoRealizadoMecanizado trabajoRealizadoMecanizado){
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
			trabajoRealizadoMecanizado =(TrabajoRealizadoMecanizado) em.get(trabajoRealizadoMecanizado.getClass(), trabajoRealizadoMecanizado.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return trabajoRealizadoMecanizado;
	}
}
