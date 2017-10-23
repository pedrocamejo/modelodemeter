package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.FormaPago;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.LibroVenta;
import cpc.modelo.demeter.administrativo.LibroVentaDetalle;
import cpc.modelo.demeter.administrativo.NotaDebito;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;


public class PerDetalleLibroVenta extends DaoGenerico<LibroVentaDetalle, Integer>{

	private static final long serialVersionUID = -735059783415520544L;


	public PerDetalleLibroVenta() {
		super(LibroVentaDetalle.class);
	}
	
	
	/*
	@SuppressWarnings({"unchecked", "unused"})
	public List<LibroVentaDetalle> getDetalleLibro(LibroVenta libro) throws HibernateException{
		Transaction tx = null;
		List<LibroVentaDetalle> criterio= null;
		
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(LibroVentaDetalle.class)
				.add(Restrictions.eq("libro",libro))
				.list();
			for (LibroVentaDetalle item : criterio) {
				for (DetalleDocumentoFiscal item2: item.getDocumento().getDetalles()){
				}
				for (ImpuestoDocumentoFiscal item2: item.getDocumento().getImpuestos()){
				}
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new  HibernateException(e.getCause()); 
		}	

		return criterio;
	}*/
	
	
	@SuppressWarnings({"unchecked", "unused"})
	public List<LibroVentaDetalle> getDetalleLibro(LibroVenta libro) throws HibernateException{
		Transaction tx = null;
		List<LibroVentaDetalle> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
	
		NotaDebito 	notaD;
		try{
			criterio = em.createCriteria(LibroVentaDetalle.class)
				.add(Restrictions.eq("libro",libro))
				.addOrder(Order.desc("documento"))
				.list();
			
	
			for (LibroVentaDetalle item : criterio) {
				
				for (DetalleDocumentoFiscal item2: item.getDocumento().getDetalles()){
				}
				for (ImpuestoDocumentoFiscal item2: item.getDocumento().getImpuestos()){
				}
				
				for ( ReciboDocumentoFiscal item2: item.getDocumento().getRecibos()){
				for (FormaPago formaPago: item2.getRecibo().getFormaspago() ){}	
			
				}
				if (!item.getDocumento().getTipoDocumento().isTipoFactura()){
					notaD = (NotaDebito) em.get(NotaDebito.class, item.getDocumento().getId());
					if (notaD != null)
						item.setNroFacturaAfecta(notaD.getFactura().getStrNroDocumento());
					}
			}
			tx.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new  HibernateException(e.getCause()); 
		}	

		return criterio;
	}
		
}
