package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.CuentaBancaria;
import cva.pc.demeter.utilidades.Formateador;

@Audited @Entity
@Table(name="tbl_dem_deposito", schema="administracion")
public class Deposito implements Serializable{

	/*
	 * esto no esta asociado al recibo como saber que recibo se cancelo en que momento determiando =? 
	 * los recibos tiene formas de pago las formas de pago tiene depositado alli esta el medio control que el intenta hacer :-D 
	 * */
	private static final long serialVersionUID = -3387937712164692933L;
	private Integer 				id;
	private List<DetalleDeposito>	cheques = new ArrayList<DetalleDeposito>();
	private CuentaBancaria 			cuentaBancaria;
	private String 					nroDeposito;
	private double 				montoEfectivo;
	private double 				montoTotal;
	private Date 					fecha;
	
	
	// para asociarlo al Cierre diario por la fecha 
	private boolean 				tipoCierre;
	private Date 					fechaCierre;
	
	
	private Date 					fechaRecepcion;
	private String					concepto;
	
	

	public Deposito(Integer id, CuentaBancaria cuentaBancaria,
			String nroDeposito, double montoEfectivo, double montoTotal,
			Date fecha, boolean tipoCierre, Date fechaCierre,
			Date fechaRecepcion, String concepto) {
		super();
		this.id = id;
		this.cuentaBancaria = cuentaBancaria;
		this.nroDeposito = nroDeposito;
		this.montoEfectivo = montoEfectivo;
		this.montoTotal = montoTotal;
		this.fecha = fecha;
		this.tipoCierre = tipoCierre;
		this.fechaCierre = fechaCierre;
		this.fechaRecepcion = fechaRecepcion;
		this.concepto = concepto;
	}
	
	
	public Deposito() {
		super();
		// TODO Auto-generated constructor stub
	}


	@SequenceGenerator(name="seqDeposito", sequenceName="administracion.tbl_dem_deposito_seq_iddeposito_seq", allocationSize=1)
	@Id @GeneratedValue(generator="seqDeposito")
	@Column(name="seq_iddeposito")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	

	
	@ManyToOne
	@JoinColumn(name="int_idcuentabanco")
	public CuentaBancaria getCuentaBancaria() {
		return cuentaBancaria;
	}
	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}
	
	@Column(name="dbl_montoefectivo")
	public double getMontoEfectivo() {
		return montoEfectivo;
	}
	public void setMontoEfectivo(double montoEfectivo) {
		this.montoEfectivo = montoEfectivo;
	}
	
	@Column(name="dbl_monto")
	public double getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	@OneToMany(cascade=CascadeType.ALL, targetEntity=DetalleDeposito.class, mappedBy="deposito")
	public List<DetalleDeposito> getCheques() {
		return cheques;
	}
	public void setCheques(List<DetalleDeposito> cheques) {
		this.cheques = cheques;
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
	@Column(name="dtm_fecharecepcion")
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fecha) {
		this.fechaRecepcion = fecha;
	}
	
	
	@Column(name="str_nrodeposito")
	public String getNroDeposito() {
		return nroDeposito;
	}
	public void setNroDeposito(String nroDeposito) {
		this.nroDeposito = nroDeposito;
	}
	
	@Column(name="bol_cierre")
	public boolean isTipoCierre() {
		return tipoCierre;
	}
	public void setTipoCierre(boolean tipoCierre) {
		this.tipoCierre = tipoCierre;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechacierre")
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
	@Column(name="str_concepto")
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	@Transient
	public String getStrMontoEfectivo(){
		return Formateador.formatearMoneda(montoEfectivo);
	}
	
	@Transient
	public String getStrMontoTotal(){
		return Formateador.formatearMoneda(montoTotal);
	}
	
	@Transient
	public String getBancoCuenta(){
		return cuentaBancaria.getStrBanco();
	}
	
	@Transient
	public String getStrCuenta(){
		return cuentaBancaria.getNroCuenta();
	}

	@Transient
	public String getStrConcepto(){
		return tipoCierre ? "CIERRE DIA "+fechaCierre.toString():"DEPOSITO PRODUCTOR";
	}
	
	@Transient
	public boolean isValido(){
		if (cuentaBancaria.getId() == null)
			return false;
		else
				return true;
	}
	
	public boolean equals(Object objeto){
		try{
			Deposito cuenta = (Deposito) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

  
	
}
