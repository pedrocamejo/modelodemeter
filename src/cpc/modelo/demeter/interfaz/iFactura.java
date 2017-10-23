package cpc.modelo.demeter.interfaz;



import cpc.modelo.demeter.administrativo.EstadoDocumentoFiscal;
import cpc.modelo.demeter.administrativo.TipoDocumentoFiscal;
import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.ministerio.gestion.Cliente;
import cva.pc.demeter.utilidades.Fecha;



public interface iFactura {

	public long getId() ;
	public void setId(long id);
	
	public String getSerie();
	public void setSerie(String serie);

	public long getNroControl();
	public void setNroControl(long nroControl);

	public Cliente getCliente();
	public void setCliente(Cliente cliente);

	/*
	public Contrato getContrato();
	public void setContrato(Contrato cliente);
	*/

	public iFactura getPadre();
	public void setPadre(iFactura cliente);

	
	public Fecha getFecha();
	public void setFecha(Fecha fecha);

	public TipoFormaPago getTipoPago();
	public void setTipoPago(TipoFormaPago tipoPago);

	public EstadoDocumentoFiscal getEstado();
	public void setEstado(EstadoDocumentoFiscal estado);

	public TipoDocumentoFiscal getTipo();
	public void setTipo(TipoDocumentoFiscal tipo);

	public double getTotal();
	public void setTotal(double total);

	
}
