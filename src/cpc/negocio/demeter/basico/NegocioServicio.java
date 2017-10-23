package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.TipoUnidadMedida;

import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.basico.PerTipoUnidadMedida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioServicio extends NegocioGenerico<Servicio, PerServicio, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioServicio 				negocio;
	
	private NegocioServicio(){
		setPersistencia(new PerServicio());
	}
	
	public  static synchronized NegocioServicio getInstance() {
		if (negocio == null)
			negocio = new NegocioServicio();
		return negocio;
	}

	public void setServicio(Servicio servicio) throws ExcFiltroExcepcion{
		super.setModelo(getPersistencia().getDato(servicio));
	}
	
	public List<TipoServicio> getTipos() throws ExcFiltroExcepcion{
		return new PerTipoServicio().getAll(); 
	}

	public List<TipoUnidadMedida> getTiposUnidades() throws ExcFiltroExcepcion{
		return new PerTipoUnidadMedida().getAll(); 
	}
	
	public List<TipoUnidadMedida> getTiposUnidadesSimples() throws ExcFiltroExcepcion{
		return new PerTipoUnidadMedida().getAllSimples(); 
	}
}
