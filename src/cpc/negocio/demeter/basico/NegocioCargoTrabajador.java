package cpc.negocio.demeter.basico;

import java.util.List;

import cpc.modelo.demeter.basico.CargoTrabajador;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerCargoTrabajador;
import cpc.persistencia.demeter.implementacion.basico.PerTrabajador;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioCargoTrabajador extends NegocioGenerico<Trabajador, PerTrabajador, Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioCargoTrabajador 				negocio;
	
	private NegocioCargoTrabajador(){
		setPersistencia(new PerTrabajador());
	}
	
	public  static synchronized NegocioCargoTrabajador getInstance() {
		if (negocio == null)
			negocio = new NegocioCargoTrabajador();
		return negocio;
	}
	
	public void setTrabajo(Trabajador trabajador){
		trabajador.setFunciones(getPersistencia().getDetalle(trabajador));
		setModelo(trabajador);
	}
	
	public List<CargoTrabajador> getCargosTrabajadores() throws ExcFiltroExcepcion{
		return  new PerCargoTrabajador().getAll(); 
	}

	
}
