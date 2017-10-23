package cpc.persistencia.demeter.implementacion.administrativo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.convert.Transformer;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;



import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCargo;
import cpc.modelo.demeter.administrativo.NotaCredito;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.basico.SectorAgricola;
import cpc.modelo.demeter.vistas.ClienteAdministrativoView;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;
import cva.pc.demeter.utilidades.Real;

public class PerClienteAdministrativo extends DaoGenerico<ClienteAdministrativo, Integer>{

	
	private static final long serialVersionUID = 533075071680140727L;

	public PerClienteAdministrativo() {
		super(ClienteAdministrativo.class);
	}

	public ClienteAdministrativoView findByIdView(Integer id ){
		Transaction tx = null;
		ClienteAdministrativoView cliente = null;
		try{
			Session em =SessionDao.getInstance().getCurrentSession();
			tx = em.beginTransaction();
			cliente = (ClienteAdministrativoView) em.get(ClienteAdministrativoView.class,id);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return cliente;
	}
	
	public List<ClienteAdministrativoView> getAllView() throws ExcFiltroExcepcion   {
		List<ClienteAdministrativoView> entities;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			entities = em.createCriteria(ClienteAdministrativoView.class).list();
			tx.commit();
		} catch (Exception e) {
			if(tx != null)	
				tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<ClienteAdministrativo> getNuevos(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<ClienteAdministrativo> clientesAd = null;
		try{
			clientesAd = new ArrayList<ClienteAdministrativo>(); 
			List<Cliente> clientes = em.getNamedQuery("ClienteAdministrativo.findNuevos").list();
			for(Cliente cliente: clientes){
				clientesAd.add(new ClienteAdministrativo(cliente));
				em.evict(cliente);
			}
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return clientesAd;
	}
	
	public void guardar(ClienteAdministrativo objeto, Integer indice, ControlSede sede)  throws Exception {
	   	 if (indice == null)
	   		 //em.persist(objeto);
	   		nuevo( objeto, sede);
	   	 else
	   		 //em.merge(objeto);
	   		 super.guardar(objeto, indice);
	}
	
	public void nuevo( ClienteAdministrativo cliente, ControlSede control)  throws ExcFiltroExcepcion {
		Transaction tx = null;
		Session em ;
		try{
			control.incrementarCliente();
			cliente.setCuentaCobro(control.getCuentaCLienteCobro().trim()+Formateador.rellenarNumero(control.getControlCliente(), control.getMascaraCliente()));
			cliente.setCuentaPago(control.getCuentaCLientePago().trim()+Formateador.rellenarNumero(control.getControlCliente(), control.getMascaraCliente()));
			em =SessionDao.getInstance().getCurrentSession();
		    tx = em.beginTransaction();
			em.save(cliente);
			em.update(control);
		    em.flush();
		    tx.commit(); 
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new  ExcFiltroExcepcion("Error al almacenar Cliente administrativo");
		}
   }
	
	@SuppressWarnings("unchecked")
	public List<ClienteAdministrativo> obtenerTodos(){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<ClienteAdministrativo> clientesAd = null;
		try{
			clientesAd = new ArrayList<ClienteAdministrativo>(); 
			List<Cliente> clientes = em.getNamedQuery("ClienteAdministrativo.findTodosSinClienteCero").list();
			for(Cliente cliente: clientes){
				clientesAd.add(new ClienteAdministrativo(cliente));
				em.evict(cliente);
			}
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
			
		return clientesAd;
	}
	
	public ClienteAdministrativo getExpediente(Cliente cliente) throws ExcFiltroExcepcion{
		Transaction tx = null;
		
		ClienteAdministrativo criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = (ClienteAdministrativo) em.createCriteria(ClienteAdministrativo.class)
				.add(Restrictions.eq("cliente",cliente))
				.uniqueResult();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			throw new ExcFiltroExcepcion("Problemas con cliente");
		}
		if (criterio ==null)
			throw new ExcFiltroExcepcion("Cliente sin expediente Administrativo");
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getSaldoPendiente(Cliente cliente){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
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
	
	@SuppressWarnings("unchecked")
	public List<Recibo> getSaldoreRecibo(Cliente cliente){
		Transaction tx = null;
		
		List<Recibo> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(Recibo.class)
				.add(Restrictions.eq("anulado",false))
				.add(Restrictions.eq("cliente",cliente))
				.add(Restrictions.gt("saldo",new Double(0.1)))
				
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getSaldoFavor(Cliente cliente){
		Transaction tx = null;
		
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.eq("beneficiario",cliente))
				.add(Restrictions.lt("montoSaldo",new Double(-1.0)))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getPendiente(List<TipoProductor> tipo){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			Criterion credito = Restrictions.gt("montoSaldo",new Double(1.0));
			Criterion debito = Restrictions.lt("montoSaldo",new Double(-1.0));
			criterio = em.createCriteria(DocumentoFiscal.class)
					   .createAlias("beneficiario", "benef")
					   .add(Restrictions.in("benef.tipo", tipo))
				.add(Restrictions.or(credito,debito))
				.addOrder(Order.desc("beneficiario"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getPendiente(ClienteAdministrativo clienteAdministrativo){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			Criterion credito = Restrictions.gt("montoSaldo",new Double(1.0));
			Criterion debito = Restrictions.lt("montoSaldo",new Double(-1.0));
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.or(credito,debito))
				.add(Restrictions.eq("beneficiario", clienteAdministrativo.getCliente()))
				.addOrder(Order.desc("beneficiario"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getPendienteNoAsociadas(List<TipoProductor> tipo){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			Criterion credito = Restrictions.gt("montoSaldo",new Double(1.0));
			Criterion debito = Restrictions.lt("montoSaldo",new Double(-1.0));
			criterio = em.createCriteria(DocumentoFiscal.class)
					   .createAlias("beneficiario", "benef")
					   .add(Restrictions.in("benef.tipo", tipo))
				.add(Restrictions.or(credito,debito)).add(Restrictions.isNotNull("serie"))
				.addOrder(Order.desc("beneficiario"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getPendienteNoAsociadas(Date incio,Date fin){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			Criterion credito = Restrictions.gt("montoSaldo",new Double(1.0));
			Criterion debito = Restrictions.lt("montoSaldo",new Double(-1.0));
			criterio = em.createCriteria(DocumentoFiscal.class)
				.add(Restrictions.or(credito,debito)).add(Restrictions.isNotNull("serie"))
				.add(Restrictions.between("fecha", incio,fin))
				.addOrder(Order.desc("beneficiario"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getPendienteAsociadas(List<TipoProductor> tipo){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			Criterion credito = Restrictions.gt("montoSaldo",new Double(1.0));
			Criterion debito = Restrictions.lt("montoSaldo",new Double(-1.0));
			criterio = em.createCriteria(DocumentoFiscal.class)
					.createAlias("beneficiario", "benef")
					   .add(Restrictions.in("benef.tipo", tipo))
				.add(Restrictions.or(credito,debito)).add(Restrictions.isNull("serie"))
				.addOrder(Order.desc("beneficiario"))
				.list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getoperacionescliente(){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			
			criterio = em.createCriteria(DocumentoFiscal.class)
				.addOrder(Order.desc("beneficiario"))
				.list();
		    for (DocumentoFiscal documentoFiscal : criterio) {
				Hibernate.initialize(documentoFiscal);
				Hibernate.initialize(documentoFiscal.getBeneficiario().getClienteAdministrativo().getCliente().getTelefonos());
				
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoFiscal> getoperacionescliente(Cliente cliente){
		Transaction tx = null;
		List<DocumentoFiscal> criterio= null;
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			
			criterio = em.createCriteria(DocumentoFiscal.class)
				.addOrder(Order.desc("beneficiario")).add(Restrictions.eq("beneficiario", cliente))
				.list();
		    for (DocumentoFiscal documentoFiscal : criterio) {
				Hibernate.initialize(documentoFiscal);
				Hibernate.initialize(documentoFiscal.getBeneficiario().getClienteAdministrativo().getCliente().getTelefonos());
				
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return criterio;
	}
	
	
	 
	
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getClientes(UbicacionSector sector) throws ExcDatosNoValido{
		Set<Cliente> clienteUnico = new HashSet<Cliente>();
		List<Cliente> salida = new ArrayList<Cliente>();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		List<Productor> productor = null;
		try{
			try{
				productor = (List<Productor>) em.createQuery("SELECT  p FROM UnidadProductiva up" +
						" INNER JOIN up.ubicacion u " +
						" INNER JOIN up.productor p " +
						" INNER JOIN u.sector s " +
						" WHERE s.id = :id")
						.setInteger("id", sector.getId())
						.list();
				tx.commit();
			}catch(Exception e){
				tx.rollback();
				return null;
			}
			for (Productor item : productor) {
				clienteUnico.add(item);
//				em.evict(item);
			}
			for(Cliente cliente: clienteUnico){
				salida.add(cliente);
//				em.evict(cliente);
			}
		}catch (Exception e) {
			throw new ExcDatosNoValido("Problemas con productor, "+e.getMessage());
		}
		return salida;
	}
	
	
	
	public Double getMontoFavor() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Double monto= null;
		Transaction tx = em.beginTransaction();
		try{
			monto= (Double) em.createQuery("SELECT sum(d.montoSaldo) as saldo FROM NotaCredito d where d.montoSaldo < 1")
						.uniqueResult();
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}	
		return Math.abs(monto);
	}
	
	
	public Double getMontoDeudor() throws ExcFiltroExcepcion{
	//public List<DocumentoFiscal> getAll() throws ExcFiltroExcepcion{
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		Double monto = null;
		try{
			monto = (Double) em.createQuery("SELECT  sum(d.montoSaldo) FROM DocumentoFiscal d Inner join d.tipoDocumento t  where t.haber = :tipo and d.montoSaldo > 1")
			.setBoolean("tipo", Boolean.TRUE)
			.uniqueResult();
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		return monto;
	}
	
	public List<ClienteAdministrativo> getClienteAdministrativosProject(){
		List<HashMap<String, Object>> criterio = new ArrayList<HashMap<String, Object>>();
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction();
		
		  ProjectionList  projections = Projections.projectionList() ;
			 projections.add(Projections.property("clientead.id"),"id");
			 projections.add(Projections.property("cliente.id"),"cliente.id");
			 projections.add(Projections.property("cliente.identidadLegal"),"cliente.identidadLegal");
			 projections.add(Projections.property("cliente.nombres"),"cliente.nombres");
		
		criterio = em.createCriteria(ClienteAdministrativo.class,"clientead").createAlias("clientead.cliente", "cliente").setProjection(projections)
				 .setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		
		List<ClienteAdministrativo> administrativos = new ArrayList<ClienteAdministrativo>();
		
		for (HashMap<String, Object> clienteM: criterio) {
			ClienteAdministrativo clienteAdministrativo = new ClienteAdministrativo();
			clienteAdministrativo.setId((Integer) clienteM.get("id"));
			Cliente cliente = new Cliente();
			
			cliente.setId((Integer) clienteM.get("cliente.id"));
			cliente.setIdentidadLegal((String) clienteM.get("cliente.identidadLegal"));
			cliente.setNombres((String) clienteM.get("cliente.nombres"));
			
			clienteAdministrativo.setCliente(cliente);
			administrativos.add(clienteAdministrativo);
		}
	return administrativos;
	}
	
	
	public ClienteAdministrativo getClienteAdministrativoProject(ClienteAdministrativo clienteAdministrativo){
		Session em =SessionDao.getInstance().getCurrentSession();
		Transaction tx = em.beginTransaction(); 
		try{
		clienteAdministrativo =(ClienteAdministrativo) em.get(clienteAdministrativo.getClass(), clienteAdministrativo.getId());
		tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return clienteAdministrativo;
	}
 
	
	
	@SuppressWarnings("unchecked")
	public List<Object> getPendienteConsolidado(List<TipoProductor> tipo){
		Transaction tx = null;
		List<Object> criterio = new ArrayList< Object>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			//hibernate no soporta outerjoin de 2 clases 
			/* criterio = em.createQuery(
			"select bene.id as id" 
			+ ",bene.nombres as nombre"
			+ ",bene.identidadLegal as idLegal,"
			+ "sum (docu.montoSaldo) as porCobrar"
			+ ",sum (nc.montoSaldo) as porPagar,"
			+ "sum (re.saldo) as saldoRecibo "+
"from DocumentoFiscal docu join docu.beneficiario as bene ,NotaCredito as nc , Recibo re ,Cliente	as clie "+
"where docu.montoSaldo > 0 and  nc.beneficiario.id = docu.beneficiario.id and re.cliente.id = docu.beneficiario.id and "+
"docu.beneficiario.id = clie.id and "
+ "clie.tipo in (:listaTipoProductor) "+ 

 "group by bene.id,bene.nombres,bene.identidadLegal")
 .setParameterList("listaTipoProductor", tipo).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

 */ 
		// extraemos los ids de la lista tipo productor
			String listaIds ="";  
			for ( TipoProductor tipoProductor: tipo) {
				listaIds =listaIds+tipoProductor.getId()+",";
				
				
			}
			String idFinales = listaIds.substring(0, listaIds.length()-1); 
			/*
		String	sqlbase ="select clie.str_nombre  as nombre ,clie.str_cedurif as cedrif,"
				+ "sum (docu.dbl_saldo) as porPagar,sum (notac.dbl_saldo) as porCobrar,sum (notacargo.dbl_montosaldo) as cargosextras,"
				+ "sum (re.dbl_saldo) as enRecibo from  administracion.tbl_dem_documentofiscal docu "
				+ "join public.tbl_dem_clientes clie on docu.int_idpagador = clie.seq_idcliente  "
				+ "and  docu.int_idtipodocumento in (1,3) "
				+ "left  join administracion.tbl_dem_documentofiscal notac on notac.int_idpagador = clie.seq_idcliente "
				+ "and notac.int_idtipodocumento =2 and notac.dbl_saldo <>0.0 "
				+ "left join administracion.tbl_dem_recibo re on re.int_idcliente = clie.seq_idcliente and re.dbl_saldo <>0.0 "
				+ "left  join administracion.tbl_dem_notacargo notacargo on notacargo.int_idcliente = clie.seq_idcliente "						
				+ "where docu.dbl_saldo >0.0 and docu.int_idtipodocumento in (1,3)";
		*/
			
		String	sqlbase =" select distinct clie.str_nombre  as nombre ,clie.str_cedurif as cedrif,tfd.porpagar as porpagar, nc.notas as cargosextras,saldorecibo as enrecibo from  public.tbl_dem_clientes clie "
				+ " join (select docu.int_idpagador as pagador,sum (docu.dbl_saldo) as porpagar from administracion.tbl_dem_documentofiscal docu  where docu.int_idtipodocumento in (1,3) "
				+ "	and docu.dbl_saldo >0.0 and docu.int_idtipodocumento in (1,3) "
				+ "	group by docu.int_idpagador order by 1) as	tfd	on 	tfd.pagador=clie.seq_idcliente left join (select  notac.int_idpagador, sum (notac.dbl_saldo)  as notas from administracion.tbl_dem_documentofiscal notac "
				+ " where notac.int_idtipodocumento =2 and notac.dbl_saldo <>0.0 GROUP BY notac.int_idpagador order by 1) as nc "
				+ " on nc.int_idpagador = clie.seq_idcliente left join ( select re.int_idcliente,sum (re.dbl_saldo) as saldorecibo from administracion.tbl_dem_recibo re where  re.dbl_saldo <>0.0  GROUP BY re.int_idcliente order by 1 "
				+ " ) as r on r.int_idcliente = clie.seq_idcliente ";
				
			
		String	condiciones ="where clie.int_tipocliente in ("+idFinales+")";
		String  grupoOrden ="GROUP BY clie.str_nombre ,clie.str_cedurif ,tfd.porpagar,nc.notas,saldorecibo order by porpagar desc "; 
					

			
			//criterio = em.createSQLQuery(sqlbase+condiciones+grupoOrden).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			  criterio = em.createSQLQuery(sqlbase+condiciones+grupoOrden).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
			tx.commit();
		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
			tx.rollback();
		}
		/*
		for (HashMap<String, Object> hashMap : criterio) {
			System.out.println(""
					+ hashMap.get("nombre") +","
							+ hashMap.get("porcobrar")+","+
									hashMap.get("enrecibo") +","+ hashMap.get("porpagar")+","	+hashMap.get("cedrif")+"");
		}*/
		return formatearReporteConsolidado(criterio);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getPendienteNoAsociadasConsolidado(List<TipoProductor> tipo){
		Transaction tx = null;
		List<Object> criterio = new ArrayList< Object>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			String listaIds ="";  
			for ( TipoProductor tipoProductor: tipo) {
				listaIds =listaIds+tipoProductor.getId()+",";
				
				
			}
			String idFinales = listaIds.substring(0, listaIds.length()-1); 
			/*
		String	sqlbase ="select clie.str_nombre  as nombre ,clie.str_cedurif as cedrif,"
				+ "sum (docu.dbl_saldo) as porPagar,sum (notac.dbl_saldo) as porCobrar,sum (notacargo.dbl_montosaldo) as cargosextras,"
				+ "sum (re.dbl_saldo) as enRecibo from  administracion.tbl_dem_documentofiscal docu "
				+ "join public.tbl_dem_clientes clie on docu.int_idpagador = clie.seq_idcliente  "
				+ "and  docu.int_idtipodocumento in (1,3) "
				+ "left  join administracion.tbl_dem_documentofiscal notac on notac.int_idpagador = clie.seq_idcliente "
				+ "and notac.int_idtipodocumento =2 and notac.dbl_saldo <>0.0 "
				+ "left join administracion.tbl_dem_recibo re on re.int_idcliente = clie.seq_idcliente and re.dbl_saldo <>0.0 "
				+ "left  join administracion.tbl_dem_notacargo notacargo on notacargo.int_idcliente = clie.seq_idcliente "
				+ "where docu.dbl_saldo >0.0 and docu.int_idtipodocumento in (1,3) "
				+ "and docu.str_serie is not null ";
		
		String	condiciones ="and clie.int_tipocliente in ("+idFinales+")";
		String  grupoOrden ="group by clie.str_nombre,clie.str_cedurif "
							+ "order by porPagar desc "; 
		*/	
			
		//criterio = em.createSQLQuery(sqlbase+condiciones+grupoOrden).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
			String	sqlbase =" select distinct clie.str_nombre  as nombre ,clie.str_cedurif as cedrif,tfd.porpagar as porpagar, nc.notas as cargosextras,saldorecibo as enrecibo from  public.tbl_dem_clientes clie "
					+ " join (select docu.int_idpagador as pagador,sum (docu.dbl_saldo) as porpagar from administracion.tbl_dem_documentofiscal docu  where docu.int_idtipodocumento in (1,3) "
					+ "	and docu.dbl_saldo >0.0 and docu.int_idtipodocumento in (1,3) and docu.str_serie is not null "
					+ "	group by docu.int_idpagador order by 1) as	tfd	on 	tfd.pagador=clie.seq_idcliente left join (select  notac.int_idpagador, sum (notac.dbl_saldo)  as notas from administracion.tbl_dem_documentofiscal notac "
					+ " where notac.int_idtipodocumento =2 and notac.dbl_saldo <>0.0 GROUP BY notac.int_idpagador order by 1) as nc "
					+ " on nc.int_idpagador = clie.seq_idcliente left join ( select re.int_idcliente,sum (re.dbl_saldo) as saldorecibo from administracion.tbl_dem_recibo re where  re.dbl_saldo <>0.0  GROUP BY re.int_idcliente order by 1 "
					+ " ) as r on r.int_idcliente = clie.seq_idcliente ";
					
				
			String	condiciones ="where clie.int_tipocliente in ("+idFinales+")";
			String  grupoOrden ="GROUP BY clie.str_nombre ,clie.str_cedurif ,tfd.porpagar,nc.notas,saldorecibo order by porpagar desc "; 
						

				
		criterio = em.createSQLQuery(sqlbase+condiciones+grupoOrden).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Object object : criterio) {
			System.out.println(object.getClass());
			
		}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return  formatearReporteConsolidado(criterio);
	}
	@SuppressWarnings("unchecked")
	public List<Object> getPendienteAsociadasConsolidado(List<TipoProductor> tipo){
		Transaction tx = null;
		List<Object> criterio = new ArrayList< Object>();
		Session em =SessionDao.getInstance().getCurrentSession();
		tx = em.beginTransaction();		
		try{
			String listaIds ="";  
			for ( TipoProductor tipoProductor: tipo) {
				listaIds =listaIds+tipoProductor.getId()+",";
				
				
			}
			String idFinales = listaIds.substring(0, listaIds.length()-1); 
			/*
		String	sqlbase ="select clie.str_nombre  as nombre ,clie.str_cedurif as cedrif,"
				+ "sum (docu.dbl_saldo) as porPagar,sum (notac.dbl_saldo) as porCobrar,sum (notacargo.dbl_montosaldo) as cargosextras,"
				+ "sum (re.dbl_saldo) as enRecibo from  administracion.tbl_dem_documentofiscal docu "
				+ "join public.tbl_dem_clientes clie on docu.int_idpagador = clie.seq_idcliente  "
				+ "and  docu.int_idtipodocumento in (1,3) "
				+ "left  join administracion.tbl_dem_documentofiscal notac on notac.int_idpagador = clie.seq_idcliente "
				+ "and notac.int_idtipodocumento =2 and notac.dbl_saldo <>0.0 "
				+ "left join administracion.tbl_dem_recibo re on re.int_idcliente = clie.seq_idcliente and re.dbl_saldo <>0.0 "
				+ "left  join administracion.tbl_dem_notacargo notacargo on notacargo.int_idcliente = clie.seq_idcliente "
				+ "where docu.dbl_saldo >0.0 and docu.int_idtipodocumento in (1,3) "
				+ "and docu.str_serie is null ";
		
		String	condiciones ="and clie.int_tipocliente in ("+idFinales+")";
		String  grupoOrden ="group by clie.str_nombre,clie.str_cedurif "
							+ "order by porPagar desc "; */
		//criterio = em.createSQLQuery(sqlbase+condiciones+grupoOrden).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
			
			String	sqlbase =" select distinct clie.str_nombre  as nombre ,clie.str_cedurif as cedrif,tfd.porpagar as porpagar, nc.notas as cargosextras,saldorecibo as enrecibo from  public.tbl_dem_clientes clie "
					+ " join (select docu.int_idpagador as pagador,sum (docu.dbl_saldo) as porpagar from administracion.tbl_dem_documentofiscal docu  where docu.int_idtipodocumento in (1,3) "
					+ "	and docu.dbl_saldo >0.0 and docu.int_idtipodocumento in (1,3) and docu.str_serie is null "
					+ "	group by docu.int_idpagador order by 1) as	tfd	on 	tfd.pagador=clie.seq_idcliente left join (select  notac.int_idpagador, sum (notac.dbl_saldo)  as notas from administracion.tbl_dem_documentofiscal notac "
					+ " where notac.int_idtipodocumento =2 and notac.dbl_saldo <>0.0 GROUP BY notac.int_idpagador order by 1) as nc "
					+ " on nc.int_idpagador = clie.seq_idcliente left join ( select re.int_idcliente,sum (re.dbl_saldo) as saldorecibo from administracion.tbl_dem_recibo re where  re.dbl_saldo <>0.0  GROUP BY re.int_idcliente order by 1 "
					+ " ) as r on r.int_idcliente = clie.seq_idcliente ";
					
				
			String	condiciones ="where clie.int_tipocliente in ("+idFinales+")";
			String  grupoOrden ="GROUP BY clie.str_nombre ,clie.str_cedurif ,tfd.porpagar,nc.notas,saldorecibo order by porpagar desc "; 
						

				
			
			criterio = em.createSQLQuery(sqlbase+condiciones+grupoOrden).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		return  formatearReporteConsolidado(criterio);
	}
	
	public List<Object> formatearReporteConsolidado(	List<Object> criterio){
		List<Object> listaFormateada = new ArrayList<Object>();
		for (Object rowmapa : criterio) {
			HashMap<String, Object> mapa= (HashMap<String, Object>) rowmapa;
			String nombre = (String) mapa.get("nombre");
			String cedrif = (String) mapa.get("cedrif");
			Double porPagar = new Double(0);
			Double porCobrar= new Double(0);
			Double enRecibo = new Double(0);
			Double cargosExtras = new Double(0);
			//java.math.BigDecimal
			if (mapa.get("porpagar")!=null&&mapa.get("porpagar").getClass().getName().equals("java.math.BigDecimal")){
				porPagar = ((BigDecimal) mapa.get("porpagar")).doubleValue();
			}else {
				porPagar = (Double) mapa.get("porpagar");
				}
			if (mapa.get("porcobrar")!=null&&mapa.get("porcobrar").getClass().getName().equals("java.math.BigDecimal")){
				porCobrar = ((BigDecimal) mapa.get("porcobrar")).doubleValue();
			}else {
				porCobrar = (Double) mapa.get("porcobrar");
			}
		
			if (mapa.get("enrecibo")!=null&&mapa.get("enrecibo").getClass().getName().equals("java.math.BigDecimal")){
				enRecibo = ((BigDecimal) mapa.get("enrecibo")).doubleValue();
			}else {
				enRecibo = (Double) mapa.get("enrecibo");
			}
			
			if (mapa.get("cargosextras")!=null&&mapa.get("cargosextras").getClass().getName().equals("java.math.BigDecimal")){
				cargosExtras = ((BigDecimal) mapa.get("cargosextras")).doubleValue();
			}else {
				cargosExtras = (Double) mapa.get("cargosextras");
			}
		
			
		
			if (porPagar==null) porPagar=new Double(0);
			if (porCobrar==null) porCobrar=new Double(0);
			if (enRecibo==null) enRecibo=new Double(0);
			if (cargosExtras==null) cargosExtras=new Double(0);
			Object[] arrayConsolidado = new Object[]{nombre,cedrif,Real.redondeoMoneda(porPagar),Real.redondeoMoneda(porCobrar),Real.redondeoMoneda(enRecibo),Real.redondeoMoneda(cargosExtras)};
			listaFormateada.add(arrayConsolidado);
		} 
		return listaFormateada;
		
		 
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Object[] getDedudasPorCliente(Object[] cliente){
	
		
		Session em =SessionDao.getInstance().getCurrentSession();
				
		Double totalSaldoDocumentos = new Double(0);
		Double totalSaldoNotas = new Double(0);
		//obtenemos los Documentos Fiscales 
	String consultaDocumentos="select d from DocumentoFiscal d where d.tipoDocumento in (1,3) and d.serie is not null and d.montoSaldo >0"
			+ "and d.beneficiario.id= :id";
	List<DocumentoFiscal> documentos=em.createQuery(consultaDocumentos)
			.setInteger("id", (Integer) cliente[0])
			.list();
	for (DocumentoFiscal documentoFiscal : documentos) {
		totalSaldoDocumentos = documentoFiscal.getMontoSaldo()+totalSaldoDocumentos;
	}
		//obtenemos las Notas de cargos 
	String consultaNotas ="select n from NotaCargo  n where n.montoSaldo > 0 and n.cliente.id =:id"; 
	List<NotaCargo> notas=em.createQuery(consultaNotas)
			.setInteger("id", (Integer) cliente[0])
			.list();
	for (NotaCargo notaCargo : notas) {
		totalSaldoNotas=notaCargo.getMontoSaldo()+totalSaldoNotas;
	}
	Object[] arrayCliente = new Object[]{cliente,documentos,notas,totalSaldoDocumentos,totalSaldoNotas};
	return arrayCliente;
}
	@SuppressWarnings("unchecked")
	public Object[] getDedudasPorCliente(Cliente cliente){
	
		
		Session em =SessionDao.getInstance().getCurrentSession();
				
		Double totalSaldoDocumentos = new Double(0);
		Double totalSaldoNotas = new Double(0);
		List<Object[]> documentos = new ArrayList<Object[]>();
		List<NotaCargo>  notas =new ArrayList<NotaCargo>();
		try {

			Transaction tx = em.beginTransaction();
			//obtenemos los Documentos Fiscales
			
			 
		//String consultaDocumentos="select d from DocumentoFiscal d where d.tipoDocumento in (1,3) and d.serie is not null and d.montoSaldo >0"
			//	+ "and d.beneficiario.id= :id";
			
			String consultaDocumentos="select d.serie ,d.nroDocumento,d.nroControl ,d.tipoDocumento.descripcion,d.montoTotal, d.montoSaldo "
											+ "from DocumentoFiscal d where d.tipoDocumento in (1,3) and d.serie is not null and d.montoSaldo >0"+
											"and d.beneficiario.id= :id";
		 documentos=em.createQuery(consultaDocumentos)
				.setInteger("id", cliente.getId())
				.list();
		for (Object[] documentoFiscal : documentos) {
			System.out.println(documentoFiscal[5].getClass());
			totalSaldoDocumentos = (Double)documentoFiscal[5] +totalSaldoDocumentos;
		}
			//obtenemos las Notas de cargos 
		String consultaNotas ="select n from NotaCargo  n where n.montoSaldo > 0 and n.cliente.id =:id"; 
	notas=em.createQuery(consultaNotas)
				.setInteger("id", cliente.getId())
				.list();
		for (NotaCargo notaCargo : notas) {
			totalSaldoNotas=notaCargo.getMontoSaldo()+totalSaldoNotas;
		}

		tx.commit();	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			Object[] arrayCliente = new Object[]{cliente,documentos,notas,totalSaldoDocumentos,totalSaldoNotas};
	return arrayCliente;
}
	
	@SuppressWarnings("unchecked")
	public Object[] getDedudasPorCliente(Cliente cliente,Transaction tx,Integer tipoconsulta){
	
		
		Session em =SessionDao.getInstance().getCurrentSession();
				
		Double totalSaldoDocumentos = new Double(0);
		Double totalSaldoNotas = new Double(0);
		List<Object[]> documentos = new ArrayList<Object[]>();
		List<NotaCargo>  notas =new ArrayList<NotaCargo>();
		
		String condicionDocumentos ="";
		if (tipoconsulta==1)
			condicionDocumentos="and d.serie is not null";
		if (tipoconsulta==2)
			condicionDocumentos="and d.serie is null";
		if (tipoconsulta==3)
			condicionDocumentos="";
		
		try {

		
			//obtenemos los Documentos Fiscales
			
			 
		//String consultaDocumentos="select d from DocumentoFiscal d where d.tipoDocumento in (1,3) and d.serie is not null and d.montoSaldo >0"
			//	+ "and d.beneficiario.id= :id";
			
			String consultaDocumentos="select d.serie ,d.nroDocumento,d.nroControl ,d.tipoDocumento.descripcion,d.montoTotal, d.montoSaldo "
											+ "from DocumentoFiscal d where d.tipoDocumento in (1,3)  and d.montoSaldo >0"+
											"and d.beneficiario.id= :id ";
			
			
		 documentos=em.createQuery(consultaDocumentos+condicionDocumentos)
				.setInteger("id", cliente.getId())
				.list();
		for (Object[] documentoFiscal : documentos) {
			totalSaldoDocumentos = (Double)documentoFiscal[5] +totalSaldoDocumentos;
		}
			//obtenemos las Notas de cargos 
		String consultaNotas ="select n from NotaCargo  n where n.montoSaldo > 0 and n.cliente.id =:id"; 
	notas=em.createQuery(consultaNotas)
				.setInteger("id", cliente.getId())
				.list();
		for (NotaCargo notaCargo : notas) {
			totalSaldoNotas=notaCargo.getMontoSaldo()+totalSaldoNotas;
		}

	
			
		} catch (Exception e) {
			System.out.println(e);
		}
			Object[] arrayCliente = new Object[]{cliente,documentos,notas,totalSaldoDocumentos,totalSaldoNotas};
	return arrayCliente;
}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDedudasPendientesTotales(){
		Transaction tx = null;
		List<Object[] > criterio = new ArrayList<Object[] >();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Integer> clientesDeudas=new ArrayList<Integer>();
		try {
			tx = em.beginTransaction();		
			//obtenemos los clientes con deudas 
		String consultaClientes="select a.id from Cliente a where (a.id in (select b.beneficiario.id from DocumentoFiscal b where b.montoSaldo > 0)) or"
				+ "(a.id in (select n.cliente.id from NotaCargo n where n.montoSaldo > 0))";
		clientesDeudas=em.createQuery(consultaClientes).list();
		for (Integer idcliente : clientesDeudas) {
			Cliente	clienteaux = (Cliente) em.get(Cliente.class,idcliente);
				Object[] clienteDeudas = getDedudasPorCliente(clienteaux,tx,3);
				criterio.add(clienteDeudas);
			}
		tx.commit();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
	
	return criterio;
}

	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDedudasPendientes(List<TipoProductor> tipoProductor,Integer tipoconsulta){
		Transaction tx = null;
		List<Object[] > criterio = new ArrayList<Object[] >();
		Session em =SessionDao.getInstance().getCurrentSession();
		List<Integer> clientesDeudas=new ArrayList<Integer>();
		try {
			tx = em.beginTransaction();		
			String condicionDocumentos ="";
			if (tipoconsulta==1)
				condicionDocumentos="and b.serie is not null";
			if (tipoconsulta==2)
				condicionDocumentos="and b.serie is null";
			if (tipoconsulta==3)
				condicionDocumentos="";
			
			
		String consultaClientes="select a.id from Cliente a where ((a.id in (select b.beneficiario.id from DocumentoFiscal b where b.montoSaldo > 0"
				+ condicionDocumentos
				+ " )) or"
				+ "(a.id in (select n.cliente.id from NotaCargo n where n.montoSaldo > 0)))";
		if (tipoProductor!=null){
			consultaClientes=consultaClientes + " and  a.tipo in (:tipo)";
			clientesDeudas=em.createQuery(consultaClientes).setParameterList("tipo", tipoProductor).
					list();
			}else
		clientesDeudas=em.createQuery(consultaClientes).list();
		for (Integer idcliente : clientesDeudas) {
			Cliente	clienteaux = (Cliente) em.get(Cliente.class,idcliente);
				Object[] clienteDeudas = getDedudasPorCliente(clienteaux,tx,tipoconsulta);
				criterio.add(clienteDeudas);
			}
		tx.commit();	
		} catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return criterio;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getDedudasPendientes(List<Productor> productores){
		Transaction tx = null;
		List<Object[] > criterio = new ArrayList<Object[] >();
		Session em =SessionDao.getInstance().getCurrentSession();
		try {
			tx = em.beginTransaction();		
			for (Productor productor : productores) {
				Object[] clienteDeudas = getDedudasPorCliente(productor,tx,1);
				criterio.add(clienteDeudas);
			}
			tx.commit();	
		} 
		catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return criterio;
	}
	
	
}