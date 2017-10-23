package cpc.negocio.demeter.mantenimiento;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sun.security.provider.certpath.CertId;
import util.Seguridad;

import cpc.modelo.demeter.mantenimiento.CertificadoOrigen;
import cpc.modelo.demeter.mantenimiento.MaquinariaExterna;
import cpc.modelo.demeter.mantenimiento.PlantaDistribuidora; 
import cpc.modelo.demeter.mantenimiento.TipoGarantia;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerCertificadoOrigen;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMaquinariaExterna;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerPlantaDistribuidora;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoGarantia;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;
import cva.pc.demeter.utilidades.Formateador;

 

public class NegocioCertificadoOrigen {
	
	private static  NegocioCertificadoOrigen negocio;
	private PerPlantaDistribuidora perPlantaDistribuidora;
	private PerMaquinariaExterna   perMaquinariaExterna;
	private PerCertificadoOrigen   perCertificado;
	private PerTipoGarantia			perTipoGarantia;
	
	
	
	private    NegocioCertificadoOrigen () {
		perPlantaDistribuidora = new PerPlantaDistribuidora();
		perMaquinariaExterna = new PerMaquinariaExterna();
		perCertificado = new PerCertificadoOrigen();
		perTipoGarantia = new PerTipoGarantia();
		
	}

	public static   NegocioCertificadoOrigen getInstance() {
		if (negocio == null)
			negocio = new   NegocioCertificadoOrigen();
		return negocio;

	}
 
	
	public void Guardar(CertificadoOrigen certificado) throws Exception
	{
		
		if(certificado.getNroControl() ==null) // id Nueva 
		{
			//modelo de la maquinaria
			String codigoserie = String.format("%03d",certificado.getMaquinariaExterna().getTipo().getSerie().getId());
			//Numero  de la maquinaria vendida 
			Long numeroProx = perCertificado.contarCertificado(certificado.getMaquinariaExterna().getTipo().getModelo());
			numeroProx = (numeroProx==null? 1:numeroProx +1);
			//ano de la maquinaria 
			Date date =  new Date();
			String codigo = codigoserie.trim() + numeroProx.toString() +(new SimpleDateFormat("yyyy").format(date)).trim();
			String codigoSeguridad = Seguridad.getMD5((certificado.getMaquinariaExterna().getSerialcarroceria()+codigo).trim());
			certificado.setFechaGeneracion(date); // fecha de la generacion del certificado 
			certificado.setNroControl(codigo.trim());
			certificado.setStatus(0);
			certificado.setNroSeguridad(codigoSeguridad.trim());
			perCertificado.guardar(certificado,null);
		}
		else
		{
			perCertificado.guardar(certificado, certificado.getNroControl()); // este metodo guardar me actualizar jajaja el nro control no es null XD 
		}
	}
	
	public List<CertificadoOrigen> getCertificados() throws ExcFiltroExcepcion
	{
		return perCertificado.getAll();
	}
	
	public List<MaquinariaExterna> getMaquinariasVendidas()
	{
		return perMaquinariaExterna.getMaquinariaConGarantia();
	}
	
	public List<MaquinariaExterna> getMaquinariaVendidaSinCertificado()
	{
		return perMaquinariaExterna.getMaquinariaSinCertificado();
		
	}
	
	public List<PlantaDistribuidora> getPlantas() throws ExcFiltroExcepcion
	{
		return perPlantaDistribuidora.getAll();
	} 
	public List<TipoGarantia> getGarantias() throws ExcFiltroExcepcion
	{
		return perTipoGarantia.getAll();
	}
	
	public List<PlantaDistribuidora> getplantas(Modelo modelo)
	{
		return   perPlantaDistribuidora.getPlantas(modelo);
	}
	
	public void procesar(CertificadoOrigen certificado) throws Exception
	{
		certificado.setStatus(1);
		perCertificado.guardar(certificado,certificado.getNroControl());
	}

	
}
