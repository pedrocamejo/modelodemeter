package cpc.persistencia.demeter.implementacion.gestion;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.gestion.AnulacionSolicitud;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.UnidadSolicitada;
import cpc.modelo.demeter.sincronizacion.SedeDemeter;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.PerSedeDemeter;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;


public class PerSolicitudMecanizado extends DaoGenerico<SolicitudMecanizado, Long>{

	
	private static final long serialVersionUID = 188670267206954382L;

	public PerSolicitudMecanizado() {
		super(SolicitudMecanizado.class);		
	}
	
	public SolicitudMecanizado getDatos(SolicitudMecanizado entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		SolicitudMecanizado docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (SolicitudMecanizado) em.load(SolicitudMecanizado.class, entrada.getId());
			for (DetalleSolicitud item: docu.getDetalles()){
				for (UnidadSolicitada item2: item.getSolicitado()){
				}
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getMessage());
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getAll(){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{//corehir luego
			
			criterio = em.createCriteria(SolicitudMecanizado.class)
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getAllProject(){
		Transaction tx = null;
		List<SolicitudMecanizado> solicitudes= new ArrayList<SolicitudMecanizado>();
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			
			
			  ProjectionList  projections = Projections.projectionList() ;
				 
				
				 
				 projections.add(Projections.property("id"));
				 projections.add(Projections.property("nroControl"));
				 projections.add(Projections.property("unidadEjecutora.descripcion"));
				 projections.add(Projections.property("productor.nombres"));
				 projections.add(Projections.property("servicio.descripcion"));
				 projections.add(Projections.property("estadosolicitud.estado"));
				 projections.add(Projections.property("fecha"));
			
		/*
		 		
		return nroControl;
	    return fecha;
		return unidadEjecutora.getDescripcion();
		return productor.getNombres();
	return servicio.getDescripcion();
	estadosolicitud.getEstado()
		public String getStrProductor() {
		if (productor== null)
			return "";
		return productor.getNombres();
	}
	
			
public String getStrServicio() {
		if (servicio== null)
			return "";
		return servicio.getDescripcion();
	}
		
		titulo.setMetodoBinder("getStrEstadoSolicitud");
		estadosolicitud.getEstado()
		 
		 */
			     
			     
			     
			     
			 		/*
					return nroControl;
				    return fecha;
					return unidadEjecutora.getDescripcion();
					return productor.getNombres();
					return servicio.getDescripcion();
					estadosolicitud.getEstado()
			     */
			List<Object> criterio =null;
			criterio = em.createCriteria(SolicitudMecanizado.class)
				
				.createAlias("unidadEjecutora", "unidadEjecutora")
				.createAlias("servicio", "servicio")
				.createAlias("productor", "productor")	
				.createAlias("estadosolicitud", "estadosolicitud",CriteriaSpecification.LEFT_JOIN)	
				.setProjection(projections)
				.addOrder(Order.desc("id"))
				.list();
			
			/*
			  id"));
				 nroControl"));
				 unidadEjecutora.getDescripcion()"));
				 productor.getNombres()"));
				 servicio.getDescripcion()"));
				 estadosolicitud.getEstado()"));
				 fecha"));
			 * */
			
			
			
			for (Object object : criterio) {
				
				SolicitudMecanizado solictud = new SolicitudMecanizado();
				Object[] objects=(Object[]) object;
				solictud.setId((Long) objects[0]);
				solictud.setNroControl((String) objects[1]);
				UnidadFuncional unidadFuncional = new UnidadFuncional();
				unidadFuncional.setDescripcion((String) objects[2]);
				if (unidadFuncional.getDescripcion()!=null)
				solictud.setUnidadEjecutora(unidadFuncional);
				Productor productor = new Productor();
				productor.setNombres((String) objects[3]);
				if (productor.getNombres()!=null)
				solictud.setProductor(productor);
				Servicio servicio = new Servicio();
				servicio.setDescripcion((String) objects[4]);
				if (servicio.getDescripcion()!=null)
				solictud.setServicio(servicio);
				EstadoSolicitud estadosolicitud = new EstadoSolicitud();
				estadosolicitud.setEstado((String) objects[5]);
				if (estadosolicitud.getEstado()!=null)
				solictud.setEstadosolicitud(estadosolicitud);
				solictud.setFecha((Date) objects[6]);
				
				solicitudes.add(solictud);
			}
			
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return solicitudes;
	}
	
	public SolicitudMecanizado getSolictud(SolicitudMecanizado solicitudMecanizado){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		solicitudMecanizado =(SolicitudMecanizado) em.get(solicitudMecanizado.getClass(), solicitudMecanizado.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return solicitudMecanizado;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getAllFechas(String inicio, String fin){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			
			criterio = em.createQuery("SELECT  d FROM SolicitudMecanizado d WHERE d.fecha between '"+inicio+" "+"' and '"+ fin +"' order by d.id desc ")
				       .list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	/*
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getAllPrestadas(){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{//correjir luego
			criterio = em.createCriteria(SolicitudMecanizado.class)
				.add(Restrictions.eq("prestada",Boolean.TRUE))
				.add(Restrictions.eq("estadosolicitud",new PerEstadoSolicitud().getculminada()))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getSegunEstado(EstadoSolicitud estado){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{//correjir luego
			criterio = em.createCriteria(SolicitudMecanizado.class)
				.add(Restrictions.eq("estadosolicitud",estado))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getSegunEstadoProductor(EstadoSolicitud estado,Productor productor){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{//correjir luego
			criterio = em.createCriteria(SolicitudMecanizado.class)
				.add(Restrictions.eq("estadosolicitud",estado))
				.add(Restrictions.eq("productor",productor))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	
	
	/*
	@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getAllSinPrestar(){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{//correjir luego
			criterio = em.createCriteria(SolicitudMecanizado.class)
				.add(Restrictions.eq("prestada",Boolean.FALSE))
				.add(Restrictions.eq("aprobada",Boolean.TRUE))
			.add(Restrictions.eq("estadosolicitud",new PerEstadoSolicitud().getrecibida()))
			.addOrder(Order.desc("id"))		
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}*/
	
	/*@SuppressWarnings("unchecked")
	public List<SolicitudMecanizado> getAllSinPrestar(Productor productor){
		Transaction tx = null;
		List<SolicitudMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(SolicitudMecanizado.class)
				.add(Restrictions.eq("prestada",Boolean.FALSE))
				.add(Restrictions.eq("aprobada",Boolean.TRUE))
				.add(Restrictions.eq("productor",productor))
				.addOrder(Order.desc("id"))		
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	*/
	public void guardar(SolicitudMecanizado objeto, Long indice, ControlUnidadFuncional control)  throws ExcFiltroExcepcion{

		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		nuevo( objeto, control, em, tx);
		   	}else{
		   		em.update(objeto);
		   	}
		   	for (DetalleSolicitud item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
		   		for (UnidadSolicitada itemInt : item.getSolicitado()){
		   			if (itemInt.getId() == null)
			   			em.save(itemInt);
			   		else
			   			em.update(itemInt);
		   		}
			}
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Solicitud, "+ e.getMessage());
		}
	}
	
	public void guardar(SolicitudMecanizado objeto, Long indice, String idsede)  throws ExcFiltroExcepcion{
	
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
	    	ControlUnidadFuncional control = new PerControlUnidadFuncional().getControl(objeto.getUnidadEjecutora(),tx);
		   	if (indice == null){
		   		nuevo( objeto, control, em, tx,idsede);
		   	}else{
		   		em.update(objeto);
		   	}
		   	for (DetalleSolicitud item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
		   		for (UnidadSolicitada itemInt : item.getSolicitado()){
		   			if (itemInt.getId() == null)
			   			em.save(itemInt);
			   		else
			   			em.update(itemInt);
		   		}
			}
		   	tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Solicitud, "+ e.getMessage());
		}
	}
	
	public void nuevo( SolicitudMecanizado docu, ControlUnidadFuncional control, Session em, Transaction tx )  throws Exception {
		control.incrementarSolicitud();
		docu.setFecha(new Date()); 
		docu.setAprobada(true);
		docu.setNroControl(control.getSerie()+ Formateador.rellenarNumero(control.getControlSolicitud(),"000000"));
		em.save(docu);
		em.update(control);
	}
	
	public void nuevo( SolicitudMecanizado docu, ControlUnidadFuncional control, Session em, Transaction tx,String idsede )  throws Exception {
	
		docu.setFecha(new Date()); 
		docu.setAprobada(true);
		System.out.println("-----> "+idsede);
		SedeDemeter sededemeter = new PerSedeDemeter().getSede(idsede,tx);
		
		if (sededemeter.isPadre()){
			control.incrementarSolicitud();
			docu.setNroControl(control.getSerie()+ Formateador.rellenarNumero(control.getControlSolicitud(),"000000"));
			em.save(docu);
			em.update(control);
		}
		else{
			Integer numero = sededemeter.incrementarSolicitud();
			String controlm = sededemeter.getPrefijoSolicitudMecanizado();
			docu.setNroControl(controlm+ Formateador.rellenarNumero(numero,"000000"));
			em.save(docu);
			em.update(sededemeter);
			
		} 
		
	}
	public int getHijosActivos(Integer id) {
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		int cantidad = 0;
		Long criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (Long) em.createQuery("SELECT count(n) FROM ContratoMecanizado n INNER JOIN n.solicitud f where f.id = :id")
				.setLong("id", id)
				.uniqueResult();
			cantidad +=criterio;
			System.out.println("primer query"+ cantidad);
			criterio = (Long) em.createQuery("SELECT count(n) FROM OrdenTrabajoMecanizado n INNER JOIN n.solicitud f where f.id = :id")
			.setLong("id", id)
			.uniqueResult();
			cantidad +=criterio;
			System.out.println("segundo "+cantidad);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		} 
		return cantidad;
	}


	public void anular(SolicitudMecanizado docu,AnulacionSolicitud anulacion) throws HibernateException, ExcFiltroExcepcion {
		Transaction tx = null;
		Session em = SessionDao.getInstance().getCurrentSession() ;
		PerAnulacionSolicitud peranulacion = new PerAnulacionSolicitud();
		
		try{ tx= em.beginTransaction();
			  //docu.setanulada(true);
		 	 docu.setAprobada(false);
			 em.update(docu);
			 peranulacion.guardar(anulacion,em,tx);
		//	 em.flush();
			 tx.commit();
			
		 }catch (Exception e) {
			 e.printStackTrace();
			tx.rollback();
			}		
}

	
}
