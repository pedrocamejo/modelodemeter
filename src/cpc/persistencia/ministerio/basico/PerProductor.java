package cpc.persistencia.ministerio.basico;

import java.util.List;

import javassist.convert.Transformer;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.demeter.vistas.ProductorView;
import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.InstitucionCrediticia;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.ministerio.gestion.ProductorNatural;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoDocumento;
import cva.pc.componentes.CompEncabezado;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerProductor extends DaoGenerico<Productor, Integer> {

	private static final long serialVersionUID = 6394140925759968246L;

	public PerProductor() {
		super(Productor.class);
	}

	@SuppressWarnings("unused")
	public Productor getDatos(Integer id) throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		Productor docu = null;
		tx = em.beginTransaction();
		try {
			docu = (Productor) em.get(Productor.class, id);
			for (InstitucionCrediticia item : docu.getFinanciamientos()) {
	
			}
			for (UnidadProductiva item : docu.getUnidadesproduccion()) {
			
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Problemas al Extraer datos productor");
		}
		return docu;
	}

	public void guardar(Productor productor, Integer indice) {
		if (productor.getTipo().isJuridico()) {
			ProductorJuridico productorReal = (ProductorJuridico) productor;
			new PerProductorJuridico().guardar(productorReal, indice);
		} else {
			ProductorNatural productorReal = (ProductorNatural) productor;
			new PerProductorNatural().guardar(productorReal, indice);
		}

	}

	@SuppressWarnings("unchecked")
	public List<Productor> getAllproductores() throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Productor> productores = null;
		try {
			productores = em.createQuery("SELECT  p FROM Productor p").list();
			for (Productor productor : productores) {
				Hibernate.initialize(productor.getTelefonos());
			}
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		return productores;
	}


	@SuppressWarnings("unchecked")
	public List<Productor> getAllProductoresProject() throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Productor> productores = null;
		try {
			 ProjectionList proList = Projections.projectionList();
			  proList.add(Projections.property ("i.id"),"id");
			  proList.add(Projections.property ("i.tipo"),"tipo");
			  proList.add(Projections.property ("i.nombres"),"nombres");
			  proList.add(Projections.property ("i.identidadLegal"),"identidadLegal");
			  proList.add(Projections.property ("i.organizacion"),"organizacion");
			  proList.add(Projections.property ("i.fechaIngreso"),"fechaIngreso");
 			productores =   em.createCriteria(Productor.class,"i").setProjection(proList).
					setResultTransformer(Transformers.aliasToBean(Productor.class)).
			
				list();
 
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		return productores;
	}
	
	@SuppressWarnings("unchecked")
	public Productor getProductorProject(Productor productor) throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		
		try {
			productor =   (Productor) em.get(productor.getClass(),productor.getId());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		return productor;
	}
	
	public List<DocumentoFiscal>  getProductoresUnidadProductivaFactura(UbicacionSector sector) throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<DocumentoFiscal> productores = null;
		try {
			productores  = em.createQuery("select f from DocumentoFiscal f,Productor p " +
					 			" where p.unidadAsociado.ubicacion.sector =:sector  " +
					 			" and f.beneficiario =  p.id " +
					 			" and f.montoSaldo > 0.0 ").setParameter("sector",sector).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Error ");
		}
		return productores;
	}

	@SuppressWarnings("unchecked")
	public List<Productor> getProductoresSinDeuda() throws ExcFiltroExcepcion
	{
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getActivo();

		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Productor> productores = null;
		try {
			productores  = em.createQuery("select new Productor(c.id,c.identidadLegal,c.nombres,c.direccion)" +
									"  from DocumentoFiscal f right join f.beneficiario c " +
									"  where f.tipoDocumento = :tipo and f.estado = :estado " +
									"  group by c.id,c.identidadLegal,c.nombres,c.direccion  having  coalesce(sum(f.montoSaldo),0) <= 0.0")
									.setParameter("tipo",tipo)
									.setParameter("estado",estado).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("error  ");
		}
		return productores;
	}
	

	public List<Productor> getProductoresConDeuda() throws ExcFiltroExcepcion
	{
		TipoDocumentoFiscal tipo = new PerTipoDocumento().getTipoFactura();
		EstadoDocumentoFiscal estado = new PerEstadoDocumentoFiscal().getActivo();

		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Productor> productores = null;
		try {
			productores  = em.createQuery("select new Productor(c.id,c.identidadLegal,c.nombres,c.direccion)" +
					"  from DocumentoFiscal f right join f.beneficiario c " +
					"  where f.tipoDocumento = :tipo and f.estado = :estado " +
					"  group by c.id,c.identidadLegal,c.nombres,c.direccion  having  coalesce(sum(f.montoSaldo),0) >  0.0")
					.setParameter("tipo",tipo)
					.setParameter("estado",estado).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("error  ");
		}
		return productores;
	}

	public List<ProductorView> getProductoresView() throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		List<ProductorView> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createCriteria(ProductorView.class).list();
			tx.commit();
		} catch (Exception e) {
			if(tx != null)	
				tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}
	
	
	
}