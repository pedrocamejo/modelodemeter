package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.Sede;



import cva.pc.demeter.utilidades.Fecha;
@Audited @Entity
@Table(name="tbl_dem_notapago", schema="administracion")
public class NotaPago implements Serializable{
	
	private static final long 	serialVersionUID = -3574608157260412533L;
	private Long				id;
	private NotaCredito			nota;
	private DocumentoFiscal		factura;
	private Date				fecha;
	private double				monto;
	private Sede				sede;
	private boolean				anulado;
	
	public NotaPago() {
		super();
	}
	
	public NotaPago(long id, DocumentoFiscal documento, Date fecha,
			String control, double monto) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.monto = monto;
	}
	
	@SequenceGenerator(name="SeqPagoNota", sequenceName="administracion.tbl_dem_notapago_seq_idpago_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqPagoNota")
	@Column(name="seq_idpago")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

	
	@Column(name="dbl_monto")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Transient
	public String getStrfecha(){
		return Fecha.obtenerFecha(fecha);
	}

	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp", insertable=false, updatable=false),
	    @JoinColumn(name="str_sede", referencedColumnName="codubifis", insertable=false, updatable=false),
	})
	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	@Column(name="bol_anulado")
	public boolean isAnulado() {
		return anulado;
	}

	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}

	@Transient
	public String getEstado() {
		return anulado? "Anulado": "Activo";
	}

	
	@ManyToOne
	@JoinColumn(name="int_idnota")
	public NotaCredito getNota() {
		return nota;
	}

	public void setNota(NotaCredito nota) {
		this.nota = nota;
	}


	@ManyToOne
	@JoinColumn(name="int_idfactura")
	public DocumentoFiscal getFactura() {
		return factura;
	}

	public void setFactura(DocumentoFiscal factura) {
		this.factura = factura;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof NotaPago)) {
			return false;
		}
		NotaPago other = (NotaPago) o;
		return true && other.getId().equals(this.getId());
	}
}
