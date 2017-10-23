package cpc.test;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.Cotizacion;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.TipoSolicitud;
import cpc.modelo.demeter.gestion.EstadoSolicitud;
import cpc.modelo.demeter.mantenimiento.Consumible;
import cpc.modelo.demeter.mantenimiento.ConsumibleEquivalente;
import cpc.modelo.demeter.mantenimiento.ConsumibleEquivalentePK;
import cpc.modelo.demeter.mantenimiento.DetalleArticuloMovimiento;
import cpc.modelo.demeter.mantenimiento.DetalleSalidaExternaArticulo;
import cpc.modelo.demeter.mantenimiento.Herramienta;
import cpc.modelo.demeter.mantenimiento.HerramientaEquivalente;
import cpc.modelo.demeter.mantenimiento.MovimientoArticulo;
import cpc.modelo.demeter.mantenimiento.Repuesto;
import cpc.modelo.demeter.mantenimiento.RepuestoEquivalente;
import cpc.modelo.demeter.mantenimiento.RepuestoEquivalentePK;
import cpc.modelo.demeter.mantenimiento.SolicitudServicioTecnico;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.ClaseAlmacen;
import cpc.modelo.sigesp.basico.TipoAlmacen;
import cpc.modelo.sigesp.basico.UnidadCentralizada;
import cpc.modelo.sigesp.indice.UnidadAdministrativaPK;
import cpc.negocio.demeter.gestion.NegocioTransferenciaActivo;
import cpc.negocio.sigesp.NegocioAlmacen;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.gestion.PerEstadoSolicitud;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerConsumible;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerSolicitudServicioTecnico;
import cpc.persistencia.sigesp.implementacion.PerActivoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerAlmacen;
import cpc.persistencia.sigesp.implementacion.PerClaseAlmacen;
import cpc.persistencia.sigesp.implementacion.PerTipoAlmacen;
import cpc.persistencia.sigesp.implementacion.PerUnidadAdministrativa;
import cpc.persistencia.sigesp.implementacion.PerUnidadCentralizada;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaAlmacen {
	
	@SuppressWarnings("unused")
	public static void main (String[] args) throws ExcFiltroExcepcion, ParseException{
	//	NegocioAlmacen negocio = NegocioAlmacen.getInstance();
				
		  SessionFactory sf = new Configuration().configure().buildSessionFactory();
		  Session session = sf.openSession();
		  Transaction t =  session.beginTransaction();
		  t.begin();
		
		/*Almacen almacen = new Almacen();
	    PerUnidadAdministrativa unidadAdministrativa = new PerUnidadAdministrativa();
		PerTipoAlmacen tipoAlmacen = new PerTipoAlmacen();
		PerUbicacionGeografica ubicacion = new PerUbicacionGeografica();
		PerTrabajador trabajador = new PerTrabajador();
		
		almacen.setUnidadAdministrativa(unidadAdministrativa.buscarId(new UnidadAdministrativaPK("0001", "0000000001")));
		almacen.setNombre("TALLER-CENTRAL");
		almacen.setDescripcion("ALMACEN TALLER PARA ACTIVOS DE CENTRAL");
		almacen.setTipoAlmacen(tipoAlmacen.getTipoDeposito());
		almacen.setUbicacionGeografica(ubicacion.buscarId(new ParroquiaPK("058", "013", "006", "001")));
		almacen.sea2tTrabajador(trabajador.buscarId(1l));
		almacen.setLocalidad("Av intercomunal Barquisimeto Cabudare".toUpperCase());
		almacen.setTelefono("0251-0000002");
		
		negocio.setAlmacen(almacen);
		negocio.guardar();*/
		
	/*	
		PerTipoAlmacen tipo = new PerTipoAlmacen();
		List<TipoAlmacen> aa = tipo.getAll();
		
		for (TipoAlmacen tipoAlmacen : aa) {
			System.out.println(tipoAlmacen.getNombre() +" "+tipoAlmacen.getNombreClase());
		}*/
		//System.out.println("Unidad"+negocio.obtenerUnidadAdministrativa().getNombre());
	
		/*
		PerUnidadCentralizada a = new PerUnidadCentralizinicioada();
		List<UnidadCentralizada> b = a.getTodos();
		
		for (UnidadCentralizada unidadCentralizada : b) {
			System.out.println(unidadCentralizada.getNombre());
		}*/
		
		/*PerClaseAlmacen a = new PerClaseAlmacen();
		ClaseAlmacen b = a.getMecanizado();
		
			System.out.println( b.getNombreClase()+" "+b.getdescripcion());*/
		/*			
		PerAlmacen a = new PerAlmacen();
		
		
		List<Almacen> z = a.getAlmacenesOperativosMecanizado();
		
		for (Almacen almacen : z) {
			System.out.println(almacen.getNombre()                                                                                                                                          );
		}*/
		
		/*   PerUnidadAdministrativa unidadAdministrativa = new PerUnidadAdministrativa();
 NegocioTransferenciaActivo negocio = NegocioTransferenciaActivo.getInstance();
		negocio.obtenerAlmacenesPorUnidadAdministrativa(unidadAdministrativa.buscarId(new UnidadAdministrativaPK("0001", "0000000001")), new PerTipoAlmacen().getTipoMecanizado());
*/
		
	/*	DaoGenerico<RepuestoEquivalente,RepuestoEquivalentePK> a = new DaoGenerico<RepuestoEquivalente, RepuestoEquivalentePK>(RepuestoEquivalente.class);
		List<RepuestoEquivalente> b =  a.getcritaeriaAll();
		for (RepuestoEquivalente repuesto :  b) {
			System.out.println(repuesto.getRepuestoOriginal().getCodigoCCNU() +" "+ repuesto.getRepuestoEquivalente().getCodigoCCNU());	
		}*/
	
		/*
	
		DaoGenerico<Repuesto, Integer> a = new DaoGenerico<Repuesto, Integer>(Repuesto.class);
		List<Repuesto> b = a.getAll();
		for (Repuesto repuesto : b) {
		List<RepuestoEquivalente> z = repuesto.getRepuestoEquivalente();
		int i = 0;fetch=FetchType.EAGER
	for (RepuestoEquivalente repuestoEquivalente : z) {
		i++;
		System.out.println(i);
		System.out.println(repuestoEquivalente.getRepuestoOriginal().getId() +"55 "+repuestoEquivalente.getRepuestoEquivalente().getId());
	}
	}
	*/
		
		/*
			DaoGenerico<RepuestoEquivalente, RepuestoEquivalentePK> as = new DaoGenerico<RepuestoEquivalente, RepuestoEquivalentePK>(RepuestoEquivalente.class);
	RepuestoEquivalente aa = new RepuestoEquivalente();
	
	RepuestoEquivalentePK id =  new RepuestoEquivalentePK(4,4);
	
	aa.setId(id);
	
	try {
		as.guardaroupdate(aa,null);
	} catch (Exception e) {	
	
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	
	System.out.println("chevere");
	
	*/
		
	/*	
		DaoGenerico<ObjetoAlmacenCantidad, ObjetoAlmacenCantidadPK> as = new DaoGenerico<ObjetoAlmacenCantidad, ObjetoAlmacenCantidadPK>(ObjetoAlmacenCantidad.class);
		RepuestoEquivalente aa = new RepuestoEquivalente();
		
		RepuestoEquivalentePK id =  new RepuestoEquivalentePK(581, 580);
		
		aa.setId(id);
		
		try {
		List<Criterion> a = null;
		@SuppressWarnings("unused")
		List<ObjetoAlmacenCantidad> f = as.getcritaeriaAll(a );
		
		for (ObjetoAlmacenCantidad objetoAlmacenCantidad : f) {
			System.out.println(objetoAlmacenCantidad.getAlmacen().getNombre()+" "+objetoAlmacenCantidad.getArticuloVenta().toString()+" "+objetoAlmacenCantidad.getCantidad());
		//	Repuesto ass = (Repuesto)objetoAlmacenCantidad;
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
		DaoGenerico<Repuesto, Integer> as = new DaoGenerico<Repuesto, Integer>(Repuesto.class);
		DaoGenerico<Articulselect controlsed_.seq_control, controlsed_.str_abogado as str2_1871_, controlsed_.str_cedulacoordinadorsede as str3_1871_, controlsed_.int_controlcliente as int4_1871_, controlsed_.int_controlconsumoarticulo as int5_1871_, controlsed_.int_controlcontrato as int6_1871_, controlsed_.int_controlcontratoserviciotecnico as int7_1871_, controlsed_.int_controlcotizacion as int8_1871_, controlsed_.int_controldevolucionarticulo as int9_1871_, controlsed_.int_controlentradaarticulo as int10_1871_, controlsed_.int_nro_control as int11_1871_, controlsed_.int_controlnotacredito as int12_1871_, controlsed_.int_controlnotadebito as int13_1871_, controlsed_.int_controlentrada as int14_1871_, controlsed_.int_control_rec as int15_1871_, controlsed_.int_controlsalida as int16_1871_, controlsed_.int_controlsalidaarticulo as int17_1871_, controlsed_.int_idcontroltransfer as int18_1871_, controlsed_.int_controltransferenciaarticulo as int19_1871_, controlsed_.bol_controlunico as bol20_1871_, controlsed_.str_cuentaclientecobro as str21_1871_, controlsed_.str_cuentaclientepago as str22_1871_, controlsed_.str_cuentaadelanto as str23_1871_, controlsed_.str_cuentacaja as str24_1871_, controlsed_.str_cuentadescuento as str25_1871_, controlsed_.str_cuentaimpuesto as str26_1871_, controlsed_.sc_cuentaingreso as sc27_1871_, controlsed_.spi_cuenta as spi28_1871_, controlsed_.str_direccionsede as str29_1871_, controlsed_.dte_fecha_cierre_cont as dte30_1871_, controlsed_.dte_inicio as dte31_1871_, controlsed_.str_impreabogado as str32_1871_, controlsed_.str_formatocliente as str33_1871_, controlsed_.str_coordinadorsede as str34_1871_, controlsed_.int_debitointerno as int35_1871_, controlsed_.int_idcontroldocumento as int36oVenta, Integer> a1 = new DaoGenerico<ArticuloVenta, Integer>(ArticuloVenta.class);
		Repuesto aa = new Repuesto();
		
		Integer id = 400;
		
		
		List<Criterion> a = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 400)});
		
		 ArticuloVenta d = a1.getcritaeriaUno(a);
		
		
		
		aa.setArticuloVenta(d);
		
			try {
			
			
		
				
				as.guardaroupdate(aa,null);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
*/
		/*
		DaoGenerico<Consumible, Integer> as = new DaoGenerico<Consumible, Integer>(Consumible.class);
		DaoGenerico<ArticuloVenta, Integer> a1 = new DaoGenerico<ArticuloVenta, Integer>(ArticuloVenta.class);
		Consumible aa = new Consumible();
		
		Integer id = 400;
		
		
		List<Criterion> a = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 401)});
		
		 ArticuloVenta d = a1.getcritaeriaUno(a);
		
		
		
		aa.setArticuloVenta(d);
		
			try {
			
			
		
				
				as.guardaroupdate(aa,null);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}a
		*/
		
		/*
		DaoGenerico<MovimientoArticulo, Integer> as = new DaoGenerico<MovimientoArticulo, Integer>(MovimientoArticulo.class);
	
		List<MovimientoArticulo> z = as.getAll();
		
		for (MovimientoArticulo movimientoArticulo : z) {
		List<DetalleArticuloMovimiento> a = movimientoArticulo.getDetallesmoviento();
		for (DetalleArticuloMovimiento detalleArticuloMovimiento : a) {
		System.out.println(detalleArticuloMovimiento.getCatidad().toString());	
		}
		
		}*/
	/*	
		DaoGenerico<Consumible, Integer> as = new DaoGenerico<Consumible, Integer>(Consumible.class);
		DaoGenerico<ConsumibleEquivalente,ConsumibleEquivalentePK> a3 = new DaoGenerico<ConsumibleEquivalente, ConsumibleEquivalentePK>(Consumible.class);
		DaoGenerico<ArticuloVenta, Integer> a1 = new DaoGenerico<ArticuloVenta, Integer>(ArticuloVenta.class);
		Consumible aa = new Consumible();
		
		Integer id = 7;
		
		
		List<Criterion> a = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 14)});
		
		 ArticuloVenta d = a1.getcritaeriaUno(a);
		
		
		 List<Criterion> r1 = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 1)});
		 List<Criterion> r2 = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 3)});
	//	aa.setArticuloVenta(d);
		aa = as.getcritaeriaUno(a);
		Consumible equivalente1 = as.getcritaeriaUno(r1);
		Consumible equivalente2 = as.getcritaeriaUno(r2);
		ConsumibleEquivalente ca =new ConsumibleEquivalente();
		ConsumibleEquivalente cb =new ConsumibleEquivalente();
		ca.setConsumibleOri(aa);
		cb.setConsumibleOri(aa);
		ca.setConsumibleEq(equivalente1);
		cb.setConsumibleEq(equivalente2);
		ConsumibleEquivalentePK pri1 = new ConsumibleEquivalentePK();
		pri1.setIdConsumibleEquivalente(equivalente1.getId());
		pri1.setIdConsumibleOriginal(aa.getId());
		ca.setId(pri1);
	//	ca.setId(new Consuselect controlsed_.seq_control, controlsed_.str_abogado as str2_1871_, controlsed_.str_cedulacoordinadorsede as str3_1871_, controlsed_.int_controlcliente as int4_1871_, controlsed_.int_controlconsumoarticulo as int5_1871_, controlsed_.int_controlcontrato as int6_1871_, controlsed_.int_controlcontratoserviciotecnico as int7_1871_, controlsed_.int_controlcotizacion as int8_1871_, controlsed_.int_controldevolucionarticulo as int9_1871_, controlsed_.int_controlentradaarticulo as int10_1871_, controlsed_.int_nro_control as int11_1871_, controlsed_.int_controlnotacredito as int12_1871_, controlsed_.int_controlnotadebito as int13_1871_, controlsed_.int_controlentrada as int14_1871_, controlsed_.int_control_rec as int15_1871_, controlsed_.int_controlsalida as int16_1871_, controlsed_.int_controlsalidaarticulo as int17_1871_, controlsed_.int_idcontroltransfer as int18_1871_, controlsed_.int_controltransferenciaarticulo as int19_1871_, controlsed_.bol_controlunico as bol20_1871_, controlsed_.str_cuentaclientecobro as str21_1871_, controlsed_.str_cuentaclientepago as str22_1871_, controlsed_.str_cuentaadelanto as str23_1871_, controlsed_.str_cuentacaja as str24_1871_, controlsed_.str_cuentadescuento as str25_1871_, controlsed_.str_cuentaimpuesto as str26_1871_, controlsed_.sc_cuentaingreso as sc27_1871_, controlsed_.spi_cuenta as spi28_1871_, controlsed_.str_direccionsede as str29_1871_, controlsed_.dte_fecha_cierre_cont as dte30_1871_, controlsed_.dte_inicio as dte31_1871_, controlsed_.str_impreabogado as str32_1871_, controlsed_.str_formatocliente as str33_1871_, controlsed_.str_coordinadorsede as str34_1871_, controlsed_.int_debitointerno as int35_1871_, controlsed_.int_idcontroldocumento as int36mibleEquivalentePK(ca.getConsumibleOriginal().getId(), ca.getConsumibleEquivalente().getId() ));
	//	ca.setId(new ConsumibleEquivalentePK(cb.getConsumibleOriginal().getId(), cb.getConsumibleEquivalente().getId() ));
		
		List<ConsumibleEquivalente> consumibleEquivalentes = Arrays.asList(new  ConsumibleEquivalente[] {ca});; 
	    
		aa.setConsumibleEquivalente(consumibleEquivalentes);
		
			try {
			
			
		
				
			as.guardaroupdate(aa,aa.getId());
			List<ConsumibleEquivalente> zz = aa.getConsumibleEquivalente();
		
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		*/
		
		
		
		
		
		
		
		
		
		
		
		/*
		
	Calendar calendario = Calendar.getInstance();
	calendario.set(1986, 04, 16);
		Date inicio = new Date ();
		   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		inicio =   ft.parse("1986-04-16");

		
		 List<DetalleDocumentoFiscal> lista = new PerFactura().getServiciosFacturados(inicio,new Date());
		 for (DetalleDocumentoFiscal detalleDocumentoFiscal : lista) {
	//			 System.out.println(detalleDocumentoFiscal.getServicio().getDescripcion() +" "+detalleDocumentoFiscal.getCantidad()+" "+ detalleDocumentoFiscal.getAlicuotaNombre()+" "+detalleDocumentoFiscal.getAlicuota().getPorcentaje() +" "+ detalleDocumentoFiscal.getServicio().getStrClase());
			 
			 if (detalleDocumentoFiscal.getServicio().getClass().equals(Labor.class)){
				 Labor labor = ((Labor)detalleDocumentoFiscal.getServicio());
				 System.out.println(((Labor)detalleDocumentoFiscal.getServicio()).getServicio().getTipoServicio().getNombre()); 
			 }
			 
			if (detalleDocumentoFiscal.getServicio().getClass().equals(ArticuloVenta.class)){
				ArticuloVenta  articulo= ((ArticuloVenta)detalleDocumentoFiscal.getServicio());
				 System.out.println(((ArticuloVenta)detalleDocumentoFiscal.getServicio()).getActivo()); 
			 }
			 System.out.println();
			}
		 System.out.println(lista.size());
 /*ResultSet d;
try {
	d = a.correrSql( "insert into  administracion.tbl_dem_tipocontrato (seq_idtipocontrato,str_nombre ,str_clausula) " +
	 		"values (6,'prueba','prueba') ;");
	
} catch (Exception e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}

	
		
	System.out.println("ok");
	*/	
		
	
	//	List<Criterion> a = Arrays.asList(new  Criterion[] { Restrictions.eq("id", 14)});
		
	
		
	
	//	aa.setArticuloVenta(d);
	//	Consumible aa = as.getcritaeriaUno(a);
//	List<ArticuloVenta> z = new PerArticuloVenta().getAllArticuloConsumible();
	
//	List<Consumible> d = new PerConsumible().getAll();
	//	ca.setId(new ConsumibleEquivalentePK(ca.getConsumibleOriginal().getId(), ca.getConsumibleEquivalente().getId() ));
	//	ca.setId(new ConsumibleEquivalentePK(cb.getConsumibleOriginal().getId(), cb.getConsumibleEquivalente().getId() ));
		
//	System.out.println("2");
		
			try {
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
}
