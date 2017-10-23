package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.CuentaContable;
import cpc.modelo.sigesp.basico.Sede;

import cva.pc.demeter.utilidades.Formateador;


@Audited @Entity
@Table(name="tbl_dem_cierrediarioasiento", schema="administracion")
public class CierreDiarioAsiento implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4501386700747721253L;
	private Integer 			id;
	private CierreDiario		cierreDiario;
	private Date				fecha;
	private double 				debe;
	private double 				haber;
	private CuentaContable 		Cuenta;
	private Sede				sede;
	private String				renglon;
	
	
	@SequenceGenerator(name="SeqCierreAsiento", sequenceName="administracion.tbl_dem_cierrediarioasiento_seq_idcierreasiento_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreAsiento")
	@Column(name="seq_idcierreasiento")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idcierre")
	public CierreDiario getCierreDiario() {
		return cierreDiario;
	}
	public void setCierreDiario(CierreDiario cierreDiario) {
		this.cierreDiario = cierreDiario;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="dbl_debe")
	public double getDebe() {
		return debe;
	}
	public void setDebe(double debe) {
		this.debe = debe;
	}
	
	@Column(name="dbl_haber")
	public double getHaber() {
		return haber;
	}
	public void setHaber(double haber) {
		this.haber = haber;
	}
	
	@ManyToOne
	@JoinColumn(name="str_cuenta")
	public CuentaContable getCuenta() {
		return Cuenta;
	}
	public void setCuenta(CuentaContable cuenta) {
		Cuenta = cuenta;
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
	
	@Column(name="str_renglon")
	public String getRenglon() {
		return renglon;
	}
	public void setRenglon(String renglon) {
		this.renglon = renglon;
	}

	
	@Transient
	public String getStrDebe(){
		return Formateador.formatearMoneda(debe);
	}
	
	@Transient
	public String getStrCuenta(){
		if (Cuenta == null) return "";
		return Cuenta.getId();
	}
	
	@Transient
	public String getStrHaber(){
		return Formateador.formatearMoneda(haber);
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiarioAsiento)) {
			return false;
		}
		CierreDiarioAsiento other = (CierreDiarioAsiento) o;
		return true && other.getId().equals(this.getId());
	}

}
