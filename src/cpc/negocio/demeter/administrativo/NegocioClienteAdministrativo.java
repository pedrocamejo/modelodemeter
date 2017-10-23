package cpc.negocio.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.zkoss.zkplus.spring.SpringUtil;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.ControlSede;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.administrativo.Recibo;
import cpc.modelo.demeter.vistas.ClienteAdministrativoView;
import cpc.modelo.excepcion.ExcDatosNoValido;
import cpc.modelo.ministerio.basico.TipoProductor;
import cpc.modelo.ministerio.dimension.UbicacionSector;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.ministerio.gestion.Productor;
import cpc.persistencia.SessionDao;
import cpc.persistencia.demeter.implementacion.PerControlSede;
import cpc.persistencia.demeter.implementacion.administrativo.PerClienteAdministrativo;
import cpc.persistencia.demeter.implementacion.administrativo.PerDocumentoFiscal;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.basico.PerProducto; 
import cpc.persistencia.ministerio.basico.PerProductor;
import cpc.persistencia.ministerio.basico.PerUnidadProductiva;
import cpc.persistencia.sigesp.implementacion.PerUnidadAdministrativa;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioClienteAdministrativo implements Serializable{
	
	
	private static final long serialVersionUID = 8612884217001389067L;
	private static NegocioClienteAdministrativo 	negocio;
	private PerClienteAdministrativo				persistenciaCliente;
	private ClienteAdministrativo					cliente;
	private List<ClienteAdministrativo>				clientesNuevos;
	private List<ClienteAdministrativo>				clientes;
	private PerProductor							perProductor;
	private PerDocumentoFiscal 						perDocumentoFiscal;
	private PerFactura								perFactura;
	
	private NegocioClienteAdministrativo(){
		perProductor = new PerProductor();
		persistenciaCliente = new PerClienteAdministrativo(); 
		perDocumentoFiscal = new PerDocumentoFiscal();
		perFactura = new PerFactura();
		
	}

	public  static synchronized NegocioClienteAdministrativo getInstance() {
		if (negocio == null)
			negocio = new NegocioClienteAdministrativo();
		return negocio;
	}
	
	public void guardar()  throws Exception{
		persistenciaCliente.guardar(cliente, cliente.getId(), getControlSede());
		
	}
	
	public void eliminar() throws Exception{
		persistenciaCliente.borrar(cliente);
	}
	
	public List<ClienteAdministrativo> getTodos() {
		List<ClienteAdministrativo> arr =null;
		try {
			arr = persistenciaCliente.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		} 
		return arr;
	}


	public List<ClienteAdministrativoView> getTodosView() {
		List<ClienteAdministrativoView> controlSede = null;
		try {
			controlSede = persistenciaCliente.getAllView();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return controlSede;
	}
	
	public ClienteAdministrativo getCliente() {
		return cliente;
	}

	public void setCliente(ClienteAdministrativo cliente) {
		this.cliente = cliente;
	}

	public List<ClienteAdministrativo> getNuevos() {
		List<ClienteAdministrativo> controlSede = null;
		try {
			controlSede = persistenciaCliente.getNuevos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return controlSede;
	}

	public List<ClienteAdministrativo> getClientesNuevos() {
		return clientesNuevos;
	}

	public void setClientesNuevos(List<ClienteAdministrativo> clientesNuevos) {
		this.clientesNuevos = clientesNuevos;
	}

	public List<ClienteAdministrativo> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteAdministrativo> clientes) {
		this.clientes = clientes;
	}
	
	public List<DocumentoFiscal> getSaldoFavor(Cliente cliente){
		return persistenciaCliente.getSaldoFavor(cliente);
	}
	
	public List<DocumentoFiscal> getSaldoPendiente(Cliente cliente){
		return persistenciaCliente.getSaldoPendiente(cliente);
	}
	
	public List<Recibo> getSaldoRecibo(Cliente cliente){
		return persistenciaCliente.getSaldoreRecibo(cliente);
	}
	public List<DocumentoFiscal> getPendientes(UbicacionSector sector ) throws ExcFiltroExcepcion {
		List<DocumentoFiscal> productores = new PerProductor().getProductoresUnidadProductivaFactura(sector);
		return productores;
	}
	 
	
	public List<DocumentoFiscal> getPendiente(ClienteAdministrativo cliente){
		return persistenciaCliente.getPendiente(cliente);
	}
	
	
	public List<DocumentoFiscal> getPendienteNoAsociados(Date inicio,Date fin){
		return persistenciaCliente.getPendienteNoAsociadas(inicio, fin);
	}
 
	
	public List<DocumentoFiscal> getoperacionescliente() throws ExcDatosNoValido{
		return persistenciaCliente.getoperacionescliente();
	}
	
	public List<DocumentoFiscal> getoperacionescliente(Cliente cliente) throws ExcDatosNoValido{
		return persistenciaCliente.getoperacionescliente(cliente);
	}
	
	private ControlSede getControlSede(){
		Integer  IdControl= (Integer) SpringUtil.getBean("idControl");
		return new PerControlSede().buscarId(IdControl);
	}

	public PerClienteAdministrativo getPersistenciaCliente() {
		return persistenciaCliente;
	}

	public void setPersistenciaCliente(PerClienteAdministrativo persistenciaCliente) {
		this.persistenciaCliente = persistenciaCliente;
	}
	
	public List<Productor> getProductoresSinDeuda() throws ExcFiltroExcepcion
	{
		return perProductor.getProductoresSinDeuda();
	}
	

	public List<Productor> getProductoresConDeuda() throws ExcFiltroExcepcion
	{
		return perProductor.getProductoresConDeuda();
	}

	public List<Object> getPendienteConsolidado(List<TipoProductor> tipo){
		return persistenciaCliente.getPendienteConsolidado(tipo);
	}
	public List<Object> getPendienteAsociadosConsolidado(List<TipoProductor> tipo){
		return persistenciaCliente.getPendienteAsociadasConsolidado(tipo);
	}
	
	
	public List<Object> getPendienteNoAsociadosConsolidado(List<TipoProductor> tipo){
		return persistenciaCliente.getPendienteNoAsociadasConsolidado(tipo);
	}
	
	public List<Object[]> getPendientesDocumentosNotasTotales(){
		return persistenciaCliente.getDedudasPendientesTotales();
	}
	
	public Object[] getPendienteDocumentosNotasTotales(Cliente cliente){
		return persistenciaCliente.getDedudasPorCliente(cliente);
	}
	
	public List<Object[]> getPendienteAsociados(List<TipoProductor> tipo){
		return new PerClienteAdministrativo().getDedudasPendientes(tipo, 2);
	};

	public List<Object[]> getPendienteNoAsociados(List<TipoProductor> tipo){
		return new PerClienteAdministrativo().getDedudasPendientes(tipo, 1);
	};
	
	public List<Object[]> getPendienteTotalAsociados(List<TipoProductor> tipo){
		return new PerClienteAdministrativo().getDedudasPendientes(tipo, 3);
	};
	
	public List<Object[]> getDeudasClientes(List<Productor>  clientes)	 {
		return new PerClienteAdministrativo().getDedudasPendientes(clientes);
	}

	public ClienteAdministrativo findById(Integer id){
		return new PerClienteAdministrativo().buscarId(id);
	}

	public ClienteAdministrativoView findByIdView(Integer id){
		return new PerClienteAdministrativo().findByIdView(id);
	}

}
