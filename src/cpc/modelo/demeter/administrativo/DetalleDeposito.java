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

import cpc.modelo.sigesp.basico.Banco;
import cva.pc.demeter.utilidades.Formateador;


@Audited @Entity
@Table(name="tbl_dem_depositocheque", schema="administracion")
public class DetalleDeposito  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3975096283495544708L;
	private Long			id;
	private Deposito 		deposito;
	private double 			monto;
	private	Date			fecha;
	private String 			cuenta;
	private String 			documento;  

	public DetalleDeposito(){
		super();
	}

	public DetalleDeposito(FormaPagoCheque pago, Deposito deposito){
		super();
		//banco = pago..getBanco(); en el cheuque no se lleva el control del banco del cual procede solo datos de nro cuenta nro cheque monto
		documento = pago.getCheque().getNroCheque();
		cuenta = pago.getCheque().getNroCuenta();
		fecha = pago.getFecha();
		monto = pago.getMonto();
		this.deposito = deposito;
	}
	
	@SequenceGenerator(name="seqDetDeposito", sequenceName="administracion.tbl_dem_depositocheque_seq_iddetalledeposito_seq", allocationSize =1)
	@Id @GeneratedValue(generator="seqDetDeposito")
	@Column(name="seq_iddetalledeposito")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne
	@JoinColumn(name="int_iddeposito")
	public Deposito getDeposito() {
		return deposito;
	}
	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}
	
	@Column(name="dbl_monto")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="str_numcue")
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	@Column(name="str_numdoc")
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	
	/*
	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_idcodemp", referencedColumnName="codemp"),
	    @JoinColumn(name="str_idcodban", referencedColumnName="codban"),
	})
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	*/
	
	@Transient
	public String getStrMonto(){
		return Formateador.formatearMoneda(monto);
	}
	
	@Transient
	public String getStrFecha(){
		return fecha.toString();
	}
	
	public String toString(){
		return deposito.getBancoCuenta()+" "+cuenta+" "+documento;
	}
	
	/*
	@Transient 
	public FormaPago getFormaPago(){
		FormaPago pago = new FormaPago();
		pago.setBanco(banco);
		pago.setCuenta(cuenta);
		pago.setDocumento(documento);
		pago.setMonto(monto);
		pago.setFecha(fecha);
		return pago; 
	}
	
	@Transient 
	public void setFormaPago(FormaPago forma){
		banco = forma.getBanco();
		cuenta = forma.getCuenta();
		documento = forma.getDocumento();
		monto = forma.getMonto();
		fecha = forma.getFecha();
	}
	*/
	public boolean equals(Object objeto){
		try{
			DetalleDeposito cuenta = (DetalleDeposito) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
}

