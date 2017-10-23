package cpc.persistencia.demeter.implementacion.mantenimiento;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.OrdenTrabajoMecanizado;
import cpc.modelo.demeter.mantenimiento.Repuesto;
import cpc.modelo.demeter.mantenimiento.RepuestoEquivalente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.administrativo.PerContratoMecanizado;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoDocumento;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerRepuesto extends DaoGenerico<Repuesto, Integer>{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1368267865384465L;


	public PerRepuesto() {
		super(Repuesto.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RepuestoEquivalente> getEquivalente(Repuesto repuesto){
		
	
			Transaction tx = null;
			
			List<RepuestoEquivalente> criterio= null;
			Session em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			try{
				criterio = em.createCriteria(RepuestoEquivalente.class)
					.add(Restrictions.eq("repuestoOriginal",repuesto))
					.list();
				tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}
			return criterio;
		}
	
	
	public Repuesto getRepuestoarticulo(ArticuloVenta articulo ) throws ExcFiltroExcepcion{
		Transaction tx = null;
		Repuesto salida = null; 
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			salida = (Repuesto) em.createCriteria(Repuesto.class)
				.add(Restrictions.eq("articuloVenta",articulo))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return salida;
	}
	
	
	public void guardar(Repuesto objeto)  throws ExcFiltroExcepcion{
		System.out.println("Guardando");
		
		
		
		
		Session em = null;
		
		
		Transaction tx = null;
		try{
			
			
			
			em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
		  
		   		System.out.println("Modificando");
		   		em.saveOrUpdate(objeto);
		   	
		   	tx.commit();
		
		}catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando repuesto, "+ e.getMessage());
		}
	
	
		}
}
