package cpc.negocio.ministerio.basico;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import cpc.modelo.demeter.administrativo.CotizacionTransporte;
import cpc.modelo.demeter.transporte.UbicacionTransporte;
import cpc.modelo.ministerio.dimension.UbicacionMunicipio;
import cpc.modelo.ministerio.dimension.UbicacionParroquia;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.negocio.demeter.administrativo.NegocioCotizacionTransporte;
import cpc.persistencia.demeter.implementacion.administrativo.PerCotizacionTransporte;
import cpc.persistencia.demeter.implementacion.administrativo.PerTipoContrato;
import cpc.persistencia.demeter.implementacion.transporte.PerUbicacionTransporte;
import cpc.persistencia.ministerio.basico.PerMunicipio;
import cpc.persistencia.ministerio.basico.PerParroquia;
import cpc.persistencia.ministerio.basico.PerSector;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioUbicacionTransporte implements Serializable {
	private static NegocioUbicacionTransporte 	negocio;	
	private PerUbicacionTransporte			persistencia;
	private UbicacionTransporte  			ubicacionTransporte;
	
	
	private NegocioUbicacionTransporte()  {		
		persistencia				= new PerUbicacionTransporte();
	}

	public  static synchronized  NegocioUbicacionTransporte getInstance() {
		if (negocio == null)
			negocio = new NegocioUbicacionTransporte();
		return negocio;
	}
	

	public List<UbicacionMunicipio> getUbicacionesMunicipios() throws ExcFiltroExcepcion{
	   return new PerMunicipio().getcritaeriaAll();
	}
	
	public List<UbicacionParroquia> getUbicacionesParroquias() throws ExcFiltroExcepcion{
		return new PerParroquia().getcritaeriaAll();
	}
	public List<UbicacionParroquia> getUbicacionesParroquias(UbicacionMunicipio municipio) throws ExcFiltroExcepcion{
		return new PerParroquia().getTodos(municipio);
	}
	
	public List<UbicacionSector> getUbicacionesSectores(UbicacionParroquia parroquia) throws ExcFiltroExcepcion{
		return new PerSector().getTodos(parroquia);
	}
	public List<UbicacionSector> getUbicacionesSectores() throws ExcFiltroExcepcion{
		return new PerSector().getcritaeriaAll();
	}
	
	public void guardar(UbicacionTransporte ubicacionTransporte )  throws Exception, SQLException{		
		
	
		persistencia.guardar(ubicacionTransporte, ubicacionTransporte.getId());;
	}
}
