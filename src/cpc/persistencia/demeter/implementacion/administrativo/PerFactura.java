package cpc.persistencia.demeter.implementacion.administrativo;

//import java.sql.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.JoinType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.Alias;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.jdom2.Element;
import org.springframework.beans.factory.ListableBeanFactory;

import com.sun.org.apache.bcel.internal.generic.DCONST;

import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.ContratoMecanizado;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.Deposito;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.LibroVenta;
import cpc.modelo.demeter.administrativo.LibroVentaDetalle;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Fecha;


public class PerFactura extends DaoGenerico<DocumentoFiscal, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7582751118931212218L;

	public PerFactura() {
		super(DocumentoFiscal.class);
	}
	
	public DocumentoFiscal getDatos(DocumentoFiscal entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		DocumentoFiscal docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (DocumentoFiscal) em.load(DocumentoFiscal.class, entrada.getId());
			for (DetalleDocumentoFiscal item: docu.getDetalles()){ //em.evict(item);
			}
			for (ImpuestoDocumentoFiscal item: docu.getImpuestos()){ //em.evict(item);
			}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return docu;
	}
	
	public void guardar(DocumentoFiscal objeto, Integer indice, ControlSede control)  throws Exception{
 	
	   	 if (indice == null)
	   		 //em.persist(objeto);
	   		nuevo( objeto, control);
	   	 else
	   		 //em.merge(objeto);
	   		 super.guardar(objeto, indice);
	   	
	}
	
	public void guardarVieja( DocumentoFiscal docu)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		LibroVenta libro = new PerLibroVenta().getLibro(2009,1);
		docu.setTipoDocumento(new PerTipoDocumento().getTipoFactura());
		docu.setPorcDescuento(0.0);
		docu.setEstado(new PerEstadoDocumentoFiscal().getEstadoNuevaFactura());
		Session em ;
		LibroVentaDetalle detalle;
		try{
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			em.save(docu);
			if (libro.getId() == null)
				em.save(libro);
			detalle = new LibroVentaDetalle();
			detalle.setLibro(libro);
			detalle.setDocumento(docu);
			em.save(detalle);
		    //em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			docu.setId(null);
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Factura, "+ e.getMessage());
		}
	}
	
	
	public void nuevo( DocumentoFiscal docu, ControlSede control)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			if (docu.getContrato()!= null){
				//EstadoContrato estado = new PerEstadoContrato().getActivo();
				//docu.getContrato().setEstado(estado);
				docu.getContrato().setFacturado(true);
			}
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			control.incrementarControlFactura();
			docu.setNroControl(control.getControlFactura()); 
			docu.setNroDocumento(control.getDocumentoFactura());
			docu.setFecha(control.getFechaCierreFactura());
			em.save(docu);
			if (docu.getContrato()!= null)
				em.update(docu.getContrato());
			em.update(control);
		    //em.flush();
		    tx.commit(); 
		   	//em.flush();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			docu.setId(null);
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error almacenando Factura, "+ e.getMessage());
		}
	}
	
	public void anular( DocumentoFiscal docu) throws HibernateException 
	{
		//si tiene recibos asiciados hay que devolver el dinero  
		Transaction tx = null;
		Session em ;
		try{

			em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			//Guardo el Contrato como No Facturado
			if (docu.getContrato() != null)
			{
				em.update(docu.getContrato()); 
				docu.setContrato(null);
			}
			//Guardo los Impuestos 
			for(ImpuestoDocumentoFiscal impuesto: docu.getImpuestos()){
				em.update(impuesto);
			}
			//Guardo los recibos 
			for(ReciboDocumentoFiscal recibo : docu.getRecibos())
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
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getDocumentoConSaldo(){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<DocumentoFiscal> facturas = null;
		Transaction tx = em.beginTransaction();
		try{
			facturas = em.getNamedQuery("DocumentoFiscal.conSaldo").list();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return facturas;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllPre() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DocumentoFiscal> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM DocumentoFiscal d INNER JOIN d.tipoDocumento t  WHERE t.tipoFactura = true and d.postServicio = false order by d.id desc").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllPrefechas(String inicio,String termino) throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DocumentoFiscal> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM DocumentoFiscal d INNER JOIN d.tipoDocumento t  WHERE t.tipoFactura = true and d.postServicio = false and d.fecha between '"+inicio+" "+"' and '"+ termino +"' order by d.id desc ").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllPost() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DocumentoFiscal> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM DocumentoFiscal d INNER JOIN d.tipoDocumento t  WHERE t.tipoFactura = true and d.postServicio = true order by d.id desc").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllPostfechas(String inicio,String termino) throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DocumentoFiscal> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM DocumentoFiscal d INNER JOIN d.tipoDocumento t  WHERE t.tipoFactura = true and d.postServicio = true and d.fecha between '"+ inicio+"' and '"+termino+"' order by d.id desc ").list();
		//	System.out.println(facturas);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.isNotNull("montoBase"))
				.add(Restrictions.isNotNull("serie"))
				
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllProject() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Transaction tx = null;
		
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		List<DocumentoFiscal>documentos =new ArrayList<DocumentoFiscal>();
		try{
			
			  ProjectionList  projections = Projections.projectionList() ;
					 projections.add(Projections.property("id"));
					 projections.add(Projections.property("serie"));
					 projections.add(Projections.property("nroControl"));
					 projections.add(Projections.property("nroDocumento"));
					 projections.add(Projections.property("fecha"));
					 projections.add(Projections.property("credito"));
					 projections.add(Projections.property("cancelada"));
					 projections.add(Projections.property("estado"));
					 projections.add(Projections.property("direccionFiscal"));
					 projections.add(Projections.property("montoBase"));
					 projections.add(Projections.property("montoDescuento"));
					 projections.add(Projections.property("montoTotal"));
					 projections.add(Projections.property("montoSaldo"));
					 projections.add(Projections.property("porcDescuento"));
					 projections.add(Projections.property("observacion"));
					 projections.add(Projections.property("postServicio"));
				     projections.add(Projections.property("beneficiario.nombres"));
				     projections.add( Projections.property("contrato.nroControl"));
				     projections.add( Projections.property("contrato.serie"));
				     projections.add(Projections.property("beneficiario.identidadLegal"));
				
 		        List<Object> criterio =null;
				criterio = em.createCriteria(DocumentoFiscal.class)
					.createAlias("beneficiario", "beneficiario")
					.createAlias("contrato", "contrato",CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("tipoDocumento",tipo))
					.add(Restrictions.isNotNull("montoBase"))
					.addOrder(Order.desc("id"))
					.setProjection(projections)
					.list();
				
				
			
				for (Object object : criterio) {
				
					
					DocumentoFiscal documento = new DocumentoFiscal();
					Object[] objects=(Object[]) object;

					documento.setId((Integer) objects[0]);
					documento.setSerie((String)  objects[1]);
					documento.setNroControl((Integer) objects[2]);
					documento.setNroDocumento((Integer) objects[3]);
					documento.setFecha((Date) objects[4]);
					documento.setCredito((Boolean) objects[5]);
					documento.setCancelada((Boolean)objects[6]);
					documento.setEstado((EstadoDocumentoFiscal)objects[7]);
					documento.setDireccionFiscal((String) objects[8]);
					documento.setMontoBase((Double)objects[9]);
					documento.setMontoDescuento((Double) objects[10]);
					documento.setMontoTotal((Double) objects[11]);
					documento.setMontoSaldo((Double) objects[12]);
					documento.setPorcDescuento((Double) objects[13]);
					documento.setObservacion((String) objects[14]);
					documento.setPostServicio((Boolean) objects[15]);
					Cliente clienteaux = new Cliente();
					clienteaux.setIdentidadLegal((String)objects[19]);
					clienteaux.setNombres((String)objects[16]);
					documento.setBeneficiario(clienteaux);
					Contrato Contratoaux = new Contrato();
					Contratoaux.setNroControl((Integer)objects[17]);
					Contratoaux.setSerie((String)objects[18]);
					if (Contratoaux.getSerie()!=null&&Contratoaux.getNroControl()!=null)
					documento.setContrato(Contratoaux);
					documentos.add(documento);
				}
				
				
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return documentos;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllfechas(String inicio,String termino) throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DocumentoFiscal> facturas = null;
		try{
			facturas = em.createQuery("SELECT  d FROM DocumentoFiscal d INNER JOIN d.tipoDocumento t  WHERE t.tipoFactura = true and d.fecha between '"+ inicio+"' and '"+termino+"' order by d.id desc ").list();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return facturas;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ImpuestoDocumentoFiscal> getImpuestosDocumento(DocumentoFiscal documento){
		Session em =SessionDao.getInstance().getCurrentSession();
		List<ImpuestoDocumentoFiscal> impuestos = new ArrayList<ImpuestoDocumentoFiscal>();
		ImpuestoDocumentoFiscal impuesto;
		Transaction tx = em.beginTransaction();
		try{
			List<Object[]> query = em.createQuery("SELECT  i.id, i.descripcion, i.porcentaje, sum(l.precioUnitario*l.cantidad) as base FROM DocumentoFiscal d Inner join d.detalles l inner join l.alicuota i where d.id = :id group by  i.id, i.descripcion, i.porcentaje")
						.setInteger("id", documento.getId())
						.list();
			for (Object[] objs : query) {                 
				impuesto = new ImpuestoDocumentoFiscal();
				impuesto.setBase(new Double((Double) objs[3]).doubleValue() );
				impuesto.setDocumento(documento);
				impuesto.setImpuesto(new Impuesto((Integer) objs[0], (String)objs[1], new Double((Double) objs[2]).doubleValue()));
				impuesto.setPorcentaje(new Double((Double) objs[2]).doubleValue());
				impuesto.setMonto(impuesto.getBase()*impuesto.getBase()/100);
				impuestos.add(impuesto);
	        } 
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return impuestos;
	}


	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllActivas(){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.ne("estado",estado))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllActivasConSaldo(List<Cliente> clientes){ 
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getActivo();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.eq("estado",estado))
				.add(Restrictions.gt("montoSaldo",0.0))
				.add(Restrictions.in("beneficiario",clientes))
				.addOrder(Order.asc("beneficiario"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	 
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllActivasProject(){
		Transaction tx = null;
		
		List<Object> criterio= null;
		List<DocumentoFiscal> docus = new ArrayList<DocumentoFiscal>();
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		
		
		  ProjectionList  projections = Projections.projectionList() ;
			 
			
			 
			 projections.add(Projections.property("id"),"id");
			 projections.add(Projections.property("serie"),"serie");
			 projections.add(Projections.property("nroControl"),"nroControl");
			 projections.add(Projections.property("fecha"),"fecha");
			 projections.add(Projections.property("estado"),"estado");
			 projections.add(Projections.property("montoTotal"),"montoTotal");
			 projections.add(Projections.property("benefi.nombres"),"beneficiario");
		
		try{
			criterio = em.createCriteria(DocumentoFiscal.class,"docu")
			    .createAlias("docu.beneficiario", "benefi")
				.add(Restrictions.eq("docu.tipoDocumento",tipo))
				.add(Restrictions.ne("docu.estado",estado))
				.setProjection(projections)
				.list();
	
			for (Object objects : criterio) {
				Object[] obj = (Object[]) objects;
				
				DocumentoFiscal docu = new DocumentoFiscal();
				docu.setId((Integer) obj[0]);
				docu.setSerie((String) obj[1]);
				docu.setNroControl((Integer) obj[2]);
				docu.setFecha((Date) obj[3]);	
				docu.setEstado((EstadoDocumentoFiscal) obj[4]);	
				docu.setMontoTotal((Double) obj[5]);
				Cliente a = new Cliente();
				a.setNombres((String) obj[6]);
				docu.setBeneficiario(a);
				docus.add(docu);
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return docus;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<IProducto> getServiciosFacturados(Integer id){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		List<IProducto> criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT  s FROM DocumentoFiscal f INNER JOIN f.detalles d  INNER JOIN  d.servicio s where f.id = :factura")
				.setInteger("factura", id)
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
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
	
	 
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllActivasConContrato(ContratoMecanizado contrato){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.ne("estado",estado))
				.add(Restrictions.eq("contrato",contrato))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getFacturaSaldo(Cliente cliente){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.eq("beneficiario",cliente))
				.add(Restrictions.gt("montoSaldo",new Double(1.0)))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	public List<DetalleDocumentoFiscal> getProductosFacturados(Date inicio,Date fin )
	{
		
		Transaction tx = null;
		List<Object> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
          projections.add(Projections.sum("cantidad"));
          projections.add(Projections.groupProperty("servicio"));
          projections.add(Projections.groupProperty("alicuota"));
          projections.add(Projections.groupProperty("precioUnitario") );

          criterio = em.createCriteria(DetalleDocumentoFiscal.class)
					.createAlias("documento", "factura")
					.createAlias("servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.add(Restrictions.eq("factura.tipoDocumento",tipo))
					.add(Restrictions.le("factura.montoSaldo",new Double(0.0)))
					.add(Restrictions.between("factura.fecha", inicio, fin))
					.add(Restrictions.eq("tipoProducto.descripcion","Producto"))
					//.add(Restrictions.eq("class","cpc.modelo.demeter.basico.ArticuloVenta"))
					.setProjection(projections)
					.list();
			for (Object ob : criterio) 
			{
				  Object[] row = (Object[])ob;
				
				@SuppressWarnings("unused")
				DetalleDocumentoFiscal a = new DetalleDocumentoFiscal();
				Double cantidad = (Double) row[0];
				System.out.println(row[1]);
				ArticuloVenta articuloVenta = (ArticuloVenta) row[1];
				Impuesto impuesto = (Impuesto) row[2];
				Double preciou = (Double) row[3];
				
				a.setCantidad(cantidad);
				a.setServicio(articuloVenta);
				a.setAlicuota(impuesto);
				a.setPrecioUnitario(preciou);
					
				detalles.add(a);
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return detalles;
	}
	
	
	public List<DetalleDocumentoFiscal> getServiciosFacturados(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
          projections.add(Projections.sum("cantidad"));
          projections.add(Projections.groupProperty("servicio"));
          projections.add(Projections.groupProperty("alicuota"));
          projections.add(Projections.groupProperty("precioUnitario") );
          projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));
 		 
          criterio = em.createCriteria(DetalleDocumentoFiscal.class)
					.createAlias("documento", "factura")
					.createAlias("servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias("servicio.servicio", "tiposervicio")
				.add(Restrictions.eq("factura.tipoDocumento",tipo))
			    .add(Restrictions.le("factura.montoSaldo",new Double(0.0)))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
			     .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Servicio"))
			  .setProjection(projections)
			    .list();
			
			System.out.println("criterio.size()");
			for (Object ob : criterio) {
				  Object[] row = (Object[])ob;
				@SuppressWarnings("unused")
				DetalleDocumentoFiscal a = new DetalleDocumentoFiscal();
				Double cantidad = (Double) row[0];
				Labor labor = (Labor) row[1];
				Impuesto impuesto = (Impuesto) row[2];
				Double preciou = (Double) row[3];
				
				a.setCantidad(cantidad);
				a.setServicio(labor);
				a.setAlicuota(impuesto);
				a.setPrecioUnitario(preciou);
					
				detalles.add(a);
				
			}

		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return detalles;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllCanceladas(){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("tipoDocumento",tipo))
				.add(Restrictions.ne("estado",estado))
				.add(Restrictions.eq("cancelada",true))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getAllCanceladasCheque(){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		 
		try{
			criterio= em.createQuery("select d from DocumentoFiscal d join d.recibos r join r.formaspago f join f.cheques where d.tipoDocumento=:tipo and d.estado!=:estado  ")
					    .setParameter("tipo", tipo)
					    .setParameter("estado",estado)
					    .list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public DocumentoFiscal getDocumento(DocumentoFiscal docu){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		docu =(DocumentoFiscal) em.get(docu.getClass(), docu.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return docu;
	}
	
	public List<DetalleDocumentoFiscal> getServiciosFacturadosDebito(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		List<TipoDocumentoFiscal> tipo = new ArrayList<TipoDocumentoFiscal>(); 
		tipo.add(new PerTipoDocumento().getTipoFactura());		
		tipo.add(new PerTipoDocumento().getNotaDedito());		
		
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
	
	
		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
          projections.add(Projections.sum("cantidad"));
          projections.add(Projections.groupProperty("servicio"));
          projections.add(Projections.groupProperty("alicuota"));
          projections.add(Projections.groupProperty("precioUnitario") );
          projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));

			
			
			criterio = em.createCriteria(DetalleDocumentoFiscal.class)
					.createAlias("documento", "factura")
					.createAlias("servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias("servicio.servicio", "tiposervicio")
					//	.createAlias("tiposervicio.tipoServicio", "2")
				.add(Restrictions.in("factura.tipoDocumento",tipo))
			 //   .add(Restrictions.le("factura.montoSaldo",new Double(0.0)))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
			    .add(Restrictions.not(Restrictions.eq("factura.estado", estado)))
			     .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Servicio"))
			    
			  .setProjection(projections)
			    //.setResultTransformer(Transformers.aliasToBean(DetalleDocumentoFiscal.class))
			    .list();
			
			System.out.println("criterio.size()");
			
			for (Object ob : criterio) {
				  Object[] row = (Object[])ob;
				  

				
				@SuppressWarnings("unused")
				DetalleDocumentoFiscal a = new DetalleDocumentoFiscal();
				Double cantidad = (Double) row[0];
				Labor labor = (Labor) row[1];
				Impuesto impuesto = (Impuesto) row[2];
				Double preciou = (Double) row[3];
				
				a.setCantidad(cantidad);
				a.setServicio(labor);
				a.setAlicuota(impuesto);
				a.setPrecioUnitario(preciou);
					
					detalles.add(a);
 
			}
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return detalles;
	}
	
	
	public List<Object> getServiciosFacturadosDebito2(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		List<TipoDocumentoFiscal> tipo = new ArrayList<TipoDocumentoFiscal>(); 
		tipo.add(new PerTipoDocumento().getTipoFactura());		
		tipo.add(new PerTipoDocumento().getNotaDedito());		
		
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
	
	
		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
          projections.add(Projections.sum("cantidad"));
          projections.add(Projections.groupProperty("servicio"));
          projections.add(Projections.groupProperty("alicuota"));
          projections.add(Projections.groupProperty("precioUnitario") );
          projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));
          projections.add(Projections.sum("factura.montoTotal"),"saldos");
          projections.add(Projections.sum("factura.montoSaldo"),"saldos");
 
			
			criterio = em.createCriteria(DetalleDocumentoFiscal.class,"detalle")
					.createAlias("detalle.documento", "factura")
					.createAlias("detalle.servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias("servicio.servicio", "tiposervicio")
				.add(Restrictions.in("factura.tipoDocumento",tipo))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
			    .add(Restrictions.not(Restrictions.eq("factura.estado", estado)))
			     .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Servicio"))
			  .setProjection(projections)

			    .list();
			
			System.out.println("criterio.size()");
	 		
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	

	public List<DetalleDocumentoFiscal> getServiciosCredito(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaCredito();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
	
		tx = em.beginTransaction();		
	
	
		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
     
          projections.add(Projections.sum("cantidad"));
    
          projections.add(Projections.groupProperty("servicio"));
          projections.add(Projections.groupProperty("alicuota"));
          projections.add(Projections.groupProperty("precioUnitario") );
          projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));

			
			
			criterio = em.createCriteria(DetalleDocumentoFiscal.class)
					.createAlias("documento", "factura")
					.createAlias("servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias("servicio.servicio", "tiposervicio")
				.add(Restrictions.eq("factura.tipoDocumento",tipo))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
		        .add(Restrictions.not(Restrictions.eq("factura.estado", estado)))
		        .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Servicio"))
			  .setProjection(projections)
			
			    .list();
			
			System.out.println("criterio.size()");
			
			for (Object ob : criterio) {
				  Object[] row = (Object[])ob;
				  
				@SuppressWarnings("unused")
				DetalleDocumentoFiscal a = new DetalleDocumentoFiscal();
				Double cantidad = (Double) row[0];
				Labor labor = (Labor) row[1];
				Impuesto impuesto = (Impuesto) row[2];
				Double preciou = (Double) row[3];
				a.setCantidad(cantidad);
				a.setServicio(labor);
				a.setAlicuota(impuesto);
				a.setPrecioUnitario(preciou);
				detalles.add(a);
				
			} 
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return detalles;
	}
	
	public List<Object> getServiciosCredito2(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaCredito();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
	
		tx = em.beginTransaction();		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
 
	        projections.add(Projections.sum("cantidad"));
	        projections.add(Projections.groupProperty("servicio"));
	        projections.add(Projections.groupProperty("alicuota"));
	           projections.add(Projections.groupProperty("precioUnitario") );
	           projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));
	           projections.add(Projections.sum("factura.montoTotal"),"saldos");
	           projections.add(Projections.sum("factura.montoSaldo"),"saldos");
			
			
			criterio = em.createCriteria(DetalleDocumentoFiscal.class,"detalle")
					.createAlias("detalle.documento", "factura")
					.createAlias("detalle.servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias("servicio.servicio", "tiposervicio")
				.add(Restrictions.eq("factura.tipoDocumento",tipo))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
			    .add(Restrictions.not(Restrictions.eq("factura.estado", estado)))
			    .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Servicio"))
			    .setProjection(projections)
			    .list();
			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	

	public List<DocumentoFiscal> getFacturas(Integer mes, Integer ano) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();
		try{
			
			criterio = em.createQuery(" select new DocumentoFiscal(  " +
					" df.serie, df.nroControl, df.nroDocumento, df.fecha, e.id,  e.descripcion, b.identidadLegal, " +
 					" df.montoBase, df.montoDescuento, df.montoTotal, df.montoSaldo,df.porcDescuento, df.observacion) " +
					" from DocumentoFiscal df " +  
					" 	left join df.estado e left join df.beneficiario b " +
					" where df.tipoDocumento = :tipo " +
					" and date_part('month',df.fecha) = :mes and date_part('year',df.fecha) = :year ")
					.setParameter("mes",mes).setParameter("year",ano).setParameter("tipo",tipo)
					.list();
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public Element getFacturasXml(Integer mes, Integer ano) {
		Element xmlFacturas = new Element("facturas");
		List<DocumentoFiscal> facturas = getFacturas(mes, ano);

		for(DocumentoFiscal factura: facturas)
		{	
			Element xmlfactura = new Element("factura");
			xmlFacturas.addContent(xmlfactura);
		
			xmlfactura.setAttribute("serie",factura.getSerie());
			xmlfactura.setAttribute("nroControl",factura.getNroControl().toString());
			xmlfactura.setAttribute("nroDocumento",factura.getStrNroDocumento());
			xmlfactura.setAttribute("fecha",factura.getStrFecha());
			xmlfactura.setAttribute("idestado",factura.getEstado().getId().toString());
			xmlfactura.setAttribute("estado",factura.getEstado().getDescripcion());
			xmlfactura.setAttribute("beneficiario",factura.getBeneficiario().getIdentidadLegal());
			xmlfactura.setAttribute("montoBase",factura.getStrBruto());
			xmlfactura.setAttribute("montoDescuento",factura.getStrDescuento());
			xmlfactura.setAttribute("montoTotal",factura.getStrTotal());
			xmlfactura.setAttribute("montoSaldo",factura.getStrSaldo());
			xmlfactura.setAttribute("porcDescuento",factura.getStrDescuento());
			xmlfactura.setAttribute("observacion",factura.getObservacion());

		}	

		return xmlFacturas;
	}
	
	public List<Object> getProductosFacturadosCredito2(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getNotaCredito();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
	
		tx = em.beginTransaction();		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
 
	        projections.add(Projections.sum("cantidad"));
	        projections.add(Projections.groupProperty("servicio"));
	        projections.add(Projections.groupProperty("alicuota"));
	           projections.add(Projections.groupProperty("precioUnitario") );
	           projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));
	           projections.add(Projections.sum("factura.montoTotal"),"saldos");
	           projections.add(Projections.sum("factura.montoSaldo"),"saldos");
			
			
			criterio = em.createCriteria(DetalleDocumentoFiscal.class,"detalle")
					.createAlias("detalle.documento", "factura")
					.createAlias("detalle.servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias ("servicio.servicio", "tiposervicio",Criteria.LEFT_JOIN)
				.add(Restrictions.eq("factura.tipoDocumento",tipo))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
			    .add(Restrictions.not(Restrictions.eq("factura.estado", estado)))
			    .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Producto"))
			    .setProjection(projections)
			    .list();
			System.out.println("criterio "+criterio.size());
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	public List<Object> getProductosFacturadosDebito2(Date inicio,Date fin ){
		Transaction tx = null;
		
		List<Object> criterio= null;
		List<TipoDocumentoFiscal> tipo = new ArrayList<TipoDocumentoFiscal>(); 
		tipo.add(new PerTipoDocumento().getTipoFactura());		
		tipo.add(new PerTipoDocumento().getNotaDedito());		
		
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getAnulado();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
	
	
		
		List<DetalleDocumentoFiscal> detalles = new ArrayList<DetalleDocumentoFiscal>();
		try{
		 ProjectionList  projections = Projections.projectionList() ;
          projections.add(Projections.sum("cantidad"));
          projections.add(Projections.groupProperty("servicio"));
          projections.add(Projections.groupProperty("alicuota"));
          projections.add(Projections.groupProperty("precioUnitario") );
          projections.add(Projections.groupProperty("tiposervicio.tipoServicio").as("tipo"));
          projections.add(Projections.sum("factura.montoTotal"),"saldos");
          projections.add(Projections.sum("factura.montoSaldo"),"saldos");
 
			
			criterio = em.createCriteria(DetalleDocumentoFiscal.class,"detalle")
					.createAlias("detalle.documento", "factura")
					.createAlias("detalle.servicio", "servicio")
					.createAlias("servicio.tipoProducto", "tipoProducto")
					.createAlias("servicio.servicio", "tiposervicio",Criteria.LEFT_JOIN)
				.add(Restrictions.in("factura.tipoDocumento",tipo))
			    .add(Restrictions.between("factura.fecha", inicio, fin))
			    .add(Restrictions.not(Restrictions.eq("factura.estado", estado)))
			     .addOrder(Order.asc("tipo"))
			    .add(Restrictions.eq("tipoProducto.descripcion", "Producto"))
			  .setProjection(projections)
			  .list();
			
			System.out.println("criterio "+criterio.size());
	 		
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}

	public DocumentoFiscal buscar(Integer nroControl, String serie) {
		// TODO Auto-generated method stub
		DocumentoFiscal objeto = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			objeto = (DocumentoFiscal) em.createQuery("from DocumentoFiscal d where d.serie = :serie and d.nroControl = :nroControl ")
					.setParameter("serie",serie)
					.setParameter("nroControl",nroControl).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return objeto;
	}
	
}