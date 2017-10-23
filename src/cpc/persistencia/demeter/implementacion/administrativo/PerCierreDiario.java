package cpc.persistencia.demeter.implementacion.administrativo;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.type.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xpath.internal.Expression;

import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioAsiento;
import cpc.modelo.demeter.administrativo.CierreDiarioCuentaAdelanto;
import cpc.modelo.demeter.administrativo.CierreDiarioCuentaCobrar;
import cpc.modelo.demeter.administrativo.CierreDiarioCuentaPagar;
import cpc.modelo.demeter.administrativo.CierreDiarioDeposito;
import cpc.modelo.demeter.administrativo.CierreDiarioDocumento;
import cpc.modelo.demeter.administrativo.CierreDiarioImpuesto;
import cpc.modelo.demeter.administrativo.CierreDiarioReversoRecibo;
import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Cotizacion;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.modelo.sigesp.indice.SedePK;
import cpc.negocio.demeter.administrativo.NegocioCierreDiario;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerCuentaBancaria;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Fecha;

public class PerCierreDiario extends DaoGenerico<CierreDiario, Integer>{

	
	private static final long serialVersionUID = -5800115074389097938L;
 
	
	
	public PerCierreDiario() {
		super(CierreDiario.class); 
		
	}

	@SuppressWarnings("unused") 
	public CierreDiario getDatos(CierreDiario entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		CierreDiario docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (CierreDiario) em.load(CierreDiario.class, entrada.getId());
			for (CierreDiarioDocumento item: docu.getDocumentos()){
				for (ImpuestoDocumentoFiscal impuesto: item.getDocumento().getImpuestos()){
				}
			}
			for (CierreDiarioCuentaCobrar item: docu.getCuentasCobrar()){
			}
			for (CierreDiarioCuentaPagar item: docu.getCuentasPagadas()){
			}
			for (CierreDiarioCuentaAdelanto item: docu.getCuentasAdelantos()){
			}
			for (CierreDiarioImpuesto item: docu.getImpuestos()){
			}
			for (CierreDiarioReversoRecibo item: docu.getReversos())
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioImpuesto> getImpuestosCierre(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioImpuesto> impuestos = new ArrayList<CierreDiarioImpuesto>();
		CierreDiarioImpuesto impuesto;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery(
					"SELECT  d.fecha, i.id, i.descripcion, i.porcentaje, l.porcentaje as nuevoPorcentaje, sum(l.base) as base, sum(l.monto) as monto " +
					"FROM DocumentoFiscal d Inner join d.impuestos l" +
					" inner join l.impuesto i " +
					"where d.fecha = :fecha group by d.fecha, i.id, i.descripcion, i.porcentaje, l.porcentaje ")
						.setDate("fecha", fecha2)
						.list();
			for (Object[] objs : query) {                 
				impuesto = new CierreDiarioImpuesto();
				impuesto.setImpuesto(new Impuesto((Integer) objs[1], (String)objs[2], new Double((Double) objs[4]).doubleValue()));
				impuesto.setBase(new Double((Double) objs[5]).doubleValue() );
				impuesto.setPorcentaje(new Double((Double) objs[4]).doubleValue());
				impuesto.setMonto(new Double((Double) objs[6]).doubleValue());
				impuestos.add(impuesto);
	        }
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//return facturas;
		return impuestos;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioDocumento> getFacturasCierre(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioDocumento> documentos = new ArrayList<CierreDiarioDocumento>();
		CierreDiarioDocumento documento;
		Transaction tx = em.beginTransaction();
		try{
			List<DocumentoFiscal> query = em.createQuery("" +
					"SELECT  d " +
					"FROM DocumentoFiscal d " +
					"where d.fecha = :fecha ")
						.setDate("fecha", fecha2)
						.list();
			for (DocumentoFiscal objs : query) {                 
				documento = new CierreDiarioDocumento();
				objs.getTotalImpuesto();
				documento.setDocumento(objs);
				documentos.add(documento);
	        }
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}
	


	/*
	 *  carga todo los documentos fiscales tipo nota de debito dada la fecha 
	 * */
	public List<CierreDiarioCuentaCobrar> getCuentasCobrar(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioCuentaCobrar> documentos = new ArrayList<CierreDiarioCuentaCobrar>();
		CierreDiarioCuentaCobrar documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery("" +
					"SELECT  d.fecha, a.id,  sum(d.montoTotal) " +
					"FROM DocumentoFiscal  d " +
					"INNER JOIN d.beneficiario  c " +
					"INNER JOIN c.clienteAdministrativo  a " +
					"INNER JOIN d.tipoDocumento  t " +
					"WHERE d.fecha = :fecha and not (t.tipoFactura =:factu and t.haber = :haber) " +
					"GROUP BY d.fecha, a.id")
						.setDate("fecha", fecha2)
						.setBoolean("factu", Boolean.FALSE)
						.setBoolean("haber", Boolean.FALSE)
						.list();
			for (Object[] objs : query) {                 
				documento = new CierreDiarioCuentaCobrar();
				documento.setCliente(new ClienteAdministrativo(new Integer((Integer) objs[1]).intValue()));
				documento.setIncremento(true);
				documento.setMonto(new Double((Double) objs[2]).doubleValue());
				documentos.add(documento);
	        }
			tx.commit();
			for(CierreDiarioCuentaCobrar cuenta: documentos){
				cuenta.setCliente(new PerClienteAdministrativo().buscarId(cuenta.getCliente().getId()));
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}

	/* carga los documentos fiscales tipo notadebito  **/
	public List<CierreDiarioCuentaPagar> getCuentasPagar(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioCuentaPagar> documentos = new ArrayList<CierreDiarioCuentaPagar>();
		CierreDiarioCuentaPagar documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery(
					"SELECT d.fecha, a.id,  sum(d.montoTotal) " +
					"FROM  DocumentoFiscal  d " +
					"INNER JOIN d.beneficiario  c " +
					"INNER JOIN c.clienteAdministrativo  a " +
					"INNER JOIN d.tipoDocumento  t " +
					"WHERE d.fecha = :fecha and (t.tipoFactura =:factu and t.haber = :haber) " +
					"GROUP BY d.fecha, a.id")
				.setDate("fecha", fecha2)
				.setBoolean("factu", Boolean.FALSE)
				.setBoolean("haber", Boolean.FALSE)
						.list();
			for (Object[] objs : query) {                 
				documento = new CierreDiarioCuentaPagar();
				documento.setCliente(new ClienteAdministrativo(new Integer((Integer) objs[1]).intValue()));
				documento.setIncremento(true);
				documento.setMonto(Math.abs(new Double((Double) objs[2]).doubleValue()));
				documentos.add(documento);
	        }
			tx.commit();
			for(CierreDiarioCuentaPagar cuenta: documentos){
				cuenta.setCliente(new PerClienteAdministrativo().buscarId(cuenta.getCliente().getId()));
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}
	
	

	/*
	 * carga los recibos del dia :-D 
	 * */
	public List<CierreDiarioCuentaAdelanto> getCuentasAdelantos(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioCuentaAdelanto> documentos = new ArrayList<CierreDiarioCuentaAdelanto>();
		CierreDiarioCuentaAdelanto documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery(
					"SELECT  r.fecha, c.id,  sum(r.monto) " +
					"FROM Recibo  r " +
					"INNER JOIN r.cliente c " +
					"WHERE r.fecha = :fecha  " +
					"GROUP BY r.fecha, c.id")
						.setDate("fecha", fecha2)
						.list();
			tx.commit();
			for (Object[] objs : query) {                 
				documento = new CierreDiarioCuentaAdelanto();
				documento.setCliente(new PerCliente().buscarId((Integer) objs[1]));
				documento.setMonto(new Double((Double) objs[2]).doubleValue());
				documentos.add(documento);
	        }

			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}
	
	
	/*
	 * carga todo los recibos dada la fecha 
	 * */
	public List<CierreDiarioCuentaCobrar> getCuentasCobradas(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioCuentaCobrar> documentos = new ArrayList<CierreDiarioCuentaCobrar>();
		CierreDiarioCuentaCobrar documento;
		Transaction tx = em.beginTransaction();
		try{

			List<Object[]> query = em.createQuery(
					"SELECT  r.fecha, a.id,  sum(r.monto) " +
					"FROM Recibo  r " + 
					"INNER JOIN r.cliente  c " +
					"INNER JOIN c.clienteAdministrativo  a " +
					"WHERE r.fecha = :fecha  " +
					"GROUP BY r.fecha, a.id")
						.setDate("fecha", fecha2)
						.list();
			for (Object[] objs : query) {                 
				documento = new CierreDiarioCuentaCobrar();
				documento.setCliente(new ClienteAdministrativo(new Integer((Integer) objs[1]).intValue()));
				documento.setIncremento(false);
				documento.setMonto(new Double((Double) objs[2]).doubleValue());
				documentos.add(documento);
	        }
			tx.commit();
			for(CierreDiarioCuentaCobrar cuenta: documentos){
				cuenta.setCliente(new PerClienteAdministrativo().buscarId(cuenta.getCliente().getId()));
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioCuentaPagar> getCuentasPagadas(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioCuentaPagar> documentos = new ArrayList<CierreDiarioCuentaPagar>();
		CierreDiarioCuentaPagar documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery(
					"SELECT  r.fecha, a.id,  sum(r.monto) " +
					"FROM NotaPago  r " +
					"INNER JOIN r.nota  n " +
					"INNER JOIN n.beneficiario  b " +
					"INNER JOIN b.clienteAdministrativo  a " +
					"WHERE r.fecha = :fecha  " +
					"GROUP BY r.fecha, a.id")
						.setDate("fecha", fecha2)
						.list();
			for (Object[] objs : query) {                 
				documento = new CierreDiarioCuentaPagar();
				documento.setCliente(new ClienteAdministrativo(new Integer((Integer) objs[1]).intValue()));
				documento.setIncremento(false);
				documento.setMonto(new Double((Double) objs[2]).doubleValue());
				documentos.add(documento);
	        }
			tx.commit();
			for(CierreDiarioCuentaPagar cuenta: documentos){
				cuenta.setCliente(new PerClienteAdministrativo().buscarId(cuenta.getCliente().getId()));
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}

	@SuppressWarnings("unchecked")
	public List<CierreDiarioCuentaCobrar> getCuentasCobradasNotas(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<CierreDiarioCuentaCobrar> documentos = new ArrayList<CierreDiarioCuentaCobrar>();
		CierreDiarioCuentaCobrar documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery(
					"SELECT  r.fecha, a.id,  sum(r.monto) " +
					"FROM NotaPago  r " +
					"INNER JOIN r.nota  n " +
					"INNER JOIN n.beneficiario  b " +
					"INNER JOIN b.clienteAdministrativo  a " +
					"WHERE r.fecha = :fecha  " +
					"GROUP BY r.fecha, a.id")
						.setDate("fecha", fecha2)
						.list();
			for (Object[] objs : query) {                 
				documento = new CierreDiarioCuentaCobrar();
				documento.setCliente(new ClienteAdministrativo(new Integer((Integer) objs[1]).intValue()));
				documento.setIncremento(false);
				documento.setMonto(new Double((Double) objs[2]).doubleValue());
				documentos.add(documento);
	        }
			tx.commit();
			for(CierreDiarioCuentaCobrar cuenta: documentos){
				cuenta.setCliente(new PerClienteAdministrativo().buscarId(cuenta.getCliente().getId()));
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioDeposito> getDepositosFormaPago(Date fecha){
		/*
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPago> criterio= null;
		List<CierreDiarioDeposito> depositos= new ArrayList<CierreDiarioDeposito>();
		CierreDiarioDeposito deposito;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery(
					"SELECT  f " +
					"FROM FormaPago  f " +
					"INNER JOIN f.recibo  r " +
					"INNER JOIN f.tipoFormaPago  t " +
					"WHERE r.fecha = :fecha  " +
					"and t.usaCuenta = true")
						.setDate("fecha", fecha)
						.list();

			tx.commit();
			PerCuentaBancaria percuenta = new PerCuentaBancaria();
			for(FormaPago pago: criterio){
				deposito = new CierreDiarioDeposito();
				deposito.setCuenta(percuenta.getCuentaBancaria(pago.getBanco(), pago.getCuenta()));
				deposito.setMonto(pago.getMonto());
				deposito.setNroDeposito(pago.getDocumento());
				deposito.setDepositado(false);
				depositos.add(deposito);
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return depositos;
		*/
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioDeposito> getDepositos(Date fecha){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Deposito> criterio= null;
		List<CierreDiarioDeposito> depositos = new ArrayList<CierreDiarioDeposito>();
		
     	tx = em.beginTransaction();
		try{
			criterio = (List<Deposito>) em.createCriteria(Deposito.class)
				.add(Restrictions.eq("fecha",fecha))
				.list();
			tx.commit();
			CierreDiarioDeposito deposito;
			for(Deposito item: criterio){
				deposito = new CierreDiarioDeposito();
				deposito.setCuenta(item.getCuentaBancaria());
				deposito.setMonto(item.getMontoTotal());
				deposito.setNroDeposito(item.getNroDeposito());
				deposito.setDepositado(true);
				depositos.add(deposito);
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return depositos;
	}
	
	public void guardar(CierreDiario objeto, SedePK sede, List<CierreDiarioAsiento> asientos, ControlSede control)  throws Exception {
		if (objeto.getId() == null){
			guardarNuevo(objeto, sede, asientos, control);
		}else
			super.guardar(objeto, objeto.getId());
	}
	
	public void guardarNuevo(CierreDiario objeto, SedePK sede, List<CierreDiarioAsiento>  asientos, ControlSede control)  throws Exception{
		Transaction tx = null;
		try{
			Date fecha = new Date();
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(control.getFechaCierreFactura());
			calendario.add(Calendar.DATE, 1);
			fecha = calendario.getTime();
			Session em =SessionDao.getInstance().getCurrentSession();
	     	tx = em.beginTransaction();
	     	em.save(objeto);
	     	for (CierreDiarioAsiento item: asientos){
	     		item.setCierreDiario(objeto);
	     		item.setSede(control.getSede());
	     		if (!(item.getCuenta() == null || item.getCuenta().getId() == null))
	     			em.save(item);
	     	}
	     	for (CierreDiarioCuentaAdelanto item: objeto.getCuentasAdelantos()){
	     		item.setCierreDiario(objeto);
	     		em.save(item);
	     	}
	     	for (CierreDiarioCuentaCobrar item: objeto.getCuentasCobrar()){
	     		item.setCierreDiario(objeto);
	     		em.save(item);
	     	}
	     	for (CierreDiarioDocumento item: objeto.getDocumentos()){
	     		item.setCierreDiario(objeto);
	     		em.save(item);
	     	}
	     	for (CierreDiarioCuentaPagar item: objeto.getCuentasPagadas()){
	     		item.setCierreDiario(objeto);
	     		em.save(item);
	     	}
	     	Date fecha2 = objeto.getFecha();
	     	CerrarCotizaciones(em,fecha2);
	     	control.setFechaCierreFactura(fecha);
	     	em.update(control);
		   	tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("No se pudo Guardar Cierre");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getCuentasxCobrarYPagar(CierreDiario cierre, String cuentaCobro, String CuentaPago) {
		Transaction tx = null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			List<Object[]> criterio =  em.createQuery("" +
					"SELECT d.fecha, t.haber, sum(d.montoTotal) " +
					"FROM DocumentoFiscal  d " +
					"INNER Join d.tipoDocumento  t " +
					"INNER Join d.estado  e " +
					"WHERE d.fecha = :fecha  and e.anulado = :anulado " +
					"GROUP BY d.fecha, t.haber ")
						.setDate("fecha", cierre.getFecha())
						.setBoolean("anulado", Boolean.FALSE)
						.list();
			tx.commit();
			for (Object[] objs : criterio) {
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				if ((Boolean)objs[1] != true ){
					renglonAsiento.setRenglon("CUENTAS POR COBRAR ");
					renglonAsiento.setDebe((Double)objs[2]);
					renglonAsiento.setHaber(0.0);	
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuentaCobro));	
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
				}else{
					renglonAsiento.setRenglon("CUENTAS x PAGAR");
					renglonAsiento.setDebe(0.0);
					renglonAsiento.setHaber((Double)objs[2]);	
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId(CuentaPago));
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
				}
				
				asiento.add(renglonAsiento);	
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getCuentasCobradasYAdelantos(CierreDiario cierre, String cuentaCobro, String CuentaAdelanto) {
		Transaction tx = null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{  

			List<Recibo> recibos = em.createCriteria(Recibo.class).add(Restrictions.eq("fecha",cierre.getFecha())).list();
			
			/*List<Object[]> criterio =  em.createQuery(
					"SELECT d.dtm_fecha, case when d.documento is not null then 0 else 1 end, sum(d.monto) " +
					"FROM Recibo  d " +  
					"WHERE d.dtm_fecha = :fecha  and d.anulado = :anulado " +
					"GROUP BY d.dtm_fecha, case when d.documento is not null then 0 else 1 end")
						.setDate("fecha", cierre.getFecha())
						.setBoolean("anulado", Boolean.FALSE)
						.list();*/
			// Recibos con Facturas las considero Adelantos 
			// Recibos con Facturas saldadas las considero Pagadas Cuentas por Cobrar cobrado 
			//
			tx.commit();
			for (Recibo objs : recibos) 
			{
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				
			//	if (objs[1] != null || ((Boolean)objs[1]))  
				if (objs.getDocumentosFiscales().size() == 0) // sin documento asigando  
				{
					renglonAsiento.setRenglon("CUENTAS POR COBRAR (COBRADO)");
					renglonAsiento.setDebe(0.0);
					renglonAsiento.setHaber(objs.getMonto());	
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuentaCobro));	
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
				}  
				else // con documento asignado 
				{
					renglonAsiento.setRenglon("CUENTAS ADELANTO");
					renglonAsiento.setDebe(0.0);
					renglonAsiento.setHaber(objs.getMonto());	
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId(CuentaAdelanto));
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
				}
				
				asiento.add(renglonAsiento);	
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getCuentasCobradasNotas(CierreDiario cierre, String cuentaCobro) {
		Transaction tx = null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			List<Object[]> criterio =  em.createQuery("" +
					"SELECT d.fecha, sum(d.monto) " +
					"FROM NotaPago  d " +
					"WHERE d.fecha = :fecha  and d.anulado = :anulado " +
					"GROUP BY d.fecha")
						.setDate("fecha", cierre.getFecha())
						.setBoolean("anulado", Boolean.FALSE)
						.list();
			tx.commit();
			for (Object[] objs : criterio) {
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				renglonAsiento.setRenglon("CUENTAS POR COBRAR (COBRADO NOTAS)");
				renglonAsiento.setDebe(0.0);
				renglonAsiento.setHaber((Double)objs[1]);	
				try{
					renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuentaCobro));	
				}catch (Exception e) {
					renglonAsiento.setCuenta(new CuentaContable());
				}
				asiento.add(renglonAsiento);	
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getCuentasPagadas(CierreDiario cierre, String cuentaPagada) {
		Transaction tx = null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			List<Object[]> criterio =  em.createQuery("" +
					"SELECT d.fecha, sum(d.monto) " +
					"FROM ConsumoCredito  d " +
					"WHERE d.fecha = :fecha  and d.anulado = :anulado " +
					"GROUP BY d.fecha")
						.setDate("fecha", cierre.getFecha())
						.setBoolean("anulado", Boolean.FALSE)
						.list();
			tx.commit();
			for (Object[] objs : criterio) {
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				renglonAsiento.setRenglon("CUENTAS POR PAGAR (PAGADO)");
				renglonAsiento.setDebe((Double)objs[1]);
				renglonAsiento.setHaber(0.0);	
				try{
					renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuentaPagada));	
				}catch (Exception e) {
					renglonAsiento.setCuenta(new CuentaContable());
				}
				asiento.add(renglonAsiento);	
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return asiento;
	}
	
	public void CerrarCotizaciones(Session em, Date fecha) throws ExcFiltroExcepcion{
	
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.add(Calendar.DATE, -3);
		Date fecha2 = calendario.getTime();
	//	Date fecha2 = fecha;
     //            fecha2.setDate(-3);
		
		
		/* * 
		 * 
		 * 
		Cotizacion ejemplo = new Cotizacion();
        ejemplo.setFacturado(false);
        Example eje =  Example.create(ejemplo);
        eje.*/
    //    ejemplo.setFecha(fecha)

	 //List<Cotizacion> criterio = em.createCriteria(Cotizacion.class,"coti").add(Property.forName(coti.fechaDesde). ("coti.fechaDesde",fecha + "coti.diasVigencia")).list();
	//	DetachedCriteria dc = DetachedCriteria.forClass(Cotizacion.class, "coti2");
		//dc.createAlias("coti2.fechaDesde", "vigen");
	//	dc.add(Restrictions.eqProperty("coti.id", "coti2.id"));
	//	dc.setProjection(Projections.id())
		;
		
		@SuppressWarnings("unchecked")
	//	List<Cotizacion> criterio =	em.createCriteria(Cotizacion.class, "coti").add(Restrictions.eq("facturado", false)).add(Subqueries.notExists(dc)).list();
		//.add(Restrictions.le ("fechaDesde", (this.fecha ) ) ).
		//	add(Restrictions.eq("facturado", false)).list();
	
		List<Cotizacion> criterio = em.createQuery("select d from Cotizacion d where d.fechaDesde < (DATE (:fecha) -  d.diasVigencia) and d.facturado=:factura and d.estado=:estado and d.expediente=:expediente").setDate("fecha", fecha)
		.setBoolean("factura", false)
		.setBoolean("expediente", false)
		.setEntity("estado", new EstadoContrato(EstadoContrato.ESTADO_CULMINADO, "culminado")).list();
    
		List<SalidaExternaArticulo> salidas = new ArrayList<SalidaExternaArticulo>();
	 
    for (Cotizacion coti : criterio) {
    //	salidas.add(coti.getSalidaExternaArticulo());
		
		for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo : coti.getSalidaExternaArticulo().getDetalleSalidaExternaArticulos()) {
			new PerArticuloAlmacenCantidad().actualizarCantidades(detalleSalidaExternaArticulo.getAlmacenOrigen(), detalleSalidaExternaArticulo.getArticuloVenta(), detalleSalidaExternaArticulo.getCantidad(), true, em);
		}
		coti.getSalidaExternaArticulo().setEstado(false);
		coti.setEstado(new EstadoContrato(EstadoContrato.ESTADO_ANULADO,""));
	
		em.saveOrUpdate(coti);
		em.saveOrUpdate(coti.getSalidaExternaArticulo());
	}
    
    
		
		
	}
	
	public void CerrarCotizacionescriteria(Session em, Date fecha) throws ExcFiltroExcepcion{
		

				
	
	@SuppressWarnings("deprecation")
	Criteria criteria = em.createCriteria(Cotizacion.class, "coti").

		add(  Restrictions.sqlRestriction ("dtm_fechadesde < (Date (?))-int_diasvigencia", fecha,
                              Hibernate.DATE )).
		add(Restrictions.eq("facturado", false));
	

	@SuppressWarnings("unchecked")
	List<Cotizacion> criterio = criteria.list();
	
	 
    for (Cotizacion coti : criterio) {
    //	salidas.add(coti.getSalidaExternaArticulo());
		
		for (DetalleSalidaExternaArticulo detalleSalidaExternaArticulo : coti.getSalidaExternaArticulo().getDetalleSalidaExternaArticulos()) {
			new PerArticuloAlmacenCantidad().actualizarCantidades(detalleSalidaExternaArticulo.getAlmacenOrigen(), detalleSalidaExternaArticulo.getArticuloVenta(), detalleSalidaExternaArticulo.getCantidad(), true, em);
		}
		coti.getSalidaExternaArticulo().setEstado(false);
		coti.setEstado(new EstadoContrato(EstadoContrato.ESTADO_ANULADO,""));
	
		em.saveOrUpdate(coti);
		em.saveOrUpdate(coti.getSalidaExternaArticulo());
	}
    
    
		
		
	}
	
	
	public List<CierreDiario> getCierreParaDepositar(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List<CierreDiario> criterio = new ArrayList<CierreDiario>();
     	tx = em.beginTransaction();
     	List<Integer> tiposforma = new ArrayList<Integer>();
     	tiposforma.add(1);
     	tiposforma.add(2);	
     	try {
     		
     		DetachedCriteria subquery = DetachedCriteria.forClass(FormaPago.class, "forma")
					.add(Restrictions.eq("forma.depositado", false))
			//		.add(Restrictions.in("ug.contrato.id", ids))
					.add(Restrictions.in("forma.tipoFormaPago", tiposforma))
					//.createAlias("ug.laborOrden", "laborOrden")
					//.createAlias("laborOrden.labor","labor")
					//.add(Restrictions.eqProperty("labor.id", "deproducto.id"))
					.setProjection(Projections.groupProperty("forma.fecha") );
     		
    		criterio=em.createCriteria(CierreDiario.class,"cierre")
    				.add( org.hibernate.criterion.Property.forName("cierre.fecha").in(subquery)).list();
    				//.add(Restrictions.in("fecha", values))
     		
     		
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
		}

		return criterio;
	}
	 
	
	
	
}
