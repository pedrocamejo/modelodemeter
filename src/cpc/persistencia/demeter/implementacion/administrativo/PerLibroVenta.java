package cpc.persistencia.demeter.implementacion.administrativo;


import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.jdom2.Element;
import org.zkoss.zk.ui.WrongValueException;

 





import cpc.modelo.demeter.administrativo.Contrato;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.LibroVenta;
import cpc.modelo.demeter.administrativo.LibroVentaDetalle;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.administrativo.NotaDebito;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcEntradaInvalida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Fecha;

public class PerLibroVenta extends DaoGenerico<LibroVenta, Integer>{

	private static final long serialVersionUID = -735059783415520544L;

	public PerLibroVenta() {
		super(LibroVenta.class);
	}
	
	public void guardar(LibroVenta objeto, Integer indice) throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try{
	     	tx = em.beginTransaction();
		   	if (indice == null){
		   		 em.save(objeto);
		   		for (LibroVentaDetalle item: objeto.getDetalles()){
		   			item.setLibro(objeto);
		   			em.save(item);
		   		}
		   	}else
		   		 em.update(objeto);
		    em.flush();
		   	tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion("Error Almacenando");
		}
	}
	
	public LibroVenta nuevoLibro(int mes, int ano) throws ExcEntradaInvalida, ExcEntradaInconsistente{
		LibroVenta libro = getLibro(ano, mes);
		if (libro.getId() != null)
			throw new ExcEntradaInvalida("Libro de Ventas ya existe");
		if (getDocumentosParaLibro(libro).size() <= 0)
			throw new ExcEntradaInvalida("No existen documentos para generar el libro");
		libro.setDetalles(getDocumentosParaLibro(libro));
		libro.setCantidadDocumentos(libro.getDetalles().size());
		double montoBase =0;
		double montoTotal =0;
		for(LibroVentaDetalle item: libro.getDetalles()){
			montoBase += item.getDocumento().getMontoBase();
			montoTotal += item.getDocumento().getMontoTotal();
		}
		libro.setMontoBase(montoBase);
		libro.setMontoTotal(montoTotal);
		libro.setDeclarado(false);
		return libro;
	}

	public LibroVenta getLibro(int ano, int mes){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		LibroVenta criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (LibroVenta) em.createCriteria(LibroVenta.class)
				.add(Restrictions.eq("mes",mes))
				.add(Restrictions.eq("ano",ano))
				.uniqueResult();
			if (criterio!= null){
				for(LibroVentaDetalle detalle: criterio.getDetalles()){em.evict(detalle);};
			}else{
				criterio = new LibroVenta();
				criterio.setAno(ano);
				criterio.setMes(mes);
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		//em.clear();
		return criterio;
	}
	
	public LibroVenta getLibro(LibroVenta entrada){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		LibroVenta docu = null;
     	tx = em.beginTransaction();
     	try{
			docu = (LibroVenta) em.load(LibroVenta.class, entrada.getId());
			for  (LibroVentaDetalle item: docu.getDetalles()){		}
			tx.commit();
     	}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
     	//em.clear();
		return docu;
	}
	
	@SuppressWarnings("unchecked")
	public List<LibroVentaDetalle> getDocumentosParaLibro(LibroVenta libro) throws ExcEntradaInconsistente{
		Transaction tx = null;
		Date fecha = new GregorianCalendar(libro.getAno(),libro.getMes(),1).getTime();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<LibroVentaDetalle> detalles=null;
		LibroVentaDetalle detalle;
		List<DocumentoFiscal> criterio= null;
		
     	tx = em.beginTransaction();
		try{
			criterio = em.createQuery("SELECT f FROM DocumentoFiscal f LEFT JOIN f.libro l  WHERE l.id = null and f.fecha < :fecha")
				.setDate("fecha", fecha)
				.list();
			detalles = new  ArrayList<LibroVentaDetalle>();
			for(DocumentoFiscal docu: criterio){
				detalle = new LibroVentaDetalle();
				detalle.setDocumento(docu);
				detalle.setLibro(libro);
				detalles.add(detalle);
				em.evict(detalle);
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new ExcEntradaInconsistente("Problemas al buscar Documentos"); 
		}
		return detalles;
	}
	
	public Integer getHijosActivos(Integer id){
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		int cantidad = 0;
		Long criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (Long) em.createQuery("SELECT count(r) FROM Recibo r INNER JOIN r.documento f where r.monto != 0.00 and f.id = :id")
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

	public LibroVenta getLibroActivo(Integer ano, Integer mes) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		LibroVenta criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (LibroVenta) em.createCriteria(LibroVenta.class)
				.add(Restrictions.eq("mes",mes))
				.add(Restrictions.eq("ano",ano))
				.uniqueResult();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}
		return criterio;
	}
	
	public LibroVenta getLibroPorDeclarar(Integer ano, Integer mes) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		LibroVenta criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (LibroVenta) em.createCriteria(LibroVenta.class)
				.add(Restrictions.eq("mes",mes))
				.add(Restrictions.eq("ano",ano))
				.add(Restrictions.eq("declarado",false))
				.uniqueResult();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
			}
		return criterio;
	}

	public Element getLibroXml(Integer mes, Integer ano) throws ExcEntradaInconsistente {
		//buscamos el libro
		Transaction tx = null;
		Session em =SessionDao.getInstance().getCurrentSession();
		LibroVenta criterio= null;
     	tx = em.beginTransaction();
		try{
			criterio = (LibroVenta) em.createCriteria(LibroVenta.class)
				.add(Restrictions.eq("mes",mes))
				.add(Restrictions.eq("ano",ano))
				.add(Restrictions.eq("declarado",false))
				.uniqueResult();
			tx.commit();
			}catch(Exception e){
				e.printStackTrace();
				tx.rollback();
				throw new ExcEntradaInconsistente("Problemas al buscar Documentos"); 
			}
		if ( criterio!=null) {
			criterio.setDetalles(new PerDetalleLibroVenta().getDetalleLibro(criterio));
			Element xmlLibro = new Element("libro");
			List<LibroVentaDetalle> listalibro = criterio.getDetalles();
			for (LibroVentaDetalle libroDetalle : listalibro) {
				Element xmlDetalle = new Element("detallelibro");
				xmlLibro.addContent(xmlDetalle);
				DocumentoFiscal docuentoFiscal = libroDetalle.getDocumento();
				xmlDetalle.setAttribute("fecha",libroDetalle.getDocumento().getStrFecha());
				xmlDetalle.setAttribute("cedulaRif",libroDetalle.getDocumento().getBeneficiario().getIdentidadLegal()); 
				xmlDetalle.setAttribute("razonSocial",docuentoFiscal.getNombreBeneficiario());
				xmlDetalle.setAttribute("numeroControl",libroDetalle.getDocumento().getNroDocumento().toString());	
				xmlDetalle.setAttribute("numeroFactura",libroDetalle.getNroFactura());
				xmlDetalle.setAttribute("numeroNotaDebito",libroDetalle.getNroNotaDebito());
				xmlDetalle.setAttribute("numeroNotaCredito",libroDetalle.getNroNotaCredito());
				xmlDetalle.setAttribute("tipoTransaccion",libroDetalle.getDocumento().getStrTipoDocumento());
				xmlDetalle.setAttribute("numeroFacturaafectada",libroDetalle.getNroFacturaAfecta());
				xmlDetalle.setAttribute("totalVentasIncluyendoIVA",docuentoFiscal.getMontoTotal().toString());
				xmlDetalle.setAttribute("ventasInternasNoGravadas",String.valueOf(docuentoFiscal.getTotalExcento()) );
				xmlDetalle.setAttribute("baseImponible",String.valueOf(docuentoFiscal.getTotalImponible()));	
				xmlDetalle.setAttribute("Alicuota","");
				xmlDetalle.setAttribute("impuestoIVA",String.valueOf(docuentoFiscal.getTotalImpuesto()));
				xmlDetalle.setAttribute("estado",(docuentoFiscal.getEstado().isAnulado() ? "Anulada":"Activa"));
				if (docuentoFiscal.getPostServicio()!=null)
				xmlDetalle.setAttribute("tipo",(docuentoFiscal.getPostServicio() ? "PostServicio":"PreServicio"));
				else 
					xmlDetalle.setAttribute("tipo","");
					
					
				
			}
			return xmlLibro;
		}
		else{
			throw new ExcEntradaInconsistente("El libro ya fue Totalizado");
		}
		
	}
	
	public void declararLibro(LibroVenta libroVenta) throws Exception{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx=em.beginTransaction();
			if (libroVenta.getId() == null){
				throw new Exception("el libro de ventas no existe");
	   		}
		   	else{
		   		 libroVenta.setDeclarado(true);	
		   		 em.update(libroVenta);
     		}	
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		
	}

	public LibroVenta getLibro(Integer id) throws Exception {
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		LibroVenta libro = null;
		try { 
			tx=em.beginTransaction();
			libro = (LibroVenta) em.get(LibroVenta.class,id);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		return libro;
	}

	public List<Map> getLibroDetalle(Integer id_libro) throws Exception {
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List<Map> detalles = new ArrayList();
		try { 
			tx=em.beginTransaction();
			detalles = em.createSQLQuery("select documento.seq_iddocumento, " +
			       "documento.int_idpagador,"+
			       "documento.int_idtipodocumento,"+
			       "documento.str_serie,"+
			       "documento.int_nrocontrol,"+
			       "documento.dtm_fecha,"+
			       "documento.dbl_montobase,"+
			       "documento.dbl_montototal,"+
			       "cliente.str_cedurif,"+
			       "cliente.str_nombre"+
				" from  administracion.tbl_dem_libroventadetalle detalle "+
				" inner join  administracion.tbl_dem_documentofiscal documento "+
				" on documento.seq_iddocumento = detalle.int_iddocumento"+
				" inner join  tbl_dem_clientes cliente "+
				" on documento.int_idpagador = cliente.seq_idcliente"+
				" where detalle.int_idlibvent = :id")
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
				.setInteger("id",id_libro).list();
			tx.commit();
		} 
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		return detalles;
	}
	
	
	

	@Override
	public List<LibroVenta> getAll() throws ExcFiltroExcepcion {
		List<LibroVenta> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createCriteria(LibroVenta.class).addOrder(Order.desc("id")).list();
			tx.commit();
		} catch (Exception e) {
			if(tx != null)	
				tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}

	public List getDetalleDocumentoFiscal(List ids_documentos) throws Exception {
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List detalles = new ArrayList();
		try {   
			tx=em.beginTransaction(); 
			detalles = em.createSQLQuery("select detalle.seq_iddetalle, "+
					" detalle.int_iddocumento, "+
					" detalle.int_idservicio, "+ 
					" producto.str_descripcion, "+
					" detalle.dbl_preciounitario * dbl_cantidad as precio, "+
					" COALESCE(str_spicuenta,'') as cuenta "+
					" from administracion.tbl_dem_documentofiscaldetalle detalle "+
					" inner join basico.tbl_dem_producto producto  "+
					" on detalle.int_idservicio = producto.seq_idproducto "+
					" left join basico.tbl_dem_articulo art  "+
					" on art.int_idproducto = producto.seq_idproducto "+
					" where int_iddocumento in :list")
			.setParameterList("list",ids_documentos)
			.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			tx.commit();
		} 
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		return detalles;
	}

	public List<Map> getDetalleImpuesto(List<Integer> ids) throws Exception {
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List detalles = new ArrayList();
		try {   
 			tx=em.beginTransaction(); 
			detalles = em.createSQLQuery( "select " +
				"  impuesto.int_iddocumento as id, " +
				"  impuesto.int_idtipoimpuesto as tipo_id, " +
				"  impuesto.dbl_pporcentaje as porcentaje, " +
				"  impuesto.dbl_base  as base, " +
				"  impuesto.int_monto as monto,  " +
				"  tipoimpuesto.str_descripcion as descripcion  " +
				"  from administracion.tbl_dem_documentoimpuesto impuesto " + 
				" left join administracion.tbl_dem_tipo_impuesto tipoimpuesto " + 
				" on impuesto.int_idtipoimpuesto =  tipoimpuesto.seq_idtipoimpuesto " +
				" where impuesto.int_iddocumento in :list ")
			.setParameterList("list",ids)
			.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			tx.commit();
		} 
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		return detalles;
	}
	
	public List<Map> getRecibosAsociados(List<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		List detalles = new ArrayList();
		try {   
 			tx=em.beginTransaction(); 
			detalles = em.createSQLQuery("select "+
				" rd.monto, "+
				" rd.int_iddocumentofiscal, "+
				" rd.int_idrecibo, "+
				" r.dtm_fecha, "+
				" r.dbl_monto, "+
				" r.str_control "+
				" from  administracion.tbl_dem_reciboDocumentoFiscal rd "+
				" inner join administracion.tbl_dem_recibo r  "+
				" on r.seq_idrecibo = rd.int_idrecibo  "+
				" where rd.int_iddocumentofiscal in :list")
			.setParameterList("list",ids)
			.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			tx.commit();
		} 
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		return detalles;
	}
	
}
