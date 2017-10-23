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
@Table(name="tbl_dem_cierrediarioimpuesto", schema="administracion")
public class CierreDiarioImpuesto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4710230113680803774L;
	private Integer 			id;
	private CierreDiario		cierreDiario;
	private Impuesto 			impuesto;
	private double 				porcentaje;
	private double 				base;
	private double 				monto;
	
	
	
	@SequenceGenerator(name="SeqCierreImpuesto", sequenceName="administracion.tbl_dem_cierrediarioimpuesto_seq_idcierreimpuesto_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreImpuesto")
	@Column(name="seq_idcierreimpuesto")
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
	@JoinColumn(name="int_idtipoimpuesto")
	public Impuesto getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}
	
	@Column(name="dbl_porcentaje")
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	@Column(name="dbl_base")
	public double getBase() {
		return base;
	}
	public void setBase(double base) {
		this.base = base;
	}
	
	@Column(name="dbl_monto")
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
	public String getStrPorcentaje() {
		return Formateador.formatearMoneda(porcentaje);
	}
	
	@Transient
	public String getStrBase() {
		return Formateador.formatearMoneda(base);
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiarioImpuesto)) {
			return false;
		}
		CierreDiarioImpuesto other = (CierreDiarioImpuesto) o;
		return true && other.getId().equals(this.getId());
	}
}
