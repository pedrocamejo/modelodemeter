package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import cpc.modelo.demeter.mantenimiento.ArticuloAlmacenCantidad;
import cpc.modelo.demeter.mantenimiento.PlantaDistribuidora;
import cpc.modelo.demeter.mantenimiento.TipoGarantia;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerArticuloAlmacenCantidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerPlanMantenimiento;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerPlantaDistribuidora;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoGarantia;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioPlantaDistribuidora {
	
	
	private static NegocioPlantaDistribuidora negocio;
	private PerPlantaDistribuidora   perPlantaDistribuidora;
	private PerModelo				 perModelo;
	private PerTipoGarantia			 perTipoGarantia;
	
	
	private NegocioPlantaDistribuidora() {
		perPlantaDistribuidora=  new PerPlantaDistribuidora();
		perModelo = new PerModelo();
		perTipoGarantia = new PerTipoGarantia();
 
	}

	public static NegocioPlantaDistribuidora getInstance() {
		if (negocio == null)
			negocio = new NegocioPlantaDistribuidora();
		return negocio;
	}

	public PerPlantaDistribuidora getPerPlantaDistribuidora() {
		return perPlantaDistribuidora;
	}

	public void setPerPlantaDistribuidora(PerPlantaDistribuidora perPlantaDistribuidora) {
		this.perPlantaDistribuidora = perPlantaDistribuidora;
	}

	public List<PlantaDistribuidora> getPlantas() throws ExcFiltroExcepcion
	{
		
		return perPlantaDistribuidora.getAll();
	}
	
	public void Guardar(PlantaDistribuidora planta) throws Exception
	{
		perPlantaDistribuidora.guardar(planta,planta.getId());
	}
	
	public List<Modelo> getmodelos() throws ExcFiltroExcepcion
	{
		return perModelo.getAll();
	}
	
	public List<TipoGarantia> gettiposGarantia() throws ExcFiltroExcepcion
	{
		return perTipoGarantia.getAll();
	}

	public TipoGarantia getTipoGarantia(Modelo modelo)
	{
		return perTipoGarantia.get(modelo);
	}
	
	
	public List<Modelo>  getmodelos_TipoGarantia()
	{
		return perTipoGarantia.getModelos();
	}
	
	
}
