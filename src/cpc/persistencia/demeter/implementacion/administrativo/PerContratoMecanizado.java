package cpc.persistencia.demeter.implementacion.administrativo;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.criteria.Subquery;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.CuotasAPagarCliente;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuotaAPagarCliente;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoOrdenTrabajo;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerContratoMecanizado extends DaoGenerico<ContratoMecanizado, Integer>{

	
	private static final long serialVersionUID = 4524428514868526037L;

	public PerContratoMecanizado() {
		super(ContratoMecanizado.class);
	}
	
	public void guardar(ContratoMecanizado objeto,  ControlSede sede)  throws Exception, SQLException {
	   	 if (objeto.getId() == null)	   		 
	   		nuevo( objeto, sede);
	   	 else
	   	   super.guardar(objeto, objeto.getId());
	}
	
	
	
	public void nuevo(ContratoMecanizado docu, ControlSede control)  throws ExcFiltroExcepcion, SQLException {
		docu.setEstadoExoneracion(new PerEstadoExoneracionContrato().getNoExonerado());
		Transaction tx = null;
		Session em ;
		try{		
			docu.setSerie(control.getProximaSerieContrato());
			docu.setNroControl(control.getControlContrato());	
			docu.setEstado(new EstadoContrato(EstadoContrato.ESTADO_POR_FIRMAR,""));	
			docu.getSolicitud().setEstadosolicitud(new PerEstadoSolicitud().getfirmada());
			//System.out.println("lo que esta seteado en docu"+ docu.getSolicitud().getEstadosolicitud().getEstado());
			em =SessionDao.getInstance().getCurrentSession();
			
		    tx = em.beginTransaction();
			em.save(docu);
			em.update(control);
			em.update(docu.getSolicitud());
		    em.flush();
		    tx.commit(); 
		   
		}catch (Exception e) {
			 
			e.printStackTrace();
			tx.rollback();
			if (control==null) throw new SQLException("Indeterminado el Control Numerico del Contrato. ¿Su registro de usuario pertenece a la Sede actual? ");
			throw new ExcFiltroExcepcion();
		}
	}
	
	@SuppressWarnings("unused")
	public ContratoMecanizado getDetalle(ContratoMecanizado entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	ContratoMecanizado c = (ContratoMecanizado) em.load(ContratoMecanizado.class, entrada.getId());
     	try{			
     		
			for (DetalleContrato item: c.getDetallesContrato()){ em.evict(item);				
				
			}
           for (CuotasAPagarCliente item: c.getCuotasAPagarCliente()){ em.evict(item);			
				
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	@SuppressWarnings("unused")
	public ContratoMecanizado getEnriqueser(Contrato entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	ContratoMecanizado c = (ContratoMecanizado) em.get(ContratoMecanizado.class, entrada.getId());
     	try{			
			for (DetalleContrato item: c.getDetallesContrato()){			
				
			}
           for (CuotasAPagarCliente item: c.getCuotasAPagarCliente()){			
				
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DetalleContrato> getContratosServicio(){
		Transaction tx = null;
	//	List<Contrato> cttos = new PerContrato().getPorYEnEjecucion();
		List<Integer> ids = new PerContrato().getIdsPorYEnEjecucion();
		List<DetalleContrato> criterio= null;
		List<Integer> numeros = new ArrayList<Integer>();
		numeros.add(new Integer(1));
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DetalleContrato.class)
				.add(Restrictions.in("contrato.id", ids))
				.add(Restrictions.eq("prestado", Boolean.FALSE))
				.addOrder(Order.desc("id"))
				.list();
			//em.evict(cttos);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public Contrato validarDetalle(Contrato entrada, LaborOrdenServicio labor){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;		
     	tx = em.beginTransaction();
     	Contrato c = (Contrato) em.load(Contrato.class, entrada.getId());
     	try{			
			for (DetalleContrato item: c.getDetallesContrato()){
				if (labor.getLabor().equals(item.getProducto())){
					item.setCantidadReal(labor.getCantidad());
					item.setPrestado(true);
					 em.evict(item);
				}
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return c;
	}
	
	public void anularContrato(ContratoMecanizado contrato) throws Exception, SQLException{		
		Transaction tx = null;
		Session em ;
		int hijosactivos= getHijosActivos(contrato);
	
		try{  EstadoSolicitud estadosolictud = new PerEstadoSolicitud().getaprobada();
		 EstadoContrato estadocontrato = new EstadoContrato(EstadoContrato.ESTADO_ANULADO,"");
		
			em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			/*Tiene una Factura No Anulada*/
			if (hijosactivos>=1){
				throw new SQLException("Imposible Anular el Documento porque tiene una Factura o una orden activa");
			}
			/*Tiene un Servicio para ser Prestado en Alguna Orden de Trabajo*/
			  //throw new SQLException("Imposible Anular el Documento porque tiene una Orden de Trabajo Activa");
			/* **** **/
			contrato.getSolicitud().setEstadosolicitud(estadosolictud);
			contrato.setEstado(estadocontrato);
			em.update(contrato);
			em.update(contrato.getSolicitud()); 
			new PerCuotaAPagarCliente().eliminarCuotasApagar(contrato,em,tx);
			 tx.commit(); 
		
		}catch(SQLException e){
			e.printStackTrace();
			tx.rollback();
			throw e;
		} catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	public void procesarContrato(ContratoMecanizado contrato) throws Exception, SQLException{		
		Transaction tx = null;
		Session em ;
	//	int hijosactivos= getHijosActivos(contrato);
	
		try{  EstadoSolicitud estadosolictud = new PerEstadoSolicitud().getculminada();
		 EstadoContrato estadocontrato = new PerEstadoContrato().getConcluidoAdministracion();
		
			em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			/*Tiene una Factura No Anulada*/
			/*Tiene un Servicio para ser Prestado en Alguna Orden de Trabajo*/
			  //throw new SQLException("Imposible Anular el Documento porque tiene una Orden de Trabajo Activa");
			/* **** **/
			contrato.getSolicitud().setEstadosolicitud(estadosolictud);
			contrato.setEstado(estadocontrato);
			em.update(contrato);
			em.update(contrato.getSolicitud()); 
			//new PerCuotaAPagarCliente().eliminarCuotasApagar(contrato,em,tx);
			 tx.commit(); 
		
		}catch (Exception e) {			
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	public Contrato getEstadoContrato(Contrato ctto, LaborOrdenServicio labor){
		ctto = validarDetalle(ctto, labor);
		boolean terminado = true;
		if (ctto.getDetallesContrato().size() > 1){
			for (DetalleContrato item : ctto.getDetallesContrato()) {
				if (item.getPrestado() == null || item.getPrestado() == false){
					terminado = false;
					break;
				} 
			}
			if (terminado){
				culminarContrato(ctto);
			}
		}else
			culminarContrato(ctto);
		return ctto;
	}
	

	
	
	private void culminarContrato(Contrato ctto){
		EstadoContrato estado = new PerEstadoContrato().getCulminado();
		ctto.setEstado(estado);
	}
	
	public int getHijosActivos(Contrato ctto) throws Exception{
		
		Transaction tx = null;
		Session em ;
		int hijos = 0;
		
		try {
		EstadoOrdenTrabajo estado = new PerEstadoOrdenTrabajo().getCancelado();
			em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		@SuppressWarnings("unchecked")
		List<OrdenTrabajoMecanizado> criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
				.add(Restrictions.eq("contrato",ctto))
				.add(Restrictions.not(Restrictions.eq("estado",estado)))
				.addOrder(Order.desc("id"))
				.list();
		
	
		
		@SuppressWarnings("unchecked")
		List<DocumentoFiscal> criterio2 = em.createCriteria(DocumentoFiscal.class)
		    		.add(Restrictions.eq("contrato",ctto))
			    	.list();
		hijos = criterio.size()+ criterio2.size();
		
		tx.commit();
		} catch (Exception e) {e.printStackTrace();
		tx.rollback();
		throw e;
			// TODO: handle exception
		}
		
	
		return hijos;
	}
	//corre
	@SuppressWarnings("unchecked")
	public synchronized List<DetalleContrato> getContratosServicioSinOrden(){
		Transaction tx = null;
		//List<Contrato> cttos = new PerContrato().getPorYEnEjecucion();
	//	List<Integer> ids = new PerContrato().getIdsPorEnEjecucionyCerradoAdmistracion();
		
		List<Object> criterio = null;
		
		List<DetalleContrato> detalles = new ArrayList<DetalleContrato>();
		EstadoOrdenTrabajo estad = new PerEstadoOrdenTrabajo().getCancelado();
		Session em =SessionDao.getInstance().getCurrentSession();
		
	    
		tx = em.beginTransaction();
		try{
			
	/*		DetachedCriteria estados = DetachedCriteria.forClass(EstadoContrato.class, "est")
										.add(Restrictions.or(Restrictions.le("est.id", 3), Restrictions.like("est.descripcion", "%Administraci%")))
										.setProjection(Projections.property("est.id"));
*/
			
			DetachedCriteria subquery = DetachedCriteria.forClass(OrdenTrabajoMecanizado.class, "ug")
					.add(Restrictions.eqProperty("ug.contrato.id", "decontrato.id"))
			//		.add(Restrictions.in("ug.contrato.id", ids))
					.add(Restrictions.not( Restrictions.eq("estado",estad)))
					.createAlias("ug.laborOrden", "laborOrden")
					.createAlias("laborOrden.labor","labor")
					.add(Restrictions.eqProperty("labor.id", "deproducto.id"))
					.setProjection(Projections.property("ug.contrato.id"));
				
			//
					//
					
				//	.add(Restrictions.eqProperty("ug.laborOrden.labor.id", "de.producto.id"))
					//.add(Restrictions.eqProperty("labor", "de.producto"))
					//.add(Restrictions.eqProperty("ug.contrato.id", "de.id"))
					
					//.add(Restrictions.eqProperty("ug.game.id","u.id"))
					//.setProjection(Projections.property("ug.game.id"));
					;
//	List<?> s = subquery.getExecutableCriteria(em).list();
					
			
					
					
					/*
	
				
					 */
			
					  ProjectionList  projections = Projections.projectionList() ;
						 
						
						 
						 projections.add(Projections.property("de.id"));
						 projections.add(Projections.property("decontrato.serie"));
						 projections.add(Projections.property("decontrato.nroControl"));
						 projections.add(Projections.property("decontrato.fecha"));
						 projections.add(Projections.property("decontrato.monto"));
						 projections.add(Projections.property("estado.descripcion"));
						
						
						 projections.add(Projections.property("pagador.nombres"));
			             projections.add(Projections.property("deproducto.descripcion"));
					
					
					
					//criterio= new CopyOnWriteArrayList<DetalleContrato>(em.createCriteria(DetalleContrato.class,"de")
			        criterio= em.createCriteria(DetalleContrato.class,"de")
					.createAlias("de.producto", "deproducto")
					.createAlias("de.contrato", "decontrato")
					.createAlias("decontrato.pagador", "pagador")
					.createAlias("decontrato.estado", "estado")
					.add(Restrictions.eq("de.prestado", Boolean.FALSE))
					//.add(Restrictions.in("decontrato.id", ids))
					//.addOrder(Order.desc("de.id"))
					.add( org.hibernate.criterion.Property.forName("decontrato.id").notIn(subquery))
				.add(Restrictions.or(Restrictions.le("estado.id", 3), Restrictions.like("estado.descripcion", "%Administraci%")))
				//	.add( org.hibernate.criterion.Property.forName("estado.id").in(estados))
					.setProjection(projections)
				//	.setMaxResults(1)
					//.setProjection(Projections.property("de.id"))
					.list();
	   System.out.println(criterio.size());
	   

		for (Object object : criterio) {
	   DetalleContrato detalleContrato = new DetalleContrato();
		Object[] objects=(Object[]) object;
		detalleContrato.setId((Long) objects[0]);
		
		Contrato contrato = new Contrato();
		contrato.setSerie((String) objects[1]);
		contrato.setNroControl((Integer) objects[2]);
		contrato.setFecha((Date) objects[3]);
		contrato.setMonto((Double) objects[4]);
		
		EstadoContrato estado= new  EstadoContrato();
		estado.setDescripcion((String) objects[5]);
		contrato.setEstado(estado);
		
		Cliente pagador = new Cliente();
		pagador.setNombres((String) objects[6]);
		contrato.setPagador(pagador );
		detalleContrato.setContrato(contrato );
		
		
		Labor labor = new Labor();
		labor.setDescripcion((String) objects[7]);
		detalleContrato.setProducto(labor );
		
		detalles.add(detalleContrato);
		
		
		
		}
					
/*
					  ProjectionList  projections = Projections.projectionList() ;
						 
						
						 
						 projections.add(Projections.property("id"),"id");
						 projections.add(Projections.property("laborOrden"),"labor");
						 projections.add(Projections.property("contrato.id"),"contrato");
			             projections.add(Projections.property("detalles"),"detalles");
			*/
					
			    //    criterio2 = em.createCriteria(OrdenTrabajoMecanizado.class)
			    /*    .createAlias("laborOrden", "laborOrden")
			        .createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN)
			        .createAlias("contrato.detallesContrato", "detalles",CriteriaSpecification.LEFT_JOIN)
			        .setFetchMode("laborOrden", FetchMode.JOIN)
			              .setFetchMode("detalles", FetchMode.JOIN)*/
			  //      .setProjection(projections)
			//		.add(Restrictions.not( Restrictions.eq("estado",estad)))
			//		.add(Restrictions.in("contrato.id", ids))
				//	.add(Restrictions.le("contrato.estado.id", 3))
			/*		.addOrder(Order.desc("id"))
					.list();
			*/
					/*List<OrdenTrabajoMecanizado> ordenes=new ArrayList<OrdenTrabajoMecanizado>();
					for (Object object : criterio2) {
								OrdenTrabajoMecanizado orden = new OrdenTrabajoMecanizado();
								Object[] objects=(Object[]) object;
								orden.setId((Long) objects[0]);
								orden.setLaborOrden((LaborOrdenServicio) objects[1]);
								
								Contrato contrato= new ContratoMecanizado();
								contrato.setId((Integer) objects[2]);
								contrato.setDetallesContrato((List<DetalleContrato>) objects[3]);
								orden.setContrato(contrato);
							
								
					}*/
			/*
			 criterio= new CopyOnWriteArrayList<DetalleContrato>(em.createCriteria(DetalleContrato.class)
				.add(Restrictions.in("contrato", cttos))
				.add(Restrictions.eq("prestado", Boolean.FALSE))
				.addOrder(Order.desc("id"))
				.list());
			
			 criterio3 = em.createCriteria(OrdenTrabajoMecanizado.class)
						.add(Restrictions.not( Restrictions.eq("estado",estad)))
						.addOrder(Order.desc("id")).list();
			 
			
			criterio2 = em.createCriteria(OrdenTrabajoMecanizado.class)
					.add(Restrictions.not( Restrictions.eq("estado",estad)))
					.addOrder(Order.desc("id")).list();
			*/
			/*
			for (OrdenTrabajoMecanizado ordenTrabajoMecanizado :ordenes) {
				
				LaborOrdenServicio a = ordenTrabajoMecanizado.getLaborOrden();
				Contrato b = ordenTrabajoMecanizado.getContrato();
				if (a!=null && b!=null){
				for (DetalleContrato detalles : criterio) {
					if ((detalles.getContrato().getId()== b.getId()) && a.getLabor().equals(detalles.getProducto())){
						System.out.println("removiendo el contrato "+ detalles.getId() +" estado del detalle "+ detalles.getPrestado()  );
						
						criterio.remove(detalles);
						}
				}
				
			   }
			}
			*/
			
			
		
						tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return detalles;
	}
	
	
	 
	
	@SuppressWarnings("unchecked")
	public synchronized boolean  IsNoRepetida(OrdenTrabajoMecanizado docu){
		Transaction tx = null;
		boolean repetida = false;
		List <Object> criterio2 = null;
		EstadoOrdenTrabajo estad = new PerEstadoOrdenTrabajo().getCancelado();
		Session em =SessionDao.getInstance().getCurrentSession();
		
	    
		tx = em.beginTransaction();
		try{
			 ProjectionList  projections = Projections.projectionList() ;
			 projections.add(Projections.property("id"));
			 projections.add(Projections.property("laborOr.labor"));
			 projections.add(Projections.property("contra.id"));
			 
			
			criterio2 = em.createCriteria(OrdenTrabajoMecanizado.class)
					.add(Restrictions.not( Restrictions.eq("estado",estad)))
					.setProjection(projections)
					.createAlias("contrato", "contra")
					.createAlias("laborOrden", "laborOr")
					.addOrder(Order.desc("id")).list();
			
			
			for (Object object : criterio2) {
				
				Object[] objects=(Object[]) object;
				
				Labor a = (Labor) objects[1];
				Integer b = (Integer) objects[2];
				if (a!=null && b!=null){
			
					if ((docu.getContrato().getId()== b) && a.equals(docu.getLaborOrden().getLabor())){
						System.out.println("removiendo el contrato "+ docu.getContrato().getId() +" estado del detalle "   );
						repetida=true;
						break;
			
				}
				
			   }
			}
			
			
						tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		
		return repetida;
	}
	
	public List<ContratoMecanizado> getAllProject(){
		Transaction tx = null;
		List<ContratoMecanizado> contratos= new ArrayList<ContratoMecanizado>();
		Session em =SessionDao.getInstance().getCurrentSession();
		
		tx = em.beginTransaction();
		try{
			
			
			  ProjectionList  projections = Projections.projectionList() ;
				 
				
				 
				 projections.add(Projections.property("id"));
				 projections.add(Projections.property("serie"));
				 projections.add(Projections.property("nroControl"));
				 projections.add(Projections.property("fecha"));
				 projections.add(Projections.property("monto"));
				
				 projections.add(Projections.property("tipoContrato.nombre"));
				 projections.add(Projections.property("estado.descripcion"));
				 projections.add(Projections.property("productor.nombres"));
				
			//	 projections.add(Projections.property("servicio.descripcion"));
			//	 projections.add(Projections.property("estadosolicitud.estado"));
				
			
		/*
		 		
	
		titulo.setMetodoBinder("getStrNroDocumento");
	public String getStrNroDocumento() {
		return serie+ Formateador.rellenarNumero(nroControl,"00000000");
	}
	

	
		titulo.setMetodoBinder("getFechaString");
	public String getFechaString() {		
		return Fecha.obtenerFecha(fecha);
	}
		
		titulo.setMetodoBinder("getNombreCliente");
	@Transient
	public String getNombreCliente(){
		if (unidadProductiva == null)
			return "";
		if (unidadProductiva.getProductor() == null)
			return "";
		return unidadProductiva.getProductor().getNombres();
	}
	
	
		
		titulo.setMetodoBinder("getMonto");
	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}

		
		titulo.setMetodoBinder("getEstado");
	@Transient
	public String getEstadoString(){
		if (estado == null)
			return "";
		return getEstado().getDescripcion();
		
		estado
	}

	
		titulo.setMetodoBinder("getStrTipo");
		return tipoContrato.getNombre();
	
	
	 unidadProductiva.getProductor().getNombres()
	 return getEstado().getDescripcion();
	 tipoContrato.getNombre();
			     */
			List<Object> criterio =null;
			criterio = em.createCriteria(ContratoMecanizado.class)
				
				.createAlias("estado", "estado")
				.createAlias("tipoContrato", "tipoContrato")
				.createAlias("unidadProductiva", "unidadProductiva")
				.createAlias("unidadProductiva.productor", "productor")
			//	.createAlias("productor", "productor")	
			//	.createAlias("estadosolicitud", "estadosolicitud",CriteriaSpecification.LEFT_JOIN)	
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
				
				ContratoMecanizado contratoMecanizado = new ContratoMecanizado();
				Object[] objects=(Object[]) object;
			
			contratoMecanizado.setId((Integer) objects[0]);
		    contratoMecanizado.setSerie((String) objects[1]);
		    contratoMecanizado.setNroControl((Integer) objects[2]);
			contratoMecanizado.setFecha((Date) objects[3]);
			contratoMecanizado.setMonto((Double) objects[4]);
			
			TipoContrato tipocontrato = new TipoContrato();
			tipocontrato.setNombre((String) objects[5]);
			if (tipocontrato!=null)
			contratoMecanizado.setTipoContrato(tipocontrato);	
				 
			EstadoContrato estadoContrato= new EstadoContrato();
			estadoContrato.setDescripcion((String) objects[6]);
			if (estadoContrato.getDescripcion()!=null)
			contratoMecanizado.setEstado(estadoContrato);	
			
				
				
				Productor productor= new Productor();
				productor.setNombres((String) objects[7]);
				UnidadProductiva unidadProductiva = new UnidadProductiva();
				unidadProductiva.setProductor(productor);
				if (productor.getNombres()!=null)
				contratoMecanizado.setUnidadProductiva(unidadProductiva);
					contratos.add(contratoMecanizado);
				
			}
			
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return contratos;
	}
	
	public ContratoMecanizado  getContratoMecanizadoProject(ContratoMecanizado contratoMecanizado){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		contratoMecanizado =(ContratoMecanizado) em.get(contratoMecanizado.getClass(), contratoMecanizado.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return contratoMecanizado;
	}
	
	
	public DetalleContrato  getDetalleProject(DetalleContrato detalleContrato){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		detalleContrato =(DetalleContrato) em.get(detalleContrato.getClass(), detalleContrato.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return detalleContrato;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
