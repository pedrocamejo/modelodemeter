package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cva.pc.demeter.utilidades.Formateador;


@Audited @Entity
@Table(name="tbl_dem_cierrediariodocumento", schema="administracion")
public class CierreDiarioDocumento implements Serializable{

	private static final long serialVersionUID = -4799101057764678536L;
	private Integer 				id;
	private CierreDiario			cierreDiario;
	private DocumentoFiscal			documento;
	
	@SequenceGenerator(name="SeqCierreDocumento", sequenceName="administracion.tbl_dem_cierrediariodocumento_seq_idcierredocumento_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqCierreDocumento")
	@Column(name="seq_idcierredocumento")
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
	
	@OneToOne
	@JoinColumn(name="int_iddocumento")
	public DocumentoFiscal getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoFiscal documento) {
		this.documento = documento;
	}
	
	@Transient
	public String getStrDocumento(){
		return documento.getStrNroDocumento();
	}
	
	@Transient
	public double getBruto(){
		return documento.getMontoBase();
	}
	
	@Transient
	public double getNeto(){
		return documento.getNeto();
	}
	
	@Transient
	public double getImpuesto(){
		return documento.getTotalImpuesto();
	}
	
	@Transient
	public double getDescuento(){
		return documento.getMontoDescuento();
	}
	
	@Transient
	public String getStrBruto(){
		return documento.getStrBruto();
	}
	
	@Transient
	public String getStrNeto(){
		return documento.getStrNeto();
	}
	
	@Transient
	public String getStrImpuesto(){
		return Formateador.formatearMoneda(documento.getTotalImpuesto());
	}
	
	@Transient
	public String getStrDescuento(){
		return documento.getStrDescuento();
	}
	
	@Transient
	public String getStrCSNeto() {
		return Formateador.formatearMoneda(getNeto());
	}
	
	@Transient
	public String getStrCSSaldo() {
		return Formateador.formatearMoneda(documento.getMontoSaldo());
	}
	
	@Transient
	public String getStrCSBruto() {
		return Formateador.formatearMoneda(documento.getMontoBase());
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CierreDiarioDocumento)) {
			return false;
		}
		CierreDiarioDocumento other = (CierreDiarioDocumento) o;
		return true && other.getId().equals(this.getId());
	}
}
