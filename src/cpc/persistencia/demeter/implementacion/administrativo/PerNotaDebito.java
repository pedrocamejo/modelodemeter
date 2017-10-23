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
import cpc.modelo.demeter.administrativo.NotaDebito;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class PerNotaDebito extends DaoGenerico<NotaDebito, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3085277396004756843L;

	public PerNotaDebito() {
		super(NotaDebito.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<NotaDebito> getAll() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Transaction tx = null;
		TipoDocumentoFiscal tipoNota = new PerTipoDocumento().getNotaDedito();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<NotaDebito> nota = null;
     	tx = em.beginTransaction();
		try{
			nota =  em.createCriteria(NotaDebito.class)
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
	public List<DetalleDocumentoFiscal> getDetallesFacturaDebito(DocumentoFiscal factura){
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
	
	@SuppressWarnings("unused") 
	public NotaDebito getDatos(NotaDebito entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		NotaDebito docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (NotaDebito) em.load(NotaDebito.class, entrada.getId());
			for (DetalleDocumentoFiscal item: docu.getDetalles()){
			}
			for (ImpuestoDocumentoFiscal item: docu.getImpuestos()){
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	public void guardar(NotaDebito objeto, Integer indice, ControlSede control)  throws Exception {
	 	
	   	 if (indice == null)
	   		nuevo( objeto, control);
	   	 else
	   		 super.guardar(objeto, indice);
	}
	
	public void anular( NotaDebito docu) throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			List<DocumentoFiscal> facturas = new ArrayList<DocumentoFiscal>();
			docu.setMontoSaldo(new Double(0));
			docu.setMontoBase(new Double(0));
			docu.setMontoTotal(new Double(0));
			docu.setEstado(new PerEstadoDocumentoFiscal().getAnulado());
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
			throw new  ExcFiltroExcepcion("Error al Almacenar Nota");
		}
	}
	
	public void nuevo(NotaDebito docu, ControlSede control) throws HibernateException {
		Transaction tx = null;
		Session em ;
		try{
			docu.setTipoDocumento(new PerTipoDocumento().getNotaDedito());
		    DocumentoFiscal factura = docu.getFactura();
			docu.setBeneficiario(factura.getBeneficiario());
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			control.incrementarControlNotaDebito();
			docu.setNroControl(control.getControlNotaDebito());
			docu.setNroDocumento(control.getDocumentoNotaDebito());
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
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getNotaSaldo(Cliente cliente){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaDedito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.eq("beneficiario",cliente))
				.add(Restrictions.gt("montoSaldo",1))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<NotaDebito> getNotasSaldos(Cliente cliente){
		Transaction tx = null;
		
		List<NotaDebito> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaDedito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(NotaDebito.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.eq("beneficiario",cliente))
				.add(Restrictions.gt("montoSaldo",1.0))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public NotaDebito getNotaDebito(DocumentoFiscal documentoFiscal){
		Transaction tx = null;
		
		NotaDebito criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaDedito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = (NotaDebito) em.createCriteria(NotaDebito.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.eq("id",documentoFiscal.getId())).uniqueResult();
				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
 


	public List<NotaDebito> getNotas(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<NotaDebito> criterio= null;
		TipoDocumentoFiscal tipoNota = new PerTipoDocumento().getNotaDedito();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{

			criterio = em.createQuery(" select new NotaDebito(  " +
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

	public Element getDebitoXml(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Element xmlnotas = new Element("notasdebito");
		List<NotaDebito> notas = getNotas(mes, ano);

		for(NotaDebito nota: notas)
		{	
			Element xmlNota = new Element("notadebito");
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
