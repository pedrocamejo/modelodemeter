package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@PrimaryKeyJoinColumn(name="int_iddocumento")
@Table(name="tbl_dem_nota", schema="administracion")
public class NotaCredito extends DocumentoFiscal implements Serializable {

	private static final long serialVersionUID = 2491147471048451702L;
	private DocumentoFiscal		factura;
	private List<NotaPago>		pagos;		

	public NotaCredito(){
		super();
	}
		
	
	public NotaCredito(String serie, Integer nroControl, Integer nroDocumento,Date fecha, Integer idestado, String estadoDesc,String beneficiario,
					Double montoBase, Double montoDescuento,Double montoTotal, Double montoSaldo, Double porcDescuento, String observacion, DocumentoFiscal factura) {
		super(serie, nroControl, nroDocumento, fecha, idestado, estadoDesc,beneficiario, montoBase,
				montoDescuento, montoTotal, montoSaldo, porcDescuento, observacion);
		this.factura = factura;
	}


	@ManyToOne (cascade=CascadeType.ALL,  targetEntity=DocumentoFiscal.class)	
	@JoinColumn(name="int_idfactura" )
	public DocumentoFiscal getFactura() {
		return factura;
	}

	public void setFactura(DocumentoFiscal factura) {
		this.factura = factura;
	}

	@Transient
	public String getStrFacturaAfectada(){
		return factura.getStrNroDocumento();
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="nota", targetEntity=NotaPago.class)
	public List<NotaPago> getPagos() {
		return pagos;
	}

	public void setPagos(List<NotaPago> pagos) {
		this.pagos = pagos;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof NotaCredito)) {
			return false;
		}
		NotaCredito other = (NotaCredito) o;
		return true && other.getId().equals(this.getId());
	}

}
