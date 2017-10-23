package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jdom2.Element;

import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioAsiento;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.DetalleDeposito;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.FormaPagoTransferencia;
import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Fecha;


public class PerDeposito extends DaoGenerico<Deposito, Integer>{

	private static final long serialVersionUID = 6394140925759968246L;

	public PerDeposito() {
		super(Deposito.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Deposito> getAll() throws ExcFiltroExcepcion{
		//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
			Transaction tx = null;
			Session em =SessionDao.getInstance().getCurrentSession();
			List<Deposito> nota = null;
	     	tx = em.beginTransaction();
			try{
				nota =  em.createCriteria(Deposito.class)
					.addOrder(Order.desc("id") )
					.list();
				tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}
			return nota;
		}
	@SuppressWarnings("unused") 
	public Deposito getDatos(Deposito entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Deposito docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (Deposito) em.load(Deposito.class, entrada.getId());
			for (DetalleDeposito item: docu.getCheques()){
			}
			
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	public void guardar(Deposito objeto,  List<FormaPago> formasdepago)  throws Exception{
	   	 if (objeto.getId() == null)
	   		 nuevo( objeto, formasdepago);
	   	 else
	   		 super.guardar(objeto, objeto.getId());
	}
	
   public void borrar(Deposito docu) {
	   List<FormaPago> cheques = new ArrayList<FormaPago>();
	   PerFormaPago perfp = new  PerFormaPago();
	   
	   for(DetalleDeposito cheque :docu.getCheques()){
		   System.out.println(".....>"+cheque);
		   cheques.add(perfp.getChequeDepositado(cheque));
	   }
	   Session em =SessionDao.getInstance().getCurrentSession();
	   //em =SessionDao.getInstance().getCurrentSession();
	   Transaction tx = null;
	   tx = em.beginTransaction();
	   for(FormaPago cheq: cheques){
		   cheq.setDepositado(false);
		   em.update(cheq); 
	   }
	   em.delete(em.merge(docu));
	   em.flush();
	   tx.commit();
    }
	
	public void nuevo(Deposito docu, List<FormaPago> formasdepago)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		try{
			Session em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
		     for (FormaPago item : formasdepago)
		     {
					item.setDepositado(true);
					em.update(item);
		     }
		    em.save(docu);
		    em.flush();
		    tx.commit(); 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al guardar deposito");
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<Deposito> getDepositos(Date inicio,Date fin){


		Session em =SessionDao.getInstance().getCurrentSession();
		List<Deposito> documentos = new ArrayList<Deposito>();
		Deposito documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery("" +
					"SELECT d.fecha, b.id, d.tipoCierre, t.id, t.descripcion, b.nroCuenta, d.nroDeposito, sum(d.montoTotal), d.fechaCierre " +
					"FROM Deposito  d " +
					"INNER Join d.cuentaBancaria  b " +
					"Inner Join b.banco  t " +
					"WHERE d.fecha   between :inicio and :fin " +
					"GROUP BY d.fecha, b.id, d.tipoCierre, t.id, t.descripcion, b.nroCuenta, d.nroDeposito, d.fechaCierre")
						.setDate("inicio", inicio)
						.setDate("fin", fin)
						.list();
			for (Object[] objs : query) {                 
				documento = new Deposito();
				documento.setFecha((Date) objs[0]);
				documento.setMontoTotal(new Double((Double) objs[7]).doubleValue());
				CuentaBancaria tipo = new CuentaBancaria();
				tipo.setId((Integer) objs[1]);
				tipo.setBanco(new Banco("0001", (String)objs[3], (String)objs[4]));
				tipo.setNroCuenta((String) objs[5]);
				documento.setCuentaBancaria(tipo);
				if (objs[8] == null){
					documento.setConcepto("Deposito Productor");
					documento.setFechaCierre(null);
				}
				else{
					documento.setConcepto(String.format("Deposito Dia (%1$td/%1$tm/%1$tY)",(Date)objs[8]));
					documento.setFechaCierre((Date)objs[8]);
				}
				documento.setNroDeposito((String)objs[6]);
				documentos.add(documento);
	        }
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return documentos;
		
	}
	
	



@SuppressWarnings("unchecked")
	public List<Deposito> getDepositos(Date fecha){
	

		Session em =SessionDao.getInstance().getCurrentSession();
		List<Deposito> documentos = new ArrayList<Deposito>();
		Deposito documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery("" +
					"SELECT d.fecha, b.id, d.tipoCierre, t.id, t.descripcion, b.nroCuenta, d.nroDeposito, sum(d.montoTotal), d.fechaCierre " +
					"FROM Deposito  d " +
					"INNER Join d.cuentaBancaria  b " +
					"Inner Join b.banco  t " +
					"WHERE d.fecha = :fecha " +
					"GROUP BY d.fecha, b.id, d.tipoCierre, t.id, t.descripcion, b.nroCuenta, d.nroDeposito, d.fechaCierre")
						.setDate("fecha", fecha)
						.list();
			for (Object[] objs : query) {                 
				documento = new Deposito();
				documento.setFecha((Date) objs[0]);
				documento.setMontoTotal(new Double((Double) objs[7]).doubleValue());
				CuentaBancaria tipo = new CuentaBancaria();
				tipo.setId((Integer) objs[1]);
				tipo.setBanco(new Banco("0001", (String)objs[3], (String)objs[4]));
				tipo.setNroCuenta((String) objs[5]);
				documento.setCuentaBancaria(tipo);
				if (objs[8] == null){
					documento.setConcepto("Deposito Productor");
					documento.setFechaCierre(null);
				}
				else{
					documento.setConcepto(String.format("Deposito Dia (%1$td/%1$tm/%1$tY)",(Date)objs[8]));
					documento.setFechaCierre((Date)objs[8]);
				}
				documento.setNroDeposito((String)objs[6]);
				documentos.add(documento);
	        }
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return documentos;
		
	}
	
	
	

	public Deposito getDepositoValido(Date fecha, FormaPagoDeposito pago) throws ExcEntradaInconsistente{
		
		CuentaBancaria cuenta = new PerFormaPago().getCuentaValida(pago);
		Deposito  deposito= new Deposito();
		deposito.setCuentaBancaria(cuenta);
		deposito.setNroDeposito(pago.getDocumento());
		deposito.setFechaRecepcion(fecha);
		deposito.setFecha(pago.getFecha());
		deposito.setTipoCierre(false);
		deposito.setMontoEfectivo(pago.getMonto());
		deposito.setMontoTotal(pago.getMonto()); 
		return deposito;
	}
	
	public List<FormaPago> getChequesDepositos(Deposito deposito){
/*		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPago>  salida= new ArrayList<FormaPago>();
		FormaPago	pago;
     	tx = em.beginTransaction();
		try{
			Deposito docu = (Deposito) em.load(Deposito.class, deposito.getId());
			for (DetalleDeposito detalle: docu.getCheques()){
				pago= new FormaPago();
				pago.setBanco(detalle.getBanco());
				pago.setCuenta(detalle.getCuenta());
				pago.setDepositado(true);
				pago.setDocumento(detalle.getDocumento());
				pago.setFecha(detalle.getFecha());
				pago.setMonto(detalle.getMonto());
				salida.add(pago);
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();	
			tx.rollback();
		}
		return salida;*/
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getDetalleIngresos(CierreDiario cierre, String cuentaCaja) {
		Transaction tx = null;
		List<Deposito> criterio= null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(Deposito.class)
				.add(Restrictions.eq("fecha",cierre.getFecha()))
				.add(Restrictions.eq("tipoCierre",Boolean.TRUE))
				.list();
			tx.commit();
			for (Deposito item :criterio){
				//***************************SALIDA DE CAJA TEMPORAL
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				renglonAsiento.setRenglon("DEPOSITO CAJA DEL "+item.getFechaCierre()+" A BANCO  "+item.getBancoCuenta()+" "+item.getStrCuenta());
				try{
					renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuentaCaja));
				}catch (Exception e) {
					renglonAsiento.setCuenta(new CuentaContable());
				}
				renglonAsiento.setDebe(item.getMontoTotal());
				renglonAsiento.setHaber(0.0);
				asiento.add(renglonAsiento);	
				//-----------------------------ENTRADA A BANCO
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				renglonAsiento.setRenglon("DEPOSITO CAJA DEL "+item.getFechaCierre()+" A BANCO  "+item.getBancoCuenta()+" "+item.getStrCuenta());
				try{
					renglonAsiento.setCuenta(new PerCuentaContable().buscarId(item.getCuentaBancaria().getStrCuentaContable()));
				}catch (Exception e) {
					renglonAsiento.setCuenta(new CuentaContable());
				}
				renglonAsiento.setDebe(0.0);
				renglonAsiento.setHaber(item.getMontoTotal());
				asiento.add(renglonAsiento);
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getDetalleIngresos(CierreDiario cierre) {
		Transaction tx = null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			List<Object[]> criterio =  em.createQuery("" +
					"SELECT d.fecha, b.descripcion, c.id, r.nroCuenta, sum(d.montoTotal) " +
					"FROM Deposito  d " +
					"INNER JOIN d.cuentaBancaria  r " +
					"INNER JOIN r.cuenta c " +
					"INNER JOIN r.banco  b " +
					"WHERE d.fecha = :fecha  and d.tipoCierre = :tipo " +
					"GROUP BY c.id, d.fecha, b.descripcion, r.nroCuenta ")
						.setDate("fecha", cierre.getFecha())
						.setBoolean("tipo", Boolean.FALSE)
						.list();
			tx.commit();
			for (Object[] objs : criterio) {
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				renglonAsiento.setRenglon("INGRESO BANCO "+objs[1]+" "+objs[3]);
				renglonAsiento.setCuenta(new PerCuentaContable().buscarId((String)objs[2]));
				renglonAsiento.setDebe((Double)objs[4]);
				renglonAsiento.setHaber(0.0);
				asiento.add(renglonAsiento);	
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}

	public Deposito enrriquecer(Deposito deposito) {
		// TODO Auto-generated method stub
		
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
     	tx = em.beginTransaction();
     		em.refresh(deposito);
     		deposito.getCheques().size();
		tx.commit();
		return deposito;
		
	}

	public Deposito buscar(String documento) {
		// TODO Auto-generated method stub
		Deposito deposito;
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
     	tx = em.beginTransaction();
     	deposito=   (Deposito) em.createCriteria(Deposito.class).add(Restrictions.eq("nroDeposito", documento)).uniqueResult();
    	tx.commit();
		return deposito;
	}

	

	public List<Deposito> getDepositos(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<Deposito> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createQuery("select new Deposito(d.id, cuenta,d.nroDeposito,d.montoEfectivo,d.montoTotal, " +
								" d.fecha, d.tipoCierre, d.fechaCierre, d.fechaRecepcion, d.concepto) "+ 
								" from Deposito d left join d.cuentaBancaria cuenta "+
								" where date_part('month',d.fechaRecepcion) = :mes " +
								" and date_part('year',d.fechaRecepcion) = :year ) ")
					.setParameter("mes",mes).setParameter("year",ano)
					.list();
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	public Element getDepositosXml(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Element xmlDepositos = new Element("depositos");

		//------------  TRANSFERENCIA  -----------------------------------------------

		List<Deposito> depositos = getDepositos(mes, ano);

		for(Deposito deposito: depositos)
		{	
			Element xmlPago = new Element("deposito");
			xmlDepositos.addContent(xmlPago);
			
			xmlPago.setAttribute("id",deposito.getId().toString());
			xmlPago.setAttribute("nroDeposito",deposito.getNroDeposito());
			xmlPago.setAttribute("montoTotal",new Double(deposito.getMontoTotal()).toString());
			xmlPago.setAttribute("fecha",Fecha.obtenerFecha(deposito.getFecha()));
			xmlPago.setAttribute("fechaRecepcion",Fecha.obtenerFecha(deposito.getFechaRecepcion()));
			xmlPago.setAttribute("concepto",deposito.getConcepto());

		}	
		 
		return xmlDepositos;
	}
 
	
}





