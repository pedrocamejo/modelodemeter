package cpc.modelo.demeter.administrativo;

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

@Audited @Entity
@Table(name = "tbl_dem_detalleexoneracioncontrato", schema = "administracion")
public class DetalleExoneracionContrato {
private Integer id;
private String Producto;
private String tipoProducto;
private Double cantidad;
private Double cantidadReal;
private Double precioUnitario;
private Double subtotal;
private AprobacionExoneracionContrato aprobacion;

@SequenceGenerator(name="seqdetalleexoneracionContrato", sequenceName="administracion.tbl_dem_detalleexoneracioncontrato_seq_iddetalle_seq", allocationSize=1)
@Id @GeneratedValue(generator="seqdetalleexoneracionContrato")
@Column(name="seq_iddetalle")
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@Column(name="str_producto")
public String getProducto() {
	return Producto;
}
public void setProducto(String producto) {
	Producto = producto;
}
@Column(name="dbl_cantidad")

public Double getCantidad() {
	return cantidad;
}
public void setCantidad(Double cantidad) {
	this.cantidad = cantidad;
}
@Column(name="dbl_cantidadreal")
public Double getCantidadReal() {
	return cantidadReal;
}
public void setCantidadReal(Double cantidadReal) {
	this.cantidadReal = cantidadReal;
}
@Column(name="dbl_preciounitario")
public Double getPrecioUnitario() {
	return precioUnitario;
}
public void setPrecioUnitario(Double precioUnitario) {
	this.precioUnitario = precioUnitario;
}
@Column(name="dbl_subtotal")
public Double getSubtotal() {
	return subtotal;
}
public void setSubtotal(Double subtotal) {
	this.subtotal = subtotal;
}

@Column(name="str_tipoproducto")
public String getTipoProducto() {
	return tipoProducto;
}
public void setTipoProducto(String tipoProducto) {
	this.tipoProducto = tipoProducto;
}
@ManyToOne
@JoinColumn(name="int_idaprobacion")
public AprobacionExoneracionContrato getAprobacion() {
	return aprobacion;
}
public void setAprobacion(AprobacionExoneracionContrato exoneracion) {
	this.aprobacion = exoneracion;
}

@Transient
public String getStrCantidad() {
	return Formateador.formatearMoneda(Math.abs(cantidad));
}
@Transient
public String getStrCantidadReal() {
	if (cantidadReal!=null)
	return Formateador.formatearMoneda(Math.abs(cantidadReal));
	else return"";
}
@Transient
public String getStrSubtotal() {
	return Formateador.formatearMoneda(Math.abs(subtotal));
}

@Transient
public String getStrPrecioUnitario() {
	return Formateador.formatearMoneda(Math.abs(subtotal));
}

}
