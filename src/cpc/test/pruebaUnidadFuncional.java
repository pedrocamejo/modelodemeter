package cpc.test;

import java.util.List;

import cpc.modelo.ministerio.dimension.TipoUnidadFuncional;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.negocio.ministerio.basico.NegocioUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaUnidadFuncional {

	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		NegocioUnidadFuncional negocio = NegocioUnidadFuncional.getInstance();
		List<TipoUnidadFuncional> tipoUf = negocio.getTiposUnidadesFuncionales();
		List<UnidadFuncional> Uf = negocio.getTodos();
		for (UnidadFuncional unidadFuncional : Uf) {
			System.out.println(unidadFuncional.getDescripcion());
		}
		for (TipoUnidadFuncional unidadFuncional : tipoUf) {
			System.out.println(unidadFuncional.getDescripcion());
		}
	}
}