package cpc.persistencia.demeter.implementacion.gestion;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.set.CompositeSet.SetMutator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import com.sun.media.sound.EmergencySoundbank;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import cpc.ares.modelo.UnidadFuncional;
import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.demeter.gestion.OperadorOrdenMecanizado;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.Solicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.gestion.TrabajoRealizado;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.administrativo.PerContratoMecanizado;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoContrato;
import cpc.persistencia.demeter.implementacion.basico.PerTipoUnidadMedida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;


public class PerOrdenTrabajoMecanizado extends DaoGenerico<OrdenTrabajoMecanizado, Long>{

	private static final long serialVersionUID = 188670267206954382L;



	public PerOrdenTrabajoMecanizado() {
		super(OrdenTrabajoMecanizado.class);		
	}
	
	public OrdenTrabajoMecanizado getDatos(OrdenTrabajoMecanizado entrada) throws ExcFiltroExcepcion{
		if (entrada == null)
			return null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		OrdenTrabajoMecanizado docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (OrdenTrabajoMecanizado) em.load(OrdenTrabajoMecanizado.class, entrada.getId());
			for (@SuppressWarnings("unused") DetalleOrdenTrabajo item: docu.getDetalles()){em.evict(item);
			}
			for (@SuppressWarnings("unused") TrabajoRealizado item: docu.getTrabajosRealizados()){em.evict(item);
			}
			for (@SuppressWarnings("unused") DetalleMaquinariaOrdenTrabajo item: docu.getEquipos()){em.evict(item);
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
	public List<OrdenTrabajoMecanizado> getAll(EstadoOrdenTrabajo estado) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("estado",estado))
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
	public List<OrdenTrabajoMecanizado> getActivas() throws ExcFiltroExcepcion{
		Transaction tx = null;
		EstadoOrdenTrabajo estado1 = new PerEstadoOrdenTrabajo().getEnEspera();
		EstadoOrdenTrabajo estado2 = new PerEstadoOrdenTrabajo().getInicio();
		EstadoOrdenTrabajo estado3 = new PerEstadoOrdenTrabajo().getCargaDatos();
		List<OrdenTrabajoMecanizado > criterio= null;
	
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			Criterion condicion1 = Restrictions.eq("estado",estado1);
			Criterion condicion2 = Restrictions.eq("estado",estado2);
			Criterion condicion3 = Restrictions.or(condicion1,condicion2);
			Criterion condicion4 = Restrictions.eq("estado",estado3);		
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.or(condicion3,condicion4))
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
	public List<OrdenTrabajoMecanizado> getActivasProject() throws ExcFiltroExcepcion{
		Transaction tx = null;
		EstadoOrdenTrabajo estado1 = new PerEstadoOrdenTrabajo().getEnEspera();
		EstadoOrdenTrabajo estado2 = new PerEstadoOrdenTrabajo().getInicio();
		EstadoOrdenTrabajo estado3 = new PerEstadoOrdenTrabajo().getCargaDatos();
		List<Object> criterio= null;
		List<OrdenTrabajoMecanizado> ordenTrabajoMecanizados= new ArrayList<OrdenTrabajoMecanizado>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			

			  ProjectionList proList = Projections.projectionList();
					  proList.add(Projections.property ("id"),"id");
					  proList.add(Projections.property ("nroControl"),"nroControl");
					  proList.add(Projections.property ("fecha"),"fecha");
					  proList.add(Projections.property ("unidadFuncional.descripcion"),"unidadfuncional");
					  proList.add(Projections.property ("productor.nombres"),"nombres");
					  proList.add(Projections.property ("sector.nombre"),"nombresector");
					  proList.add(Projections.property ("labor.descripcion"),"descripcion");
					  proList.add(Projections.property ("rubro.descripcion"),"rubrodescripcion");
					  proList.add(Projections.property ("estado.descripcion"),"descripcion");
					  
			Criterion condicion1 = Restrictions.eq("estado",estado1);
			Criterion condicion2 = Restrictions.eq("estado",estado2);
			Criterion condicion3 = Restrictions.or(condicion1,condicion2);
			Criterion condicion4 = Restrictions.eq("estado",estado3);		
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
					.add(Restrictions.or(condicion3,condicion4))
					.createAlias("laborOrden", "laborOrden")
					.createAlias("laborOrden.labor", "labor")
					.createAlias("unidadFuncional", "unidadFuncional")
					.createAlias("productor", "productor")
					.createAlias("unidadProductiva", "unidadProductiva")
					.createAlias("unidadProductiva.ubicacion", "ubicacion")
					.createAlias("ubicacion.sector", "sector")
					.createAlias("rubro", "rubro")
					.createAlias("estado", "estado")
					.setProjection(proList)
				.addOrder(Order.desc("id"))
				.list();
			
			for (Object object : criterio ) {
				OrdenTrabajoMecanizado orden=new OrdenTrabajoMecanizado(); 
				
				Object[] objects=(Object[]) object;
				
				/*
				 	  id"),"id
		  nroControl"),"nroControl
		  unidadFuncional.descripcion"),"unidadfuncional
		  productor.nombres"),"nombres
		  sector.nombre"),"nombresector
		  labor.descripcion"),"descripcion
		  rubro.descripcion"),"rubrodescripcion
		  estado.descripcion"),"descripcion
				 
				 */
				
				orden.setId((Long) objects[0]);
				orden.setNroControl((String) objects[1]);
				orden.setFecha((Date) objects[2]);
				
				if (objects[3]!=null){
					cpc.modelo.ministerio.dimension.UnidadFuncional unidadFuncional = new cpc.modelo.ministerio.dimension.UnidadFuncional();
					unidadFuncional.setDescripcion((String) objects[3]);
					orden.setUnidadFuncional(unidadFuncional);	
					}
				
				if (objects[4]!=null){
					Productor productor = new Productor();
				productor.setNombres((String) objects[4]);
				orden.setProductor(productor);
				}
				if (objects[5]!=null){
					UnidadProductiva unidadProductiva = new UnidadProductiva();
					UbicacionDireccion ubicacionDireccion = new UbicacionDireccion();
					UbicacionSector ubicacionSector  = new UbicacionSector();
					ubicacionSector.setNombre((String) objects[5]);
					ubicacionDireccion.setSector(ubicacionSector );
					unidadProductiva.setUbicacion(ubicacionDireccion);
					orden.setUnidadProductiva(unidadProductiva);
					
				}
				if (objects[6]!=null){
					LaborOrdenServicio laborOrdenServicio=new LaborOrdenServicio();
					Labor labor = new Labor();
					labor.setDescripcion((String) objects[6]);
					laborOrdenServicio.setLabor(labor);
					orden.setLaborOrden(laborOrdenServicio);
				}
				if (objects[7]!=null){
					Rubro rubro = new Rubro();
					rubro.setDescripcion((String) objects[7]);
					orden.setRubro(rubro);
					
				}
				if (objects[8]!=null){
					EstadoOrdenTrabajo estado= new EstadoOrdenTrabajo();
					estado.setDescripcion((String) objects[8]);
					orden.setEstado(estado);
				}
				
		ordenTrabajoMecanizados.add(orden);	
				
		
		
			}
			
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return ordenTrabajoMecanizados;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getActivasRecientes() throws ExcFiltroExcepcion{
		Transaction tx = null;
		EstadoOrdenTrabajo estado1 = new PerEstadoOrdenTrabajo().getEnEspera();
		EstadoOrdenTrabajo estado2 = new PerEstadoOrdenTrabajo().getInicio();
		EstadoOrdenTrabajo estado3 = new PerEstadoOrdenTrabajo().getCargaDatos();
		Date			a			=  new Date();	 
		
     a.setMonth(a.getMonth() - 6);
     
		//inicioServicio;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			Criterion condicion1 = Restrictions.eq("estado",estado1);
			Criterion condicion2 = Restrictions.eq("estado",estado2);
			Criterion condicion3 = Restrictions.or(condicion1,condicion2);
			Criterion condicion4 = Restrictions.eq("estado",estado3);		
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.or(condicion3,condicion4))
				.add(Restrictions.ge("inicioServicio", a))
				.addOrder(Order.desc("id"))
				.list();
			em.evict(estado1);
			em.evict(estado2);
			em.evict(estado3);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getOrdenesProduccionCerradas() throws ExcFiltroExcepcion{
		Transaction tx = null;
		EstadoOrdenTrabajo estado = new PerEstadoOrdenTrabajo().getTerminado();
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("estado",estado))
				.add(Restrictions.eq("produccion",Boolean.TRUE))
				.add(Restrictions.eq("transportado",Boolean.FALSE))
				.addOrder(Order.desc("id"))
				.list();
			em.evict(estado);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getAllMenos(EstadoOrdenTrabajo estado) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.ne("estado",estado))
				.addOrder(Order.desc("id"))
				.list();
		
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public void guardar(OrdenTrabajoMecanizado objeto, Long indice, ControlUnidadFuncional control)  throws ExcFiltroExcepcion{
		System.out.println("Guardando");
		try {
		if (indice == null){
	   		System.out.println("Creando");
	   	
	   		if (new PerContratoMecanizado().IsNoRepetida(objeto)){
	   			
					throw new  WrongPolicy("Error creando Orden de Trabajo La orden para ese detalle del contrato ya esta creada");
			
	   			}
	   		} 	
		} catch (WrongPolicy e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
		
		Session em = null;
		
		
		Transaction tx = null;
		try{
			
			
			
			em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		System.out.println("Creando");
		   		nuevo( objeto, control, em, tx);
		   	}else{
		   		System.out.println("Modificando");
		   		em.update(objeto);
		   	}
		   	for (DetalleMaquinariaOrdenTrabajo item : objeto.getEquipos()) {
		   		if (item.getId() == null){
		   			if (item.getOperativa() == null)
		   				item.setOperativa(true);
		   			em.save(item);
		   		}else
		   			em.update(item);
			}
		   	tx.commit();
		
		}catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Orden de Trabajo, "+ e.getMessage());
		}
	}
	
	public void cerrar(OrdenTrabajoMecanizado objeto, Long indice)  throws ExcFiltroExcepcion{
		System.out.println("Guardando");
		EstadoSolicitud estado = new PerEstadoSolicitud().getculminada(); 
		EstadoOrdenTrabajo estadoOrden = new PerEstadoOrdenTrabajo().getTerminado();
		EstadoContrato estadoContratoAd = new PerEstadoContrato().getConcluidoAdministracion();
		Transaction tx = null;
		try{
			if (objeto.getContrato()!= null){
				if (objeto.getContrato().getEstado().getDescripcion().equals(estadoContratoAd.getDescripcion())){
					objeto.setEstado(new PerEstadoOrdenTrabajo().getCargaCompleta());}
				else objeto.setEstado(new PerEstadoOrdenTrabajo().getTerminado());}
			else objeto.setEstado(new PerEstadoOrdenTrabajo().getTerminado());
			
			
		
			if (objeto.getContrato()!= null){
				if (objeto.getContrato().getEstado().getDescripcion().equals(estadoContratoAd.getDescripcion())){}
				else {
				if (objeto.getCantidadRealProducida() !=null ){
					objeto.getLaborOrden().setCantidad(objeto.getCantidadRealProducida());
				if (objeto.getLaborOrden().getLabor().getMedidaCobro().getTipo().equals(new PerTipoUnidadMedida().getAllTipo("AREA"))){
					objeto.getLaborOrden().setCantidad(objeto.getCantidadLaborada());
				} 
				}
				else objeto.getLaborOrden().setCantidad(objeto.getCantidadLaborada());
				objeto.setContrato( new PerContratoMecanizado().getEstadoContrato(objeto.getContrato(), objeto.getLaborOrden()));
				}}
			if (objeto.getSolicitud() != null){
				//corregir luego
				if (objeto.getContrato().getEstado().getDescripcion().equals(estadoContratoAd.getDescripcion())){}
				objeto.getSolicitud().setPrestada(true);
				
				
			}
			Session em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
	   
   			em.update(objeto);
   			if (objeto.getContrato()!= null)
   				em.update(objeto.getContrato());
   			for (DetalleOrdenTrabajo item : objeto.getDetalles()) {
		   		if (item.getId() == null)
		   			em.save(item);
		   		else
		   			em.update(item);
			}
   			
   		
   			
   			if (objeto.getSolicitud() != null){
   				 if (GetTodasTerminadas(objeto.getContrato(),estadoOrden,em,tx)){
   					objeto.getSolicitud().setEstadosolicitud(estado);
 				System.out.println("dispara");
 				em.merge(objeto.getSolicitud());
 			//	em.update(objeto.getSolicitud());
 				}
   				System.out.println("condicion 2 ");
   				}
		   	tx.commit();
	
		}catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Cierre de Orden Trabajo, "+ e.getMessage());
		}
	}
	
	public void anular(OrdenTrabajoMecanizado objeto, Long indice, boolean anularSolicitud)  throws ExcFiltroExcepcion{
		System.out.println("Guardando");
	    EstadoOrdenTrabajo estadocancelado = new PerEstadoOrdenTrabajo().getCancelado();
		EstadoSolicitud estadosol= new PerEstadoSolicitud().getfirmada();   
		EstadoContrato estadocon = new PerEstadoContrato().getPorFirmar();
		EstadoContrato estadoContratoAd = new PerEstadoContrato().getConcluidoAdministracion();
		Transaction tx = null;
		try{
			objeto.setEstado(new PerEstadoOrdenTrabajo().getCancelado());
			if (anularSolicitud){
			if (objeto.getSolicitud() != null){
				//correjir
				objeto.getSolicitud().setAprobada(false);
			  }
			Session em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
	    	
	   		em.update(objeto);
	   		    
	     
	     	if (objeto.getContrato()  != null){
	     		if (objeto.getContrato().getEstado().getDescripcion().equals(estadoContratoAd.getDescripcion())){}
	     		else{
		     	if (GetTodasAnuladas(objeto,estadocancelado,em,tx)){
		     		objeto.getContrato().setEstado(estadocon);
		        em.update(objeto.getContrato());}
	     		} }
	     	
	    	if (objeto.getSolicitud() != null){
	    		if (objeto.getContrato().getEstado().getDescripcion().equals(estadoContratoAd.getDescripcion())){}
	    		else{
		     	if (GetTodasAnuladas(objeto,estadocancelado,em,tx)){
		     		objeto.getSolicitud().setEstadosolicitud(estadosol);
		        em.update(objeto.getSolicitud());}
		        
	    		}}
	   		//em.update(objeto.getContrato());
	   		// numero de detalles de contrato
	   		
	   		
	   		
	   		
	   			
	   				
	   		}
	   		
	   		
	   		
		   	tx.commit();
		   //	em.evict(objeto);
		}catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error Anulando Orden Trabajo, "+ e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OperadorOrdenMecanizado> getListadoOperador(Date inicio, Date fin, UbicacionSector sector) throws ExcFiltroExcepcion{
		List<OperadorOrdenMecanizado> operadores = new ArrayList<OperadorOrdenMecanizado>();
		OperadorOrdenMecanizado operador;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<OrdenTrabajoMecanizado>  maquinarias = null;
		try{
			maquinarias = (List<OrdenTrabajoMecanizado>) em.createQuery("SELECT  ot FROM OrdenTrabajoMecanizado ot INNER JOIN ot.unidadProductiva up INNER JOIN ot.estado es INNER JOIN up.ubicacion ub INNER JOIN ub.sector sec WHERE sec.id = :sector and ot.inicioServicio >= :inicio and ot.inicioServicio <= :fin AND es.activa = :activa AND es.finalizada = :finalizada AND es.detenida = :detenida")
				.setInteger("sector", sector.getId())
				.setDate("inicio", inicio)
				.setDate("fin", fin)
				.setBoolean("activa", Boolean.TRUE)
				.setBoolean("detenida", Boolean.FALSE)
				.setBoolean("finalizada", Boolean.FALSE)
				.list();
			for (OrdenTrabajoMecanizado item : maquinarias) {
				for (DetalleMaquinariaOrdenTrabajo item2 : item.getEquipos()) {
					operador = new OperadorOrdenMecanizado();
					operador.setSector(sector);
					operador.setOrdenTrabajo(item);
					operador.setOperador(item2.getOperador());
					operador.setMaquinaria(item2.getMaquinaria());	
					operadores.add(operador);
			
				}
		
				
			}
		
			
			
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return operadores;
		
	}
	
	
	public void nuevo( OrdenTrabajoMecanizado docu, ControlUnidadFuncional control, Session em, Transaction tx )  throws Exception {
	
		control.incrementarSeguimiento();
		docu.setFecha(new Date()); 
		docu.setNroControl(control.getSerie()+ Formateador.rellenarNumero(control.getControlSeguimiento(),"000000"));
		EstadoContrato criteriocontrato = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%jecuci%"))
				.uniqueResult();
		
		EstadoContrato criteriocontrato2 = (EstadoContrato) em.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%dministra%"))
				.uniqueResult();
		
		
		EstadoSolicitud criteriosol = (EstadoSolicitud) em.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "en proceso"))
				.uniqueResult();

		EstadoOrdenTrabajo criterioCargando = (EstadoOrdenTrabajo) em.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.like("descripcion", "%atos%"))
				.uniqueResult();

		
	
		em.save(docu);
		em.save(docu.getLaborOrden());
		em.update(control);
		
if (docu.getContrato()!= null){
	if (docu.getContrato().getEstado().getDescripcion().equals(criteriocontrato2.getDescripcion())){
		docu.setEstado(criterioCargando);
	}
	else{
	docu.getContrato().setEstado(criteriocontrato);
em.update(docu.getContrato());}

	System.out.println("estado contrato"+ docu.getContrato().getEstadoString());	
	}



if (docu.getSolicitud() != null){
	
if (docu.getContrato().getEstado().getDescripcion().equals(criteriocontrato2.getDescripcion())){
		
	}else {
	docu.getSolicitud().setEstadosolicitud(criteriosol);


em.update(docu.getSolicitud());}

	System.out.println("estado solicitud"+ docu.getSolicitud().getEstadosolicitud());
}
		
		
	}

	
	
	/*
	// Lista todas las maquinarias en ordenes activas, no finalizadas ni detenidas, sin tomar en cuenta la orden actual
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getOrdenesTrabajoPorMaquinaria(MaquinariaUnidad maquinaria, OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
					.createAlias("equipos", "equipos")
				.add(Restrictions.eq("equipos.maquinaria",maquinaria))
				.createAlias("estado", "estado")
				.add(Restrictions.eq("estado.activa",Boolean.TRUE))
				.add(Restrictions.eq("estado.detenida",Boolean.FALSE))
				.add(Restrictions.eq("estado.finalizada",Boolean.FALSE))
				.add(Restrictions.ne("id", orden.getId()))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;		
	}*/
	// Lista todas las maquinarias en ordenes activas, no finalizadas ni detenidas, sin tomar en cuenta la orden actual
	public List<OrdenTrabajoMecanizado> getOrdenesTrabajoPorMaquinaria(MaquinariaUnidad maquinaria, OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
					.createAlias("equipos", "equipos")
				.add(Restrictions.eq("equipos.maquinaria",maquinaria))
				.createAlias("estado", "estado")
				.add(Restrictions.eq("estado.activa",Boolean.TRUE))
				.add(Restrictions.eq("estado.detenida",Boolean.FALSE))
				.add(Restrictions.eq("estado.finalizada",Boolean.FALSE))
				.add(Restrictions.ne("id", orden.getId()))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;		
	}
	
	public List<OrdenTrabajoMecanizado> getOrdenesTrabajoPorImplemento(ImplementoUnidad implemento, OrdenTrabajoMecanizado orden) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
					.createAlias("equipos", "equipos")
				.add(Restrictions.eq("equipos.implemento",implemento))
				.createAlias("estado", "estado")
				.add(Restrictions.eq("estado.activa",Boolean.TRUE))
				.add(Restrictions.eq("estado.detenida",Boolean.FALSE))
				.add(Restrictions.eq("estado.finalizada",Boolean.FALSE))
				.add(Restrictions.ne("id", orden.getId()))
				.addOrder(Order.desc("id"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;		
	}
	
//	@SuppressWarnings("unchecked")
//	public List<OrdenTrabajoMecanizado> getOrdenesTrabajoMecanizado(Date inicio, Date fin, UbicacionSector sector){
//		Transaction tx = null;
//		List<OrdenTrabajoMecanizado> criterio = null;
//		Session em = SessionDao.getInstance().getCurrentSession();
//		tx = em.beginTransaction();
//		try {
//			String sql = "SELECT DISTINCT otm, df " +
//					" FROM OrdenTrabajoMecanizado otm " +
//					" LEFT JOIN FETCH otm.contrato = DocumentoFiscal.contrato ";
//			
//			
//			Criteria cr = em.createCriteria(OrdenTrabajoMecanizado.class);
//			cr.createAlias("estado", "estado");
//			cr.add(Restrictions.eq("estado.activa", true));
//			cr.add(Restrictions.eq("estado.finalizada", true));
//			cr.add(Restrictions.between("fecha", inicio, fin));			
////			if (sector != null)
////				cr.add(Restrictions.eq("idSector", sector.getId())).list();
//			criterio = cr.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//		}
//		return criterio;
//	}

	
	
	/*@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getAll(ObjetoMantenimiento bienProduccion) throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<OrdenTrabajoMecanizado> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("estado",estado))
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
	
	Boolean GetTodasTerminadas(Contrato contrato,	EstadoOrdenTrabajo estado, Session em,Transaction tx){
	
		 //corre
		
		int detalles = contrato.getDetallesContrato().size();
		System.out.println("tamaño del contrato" + detalles); 
		 Boolean terminado = false;
		@SuppressWarnings("unchecked")
		List<OrdenTrabajoMecanizado> criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("contrato",contrato))
				.add(Restrictions.eq("estado",estado))
				.addOrder(Order.desc("id"))
				.list();
	
		if (detalles==(criterio.size())) terminado= true;
		System.out.println("condicion dd "+ terminado);
		for (OrdenTrabajoMecanizado ordenTrabajoMecanizado : criterio) {
			em.evict(ordenTrabajoMecanizado);
				}

		em.evict(criterio);
		em.evict(contrato);
		em.flush();
		
		return terminado; 
	}
	
	Boolean GetTodasAnuladas(OrdenTrabajoMecanizado docu ,	EstadoOrdenTrabajo estado,Session em,Transaction tx){
	 
	
		 Boolean anulado = false;
		@SuppressWarnings("unchecked")
		List<OrdenTrabajoMecanizado> criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("contrato",docu.getContrato()))
				.add(Restrictions.not(Restrictions.eq("estado",estado)))
				.add(Restrictions.not(Restrictions.eq("id",docu.getId())))
				.addOrder(Order.desc("id"))
				.list();
		
		if (criterio.size()==0) anulado= true;
		for (OrdenTrabajoMecanizado ordenTrabajoMecanizado : criterio) {
			em.evict(ordenTrabajoMecanizado);
				}

		em.evict(criterio);
		em.flush();
		return anulado; 
	}
	
	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getTodosProject() throws ExcFiltroExcepcion{
		Transaction tx = null;
	
		List<Object> criterio= null;
		List<OrdenTrabajoMecanizado> ordenTrabajoMecanizados = new ArrayList<OrdenTrabajoMecanizado>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
	

		
		try{
	/*
	 
		return nroControl;
	
		return fecha.toString();

		return unidadFuncional.getDescripcion();
		return productor.getNombres();

			UbicacionSector sector = getUnidadProductiva().getSector();
			return sector.getNombre();
			return labor.getDescripcion();
		return rubro.getDescripcion();
		return estado.getDescripcion();
	 * */
			
			

  ProjectionList proList = Projections.projectionList();
		  proList.add(Projections.property ("id"),"id");
		  proList.add(Projections.property ("nroControl"),"nroControl");
		  proList.add(Projections.property ("fecha"),"fecha");
		  proList.add(Projections.property ("unidadFuncional.descripcion"),"unidadfuncional");
		  proList.add(Projections.property ("productor.nombres"),"nombres");
		  proList.add(Projections.property ("sector.nombre"),"nombresector");
		  proList.add(Projections.property ("labor.descripcion"),"descripcion");
		  proList.add(Projections.property ("rubro.descripcion"),"rubrodescripcion");
		  proList.add(Projections.property ("estado.descripcion"),"descripcion");
		  
				
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class).
					setProjection(proList)
					.createAlias("laborOrden", "laborOrden")
					.createAlias("laborOrden.labor", "labor")
					.createAlias("unidadFuncional", "unidadFuncional")
					.createAlias("productor", "productor")
					.createAlias("unidadProductiva", "unidadProductiva")
					.createAlias("unidadProductiva.ubicacion", "ubicacion")
					.createAlias("ubicacion.sector", "sector")
					.createAlias("rubro", "rubro")
					.createAlias("estado", "estado")
					//.setResultTransformer(Transformers.aliasToBean(OrdenTrabajoMecanizado.class))
					.list();
					//setFetchMode("laborOrden.labor", FetchMode.SELECT).

			
				//	createAlias("i.laborOrden", "es", CriteriaSpecification.FULL_JOIN).
					//add(Property.forName("i.laborOrden").eq(subquery)).
					
			
		/*	for (OrdenTrabajoMecanizado ordenTrabajoMecanizado : criterio) {
			List<LaborOrdenServicio> a=	em.createCriteria(LaborOrdenServicio.class).add(Restrictions.eq("orden",ordenTrabajoMecanizado)).list() ;
				ordenTrabajoMecanizado.setLaborOrden(a.get(0));
				
//			
//			}*/
//		//		createAlias("i.laborOrden", "es", CriteriaSpecification.FULL_JOIN).
//		//createAlias("i.laborOrden.labor", "i", CriteriaSpecification.FULL_JOIN)
//			//		.add(Expression.eq("laborOrden", subquery)).//setProjection(proList).
//			//	setResultTransformer(Transformers.aliasToBean(OrdenTrabajoMecanizado.class)).
//		//	 em.get(LaborOrdenServicio.class, 1);
//		
	//	criterio = em.createQuery("select ot.laborOrden.labor , ot.nroControl, ot.fecha , ot.unidadFuncional, ot.estado , ot.rubro , ot.productor  from cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado  ot INNER JOIN ot.unidadProductiva up INNER JOIN ot.estado es INNER JOIN up.ubicacion ub INNER JOIN ub.sector sec").list();
	
			for (Object object : criterio ) {
				OrdenTrabajoMecanizado orden=new OrdenTrabajoMecanizado(); 
				
				Object[] objects=(Object[]) object;
				
				/*
				 	  id"),"id
		  nroControl"),"nroControl
		  unidadFuncional.descripcion"),"unidadfuncional
		  productor.nombres"),"nombres
		  sector.nombre"),"nombresector
		  labor.descripcion"),"descripcion
		  rubro.descripcion"),"rubrodescripcion
		  estado.descripcion"),"descripcion
				 
				 */
				
				orden.setId((Long) objects[0]);
				orden.setNroControl((String) objects[1]);
				orden.setFecha((Date) objects[2]);
				
				if (objects[3]!=null){
					cpc.modelo.ministerio.dimension.UnidadFuncional unidadFuncional = new cpc.modelo.ministerio.dimension.UnidadFuncional();
					unidadFuncional.setDescripcion((String) objects[3]);
					orden.setUnidadFuncional(unidadFuncional);	
					}
				
				if (objects[4]!=null){
					Productor productor = new Productor();
				productor.setNombres((String) objects[4]);
				orden.setProductor(productor);
				}
				if (objects[5]!=null){
					UnidadProductiva unidadProductiva = new UnidadProductiva();
					UbicacionDireccion ubicacionDireccion = new UbicacionDireccion();
					UbicacionSector ubicacionSector  = new UbicacionSector();
					ubicacionSector.setNombre((String) objects[5]);
					ubicacionDireccion.setSector(ubicacionSector );
					unidadProductiva.setUbicacion(ubicacionDireccion);
					orden.setUnidadProductiva(unidadProductiva);
					
				}
				if (objects[6]!=null){
					LaborOrdenServicio laborOrdenServicio=new LaborOrdenServicio();
					Labor labor = new Labor();
					labor.setDescripcion((String) objects[6]);
					laborOrdenServicio.setLabor(labor);
					orden.setLaborOrden(laborOrdenServicio);
				}
				if (objects[7]!=null){
					Rubro rubro = new Rubro();
					rubro.setDescripcion((String) objects[7]);
					orden.setRubro(rubro);
					
				}
				if (objects[8]!=null){
					EstadoOrdenTrabajo estado= new EstadoOrdenTrabajo();
					estado.setDescripcion((String) objects[8]);
					orden.setEstado(estado);
				}
				
		ordenTrabajoMecanizados.add(orden);	
				
		
		
			}
			
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return ordenTrabajoMecanizados;
	}
	
	public OrdenTrabajoMecanizado getOrdenTrabajoMecanizadoProject(OrdenTrabajoMecanizado ordenTrabajoMecanizado){
		
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
			ordenTrabajoMecanizado =(OrdenTrabajoMecanizado) em.get(ordenTrabajoMecanizado.getClass(), ordenTrabajoMecanizado.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return ordenTrabajoMecanizado;
	}

	@SuppressWarnings({ "unchecked", })
	public List<Object[]> getReporteConsolidado(Date fechainicio,Date fechafin) {
		// TODO Auto-generated method stub
		List<HashMap<String, Object>> listaMapa = new ArrayList<HashMap<String, Object>>();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		List<Object[]>  listaReporte =new ArrayList<Object[]>();
		try{
			String consulta2 ="select s.dtm_fecha as fechaSolicitud,s.str_nrocon as controlSolicitud,cli.str_cedurif as ceduRif,"
							+ " cli.str_nombre as nombre,rubro.str_descripcion as rubro,if.str_descripcion as financiamiento,"
							+ " sec.str_descripcion as sector,parr.str_descripcion as parroquia,mun.str_descripcion as municipio,"
							+ " est.str_descripcion as estado,producto.str_descripcion as labor,sd.dbl_pase as pases,sdu1.dbl_cantidad as cantidad1,"
							+ " um1.str_descripcion as unidad1,sdu2.dbl_cantidad as cantidad2,um2.str_descripcion as unidad2,c.int_nrocontrol as numeroContrato,"
							+ " c.dtm_fecha as fechaContrato,os.str_nrocon as ordenControl,osm.dbl_produccionreal as produccionReal,"
							+ " osm.dbl_trabajofisico as superficieTrabajada,osm.dbl_trabajolabor as totalTrabajado,sum(trabajomecanizado.dbl_cantidadtrabajo) as estimadoTrabajado,"
							+ " sum(trabajomecanizado.dbl_cantidadproduccion) as estimadoProducido,d.int_nrocontrol as numeroFactura,dd.dbl_cantidad as cantidadFacturada,"
							+ " dd.dbl_preciounitario as PrecioBase,dd.dbl_cantidad-dc.dbl_cantidad as solicitadoFactudado,"
							+ " case when osm.dbl_produccionreal is null then (dd.dbl_cantidad-osm.dbl_trabajolabor)"
							+ " else (dd.dbl_cantidad-osm.dbl_produccionreal) end as cerradofacturado,"
							+ " array_to_string(array_agg((select distinct reci.str_control"
							+ " from administracion.tbl_dem_recibo reci where  rdf.int_idrecibo = reci.seq_idrecibo"
							+ " group by reci.str_control)) , ' , ')"
							+ " as recibos,"
							+ " array_to_string(array(SELECT DISTINCT UNNEST(array_agg("
							+ " (select distinct (trabajadores.str_cedula || ' '||trabajadores.str_nombres || ' ' ||trabajadores.str_apellidos) as algo from"
							+ " basico.tbl_dem_trabajador trabajadores where detalleOrden.int_idoperador=trabajadores.seq_idtrabajador group by algo"
							+ " )) )), ' , ') as operador,"
							+ " array_to_string(array(SELECT DISTINCT UNNEST(array_agg "
							+ " (iactivo.denact||' codigo: '|| idta.codact || 'idactivo:  ' ||idta.ideact ||' Serial '||idta.seract || 'Chapa: '||idta.idchapa||' , '))"
							+ " ), ' , ') as implemento ,"
							+ " case when  maqu.int_idmaquinaimpropia is null then ("
							+ " array_to_string(array(SELECT DISTINCT UNNEST(array_agg ((mactivo.denact||' codigo: '|| mdta.codact || 'idactivo:  '"
							+ " ||mdta.ideact ||' Serial '||mdta.seract || 'Chapa: '||mdta.idchapa)))),' , '))"
							+ " else  (array_to_string(array(SELECT DISTINCT UNNEST(array_agg (('Nombre: '||"
							+ " maqimp.str_nombre ||'Procedencia: '|| maqimp.str_procedencia||'Serial Chasis: '|| maqimp.str_serialchasis||'Serial Otro: '||"
							+ " maqimp.str_serialotro )))),' , ')"
							+ " ) end  as maquinaria,"
							+ " estadoso.str_descripcion as estadosolicitud,"
							+ " estadoorden.str_descripcion as estadoorden,"
							+ " estadocontra.str_descripcion as estadocontrato,"
							+ " sm.dtm_fechaatencion as fechaatencion "
							+ " from gestion.tbl_dem_solicitud s"
							+ " join gestion.tbl_dem_solicitudmecanizado sm on sm.int_idsolicitud =s.seq_idsolicitud"
							+ " join basico.tbl_dem_rubro rubro on rubro.seq_idrubro =sm.int_idrubro"
							+ " join ministerio.tbl_dem_institucionfinanciera if on if.seq_idinstitucionf =sm.int_idfinanciamiento"
							+ " join ministerio.tbl_dem_unidadproductiva up on up.seq_idunidadproductiva=sm.int_idunidadproduccion"
							+ " join ministerio.tbl_dem_direccion dire on dire.seq_iddireccion= up.int_iddireccion"
							+ " join ministerio.tbl_dem_sector sec on sec.seq_idsector = dire.int_idsector"
							+ " join ministerio.tbl_dem_parroquia parr on parr.seq_idparroquia= sec.int_idparroquia"
							+ " join ministerio.tbl_dem_municipio mun on parr.int_idmunicipio = mun.seq_idmunicipio"
							+ " join  ministerio.tbl_dem_estado est on est.seq_idestado = mun.int_idestado"
							+ " join ministerio.tbl_dem_productor pro on pro.seq_idproductor = s.int_idcliente"
							+ " join tbl_dem_clientes cli on cli.seq_idcliente = pro.seq_idproductor"
							+ " join gestion.tbl_dem_solicituddetalle sd on s.seq_idsolicitud = sd.int_idsolicitud"
							+ " join basico.tbl_dem_producto producto on producto.seq_idproducto = sd.int_idproducto"
							+ " join gestion.tbl_dem_solicituddetalleunidad sdu1 on sd.seq_idrenglon =sdu1.int_idrenglon"
							+ " and sdu1.seq_idrenglonunidad in (select seq_idrenglonunidad from gestion.tbl_dem_solicituddetalleunidad"
							+ "	where sd.seq_idrenglon = int_idrenglon limit 1 )"
							+ " join tbl_dem_unidad_medidas um1 on um1.seq_idumedida =sdu1.int_idunidad"
							+ " left join gestion.tbl_dem_solicituddetalleunidad sdu2 on sd.seq_idrenglon =sdu2.int_idrenglon"
							+ "	and sdu1.seq_idrenglonunidad <>sdu2.seq_idrenglonunidad"
							+ " left join tbl_dem_unidad_medidas um2 on um2.seq_idumedida =sdu2.int_idunidad"
							+ " left outer join	 administracion.tbl_dem_contratomecanizado   cm on cm.int_idsolicitudmecanizado = s.seq_idsolicitud"
							+ " left outer join	 administracion.tbl_dem_contrato c on c.seq_idcontrato  =cm.int_idcontrato"
							+ " left outer join  gestion.tbl_dem_orden_servicio os on os.int_idsolicitud = s.seq_idsolicitud"
							+ " left outer join  gestion.tbl_dem_orden_servicio_mecanizado osm on osm.int_idordenservicio  = os.seq_idordenservicio"
							+ " left outer join gestion.tbl_dem_trabajo_orden_servicio trabajo on osm.int_idordenservicio= trabajo.int_idordenservicio"
							+ " left outer join gestion.tbl_dem_trabajo_orden_servicio_mecanizado trabajomecanizado on  trabajo.seq_idtrabajoorden= trabajomecanizado.int_idtrabajoorden"
							+ " left outer join  administracion.tbl_dem_documentofiscal d on d.int_idcontrato =cm.int_idcontrato"
							+ " left outer join	  administracion.tbl_dem_documentofiscaldetalle dd on dd.int_idservicio = sd.int_idproducto and dd.int_iddocumento =d.seq_iddocumento"
							+ " left outer join	 administracion.tbl_dem_detallecontrato dc on dc.int_idproducto= sd.int_idproducto and dc.int_idcontrato=cm. int_idcontrato"
							+ " left outer join	 gestion.tbl_dem_labor_orden_servicio_mecanizado lom on lom.int_idlabor=sd.int_idproducto and lom.int_idordenservicio =os.seq_idordenservicio"
							+ " left outer join  administracion.tbl_dem_recibodocumentofiscal rdf on rdf.int_iddocumentofiscal =d.seq_iddocumento"
							+ " left outer join gestion.tbl_dem_detalle_maquinaria_orden_trabajo detalleOrden on detalleOrden.int_idordenservicio = os.seq_idordenservicio"
							+ " left outer join gestion.tbl_dem_implemento_unidad impu   on detalleOrden.int_idimplemento=impu.seq_idimplemento and detalleOrden.int_idordenservicio = os.seq_idordenservicio"
							+ " left outer join sigesp.saf_dta idta on impu.str_codempmaq=idta.codemp and impu.str_idactivomaq= idta.codact and  impu.str_idejemplarmaq=idta.ideact"
							+ " left outer join sigesp.saf_activo iactivo on iactivo.codemp=idta.codemp and iactivo.codact=idta.codact"
							+ " left outer join gestion.tbl_dem_maquinaria_unidad maqu   on detalleOrden.int_idmaquinaria=maqu.seq_idmaquinaria and detalleOrden.int_idordenservicio = os.seq_idordenservicio"
							+ " left outer join sigesp.saf_dta mdta on maqu.str_codempmaq=mdta.codemp and maqu.str_idactivomaq= mdta.codact and  maqu.str_idejemplarmaq=mdta.ideact"
							+ " left outer join sigesp.saf_activo mactivo on mactivo.codemp=mdta.codemp and mactivo.codact=mdta.codact"
							+ " left outer join gestion.tbl_dem_maquinaria_impropia maqimp on maqimp.seq_idmaquinariaimpropia =maqu.int_idmaquinaimpropia"
							+ " left outer join basico.tbl_dem_trabajador trabajador  on detalleOrden.int_idoperador=trabajador.seq_idtrabajador and detalleOrden.int_idordenservicio = os.seq_idordenservicio"
							+ " left outer join gestion.tbl_dem_estado_solicitud  estadoso on estadoso.seq_idestado= s.int_estado"
							+ " left outer join gestion.tbl_dem_estadoordentrabajo estadoorden on estadoorden.seq_idestado=os.int_idestadoorden"
							+ " left outer join  administracion.tbl_dem_estado_contrato estadocontra on estadocontra.seq_idestadocontrato = c.int_estado"
							+ " where s.dtm_fecha BETWEEN :fechainicio and :fechafin"
							+ " group by s.dtm_fecha , s.str_nrocon ,cli.str_cedurif, cli.str_nombre, rubro.str_descripcion, if.str_descripcion,"
							+ " sec.str_descripcion, parr.str_descripcion,mun.str_descripcion,est.str_descripcion,producto.str_descripcion,"
							+ " sd.dbl_pase,sdu1.dbl_cantidad,um1.str_descripcion,sdu2.dbl_cantidad,um2.str_descripcion,c.int_nrocontrol,c.dtm_fecha,"
							+ " os.str_nrocon,osm.dbl_produccionreal ,osm.dbl_trabajofisico ,osm.dbl_trabajolabor ,d.int_nrocontrol,dd.dbl_cantidad,dd.dbl_preciounitario,"
							+ " dc.dbl_cantidad,maqu.int_idmaquinaimpropia,estadoso.str_descripcion ,estadoorden.str_descripcion,estadocontra.str_descripcion,"
							+ " sm.dtm_fechaatencion";
					
			
			
			
			String consulta="select s.dtm_fecha as fechaSolicitud,s.str_nrocon as controlSolicitud,"
					+ " cli.str_cedurif as ceduRif,cli.str_nombre as nombre,rubro.str_descripcion as rubro,"
					+ " if.str_descripcion as financiamiento,sec.str_descripcion as sector,parr.str_descripcion as parroquia,"
					+ " mun.str_descripcion as municipio,est.str_descripcion as estado,producto.str_descripcion as labor,"
					+ " sd.dbl_pase as pases,sdu1.dbl_cantidad as cantidad1,um1.str_descripcion as unidad1,sdu2.dbl_cantidad as cantidad2,"
					+ " um2.str_descripcion as unidad2,c.int_nrocontrol as numeroContrato,c.dtm_fecha as fechaContrato,os.str_nrocon as ordenControl,"
					+ " osm.dbl_produccionreal as produccionReal,osm.dbl_trabajofisico as superficieTrabajada,osm.dbl_trabajolabor as totalTrabajado,"
					+ " sum(trabajomecanizado.dbl_cantidadtrabajo) as estimadoTrabajado,sum(trabajomecanizado.dbl_cantidadproduccion) as estimadoProducido,"
					+ " d.int_nrocontrol as numeroFactura,dd.dbl_cantidad as cantidadFacturada,dd.dbl_preciounitario as PrecioBase,"
					+ " dd.dbl_cantidad-dc.dbl_cantidad as solicitadoFactudado,"
					+ " case when osm.dbl_produccionreal is null then (dd.dbl_cantidad-osm.dbl_trabajolabor)	else (dd.dbl_cantidad-osm.dbl_produccionreal)"
					+ " end as cerradoFacturado, "
					+ " array_to_string( array_agg((select distinct reci.str_control  from administracion.tbl_dem_recibo reci where  "
					+ " rdf.int_idrecibo = reci.seq_idrecibo group by reci.str_control) ),' , ') as recibos"
					+ " from gestion.tbl_dem_solicitud s "
					+ " join gestion.tbl_dem_solicitudmecanizado sm on sm.int_idsolicitud =s.seq_idsolicitud"
					+ " join basico.tbl_dem_rubro rubro on rubro.seq_idrubro =sm.int_idrubro "
					+ " join ministerio.tbl_dem_institucionfinanciera if on if.seq_idinstitucionf =sm.int_idfinanciamiento"
					+ " join ministerio.tbl_dem_unidadproductiva up on up.seq_idunidadproductiva=sm.int_idunidadproduccion "
					+ " join ministerio.tbl_dem_direccion dire on dire.seq_iddireccion= up.int_iddireccion"
					+ " join ministerio.tbl_dem_sector sec on sec.seq_idsector = dire.int_idsector "
					+ " join ministerio.tbl_dem_parroquia parr on parr.seq_idparroquia= sec.int_idparroquia"
					+ " join ministerio.tbl_dem_municipio mun on parr.int_idmunicipio = mun.seq_idmunicipio "
					+ " join  ministerio.tbl_dem_estado est on est.seq_idestado = mun.int_idestado"
					+ " join ministerio.tbl_dem_productor pro on pro.seq_idproductor = s.int_idcliente "
					+ " join tbl_dem_clientes cli on cli.seq_idcliente = pro.seq_idproductor"
					+ " join gestion.tbl_dem_solicituddetalle sd on s.seq_idsolicitud = sd.int_idsolicitud "
					+ " join basico.tbl_dem_producto producto on producto.seq_idproducto = sd.int_idproducto"
					+ " join gestion.tbl_dem_solicituddetalleunidad sdu1 on sd.seq_idrenglon =sdu1.int_idrenglon and sdu1.seq_idrenglonunidad in (select seq_idrenglonunidad from gestion.tbl_dem_solicituddetalleunidad"
					+ " where sd.seq_idrenglon = int_idrenglon limit 1 ) join tbl_dem_unidad_medidas um1 on um1.seq_idumedida =sdu1.int_idunidad"
					+ " left join gestion.tbl_dem_solicituddetalleunidad sdu2 on sd.seq_idrenglon =sdu2.int_idrenglon and sdu1.seq_idrenglonunidad <>sdu2.seq_idrenglonunidad"
					+ " left join tbl_dem_unidad_medidas um2 on um2.seq_idumedida =sdu2.int_idunidad "
					+ " left outer join	 administracion.tbl_dem_contratomecanizado   cm on cm.int_idsolicitudmecanizado = s.seq_idsolicitud"
					+ " left outer join	 administracion.tbl_dem_contrato c on c.seq_idcontrato  =cm.int_idcontrato"
					+ " left outer join  gestion.tbl_dem_orden_servicio os on os.int_idsolicitud = s.seq_idsolicitud"
					+ " left outer join  gestion.tbl_dem_orden_servicio_mecanizado osm on osm.int_idordenservicio  = os.seq_idordenservicio"
					+ " left outer join gestion.tbl_dem_trabajo_orden_servicio trabajo on osm.int_idordenservicio= trabajo.int_idordenservicio "
					+ " left outer join gestion.tbl_dem_trabajo_orden_servicio_mecanizado trabajomecanizado on  trabajo.seq_idtrabajoorden= trabajomecanizado.int_idtrabajoorden"
					+ " left outer join  administracion.tbl_dem_documentofiscal d on d.int_idcontrato =cm.int_idcontrato"
					+ " left outer join	  administracion.tbl_dem_documentofiscaldetalle dd on dd.int_idservicio = sd.int_idproducto and dd.int_iddocumento =d.seq_iddocumento"
					+ " left outer join	 administracion.tbl_dem_detallecontrato dc on dc.int_idproducto= sd.int_idproducto and dc.int_idcontrato=cm. int_idcontrato"
					+ " left outer join	 gestion.tbl_dem_labor_orden_servicio_mecanizado lom on lom.int_idlabor=sd.int_idproducto and lom.int_idordenservicio =os.seq_idordenservicio"
					+ " left outer join  administracion.tbl_dem_recibodocumentofiscal rdf on rdf.int_iddocumentofiscal =d.seq_iddocumento"
					
					+ " where s.dtm_fecha BETWEEN :fechainicio and :fechafin"
					+ " group by s.dtm_fecha ,s.str_nrocon ,cli.str_cedurif,	cli.str_nombre,	rubro.str_descripcion, if.str_descripcion, sec.str_descripcion,"
					+ " parr.str_descripcion, mun.str_descripcion, est.str_descripcion,	producto.str_descripcion, sd.dbl_pase,"
					+ "	sdu1.dbl_cantidad,um1.str_descripcion, sdu2.dbl_cantidad, um2.str_descripcion,"
					+ "	c.int_nrocontrol, c.dtm_fecha, os.str_nrocon, osm.dbl_produccionreal , osm.dbl_trabajofisico ,"
					+ "	osm.dbl_trabajolabor , d.int_nrocontrol, dd.dbl_cantidad, dd.dbl_preciounitario, dc.dbl_cantidad";
					listaMapa=em.createSQLQuery(consulta2)
							.addScalar("totaltrabajado", StandardBasicTypes.DOUBLE)
							.addScalar("cantidad2", StandardBasicTypes.DOUBLE)
							.addScalar("cantidadfacturada", StandardBasicTypes.DOUBLE)
							.addScalar("pases", StandardBasicTypes.DOUBLE)
							.addScalar("cantidad1", StandardBasicTypes.DOUBLE)
							.addScalar("estimadoproducido", StandardBasicTypes.DOUBLE)
							.addScalar("cerradofacturado", StandardBasicTypes.DOUBLE)
							.addScalar("solicitadofactudado", StandardBasicTypes.DOUBLE)
							.addScalar("superficietrabajada", StandardBasicTypes.DOUBLE)
							.addScalar("preciobase", StandardBasicTypes.DOUBLE)
							.addScalar("produccionreal", StandardBasicTypes.DOUBLE)
							.addScalar("estimadotrabajado", StandardBasicTypes.DOUBLE)
							.addScalar("fechacontrato", StandardBasicTypes.DATE)
							.addScalar("fechasolicitud", StandardBasicTypes.DATE)
							.addScalar("numerofactura", StandardBasicTypes.INTEGER)
							.addScalar("numerocontrato", StandardBasicTypes.INTEGER)
							.addScalar("ordencontrol", StandardBasicTypes.STRING)
							.addScalar("sector", StandardBasicTypes.STRING)
							.addScalar("unidad2", StandardBasicTypes.STRING)
							.addScalar("unidad1", StandardBasicTypes.STRING)
							.addScalar("nombre", StandardBasicTypes.STRING)
							.addScalar("parroquia", StandardBasicTypes.STRING)
							.addScalar("cedurif", StandardBasicTypes.STRING)
							.addScalar("financiamiento", StandardBasicTypes.STRING)
							.addScalar("labor", StandardBasicTypes.STRING)
							.addScalar("estado", StandardBasicTypes.STRING)
							.addScalar("municipio", StandardBasicTypes.STRING)
							.addScalar("controlsolicitud", StandardBasicTypes.STRING)
							.addScalar("rubro", StandardBasicTypes.STRING)
							.addScalar("recibos", StandardBasicTypes.STRING)
							.addScalar("operador", StandardBasicTypes.STRING)
							.addScalar("implemento", StandardBasicTypes.STRING)
							.addScalar("maquinaria", StandardBasicTypes.STRING)
							.addScalar("estadosolicitud", StandardBasicTypes.STRING)
							.addScalar("estadoorden", StandardBasicTypes.STRING)
							.addScalar("estadocontrato", StandardBasicTypes.STRING)
							.addScalar("fechaatencion", StandardBasicTypes.DATE)
							           
							.setDate("fechainicio", fechainicio)
							.setDate("fechafin",   fechafin)
							.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
							.list();
					for (HashMap<String, Object> mapa : listaMapa) {
						//HashMap<String, Object>mapa =(HashMap<String, Object>) object;
						Object[] detalle = {
								  mapa.get( "sector"),
								  mapa.get( "fechacontrato"),
								  mapa.get( "totaltrabajado"),
								  mapa.get( "cantidad2"),
								  mapa.get( "unidad2"),
								  mapa.get( "unidad1"),
								  mapa.get( "cantidadfacturada"),
								  mapa.get( "ordencontrol"),
								  mapa.get( "nombre"),
								  mapa.get( "pases"),
								  mapa.get( "parroquia"),
								  mapa.get( "cantidad1"),
								  mapa.get( "estimadoproducido"),
								  mapa.get( "fechasolicitud"),
								  mapa.get( "cedurif"),
								  mapa.get( "numerofactura"),
								  mapa.get( "cerradofacturado"),
								  mapa.get( "numerocontrato"),
								  mapa.get( "solicitadofactudado"),
								  mapa.get( "financiamiento"),
								  mapa.get( "labor"),
								  mapa.get( "estado"),
								  mapa.get( "municipio"),
								  mapa.get( "superficietrabajada"),
								  mapa.get( "controlsolicitud"),
								  mapa.get( "preciobase"),
								  mapa.get( "rubro"),
								  mapa.get( "produccionreal"),
								  mapa.get( "estimadotrabajado"),
								  mapa.get( "recibos"),
								  mapa.get( "operador"),
								  mapa.get( "implemento"),
								  mapa.get( "maquinaria"),
								  mapa.get( "estadosolicitud"),
								  mapa.get( "estadoorden"),
								  mapa.get( "estadocontrato"),
								  mapa.get( "fechaatencion")
								
						};
						listaReporte.add(detalle) ;
						/*
						Iterator it = mapa.keySet().iterator();
						Integer i=0;
						while(it.hasNext()){
						 String key = (String) it.next();
						
						 */
						   /*
						 <field name="field2" class="java.lang.String">
							<fieldDescription><![CDATA[[1]]]></fieldDescription>
						</field>
						 */
						/*
						 if (mapa.get(key)!=null)
						  System.out.println("<field name=\""+key+"\"  class=\""+mapa.get(key).getClass()+"\""
						  		+ "><fieldDescription><![CDATA[["+i+"]]]></fieldDescription></field>");
						 
						 
					i++;
						  
						  */
						  
						
					}
					
			
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
	
		return listaReporte;

	}
	@SuppressWarnings("unchecked")
	public List<OperadorOrdenMecanizado> getListadoOperador(Date inicio, Date fin) throws ExcFiltroExcepcion{
		List<OperadorOrdenMecanizado> operadores = new ArrayList<OperadorOrdenMecanizado>();
		OperadorOrdenMecanizado operador;
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<OrdenTrabajoMecanizado>  maquinarias = null;
		try{
			maquinarias = (List<OrdenTrabajoMecanizado>) em.createQuery("SELECT  ot FROM OrdenTrabajoMecanizado ot INNER JOIN ot.unidadProductiva up INNER JOIN ot.estado es INNER JOIN up.ubicacion ub INNER JOIN ub.sector sec WHERE ot.inicioServicio >= :inicio and ot.inicioServicio <= :fin AND es.activa = :activa AND es.finalizada = :finalizada AND es.detenida = :detenida")
				
				.setDate("inicio", inicio)
				.setDate("fin", fin)
				.setBoolean("activa", Boolean.TRUE)
				.setBoolean("detenida", Boolean.FALSE)
				.setBoolean("finalizada", Boolean.FALSE)
				.list();
			for (OrdenTrabajoMecanizado item : maquinarias) {
				for (DetalleMaquinariaOrdenTrabajo item2 : item.getEquipos()) {
					operador = new OperadorOrdenMecanizado();
					operador.setSector(item.getUnidadProductiva().getSector());
					operador.setOrdenTrabajo(item);
					operador.setOperador(item2.getOperador());
					operador.setMaquinaria(item2.getMaquinaria());	
					operadores.add(operador);
		
				}
	
				
			}
		
			
			
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return operadores;
		
	}
}
