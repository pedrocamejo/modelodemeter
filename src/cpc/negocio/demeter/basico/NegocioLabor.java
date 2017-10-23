package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Servicio;
import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.basico.UsoPreciosProducto;
import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerIva;
import cpc.persistencia.demeter.implementacion.PerTipoServicio;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.basico.PerLabor;
import cpc.persistencia.demeter.implementacion.basico.PerServicio;
import cpc.persistencia.demeter.implementacion.basico.PerTipoProducto;
import cpc.persistencia.ministerio.basico.PerTipoProductor;
import cva.pc.demeter.excepciones.ExcAccesoInvalido;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioLabor extends NegocioGenerico<Labor, PerLabor, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioLabor 				negocio;
	
	private NegocioLabor(){
		setPersistencia(new PerLabor());
	}
	
	public  static synchronized NegocioLabor getInstance() {
		if (negocio == null)
			negocio = new NegocioLabor();
		return negocio;
	}

	public void nuevo() throws ExcFiltroExcepcion{
		setModelo(new Labor()); 
		getModelo().setTipoProducto(new PerTipoProducto().buscarId(2)); 
		getModelo().setActivo(true);
	}
	
	public void setLabor(Labor labor){
		super.setModelo((Labor)getPersistencia().getDato(labor));
	}
	
	public List<Labor> enriqueserDatos(List<Labor> labores) throws ExcAccesoInvalido{
		return getPersistencia().enriqueserDatos(labores);
	}
	
	public List<UsoPreciosProducto> getPreciosDetallados(List<Labor> labores) throws ExcAccesoInvalido{
		return getPersistencia().getPreciosDetallados(labores);
	}
	
	public List<TipoServicio> getTipos() throws ExcFiltroExcepcion{
		return new PerTipoServicio().getAll(); 
	}

	public List<Servicio> getServicios() throws ExcFiltroExcepcion{
		return new PerServicio().getAll(); 
	}
	
	public List<TipoProductor> getTiposProductores() throws ExcFiltroExcepcion{
		return new PerTipoProductor().getAll(); 
	}
	
	public List<UnidadMedida> getUnidadesMedidas() throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(); 
	}
	
	public List<UnidadMedida> getUnidadMedida(Servicio servicio) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(servicio.getTipoUnidadMedida()); 
	}
	
	public List<UnidadMedida> getUnidadMedida(UnidadMedida unidad) throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll(unidad); 
	}

	public List<Impuesto> getImpuestos() throws ExcFiltroExcepcion{
		return new PerIva().getAll(); 
	}
	
	public List<UnidadMedida> getUnidadesCobro() throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAllSimples(); 
	}
	public List<Labor> getTodosActivos(){
		return new PerLabor().getLaboresActivas();
	}
	
	public List<Labor> getTodosInactivos(){
		return new PerLabor().getLaboresInactivas();
	}
}
