package cpc.persistencia.demeter.implementacion.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Rubro;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.DetalleOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.gestion.LaborOrdenServicio;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.gestion.OrdenTrabajoTransporte;
import cpc.modelo.demeter.gestion.OrdenTrabajoTransporteInterno;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoContrato;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

public class PerOrdenTrabajoTransporte extends
		DaoGenerico<OrdenTrabajoTransporte, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461948843089944348L;

	public PerOrdenTrabajoTransporte() {
		super(OrdenTrabajoTransporte.class);
		// TODO Auto-generated constructor stub
	}
	
	public void guardarOrden(OrdenTrabajoTransporte objeto, Long indice
		) throws ExcFiltroExcepcion {
		System.out.println("Guardando");

		Session em = null;

		Transaction tx = null;
		try {
			objeto.setTipo(new PerTipoTrabajo().getTipoTransporte());
			objeto.setEstado(new PerEstadoOrdenTrabajo().getInicio());
			em = SessionDao.getInstance().getCurrentSession();
		
			tx = em.beginTransaction();
			ControlUnidadFuncional control= new PerControlUnidadFuncional().getControl(objeto.getUnidadFuncional(), tx);
			if (indice == null) {
				System.out.println("Creando");
				
				nuevo(objeto, control, em, tx);
			} else {
				System.out.println("Modificando");
				em.update(objeto);
			}
			for (DetalleMaquinariaOrdenTrabajo item : objeto.getEquipos()) {
				if (item.getId() == null) {
					if (item.getOperativa() == null)
						item.setOperativa(true);
					if (item.getOrdenTrabajo()==null)
						item.setOrdenTrabajo(objeto);
					em.save(item);
				} else
					em.update(item);
			}
			tx.commit();

		} catch (Exception e) {

			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error almacenando Orden de Trabajo, "
					+ e.getMessage());
		}
	}

	public void nuevo(OrdenTrabajoTransporte docu,
			ControlUnidadFuncional control, Session em, Transaction tx)
			throws Exception {

		control.incrementarOrdenTransporte();;
		docu.setFecha(new Date());
		docu.setNroControl(control.getSerieOrdenTransporte()
				+ Formateador.rellenarNumero(control.getControlOrdenTransporte(),
						"000000"));
	
		EstadoContrato criteriocontrato = (EstadoContrato) em
				.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%jecuci%"))
				.uniqueResult();

		EstadoContrato criteriocontrato2 = (EstadoContrato) em
				.createCriteria(EstadoContrato.class)
				.add(Restrictions.like("descripcion", "%dministra%"))
				.uniqueResult();

		EstadoSolicitud criteriosol = (EstadoSolicitud) em
				.createCriteria(EstadoSolicitud.class)
				.add(Restrictions.eq("estado", "en proceso")).uniqueResult();

		EstadoOrdenTrabajo criterioCargando = (EstadoOrdenTrabajo) em
				.createCriteria(EstadoOrdenTrabajo.class)
				.add(Restrictions.like("descripcion", "%atos%")).uniqueResult();

		em.save(docu);
		em.update(control);

		if (docu.getCotizacionTransporte() != null) {
			if (docu.getCotizacionTransporte().getEstado().getDescripcion()
					.equals(criteriocontrato2.getDescripcion())) {
				docu.setEstado(criterioCargando);
			} else {
				docu.getCotizacionTransporte().setEstado(criteriocontrato);
				em.update(docu.getCotizacionTransporte());
			}

			System.out.println("estado contrato"
					+ docu.getCotizacionTransporte().getEstadoString());
		}

	}

	public void cerrar(OrdenTrabajoTransporte objeto, Long indice)
			throws ExcFiltroExcepcion {
		System.out.println("Guardando");

		EstadoOrdenTrabajo estadoOrden = new PerEstadoOrdenTrabajo()
				.getTerminado();
		EstadoContrato estadoContratoAd = new PerEstadoContrato()
				.getConcluidoAdministracion();
		EstadoContrato estadoContratoCul = new PerEstadoContrato().getCulminado();
		Transaction tx = null;
		try {
			if (objeto.getCotizacionTransporte() != null) {
				if (objeto.getCotizacionTransporte().getEstado()
						.getDescripcion()
						.equals(estadoContratoAd.getDescripcion())) {
					objeto.setEstado(new PerEstadoOrdenTrabajo()
							.getCargaCompleta());
				} else
					objeto.setEstado(new PerEstadoOrdenTrabajo().getTerminado());
			} else
				objeto.setEstado(new PerEstadoOrdenTrabajo().getTerminado());

			if (objeto.getCotizacionTransporte() != null) {
				if (objeto.getCotizacionTransporte().getEstado()
						.getDescripcion()
						.equals(estadoContratoAd.getDescripcion())) {
				} else {objeto.getCotizacionTransporte().setEstado(estadoContratoCul);
				}
			}

			Session em = SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();

			em.update(objeto);
			if (objeto.getCotizacionTransporte() != null)
				em.update(objeto.getCotizacionTransporte());
			for (DetalleOrdenTrabajo item : objeto.getDetalles()) {
				if (item.getId() == null)
					em.save(item);
				else
					em.update(item);
			}

			tx.commit();

		} catch (Exception e) {

			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(
					"Error almacenando Cierre de Orden Trabajo, "
							+ e.getMessage());
		}
	}

	public void anular(OrdenTrabajoTransporte objeto, Long indice,
			boolean anularSolicitud) throws ExcFiltroExcepcion {
		System.out.println("Guardando");
		EstadoOrdenTrabajo estadocancelado = new PerEstadoOrdenTrabajo()
				.getCancelado();
		EstadoContrato estadoActivo = new PerEstadoContrato().getActivo();
		/*EstadoSolicitud estadosol = new PerEstadoSolicitud().getfirmada();
		EstadoContrato estadocon = new PerEstadoContrato().getPorFirmar();
		EstadoContrato estadoContratoAd = new PerEstadoContrato()
				.getConcluidoAdministracion();
		*/
		Session em = SessionDao.getInstance().getCurrentSession();
		
		Transaction tx = em.beginTransaction();
		try {
			objeto.setEstado(estadocancelado);
			
				if (objeto.getSolicitud() != null) {
					// correjir
					objeto.getSolicitud().setAprobada(false);
				}
				
				if (objeto.getCotizacionTransporte()!=null)
					objeto.getCotizacionTransporte().setEstado(estadoActivo);
				objeto.setEstado(estadocancelado);
				

				em.update(objeto);
				em.update(objeto.getCotizacionTransporte());

			

			tx.commit();

		} catch (Exception e) {

			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error Anulando Orden Trabajo, "
					+ e.getMessage());
		}
	}

	public List<OrdenTrabajoTransporte> getAll(EstadoOrdenTrabajo estado)
			throws ExcFiltroExcepcion {
		Transaction tx = null;
		List<OrdenTrabajoTransporte> criterio = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try {
			criterio = em.createCriteria(OrdenTrabajoTransporte.class)
					.add(Restrictions.eq("estado", estado))
					.addOrder(Order.desc("id")).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoTransporte> getActivas() throws ExcFiltroExcepcion {
		Transaction tx = null;
		EstadoOrdenTrabajo estado1 = new PerEstadoOrdenTrabajo().getEnEspera();
		EstadoOrdenTrabajo estado2 = new PerEstadoOrdenTrabajo().getInicio();
		EstadoOrdenTrabajo estado3 = new PerEstadoOrdenTrabajo()
				.getCargaDatos();
		List<OrdenTrabajoTransporte> criterio = null;

		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try {
			Criterion condicion1 = Restrictions.eq("estado", estado1);
			Criterion condicion2 = Restrictions.eq("estado", estado2);
			Criterion condicion3 = Restrictions.or(condicion1, condicion2);
			Criterion condicion4 = Restrictions.eq("estado", estado3);
			criterio = em.createCriteria(OrdenTrabajoTransporte.class)
					.add(Restrictions.or(condicion3, condicion4))
					.addOrder(Order.desc("id")).list();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	/*public List<OrdenTrabajoTransporte> getActivasProject()
			throws ExcFiltroExcepcion {
		Transaction tx = null;
		EstadoOrdenTrabajo estado1 = new PerEstadoOrdenTrabajo().getEnEspera();
		EstadoOrdenTrabajo estado2 = new PerEstadoOrdenTrabajo().getInicio();
		EstadoOrdenTrabajo estado3 = new PerEstadoOrdenTrabajo()
				.getCargaDatos();
		List<Object> criterio = null;
		List<OrdenTrabajoTransporte> ordenTrabajoMecanizados = new ArrayList<OrdenTrabajoTransporte>();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try {

			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.property("id"), "id");
			proList.add(Projections.property("nroControl"), "nroControl");
			proList.add(Projections.property("fecha"), "fecha");
			proList.add(Projections.property("unidadFuncional.descripcion"),
					"unidadfuncional");
			proList.add(Projections.property("productor.nombres"), "nombres");
			proList.add(Projections.property("sector.nombre"), "nombresector");
			proList.add(Projections.property("labor.descripcion"),
					"descripcion");
			proList.add(Projections.property("rubro.descripcion"),
					"rubrodescripcion");
			proList.add(Projections.property("estado.descripcion"),
					"descripcion");

			Criterion condicion1 = Restrictions.eq("estado", estado1);
			Criterion condicion2 = Restrictions.eq("estado", estado2);
			Criterion condicion3 = Restrictions.or(condicion1, condicion2);
			Criterion condicion4 = Restrictions.eq("estado", estado3);
			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
					.add(Restrictions.or(condicion3, condicion4))
					.createAlias("laborOrden", "laborOrden")
					.createAlias("laborOrden.labor", "labor")
					.createAlias("unidadFuncional", "unidadFuncional")
					.createAlias("productor", "productor")
					.createAlias("unidadProductiva", "unidadProductiva")
					.createAlias("unidadProductiva.ubicacion", "ubicacion")
					.createAlias("ubicacion.sector", "sector")
					.createAlias("rubro", "rubro")
					.createAlias("estado", "estado").setProjection(proList)
					.addOrder(Order.desc("id")).list();

			for (Object object : criterio) {
				OrdenTrabajoMecanizado orden = new OrdenTrabajoMecanizado();

				Object[] objects = (Object[]) object;

				
				 * id"),"id nroControl"),"nroControl
				 * unidadFuncional.descripcion"),"unidadfuncional
				 * productor.nombres"),"nombres sector.nombre"),"nombresector
				 * labor.descripcion"),"descripcion
				 * rubro.descripcion"),"rubrodescripcion
				 * estado.descripcion"),"descripcion
				 

				orden.setId((Long) objects[0]);
				orden.setNroControl((String) objects[1]);
				orden.setFecha((Date) objects[2]);

				if (objects[3] != null) {
					cpc.modelo.ministerio.dimension.UnidadFuncional unidadFuncional = new cpc.modelo.ministerio.dimension.UnidadFuncional();
					unidadFuncional.setDescripcion((String) objects[3]);
					orden.setUnidadFuncional(unidadFuncional);
				}

				if (objects[4] != null) {
					Productor productor = new Productor();
					productor.setNombres((String) objects[4]);
					orden.setProductor(productor);
				}
				if (objects[5] != null) {
					UnidadProductiva unidadProductiva = new UnidadProductiva();
					UbicacionDireccion ubicacionDireccion = new UbicacionDireccion();
					UbicacionSector ubicacionSector = new UbicacionSector();
					ubicacionSector.setNombre((String) objects[5]);
					ubicacionDireccion.setSector(ubicacionSector);
					unidadProductiva.setUbicacion(ubicacionDireccion);
					orden.setUnidadProductiva(unidadProductiva);

				}
				if (objects[6] != null) {
					LaborOrdenServicio laborOrdenServicio = new LaborOrdenServicio();
					Labor labor = new Labor();
					labor.setDescripcion((String) objects[6]);
					laborOrdenServicio.setLabor(labor);
					orden.setLaborOrden(laborOrdenServicio);
				}
				if (objects[7] != null) {
					Rubro rubro = new Rubro();
					rubro.setDescripcion((String) objects[7]);
					orden.setRubro(rubro);

				}
				if (objects[8] != null) {
					EstadoOrdenTrabajo estado = new EstadoOrdenTrabajo();
					estado.setDescripcion((String) objects[8]);
					orden.setEstado(estado);
				}

				ordenTrabajoMecanizados.add(orden);

			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return ordenTrabajoMecanizados;
	}

	@SuppressWarnings("unchecked")
	public List<OrdenTrabajoMecanizado> getTodosProject()
			throws ExcFiltroExcepcion {
		Transaction tx = null;

		List<Object> criterio = null;
		List<OrdenTrabajoMecanizado> ordenTrabajoMecanizados = new ArrayList<OrdenTrabajoMecanizado>();
		Session em = SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();

		try {
			
			 * 
			 * return nroControl;
			 * 
			 * return fecha.toString();
			 * 
			 * return unidadFuncional.getDescripcion(); return
			 * productor.getNombres();
			 * 
			 * UbicacionSector sector = getUnidadProductiva().getSector();
			 * return sector.getNombre(); return labor.getDescripcion(); return
			 * rubro.getDescripcion(); return estado.getDescripcion();
			 

			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.property("id"), "id");
			proList.add(Projections.property("nroControl"), "nroControl");
			proList.add(Projections.property("fecha"), "fecha");
			proList.add(Projections.property("unidadFuncional.descripcion"),
					"unidadfuncional");
			proList.add(Projections.property("productor.nombres"), "nombres");
			proList.add(Projections.property("sector.nombre"), "nombresector");
			proList.add(Projections.property("labor.descripcion"),
					"descripcion");
			proList.add(Projections.property("rubro.descripcion"),
					"rubrodescripcion");
			proList.add(Projections.property("estado.descripcion"),
					"descripcion");

			criterio = em.createCriteria(OrdenTrabajoMecanizado.class)
					.setProjection(proList)
					.createAlias("laborOrden", "laborOrden")
					.createAlias("laborOrden.labor", "labor")
					.createAlias("unidadFuncional", "unidadFuncional")
					.createAlias("productor", "productor")
					.createAlias("unidadProductiva", "unidadProductiva")
					.createAlias("unidadProductiva.ubicacion", "ubicacion")
					.createAlias("ubicacion.sector", "sector")
					.createAlias("rubro", "rubro")
					.createAlias("estado", "estado")
					// .setResultTransformer(Transformers.aliasToBean(OrdenTrabajoMecanizado.class))
					.list();
			// setFetchMode("laborOrden.labor", FetchMode.SELECT).

			// createAlias("i.laborOrden", "es",
			// CriteriaSpecification.FULL_JOIN).
			// add(Property.forName("i.laborOrden").eq(subquery)).

			
			 * for (OrdenTrabajoMecanizado ordenTrabajoMecanizado : criterio) {
			 * List<LaborOrdenServicio> a=
			 * em.createCriteria(LaborOrdenServicio.class
			 * ).add(Restrictions.eq("orden",ordenTrabajoMecanizado)).list() ;
			 * ordenTrabajoMecanizado.setLaborOrden(a.get(0));
			 * 
			 * // // }
			 
			// // createAlias("i.laborOrden", "es",
			// CriteriaSpecification.FULL_JOIN).
			// //createAlias("i.laborOrden.labor", "i",
			// CriteriaSpecification.FULL_JOIN)
			// // .add(Expression.eq("laborOrden",
			// subquery)).//setProjection(proList).
			// //
			// setResultTransformer(Transformers.aliasToBean(OrdenTrabajoMecanizado.class)).
			// // em.get(LaborOrdenServicio.class, 1);
			//
			// criterio =
			// em.createQuery("select ot.laborOrden.labor , ot.nroControl, ot.fecha , ot.unidadFuncional, ot.estado , ot.rubro , ot.productor  from cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado  ot INNER JOIN ot.unidadProductiva up INNER JOIN ot.estado es INNER JOIN up.ubicacion ub INNER JOIN ub.sector sec").list();

			for (Object object : criterio) {
				OrdenTrabajoMecanizado orden = new OrdenTrabajoMecanizado();

				Object[] objects = (Object[]) object;

				
				 * id"),"id nroControl"),"nroControl
				 * unidadFuncional.descripcion"),"unidadfuncional
				 * productor.nombres"),"nombres sector.nombre"),"nombresector
				 * labor.descripcion"),"descripcion
				 * rubro.descripcion"),"rubrodescripcion
				 * estado.descripcion"),"descripcion
				 

				orden.setId((Long) objects[0]);
				orden.setNroControl((String) objects[1]);
				orden.setFecha((Date) objects[2]);

				if (objects[3] != null) {
					cpc.modelo.ministerio.dimension.UnidadFuncional unidadFuncional = new cpc.modelo.ministerio.dimension.UnidadFuncional();
					unidadFuncional.setDescripcion((String) objects[3]);
					orden.setUnidadFuncional(unidadFuncional);
				}

				if (objects[4] != null) {
					Productor productor = new Productor();
					productor.setNombres((String) objects[4]);
					orden.setProductor(productor);
				}
				if (objects[5] != null) {
					UnidadProductiva unidadProductiva = new UnidadProductiva();
					UbicacionDireccion ubicacionDireccion = new UbicacionDireccion();
					UbicacionSector ubicacionSector = new UbicacionSector();
					ubicacionSector.setNombre((String) objects[5]);
					ubicacionDireccion.setSector(ubicacionSector);
					unidadProductiva.setUbicacion(ubicacionDireccion);
					orden.setUnidadProductiva(unidadProductiva);

				}
				if (objects[6] != null) {
					LaborOrdenServicio laborOrdenServicio = new LaborOrdenServicio();
					Labor labor = new Labor();
					labor.setDescripcion((String) objects[6]);
					laborOrdenServicio.setLabor(labor);
					orden.setLaborOrden(laborOrdenServicio);
				}
				if (objects[7] != null) {
					Rubro rubro = new Rubro();
					rubro.setDescripcion((String) objects[7]);
					orden.setRubro(rubro);

				}
				if (objects[8] != null) {
					EstadoOrdenTrabajo estado = new EstadoOrdenTrabajo();
					estado.setDescripcion((String) objects[8]);
					orden.setEstado(estado);
				}

				ordenTrabajoMecanizados.add(orden);

			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return ordenTrabajoMecanizados;
	}
*/
	public OrdenTrabajoTransporte getOrdenTrabajoTransporteProject(
			OrdenTrabajoTransporte ordenTrabajoMecanizado) {

		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		try {
			ordenTrabajoMecanizado = (OrdenTrabajoTransporte) em.get(
					ordenTrabajoMecanizado.getClass(),
					ordenTrabajoMecanizado.getId());
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return ordenTrabajoMecanizado;
	}
	
	public OrdenTrabajoTransporte inizializar(OrdenTrabajoTransporte orden){
		if (orden!=null&&orden.getId()!=null){
	
		Session em = SessionDao.getInstance().getCurrentSession();
		
		Transaction tx = em.beginTransaction();
		OrdenTrabajoTransporte docu = (OrdenTrabajoTransporte) em.get(OrdenTrabajoTransporte.class, orden.getId());
		try {
			//System.out.println(docu.getEquipos());
		for (DetalleMaquinariaOrdenTrabajo detalleMaquinariaOrdenTrabajo  :docu.getEquipos()) {
			Hibernate.initialize(detalleMaquinariaOrdenTrabajo);
		}
			
		} catch (Exception e) {
			System.out.println(e);
			tx.rollback();
			// TODO: handle exception
		}
		
		
		
		return docu;
	}	else return null;}
	
}
