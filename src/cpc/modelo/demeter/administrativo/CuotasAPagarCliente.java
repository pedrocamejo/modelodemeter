package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cpc.modelo.ministerio.gestion.Cliente;

@Audited @Entity
@Table(name="tbl_dem_cuotasporpagar_cliente", schema="administracion")
public class CuotasAPagarCliente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7639539917513108886L;
	private Integer				id;
	private Contrato			contrato;
	private Cliente             pagador;
	private String				cuota;
	private double				montoCuota;
	private Date				fechaVencimiento;
	private Date				fechaUltimoLlamado;
	private boolean				cancelado;
	private boolean				vencido;
	
	@SequenceGenerator(name="Seq", sequenceName="administracion.tbl_dem_cuotasporpagar_cliente_seq_idcuotasporpagar_seq", allocationSize=1)
	@Id @GeneratedValue(generator="Seq")
	@Column(name="seq_idcuotasporpagar")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idcontrato")	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idpagador")
	public Cliente getPagador() {
		return pagador;
	}
	public void setPagador(Cliente pagador) {
		this.pagador = pagador;
	}
	
	@Column(name="str_cuota")
	public String getCuota() {
		return cuota;
	}
	public void setCuota(String cuota) {
		this.cuota = cuota;
	}
	
	@Column(name="dbl_montocuota")
	public double getMontoCuota() {
		return montoCuota;
	}
	public void setMontoCuota(double montoCuota) {
		this.montoCuota = montoCuota;
	}
	
	@Column(name="dtm_fechavence")	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}	
	
	@Column(name="dtm_fechaultimollamado")
	public Date getFechaUltimoLlamado() {
		return fechaUltimoLlamado;
	}
	public void setFechaUltimoLlamado(Date fechaUltimoLlamado) {
		this.fechaUltimoLlamado = fechaUltimoLlamado;
	}
	
	@Column(name="bol_cancelado")	
	public boolean isCancelado() {
		return cancelado;
	}
	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
	
	@Column(name="bol_vencido")	
	public boolean isVencido() {
		return vencido;
	}
	public void setVencido(boolean vencido) {
		this.vencido = vencido;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CuotasAPagarCliente)) {
			return false;
		}
		CuotasAPagarCliente other = (CuotasAPagarCliente) o;
		return true && other.getId().equals(this.getId());
	}
}
