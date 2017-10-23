package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.CargoTrabajador;
import cpc.modelo.demeter.basico.FuncionTrabajador;
import cpc.modelo.demeter.basico.TipoTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerCargoTrabajador;
import cpc.persistencia.demeter.implementacion.PerFuncionTrabajador;
import cpc.persistencia.demeter.implementacion.PerTipoTrabajador;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioTrabajador extends NegocioGenerico<Trabajador, PerTrabajador, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioTrabajador 				negocio;
	
	private NegocioTrabajador(){
		setPersistencia(new PerTrabajador());
	}
	
	public  static synchronized NegocioTrabajador getInstance() {
		if (negocio == null)
			negocio = new NegocioTrabajador();
		return negocio;
	}

	public void setTrabajo(Trabajador trabajador){
		if (trabajador != null)
			trabajador.setFunciones(getPersistencia().getDetalle(trabajador));
		setModelo(trabajador);
	}
	
	public List<FuncionTrabajador> getFuncionesTrabajadores() throws ExcFiltroExcepcion{
		return  new PerFuncionTrabajador().getAll(); 
	}
	
	public List<CargoTrabajador> getCargosTrabajadores() throws ExcFiltroExcepcion{
		return  new PerCargoTrabajador().getAll(); 
	}
	
	public List<TipoTrabajador> getTiposTrabajadores() throws ExcFiltroExcepcion{
		return  new PerTipoTrabajador().getAll(); 
	}

	public List<Trabajador> getOperadores() throws ExcFiltroExcepcion{
		return  new PerTrabajador().getOperadores(); 
	}
	
	public List<Trabajador> getConductor() throws ExcFiltroExcepcion{
		return  new PerTrabajador().getConductores(); 
	}
}
