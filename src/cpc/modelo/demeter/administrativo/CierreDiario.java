package cpc.modelo.demeter.administrativo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cpc.persistencia.demeter.implementacion.administrativo.PerCierreDiario;

import cva.pc.demeter.utilidades.Fecha;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


@Audited @Entity
@Table(name="tbl_dem_cierrediario", schema="administracion")//2300176.885

public class CierreDiario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7122921517322024598L;
	private Integer								id;
	private Date 								fecha;
	private double								ingresoFacturado;
	private double								ImpuestoFacturado;
	private double								cuentasPorCobrar;
	private double								montoAdelantos;
	private double								montoRecibos;
	private double								montoDescuentos;
	private double								cuentasPorPagar;
	private double								montoDepositado;
	private String								Observacion;
	private ControlSede 						datosSede;
	
	private List<CierreDiarioDocumento> 		documentos;
	private List<CierreDiarioImpuesto> 			impuestos;
	private List<CierreDiarioCuentaCobrar> 		cuentasCobrar;
	private List<CierreDiarioCuentaAdelanto>	cuentasAdelantos;
	private List<CierreDiarioCuentaPagar> 		cuentasPagadas;
	private List<CierreDiarioDeposito> 			depositos;
	private List<CierreDiarioReversoRecibo>    reversos;
	
	
	
	@SequenceGenerator(name="SeqCierreDiario", sequenceName="administracion.tbl_dem_cierrediario_seq_idcierre_seq", allocationSize=1)
	@Id
	@GeneratedValue(generator="SeqCierreDiario")
	@Column(name="seq_idcierre")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha", nullable = false)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="dbl_ingfac")
	public double getIngresoFacturado() {
		return ingresoFacturado;
	}
	public void setIngresoFacturado(double ingresoFacturado) {
		this.ingresoFacturado = ingresoFacturado;
	}
	
	@Column(name="dbl_impfac")
	public double getImpuestoFacturado() {
		return ImpuestoFacturado;
	}
	public void setImpuestoFacturado(double impuestoFacturado) {
		ImpuestoFacturado = impuestoFacturado;
	}
	
	@Column(name="dbl_cuecob")
	public double getCuentasPorCobrar() {
		return cuentasPorCobrar;
	}
	public void setCuentasPorCobrar(double cuentasPorCobrar) {
		this.cuentasPorCobrar = cuentasPorCobrar;
	}
	
	@Column(name="dbl_monrec")
	public double getMontoRecibos() {
		return montoRecibos;
	}
	public void setMontoRecibos(double montoRecibos) {
		this.montoRecibos = montoRecibos;
	}
	
	@Column(name="dbl_cuepag")
	public double getCuentasPorPagar() {
		return cuentasPorPagar;
	}
	public void setCuentasPorPagar(double cuentasPorPagar) {
		this.cuentasPorPagar = cuentasPorPagar;
	}
	
	@Column(name="dbl_mondep")
	public double getMontoDepositado() {
		return montoDepositado;
	}
	public void setMontoDepositado(double montoDepositado) {
		this.montoDepositado = montoDepositado;
	}
	
	@Column(name="str_observacion")
	public String getObservacion() {
		return Observacion;
	}
	public void setObservacion(String observacion) {
		Observacion = observacion;
	}
	
	@Column(name="dbl_monade")
	public double getMontoAdelantos() {
		return montoAdelantos;
	}
	public void setMontoAdelantos(double montoAdelantos) {
		this.montoAdelantos = montoAdelantos;
	}
	
	@OneToMany( mappedBy="cierreDiario", targetEntity=CierreDiarioCuentaAdelanto.class)
		public List<CierreDiarioCuentaAdelanto> getCuentasAdelantos() {
		return cuentasAdelantos;
	}
	public void setCuentasAdelantos(List<CierreDiarioCuentaAdelanto> cuentasAdelantos) {
		this.cuentasAdelantos = cuentasAdelantos;
	}

	
	@OneToMany(mappedBy="cierreDiario", targetEntity=CierreDiarioDocumento.class)
	public List<CierreDiarioDocumento> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<CierreDiarioDocumento> documentos) {
		this.documentos = documentos;
	}
	
	@OneToMany(mappedBy="cierreDiario", targetEntity=CierreDiarioImpuesto.class)
	public List<CierreDiarioImpuesto> getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(List<CierreDiarioImpuesto> impuestos) {
		this.impuestos = impuestos;
	}
	
	@OneToMany(mappedBy="cierreDiario", targetEntity=CierreDiarioCuentaCobrar.class)
	public List<CierreDiarioCuentaCobrar> getCuentasCobrar() {
		return cuentasCobrar;
	}
	public void setCuentasCobrar(List<CierreDiarioCuentaCobrar> cuentasCobrar) {
		this.cuentasCobrar = cuentasCobrar;
	}
	
	@OneToMany(mappedBy="cierreDiario", targetEntity=CierreDiarioCuentaPagar.class)
	public List<CierreDiarioCuentaPagar> getCuentasPagadas() {
		return cuentasPagadas;
	}
	public void setCuentasPagadas(List<CierreDiarioCuentaPagar> cuentasPagadas) {
		this.cuentasPagadas = cuentasPagadas;
	}
	
	@Column(name="dbl_mondes")
	public double getMontoDescuentos() {
		return montoDescuentos;
	}
	public void setMontoDescuentos(double montoDescuentos) {
		this.montoDescuentos = montoDescuentos;
	}
	
	@ManyToOne
	@JoinColumn(name="int_control")
	public ControlSede getDatosSede() {
		return datosSede;
	}
	public void setDatosSede(ControlSede datosSede) {
		this.datosSede = datosSede;
	}
	
	@OneToMany(cascade= CascadeType.ALL, mappedBy="cierreDiario", targetEntity=CierreDiarioDeposito.class)
	public List<CierreDiarioDeposito> getDepositos() {
		return depositos;
	}
	public void setDepositos(List<CierreDiarioDeposito> depositos) {
		this.depositos = depositos;
	}
	
	
	
	@OneToMany(cascade= CascadeType.ALL, mappedBy="cierreDiario", targetEntity=CierreDiarioReversoRecibo.class)
	public List<CierreDiarioReversoRecibo> getReversos() {
		return reversos;
	}
	public void setReversos(List<CierreDiarioReversoRecibo> reversos) {
		this.reversos = reversos;
	}
	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiario)) {
			return false;
		}
		CierreDiario other = (CierreDiario) o;
		return true && other.getId().equals(this.getId());
	}

}
