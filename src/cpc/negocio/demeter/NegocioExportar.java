package cpc.negocio.demeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.sincronizacion.Exportar;
import cpc.modelo.demeter.sincronizacion.SedeDemeter;
import cpc.persistencia.demeter.PerExportar;
import cpc.persistencia.demeter.PerSedeDemeter;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioExportar  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private PerExportar perExportar;
	private PerSedeDemeter perSede;
	
	private static NegocioExportar negocio;
 	
	
	public NegocioExportar( ) 
	{
		super();
		this.perExportar = new PerExportar();
		this.perSede = new PerSedeDemeter();
		
		
	}
	
	public  static synchronized NegocioExportar getInstance() {
		if (negocio == null)
			negocio = new NegocioExportar( );
		return negocio;
	}
	
	public PerExportar getPerExportar() 
	{
		return perExportar;
	}
	
	public void setPerExportar(PerExportar exportar) 
	{
		this.perExportar = exportar;
	}
	
	public List<Exportar>  getExportaciones() throws ExcFiltroExcepcion
	{
		return perExportar.getAll();
	}
	
	
	public SedeDemeter getSede(String id)
	{
		return perSede.buscarId(id);
	}
	
	public SedeDemeter getSedeActual() throws ExcFiltroExcepcion
	{
		return perSede.getAll().get(0);
	}
	
	public void ActualizarSede (SedeDemeter sedeDemeter ) throws Exception
	{  List <SedeDemeter> sedesExtra = perSede.getsedesdistintas(sedeDemeter);
	for (SedeDemeter sedeextra : sedesExtra) {
		perSede.borrar(sedeextra);
	}
		
		perSede.guardaroupdate(sedeDemeter, sedeDemeter.getIdSede());

	}
	
	public Exportar CrearExportar(String cedula, String md5) throws ExcFiltroExcepcion{
		return perExportar.CrearExportar(cedula, md5);
	}
	public File CrearArchivoExportar(Exportar expor,String ruta) throws ExcFiltroExcepcion, FileNotFoundException, IOException{
		 return perExportar.CrearArchivo(expor, ruta);}
}
