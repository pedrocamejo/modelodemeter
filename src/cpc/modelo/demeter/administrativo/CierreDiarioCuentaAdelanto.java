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

import cpc.modelo.ministerio.gestion.Cliente;

import cva.pc.demeter.utilidades.Formateador;


@Audited @Entity
@Table(name="tbl_dem_cierrediarioadelanto", schema="administracion")
public class CierreDiarioCuentaAdelanto implements Serializable{

	
	private static final long serialVersionUID = -5603314500225545211L;
	private Integer 				id;
	private CierreDiario			cierreDiario;
	private Cliente					cliente;
	private double					monto;
	
	
	
	@SequenceGenerator(name="SeqCierreAdelanto", sequenceName="administracion.tbl_dem_cierrediarioadelanto_seq_idcierreadelanto_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreAdelanto")
	@Column(name="seq_idcierreadelanto")
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
	@JoinColumn(name="int_idcliente")
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		return cliente.getNombres();
	}
	
	@Transient
	public double getCobrado() {
		return monto;
	}
	
	@Transient
	public String getStrCobrado() {
		return  Formateador.formatearMoneda(getCobrado());
	}
	

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiarioCuentaAdelanto)) {
			return false;
		}
		CierreDiarioCuentaAdelanto other = (CierreDiarioCuentaAdelanto) o;
		return true && other.getId().equals(this.getId());
	}
	
}
