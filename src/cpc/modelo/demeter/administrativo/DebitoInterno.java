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
@Table(name="tbl_dem_debitointerno", schema="administracion")
public class DebitoInterno implements Serializable{
	
	private static final long 	serialVersionUID = -3574608157260412533L;
	private Integer				id;
	private DocumentoFiscal		documento;
	private Date				fecha;
	private String				concepto;
	private String 				control;
	private double				monto;
	private Sede				sede;
	private boolean				anulado;
	
	public DebitoInterno() {
		super();
	}
	
	public DebitoInterno(int id, DocumentoFiscal documento, Date fecha,
			String control, double monto) {
		super();
		this.id = id;
		this.documento = documento;
		this.fecha = fecha;
		this.control = control;
		this.monto = monto;
	}
	
	@SequenceGenerator(name="SeqDebitoInterno", sequenceName="administracion.tbl_dem_debitointerno_seq_iddebito_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqDebitoInterno")
	@Column(name="seq_iddebito")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_iddocumento")
	public DocumentoFiscal getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoFiscal documento) {
		this.documento = documento;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="str_control")
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	
	@Column(name="dbl_monto")
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	@Transient
	public String getStrFactura(){
		return documento.getStrNroDocumento();
	}
	
	@Transient
	public String getStrPagador(){
		return documento.getBeneficiario().getNombres();
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

	@Column(name="str_concepto",columnDefinition="TEXT")
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
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
	
	@Transient
	public double getSaldoFactura(){
		return documento.getMontoSaldo();
	}

	public boolean equals(Object objeto){
		try{
			DebitoInterno cuenta = (DebitoInterno) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
	

}
