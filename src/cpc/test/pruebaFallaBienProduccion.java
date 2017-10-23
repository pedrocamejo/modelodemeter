package cpc.test;

import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.demeter.gestion.DetalleMaquinariaOrdenTrabajo;
import cpc.modelo.demeter.gestion.Sustitucion;
import cpc.modelo.demeter.mantenimiento.Falla;
import cpc.modelo.demeter.mantenimiento.Maquinaria;
import cpc.modelo.demeter.mantenimiento.ObjetoMantenimiento;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.negocio.demeter.gestion.NegocioSustitucion;
import cpc.negocio.demeter.mantenimiento.NegocioFallaBienProduccion;
import cpc.negocio.demeter.mantenimiento.NegocioMaquinaria;
import cpc.negocio.demeter.mantenimiento.NegocioObjetoMantenimiento;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaFallaBienProduccion {
	
	public static void main(String[] args) throws ExcFiltroExcepcion {
		NegocioFallaBienProduccion negocio = NegocioFallaBienProduccion.getInstance();
	
		for (Falla falla : negocio.getTodos()) {
			System.out.println(falla.getFechaFalla());
		}		
		
		NegocioObjetoMantenimiento negocio2 = NegocioObjetoMantenimiento.getInstance();
		
		for (ObjetoMantenimiento falla : negocio2.getTodos()) {
			System.out.println(falla.getCodigo());
		}
		
		NegocioMaquinaria negocio3 = NegocioMaquinaria.getInstance();
		for (Maquinaria falla : negocio3.getTodos()) {
			System.out.println(falla.getCodigo());
		}
		
		NegocioSustitucion negocio4 = NegocioSustitucion.getInstance();
		for (Sustitucion sus : negocio4.getTodos()) {
			System.out.println(sus.getMaquinaActual().getCodigoActivo());
		}
		
		
		for (UnidadFuncional uf : NegocioSustitucion.getInstance().obteneUnidadesFuncionales()) {
			System.out.println(uf.getDescripcion());
		}
		Trabajador trab = new PerTrabajador().buscarId(5);
		
		for (DetalleMaquinariaOrdenTrabajo det: NegocioSustitucion.getInstance().obtenerOrdenesPorOperador(trab)){
			System.out.println(det.getOrdenTrabajo().getNroControl()+ " Operador : "+det.getOperador().getNombreCompleto());
		}
		
		
	}

}
