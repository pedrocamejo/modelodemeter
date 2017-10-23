package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.gestion.Cliente;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;


@Audited @Entity
@Table(name = "tbl_dem_contrato", schema = "administracion")
@Inheritance(strategy = InheritanceType.JOINED)
public class Contrato implements Serializable {
	
	private static final long 			serialVersionUID = -3608590339355662840L;
	private Integer						id;  
	private PlanFinanciamiento			planFinanciamiento;  	
	private Cliente             		pagador;	
	private String						serie;		
	private Integer						nroControl;	
	private Date						fecha;		
	private Date						fechaDesde;	
	private EstadoContrato				estado;   
	private Integer						diasVigencia; 
	private Double						monto;   
	private TipoContrato				tipoContrato;
	private Double						total;
	private Double						saldo;
	
	private List<DetalleContrato>		detallesContrato;
	private List<CuotasAPagarCliente> 	cuotasPorPagar;	
	private Boolean						facturado; 
	
	private EstadoExoneracionContrato  estadoExoneracion;
	
	public Contrato() {
		super();
		fechaDesde= new Date();
		fecha= new Date();
		detallesContrato = new ArrayList<DetalleContrato>();
		cuotasPorPagar = new ArrayList<CuotasAPagarCliente>();
	}

	@Column(name="seq_idcontrato")
	@SequenceGenerator(name="Contrato_Gen", sequenceName="administracion.tbl_dem_contrato_seq_idcontrato_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="Contrato_Gen")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="int_idplanfinanciamiento")
	public PlanFinanciamiento getPlanFinanciamiento() {
		return planFinanciamiento;
	}
	public void setPlanFinanciamiento(PlanFinanciamiento planFinanciamiento) {
		this.planFinanciamiento = planFinanciamiento;
	}

	@ManyToOne
	@JoinColumn(name="int_idpagador")
	public Cliente getPagador() {
		return pagador;
	}
	public void setPagador(Cliente pagador) {
		this.pagador = pagador;
	}

	@OneToOne
	@JoinColumn(name="int_idtipocontrato")
	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}
	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
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
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechadesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	@ManyToOne
	@JoinColumn(name="int_estado")
	public EstadoContrato getEstado() {
		return estado;
	}
	public void setEstado(EstadoContrato estado) {
		this.estado = estado;
	}

	@Column(name="int_diasvigencia")
	public Integer getDiasVigencia() {
		return diasVigencia;
	}
	public void setDiasVigencia(Integer diasVigencia) {
		this.diasVigencia = diasVigencia;
	}

	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="contrato", targetEntity=DetalleContrato.class, fetch=FetchType.EAGER)		
	public List<DetalleContrato> getDetallesContrato() {
		return detallesContrato;
	}
	public void setDetallesContrato(List<DetalleContrato> detalle) {
		this.detallesContrato = detalle;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="contrato", targetEntity=CuotasAPagarCliente.class,fetch=FetchType.LAZY)		
	public List<CuotasAPagarCliente> getCuotasAPagarCliente() {
		return cuotasPorPagar;
	}
	public void setCuotasAPagarCliente(List<CuotasAPagarCliente> cuotasPorPagar) {
		this.cuotasPorPagar = cuotasPorPagar;
	}

	@Column(name="bol_facturado")
	public Boolean getFacturado() {
		return facturado;
	}
	public void setFacturado(Boolean facturado) {
		this.facturado = facturado;
	}
	
	@Column(name="dbl_montoconiva")
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name="dbl_saldo")
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	
	@Transient
	public String getFechaString() {		
		return Fecha.obtenerFecha(fecha);
	}

	@Transient
	public String getFechaDesdeString() {
		return Fecha.obtenerFecha(fechaDesde);
	}
	
	@Transient
	public String getNombrePagador(){
		if (pagador == null)
			return "";
		return getPagador().getNombres();
	}
	
	@Transient
	public String getEstadoString(){
		if (estado == null)
			return "";
		return getEstado().getDescripcion();
	}

	@Transient
	public String getStrNroDocumento() {
		return serie+ Formateador.rellenarNumero(nroControl,"00000000");
	}
	
	@Transient
	public String getStrTipo() {
		if (tipoContrato != null)
			return tipoContrato.getNombre();
		return "";
	}
	
	public String toString(){
		return getStrNroDocumento();
	}
	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Contrato)) {
			return false;
		}
		Contrato other = (Contrato) o;
		return true && other.getId().equals(this.getId());
	}

	@Transient
	public void actualizarSaldo(double pago){
		saldo -= pago;
	}
	
	@Transient
	public void saldarSaldo(){
		saldo = 0.0;
	}

	@Transient
	public String getStrFacturado() {	
		if (facturado==true) return "SI"; else 
		return "NO";
	}

	@ManyToOne 
	@JoinColumn(name="int_estadoexoneracion")
	public EstadoExoneracionContrato getEstadoExoneracion() {
		return estadoExoneracion;
	}

	public void setEstadoExoneracion(EstadoExoneracionContrato exoneracionContrato) {
		this.estadoExoneracion = exoneracionContrato;
	}
	
	
    
	
}