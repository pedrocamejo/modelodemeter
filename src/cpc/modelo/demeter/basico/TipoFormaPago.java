package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name= "tbl_dem_tipo_forma_pago", schema="administracion")
public class TipoFormaPago implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6431114536742220298L;
	
	private Integer 	id;
	private String 		descripcion;
	private boolean 	usaDocumento;
	private boolean 	usaCuenta;
	private boolean 	usaBanco;
	private boolean	 nota;
	private Boolean		deposito;
	private String		cuentaContable;
	
	public TipoFormaPago(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	
	public TipoFormaPago() {
		super();
	}
	@SequenceGenerator(name="SeqTipoPago", sequenceName="administracion.tbl_dem_tipo_forma_pago_seq_idtipoformapago_seq", allocationSize=1)
	@Column(name="seq_idtipoformapago")	
	@Id @GeneratedValue(generator="SeqTipoPago") 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_usadocu")
	public boolean isUsaDocumento() {
		return usaDocumento;
	}
	public void setUsaDocumento(boolean usaDocumento) {
		this.usaDocumento = usaDocumento;
	}

	@Column(name="bol_usacuen")
	public boolean isUsaCuenta() {
		return usaCuenta;
	}
	public void setUsaCuenta(boolean usaCuenta) {
		this.usaCuenta = usaCuenta;
	}

	@Column(name="bol_usaban")
	public boolean isUsaBanco() {
		return usaBanco;
	}
	public void setUsaBanco(boolean usaBanco) {
		this.usaBanco = usaBanco;
	}
	
	@Column(name="bol_nota")
	public boolean isNota() {
		return nota;
	}
	public void setNota(boolean nota) {
		this.nota = nota;
	}
	
	@Column(name="bol_deposito")
	public Boolean getDeposito() {
		return deposito;
	}
	public void setDeposito(Boolean deposito) {
		this.deposito = deposito;
	} 
	
	@Column(name="str_cuentacontable")
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}


	public boolean equals(Object objeto){
		try{
			TipoFormaPago cuenta = (TipoFormaPago) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

	public String toString(){
		return descripcion;
	}

}
