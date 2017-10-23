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

import cva.pc.demeter.utilidades.Formateador;


@Audited @Entity
@Table(name="tbl_dem_cierrediariocuentacobro", schema="administracion")
public class CierreDiarioCuentaCobrar implements Serializable{

	
	private static final long serialVersionUID = -5603314500225545211L;
	private Integer 				id;
	private CierreDiario			cierreDiario;
	private ClienteAdministrativo	cliente;
	private boolean					incremento;
	private double					monto;
	
	
	
	@SequenceGenerator(name="SeqCierreCobro", sequenceName="administracion.tbl_dem_cierrediariocuentacobro_seq_idcierrecuenta_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreCobro")
	@Column(name="seq_idcierrecuenta")
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
	@JoinColumn(name="int_idclienteadm")
	public ClienteAdministrativo getCliente() {
		return cliente;
	}
	public void setCliente(ClienteAdministrativo cliente) {
		this.cliente = cliente;
	}
	
	@Column(name="bol_incremento")
	public boolean isIncremento() {
		return incremento;
	}
	
	public void setIncremento(boolean incremento) {
		this.incremento = incremento;
	}
	
	@Column(name="dbl_monto", scale=2)
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Transient
	public String getStrMonto() {
		return Formateador.formatearMoneda(monto);
	}
	
	@Transient
	public String getStrCliente() {
		return cliente.getNombreCliente();
	}
	
	@Transient
	public double getCobrar() {
		return incremento ? monto : 0;
	}
	
	
	@Transient
	public double getCobrado() {
		return incremento ? 0 : monto ;
	}
	
	
	@Transient
	public String getStrCobrar() {
		return  Formateador.formatearMoneda(getCobrar());
	}
	
	
	@Transient
	public String getStrCobrado() {
		return  Formateador.formatearMoneda(getCobrado());
	}
	
	
	@Transient
	public String getStrCuenta() {
		return cliente.getCuentaCobro();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiarioCuentaCobrar)) {
			return false;
		}
		CierreDiarioCuentaCobrar other = (CierreDiarioCuentaCobrar) o;
		return true && other.getId().equals(this.getId());
	}
	
}
