package cpc.persistencia.demeter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.basico.FuncionTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.DetalleSolicitud;
import cpc.modelo.demeter.gestion.Solicitud;
import cpc.modelo.demeter.gestion.SolicitudMecanizado;
import cpc.modelo.demeter.sincronizacion.DetalleExport;
import cpc.modelo.demeter.sincronizacion.DetalleImportar;
import cpc.modelo.demeter.sincronizacion.DetalleImportarError;
import cpc.modelo.demeter.sincronizacion.Exportar;
import cpc.modelo.demeter.sincronizacion.Importar;
import cpc.modelo.demeter.sincronizacion.SedeDemeter;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.ministerio.gestion.ProductorNatural;
import cpc.modelo.ministerio.gestion.UnidadProductiva;
import cpc.persistencia.DaoGenerico;
import cpc.persistencia.SessionDao;
import cpc.zk.componente.interfaz.IZkAplicacion;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class PerImportar extends DaoGenerico<Importar, Integer> {

	private static Configuration cfg;
	private static SessionFactory sf;
	private static List<DetalleImportar> deImportars = new ArrayList<DetalleImportar>();
	private List<DetalleImportarError> deImportarsError = new ArrayList<DetalleImportarError>();

	public PerImportar() {
		super(Importar.class);
		Session session2;
		Configuration cfg;
		SessionFactory sf;
		// TODO Auto-generated constructor stub
	}

	public void guardar(DetalleImportar detalle) {
		// TODO Auto-generated method stub
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			em.saveOrUpdate(detalle);
			em.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

	}

	public DetalleImportar getPerteneceObjecto(String idsede,
			SedeDemeter sedeDemeter) throws ExcFiltroExcepcion {
		// TODO Auto-generated method stub
		DetalleImportar detalle = null;
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			System.out.println(idsede + "   " + sedeDemeter.getIdSede());

			detalle = (DetalleImportar) em
					.createCriteria(DetalleImportar.class)
					.add(Restrictions.eq("idsede", idsede))
					.add(Restrictions.eq("sedeDemeter", sedeDemeter))
					.uniqueResult();
			tx.commit();
			return detalle;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}

	}

	public DetalleImportar getDetalleImportar(String id, String clase,
			SedeDemeter sede) throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			List lista = em.createCriteria(DetalleImportar.class)
					.add(Restrictions.eq("sedeDemeter", sede))
					.add(Restrictions.eq("nameclass", clase))
					.add(Restrictions.eq("idsede", id)).list();
			tx.commit();
			return (DetalleImportar) (lista.size() != 0 ? lista.get(0) : null);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
	}

	public String getidsede(String idservidor, String clase, SedeDemeter sede)
			throws ExcFiltroExcepcion {
		Session em = SessionDao.getInstance().getCurrentSession();
		Transaction tx = null;
		try {
			tx = em.beginTransaction();
			List<DetalleImportar> lista = em
					.createCriteria(DetalleImportar.class)
					.add(Restrictions.eq("sedeDemeter", sede))
					.add(Restrictions.eq("nameclass", clase))
					.add(Restrictions.eq("idservidor", idservidor)).list();
			tx.commit();
			// return (lista.size() != 0? lista.get(0).getIdSede():null);
			return null;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw new ExcFiltroExcepcion(e.getMessage());
		}
	}

	public Importar crear_importar(Exportar exportar) throws Exception {
		// TODO Auto-generated method stub
		PerSedeDemeter perSedeDemeter = new PerSedeDemeter();

		Importar importar = new Importar();
		SedeDemeter sedeDemeter = new SedeDemeter();
		sedeDemeter = perSedeDemeter.buscarId(exportar.getSede().getIdSede());

		if (sedeDemeter != null) {
			importar.setSedeDemeter(sedeDemeter);
			importar.setCedula(exportar.getCedula());
			importar.setMd5(exportar.getMd5());
			this.guardar(importar, importar.getId());
		} else {
			throw new Exception("Sede No autorizada para Importar ");
		}

		return importar;
	}

	public String getid_toString(Importar obj) {
		// TODO Auto-generated method stub
		return obj.getId().toString();
	}

	public Integer getid(cpc.modelo.demeter.sincronizacion.Importar objeto) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sincronizarObjecto(
			cpc.modelo.demeter.sincronizacion.Importar obj_sede,
			Importar obj_export, Exportar exportar, Importar importar) {
		// TODO Auto-generated method stub

	}

	public Exportar importarData(Exportar exportar2)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ClassNotFoundException {

	/*	ApplicationContext context = new ClassPathXmlApplicationContext(
				"beansConfiguracion.xml");
		BeanFactory factory = (BeanFactory) context;
		String sede = (String) factory.getBean("idsede");

		AuditorUsuario appaaaa = new AuditorUsuario();
		IZkAplicacion a = (IZkAplicacion) TransactionSynchronizationManager
				.getResource("obj");
		// TransactionSynchronizationManager.isSynchronizationActive();
		boolean b = TransactionSynchronizationManager.hasResource("obj");
		boolean igual = appaaaa.equals(a);
		if (!igual) {
			if (b) {
				TransactionSynchronizationManager.unbindResource("obj");
			}

			TransactionSynchronizationManager.bindResource("obj", appaaaa);
		}
		List<Class> dd = listaclase();
		try {
			List<DetalleExport> sincroorigen = exportar2.getDetalles();
			Collections.sort(sincroorigen, new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					return new Integer(((DetalleExport) o1).getId())
							.compareTo(new Integer(((DetalleExport) o1).getId()));
				}

			});
			for (DetalleExport sincronizacion : sincroorigen) {

				Object guarda = deserialize(sincronizacion.getObjeto());
				Object idorigen = new Object();

				if (sincronizacion.isIsupdate() == false) {
					Session session4 = abrirSession();
					Transaction tx2 = session4.beginTransaction();
					try {
						analizarobjeto(guarda, dd, new ArrayList<Object>(),
								tx2, session4);
						AntesDeGuardar(guarda);
						session4.saveOrUpdate(guarda);
						tx2.commit();
						System.out.println("ok");
						sincronizacion.setCompletado(true);

						DetalleImportar detalleImportar = new DetalleImportar();
						detalleImportar.setCompletado(true);
						detalleImportar.setIddestino(serialize(guarda
								.getClass().getMethod("getId", null)
								.invoke(guarda)));
						detalleImportar.setIdorigen(sincronizacion
								.getIdorigen());
						detalleImportar.setObjeto(serialize(guarda));
						detalleImportar.setNameclass(guarda.getClass()
								.getName());
						detalleImportar
								.setIsupdate(sincronizacion.isIsupdate());
						Object pruebaidorigen = deserialize(detalleImportar
								.getIdorigen());
						Object pruebaiddestino = deserialize(detalleImportar
								.getIddestino());
						if (pruebaidorigen.equals(pruebaiddestino)) {
							System.out.println("paso algo");
						}
						;
						deImportars.add(detalleImportar);

						session4.evict(guarda);
					} catch (Exception e) {
						e.printStackTrace();

						Writer strerror = new StringWriter();
						PrintWriter printWriter = new PrintWriter(strerror);
						e.printStackTrace(printWriter);
						System.out.println(strerror.toString());

						System.out.println(e);
						tx2.rollback();
						DetalleImportarError error = new DetalleImportarError();
						error.setExcepcion(serialize(e));
						error.setIdorigen(serialize(guarda.getClass()
								.getMethod("getId", null).invoke(guarda)));
						error.setIsupdate(sincronizacion.isIsupdate());
						error.setNameclass(guarda.getClass().getName());
						error.setObjeto(serialize(guarda));
						System.out.println(strerror.toString().length());
						error.setStrError(strerror.toString());

						deImportarsError.add(error);
						session4.evict(guarda);
					}

					cerrarSession(session4);
				}
				if (sincronizacion.isIsupdate() == true) {
					Session session5 = abrirSession();
					Transaction tx3 = session5.beginTransaction();

					try {

						seteariddestino(guarda, session5);
						actualizarobjeto(guarda, dd, new ArrayList<Object>(),
								tx3, session5);
						if (guarda instanceof Trabajador) {
							Integer id = ((Trabajador) guarda).getId();
							Trabajador trabajador = (Trabajador) guarda;
							System.out.println(trabajador.getNombre());
							String nombre = trabajador.getNombre();
							List<FuncionTrabajador> funciones = new ArrayList<FuncionTrabajador>();
							for (FuncionTrabajador item : ((Trabajador) guarda)
									.getFunciones()) {
								funciones.add(item);

							}
							trabajador.setFunciones(funciones);

							session5.saveOrUpdate(trabajador);

						} else {

							session5.saveOrUpdate(guarda);
						}
						tx3.commit();
						System.out.println("ok2");

						sincronizacion.setIddestino(serialize(guarda.getClass()
								.getMethod("getId", null).invoke(guarda)));
						sincronizacion.setCompletado(true);

						DetalleImportar detalleImportar = new DetalleImportar();
						detalleImportar.setCompletado(true);
						detalleImportar.setIddestino(serialize(guarda
								.getClass().getMethod("getId", null)
								.invoke(guarda)));
						detalleImportar.setIdorigen(sincronizacion
								.getIdorigen());
						detalleImportar.setObjeto(serialize(guarda));
						detalleImportar.setNameclass(guarda.getClass()
								.getName());
						detalleImportar
								.setIsupdate(sincronizacion.isIsupdate());
						Object pruebaidorigen = deserialize(detalleImportar
								.getIdorigen());
						Object pruebaiddestino = deserialize(detalleImportar
								.getIddestino());
						if (pruebaidorigen.equals(pruebaiddestino)) {
							System.out.println("paso algo");
						}
						;
						deImportars.add(detalleImportar);
						session5.evict(guarda);

					} catch (Exception e) {
						e.printStackTrace();

						Writer strerror = new StringWriter();
						PrintWriter printWriter = new PrintWriter(strerror);
						e.printStackTrace(printWriter);
						System.out.println(strerror.toString());
						tx3.rollback();
						System.out.println("2" + e + guarda.toString());
						DetalleImportarError error = new DetalleImportarError();
						error.setExcepcion(serialize(e));
						error.setIdorigen(serialize(guarda.getClass()
								.getMethod("getId", null).invoke(guarda)));
						error.setIsupdate(sincronizacion.isIsupdate());
						error.setNameclass(guarda.getClass().getName());
						error.setObjeto(serialize(guarda));
						System.out.println(strerror.toString().length());
						error.setStrError(strerror.toString());
						deImportarsError.add(error);
						session5.evict(guarda);
					}
					cerrarSession(session5);
				}
				System.out.println("guardado sincro");

				System.out.println("guardado");

				System.out.println();
			}
			// org.hibernate.classic.Session session3 = sf2.openSession();
			Session session3 = abrirSession();
			Transaction tx1 = session3.beginTransaction();

			Importar importar = new Importar();
			importar.setDetalles(deImportars);
			importar.setDetalleserror(deImportarsError);
			for (DetalleImportar detalleImportar : deImportars) {
				detalleImportar.setImportar(importar);
			}
			for (DetalleImportarError detalleImportarError : deImportarsError) {
				detalleImportarError.setImportar(importar);
			}
			session3.save(importar);
			tx1.commit();
			cerrarSession(session3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("fin");
		return exportar2;
		*/
		return null;
	}

	public static void analizarobjeto(Object object, List<Class> dd,
			List<Object> trabajados, Transaction tx, Session session2)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException,
			NoSuchMethodException, IOException, ClassNotFoundException,
			InstantiationException {

		if (object != null) {
			if (object.getClass().getName()
					.equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
				System.out
						.println("aqui esta pasando productor analizarobjeto(Object, List<Class>, List<Object>, Transaction, Session)");
			}

			Method[] metodos = object.getClass().getMethods();
			Arrays.sort(metodos, new Comparator() {
					 public int compare(Object o1, Object o2) { // TODO		 Auto-generated method stub 
						 return new String(((Method)o2).toString()).compareTo(new
					String(((Method)o1).toString())); }
					});
				
			  for (Method metodo : metodos) {
				  System.out.println(metodo.toString());
			  }
			//	JOptionPane.showMessageDialog(null,"orden"  ,"setnull",JOptionPane.WARNING_MESSAGE);
			  
			for (Method metodo : metodos) {
				if (metodo.getName().equals("getId")) {

				} else {
					if (metodo.getReturnType().isPrimitive()) {
					}

					if (dd.contains(metodo.getReturnType())) {
					//	if (metodo.invoke(object)!=null){if (metodo.invoke(object).getClass().getName().equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
						if (metodo.invoke(object)!=null){
							if (metodo.invoke(object)!=null) {	
						System.out.println("aqui esta pasando analizar3 "+metodo.invoke(object).getClass());
							System.out.println(trabajados.contains(metodo.invoke(object)));
						

							Object iddestino = getIdDestino(metodo.invoke(object), session2, tx, dd);
							Object idorigen = metodo.invoke(object).getClass().getMethod("getId", null)
									.invoke(metodo.invoke(object));
							boolean condicion1 = (iddestino != null);
							boolean condicion2 = false;
							if (iddestino!=null&&idorigen!=null) 
							
							condicion2 = !idorigen.equals(iddestino);
							boolean condicion3 = trabajados.contains(metodo.invoke(object));
							//boolean condicion4 = !Esmaestro(metodo.invoke(object), dd, tx, session2);
							if (condicion1 && condicion2) {
								if (condicion3) {
								//	JOptionPane.showMessageDialog(null,"borrando "+idorigen+" "+"deberia ser "+iddestino+" "+condicion4 ,"analizar2 "+object.getClass(),JOptionPane.WARNING_MESSAGE);
									trabajados.remove(metodo.invoke(object));
								}
							}
							
						}
						}
						if (!trabajados.contains(metodo.invoke(object))) {
							trabajados.add(metodo.invoke(object));
							// analizarobjeto(metodo.invoke(object),dd,trabajados,
							// tx,session2,syn);
							if (object
									.getClass()
									.getName()
									.equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
								System.out
										.println("aqui esta pasando productoranalñizar3");
								System.out.println(Esmaestro(object, dd, tx,
										session2));
							}
							;
							analizarobjeto(metodo.invoke(object), dd,
									trabajados, tx, session2);

						}
					}
					// sino es primitivo es un objeto
					// lleemos el resto de los objetos
					// if (metodo.getName()!="equals"){
					// Object objetosecundario = metodo.invoke(object);
					// analizarobjeto(objetosecundario);
					// }
					// System.out.println("va");
				}

			}
			if (object!=null) {
				System.out.println("aqui esta pasando analizar2" +object.getClass());
				System.out.println(trabajados.contains(object));

				// si tiene id y el id no es igual al de destino removemos de la
				// lista

				Object iddestino = getIdDestino(object, session2, tx, dd);
				Object idorigen = object.getClass().getMethod("getId", null)
						.invoke(object);
				boolean condicion1 = (iddestino != null);
				boolean condicion2 = false;
				if (iddestino!=null&&idorigen!=null) 
				 condicion2 = !idorigen.equals(iddestino);
				boolean condicion3 = trabajados.contains(object);
				//boolean condicion4 = !Esmaestro(object, dd, tx, session2);
				if (condicion1 && condicion2) {
					if (condicion3) {
				//		JOptionPane.showMessageDialog(null,"borrando "+idorigen+" "+"deberia ser "+iddestino+" "+condicion4+" "+object.getClass() ,"abalizar "+object.getClass(),JOptionPane.WARNING_MESSAGE);
						trabajados.remove(object);
					}
				}
			}
			;
			if (!trabajados.contains(object)) {
				trabajados.add(object);

				Object ids = object.getClass().getMethod("getId", null)
						.invoke(object);
				if (ids != null) {
					// seteariddestino(object, session2);

				/*	if (object.getClass().getName().equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
						System.out
								.println("aqui esta pasando productoranalñizar3");
						System.out.println(Esmaestro(object, dd, tx, session2));
					};*/
					if (!Esmaestro(object, dd, tx, session2)) {
						// setnull(object,dd,tx,session2,syn);

						setnull(object, dd, tx, session2);

						// aqui no va
						// actualizarHijos(object ,object,dd,
						// trabjadosaux ,tx,session2, syn);
					}
				}

			}
		}

	}

	public static boolean Esmaestro(Object object, List<Class> dd,
			Transaction tx, Session session2) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		boolean iguales = true;
		Method metodogetid = object.getClass().getMethod("getId");
		Object id = metodogetid.invoke(object);
		Object object2 = session2.createCriteria(object.getClass())
				.add(Restrictions.eq("id", id)).uniqueResult();
		if (object2 == null) {
			return false;
		}

		// ojo inicializarObjeto(object2, new ArrayList<Object>());
		Method[] metodos = object.getClass().getMethods();
		for (Method method : metodos) {
			int parametros = method.getParameterTypes().length;
			if (method.getReturnType().isPrimitive()
					&& !method.getName().equals("hashCode")&& !method.getName().equals("compareTo")&&parametros==0) {
				// System.out.println(method.getName());
				// System.out.println(method.getReturnType().getName());
				// if (!method.getReturnType().equals(void)){
				
			
				if (!method.getReturnType().getName().equals("void")) {
					if (!method.getName().equals("equals")) {
						System.out.println(method.getName());
						
						
						if (method.invoke(object)
								.equals(method.invoke(object2))) {
							iguales = true;
						} else {
						
							Object a = method.invoke(object);
							Object b = method.invoke(object2);
							if (a.equals(b)) {
								iguales = true;
							} else
								iguales = false;
						
											
							break;
						}
					}
				}
			} else {
				if (dd.contains(method.getReturnType())
						|| method.getReturnType().equals(String.class)) {
					if (method.invoke(object) != null
							&& method.invoke(object2) != null) {
						if (method.invoke(object)
								.equals(method.invoke(object2))) {
							iguales = true;
						} else {
							System.out.println(method.toString());
							if(method.toString().equals("cpc.modelo.ministerio.gestion.UnidadProductiva.getProductor()")){
								
							};
							if (object instanceof UnidadProductiva){
								System.out.println("ojo que es UnidadProductiva" +method.toString());
							}
							if (object instanceof Solicitud){
								System.out.println("ojo que es solicitud" +method.toString());
							}	if (object instanceof SolicitudMecanizado){
								System.out.println("ojo que es solicitudMecanizado" +method.toString());
							}
							Object a = method.invoke(object);
							Class<? extends Object> d = a.getClass();
							Object b = method.invoke(object2);
							if (a.equals(b)) {
								iguales = true;
							} else
								iguales = false;
							if (object instanceof UnidadProductiva){
								System.out.println("ojo que es UnidadProductiva");
							}
							break;
						}

					}
				}

			}
		}
		if (!iguales) {
			System.out.println(object.getClass().getName() + " no es maestro");
		}

		return iguales;
	}

	public static <T> void inicializarObjeto(Object object,
			List<Object> trabajados) throws HibernateException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		try {
			Method[] metodos = object.getClass().getMethods();
			for (Method method : metodos) {
				String dd = "getDirecciones";
				if (!method.getReturnType().getName().equals("void")
						&& method.getName().contains("get")
						&& method.invoke(object) != null
						&& !method.getName().contentEquals(dd)
						&& !trabajados.contains(method.invoke(object))) {
					// System.out.println("dd " + method.invoke(object));
					Hibernate.initialize(method.invoke(object));
					trabajados.add(method.invoke(object));
					inicializarObjeto(method.invoke(object), trabajados);
					if (method.getReturnType().getName()
							.equals("java.util.List")) {
						for (T list_entry : (List<T>) method.invoke(object)) {

							// trabajados.add(list_entry);
							// Hibernate.initialize(method.invoke(object));
							if (!trabajados.contains(list_entry)) {
								trabajados.add(list_entry);
								// Hibernate.initialize(list_entry);
								inicializarObjeto(list_entry, trabajados);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// public static void setnull(Object object,List<Class> dd,Transaction
	// tx,Session session2, List<Sincronizacion> syn) throws
	// IllegalArgumentException, IllegalAccessException,
	// InvocationTargetException, SecurityException, NoSuchMethodException,
	// IOException, ClassNotFoundException, InstantiationException{
	public static void setnull(Object object, List<Class> dd, Transaction tx,
			Session session2) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException, IOException,
			ClassNotFoundException, InstantiationException {
		Object viejo = new Object();
		viejo = object;
		// Hibernate.initialize(object);
		Method metodogetid = object.getClass().getMethod("getId");
		Method[] metodos = object.getClass().getMethods();
		if (object instanceof UbicacionDireccion){
			System.out.println("ojo que es direccion");
			}

		if (object instanceof UnidadProductiva){
			System.out.println("ojo que es UnidadProductiva");
		}
		
		
		if (object instanceof UnidadProductiva){
			System.out.println("ojo que es UnidadProductiva" );
		}
		if (object instanceof Solicitud){
			System.out.println("ojo que es solicitud" );
		}	if (object instanceof SolicitudMecanizado){
			System.out.println("ojo que es solicitudMecanizado" );
		     boolean a =Esmaestro(object, dd, tx, session2);
		     
		}
		
		
		for (Method method : metodos) {
			if (method.getName() == "setId") {
				Class[] h = method.getParameterTypes();
				for (int i = 0; i < h.length; i++) {
					Class class1 = h[i];

					if (class1.equals(Long.class)) {
						// Sincronizacion syncro = new Sincronizacion();
						Long a = (Long) metodogetid.invoke(object);
						// syncro.setIdorigen(serialize(a.intValue()));
						// syncro.setClase(object.getClass().getName());
						// / syncro.setTipo(true);
						// syncro.setTrabajado(true);
						Long id = (Long) getIdDestino(object, session2, tx, dd);
						if (id!=null){
						//	JOptionPane.showMessageDialog(null,"id no es nulo"+ id +" "+object.getClass()  ,"setnull",JOptionPane.WARNING_MESSAGE);
							System.out.println("va a poner id ");
						}
						method.invoke(object, id);
						// syn.add(syncro);
						List<Object> trabjadosaux = new ArrayList<Object>();

						// actualizarHijos(object ,object,dd, trabjadosaux
						// ,tx,session2, syn);
						actualizarHijos(object, object, dd, trabjadosaux, tx,
								session2);
					} else if (class1.equals(Integer.class)) {
						// Sincronizacion syncro = new Sincronizacion();
						// syncro.setIdorigen(serialize(metodogetid.invoke(object)));
						// syncro.setClase(object.getClass().getName());
						// syncro.setTipo(true);
						// syncro.setTrabajado(true);
						Integer id = (Integer) getIdDestino(object, session2, tx, dd);
						if (id!=null){
						//	JOptionPane.showMessageDialog(null,"id no es nulo" + id +" "+object.getClass() ,"setnull",JOptionPane.WARNING_MESSAGE);
							System.out.println("va a poner id ");
						}else {
							Object idorigen =  object.getClass().getMethod("getId", null).invoke( object);
						//	JOptionPane.showMessageDialog(null,"id es nulo" + idorigen +" "+object.getClass() ,"setnull",JOptionPane.WARNING_MESSAGE);
							System.out.println("va a volar id ");
						}
						method.invoke(object, id);
						// syn.add(syncro);
						// Object regresado = deserialize(syncro.getIdorigen());
						List<Object> trabjadosaux = new ArrayList<Object>();
						// actualizarHijos(object ,object,dd, trabjadosaux
						// ,tx,session2, syn);
						actualizarHijos(object, object, dd, trabjadosaux, tx,
								session2);
					} else {
						System.out.println(method);

						Object id = getIdDestino(object, session2, tx, dd);
						if (id == null) {
							method.invoke(object, class1.newInstance());
						} else
							method.invoke(object, id);
						if (id!=null){
				//			JOptionPane.showMessageDialog(null,"id no es nulo" + id +" "+object.getClass() ,"fin analizar",JOptionPane.WARNING_MESSAGE);
							System.out.println("va a poner id ");
						}
						List<Object> trabjadosaux = new ArrayList<Object>();
						// actualizarHijos(object ,object,dd, trabjadosaux
						// ,tx,session2, syn);
						actualizarHijos(object, object, dd, trabjadosaux, tx,
								session2);
					}
					/*
					 * else if (class1.equals(Integer.class)){ Sincronizacion
					 * syncro = new Sincronizacion();
					 * syncro.setIdorigen(serialize
					 * (metodogetid.invoke(object)));
					 * syncro.setClase(object.getClass().getName());
					 * syncro.setTipo(true); syncro.setTrabajado(true); Integer
					 * id =null; method.invoke(object, id); syn.add(syncro);
					 * Object regresado = deserialize(syncro.getIdorigen());
					 * List<Object> trabjadosaux = new ArrayList<Object>();
					 * actualizarHijos(object ,object,dd, trabjadosaux
					 * ,tx,session2, syn); }
					 */

				}

			}
		}
		System.out.println("22");

	}

	// private static <T> void actualizarHijos(Object viejo, Object
	// object,List<Class> dd, List<Object> trabajados,Transaction tx,Session
	// session2, List<Sincronizacion> syn) throws IllegalArgumentException,
	// IllegalAccessException, InvocationTargetException, SecurityException,
	// NoSuchMethodException, IOException, ClassNotFoundException,
	// InstantiationException {
	private static <T> void actualizarHijos(Object viejo, Object object,
			List<Class> dd, List<Object> trabajados, Transaction tx,
			Session session2) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException, IOException,
			ClassNotFoundException, InstantiationException {
		// TODO Auto-generated method stub

		if (object != null && object.getClass().getMethods() != null) {
			if (object.getClass().getName()
					.equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
				System.out
						.println("aqui esta pasando productor actualizarHijos");
			};

			Method[] metodos = object.getClass().getMethods();
			for (Method method : metodos) {
				if (method
						.toString()
						.equals("public void cpc.modelo.ministerio.basico.Telefono.setCliente(cpc.modelo.ministerio.gestion.Cliente)"))
					System.out.println("oso");
				if (method.getReturnType().getName().equals("void")) {
					Class<?> d[] = method.getParameterTypes();
					for (Class<?> class1 : d) {
						String z = class1.getName();
						String zz = viejo.getClass().getName();
						String ddd = viejo.getClass().getSuperclass().getName();
						String dddd = "";
						if (viejo.getClass().getSuperclass().getSuperclass() != null) {
							dddd = viejo.getClass().getSuperclass()
									.getSuperclass().getName();
						}

						if (z.equals(zz) || z.equals(ddd) || z.equals(dddd))
							// method.invoke(object, viejo);
							// setnull(object,dd, tx, session2, syn);
							// seteariddestino(object, session2);
							setnull(object, dd, tx, session2);
						// seteariddestino( object, session2);
					}
				}
			}
			for (Method method : metodos) {
				if (dd.contains(method.getReturnType())) {
					//if (object.getClass().getName().equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
					if (method.invoke(object)!=null) {
						System.out.println("aqui esta pasando actualizar hijos1" +object.getClass());
						System.out.println(trabajados.contains(method.invoke(object)));

						// si tiene id y el id no es igual al de destino removemos de la
						// lista

						Object iddestino = getIdDestino(method.invoke(object), session2, tx, dd);
						Object idorigen = method.invoke(object).getClass().getMethod("getId", null)
								.invoke(method.invoke(object));
						boolean condicion1 = (iddestino != null);
						boolean condicion2 = false;
						if (iddestino!=null&&idorigen!=null) 
						 condicion2 = !idorigen.equals(iddestino);
						boolean condicion3 = trabajados.contains(method.invoke(object));
						//boolean condicion4 = !Esmaestro(method.invoke(object), dd, tx, session2);
						if (condicion1 && condicion2) {
							if (condicion3) {
							//	JOptionPane.showMessageDialog(null,"borrando "+idorigen +"deberia ser "+iddestino+" "+condicion4 ,"actualizarhijos1"+object.getClass(),JOptionPane.WARNING_MESSAGE);
								trabajados.remove(method.invoke(object));
							}
						}
					}

					if (!trabajados.contains(method.invoke(object))) {
						trabajados.add(method.invoke(object));
						// actualizarHijos(viejo,method.invoke(object),dd,trabajados,
						// tx,session2,syn);
						actualizarHijos(viejo, method.invoke(object), dd,
								trabajados, tx, session2);
					}
				}
				if (method.getReturnType().getName().equals("java.util.List")) {
					try {
						Class<?> clases[] = method.getParameterTypes();
						System.out.println(clases.length);
						System.out.println(method.getName());
						System.out.println(method.toString());
						// cpc.modelo.ministerio.gestion.Productor.getDirecciones()
						Integer a = 0;
						Integer b = clases.length;
						boolean c = a.equals(b);
						String d = "getListadoPrecios";
						String v = "getListaPrecios";
						String vv = "getDetalles";
						// String aux =
						// "public java.util.List cpc.modelo.ministerio.gestion.Productor.getDirecciones()";
						String aux2 = "public java.util.List cpc.modelo.ministerio.dimension.UbicacionSector.getDirecciones()";
						String aux3 = "public java.util.List cpc.modelo.ministerio.dimension.UbicacionParroquia.getSectores()";
						// String aux4 =
						// "public java.util.List cpc.modelo.ministerio.gestion.ProductorJuridico.getRepresentantes()";
						String aux4 = "public java.util.List cpc.modelo.ministerio.gestion.Representante.getProductores()";
						if (method
								.toString()
								.equals("public java.util.List cpc.modelo.ministerio.gestion.Cliente.getTelefonos()")) {
							System.out.println("ojo");
						}

						if (!trabajados.contains(method.invoke(object))
								&& !method.getName().equals(d)
								&& !method.getName().equals(v)
								// && !method.toString().equals(aux)
								&& !method.toString().equals(aux2)
								&& !method.toString().equals(aux3)
								&& !method.toString().equals(aux4)) {
							// Hibernate.initialize(object);
							// Object o = method.invoke(object);
							// Hibernate.initialize(o);
							
							//if (object.getClass().getName().equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
							
							trabajados.add(method.invoke(object));
							if (method.invoke(object) != null) {
								for (T list_entry : (List<T>) method
										.invoke(object)) {
									
									if (list_entry!=null) {
										System.out.println("aqui esta pasando actualizad hisjos 2" +object.getClass());
										System.out.println(trabajados.contains(list_entry));

										// si tiene id y el id no es igual al de destino removemos de la
										// lista

										Object iddestino = getIdDestino(list_entry, session2, tx, dd);
										Object idorigen = list_entry.getClass().getMethod("getId", null)
												.invoke(list_entry);
										boolean condicion1 = (iddestino != null);
										boolean condicion2 = false;
										if (iddestino!=null&&idorigen!=null) 
										condicion2 = !idorigen.equals(iddestino);
										boolean condicion3 = trabajados.contains(list_entry);
										//boolean condicion4 = !Esmaestro(list_entry, dd, tx, session2);
										if (condicion1 && condicion2) {
											if (condicion3) {
											//	JOptionPane.showMessageDialog(null,"borrando "+idorigen+"deberia ser "+iddestino+"  "+condicion4 ,"actualizarhijos2"+object.getClass(),JOptionPane.WARNING_MESSAGE);
												trabajados.remove(list_entry);
											}
										}
									}
									
									
									
									
									
									
									
									
									if (!trabajados.contains(list_entry)) {
										trabajados.add(list_entry);
										// actualizarHijos(viejo,list_entry,dd,
										// trabajados,tx,session2, syn);
										actualizarHijos(viejo, list_entry, dd,
												trabajados, tx, session2);
									}
								}
							}
						}

						// aqui
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
		}
	}

	public static List<Class> listaclase() throws ClassNotFoundException {

		List<Class> clases = new ArrayList<Class>();
		// Configuration cfg = new Configuration().configure();
		// Iterator ddddd = cfg.getCollectionMappings();
		// SessionFactory sf = new
		// Configuration().configure().buildSessionFactory();

		Session session = abrirSession();
		Map<String, ClassMetadata> sss = session.getSessionFactory()
				.getAllClassMetadata();
		Collection<ClassMetadata> rr = sss.values();

		for (ClassMetadata classMetadata : rr) {
			EntityMode arg0 = EntityMode.MAP;
			Class p = classMetadata.getMappedClass(arg0);
			String gg = classMetadata.getEntityName();
			boolean ca = gg.endsWith("AUD");
			boolean cc = gg.endsWith("AUD1");
			boolean cb = gg.endsWith("aud");
			if (!ca) {
				if (!cb && !cc) {
					Class ss = Class.forName(gg);
					clases.add(ss);
				}
			}
		}
		Transaction aux = session.beginTransaction();
		aux.commit();
		cerrarSession(session);
		return clases;
	}

	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}

	public static Object deserialize(byte[] bytes) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}

	public static List<Object> listarid(Object object, List<Class> mapeadas)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ClassNotFoundException {
		if (object == null) {
			return null;
		}
		Method[] metodos = object.getClass().getMethods();
		List<Object> ids = new ArrayList<Object>();
		if (object != null) {
			for (Method method : metodos) {
				if (mapeadas.contains(method.getReturnType())) {

					Object hijo = method.invoke(object, null);
					if (hijo != null) {
						Object[] idobjetos = {
								method,
								hijo.getClass(),
								hijo.getClass().getMethod("getId", null)
										.invoke(hijo) };
						ids.add(idobjetos);
					} else {
						// Object[] idobjetos = {method,hijo.getClass(),null };
						// ids.add(idobjetos);
						Object[] idobjetos = { method, method.getReturnType(),
								null };
						ids.add(idobjetos);
					}
				}
			}
			return ids;
		} else
			return null;
	}

	public static <T> List<Object> listaridcolecciones(Object object,
			List<Class> mapeadas) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			ClassNotFoundException {
		if (object == null) {
			return null;
		}
		Method[] metodos = object.getClass().getMethods();
		List<Object> ids = new ArrayList<Object>();
		if (object != null) {
			for (Method method : metodos) {
				Integer poss = 0;
				System.out.println(method);
				try {
					if (method.getReturnType().getName()
							.equals("java.util.List")
							&& method.invoke(object) != null) {
						for (T list_entry : (List<T>) method.invoke(object)) {

							Object hijo = list_entry;
							if (hijo != null) {
								Object[] idobjetos = {
										method,
										poss,
										hijo.getClass(),
										hijo.getClass()
												.getMethod("getId", null)
												.invoke(hijo) };
								ids.add(idobjetos);

							} else {
								Object[] idobjetos = { method, poss,
										hijo.getClass(), null };
								ids.add(idobjetos);

							}
							poss++;

						}

					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}
		return ids;
	}

	public static void seteariddestino(Object obj, Session session2)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, IOException, ClassNotFoundException {
		Object idorigen = obj.getClass().getMethod("getId", null).invoke(obj);
		String name = obj.getClass().getName();

		List<DetalleImportar> detalleImportars = session2
				.createCriteria(DetalleImportar.class)
				.add(Restrictions.eq("idorigen", serialize(idorigen)))
				.add(Restrictions.eq("nameclass", name)).setMaxResults(1)
				.list();
		if (!detalleImportars.isEmpty()) {
			DetalleImportar detalleImportar = detalleImportars.get(0);
			Object iddestino = deserialize(detalleImportar.getIddestino());
			Object idorigens = deserialize(detalleImportar.getIdorigen());
			Method[] metodos = obj.getClass().getMethods();
			for (Method method : metodos) {
				if (method.getName() == "setId") {
					method.invoke(obj, iddestino);
				}
			}
			System.out.println("aqui vamos bien");
		}

	};

	// public static <T> void actualizarobjeto(Object objectOrigen,List<Class>
	// dd, List<Object> trabajados,Transaction tx,Session session2,
	// List<Sincronizacion> syn) throws IllegalArgumentException,
	// IllegalAccessException, InvocationTargetException, SecurityException,
	// NoSuchMethodException, IOException, ClassNotFoundException,
	// InstantiationException{
	public static <T> void actualizarobjeto(Object objectOrigen,
			List<Class> dd, List<Object> trabajados, Transaction tx,
			Session session2) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException, IOException,
			ClassNotFoundException, InstantiationException {
		// obtenemos los metodos
		// obtenemos las ids del objeto a actualizar
		// List<Object> foreaneasids = listarid(objectOrigen,dd);
		// List<Object> foreaneasidscolet =
		// listaridcolecciones(objectOrigen,dd);
		// consultamos la bddestino
		Object id = objectOrigen.getClass().getMethod("getId", null)
				.invoke(objectOrigen);
		System.out.println(id);
		// List<DetalleImportar> detalleImportars =
		// session2.createCriteria(DetalleImportar.class).add(Restrictions.eq("idorigen",
		// serialize(idorigen))).add(Restrictions.eq("nameclass",name))
		// .setMaxResults(1).list()

		// Object objetodestino=
		// session2.createCriteria(objectOrigen.getClass()).add(Restrictions.eq("id",
		// id)).uniqueResult();
		// List<Object> idsdestino = listarid(objetodestino,dd);

		Method[] metodos = objectOrigen.getClass().getMethods();
		for (Method method : metodos) {
			if (dd.contains(method.getReturnType())) {
				seteariddestino(objectOrigen, session2);
			}

			if (method.getReturnType().getName().equals("java.util.List")) {
				try {
					Class<?> clases[] = method.getParameterTypes();
					System.out.println(clases.length);
					System.out.println(method.getName());
					System.out.println(method.toString());
					// cpc.modelo.ministerio.gestion.Productor.getDirecciones()
					Integer a = 0;
					Integer b = clases.length;
					boolean c = a.equals(b);
					String d = "getListadoPrecios";
					String v = "getListaPrecios";
					String vv = "getDetalles";
					String aux = "public java.util.List cpc.modelo.ministerio.gestion.Productor.getDirecciones()";
					String aux2 = "public java.util.List cpc.modelo.ministerio.dimension.UbicacionSector.getDirecciones()";
					String aux3 = "public java.util.List cpc.modelo.ministerio.dimension.UbicacionParroquia.getSectores()";

					if (method
							.toString()
							.equals("public java.util.List cpc.modelo.ministerio.gestion.Cliente.getTelefonos()")) {
						System.out.println("ojo");
					}

					if (!trabajados.contains(method.invoke(objectOrigen))
							&& !method.getName().equals(d)
							&& !method.getName().equals(v)
							&& !method.toString().equals(aux)
							&& !method.toString().equals(aux2)
							&& !method.toString().equals(aux3)) {
						// Hibernate.initialize(object);
						// Object o = method.invoke(object);
						// Hibernate.initialize(o);
						trabajados.add(method.invoke(objectOrigen));
						if (method.invoke(objectOrigen) != null) {
							for (T list_entry : (List<T>) method
									.invoke(objectOrigen)) {
								if (!trabajados.contains(list_entry)) {
									trabajados.add(list_entry);
									// actualizarHijos(viejo,list_entry,dd,
									// trabajados,tx,session2, syn);
									seteariddestino(list_entry, session2);
								}
							}
						}
					}

					// aqui
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		/*
		 * for (int i = 0; i < foreaneasids.size(); i++) { try{ Object object2 =
		 * foreaneasids.get(i); Object[] idobjetos =(Object[]) object2; Method
		 * metodo = (Method) idobjetos[0]; Class clase=(Class) idobjetos[1];
		 * Object idorigen = idobjetos[2]; Method[] metodos =
		 * objectOrigen.getClass().getMethods(); Object auxiliar =
		 * metodo.invoke(objectOrigen); Method[] metodosaux =
		 * auxiliar.getClass().getMethods(); for (Method method : metodosaux) {
		 * if (method.getName()=="setId") { if (!trabajados.contains(auxiliar)){
		 * try { System.out.println(method); Object object3=idsdestino.get(i);
		 * Object[] idobjetosdestino =(Object[]) object3;
		 * method.invoke(auxiliar,idobjetosdestino[2] );
		 * trabajados.add(auxiliar); //actualizarobjeto(auxiliar, dd,
		 * trabajados, tx, session2, syn); actualizarobjeto(auxiliar, dd,
		 * trabajados, tx, session2); }catch (Exception e) {
		 * System.out.println(e); } }} }
		 * 
		 * 
		 * }catch (Exception e) { System.out.println(e); // TODO: handle
		 * exception } }
		 * 
		 * for (int i = 0; i < foreaneasidscolet.size(); i++) { Object object2 =
		 * foreaneasidscolet.get(i); Object[] idobjetos =(Object[]) object2;
		 * Method metodo = (Method) idobjetos[0]; Integer poss = (Integer)
		 * idobjetos[1]; Class clase=(Class) idobjetos[2]; Object idorigen =
		 * idobjetos[3]; Method[] metodos =
		 * objectOrigen.getClass().getMethods(); Object auxiliar =
		 * ((List<Class>) metodo.invoke(objectOrigen)).get(poss); Method[]
		 * metodosaux = auxiliar.getClass().getMethods(); for (Method method :
		 * metodosaux) { if (method.getName()=="setId") { if
		 * (!trabajados.contains(auxiliar)){ try {
		 * 
		 * Object hijo= ((List<Class>) metodo.invoke(objetodestino)).get(poss);
		 * Object idobjetosdestino
		 * =hijo.getClass().getMethod("getId",null).invoke(hijo);
		 * method.invoke(auxiliar,idobjetosdestino ); trabajados.add(auxiliar);
		 * // actualizarobjeto(auxiliar, dd, trabajados, tx, session2, syn);
		 * actualizarobjeto(auxiliar, dd, trabajados, tx, session2); } catch
		 * (Exception e) { Object hijo = auxiliar.getClass().newInstance();
		 * Object idobjetosdestino
		 * =hijo.getClass().getMethod("getId",null).invoke(hijo);
		 * method.invoke(auxiliar, idobjetosdestino); trabajados.add(auxiliar);
		 * // actualizarobjeto(auxiliar, dd, trabajados, tx, session2, syn);
		 * actualizarobjeto(auxiliar, dd, trabajados, tx, session2); // TODO:
		 * handle exception } }} }
		 * 
		 * 
		 * 
		 * }
		 */

		/*
		 * for (Object object2 : foreaneasids) { Object[] idobjetos =(Object[])
		 * object2; Method metodo = (Method) idobjetos[0]; Class clase=(Class)
		 * idobjetos[1]; Object iddestino = idobjetos[2]; Method[] metodos =
		 * objectOrigen.getClass().getMethods(); Object auxiliar =
		 * metodo.invoke(objectOrigen); Method[] metodosaux =
		 * auxiliar.getClass().getMethods(); for (Method method : metodosaux) {
		 * if (method.getName()=="setId") { if (!trabajados.contains(auxiliar)){
		 * method.invoke(auxiliar, iddestino); trabajados.add(auxiliar);
		 * actualizarobjeto(auxiliar, dd, trabajados, tx, session2, syn); }} } }
		 */

		/*
		 * Method[] metodos = objectOrigen.getClass().getMethods();
		 * 
		 * for (Method method2 : metodos) { try { if
		 * (method2.getReturnType().getName
		 * ().equals("java.util.List")&&method2.invoke(objectOrigen)!=null){ for
		 * (T list_entry :(List<T>) method2.invoke(objectOrigen) ) { if
		 * (!trabajados.contains(list_entry)){ trabajados.add(list_entry);
		 * 
		 * actualizarobjeto(list_entry, dd, trabajados, tx, session2, syn); } }
		 * }
		 * 
		 * }catch (Exception e) { System.out.println(e); } }
		 */
	}

	public static Session abrirSession() {

		// if (this.session == null)
		// this.session = openSession();
		// return this.session;
		if (sf == null) {
			cfg = new AnnotationConfiguration().configure();
			System.out.println(1);
			sf = cfg.buildSessionFactory();
			System.out.println(2);
		}
		return sf.openSession();
	}

	public static void cerrarSession(Session session) {

		// if (this.session == null)
		// this.session = openSession();
		// return this.session;
		if (session.isConnected()) {
			session.disconnect();
		}
		if (session.isOpen()) {
			session.close();
		}

	}

	public static Object getIdDestino(Object obj, Session session2, Transaction tx, List<Class> dd)
			throws IOException, ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		if (obj instanceof UbicacionDireccion){
			System.out.println("ojo que es direccion");
		}
		

		if (obj instanceof UnidadProductiva){
			System.out.println("ojo que es UnidadProductiva");
		}
		
		
		if (obj.getClass().getName()
				.equals("cpc.modelo.ministerio.gestion.ProductorNatural")) {
			System.out
					.println("aqui esta pasando productor getIdDestino(Object, Session)");
		};
		
		Object idorigen = obj.getClass().getMethod("getId", null).invoke(obj);
		
		String name = obj.getClass().getName();
		for (DetalleImportar detalleImportar : deImportars) {
			Object iddestino = deserialize(detalleImportar.getIddestino());
			Object idori = deserialize(detalleImportar.getIdorigen());
			String clasenombre = detalleImportar.getNameclass();

			if (idori.equals(idorigen) && clasenombre.equals(name))
				return iddestino;
		}

		List<DetalleImportar> detalleImportars = session2
				.createCriteria(DetalleImportar.class)
				.add(Restrictions.eq("idorigen", serialize(idorigen)))
				.add(Restrictions.eq("nameclass", name)).setMaxResults(1)
				.list();
		if (!detalleImportars.isEmpty()) {
			DetalleImportar detalleImportar = detalleImportars.get(0);
			Object iddestino = deserialize(detalleImportar.getIddestino());
			Object idorigens = deserialize(detalleImportar.getIdorigen());
			return iddestino;
		}

		if (Esmaestro(obj, dd, tx, session2)){return idorigen;}
		return null;
	}
public void AntesDeGuardar(Object object){
	if (object instanceof Productor){
		((Productor)object).setTelefonos(null);
		}
	if (object instanceof ProductorJuridico){
		((ProductorJuridico)object).setRepresentantes(null);
		((ProductorJuridico)object).setTelefonos(null);
	}
		
	if (object instanceof UnidadProductiva){
		((UnidadProductiva)object).setTiposRiego(null);
		}
	if (object instanceof UbicacionDireccion){
		((UbicacionDireccion)object).setLinderos(null);
		((UbicacionDireccion)object).setCoordenadasGeograficas(null);
		((UbicacionDireccion)object).setSuperficies(null);
		}
	if (object instanceof Solicitud){
		((Solicitud)object).setDetalles(null);;
		}
	if (object instanceof DetalleSolicitud){
		((DetalleSolicitud)object).setSolicitado(null);
		}
	
	
}
}
