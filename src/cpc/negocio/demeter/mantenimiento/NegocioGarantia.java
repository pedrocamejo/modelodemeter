package cpc.negocio.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;
  
import cpc.modelo.demeter.mantenimiento.EnteExterno;
import cpc.modelo.demeter.mantenimiento.Fabricante;
import cpc.modelo.demeter.mantenimiento.Garantia;
import cpc.modelo.demeter.mantenimiento.Lote; 
import cpc.modelo.demeter.mantenimiento.MaquinariaExterna;
import cpc.modelo.demeter.mantenimiento.TipoGarantia;
import cpc.modelo.demeter.mantenimiento.UsuarioMantenimiento;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEnteExterno;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerFabricante;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerGarantia;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerLote;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerMaquinariaExterna;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerTipoGarantia;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerUsuarioMantenimiento;
import cpc.persistencia.sigesp.implementacion.PerMarca;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;




import util.Seguridad;


 
 

public class NegocioGarantia implements Serializable {
	
	private static final long serialVersionUID = -7144934362319560530L;
	private static NegocioGarantia 	negocio;
	
	private PerMarca 				permarca;
	private PerUsuarioMantenimiento perUsuarioMantenimiento;
	private PerEnteExterno  		perEnteExterno;
	private PerFabricante			perFabricante;
	private PerModelo 				permodelo;
	private PerCliente  			perCliente;
	private PerGarantia				perGarantia;
	private PerMaquinariaExterna	perMaquinariaExterna;
	private PerTipoGarantia			perTipoGarantia;
	
	
	private List<Marca> 			marcas; 
	
    public NegocioGarantia() {
		// TODO Auto-generated constructor stub
    	permarca = new PerMarca();
    	permodelo = new PerModelo();
    	perUsuarioMantenimiento = new PerUsuarioMantenimiento();
    	perEnteExterno = new PerEnteExterno();
    	perCliente = new PerCliente();
    	perFabricante = new PerFabricante();
    	perGarantia = new PerGarantia(); 
    	perMaquinariaExterna = new PerMaquinariaExterna();
    	perTipoGarantia = new PerTipoGarantia();
    	
    }
	

	public  static synchronized NegocioGarantia getInstance() {
		if (negocio == null)
			negocio = new NegocioGarantia();
		return negocio;
	}
    
	public Lote getLote(Integer indice) {
		return new PerLote().buscarId(indice);
	}
	public List<UsuarioMantenimiento> getUsuarioMatenimiento()
	{
		try {
			return perUsuarioMantenimiento.getAll();
		} catch (ExcFiltroExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<EnteExterno> getenteExterno(Object[] se) throws ExcFiltroExcepcion
	{
		return perEnteExterno.getAll();
		
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

	
	public UsuarioMantenimiento IniciarSession(String cedula, String contrasena) throws Exception
	{
			System.out.println(cedula+ "   " + Seguridad.getMD5(contrasena));
			
			List lista=   perUsuarioMantenimiento.InicarSession(cedula,Seguridad.getMD5(contrasena));
			System.out.println(lista.size());
			
			if (lista.size() <= 0)
			{
				throw new Exception("Credenciales de Usuario Invalido");
			}
			else
			{
				return (UsuarioMantenimiento) lista.get(0);
			}
	}
	public List<Cliente> getClientes( ) throws ExcFiltroExcepcion
	{
		return perCliente.getAll();
		
	} 
	public void GuardarCliente(Cliente cliente) throws Exception
	{
		perCliente.guardar(cliente,cliente.getId());
	}
	
	public List<Fabricante> fabricas() throws ExcFiltroExcepcion
	{
		return perFabricante.getAll();
	}
	
	public void guardar(Garantia garantia) throws Exception
	{
		//garantia.getMaquinaria().setGarantia(garantia);
		perGarantia.guardar(garantia,garantia.getId());
	}
	 
	public void guardar(MaquinariaExterna maquinaria) throws Exception
	{
		perMaquinariaExterna.guardar(maquinaria,maquinaria.getId());
	}
	
	
	public TipoGarantia getTipoGarantia(Modelo modelo)
	{
		return perTipoGarantia.get(modelo);
	}
	
	public List<Modelo> getModelotipoGarantia()
	{
		return permodelo.getModeloTipoGarantia( );
		
	}
	
	public List<Garantia>  getGarantias() throws ExcFiltroExcepcion
	{
		return perGarantia.getAll();
	}

	public List<Garantia>  getGarantias(EnteExterno ente) throws ExcFiltroExcepcion
	{
		return perGarantia.getxEnte(ente);
	}
	
	public List<Marca> getMarcasxGarantia()
	{
		return perGarantia.getmarcasxGarantia();
	}
	
	
	public List<TipoGarantia> getModeloxGarantia(Marca m)
	{
		return perGarantia.getModeloxGarantia(m);
	}
	
	public Cliente getCliente(Cliente cliente)
	{
		return perCliente.buscarId(cliente.getId());
	}
	
	public Garantia getGarantia(Garantia garantia)
	{
		return perGarantia.buscarId(garantia.getId());
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
	
	public Boolean perteneceserialMotor(String serialMotor,Integer idMaquinaria)
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
	
	public EnteExterno entepordefault()
	{
		return perEnteExterno.buscarId(0);
	}

	public void eliminarGarantia(Garantia g ) throws Exception
	{
		if (g.getEstatus() != 0 )
		{
			throw new Exception("NO Puedes Eliminar esta Garantia ");
		}
		perGarantia.borrar(g);
	}
	
	
	public String 	getUrlGarantia(Modelo modelo)
	{
		return perTipoGarantia.getUrl(modelo); 
	}
	
	public void borrar(MaquinariaExterna maquinaria) throws Exception
	{
		perMaquinariaExterna.borrar(maquinaria);
	}
}
