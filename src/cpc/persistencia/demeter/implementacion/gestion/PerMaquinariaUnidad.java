package cpc.persistencia.demeter.implementacion.gestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import cpc.modelo.demeter.administrativo.CotizacionTransporte;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.EstadoOrdenTrabajo;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.modelo.sigesp.basico.Activo;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerMaquinariaUnidad extends DaoGenerico<MaquinariaUnidad, Integer>{

	private static final long serialVersionUID = 2075696871271083087L;

	public PerMaquinariaUnidad() {
		super(MaquinariaUnidad.class);		
	}
	
	@SuppressWarnings("unchecked")
	public List<MaquinariaUnidad> getMaquinaria(UnidadFuncional unidad){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<MaquinariaUnidad>  criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<MaquinariaUnidad>) em.createCriteria(MaquinariaUnidad.class)
				.add(Restrictions.eq("unidad", unidad))
				.add(Restrictions.eq("operativo", Boolean.TRUE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public MaquinariaUnidad getPorMaquinariaYUnidadFuncional(UnidadFuncional unidad, Activo activo){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		MaquinariaUnidad  criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (MaquinariaUnidad) em.createCriteria(MaquinariaUnidad.class)
				.add(Restrictions.eq("unidad", unidad))
				.add(Restrictions.eq("activo", activo))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<MaquinariaUnidad> getMaquinariaTransporte(){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<MaquinariaUnidad>  criterio= null;
		List<String> tipo = new ArrayList<String>();
		tipo.add("00000003");
		tipo.add("00000004");
     	tx = em.beginTransaction();
		try{
			
			criterio = (List<MaquinariaUnidad>) em.createCriteria(MaquinariaUnidad.class,"maq")
					//.add(Restrictions.in("cat1.codigoCategoria", tipo))
					//.add(Restrictions.in("cat2.codigoCategoria", tipo))
					.add(Restrictions.or(Restrictions.in("cat1.codigoCategoria", tipo), Restrictions.in("cat2.codigoCategoria", tipo)))
					.createAlias("maq.activo", "act1")
					.createAlias("act1.categoria", "cat1")
					.createAlias("maq.maquinariaImpropia", "maqImp")
					.createAlias("maqImp.categoria", "cat2")
					
					
				.add(Restrictions.eq("operativo", Boolean.TRUE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<MaquinariaUnidad> getMaquinariasEnCampo() {
		List<EstadoOrdenTrabajo >estadostrabajo =new ArrayList<EstadoOrdenTrabajo>();
		estadostrabajo.add(new PerEstadoOrdenTrabajo().getCancelado());
		estadostrabajo.add(new PerEstadoOrdenTrabajo().getCargaCompleta());
		estadostrabajo.add(new PerEstadoOrdenTrabajo().getTerminado());
		
		List<MaquinariaUnidad> criterio =new ArrayList<MaquinariaUnidad>();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		try {

			DetachedCriteria Maquinarias = DetachedCriteria.forClass(DetalleMaquinariaOrdenTrabajo.class,"detalleorden")
					.createAlias("detalleorden.ordenTrabajo", "orden")
					.createAlias("detalleorden.maquinaria", "maquinaria")
					.add(Restrictions.not(Restrictions.in("orden.estado", estadostrabajo)))
				    .setProjection( Projections.property("maquinaria.id") );
			 
			
			 ProjectionList  projections = Projections.projectionList() ;
			 
		//	 projections.add(Projections.property("maquinaria"));
		//	 projections.add(Projections.property("maquinaria"));
			 
			
			
			
			  criterio= em.createCriteria(MaquinariaUnidad.class,"maquinaria")
				
					  .add( org.hibernate.criterion.Property.forName("maquinaria.id").in(Maquinarias))
					  .list();
					  
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		
		
				//maquinaria
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object> getMaquinariasEnCampo2() {
		List<EstadoOrdenTrabajo >estadostrabajo =new ArrayList<EstadoOrdenTrabajo>();
		estadostrabajo.add(new PerEstadoOrdenTrabajo().getCancelado());
		estadostrabajo.add(new PerEstadoOrdenTrabajo().getCargaCompleta());
		estadostrabajo.add(new PerEstadoOrdenTrabajo().getTerminado());
		
		List<Object> criterio =new ArrayList<Object>();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		tx = em.beginTransaction();
		try {

		/*	DetachedCriteria Maquinarias = DetachedCriteria.forClass(DetalleMaquinariaOrdenTrabajo.class,"detalleorden")
					.createAlias("detalleorden.ordenTrabajo", "orden")
					.createAlias("detalleorden.maquinaria", "maquinaria")
					.add(Restrictions.not(Restrictions.in("orden.estado", estadostrabajo)))
				    .setProjection( Projections.property("maquinaria.id") );
			 */
			
			 ProjectionList  projections = Projections.projectionList() ;
			 
			// projections.add(Projections.property("maquinaria"));
			 projections.add(Projections.groupProperty("maquinaria"),"maquinaria");
			 projections.add(Projections.groupProperty("implemento"),"implemento");
			 projections.add(Projections.groupProperty("operador"),"operador");
			 projections.add(Projections.groupProperty("orden.tipo"),"tipo");
			 projections.add(Projections.min("orden.fecha"),"fecha");
			 projections.add(Projections.groupProperty("orden.nroControl"),"numero");
			 projections.add(Projections.groupProperty("orden.unidadProductiva"),"unidadProductiva");
			 projections.add(Projections.groupProperty("orden.cotizacionTransporte"),"cotizacionTransporte");
			
		
			 
			
			
			criterio= em.createCriteria(DetalleMaquinariaOrdenTrabajo.class,"detallemaquinaria")
					  .createAlias("ordenTrabajo", "orden")
					//  .createAlias("ordenTrabajo.unidadProductiva", "unidadProductiva",CriteriaSpecification.LEFT_JOIN)
					//  .createAlias ("ordenTrabajo.cotizacionTransporte", "cotizacionTransporte")
					  .add(Restrictions.not(Restrictions.in("orden.estado", estadostrabajo)))
					  .setProjection(projections)
					  .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					   
					  .addOrder( Order.asc("fecha") )
					  .list();
					  
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		List<Object> criterioaux =new ArrayList<Object>();
		for (Object mapas : criterio) {
			
			HashMap<String , Object> mapa = (HashMap<String, Object>) mapas;
			Object[] maquinaria = new Object[8];
		
			
try {	maquinaria[0]=mapa.get("maquinaria");
maquinaria[1]=mapa.get("implemento");
maquinaria[2]=mapa.get("operador");
maquinaria[3]=mapa.get("tipo");
maquinaria[4]=mapa.get("fecha");
maquinaria[5]=mapa.get("numero");
if (mapa.get("unidadProductiva")==null)
	maquinaria[6]="";else{maquinaria[6]=((UnidadProductiva) mapa.get("unidadProductiva")).toString();}


if (mapa.get("cotizacionTransporte")==null)
	maquinaria[7]="";else {
 CotizacionTransporte	cotizacion=	((CotizacionTransporte)mapa.get("cotizacionTransporte"));
		maquinaria[7]=	"Direccion Origen: "+cotizacion.getDireccionOrigen().toString() + " Direccion Destino: "+cotizacion.getDireccionLlegada().toString();
	}



				
			} catch (Exception e) {
				e.printStackTrace();
			}
			criterioaux.add(maquinaria)	;
			
		}
		
				//maquinaria
		return criterioaux;
	}
}
