package cpc.modelo.demeter.administrativo;

import java.io.Serializable;



import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
 


import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns; 
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Sede;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;
@Audited
@Entity
@Table(name="tbl_dem_recibo", schema="administracion")
public class Recibo implements Serializable{
	
	private static final long 	serialVersionUID = -3574608157260412533L;

	private Long				  id;
	private Date				  fecha;
	
	private String				  concepto;
	private String 				  control;
	
	private Sede				  sede;
	private Cliente	  			  cliente;

	private Double				  monto;
	private Double 				  saldo; 
	
	private Boolean				  anulado = false; // No anulado :-D
	@Transient
	private Boolean				  tipoFactura;

	private List<ReciboDocumentoFiscal> documentosFiscales= new   ArrayList<ReciboDocumentoFiscal>();
	private List<ReciboNotaCargo> notasCargos= new  ArrayList<ReciboNotaCargo>(); 
	
	private List<FormaPago> 	  formaspago = new   ArrayList<FormaPago>();
	
	
	
	
	public Recibo(Long id, Date fecha, String concepto, String control, Cliente cliente, Double monto, Double saldo, Boolean anulado) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.concepto = concepto;
		this.control = control;
		this.cliente = cliente;
		this.monto = monto;
		this.saldo = saldo;
		this.anulado = anulado;
	}
	
	
	public Recibo(Long id, Date fecha, String concepto, String control,Integer idCliente, 
				String  nombrecliente,String identidadLengal,String direccion, Double monto, Double saldo, Boolean anulado) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.concepto = concepto;
		this.control = control;
		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		cliente.setIdentidadLegal(identidadLengal);
		cliente.setNombres(nombrecliente);
		cliente.setDireccion(direccion);
		this.cliente = cliente;
		this.monto = monto;
		this.saldo = saldo;
		this.anulado = anulado;
	}
	
	public Recibo() {
		super();
	}

	public Recibo(Long id, Date fecha, String concepto, String control,Integer clienteId, 
			String identidadLegal , Double monto, Double saldo, Boolean anulado) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.concepto = concepto;
		this.control = control;
		this.cliente = new Cliente();
		cliente.setId(clienteId);
		cliente.setIdentidadLegal(identidadLegal);
		this.monto = monto;
		this.saldo = saldo;
		this.anulado = anulado;
	}

	
	
	
	public Recibo(long id/*, DocumentoFiscal documento*/, Date fecha,
			String control, Double monto) {
		super();
		this.id = id;
		//this.documento = documento;
		this.fecha = fecha;
		this.control = control;
 
		this.monto = monto;
	}
	
	@SequenceGenerator(name="SeqRecibo", sequenceName="administracion.tbl_dem_recibo_seq_idrecibo_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqRecibo")
	@Column(name="seq_idrecibo")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="str_control")
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	
	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="recibo", targetEntity=FormaPago.class,fetch= FetchType.EAGER)
	public List<FormaPago> getFormaspago() {
		
		return formaspago;
	}

	public void setFormaspago(List<FormaPago> formaspago) {
		this.formaspago = formaspago;
	}

	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp", insertable=false, updatable=false),
	    @JoinColumn(name="str_sede", referencedColumnName="codubifis", insertable=false, updatable=false),
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	@Column(name="str_concepto")
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	@Column(name="bol_anulado")
	public boolean isAnulado() {
		return anulado;
	}
	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}
	
	@Transient
	public String getEstado() {
		return anulado? "Anulado": "Activo";
	}
	
	public boolean equals(Object objeto){
		try{
			Recibo cuenta = (Recibo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	@Transient
	public String getStrFecha(){
		return Fecha.obtenerFecha(fecha);
	}

	@OneToMany(fetch=FetchType.EAGER,mappedBy="recibo")
	public List<ReciboDocumentoFiscal> getDocumentosFiscales() {
		return documentosFiscales;
	}

	public void setDocumentosFiscales(List<ReciboDocumentoFiscal> documentosFiscales) {
		this.documentosFiscales = documentosFiscales;
	}

	@OneToMany(fetch=FetchType.EAGER,mappedBy="recibo")
	public List<ReciboNotaCargo> getNotasCargos() {
		return notasCargos;
	}

	public void setNotasCargos(List<ReciboNotaCargo> reciboNotaCargos) {
		this.notasCargos = reciboNotaCargos;
	}

	
	@ManyToOne
	@JoinColumn(name="int_idCliente")
	@Fetch(FetchMode.JOIN)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Column(name="dbl_saldo")
	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	
	@Transient
	public String getStrPagador(){
		if (cliente != null)
			return cliente.getNombres();
		else
			return "";
	}
	
	@Transient
	public String getStrIdentidadLegalPagador(){
		if (cliente != null)
			return cliente.getIdentidadLegal();
		else
			return "";
	}
	
	@Transient
	public  JRDataSource getFormaspagoDS()
	{//new net.sf.jasperreports.engine.JREmptyDataSource($F{formaspago})
		return new JRBeanCollectionDataSource(formaspago);
	}
	

	@Transient
	public String getStrMonto()
	{
	//	DecimalFormat format = new DecimalFormat("####,###,###,###,###0.00");
	//	return format.format(monto);
		return Formateador.formatearMoneda(Math.abs(monto));
	}
	
	@Transient
	public String getStrSaldo()
	{
		//DecimalFormat format = new DecimalFormat("####,###,###,###,###0.00");
		//return format.format(saldo);
		return Formateador.formatearMoneda(Math.abs(saldo));
	}
	
	@Transient
	public String getStrEstado()
	{
		return (anulado ? "ANULADO":"ACTIVA");
	}
	
	@Transient
	public String getStrFacturas()
	{
		String resultado = "";
		for(ReciboDocumentoFiscal factura : this.documentosFiscales)
		{
			resultado += " "+factura.getDocumentoFiscal().getStrNroDocumento();
		}
		return resultado;
	}
	
	
	@Transient
	public String getStrMontoFactura()
	{
		String resultado = "";
		for(ReciboDocumentoFiscal factura : this.documentosFiscales)
		{
				
			resultado +=" "+factura.getDocumentoFiscal().getStrTotal();
				
		}
		return resultado + "";
	}
	
	@Transient
	public String 	getFormaPagosHtml()
	{
		String title = "<ul>";
		
		for(FormaPago pago :formaspago)
		{
			String tr = "<li> " + pago.getStrBanco()+ " ---  " + 
					pago.getStrCuenta()+  "  ---  " + pago.getStrMonto()+  " Bs. " + 
					"</li>";
			title += tr;
		}		
		title += "</ul>";
		return title;
	}
	
	
}
