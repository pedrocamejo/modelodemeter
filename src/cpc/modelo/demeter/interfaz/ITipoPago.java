package cpc.modelo.demeter.interfaz;

import java.util.Calendar;
import java.util.List;

import cpc.modelo.demeter.basico.TipoFormaPago;
import cpc.modelo.sigesp.basico.Banco;


public interface ITipoPago {
	public long getId();
	public void setId(long id);
	public cpc.modelo.demeter.basico.TipoFormaPago getTipoFormaPago();
	public void setTipoFormaPago(TipoFormaPago descripcion) ;
	public String getCuenta() ;
	public void setCuenta(String cuenta) ;
	public String getDocumento() ;
	public void setDocumento(String documento);
	public Calendar getFecha() ;
	public void setFecha(Calendar fecha);
	public void setTipoPago(List<ITipoPago> tipoPago);
	public List<ITipoPago> getTipoPago();
	
	public double getMonto() ;
	public void setMonto(double monto);
	public Banco getBanco() ;
	public void setBanco(Banco banco);
}
