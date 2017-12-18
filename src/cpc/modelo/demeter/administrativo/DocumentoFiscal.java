package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.ministerio.basico.Telefono;
import cpc.modelo.ministerio.gestion.Cliente;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;
import cva.pc.demeter.utilidades.Real;

@Audited @Entity
@Table(name="tbl_dem_documentofiscal", schema="administracion")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "DocumentoFiscal.conSaldo", query = "SELECT d FROM DocumentoFiscal d where d.montoSaldo > 0")

public class DocumentoFiscal implements Serializable{

	private static final long serialVersionUID = 5622945254081679795L;
	
	private Integer		id;
	private String		serie;
	private	 Integer	nroControl;
	private Integer		nroDocumento;
	private Date		fecha;
	private boolean	credito;
	private boolean	cancelada;
	private EstadoDocumentoFiscal		estado;
	private Cliente		beneficiario;
	private String		direccionFiscal;
		
	private List<ReciboDocumentoFiscal>		recibos  = new ArrayList<ReciboDocumentoFiscal>();
	private List<DetalleDocumentoFiscal>	detalles = new ArrayList<DetalleDocumentoFiscal>();
	
	private Double 	montoBase;
	private Double 	montoDescuento;
	private Double 	montoTotal;
	private Double 	montoSaldo;
	
	private Double	porcDescuento;
	private String	observacion;
	
	private TipoDocumentoFiscal	 tipoDocumento;
	
	private List<ImpuestoDocumentoFiscal>  impuestos = new ArrayList<ImpuestoDocumentoFiscal>();
	
	private LibroVentaDetalle	 libro;
	private Contrato	contrato;
	
	private Boolean	 postServicio;
	
	
	
	public DocumentoFiscal() {
		super();
		nroControl 		= new Integer(0);
		nroDocumento	= new Integer(0);
		montoBase 		= new Double(0);
		montoDescuento 	= new Double(0);
		montoTotal		= new Double(0);
		montoSaldo		= new Double(0);
		porcDescuento 	= new Double(0);
		
	}
	
	
	
	public DocumentoFiscal(String serie,Integer	 nroControl,Integer	nroDocumento,Date fecha,
				Integer idestado,String estadoDesc,String beneficiario,Double montoBase,Double montoDescuento,
				Double montoTotal,Double montoSaldo,Double porcDescuento, String observacion)
	{
		this.serie = serie;
		this.nroControl = nroControl;
		this.nroDocumento = nroDocumento;
		this.fecha = fecha;
		this.estado = new EstadoDocumentoFiscal(idestado,estadoDesc);		
		this.beneficiario = new Cliente();
		this.beneficiario.setIdentidadLegal(beneficiario);
		this.montoBase = montoBase;
		this.montoDescuento = montoDescuento;
		this.montoTotal = montoTotal;
		this.montoSaldo = montoSaldo;
		this.porcDescuento = porcDescuento;
		this.observacion = observacion;

	}
	
	
	
	public DocumentoFiscal(int id, String serie, int nroControl,
			Date fecha, Contrato contrato, Cliente beneficiario,
			boolean credito, boolean cancelada, List<ReciboDocumentoFiscal> recibos,
			List<DetalleDocumentoFiscal> detalles) {
		super();
		this.id = id;
		this.serie = serie;
		this.nroControl = nroControl;
		this.fecha = fecha;
		//this.contrato = contrato;
		this.beneficiario = beneficiario;
		this.credito = credito;
		this.cancelada = cancelada;
		this.recibos = recibos;
		this.detalles = detalles;
		nroDocumento	= new Integer(0);
		montoBase 		= new Double(0);
		montoDescuento 	= new Double(0);
		montoTotal		= new Double(0);
		montoSaldo		= new Double(0);
		porcDescuento 	= new Double(0);
	}
	
	public DocumentoFiscal(int id, String serie, int nroControl,
			Date fecha, Contrato contrato, 
			String direccionBeneficiario, boolean credito, boolean cancelada, List<ReciboDocumentoFiscal> recibos,
			List<DetalleDocumentoFiscal> detalles) {
		super();
		this.id = id;
		this.serie = serie;
		this.nroControl = nroControl;
		this.fecha = fecha;
		this.credito = credito;
		this.cancelada = cancelada;
		this.recibos = recibos;
		this.detalles = detalles;
	}
	
	@SequenceGenerator(name="SeqFactura", sequenceName="administracion.tbl_dem_documentofiscal_seq_iddocumento_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqFactura")
	@Column(name="seq_iddocumento")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_serie")
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	@Column(name="int_nrocontrol")
	public Integer getNroControl() {
		return nroControl;
	}
	public void setNroControl(Integer nroControl) {
		this.nroControl = nroControl;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@OneToOne
	@JoinColumn(name="int_idcontrato")
	@Basic(optional= true)
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idpagador")
	public Cliente getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Cliente beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	@Column(name="bol_credito")
	public boolean isCredito() {
		return credito;
	}
	public void setCredito(boolean credito) {
		this.credito = credito;
	}
	
	@Column(name="bol_cancelada")
	public boolean isCancelada() {
		return cancelada;
	}
	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}
	
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="documentoFiscal")
	public List<ReciboDocumentoFiscal> getRecibos() {
		return recibos;
	}
	public void setRecibos(List<ReciboDocumentoFiscal> recibos) {
		this.recibos = recibos;
	}
	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="documento", targetEntity=DetalleDocumentoFiscal.class)
	public List<DetalleDocumentoFiscal> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleDocumentoFiscal> detalle) {
		this.detalles = detalle;
	}


	@ManyToOne
	@JoinColumn(name="int_estado")
	public EstadoDocumentoFiscal getEstado() {
		return estado;
	}
	public void setEstado(EstadoDocumentoFiscal estado) {
		this.estado = estado;
	}

	@Column(name="dbl_montobase")
	public Double getMontoBase() {
		return montoBase;
	}
	public void setMontoBase(Double montoBase) {
		this.montoBase = montoBase;
	}

	@Column(name="dbl_montototal")
	public Double getMontoTotal() {
		return montoTotal;
	}
	
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	@Column(name="dbl_descuento")
	public Double getMontoDescuento() {
		return montoDescuento;
	}
	public void setMontoDescuento(Double montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	@Column(name="dbl_pordesc")
	public Double getPorcDescuento() {
		return porcDescuento;
	}
	public void setPorcDescuento(Double porcDescuento) {
		this.porcDescuento = porcDescuento;
	}

	
	@Column(name="dbl_saldo")
	public Double getMontoSaldo() {
		return montoSaldo;
	}
	public void setMontoSaldo(Double montoSaldo) {
		this.montoSaldo = montoSaldo;
	}
	
	
	@Column(name="str_observacion")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER, mappedBy="documento", targetEntity=ImpuestoDocumentoFiscal.class)
	public List<ImpuestoDocumentoFiscal> getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(List<ImpuestoDocumentoFiscal> impuestos) {
		this.impuestos = impuestos;
	}
	
	@Column(name="int_nrodocumento")
	public Integer getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(Integer nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	@ManyToOne
	@JoinColumn(name="int_idtipodocumento")
	public TipoDocumentoFiscal getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoFiscal tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	@Column(name="str_direccion")
	public String getDireccionFiscal() {
		return direccionFiscal;
	}
	public void setDireccionFiscal(String direccionFiscal) {
		this.direccionFiscal = direccionFiscal;
	}
	
	@OneToOne(mappedBy="documento", targetEntity=LibroVentaDetalle.class)
	public LibroVentaDetalle getLibro() {
		return libro;
	}
	public void setLibro(LibroVentaDetalle libro) {
		this.libro = libro;
	}
	
	@Column(name="bol_postservicio")
	public Boolean getPostServicio() {
		return postServicio;
	}
	public void setPostServicio(Boolean postServicio) {
		this.postServicio = postServicio;
	}
	

	@Transient
	public String getStrNroDocumento() {
		return serie+ Formateador.rellenarNumero(nroControl,"00000");
	}
	
	@Transient
	public String getNombreBeneficiario() {
		return beneficiario.getNombres();
	}
	
	@Transient
	public String getStrEstado() {
		return estado.getDescripcion();
	}

	@Transient
	public String getDireccionBeneficiario() {
		return beneficiario.getDireccion();
	}
	
	@Transient
	public String getStrCancelado() {
		return cancelada? "Cancelada": "Con deuda";
	}
	
	@Transient
	public String getStrFecha() {
		return  Fecha.obtenerFecha(fecha);
	}
	
	@Transient
	public Telefono getTelefono() {
		return beneficiario.getTelefonos().get(0);
	}
	public void setTelefono(Telefono telefono) {
		this.beneficiario.getTelefonos().add(telefono);
	}
	
	@Transient
	public double getTotalImpuesto(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				monto += impuesto.getMonto();
			}
		return monto;
	}

	@Transient
	public double getTotalImpuesto12(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if( new Double(impuesto.getPorcentaje()).equals(12.00d)){
					monto += impuesto.getMonto();
				}
			}
		return monto;
	}

	
	@Transient
	public double getTotalImpuesto9(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if( new Double(impuesto.getPorcentaje()).equals(9.00d)){
					monto += impuesto.getMonto();
				}
			}
		return monto;
	}


	
	@Transient
	public double getTotalImpuesto7(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if( new Double(impuesto.getPorcentaje()).equals(7.00d)){
					monto += impuesto.getMonto();
				}
			}
		return monto;
	}


	
	
	@Transient
	public String getStrTotal() {
		return Formateador.formatearMoneda(Math.abs(montoTotal));
	}
	
	@Transient
	public String getStrNeto() {
		return Formateador.formatearMoneda(Math.abs(getNeto()));
	}
	
	@Transient
	public String getStrSaldo() {
		return Formateador.formatearMoneda(Math.abs(montoSaldo));
	}
	
	@Transient
	public String getStrBruto() {
		return Formateador.formatearMoneda(Math.abs(montoBase));
	}
	
	@Transient
	public String getStrDescuento() {
		return Formateador.formatearMoneda(Math.abs(montoDescuento));
	}
	
	@Transient
	public String getStrCSNeto() {
		return Formateador.formatearMoneda(getNeto());
	}
	
	@Transient
	public String getStrCSSaldo() {
		return Formateador.formatearMoneda(montoSaldo);
	}
	
	@Transient
	public String getStrCSBruto() {
		return Formateador.formatearMoneda(montoBase);
	}
	
	@Transient
	public double getNeto() {
		if (montoBase==null)
			return 0;
		
		if (montoDescuento!=null)			
			return montoBase-montoDescuento;
		
		return 0;
	}
	
	@Transient
	public String getStrTipoDocumento(){
		return tipoDocumento.getDescripcion();
	}
	
	@Transient
	public String getStrNroContrato(){
		if (contrato != null)
			return contrato.getStrNroDocumento();
		return null;
	}
	
	@Transient
	public void actualizarSaldo(double pago){
		montoSaldo = montoSaldo - pago;
	}
	
	@Transient
	public void saldarSaldo(){
		montoSaldo = new Double(0);
	}
	
	@Transient
	public void reversarSaldo(double pago){
		montoSaldo = montoSaldo + pago;
	}
	
	public boolean equal(Object objeto){
		DocumentoFiscal factura = (DocumentoFiscal) objeto;
		if (factura.getId() == this.id)
			return true;
		else 
			return false;
	}

	@Transient
	public  String getStrImpuestos() {
		StringBuilder salida = new StringBuilder(); 
		for(ImpuestoDocumentoFiscal item: impuestos){
			if (item.getBase() != 0){
				if (item.getImpuesto().getPorcentaje() != 0.00){
					if (salida.length()>0)
						salida.append(", ");
					salida.append(item.getImpuesto().getDescripcion());
					salida.append(" (");
					salida.append(item.getPorcentaje());
					salida.append("%) sobre ");
					salida.append(Math.abs(item.getBase()));
				}
			}
		}
		salida.append(" :");
		return salida.toString();
	}

	@Transient
	public double getTotalImponible(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if (impuesto.getPorcentaje() != 0.00)
					monto += impuesto.getBase();
			}
		return monto;
	}
	
	@Transient
	public double getTotalExcento(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if (impuesto.getPorcentaje() == 0.00)
					monto += impuesto.getBase();
			}
		return monto;
	}
	
	@Transient
	public String getTipoCobro(){
		if (tipoDocumento== null)
			return "";
		if (tipoDocumento.isHaber())
			return "Cuentas Por Cobrar";
		else
			return "Cuentas Por Pagar";
	}
	
	@Transient
	public Double getSaldoSinSigno(){
		return Math.abs(montoSaldo);
	}

	public String toString(){
		return getStrNroDocumento();
	}
	
	 
	
	public boolean equals(Object objeto){
		try{
			DocumentoFiscal cuenta = (DocumentoFiscal) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

	@Transient
	public double getTotalImponibleMoneda(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if (impuesto.getPorcentaje() != 0.00)
					monto += impuesto.getBase();
			}
		return Real.redondeoMoneda(monto);
	}
	
	@Transient
	public double getTotalExcentoMoneda(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				if (impuesto.getPorcentaje() == 0.00)
					monto += impuesto.getBase();
			}
		return Real.redondeoMoneda(monto);
	}
	@Transient
	public double getTotalImpuestoMoneda(){
		double monto=0;
		if (impuestos != null)
			for (ImpuestoDocumentoFiscal impuesto: impuestos){
				monto += impuesto.getMonto();
			}
		return Real.redondeoMoneda(monto);
	}
	
	@Transient
	public  String getStrImpuestosMoneda() {
		StringBuilder salida = new StringBuilder(); 
		for(ImpuestoDocumentoFiscal item: impuestos){
			if (item.getBase() != 0){
				if (item.getImpuesto().getPorcentaje() != 0.00){
					if (salida.length()>0)
						salida.append(", ");
					salida.append(item.getImpuesto().getDescripcion());
					salida.append(" (");
					salida.append(item.getPorcentaje());
					salida.append("%) sobre ");
					salida.append(Math.abs(Real.redondeoMoneda(item.getBase())));
				}
			}
		}
		salida.append(" :");
		return salida.toString();
	}
	
	@Transient
	public String getStrIdentidadLegalBeneficiario(){
		return beneficiario.getIdentidadLegal();
	}
}
