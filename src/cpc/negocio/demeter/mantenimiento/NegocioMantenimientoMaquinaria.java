package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List; 

import cpc.modelo.demeter.mantenimiento.Actividad;
import cpc.modelo.demeter.mantenimiento.MantenimientoMaquinaria;
import cpc.modelo.demeter.mantenimiento.Periodicidad;
import cpc.modelo.demeter.mantenimiento.PlanMantenimiento;
import cpc.modelo.demeter.mantenimiento.TipoGarantia;
import cpc.modelo.sigesp.basico.Marca; 
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerActividad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMantenimientoMaquinaria;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerPeriodicidad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerPlanMantenimiento;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoGarantia;
import cpc.persistencia.sigesp.implementacion.PerMarca;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMantenimientoMaquinaria  implements Serializable {
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioMantenimientoMaquinaria 	negocio;
	
	private PerMarca 					permarca;
	private PerPlanMantenimiento 		perPlanMantenimiento;
	private PerMantenimientoMaquinaria	perMantenimientoMaquinaria;
	private PerTipoGarantia				perTipoGarantia;
	private PerActividad				perActividad;
	private PerPeriodicidad				perPeriodicidad;
	
	
	private List<Marca> 			marcas; 
	
    public NegocioMantenimientoMaquinaria() {
		// TODO Auto-generated constructor stub
    	permarca = new PerMarca();
    	perPlanMantenimiento = new PerPlanMantenimiento();
    	perMantenimientoMaquinaria = new PerMantenimientoMaquinaria();
    	perTipoGarantia = new PerTipoGarantia();
    	perActividad = new PerActividad();
    	perPeriodicidad = new PerPeriodicidad();
    }
	
    
    
    public TipoGarantia getTipoGarantia(Modelo modelo)
    {
    	return perTipoGarantia.get(modelo);
    }
    
    
	public  static synchronized NegocioMantenimientoMaquinaria getInstance() {
		if (negocio == null)
			negocio = new NegocioMantenimientoMaquinaria();
		return negocio;
	}
 
	public List<PlanMantenimiento> getPlanMantenimiento() throws ExcFiltroExcepcion
	{
		return perPlanMantenimiento.getAll();
	}
	 
	public List<Marca> getMarcas()
	{
		try {
			 marcas =  permarca.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return marcas;
	}
	public List<MantenimientoMaquinaria> getMantenimientoMaquinaria() throws ExcFiltroExcepcion
	{	
		return perMantenimientoMaquinaria.getAll();
	}

	public List<Modelo> modeloConTipoGarantia()
	{
		return  perMantenimientoMaquinaria.getmodeloConTipoGarantia();
	}
	
	
	public void Guardar(MantenimientoMaquinaria mantenimientoMaquinaria) throws Exception
	{
		// guardar toda las Periodicidad Nueva
		perMantenimientoMaquinaria.guardar(mantenimientoMaquinaria,mantenimientoMaquinaria.getId());
	}
	
	public void Guardar(Periodicidad p) throws Exception
	{
		perPeriodicidad.guardar(p,p.getId());
	}
	
	
	public void Eliminar(MantenimientoMaquinaria mantenimientoMaquinaria) throws Exception
	{
		perMantenimientoMaquinaria.borrar(mantenimientoMaquinaria);
	}
	
	public void borrarActividad(Actividad acividad) throws Exception
	{
		perActividad.borrar(acividad);
	}
	public void borrarActividad(List<Actividad> acividad) throws Exception
	{ 
		for(Actividad p : acividad)
		{
			perActividad.borrar(p);
		}
	}
	
	public void borrarPeriodicidad(Periodicidad periodicidad) throws Exception
	{
		perPeriodicidad.borrar(periodicidad);
	}
	public void borrarPeriodicidad(List<Periodicidad> periodicidad) throws Exception
	{ 
		for(Periodicidad p : periodicidad)
		{
			perPeriodicidad.borrar(p);
		}
	}
	
} 