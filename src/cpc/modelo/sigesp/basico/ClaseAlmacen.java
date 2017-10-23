package cpc.modelo.sigesp.basico;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Audited @Entity
@Table(name = "tbl_dem_clase_almacen", schema = "sigesp")
public class ClaseAlmacen {
	private Integer		id;
	private String		nombreClase, descripcion;

	
	@Column(name="seq_idclasealmacen")
	@SequenceGenerator(name="ClaseAlmacen_Gen", sequenceName="sigesp.tbl_dem_clase_almacen_pkey",  allocationSize=1 )
	@Id	@GeneratedValue( generator="ClaseAlmacen_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="str_nombre")
	public String getNombreClase() {
		return nombreClase;
	}
	public void setNombreClase(String nombreClase) {
		this.nombreClase = nombreClase;
	}
	@Column(name="str_descripcion")
	public String getdescripcion() {
		return descripcion;
	}
	public void setdescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
