package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.Type;

import cpc.modelo.demeter.administrativo.Cheque;
import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;


public class PerRecibo extends DaoGenerico<Recibo, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1530515060555158298L;

	public PerRecibo() {
		super(Recibo.class);
	}
	
	public Recibo getDatos(Recibo entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Recibo docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (Recibo) em.get(Recibo.class, entrada.getId());
			for (FormaPago item: docu.getFormaspago()){}
			for(ReciboDocumentoFiscal pago : docu.getDocumentosFiscales()){}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return docu;
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Recibo> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Recibo> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM Recibo d  order by d.id desc").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	public List<Recibo> getAllCriteria() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Recibo> facturas = null;
		try{
			facturas = em.createCriteria(Recibo.class).list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	
	public List<Recibo> getAllproject() throws ExcFiltroExcepcion{
	
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Recibo> recibos;
		Transaction tx = em.beginTransaction();
		recibos = em.createQuery("select new Recibo(r.id,r.fecha,r.concepto,r.control, " +
							 " cliente.id,cliente.nombres,cliente.identidadLegal,cliente.direccion,r.monto,r.saldo,r.anulado) " +
							 " from Recibo r left join r.cliente as cliente ").list();
 		tx.commit(); 
		return recibos;
	}
	
	public List<Recibo> getAllActivosFactura() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Recibo> facturas = null;
		try{
			facturas = em.createCriteria(Recibo.class).add(Restrictions.eq("anulado", false))
					.add(Restrictions.isNull("contrato"))
					.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	
	public List<Recibo> getAllActivosSaldoCompleto() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Recibo> facturas = null;
		try{
			facturas = em.createCriteria(Recibo.class).add(Restrictions.eq("anulado", false))
					.add(Restrictions.eqProperty("monto","saldo"))
						.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	
	public void anular(Recibo docu){
		Transaction tx = null;
		Session em ;
		try{
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();

		    for(FormaPago item: docu.getFormaspago()){
		    	em.update(item);
		    }
			em.update(docu);
		    em.flush();
		    tx.commit();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	public void guardar(Recibo objeto, Long indice, ControlSede control) throws Exception{
	   	 if (indice == null)
	   		nuevo( objeto, control);
	   	 else
	   		 super.guardar(objeto, indice);
	}
	
	@SuppressWarnings("unchecked")
	public double getAnticipos(Contrato ctto)  throws ExcFiltroExcepcion{
		Transaction tx = null;
		double monto = 0.0;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Recibo.class)
				.add(Restrictions.eq("contrato",ctto))
				.add(Restrictions.eq("anulado",Boolean.FALSE))
				.list();
			tx.commit();
			for (Recibo item: criterio){
				monto += item.getMonto();
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion(e.getCause());
		}
		return monto;
	}
	
	
	
	
	public void nuevo( Recibo recibo, ControlSede control) throws HibernateException,  ExcFiltroExcepcion, ExcEntradaInconsistente {
		Transaction tx = null;
		Session em ;
		//Lista de Depositos Luego seran utilizados para hacer el Cierre Diario
		List<Deposito> depositos = new ArrayList<Deposito>();
		
		recibo.setFecha(control.getFechaCierreFactura());
		for (FormaPago item :recibo.getFormaspago())
		{
			item.setRecibo(recibo);
			
			if(item.getClass().equals(FormaPagoDeposito.class))
			{
			
				Deposito deposito = new PerDeposito().getDepositoValido(recibo.getFecha(), (FormaPagoDeposito) item);
				deposito.setConcepto("pago recibo de "+ recibo.getStrPagador());
				depositos.add(deposito);
			}
		}
		try{
			control.incrementarRecibo();
			recibo.setControl(Formateador.rellenarNumero(control.getControlRecibo(),"00000000"));
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
		
		    em.save(recibo);
			em.update(control);
			
			if ( depositos.size() > 0 ) {
				for(Deposito item : depositos){
					em.save(item);
				}
			}
			 
			em.flush();
		    tx.commit(); 
		}catch (Exception e) {
			e.printStackTrace();
			recibo.setId(null);
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al almacenar Recibo");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Recibo> getTodos(Date fecha){
		Transaction tx = null;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Recibo.class)
				.add(Restrictions.eq("fecha",fecha))
				.add(Restrictions.eq("anulado", Boolean.FALSE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<Recibo> getTodos(Date inicio,Date fin){
		Transaction tx = null;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Recibo.class)
				.add(Restrictions.between("fecha", inicio, fin))
				.add(Restrictions.eq("anulado", Boolean.FALSE))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	} 
	
	
	
	public List<Recibo> getall(DocumentoFiscal factura)
	{
		Transaction tx = null;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Recibo.class)
				.add(Restrictions.eq("documento",factura))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<Recibo> getAll(Cheque cheque) {
		// TODO Auto-generated method stub
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Recibo> recibo = new ArrayList<Recibo>();
		try{
			recibo = em.createQuery("select r from Recibo r " +
					                  " join r.formaspago fp  " +
					                  " left join fp.cheques che " +
					                  " where fp.cheque = :cheque " +
					                  " or che = :cheque ").setParameter("cheque",cheque)
					                   .list();  
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return recibo;  
	}
	
		
	
	
	public Recibo getRecibo(Recibo docu){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		docu =(Recibo) em.get(docu.getClass(), docu.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return docu;
	}

	public List<Recibo> getall(Cliente cliente) {
		// TODO Auto-generated method stub
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Recibo> recibos = new ArrayList<Recibo>();
		try{
			recibos = em.createQuery(" select r from Recibo r where r.cliente = :cliente and r.anulado = false and r.saldo > 0 ").setParameter("cliente",cliente).list();
			
			for(Recibo recibo : recibos)
			{
				for(FormaPago pago: recibo.getFormaspago());
				for(ReciboDocumentoFiscal factura : recibo.getDocumentosFiscales());
				
			}
			
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return recibos;  
	}
	
	public List<Recibo> getAll(Integer mes, Integer year ) {
		Transaction tx = null;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createQuery(" select new Recibo(r.id ,r.fecha, r.concepto, r.control," +
					" cliente.id,cliente.identidadLegal," +
					" r.monto,r.saldo,r.anulado) " +
					" from Recibo r left join r.cliente cliente " +
					" where date_part('month',dtm_fecha) = :mes and date_part('year',dtm_fecha) = :year ")
					.setParameter("mes",mes).setParameter("year",year)
					.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public List<Recibo> getAll(Integer dia, Integer mes, Integer year ) {
		Transaction tx = null;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createQuery(" select new Recibo(r.id ,r.fecha, r.concepto, r.control," +
					" cliente.id,cliente.identidadLegal," +
					" r.monto,r.saldo,r.anulado) " +
					" from Recibo r left join r.cliente cliente " +
					" where date_part('day',dtm_fecha) = :dia and date_part('month',dtm_fecha) = :mes and date_part('year',dtm_fecha) = :year ")
					.setParameter("mes",mes).setParameter("year",year).setParameter("dia",dia)
					.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public List<Recibo> getRecibosPagoElectronico(Cliente cliente){
		List<TipoFormaPago> PAgoElectronico = new PerTipoFormaPago().getTipoElectronico();
		List<TipoFormaPago> PAgoNoElectronico = new PerTipoFormaPago().getTipoNoElectronico();
		List<Long> ids = getIDRecibosPagoNOElectronico(cliente);
		Transaction tx = null;
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			Criteria criteria = em.createCriteria(Recibo.class)
					//.createAlias("formaspago", "formapago")
					//.createAlias("formapago.tipoFormaPago", "tipoFormaPago")
						.add(Restrictions.eq("cliente", cliente))
						.add(Restrictions.gt("saldo", new Double(0)))	;
			
			if (!(ids.size()==0)){
				criteria.add(Restrictions.not(Restrictions.in("id", ids)));	
			}
		criterio= criteria.list();
				
			//.add(Subqueries.notIn("id", recibos))
		
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
		
	public List<Long> getIDRecibosPagoNOElectronico(Cliente cliente){
		List<TipoFormaPago> PAgoElectronico = new PerTipoFormaPago().getTipoElectronico();
		List<TipoFormaPago> PAgoNoElectronico = new PerTipoFormaPago().getTipoNoElectronico();
		Transaction tx = null;
		List<Long> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
		
				 
				
			
		criterio=em.createCriteria(Recibo.class)
		.createAlias("formaspago", "formapago")
		.createAlias("formapago.tipoFormaPago", "tipoFormaPago")
		 .setProjection(  Projections.projectionList().add( Projections.property("id") ))
		 	.add(Restrictions.eq("cliente", cliente))
		.add(Restrictions. in("formapago.tipoFormaPago", PAgoNoElectronico))	
			.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
}
