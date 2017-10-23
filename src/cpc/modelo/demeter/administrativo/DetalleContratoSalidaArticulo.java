package cpc.modelo.demeter.administrativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import cpc.modelo.demeter.mantenimiento.SalidaExternaArticulo;
@Audited
@Entity
@Table(name="tbl_dem_detallecontratosalidaarticulo", schema="administracion")
@PrimaryKeyJoinColumn(name="int_idcontrato")
public class DetalleContratoSalidaArticulo {
private Integer id;
private SalidaExternaArticulo salidaExternaArticulo;
private ContratoServicioTecnico contratoServicioTecnico;
@SequenceGenerator(name="SeqDetalleContratoSalidaArticulo", sequenceName="administracion.tbl_dem_detallecontratosalidaarticulo_seq_iddetallesalida_seq", allocationSize=1)
@Id @GeneratedValue(generator="SeqDetalleContratoSalidaArticulo")
@Column(name="seq_iddetallesalida")
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@OneToOne
@JoinColumn(name="int_idsalidaexternaarticulo")
public SalidaExternaArticulo getSalidaExternaArticulo() {
	return salidaExternaArticulo;
}
public void setSalidaExternaArticulo(SalidaExternaArticulo salidaExternaArticulo) {
	this.salidaExternaArticulo = salidaExternaArticulo;
}

@ManyToOne
@JoinColumn(name="int_idcontratoserviciotecnico")
public ContratoServicioTecnico getContratoServicioTecnico() {
	return contratoServicioTecnico;
}
public void setContratoServicioTecnico(
		ContratoServicioTecnico contratoServicioTecnico) {
	this.contratoServicioTecnico = contratoServicioTecnico;
}
@Transient
public String getUsuario(){
	return salidaExternaArticulo.getUsuario();
}
@Transient
public String getDestinatario(){
	return salidaExternaArticulo.getDestinatario().getNombreCliente();
}
@Transient
public String getFecha(){
	return salidaExternaArticulo.getFecha().toGMTString();
}
}
