package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
 
import cpc.modelo.demeter.mantenimiento.Actividad;
import cpc.modelo.demeter.mantenimiento.DetalleGarantina;
import cpc.modelo.demeter.mantenimiento.Lote;
import cpc.modelo.demeter.mantenimiento.MaquinariaExterna; 
import cpc.modelo.demeter.mantenimiento.OrdenGarantia;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerActividad;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerDetalleGarantia;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerLote;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMaquinariaExterna; 
import cpc.persistencia.demeter.implementacion.mantenimiento.PerOrdenGarantia;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioMaquinariaExterna implements Serializable {
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioMaquinariaExterna 	negocio;
	
	private PerModelo 				permodelo;
	private PerCliente  			perCliente;
	private PerMaquinariaExterna	perMaquinariaExterna;
	private PerOrdenGarantia		perOrdenGarantia;
	private PerDetalleGarantia		perDetalleGarantia;
	private PerActividad			perActividad;
 
	
	
    public  NegocioMaquinariaExterna() {
		// TODO Auto-generated constructor stub
    	permodelo = new PerModelo();
    	perActividad = new PerActividad();
    	perCliente = new PerCliente();
    	perMaquinariaExterna = new PerMaquinariaExterna();
    	perOrdenGarantia = new PerOrdenGarantia();
    	perDetalleGarantia = new PerDetalleGarantia();
    	
    }
	
    public List<MaquinariaExterna> getMaquinarias () throws ExcFiltroExcepcion
    {
    	return perMaquinariaExterna.getAll();
    }
    
	public  static synchronized NegocioMaquinariaExterna getInstance() {
		if (negocio == null)
			negocio = new NegocioMaquinariaExterna();
		return negocio;
	}
    
	public Lote getLote(Integer indice) {
		return new PerLote().buscarId(indice);
	}
	 
	
	
	public List<MaquinariaExterna> maquinaria (MaquinariaExterna maquinaria) throws ExcFiltroExcepcion
	{
		return perMaquinariaExterna.getAll();
	}

	
	public List<Modelo> modelos() throws ExcFiltroExcepcion
	{
		return permodelo.getAll();
		
	}
	
	public List<Modelo> getModelos(Marca marca)
	{
		return permodelo.getPorMarca(marca);
	}

	
	 
	public List<Cliente> getClientes() throws ExcFiltroExcepcion
	{
		return perCliente.getAll();
		
	} 
	public void GuardarCliente(Cliente cliente) throws Exception
	{
		perCliente.guardar(cliente,cliente.getId());
	}
	 
	public void guardar(MaquinariaExterna maquinaria) throws Exception
	{
		perMaquinariaExterna.guardar(maquinaria,maquinaria.getId());
	}
	
	 
	
	public List<Modelo> getModelotipoGarantia()
	{
		return permodelo.getModeloTipoGarantia();
		
	}   
	
	public Cliente getCliente(Cliente cliente)
	{
		return perCliente.buscarId(cliente.getId());
	} 
	
	public MaquinariaExterna getMaquinaria(MaquinariaExterna maquinaria)
	{
		return perMaquinariaExterna.buscarId(maquinaria.getId());
	}
	
	public Boolean perteneceplaca(String placa,Integer idMaquinaria)
	{
		if(idMaquinaria == null)
		{
			return perMaquinariaExterna.perteneceplaca(placa);
		}
		else
		{ 
			return perMaquinariaExterna.perteneceplaca(placa,idMaquinaria);
		}
	}
	
	public Boolean perteneceserialMotor(String serialMotor, Object objecto[],Integer idMaquinaria)
	{
		if(idMaquinaria == null)
		{
			return perMaquinariaExterna.perteneceserialMotor(serialMotor);
		}
		else
		{
			return perMaquinariaExterna.perteneceserialMotor(serialMotor,idMaquinaria);
		}
	}
	
	public Boolean perteneceserialcarroceria(String serialcarroceria,Integer idMaquinaria)
	{
		if(idMaquinaria == null)
		{
			return perMaquinariaExterna.perteneceserialcarroceria(serialcarroceria);
		}
		else
		{
			return perMaquinariaExterna.perteneceserialcarroceria(serialcarroceria,idMaquinaria);
		}
	}
	
	public Boolean perteneceidentidadLegal(String identidadLegal)
	{
		
		return  perCliente.perteneceidentidadLegal(identidadLegal);
	}	
	
	public Boolean perteneceserie(String serial)
	{
		return perMaquinariaExterna.perteneceserie(serial);
	}
	
	
	public List<MaquinariaExterna> getMaquinariasSinGarantia()
	{
		return perMaquinariaExterna.getMaquinariaSinGarantia();
	}

	public List<MaquinariaExterna> getMaquinariaSinPropietario()
	{
		return perMaquinariaExterna.getMaquinariaSinPropietario();
	}

	public List<MaquinariaExterna> getMaquinariasConGarantia()
	{
		return perMaquinariaExterna.getMaquinariaConGarantia();
	}	

	public List<MaquinariaExterna> getMaqnaConGarantiaActiva(List<Integer> estatus)
	{

		return perMaquinariaExterna.getMaqnaConGarantiaEstatus(estatus);
	}	
	
	public List< OrdenGarantia> ListarOrdenes () throws ExcFiltroExcepcion
	{
		return perOrdenGarantia.getAll();
	}
	
	
	public void guardar(OrdenGarantia g) throws Exception
	{
		perOrdenGarantia.guardar(g,g.getId());
	}
	
	public void eliminar(OrdenGarantia g) throws Exception
	{
		perOrdenGarantia.borrar(g);
	}
	
	public List<DetalleGarantina> detalle_garantiasinMaquinaria( MaquinariaExterna maquinaria)
	{
		return perDetalleGarantia.detalleGarantia_not_in_Maquinaria(maquinaria);
		
	}
	
	public List<Actividad> actividades( DetalleGarantina detalleGarantia,Modelo modelo)
	{
		return perActividad.getActividad_DetalleGarantia( detalleGarantia, modelo);
	}
	
}
 
