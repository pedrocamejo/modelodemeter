package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.CuentaBancaria;

import cva.pc.demeter.utilidades.Formateador;



@Audited @Entity
@Table(name="tbl_dem_cierrediariodeposito", schema="administracion")
public class CierreDiarioDeposito implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4799101057764678536L;
	private Integer 				id;
	private CierreDiario			cierreDiario;
	private CuentaBancaria			cuenta;
	private String					nroDeposito;
	private double					monto;
	private boolean				depositado;
 
	
	
	@SequenceGenerator(name="SeqCierreDeposito", sequenceName="administracion.tbl_dem_cierrediariodeposito_seq_idcierredeposito_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreDeposito")
	@Column(name="seq_idcierredeposito")
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
	
	@ManyToOne
	@JoinColumn(name="str_idcuenta")
	public CuentaBancaria getCuenta() {
		return cuenta;
	}
	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}
	
	@Column(name="str_nrocuenta")
	public String getNroDeposito() {
		return nroDeposito;
	}
	public void setNroDeposito(String nroDeposito) {
		this.nroDeposito = nroDeposito;
	}
	
	@Column(name="dbl_monto")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Transient
	public boolean isValido(){
		return (cuenta.getId() == null) ? false : true; 
	}
	
	@Transient
	public String getStrBanco(){
		return cuenta.getStrBanco();
	}
	
	@Transient
	public String getStrNroCuenta(){
		return cuenta.getNroCuenta(); 
	}
	
	@Transient
	public String getStrMonto(){
		return Formateador.formatearMoneda(monto); 
	}
	
	@Transient
	public boolean isDepositado() {
		return depositado;
	}
	public void setDepositado(boolean depositado) {
		this.depositado = depositado;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiarioDeposito)) {
			return false;
		}
		CierreDiarioDeposito other = (CierreDiarioDeposito) o;
		return true && other.getId().equals(this.getId());
	}
	
}
