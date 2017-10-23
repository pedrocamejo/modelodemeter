package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import cpc.modelo.demeter.gestion.TipoMovimiento;
import cpc.modelo.sigesp.basico.Sede;
import cva.pc.demeter.utilidades.Formateador;
@Audited
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="tbl_dem_movimiento_articulo",schema="mantenimiento")
public class MovimientoArticulo implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1430074265149698736L;
private Integer id;
private String  usuario;
private String  codigomovimiento;
private Date    					fecha;
private boolean estado;
private TipoMovimientoArticulo tipoMovimiento ;
//private List<DetalleArticuloMovimiento>  detallesmoviento;
private List<DetalleEntradaArticulo>  detalleEntradaArticulos;
private List<DetalleSalidaExternaArticulo>  detalleSalidaExternaArticulos;
private List<DetalleSalidaInternaArticulo>  detalleSalidaInternaArticulos;
private List<DetalleTransferenciaArticulo>  detalleTransferenciaArticulos;
private List<DetalleDevolucionArticulo>  detalleDevolucionArticulos;
private Integer					numerocontrol;
private Sede				sede;

@SequenceGenerator(name="Seq_idmovimientoarticulo", sequenceName="mantenimiento.tbl_dem_movimiento_articulo_seq_idmovimientoarticulo_seq", allocationSize=1)
@Column(name="seq_idmovimientoarticulo")
@Id @GeneratedValue(generator="Seq_idmovimientoarticulo")
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@Column(name="str_usuario")
public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}
@Temporal(TemporalType.DATE)
@Column(name="dtm_fecha")
public Date getFecha() {
	return fecha;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
@Column(name="bol_activo")
public boolean getEstado() {
	return estado;
}
public void setEstado(boolean estado) {
	this.estado = estado;
}
@OneToOne
@JoinColumn(name="int_tipomovimiento")
public TipoMovimientoArticulo getTipoMovimiento() {
	return tipoMovimiento;
}
public void setTipoMovimiento(TipoMovimientoArticulo tipoMovimiento) {
	this.tipoMovimiento = tipoMovimiento;
}
/*
@OneToMany(mappedBy="movimiento",targetEntity=DetalleArticuloMovimiento.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
public List<DetalleArticuloMovimiento> getDetallesmoviento() {
	return detallesmoviento;
}
public void setDetallesmoviento(List<DetalleArticuloMovimiento> detallesmoviento) {
	this.detallesmoviento = detallesmoviento;
}
*/







@Column(name="str_codigomovimiento")
public String getCodigoMovimiento() {
	return codigomovimiento;
}
public void setCodigoMovimiento(String codigomivimiento) {
	this.codigomovimiento = codigomivimiento;
}

@OneToMany(mappedBy="movimiento",targetEntity=DetalleArticuloMovimiento.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
public List<DetalleEntradaArticulo> getDetalleEntradaArticulos() {
	return detalleEntradaArticulos;
}
public void setDetalleEntradaArticulos(
		List<DetalleEntradaArticulo> detalleEntradaArticulos) {
	this.detalleEntradaArticulos = detalleEntradaArticulos;
}
@OneToMany(mappedBy="movimiento",targetEntity=DetalleArticuloMovimiento.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
public List<DetalleSalidaExternaArticulo> getDetalleSalidaExternaArticulos() {
	return detalleSalidaExternaArticulos;
}
public void setDetalleSalidaExternaArticulos(
		List<DetalleSalidaExternaArticulo> detalleSalidaExternaArticulos) {
	this.detalleSalidaExternaArticulos = detalleSalidaExternaArticulos;
}
@OneToMany(mappedBy="movimiento",targetEntity=DetalleArticuloMovimiento.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
public List<DetalleSalidaInternaArticulo> getDetalleSalidaInternaArticulos() {
	return detalleSalidaInternaArticulos;
}
public void setDetalleSalidaInternaArticulos(
		List<DetalleSalidaInternaArticulo> detalleSalidaInternaArticulos) {
	this.detalleSalidaInternaArticulos = detalleSalidaInternaArticulos;
}
@OneToMany(mappedBy="movimiento",targetEntity=DetalleArticuloMovimiento.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
public List<DetalleTransferenciaArticulo> getDetalleTransferenciaArticulos() {
	return detalleTransferenciaArticulos;
}
public void setDetalleTransferenciaArticulos(
		List<DetalleTransferenciaArticulo> detalleTransferenciaArticulos) {
	this.detalleTransferenciaArticulos = detalleTransferenciaArticulos;
}
@OneToMany(mappedBy="movimiento",targetEntity=DetalleArticuloMovimiento.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
public List<DetalleDevolucionArticulo> getDetalleDevolucionArticulos() {
	return detalleDevolucionArticulos;
}
public void setDetalleDevolucionArticulos(
		List<DetalleDevolucionArticulo> detalleDevolucionArticulos) {
	this.detalleDevolucionArticulos = detalleDevolucionArticulos;
}


@Column(name="int_numerocontrol")
public Integer getNumerocontrol() {
	return numerocontrol;
}
public void setNumerocontrol(Integer numerocontrol) {
	this.numerocontrol = numerocontrol;
}

@Transient
public String getStrNroDocumento() {
	return codigomovimiento+ Formateador.rellenarNumero(numerocontrol,"00000000");
}

@Override
public String toString() {
	return getStrNroDocumento();
}




@ManyToOne
@JoinColumns({
	@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
	@JoinColumn(name="str_idsede",referencedColumnName="codubifis")
})
public Sede getSede() {
	return sede;
}
public void setSede(Sede sede) {
	this.sede = sede;
}



}
