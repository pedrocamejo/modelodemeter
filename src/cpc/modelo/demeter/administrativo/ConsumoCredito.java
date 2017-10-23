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

import cpc.modelo.demeter.basico.TipoConsumo;
import cpc.modelo.sigesp.basico.Sede;

@Audited @Entity
@Table(name="tbl_dem_consumocredito", schema="administracion")
public class ConsumoCredito implements Serializable{
	
	private static final long serialVersionUID = -3535261271594438079L;
	private Integer				id;
	private NotaCredito			nota;
	private DocumentoFiscal		documento;
	private String 				concepto;
	private Date				fecha;
	private TipoConsumo			tipoConsumo;
	private String 				control;
	private double				monto;
	private Sede				sede;
	private boolean				anulado;
	
	public ConsumoCredito() {
		super();
	}
	
	public ConsumoCredito(int id, DocumentoFiscal documento, Date fecha,
			String control, double monto) {
		super();
		this.id = id;
		this.documento = documento;
		this.fecha = fecha;
		this.control = control;
		this.monto = monto;
	}
	
	@SequenceGenerator(name="SeqConsumoCredito", sequenceName="administracion.tbl_dem_consumocredito_seq_idconcre_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqConsumoCredito")
	@Column(name="seq_idconcre")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idnota")
	public NotaCredito getNota() {
		return nota;
	}

	public void setNota(NotaCredito nota) {
		this.nota = nota;
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
	public String getStrMonto() {
		return String.format("%.2f", monto);
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

	@Column(name="str_concepto")
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
	
	@ManyToOne(optional=true)
	@JoinColumn(name="int_iddocumento")
	public DocumentoFiscal getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoFiscal documento) {
		this.documento = documento;
	}
	
	
	@ManyToOne
	@JoinColumn(name="int_idtipcon")
	public TipoConsumo getTipoConsumo() {
		return tipoConsumo;
	}

	public void setTipoConsumo(TipoConsumo tipoConsumo) {
		this.tipoConsumo = tipoConsumo;
	}
	
	@Transient
	public String getStrNota(){
		if (nota!= null)
			return nota.getStrNroDocumento();
		else
			return "";
	}
	

	@Transient
	public String getStrFactura(){
		if (documento!= null)
			return documento.getStrNroDocumento();
		else
			return "";
	}
	
	@Transient
	public String getStrPagador(){
		return nota.getBeneficiario().getNombres();
	
	}
	
	@Transient
	public double getAbonado(){
		if (documento != null)
			return documento.getMontoSaldo() > monto ? monto : documento.getMontoSaldo();
		else
			return 0;	
	}

	@Transient
	public String getStrTipoConsumo(){
		return tipoConsumo.toString();
	}
	
	@Transient
	public String getStrfecha(){
		return String.format("%1$te/%1$tm/%1$tY", fecha);
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
		ConsumoCredito cuenta = (ConsumoCredito) objeto;
		if (cuenta.getId().equals(id))
			return true;
		else
			return false;
	}  
	
}
