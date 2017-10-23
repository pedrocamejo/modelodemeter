package cpc.modelo.demeter.administrativo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table; 
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.Banco;



@Audited
@Entity
@Table(name="tbl_dem_Forma_Pago_Transferencia", schema="administracion")
public class FormaPagoTransferencia extends FormaPago {

	/*
	 * nuemro de cuenta del banco esta es la que esta relacionada con 
	 * la cuenta contable :-D con el Objecto Cuenta Bancaria 
	 * */
	private String 			cuenta;
	private String 			documento;
	private Banco			banco;

	
	public FormaPagoTransferencia(Integer id, Long idRecibo,String identidadLegal, Date fecha, Date fechaRecepcion,
			Double monto, String cuenta, String documento) 
	{
		super(id, idRecibo, identidadLegal, fecha, fechaRecepcion, monto);
		this.cuenta = cuenta;
		this.documento = documento;
	}
	
	
	
	
	public FormaPagoTransferencia() {
		super();
		// TODO Auto-generated constructor stub
	}




	@Column
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Column
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
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
	 
	 
	@Override
	@Transient
	public String getStrBanco() {
		// TODO Auto-generated method stub
		return banco.getDescripcion();
	}
	
	
	@Transient
	public String getStrCuenta() {
		// TODO Auto-generated method stub
		return cuenta;
	}
	 
	@Transient
	public String getStrDocumento()
	{
		return documento;
	}
	
	
}
