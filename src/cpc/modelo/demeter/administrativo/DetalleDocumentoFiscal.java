package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.Producto;
import cpc.modelo.demeter.interfaz.IProducto;


@Audited @Entity
@Table(name="tbl_dem_documentofiscaldetalle", schema="administracion")
@PrimaryKeyJoinColumn(name="int_iddocumento")
public class DetalleDocumentoFiscal implements Serializable{
	
	
	private static final long serialVersionUID = -8706821531293955523L;
	private	 Long 				id;
	private double 				cantidad;
	private IProducto			servicio;
	private Impuesto			alicuota;
	private double 				precioUnitario;
	private DocumentoFiscal		documento;
	private String 				complementoDescripcion;
	private double 				precio;
	
	
	public DetalleDocumentoFiscal() {
		super();
	}
	
	public DetalleDocumentoFiscal(long id, double cantidad,
			Labor servicio, Impuesto alicuota, double precioUnitario) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.servicio = servicio;
		this.alicuota = alicuota;
		this.precioUnitario = precioUnitario;
	}
	
	@SequenceGenerator(name="SeqDetalleFactura", sequenceName="administracion.tbl_dem_documentofiscaldetalle_seq_iddetalle_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqDetalleFactura")
	@Column(name="seq_iddetalle")
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="dbl_cantidad")
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	@ManyToOne(targetEntity=Producto.class)
	@JoinColumn(name="int_idservicio")
	public IProducto getServicio() {
		return servicio;
	}
	public void setServicio(IProducto servicio) {
		this.servicio = servicio;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtipoimpuesto" )
	public Impuesto getAlicuota() {
		return alicuota;
	} 
	public void setAlicuota(Impuesto alicuota) {
		this.alicuota = alicuota;
	}
	
	@Column(name="dbl_preciounitario")
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	@ManyToOne
	@JoinColumn(name="int_iddocumento")
	@Basic(fetch=FetchType.EAGER)
	public DocumentoFiscal getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoFiscal documento) {
		this.documento = documento;
	}

	@Column(name="str_comdes")
	public String getComplementoDescripcion() {
		return complementoDescripcion;
	}

	public void setComplementoDescripcion(String complementoDescripcion) {
		this.complementoDescripcion = complementoDescripcion;
	}

	@Transient
	public double getPrecio(){

		precio = precioUnitario* cantidad;
		return precio;
	}
	public void setPrecio(){
		precio = precioUnitario* cantidad ;
	}
	
	@Transient
	public String getExcento(){
		return (alicuota.getPorcentaje() ==0.0) ?"(E)" : "";
	}
	
	@Transient
	public  String getAlicuotaNombre() {
		return getAlicuota().toString();
	}
	
	
	public boolean equals(Object objeto){
		try{
			DetalleDocumentoFiscal cuenta = (DetalleDocumentoFiscal) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
}
