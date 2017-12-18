package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cva.pc.demeter.utilidades.Fecha;

@Audited @Entity
@Table(name="tbl_dem_libroventadetalle", schema="administracion")
public class LibroVentaDetalle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3245625295067174216L;
	private Integer			 id;
	private LibroVenta		 libro;
	private DocumentoFiscal	 documento;
	
	@Transient
	private String			facturaAfectada;
	
	
	@SequenceGenerator(name="SeqLibroDetalle", sequenceName="administracion.tbl_dem_libroventadetalle_seq_iddetlibvent_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqLibroDetalle")
	@Column(name="seq_iddetlibvent")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idlibvent")
	public LibroVenta getLibro() {
		return libro;
	}
	public void setLibro(LibroVenta libro) {
		this.libro = libro;
	}
	
	@OneToOne
	@JoinColumn(name="int_iddocumento")
	public DocumentoFiscal getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoFiscal documento) {
		this.documento = documento;
	}
	
	@Transient
	public String getStrCSBruto(){
		return documento.getStrBruto();
	}
	
	@Transient
	public String getStrDocumento(){
		return documento.getStrNroDocumento();
	}
	
	@Transient
	public String getBeneficiario(){
		return documento.getNombreBeneficiario();
	}
	
	@Transient
	public String getStrCSNeto(){
		return documento.getStrNeto();
	}
	
	@Transient
	public String getNroNotaDebito(){
		if (documento.getTipoDocumento().isTipoFactura())
			return "";
		else
			if (documento.getTipoDocumento().isHaber())
				return "";
			else
				return documento.getStrNroDocumento();
	}
	
	@Transient
	public String getNroNotaCredito(){
		if (documento.getTipoDocumento().isTipoFactura())
			return "";
		else
			if (documento.getTipoDocumento().isHaber())
				return documento.getStrNroDocumento();
			else
				return "";
	}
	
	@Transient
	public String getNroFactura(){
		if (documento.getTipoDocumento().isTipoFactura())
			return documento.getStrNroDocumento();
		else 
			return "";
	}
	
	@Transient
	public String getNroFacturaAfecta(){
		if (facturaAfectada == null) return "";
		return facturaAfectada;
	}
	
	@Transient
	public String setNroFacturaAfecta(String nroFactura){
		return facturaAfectada = nroFactura;
	}
	
	
	@Transient
	public String getFecha(){
		return documento.getFecha().toString();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof LibroVentaDetalle)) {
			return false;
		}
		LibroVentaDetalle other = (LibroVentaDetalle) o;
		return true && other.getId().equals(this.getId());
	}
	//para libro de ventas extendido 
	@Transient
	public String getNumerosREcibos(){
		String numerosrecibos=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
	        numerosrecibos=  numerosrecibos+recibo.getRecibo().getControl()+" / ";		
		}
		return numerosrecibos;
	}
	

	@Transient
	public String getNumerosFormapago(){
		String numerosFormaPago=" ";
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			 for (FormaPago formaPago : recibo.getRecibo().getFormaspago()) {
				 if (formaPago.getClass().equals(FormaPagoDeposito.class))
				 {
					 FormaPagoDeposito deposito = (FormaPagoDeposito) formaPago;
					 numerosFormaPago = numerosFormaPago + deposito.getDocumento() +" / ";	
				 }
				 if (formaPago.getClass().equals(FormaPagoTransferencia.class))
				 {
					 FormaPagoTransferencia transferencia = (FormaPagoTransferencia) formaPago;
					// System.out.println("numero de transferencia"+ transferencia.getDocumento());
					 numerosFormaPago = numerosFormaPago + transferencia.getDocumento() +" / ";	
				 }
			}
		}
		return numerosFormaPago;
	}

	
	@Transient
	public String getMontosFormaPago(){
		String montosFormaPago=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			 
			 for (FormaPago formaPago : recibo.getRecibo().getFormaspago()) {
				 
				 DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
				 otherSymbols.setDecimalSeparator(',');
				 otherSymbols.setGroupingSeparator('.'); 
							 
				 DecimalFormat decimalFormat = new DecimalFormat();
				 decimalFormat.setDecimalFormatSymbols(otherSymbols);
				 decimalFormat.setGroupingSize(3);
				 montosFormaPago = montosFormaPago + decimalFormat.format(formaPago.getMonto())+" / ";	
			}
	      
		}
		return montosFormaPago;
	}

	@Transient
	public String getBancosFormaPago(){
		String bancosFormaPago=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			 for (FormaPago formaPago : recibo.getRecibo().getFormaspago()){
				 bancosFormaPago = bancosFormaPago + formaPago.getStrBanco() +" / ";	
			}
		}
		return bancosFormaPago;
	}
	
	@Transient
	public String getFechasFormaPago(){
		String fechasFormaPago=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
		
			 for (FormaPago formaPago : recibo.getRecibo().getFormaspago()) {
				 fechasFormaPago = fechasFormaPago + Fecha.obtenerFecha(formaPago.getFecha())+" * ";	
			}
	      
		}
		return fechasFormaPago;
	}
	
	@Transient
	public String getCuentasFormaPago(){
		String cuentasFormaPago=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			 for (FormaPago formaPago : recibo.getRecibo().getFormaspago()){
				 cuentasFormaPago = cuentasFormaPago + formaPago.getStrCuenta() +" / ";	
			}
	      
		}
		return cuentasFormaPago;
	}

	@Transient
	public String getMontosAplicados(){
		String montoAplicado=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			 otherSymbols.setDecimalSeparator(',');
			 otherSymbols.setGroupingSeparator('.'); 
			 
			 DecimalFormat decimalFormat = new DecimalFormat();
			 decimalFormat.setDecimalFormatSymbols(otherSymbols);
			 decimalFormat.setGroupingSize(3);
			
			
	        montoAplicado=  montoAplicado+decimalFormat.format(recibo.getMonto())+" / ";		
		}
		return montoAplicado;
	}
	
	@Transient
	public String getMontoRecibos(){
		String montoRecibos=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			 otherSymbols.setDecimalSeparator(',');
			 otherSymbols.setGroupingSeparator('.'); 
			 
			 DecimalFormat decimalFormat = new DecimalFormat();
			 decimalFormat.setDecimalFormatSymbols(otherSymbols);
			 decimalFormat.setGroupingSize(3);
			 
	        montoRecibos=  montoRecibos+decimalFormat.format(recibo.getRecibo().getMonto())+" / ";		
		}
		return montoRecibos;
	}
	@Transient
	public String getSaldoRecibos(){
		String saldoRecibos=" ";
	
		for (ReciboDocumentoFiscal recibo : documento.getRecibos()) {
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
			 otherSymbols.setDecimalSeparator(',');
			 otherSymbols.setGroupingSeparator('.'); 
			 
			 DecimalFormat decimalFormat = new DecimalFormat();
			 decimalFormat.setDecimalFormatSymbols(otherSymbols);
			 decimalFormat.setGroupingSize(3);
			
	        saldoRecibos=  saldoRecibos+decimalFormat.format(recibo.getRecibo().getSaldo())+" / ";		
		}
		return saldoRecibos;
	}
	

	@Transient
	public double getTotalImpuesto12(){
		return documento.getTotalImpuesto12();
	}

	@Transient
	public double getTotalImpuesto9(){
		return documento.getTotalImpuesto9();
	}
	
	@Transient
	public double getTotalImpuesto7(){
		return documento.getTotalImpuesto7();
	}

}
