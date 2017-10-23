package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited @Entity

@Table(name = "tbl_dem_tipoaprobaciondebitointerno", schema = "administracion")
public class TipoAprobacionDebitoInterno implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -8807856558977051311L;
Integer id;
String descripcion;

@Column(name = "seq_id")
@SequenceGenerator(name = "AprobacionDebitoInterno_Gen", sequenceName = "administracion.tbl_dem_aprobaciondebitointerno_seq_id_seq", allocationSize = 1)
@Id
@GeneratedValue(generator = "AprobacionDebitoInterno_Gen")
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
@Column(name="str_descripcion",length=255)
public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

@Override
public String toString() {
	return getDescripcion();
}


}
