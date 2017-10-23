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
@Table(name="tbl_dem_notacargo", schema="administracion")
@Inheritance(strategy = InheritanceType.JOINED)
//@NamedQuery(name = "DocumentoFiscal.conSaldo", query = "SELECT d FROM DocumentoFiscal d where d.montoSaldo > 0")

public class NotaCargo implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4994357951133658112L;
	
	private Integer							id;
	private	String							serie;
	private Integer							nroNotaCargo;
	
	
	private Date							fecha;
	private boolean						cancelada;
	private boolean						anulada;

	//datos para referencia
	private Cliente							cliente;
	private ConceptoNotaCargo				concepto;
		
	private List<ReciboNotaCargo>		recibos  = new ArrayList<ReciboNotaCargo>();
	
	
	private Double 							monto;
	private Double 							montoSaldo;
	
	private String							observacion;

	
	
	@Id
	@Column(name="seq_idnotacargo")
	@SequenceGenerator(name="seq_idnotacargo",sequenceName="administracion.tbl_dem_notacargo_seq_idnotacargo_seq",allocationSize=1 )
	@GeneratedValue(generator="seq_idnotacargo")
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

	@Column(name="int_nronotacargo")
	public Integer getNroNotaCargo() {
		return nroNotaCargo;
	}

	public void setNroNotaCargo(Integer nroNotaCargo) {
		this.nroNotaCargo = nroNotaCargo;
	}

	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name="bol_cancelada")
	public boolean isCancelada() {
		return cancelada;
	}

	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}
	@Column(name="bol_anulada")
	public boolean isAnulada() {
		return anulada;
	}

	public void setAnulada(boolean anulada) {
		this.anulada = anulada;
	}
	

	@ManyToOne
	@JoinColumn(name="int_idcliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	@ManyToOne
	@JoinColumn(name="int_idconcepto")
	public ConceptoNotaCargo getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoNotaCargo concepto) {
		this.concepto = concepto;
	}
	@OneToMany(fetch=FetchType.EAGER,mappedBy="notaCargo")
	public List<ReciboNotaCargo> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<ReciboNotaCargo> recibos) {
		this.recibos = recibos;
	}

	@Column(name="dbl_monto")
	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	@Column(name="dbl_montosaldo")
	public Double getMontoSaldo() {
		return montoSaldo;
	}

	public void setMontoSaldo(Double montoSaldo) {
		this.montoSaldo = montoSaldo;
	}

	@Column (name="str_observacion")
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	@Transient
	public String getStrFecha() {
		return  Fecha.obtenerFecha(fecha);
	}
	
	@Transient
	public String getStrSaldo() {
		return Formateador.formatearMoneda(Math.abs(montoSaldo));
	}
	@Transient
	public String getStrMonto() {
		return Formateador.formatearMoneda(Math.abs(monto));
	}
	
	@Transient
	public String getNombreCliente() {
		return cliente.getNombres();
	}
	
	@Transient
	public String getCedulaRifCliente() {
		return cliente.getIdentidadLegal();
	}
	
	@Transient
	public String getStrAnulada() {
		return anulada? "Anulada": "Activa";
	}
	
	@Transient
	public String getStrNroNota() {
		
		if (serie == null && nroNotaCargo == null)
			return "";
		if(serie == null && nroNotaCargo != null)
			return Formateador.rellenarNumero(nroNotaCargo,"00000");
		
		if (nroNotaCargo==null) nroNotaCargo = new Integer(0);
		
			return serie+ Formateador.rellenarNumero(nroNotaCargo,"00000");
	}

}