package cpc.negocio.ministerio.basico;
import java.util.List;

import cpc.modelo.demeter.gestion.ControlUnidadFuncional;
import cpc.modelo.ministerio.dimension.TipoUnidadFuncional;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UnidadFuncional;

import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.gestion.PerControlUnidadFuncional;
import cpc.persistencia.ministerio.basico.PerDireccion;
import cpc.persistencia.ministerio.basico.PerTipoUnidadFuncional;
import cpc.persistencia.ministerio.basico.PerUnidadFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioUnidadFuncional extends NegocioGenerico<UnidadFuncional, PerUnidadFuncional, Integer>{

	
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioUnidadFuncional 				negocio;
	
	private NegocioUnidadFuncional(){
		setPersistencia(new PerUnidadFuncional());
	}
	
	public  static synchronized NegocioUnidadFuncional getInstance() {
		if (negocio == null)
			negocio = new NegocioUnidadFuncional();
		return negocio;
	}
	
	public void guardar(String serie) throws Exception{
		getPersistencia().guardar(getModelo(), getModelo().getId(), serie);
	}
	

	
	public String getSerie() throws ExcFiltroExcepcion{
		ControlUnidadFuncional dato = new PerControlUnidadFuncional().getControl(getModelo());
		return dato.getSerie();
	}
	
	public List<TipoUnidadFuncional> getTiposUnidadesFuncionales() throws ExcFiltroExcepcion{
		return new PerTipoUnidadFuncional().getAll();
	}
	
	public List<UbicacionDireccion> getUbicacionesFisicas() throws ExcFiltroExcepcion{
		return new PerDireccion().getAll();
	}
	
}
