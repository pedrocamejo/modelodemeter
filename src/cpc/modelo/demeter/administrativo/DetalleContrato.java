package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Producto;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.gestion.Cliente;

@Audited @Entity
@Table(name = "tbl_dem_detallecontrato", schema = "administracion")
public class DetalleContrato implements Serializable {

	private static final long serialVersionUID = -2659606196134265762L;
	private Long 				id;
	private Contrato 			contrato;
	private IProducto 			producto;
	private Impuesto			impuesto;
	private UnidadMedida		unidadCobro;
	private Double				cantidad;
	private Double				precioUnidad;
	private Double				subtotal; 
	private Boolean				prestado;
	private Double				cantidadReal;
	
	public DetalleContrato() {
		super();
	}

	@SequenceGenerator(name="SeqDetalleContrato", sequenceName="administracion.tbl_dem_detallecontrato_seq_iddetalle_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqDetalleContrato")
	@Column(name="seq_iddetalle")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="int_idcontrato")
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	/*
	@Transient
	@JoinColumn(name="int_idcontrato",insertable=false,updatable=false)
	public String  getbeneficiado() {
		return  new PerContratoMecanizado().getEnriqueser(this.contrato).getNombreCliente();
	}
	public void setContratoMecanizado(ContratoMecanizado contratomecanizado) {
		this.contrato = contratomecanizado;
	}*/
	
	
	@ManyToOne(targetEntity=Producto.class,fetch=FetchType.EAGER)
	@JoinColumn(name="int_idproducto")
	public IProducto getProducto() {
		return producto;
	}
	public void setProducto(IProducto servicio) {
		this.producto = servicio;
	}
	@ManyToOne(targetEntity=Impuesto.class,fetch=FetchType.EAGER)
	@JoinColumn(name="int_idtipoimpuesto")
	public Impuesto getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}

	@OneToOne
	@JoinColumn(name="int_idumedidacobro")
	public UnidadMedida getUnidadCobro() {
		return unidadCobro;
	}
	public void setUnidadCobro(UnidadMedida unidadCobro) {
		this.unidadCobro = unidadCobro;
	}

	@Column(name="dbl_cantidad")
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name="dbl_preciounidad")
	public Double getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(Double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	@Column(name="dbl_subtotal")
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	
	@Column(name="bol_prestado")
	public Boolean getPrestado() {
		return prestado;
	}
	public void setPrestado(Boolean prestado) {
		this.prestado = prestado;
	}
	
	@Column(name="dbl_cantidadreal")
	public Double getCantidadReal() {
		return cantidadReal;
	}
	public void setCantidadReal(Double cantidadReal) {
		this.cantidadReal = cantidadReal;
	}  
	
	@Transient
	public String getSubTotal() {
		return ""+subtotal;
	}
	
	@Transient
	public String getStrProducto() {
		return producto.getDescripcion();
	}

	public boolean equals(Object objeto){
		try{
			DetalleContrato cuenta = (DetalleContrato) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	
	@Transient
	public String getFechaString() {		
		return contrato.getFechaString();
	}

	
	@Transient
	public String getNombreCliente(){
		if (contrato.getPagador() == null)
		return "";
		//if (contrato.getPagador().getNombres() == contratomecanizado.getNombreCliente())
			//return contratomecanizado.getNombreCliente();
		return contrato.getPagador().getNombres();
		//return  new PerContratoMecanizado().getEnriqueser(ctto.getContrato()).getNombreCliente();
	}
	
	@Transient
	public void setNombreCliente(Cliente pagador){
		this.contrato.setPagador(pagador);
	}
	
	@Transient
	public String getEstadoString(){
		return contrato.getEstadoString();
	}

	@Transient
	public String getStrNroDocumento() {
		return contrato.getStrNroDocumento();
	}
	
	@Transient
	public Double getMontoTotal() {
		return contrato.getMonto();
	}
	
	@Transient
	public String toString(){

if (contrato!=null)
		return getStrNroDocumento();
else return "";
	}
	
}