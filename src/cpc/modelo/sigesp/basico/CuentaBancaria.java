package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@Audited @Entity
@Table(name="scb_ctabanco", schema="sigesp")
public class CuentaBancaria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7076685204504283734L;
	private Integer			id;
	private Banco 			banco;
	private String 			nroCuenta;
	private String 			descripcion;
	private CuentaContable 	cuenta;
	private TipoCuenta 		codigoTipoCuenta;
	
	@SequenceGenerator(name="SeqCuentaBanco", sequenceName="sigesp.scb_ctabanco_seq_idcuentabanco_seq", allocationSize=1)
	@GeneratedValue(generator="SeqCuentaBanco")
	@Id @Column(name="seq_idcuentabanco")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

	@Column(name="dencta")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne
	@JoinColumn(name="sc_cuenta")
	public CuentaContable getCuenta() {
		return cuenta;
	}
	public void setCuenta(CuentaContable cuenta) {
		this.cuenta = cuenta;
	}
	
	@ManyToOne
	@JoinColumn(name="codtipcta")
	public TipoCuenta getCodigoTipoCuenta() {
		return codigoTipoCuenta;
	}
	public void setCodigoTipoCuenta(TipoCuenta codigoTipoCuenta) {
		this.codigoTipoCuenta = codigoTipoCuenta;
	}
	
	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="codemp", referencedColumnName="codemp"),
	    @JoinColumn(name="codban", referencedColumnName="codban"),
	})
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	@Column(name="ctaban")
	public String getNroCuenta() {
		return nroCuenta;
	}
	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}
	
	@Transient
	public String getStrBanco(){
		return banco.getDescripcion();
	}
	
	@Transient
	public String getStrTipoCuenta(){
		return codigoTipoCuenta.getDescripcion();
	}
	
	@Transient
	public String getStrCuentaContable(){
		return cuenta.getId();
	}

	public String toString(){
		return nroCuenta;
	}
	
	public boolean equals(Object objeto){
		try{
			CuentaBancaria cuenta = (CuentaBancaria) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	
}
