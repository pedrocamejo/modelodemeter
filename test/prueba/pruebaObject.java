package prueba;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cpc.persistencia.demeter.PerSedeDemeter;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoContrato;
import cpc.persistencia.ministerio.basico.PerEstado;

public class pruebaObject {
	public static void main(String[] args) throws Exception {
		Object objetoprueba = new PerEstadoContrato().getCulminado();
		Method[] metodos = objetoprueba.getClass().getMethods();
		for (Method method : metodos) {
		Annotation[] q = method.getAnnotations();
		Annotation[] w = method.getDeclaredAnnotations();
		Class<?> e = method.getDeclaringClass();
		int r = method.getModifiers();
		System.out.println(method);
		}
		Field[] campos = objetoprueba.getClass().getFields();
		for (Field field : campos) {
		System.out.println(field.getName());
			}
		
		Field[] campos2 = objetoprueba.getClass().getDeclaredFields();
		for (Field field : campos2) {
		System.out.println(field.getName());
			}
	}
}
