package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_documentoimpuesto", schema="administracion")
public class ImpuestoDocumentoFiscal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4527408997960764611L;
	private Integer				id;
	private Impuesto 			impuesto;
	private DocumentoFiscal 	documento;
	private double 				porcentaje;
	private double 				base;
	private double 				monto;
	
	@SequenceGenerator(name="SeqImpuestoFactura", sequenceName="administracion.tbl_dem_documentoimpuesto_seq_iddetalle_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqImpuestoFactura")
	@Column(name="seq_iddetalle")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="int_idtipoimpuesto")
	public Impuesto getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}
	
	@ManyToOne
	@JoinColumn(name="int_iddocumento")
	public DocumentoFiscal getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoFiscal documento) {
		this.documento = documento;
	}
	
	@Column(name="dbl_pporcentaje")
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
	
	@Column(name="int_monto")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Transient
	public void addMonto(double monto) {
		this.monto += monto;
	}

	@Transient
	public void delMonto(double monto) {
		this.monto -= monto;
	}
	
	@Transient
	public void addBase(double monto) {
		this.base += monto;
	}

	@Transient
	public void delBase(double monto) {
		this.base -= monto;
	}

	@Transient
	public void actualizarMonto() {
		this.monto =  base*porcentaje/100.00;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ImpuestoDocumentoFiscal)) {
			return false;
		}
		ImpuestoDocumentoFiscal other = (ImpuestoDocumentoFiscal) o;
		return true && other.getId().equals(this.getId());
	}
}
