package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import cva.pc.demeter.utilidades.Formateador;

@Audited @Entity
@Table(name="tbl_dem_libroventa", schema="administracion")
public class LibroVenta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6999601230806708367L;
	public final static String[] 	MESES= {"Enero","Febrero","Marzo", "Abril","Mayo","Junio","Julio", "Agosto","Septiembre","Octubre","Noviembre", "Diciembre"};
	private Integer 				id;
	private int					ano;
	private int					mes;
	private Double					montoBase;
	private Double					montoTotal;
	private Integer					cantidadDocumentos;
	private List<LibroVentaDetalle> detalles;
	private boolean                declarado;

	@SequenceGenerator(name="SeqLibro", sequenceName="administracion.tbl_dem_libroventa_seq_idlibvent_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqLibro")
	@Column(name="seq_idlibvent")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="ano")
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	@Column(name="mes")
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	@Column(name="dbl_montobase")
	public Double getMontoBase() {
		return montoBase;
	}
	public void setMontoBase(Double montoBase) {
		this.montoBase = montoBase;
	}
	
	@Column(name="dbl_montototal")
	public Double getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	@OneToMany(mappedBy="libro", targetEntity=LibroVentaDetalle.class, cascade = CascadeType.ALL)
	public List<LibroVentaDetalle> getDetalles() {
		return detalles;
	}
	
	public void setDetalles(List<LibroVentaDetalle> detalles) {
		this.detalles = detalles;
	}
	
	@Column(name="int_cantdocu")
	public Integer getCantidadDocumentos() {
		return cantidadDocumentos;
	}
	public void setCantidadDocumentos(Integer cantidadDocumentos) {
		this.cantidadDocumentos = cantidadDocumentos;
	}
	
	@Transient
	public String getStrMes(){
		return MESES[mes-1];
	}

	@Transient
	public String getStrCantidadDocumentos(){
		return Formateador.formatearMoneda(cantidadDocumentos);
	}
	
	@Transient
	public String getStrMontoBase(){
		return Formateador.formatearMoneda(montoBase);
	}
	
	@Transient
	public String getStrMontoTotal(){
		return Formateador.formatearMoneda(montoTotal);
	}
	
	@Column(name="bol_declarado")
	public boolean isDeclarado() {
		return declarado;
	}
	public void setDeclarado(boolean declarado) {
		this.declarado = declarado;
	}
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof LibroVenta)) {
			return false;
		}
		LibroVenta other = (LibroVenta) o;
		return true && other.getId().equals(this.getId());
	}
	
}
