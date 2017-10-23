package cpc.modelo.demeter.administrativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import cpc.modelo.demeter.mantenimiento.DevolucionArticulo;
@Audited
@Entity
@Table(name = "tbl_dem_detallecontratodevolucionarticulo", schema = "administracion")
public class DetalleContratoDevolucionArticulo {
 private Integer id;
 private DevolucionArticulo devolucionArticulo;
 private ContratoServicioTecnico contratoServicioTecnico;
 
 
	@SequenceGenerator(name="SeqDetalleContratoDevolucionArticulo", sequenceName="administracion.tbl_dem_detallecontratodevolucionar_seq_iddetalledevolucion_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqDetalleContratoDevolucionArticulo")
	@Column(name="seq_iddetalledevolucion")
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@OneToOne
@JoinColumn(name="int_iddevolucionarticulo")
public DevolucionArticulo getDevolucionArticulo() {
	return devolucionArticulo;
}
public void setDevolucionArticulo(DevolucionArticulo devolucionArticulo) {
	this.devolucionArticulo = devolucionArticulo;
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
public String getStrNroDocumento() {
	return getDevolucionArticulo().getStrNroDocumento();
}
	
}
