package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.exception.ConstraintViolationException;
 









import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal; 
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCargo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ReciboNotaCargo;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerNotaCargo  extends DaoGenerico<NotaCargo, Integer>{ 
	
	
	 
	
	 

	public PerNotaCargo( ) {
 		super(NotaCargo.class);
		// TODO Auto-generated constructor stub
	}


	public List<NotaCargo> getAll(Cliente cliente) {
		// TODO Auto-generated method stub
		List<NotaCargo> lista = new ArrayList<NotaCargo>();
		Session session = SessionDao.getInstance().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
			lista = session.createCriteria(NotaCargo.class)
						.add(Restrictions.eq("cliente",cliente))
						.add(Restrictions.eq("cancelada",false))
						.add(Restrictions.eq("estado.id",1))
						.add(Restrictions.gt("montoSaldo",new Double(0)))
						.addOrder(Order.desc("fecha")).list();
		transaction.commit();
		return lista;
	}
	
	public List<NotaCargo> getAllSaldoActivas(Cliente cliente) {
		// TODO Auto-generated method stub
		List<NotaCargo> lista = new ArrayList<NotaCargo>();
		Session session = SessionDao.getInstance().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
			lista = session.createCriteria(NotaCargo.class)
						.add(Restrictions.eq("cliente",cliente))
						.add(Restrictions.eq("cancelada",false))
						.add(Restrictions.gt("montoSaldo",new Double(0)))
						.addOrder(Order.desc("fecha")).list();
		transaction.commit();
		return lista;
	}
	
	public void pagar(NotaCargo notaCargo) throws Exception {
		// TODO Auto-generated method stub
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
				for (ReciboNotaCargo  ite: notaCargo.getRecibos())
				{
					if(ite.getId() == null )
					{
						em.save(ite);
						em.saveOrUpdate(ite.getRecibo());
					}
				}
				em.saveOrUpdate(notaCargo);
			em.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new Exception("Error al intentar Alamcenar. "+ e.getMessage(), e.getCause());
		}
		
	}
	
	public void anular( NotaCargo docu) throws HibernateException 
	{
		//si tiene recibos asiciados hay que devolver el dinero  
		Transaction tx = null;
		Session em ;
		try{

			em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			
			//Guardo los recibos 
			for(ReciboNotaCargo recibo : docu.getRecibos())
			{	
				//guardo el recibo 
				em.update(recibo.getRecibo());
				em.update(recibo);
			}
			//Guardo el Documento Fiscal 
			em.update(docu);
			em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		
	}
	public void guardar(NotaCargo objeto, Integer indice, ControlSede control)  throws Exception{
	 	
	   	 if (indice == null)
	   		 //em.persist(objeto);
	   		nuevo( objeto, control);
	   	 else
	   		 //em.merge(objeto);
	   		 super.guardar(objeto, indice);
	   	
	}
	
	public void nuevo( NotaCargo docu, ControlSede control)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			control.incrementarControlNotaCargo();
			docu.setNroNotaCargo(control.getControlNotaCargo());
			docu.setSerie(control.getSerie());
			docu.setFecha(control.getFechaCierreFactura());
			em.save(docu);
			
			em.update(control);
		    //em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			docu.setId(null);
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Nota, "+ e.getMessage());
		}
	}
	public Integer getHijosActivos(Integer id) {
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		int cantidad = 0;
		Long criterio= null;
     	tx = em.beginTransaction();
		try{
			
			criterio = (Long) em.createQuery("SELECT count(n) FROM NotaCredito n INNER JOIN n.factura f where n.montoBase != 0.00 and f.id = :id")
				.setLong("id", id)
				.uniqueResult();
			cantidad +=criterio;
	
			criterio = (Long) em.createQuery("SELECT count(n) FROM NotaDebito n INNER JOIN n.factura f where n.montoBase != 0.00 and f.id = :id")
					.setLong("id", id)
					.uniqueResult();
			cantidad +=criterio;
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return cantidad;
	}

}