package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jdom2.Element;

import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.administrativo.NotaDebito;
import cpc.modelo.demeter.administrativo.NotaPago;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Sede;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class PerNotaCredito extends DaoGenerico<NotaCredito, Integer>{

	
	private static final long serialVersionUID = -2620127877169243768L;

	public PerNotaCredito() {
		super(NotaCredito.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<NotaCredito> getAll() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Transaction tx = null;
		TipoDocumentoFiscal tipoNota = new PerTipoDocumento().getNotaCredito();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<NotaCredito> nota = null;
     	tx = em.beginTransaction();
		try{
			nota =  em.createCriteria(NotaCredito.class)
				.add(Restrictions.eq("tipoDocumento", tipoNota))
				.addOrder(Order.desc("id") )
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return nota;
	}
	
	@SuppressWarnings("unchecked")
	public List<DetalleDocumentoFiscal> getDetallesFacturaCredito(DocumentoFiscal factura){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DetalleDocumentoFiscal> detalles= null;
		Transaction tx = em.beginTransaction();
		try{
			detalles = em.createQuery("SELECT  l FROM DocumentoFiscal d Inner join d.detalles l  where d.id = :id")
						.setInteger("id", factura.getId())
						.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return detalles;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<NotaCredito> getNotasConSaldo() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		List<NotaCredito> notas= null;
		Transaction tx = em.beginTransaction();
		try{
			notas = em.createQuery("SELECT  d FROM NotaCredito d where d.montoSaldo < 0")
						.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return notas;
	}
	
	@SuppressWarnings("unused") 
	public NotaCredito getDatos(NotaCredito entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		NotaCredito docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (NotaCredito) em.load(NotaCredito.class, entrada.getId());
			for (DetalleDocumentoFiscal item: docu.getDetalles()){
			}
			for (ImpuestoDocumentoFiscal item: docu.getImpuestos()){
			}
			for (NotaPago item: docu.getPagos()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	public void guardar(NotaCredito objeto, Integer indice, ControlSede sede)  throws Exception{
	 	
	   	 if (indice == null)
	   		nuevo( objeto, sede);
	   	 else
	   		 super.guardar(objeto, indice);
	}
	
	public void anular( NotaCredito docu)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			List<DocumentoFiscal> facturas = new ArrayList<DocumentoFiscal>();
			DocumentoFiscal factura;
			docu.setMontoSaldo(new Double(0));
			docu.setMontoBase(new Double(0));
			docu.setMontoTotal(new Double(0));
			docu.setEstado(new PerEstadoDocumentoFiscal().getAnulado());
			for(NotaPago pagos: docu.getPagos()){
				factura = pagos.getFactura();
				factura.actualizarSaldo(pagos.getMonto()*-1);
				facturas.add(factura);
				pagos.setAnulado(true);
			} 
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
		    
		    
			em.update(docu);
			for(DocumentoFiscal factu: facturas){
				em.update(factu);
			} 
			for(ImpuestoDocumentoFiscal impuesto: docu.getImpuestos()){
				impuesto.setBase(0);
				impuesto.setMonto(0);
				em.update(impuesto);
			}
		    em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al almacenar nota");
		}
	}
	
	public void nuevo(NotaCredito docu, ControlSede control) throws HibernateException {
		Transaction tx = null;
		Session em ;
		try{
			docu.setTipoDocumento(new PerTipoDocumento().getNotaCredito());
		    DocumentoFiscal factura = docu.getFactura();
			double monto;
			double saldo;
			if (factura.getMontoSaldo()>docu.getMontoTotal()){
				monto = docu.getMontoTotal();
				saldo = 0;
			}else{
				monto =factura.getMontoSaldo();
				saldo = docu.getMontoTotal()-factura.getMontoSaldo();
			}
			List<NotaPago> lista = new ArrayList<NotaPago>(); 
			lista.add(crearPago(docu, monto, control.getSede()));
			factura.actualizarSaldo(monto);
			docu.setPagos(lista);
			if (factura.getMontoSaldo()>0)
				factura.setCancelada(false);
			else
				factura.setCancelada(true);
			
			for(ImpuestoDocumentoFiscal impuesto: docu.getImpuestos()){
				impuesto.setBase(impuesto.getBase()*-1);
				impuesto.setMonto(impuesto.getMonto()*-1);
			}
			docu.setMontoSaldo(saldo*-1);
			docu.setMontoBase(docu.getMontoBase()*-1);
			docu.setMontoTotal(docu.getMontoTotal()*-1);
			docu.setBeneficiario(factura.getBeneficiario());
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
		    
			control.incrementarControlNotaCredito();
			docu.setNroControl(control.getControlNotaCredito());
			docu.setNroDocumento(control.getDocumentoNotaCedito());
			docu.setFecha(control.getFechaCierreFactura());
			em.save(docu);
			em.update(control);
		
	em.flush();
		    tx.commit(); 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	private NotaPago crearPago(NotaCredito docu, double monto, Sede sede){
		NotaPago pago = new NotaPago();
		pago.setFactura(docu.getFactura());
		pago.setNota(docu);
		pago.setAnulado(false);
		pago.setFecha(docu.getFecha());
		pago.setMonto(monto);
		pago.setSede(sede);
		return pago;
	}
	
	  
	public  Double getTotalSaldoNotasCreditos(DocumentoFiscal factura) // notas de credito que han sido asignadas a facturas 
	{
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaCredito();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		Double total = new Double(0);

		List lista = em.createQuery(" select sum(nc.montoTotal) " +
		  									" from NotaCredito nc " +
		  									"  where nc.tipoDocumento=:tipo " +
		  									"  and nc.factura=:factura ")
		  										.setParameter("tipo",tipo)
		  										.setParameter("factura",factura)
		  										.list(); 
		if(lista.size() != 0)
		{
			total = (Double)(lista.get(0) != null? lista.get(0):new Double(0));
		}
		tx.commit();
		return total;
		
	}

	public List<NotaCredito> getAll(Cliente cliente) {
		// TODO Auto-generated method stub
		Session em =SessionDao.getInstance().getCurrentSession();
		List<NotaCredito> notas= null;
		Transaction tx = em.beginTransaction();
		try{
			notas = em.createQuery(" SELECT  d FROM NotaCredito d where d.montoSaldo < 0 and d.beneficiario = :cliente ")
					.setParameter("cliente",cliente)
					.list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return notas;
	}


	public List<NotaCredito> getNotas(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<NotaCredito> criterio= null;
		TipoDocumentoFiscal tipoNota = new PerTipoDocumento().getNotaDedito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{

			criterio = em.createQuery(" select new NotaCredito(  " +
					" df.serie, df.nroControl, df.nroDocumento, df.fecha, e.id,  e.descripcion, b.identidadLegal, " +
 					" df.montoBase, df.montoDescuento, df.montoTotal, df.montoSaldo,df.porcDescuento, df.observacion,factura) " +
					" from DocumentoFiscal df " +  
					" 	left join df.estado e left join df.beneficiario b left join df.factura factura " +
					" where df.tipoDocumento = :tipo " +
					" and date_part('month',df.fecha) = :mes and date_part('year',df.fecha) = :year ")
					.setParameter("mes",mes).setParameter("year",ano).setParameter("tipo",tipoNota)
					.list();
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public Element getCreditoXml(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Element xmlnotas = new Element("notascredito");
		List<NotaCredito> notas = getNotas(mes, ano);

		for(NotaCredito nota: notas)
		{	
			Element xmlNota = new Element("notacredito");
			xmlnotas.addContent(xmlNota);
		
			xmlNota.setAttribute("serie",nota.getSerie());
			xmlNota.setAttribute("nroControl",nota.getNroControl().toString());
			xmlNota.setAttribute("nroDocumento",nota.getStrNroDocumento());
			xmlNota.setAttribute("fecha",nota.getStrFecha());
			xmlNota.setAttribute("idestado",nota.getEstado().getId().toString());
			xmlNota.setAttribute("estado",nota.getEstado().getDescripcion());
			xmlNota.setAttribute("beneficiario",nota.getBeneficiario().getIdentidadLegal());
			xmlNota.setAttribute("montoBase",nota.getStrBruto());
			xmlNota.setAttribute("montoDescuento",nota.getStrDescuento());
			xmlNota.setAttribute("montoTotal",nota.getStrTotal());
			xmlNota.setAttribute("montoSaldo",nota.getStrSaldo());
			xmlNota.setAttribute("porcDescuento",nota.getStrDescuento());
			xmlNota.setAttribute("observacion",nota.getObservacion());
			xmlNota.setAttribute("factura",nota.getFactura().getStrNroDocumento());
		}	

		return xmlnotas;
	}
	
	 
	
}



