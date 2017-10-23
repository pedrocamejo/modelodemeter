package cpc.persistencia.demeter.implementacion.administrativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.jdom2.Element;
import cpc.modelo.demeter.administrativo.CierreDiario;
import cpc.modelo.demeter.administrativo.CierreDiarioAsiento;
import cpc.modelo.demeter.administrativo.DetalleDeposito;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.FormaPagoCheque;
import cpc.modelo.demeter.administrativo.FormaPagoDeposito;
import cpc.modelo.demeter.administrativo.FormaPagoEfectivo;
import cpc.modelo.demeter.administrativo.FormaPagoPunto;
import cpc.modelo.demeter.administrativo.FormaPagoRetencion;
import cpc.modelo.demeter.administrativo.FormaPagoTransferencia;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.sigesp.basico.Banco;
import cpc.modelo.sigesp.basico.CuentaBancaria;
import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerCuentaContable;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;


public class PerFormaPago extends DaoGenerico<FormaPago, Long>{
	
	private static final long serialVersionUID = 1389180990584681224L;


	public PerFormaPago() {
		super(FormaPago.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FormaPago> getPagosCaja(Date fecha2){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPago> documentos = new ArrayList<FormaPago>();
		Transaction tx = em.beginTransaction();
		try{
			documentos =  em.createQuery("select fp from FormaPago fp " +
												" left join fp.banco b " +
												" where fp.recibo.fecha = :fecha and fp.depositado = :depositado ")
												.setDate("fecha",fecha2)
												.setBoolean("depositado",Boolean.FALSE)
												.list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return documentos;
	}
	/*
	
	@SuppressWarnings("unchecked")
	public List<FormaPago> getDepositosCaja(Date fecha2){

		/*	TipoFormaPago tipo = new PerTipoFormaPago().getTipoDeposito();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPago> documentos = new ArrayList<FormaPago>();
		FormaPago documento;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery("" +
					"SELECT d.fecha, t.id, t.descripcion, b.codigoEmpresa, b.id, b.descripcion, d.cuenta, d.documento, sum(d.monto) " +
					"FROM FormaPago  d " +
					"Inner Join d.recibo  r " +
					"Inner Join d.banco  b " +
					"Inner Join d.tipoFormaPago  t " +
					"WHERE r.fecha = :fecha  and  t.usaCuenta = true " +
					"GROUP BY d.fecha, t.id, t.descripcion, b.codigoEmpresa, b.id, b.descripcion, d.cuenta, d.documento")
						.setDate("fecha", fecha2)
						.list();
			for (Object[] objs : query) {                 
				documento = new FormaPago();
				documento.setFecha((Date) objs[0]);
				documento.setMonto(new Double((Double) objs[8]).doubleValue());
				documento.setTipoFormaPago(tipo);
				try{
					documento.setBanco(new Banco((String)objs[3],(String)objs[4],(String)objs[5]));
					documento.setCuenta((String)objs[6]);
					documento.setDocumento((String)objs[7]);
				}catch(NullPointerException e){
					e.printStackTrace();
				}
				
				documentos.add(documento);
	        }
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return documentos;
	
		
		return null;
		
	}
		*/
	
	@SuppressWarnings("unchecked")
	public List<FormaPago> getAllNoEfectivo(){
		Transaction tx = null;
		
		List<FormaPago> criterio= null;
		TipoFormaPago tipoEfectivo= new PerTipoFormaPago().getTipoEfectivo();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = (List<FormaPago>) em.createCriteria(FormaPago.class)
				.add(Restrictions.ne("tipoFormaPago",tipoEfectivo))
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
	public List<FormaPago> getAllFecha(Date inicio,Date fin){
		Transaction tx = null;
		
		List<FormaPago> criterio= null;
		TipoFormaPago tipoDeposito= new PerTipoFormaPago().getTipoDeposito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = (List<FormaPago>) em.createCriteria(FormaPago.class)
				.add(Restrictions.between("fechaRecepcion",inicio,fin))
				.add(Restrictions.ne("tipoFormaPago",tipoDeposito))
				.addOrder(Order.desc("tipoFormaPago"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
 
	
	@SuppressWarnings("unchecked")
	public List<FormaPago> getSoloDepositos(){
		Transaction tx = null;
		
		List<FormaPago> criterio= null;
		TipoFormaPago tipoDeposito= new PerTipoFormaPago().getTipoDeposito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = (List<FormaPago>) em.createCriteria(FormaPago.class)
				.add(Restrictions.eq("tipoFormaPago",tipoDeposito))
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
	public List<FormaPagoCheque> getChequeSinDepositar( )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoCheque> criterio  = new ArrayList<FormaPagoCheque>();

        criterio = (List<FormaPagoCheque>) em.createQuery(" select cheque from "
					+ "	FormaPagoCheque cheque "
					+ "	where cheque.depositado = :activo " 
					+ " and cheque.recibo.anulado = :anulado ")
			    .setParameter("activo",false) //No depositado 
			    .setParameter("anulado",false) // recibo no anulado 
				.list();
			tx.commit();
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<FormaPago> getChequesCierre(Date fecha){
		Transaction tx = null;
		
		TipoFormaPago tipo= new PerTipoFormaPago().getTipoCheque();
		List<FormaPago> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
     	tx = em.beginTransaction();
		try{
			criterio = (List<FormaPago>) em.createCriteria(FormaPago.class)
				.add(Restrictions.eq("tipoFormaPago",tipo))
				.add(Restrictions.eq("depositado",Boolean.FALSE))
				.add(Restrictions.eq("fechaRecepcion",fecha))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
 
	
	public FormaPagoCheque getChequeDepositado(DetalleDeposito detalle){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		FormaPagoCheque criterio= null;
     	tx = em.beginTransaction();
		try{
			System.out.println("---------------------------");
			System.out.println(detalle.getCuenta());
			System.out.println(detalle.getDocumento());
			System.out.println("---------------------------");
			criterio =  (FormaPagoCheque) em.createQuery(" select fp from FormaPagoCheque fp " +
					"	where fp.cheque.nroCuenta = :cuenta and fp.cheque.nroCheque =:cheque ") 
					.setParameter("cuenta",detalle.getCuenta().trim())
					.setParameter("cheque",detalle.getDocumento().trim())
					.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	/*
	public void validar_Cuenta(FormaPago pago) throws ExcDatosNoValido{
		boolean valida= false;
		if (pago.getTipoFormaPago().isUsaCuenta()){
			try {
				List<CuentaBancaria> cuentas = new PerCuentaBancaria().getAll();
				for (CuentaBancaria cuenta : cuentas){
					if (cuenta.getBanco().equals(pago.getBanco()) && cuenta.getCuenta().equals(pago.getCuenta()))
						valida = true;
				}
			} catch (ExcFiltroExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (valida == false)
				throw new ExcDatosNoValido("Cuenta no existe");
			
		}
		
	}
*/
	@SuppressWarnings("unchecked")
	public List<FormaPago> getSoloBancos(){
		Transaction tx = null;
		
		List<FormaPago> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(FormaPago.class)
				.add(Restrictions.ne("id","---"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	
	public CuentaBancaria getCuentaValida(FormaPagoDeposito pago) throws ExcEntradaInconsistente{
		Transaction 	tx = null;
		CuentaBancaria  criterio= null;
		TipoFormaPago 	tipoDeposito = new PerTipoFormaPago().getTipoDeposito();
			if (pago.getTipoFormaPago().equals(tipoDeposito))
			{
				try{
					Session em =SessionDao.getInstance().getCurrentSession();
					tx = em.beginTransaction();
					criterio =  (CuentaBancaria) em.createQuery("" +
							"SELECT c " +
							"FROM CuentaBancaria  c " +
							"Inner Join c.banco  b " +
							"WHERE b.id = :id  and  c.nroCuenta = :cuenta ")
								.setString("id", pago.getBanco().getId())
								.setString("cuenta", pago.getCuenta())
								.uniqueResult();
			 		tx.commit();
				}catch(Exception e){
					e.printStackTrace();
					tx.rollback();
					throw new ExcEntradaInconsistente("problema con los datos");
				}
				if (criterio == null)
					throw new ExcEntradaInconsistente("Verificar datos del Deposito");
			}
			else
			{
				return null;
			}
			return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<CierreDiarioAsiento> getDetalleIngresos(CierreDiario cierre, String cuentaCaja) {
		Transaction tx = null;
		List<CierreDiarioAsiento> asiento = new ArrayList<CierreDiarioAsiento>();
		CierreDiarioAsiento renglonAsiento;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			List<Object[]> criterio =  em.createQuery("" +
					"SELECT r.fecha, t.nota, t.cuentaContable, t.descripcion, sum(d.monto) " +
					"FROM FormaPago  d " +
					"INNER Join d.recibo  r " +
					"LEFT Join d.banco  b " +
					"INNER Join d.tipoFormaPago  t " +
					"WHERE r.fecha = :fecha  and t.deposito = :deposito and d.depositado = :depositado " +
					"GROUP BY r.fecha, t.nota, t.cuentaContable, t.descripcion")
						.setDate("fecha", cierre.getFecha())
						.setBoolean("deposito", Boolean.FALSE)
						.setBoolean("depositado", Boolean.FALSE)
						.list();
			tx.commit();
			for (Object[] objs : criterio) {
				renglonAsiento = new CierreDiarioAsiento();
				renglonAsiento.setCierreDiario(cierre);
				renglonAsiento.setFecha(cierre.getFecha());
				if ((Boolean)objs[1] == true ){
					if (objs[3] == null)
						renglonAsiento.setRenglon("INGRESO DOCUMENTO ");
					else	
						renglonAsiento.setRenglon("INGRESO DOCUMENTO "+objs[3]);
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId((String)objs[2]));
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
				}else{
					if (objs[3] == null)
						renglonAsiento.setRenglon("INGRESO DOCUMENTO ");
					else
						renglonAsiento.setRenglon("INGRESO CAJA TEMPORAL "+objs[3]);
					try{
						renglonAsiento.setCuenta(new PerCuentaContable().buscarId(cuentaCaja));
					}catch (Exception e) {
						renglonAsiento.setCuenta(new CuentaContable());
					}
				}
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
	/*
	public List<FormaPago> getFormasPagoNumero(FormaPago formapago) {
		Transaction 	tx = null;
		List<FormaPago> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(FormaPago.class)
				.add(Restrictions.eq("documento", formapago.getDocumento()) )
				.add(Restrictions.eq("banco", formapago.getBanco()) )
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return null;
	}

	public double getFormaPagoUsada(FormaPago pago){
		List<FormaPago> usado = getFormasPagoNumero(pago);
		 double a = 0;
		if (usado!=null){
			for (FormaPago formaPago : usado) {
				a=a+formaPago.getMonto();
			}
			
		}
		return a;
	}
*/
@SuppressWarnings("unchecked")
public List<FormaPago> getformaPago(Recibo recibo)
{
	Transaction 	tx = null;
	List<FormaPago> criterio= null;
	Session em =SessionDao.getInstance().getCurrentSession();
	tx = em.beginTransaction();
	try{
		criterio = em.createCriteria(FormaPago.class)
			.add(Restrictions.eq("recibo",recibo)).list();
		tx.commit();
	}catch(Exception e){
		e.printStackTrace();
		tx.rollback();
	}
	return criterio;
}

	// true si esta asigando a una forma de pago con estado true 
	public Boolean getDocumentoUsado(String documento)
	{
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Long resultado = null;
		tx = em.beginTransaction();
		try{
			resultado = (Long) em.createCriteria(FormaPagoDeposito.class)
							.setProjection(Projections.rowCount())
							.add(Restrictions.eq("documento",documento))
							.add(Restrictions.eq("estado",true))
							.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return (resultado != 0? true : false);
	}


	public Boolean getDocumentoUsadoTransferencia(String documento) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Long resultado = null;
		tx = em.beginTransaction();
		try{
			resultado = (Long) em.createCriteria(FormaPagoTransferencia.class)
							.setProjection(Projections.rowCount())
							.add(Restrictions.eq("documento",documento))
							.add(Restrictions.eq("estado",true))
							.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return (resultado != 0? true : false);
	}


	public Double getEfectivoCierre(Date fecha) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		Double resultado = new Double(0);
		tx = em.beginTransaction();
		try{
			resultado = (Double) em.createQuery("select sum(forma.monto) from "
									+ " FormaPagoEfectivo forma"
									+ " where forma.recibo.fecha = :fecha ")
					.setParameter("fecha",fecha).uniqueResult();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
		return (resultado==null ? new Double(0): resultado);
	}

	@SuppressWarnings("unchecked")
	public List<FormaPagoEfectivo> getEfectivoSinDepositar( ) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPagoEfectivo> resultado = new ArrayList<FormaPagoEfectivo>();
		tx = em.beginTransaction();
		resultado =em.createQuery(" select forma  from  FormaPagoEfectivo forma"
					+ " where forma.depositado = :activo "
					+ " and forma.recibo.anulado = :anulado ")
				    .setParameter("activo",false) //No depositado 
				    .setParameter("anulado",false) // recibo no anulado 
					.list();
		tx.commit();
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<FormaPagoRetencion> getRetencionesreporte(Date desde , Date hasta) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPagoRetencion> resultado = new ArrayList<FormaPagoRetencion>();
		tx = em.beginTransaction();
	
		resultado = em.createCriteria(FormaPagoRetencion.class)
					.add(Restrictions.ge("fecha", desde))
					.add(Restrictions.le("fecha",hasta)).list();
		for(FormaPago p : resultado)
		{
			p.getRecibo();
		}
		
		tx.commit();
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<FormaPagoRetencion> getRetencionesreporte() {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<FormaPagoRetencion> resultado = new ArrayList<FormaPagoRetencion>();
		tx = em.beginTransaction();
		resultado = em.createCriteria(FormaPagoRetencion.class).list();
		for(FormaPago p : resultado)
		{
			p.getRecibo();
		}
		tx.commit();
		return resultado;
	}


	@SuppressWarnings("unchecked")
	public List<FormaPagoCheque> getAllCheques(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<FormaPagoCheque> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			criterio = em.createQuery(" select new FormaPagoCheque(p.id,recibo.id,cliente.identidadLegal,p.fecha,p.fechaRecepcion, " +
					" p.monto,cheque.id,cheque.nroCheque,cheque.nroCuenta,cheque.monto) " +
					" from FormaPagoCheque p left join p.recibo recibo left join p.recibo.cliente cliente left join p.cheque cheque " +
					" where date_part('month',recibo.fecha) = :mes and date_part('year',recibo.fecha) = :year " +
					" and p.recibo.anulado = false ")
					.setParameter("mes",mes).setParameter("year",ano)
					.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	

	@SuppressWarnings("unchecked")
	public List<FormaPagoTransferencia> getAllTransferencia(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<FormaPagoTransferencia> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			criterio = em.createQuery(" select new FormaPagoTransferencia(p.id,recibo.id,cliente.identidadLegal,p.fecha,p.fechaRecepcion, " +
					" p.monto,p.cuenta,p.documento) " +
					" from FormaPagoTransferencia p left join p.recibo recibo left join p.recibo.cliente cliente " +
					" where date_part('month',recibo.fecha) = :mes and date_part('year',recibo.fecha) = :year " +
					" and p.recibo.anulado = false ")
					.setParameter("mes",mes).setParameter("year",ano)
					.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<FormaPagoDeposito> getAllDepositos(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<FormaPagoDeposito> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			criterio = em.createQuery(" select new FormaPagoDeposito(p.id,recibo.id,cliente.identidadLegal,p.fecha,p.fechaRecepcion, " +
					" p.monto,p.cuenta,p.documento) " +
					" from FormaPagoDeposito p left join p.recibo recibo left join p.recibo.cliente cliente " +
					" where date_part('month',recibo.fecha) = :mes and date_part('year',recibo.fecha) = :year " +
					" and p.recibo.anulado = false ")
					.setParameter("mes",mes).setParameter("year",ano)
					.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	
	public Element getFormaPagosXml(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Element xmlPagos = new Element("formapagos");
 		//------------  TRANSFERENCIA  -----------------------------------------------
		Element xmlTransferencia = new Element("transferencias");
		xmlPagos.addContent(xmlTransferencia);
		
		List<FormaPagoTransferencia> transferencias = getAllTransferencia(mes, ano);

		for(FormaPagoTransferencia transferencia : transferencias)
		{	
			Element xmlPago = new Element("pago");
			xmlTransferencia.addContent(xmlPago);
			
			xmlPago.setAttribute("id",transferencia.getId().toString());
			xmlPago.setAttribute("idRecibo",transferencia.getRecibo().getId().toString());
			xmlPago.setAttribute("identidad",transferencia.getRecibo().getCliente().getIdentidadLegal());
			xmlPago.setAttribute("fecha",transferencia.getStrFecha());
			xmlPago.setAttribute("monto",transferencia.getStrMonto());
			xmlPago.setAttribute("monto",transferencia.getMonto().toString());
			
			Element xmlTrans = new Element("transferencia");
			xmlPago.addContent(xmlTrans);
			xmlTrans.setAttribute("cuenta",transferencia.getCuenta());
			xmlTrans.setAttribute("documento",transferencia.getDocumento());
		}	
		
		//------------  Depositos   -----------------------------------------------
		Element xmlDepositos = new Element("depositos");
		xmlPagos.addContent(xmlDepositos);

		List<FormaPagoDeposito> depositos = getAllDepositos(mes, ano);
		for(FormaPagoDeposito deposito : depositos)
		{	
			Element xmlPago = new Element("pago");
			xmlDepositos.addContent(xmlPago);
			xmlPago.setAttribute("id",deposito.getId().toString());
			xmlPago.setAttribute("idRecibo",deposito.getRecibo().getId().toString());
			xmlPago.setAttribute("identidad",deposito.getRecibo().getCliente().getIdentidadLegal());
			xmlPago.setAttribute("fecha",deposito.getStrFecha());
			xmlPago.setAttribute("monto",deposito.getStrMonto());
			xmlPago.setAttribute("monto",deposito.getMonto().toString());
			
			Element xmlTrans = new Element("deposito");
			xmlPago.addContent(xmlTrans);
			xmlTrans.setAttribute("cuenta",deposito.getCuenta());
			xmlTrans.setAttribute("documento",deposito.getDocumento());
		}	
		
		return xmlPagos;
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> getAllChequesRecibos() {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<HashMap<String, Object>> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			criterio = em.createQuery("select r.id as reciboID,r.fecha as reciboFecha,r.concepto as reciboConcepto,r.control as reciboControl, "
					+ "cliente.id as clienteID,cliente.nombres as clienteNombre,cliente.identidadLegal as clienteIdLegal,cliente.direccion as clienteDireccion,"
					+ "r.monto as reciboMonto,r.saldo as reciboSaldo,r.anulado as reciboAnulado, "
					+ "formache.tipoFormaPago.id as esCheque,formade.tipoFormaPago.id as esDeposito, formache.id as formaChequeID,cheque.id as chequeId, cheque.nroCheque as chequeNroCheque, "
					+ "formade.id  as formaDepositoId, formadecheques.id as chequesId ,formadecheques.nroCheque	as chequesNroCheque "
					+ "from Recibo r "
					+ "left join r.cliente as cliente "
					+ "left outer join  r.formaspago as formache with formache.tipoFormaPago.id = 2	"
					+ "left outer join  formache.cheque as cheque "
					+ "left outer join  r.formaspago as formade with  formade.tipoFormaPago.id = 3 and formade.cheques is not empty "
					+ "left outer join  formade.cheques as formadecheques "
					+ "where (formache.id is not null and cheque.id is not null) or (formade.id is not null and  formadecheques.id is not null) "
					+ "order by formache.tipoFormaPago.id,formade.tipoFormaPago.id")
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP) .list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		/*
		for (Object object : criterio) {
			HashMap<String, Object> mapa =(HashMap<String, Object>) object;
			Iterator it = mapa.keySet().iterator();
			while(it.hasNext()){
			 String key = (String) it.next();
			  System.out.println("private " +  " " + key +";");
			}
		}
		*/
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<FormaPagoDeposito> getDepocitosEnRecibos(Date inicio,Date fin )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoDeposito> criterio  = new ArrayList<FormaPagoDeposito>();

        criterio = (List<FormaPagoDeposito>) em.createQuery(" select fd from FormaPagoDeposito fd"
        		+ " where fd.recibo.fecha between :inicio and :fin "
        		+ "and fd.recibo.anulado=false")
        		.setDate("inicio", inicio)
				.setDate("fin", fin)
				.list();
			tx.commit();
		return criterio;
	}
	
	
	public FormaPagoPunto getFormaPagoPuntoActiva(String nrotarjeta,String documento )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction tx = em.beginTransaction();
        FormaPagoPunto formaPago = null;
        formaPago = (FormaPagoPunto) em.createCriteria(FormaPagoPunto.class).add(
        			Restrictions.eq("documento",documento)).add(
        			Restrictions.eq("nrotarjeta",nrotarjeta)).add(
        			Restrictions.eq("estado",true)).uniqueResult();
		tx.commit(); 
		return formaPago;
	}
	
	@SuppressWarnings("unchecked")
	public List<FormaPagoTransferencia> getTransferenciasEnRecibos(Date inicio,Date fin )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoTransferencia> criterio  = new ArrayList<FormaPagoTransferencia>();

        criterio = (List<FormaPagoTransferencia>) em.createQuery(" select fd from FormaPagoTransferencia fd"
        		+ " where fd.recibo.fecha between :inicio and :fin "
        		+ "and fd.recibo.anulado=false")
        		.setDate("inicio", inicio)
				.setDate("fin", fin)
				.list();
			tx.commit();
		return criterio;
	}
	@SuppressWarnings("unchecked")
	public List<FormaPagoRetencion> getRetencionesEnRecibos(Date inicio,Date fin )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoRetencion> criterio  = new ArrayList<FormaPagoRetencion>();

        criterio = (List<FormaPagoRetencion>) em.createQuery(" select fd from FormaPagoRetencion fd"
        		+ " where fd.recibo.fecha between :inicio and :fin "
        		+ "and fd.recibo.anulado=false")
        		.setDate("inicio", inicio)
				.setDate("fin", fin)
				.list();
			tx.commit();
		return criterio;
	}
	@SuppressWarnings("unchecked")
	public List<FormaPagoCheque> getChequesEnRecibos(Date inicio,Date fin )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoCheque> criterio  = new ArrayList<FormaPagoCheque>();

        criterio = (List<FormaPagoCheque>) em.createQuery(" select fd from FormaPagoCheque fd"
        		+ " where fd.recibo.fecha between :inicio and :fin "
        		+ "and fd.recibo.anulado=false")
        		.setDate("inicio", inicio)
				.setDate("fin", fin)
				.list();
			tx.commit();
		return criterio;
	}
	@SuppressWarnings("unchecked")
	public List<FormaPagoEfectivo> getEfectivoEnRecibos(Date inicio,Date fin )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoEfectivo> criterio  = new ArrayList<FormaPagoEfectivo>();

        criterio = (List<FormaPagoEfectivo>) em.createQuery(" select fd from FormaPagoEfectivo fd"
        		+ " where fd.recibo.fecha between :inicio and :fin "
        		+ "and fd.recibo.anulado=false")
        		.setDate("inicio", inicio)
				.setDate("fin", fin)
				.list();
			tx.commit();
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<FormaPagoPunto> getPuntosEnRecibos(Date inicio,Date fin )
	{
		Session em =SessionDao.getInstance().getCurrentSession();
        Transaction 	tx = em.beginTransaction();
        List<FormaPagoPunto> criterio  = new ArrayList<FormaPagoPunto>();

        criterio = (List<FormaPagoPunto>) em.createQuery(" select fd from FormaPagoPunto fd"
        		+ " where fd.recibo.fecha between :inicio and :fin "
        		+ "and fd.recibo.anulado=false")
        		.setDate("inicio", inicio)
				.setDate("fin", fin)
				.list();
			tx.commit();
		return criterio;
	}
	
}


