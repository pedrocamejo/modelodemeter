package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ConceptoNotaCargo;
import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.ImpuestoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.NotaCargo;
import cpc.modelo.demeter.administrativo.ReciboDocumentoFiscal;
import cpc.modelo.demeter.administrativo.ReciboNotaCargo;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.administrativo.PerConceptoNotaCargo;
import cpc.persistencia.demeter.implementacion.administrativo.PerEstadoDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerNotaCargo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioNotaCargo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3999672989378177421L;
	/**
	 * 
	 */

	private static NegocioNotaCargo 	negocio;
	
		
	private NegocioNotaCargo(){
	 
	}

	public  static synchronized NegocioNotaCargo getInstance() {
		if (negocio == null)
			negocio = new NegocioNotaCargo();
		return negocio;
	}

	public Date getFechaCierre(){
		return getControlSede().getFechaCierreFactura();
	}
	
//	public Integer getHijosActivos(Integer id){
//		return new PerNotaCargo().getHijosActivos(id);
//		}
	
	public void anular(NotaCargo notaCargo){
		//preparar la factura y sus recibos
		// hay que devolver el monto aplicado del recibo 
		// hay que anular el contrato 
		
		
		notaCargo.setMontoSaldo(new Double(0));
		notaCargo.setMonto(new Double(0));
		notaCargo.setAnulada(true);
		
		//recibos
		for(ReciboNotaCargo reciboNotaCargo :notaCargo.getRecibos())
		{
			Double montoRecibo = reciboNotaCargo.getRecibo().getSaldo();
			Double montoAplicado = reciboNotaCargo.getMonto(); // monto aplicado a la factura 
			
			reciboNotaCargo.getRecibo().setSaldo(montoAplicado+ montoRecibo); //reintegro el dinero 
			reciboNotaCargo.setMonto(0.0);
		}
		new PerNotaCargo().anular(notaCargo);
	}
	
	public void guardar(NotaCargo nota) throws Exception{
		
		
		new PerNotaCargo().guardar(nota, nota.getId(),getControlSede());
		
		
			
	}
	
	public ClienteAdministrativo getExpedienteAdministrativo(Cliente cliente) throws ExcFiltroExcepcion {
		return new PerClienteAdministrativo().getExpediente(cliente);
	}
	
	
	
	public ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}
	
	public List<NotaCargo> getTodos() throws ExcFiltroExcepcion{
		return new PerNotaCargo().getAll();
	}
	
	public void pagarNotaCargo(NotaCargo nota) throws Exception
	{
		Double montoPagado = new Double(0);
		for(ReciboNotaCargo recibo : nota.getRecibos())
		{
			recibo.setNotaCargo(nota);
		}
		
		if(nota.getMontoSaldo() <= 0)
		{
			nota.setCancelada(true);
		}
		
		new PerNotaCargo().pagar(nota);
			
	}
	
	public Cliente getClienteproject(Cliente cliente){
		PerCliente persistencia = new PerCliente();
		cliente =persistencia.getclienteproject(cliente);	
		
	
		return cliente;
	}
	
	public List<Cliente> getClientesProject() throws ExcFiltroExcepcion{
		PerCliente perCliente = new PerCliente();
		List<Cliente> clientes = perCliente.getAdministrativosproject();
		return clientes; 
	}
	
	public List<ConceptoNotaCargo> getConceptos() throws ExcFiltroExcepcion{
		return new PerConceptoNotaCargo().getAll();
		
		
	};
}
