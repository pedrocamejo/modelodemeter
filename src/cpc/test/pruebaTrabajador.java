package cpc.test;

import java.util.List;

import cpc.modelo.demeter.basico.FuncionTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.negocio.demeter.basico.NegocioTrabajador;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaTrabajador {
	
	public static void main(String[] args) throws ExcFiltroExcepcion {
		NegocioTrabajador negocio = NegocioTrabajador.getInstance();
		List<Trabajador> trabajadores = negocio.getTodos();
		for (Trabajador trabajador : trabajadores) {
			System.out.println(trabajador.getNombre());
			List<FuncionTrabajador> funcio = trabajador.getFunciones();
			for (FuncionTrabajador funcionTrabajador : funcio) {
				for (FuncionTrabajador funcionTrabajadord : funcio) {
					System.out.println(funcionTrabajadord);
				}
			}
		}
		System.out.println("----------------------------------------------");
	/*	trabajadores = negocio.getOperadores();
		for (Trabajador trabajador : trabajadores) {
			System.out.println(trabajador.getNombre());
		}*/
		/*
		trabajador.setNroCedula("V-223289626");
		trabajador.setApellido("MAntilla");
		trabajador.setNombre("reynaldo");
		trabajador.setTipoTrabajador(tipo);
		trabajador.setTlfFijo("0251235698");
		negocio.setModelo(trabajador);
		negocio.guardar(trabajador.getId());*/
		System.out.println("fin");
	}

}
