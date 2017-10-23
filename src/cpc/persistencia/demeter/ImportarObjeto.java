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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;

import org.zkoss.zkplus.spring.SpringUtil;
 

import cpc.modelo.demeter.basico.FuncionTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.sincronizacion.DetalleExport;
import cpc.modelo.demeter.sincronizacion.DetalleImportar;
import cpc.modelo.demeter.sincronizacion.DetalleImportarError;
import cpc.modelo.demeter.sincronizacion.Exportar;
import cpc.modelo.demeter.sincronizacion.Importar;
import cpc.modelo.ministerio.gestion.Productor;
import sun.nio.cs.ext.ISCII91;

public class ImportarObjeto {
	// public static void analizarobjeto(Object object,List<Class> dd,
	// List<Object> trabajados,Transaction tx,Session session2,
	// List<Sincronizacion> syn) throws IllegalArgumentException,
	// IllegalAccessException, InvocationTargetException, SecurityException,
	// NoSuchMethodException, IOException, ClassNotFoundException,
	// InstantiationException{
	public static void analizarobjeto(Object object, List<Class> dd,
			List<Object> trabajados, Transaction tx, Session session2)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException,
			NoSuchMethodException, IOException, ClassNotFoundException,
			InstantiationException {
		// obtenemos los metodos
		if (object != null) {
			Method[] metodos = object.getClass().getMethods();
			for (Method metodo : metodos) {
				if (metodo.getName().equals("getId")) {
					// averiguamos si el id esta usado
					// si esta usado lo ponemos null
					// sino hay que hacer una replicacion
					if (!trabajados.contains(object)) {
						trabajados.add(object);
						Object ids = metodo.invoke(object);
						if (ids != null) {
							if (!Esmaestro(object, dd, tx, session2)) {
								// setnull(object,dd,tx,session2,syn);
								setnull(object, dd, tx, session2);

								// aqui no va
								// actualizarHijos(object ,object,dd,
								// trabjadosaux ,tx,session2, syn);
							}
						}

					}
				} else {
					if (metodo.getReturnType().isPrimitive()) {
					}

					if (dd.contains(metodo.getReturnType())) {
						if (!trabajados.contains(metodo.invoke(object))) {
							trabajados.add(metodo.invoke(object));
							// analizarobjeto(metodo.invoke(object),dd,trabajados,
							// tx,session2,syn);
							analizarobjeto(metodo.invoke(object), dd,
									trabajados, tx, session2);
							seteariddestino(object, session2);
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
			if (method.getReturnType().isPrimitive()
					&& !method.getName().equals("hashCode")) {
				// System.out.println(method.getName());
				// System.out.println(method.getReturnType().getName());
				// if (!method.getReturnType().equals(void)){
				if (!method.getReturnType().getName().equals("void")) {
					if (!method.getName().equals("equals")) {
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
							Object a = method.invoke(object);
							Class<? extends Object> d = a.getClass();
							Object b = method.invoke(object2);
							if (a.equals(b)) {
								iguales = true;
							} else
								iguales = false;
							break;
						}

					}
				}

			}
		}
		if (!iguales) {
			System.out.println(object.getClass().getName() + "no es maestro");
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
					System.out.println("dd " + method.invoke(object));
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
						Long id = null;
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
						Integer id = null;
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
						method.invoke(object, class1.newInstance());
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
							setnull(object, dd, tx, session2);
					}
				}
				if (dd.contains(method.getReturnType())) {
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
						String aux = "public java.util.List cpc.modelo.ministerio.gestion.Productor.getDirecciones()";
						String aux2 = "public java.util.List cpc.modelo.ministerio.dimension.UbicacionSector.getDirecciones()";
						String aux3 = "public java.util.List cpc.modelo.ministerio.dimension.UbicacionParroquia.getSectores()";
						String aux4 = "public java.util.List cpc.modelo.ministerio.gestion.ProductorJuridico.getRepresentantes()";

						if (method
								.toString()
								.equals("public java.util.List cpc.modelo.ministerio.gestion.Cliente.getTelefonos()")) {
							System.out.println("ojo");
						}

						if (!trabajados.contains(method.invoke(object))
								&& !method.getName().equals(d)
								&& !method.getName().equals(v)
								&& !method.toString().equals(aux)
								&& !method.toString().equals(aux2)
								&& !method.toString().equals(aux3)
								&& !method.toString().equals(aux4)) {
							// Hibernate.initialize(object);
							// Object o = method.invoke(object);
							// Hibernate.initialize(o);
							trabajados.add(method.invoke(object));
							if (method.invoke(object) != null) {
								for (T list_entry : (List<T>) method
										.invoke(object)) {
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
		Configuration cfg = new Configuration().configure();
		Iterator ddddd = cfg.getCollectionMappings();
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();

		Session session = sf.openSession();
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

	public static <T> void main(String[] args) throws SQLException, Exception {
		/*
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beansConfiguracion.xml");
		BeanFactory factory = (BeanFactory) context;
		String sede = (String) factory.getBean("idsede");
		TransactionSynchronizationManager.bindResource("obj", new AuditorUsuario());
		List<DetalleImportar> deImportars = new ArrayList<DetalleImportar>();
		List<DetalleImportarError> deImportarsError = new ArrayList<DetalleImportarError>();
		List<Class> dd = listaclase();

		Configuration cfg2 = new Configuration().configure();
		SessionFactory sf2 = cfg2.buildSessionFactory();

		// leemos el archivo con una lista de objetos de sincronizacion
		try {
			File entrada = new File("/home/erivas/OBJETO");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					entrada));
			Exportar exportar = (Exportar) ois.readObject();
			List<DetalleExport> sincroorigen = exportar.getDetalles();
			for (DetalleExport sincronizacion : sincroorigen) {
				Object guarda = deserialize(sincronizacion.getObjeto());
				if (sincronizacion.isIsupdate() == false) {
					org.hibernate.classic.Session session4 = sf2.openSession();
					Transaction tx2 = session4.beginTransaction();
					try {

						analizarobjeto(guarda, dd, new ArrayList<Object>(),
								tx2, session4);
						session4.saveOrUpdate(guarda);
						tx2.commit();
						System.out.println("ok");
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
						deImportars.add(detalleImportar);

						session4.evict(guarda);
					} catch (Exception e) {
						System.out.println(e);
						tx2.rollback();
						DetalleImportarError error = new DetalleImportarError();
						error.setExcepcion(serialize(e));
						error.setIdorigen(serialize(guarda.getClass()
								.getMethod("getId", null).invoke(guarda)));
						error.setIsupdate(sincronizacion.isIsupdate());
						error.setNameclass(guarda.getClass().getName());
						error.setObjeto(serialize(guarda));
						deImportarsError.add(error);

					}

					session4.close();
				}
				if (sincronizacion.isIsupdate() == true) {
					org.hibernate.classic.Session session5 = sf2.openSession();
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
						deImportars.add(detalleImportar);
						session5.evict(guarda);

					} catch (Exception e) {
						tx3.rollback();
						System.out.println("2" + e + guarda.toString());
						DetalleImportarError error = new DetalleImportarError();
						error.setExcepcion(serialize(e));
						error.setIdorigen(serialize(guarda.getClass()
								.getMethod("getId", null).invoke(guarda)));
						error.setIsupdate(sincronizacion.isIsupdate());
						error.setNameclass(guarda.getClass().getName());
						error.setObjeto(serialize(guarda));
						deImportarsError.add(error);
					}
					session5.close();
				}
				System.out.println("guardado sincro");
				System.out.println("guardado");
				System.out.println();
			}
			org.hibernate.classic.Session session3 = sf2.openSession();
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
			for (DetalleImportar detalleImportar : deImportars) {

				session3.save(detalleImportar);

			}
			File salida = new File("/home/erivas/OBJETO2");
			ObjectOutputStream o;
			o = new ObjectOutputStream(new FileOutputStream(salida));
			o.writeObject(sincroorigen);
			o.close();

			tx1.commit();

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
		*/
	}

	/*
	 * interesante private static final ThreadLocal<Session> session = new
	 * ThreadLocal<Session>();
	 * 
	 * public static void closeSession() throws HibernateException { Session s =
	 * session.get(); if (s != null) { s.close(); session.remove(); } }
	 */

}
