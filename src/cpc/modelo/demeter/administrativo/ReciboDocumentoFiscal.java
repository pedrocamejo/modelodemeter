package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cva.pc.demeter.utilidades.Formateador;

@Entity
@Audited
@Table(name="tbl_dem_reciboDocumentoFiscal", schema="administracion")

public class ReciboDocumentoFiscal implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Recibo recibo;
	private DocumentoFiscal documentoFiscal;
	private Double  monto;
	
	@SequenceGenerator(name="SeqReciboDocumentoFiscal", sequenceName="administracion.tbl_dem_documentofiscal_recibo_documentoFiscal_seq", allocationSize=1)
	@Id 
	@GeneratedValue(generator="SeqReciboDocumentoFiscal")
	@Column(name="seq_idDocumento")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
 
	@ManyToOne
	@JoinColumn(name="int_idRecibo")
	public Recibo getRecibo() {
		return recibo;
	}
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
	 
	@ManyToOne
	@JoinColumn(name="int_idDocumentoFiscal")
	public DocumentoFiscal getDocumentoFiscal() {
		return documentoFiscal;
	}
	public void setDocumentoFiscal(DocumentoFiscal documentoFiscal) {
		this.documentoFiscal = documentoFiscal;
	}

	@Column
	public Double getMonto() {
		return monto;
	}

	
	public void setMonto(Double monto) {
		this.monto = monto;
	}

	@Transient
	public String getStrMonto()
	{   //DecimalFormat format = new DecimalFormat("###,###,###,###,###.00");
		//return format.format(monto);
		return Formateador.formatearMoneda(Math.abs(monto));
		
	} 
}
