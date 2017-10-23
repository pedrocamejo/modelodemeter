package cpc.modelo.demeter.administrativo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;



@Audited
@Entity
@Table(name="tbl_dem_tipo_cotizacion", schema="administracion")
public class TipoCotizacion {
	
	public static Integer  MANTENIMIENTO = 1;
	public static Integer  VIALIDAD = 2;
	public static Integer  EQUIPO = 3;
	public static Integer  TRANSPORTE = 4;
	
	
	private Integer    id;
	private String     descripcion;
	
	@Id
	@SequenceGenerator(name="TipoCotizaacion_gen", sequenceName="administracion.tbl_dem_tipoCotizacion_seq",  allocationSize=1 )
	@GeneratedValue( generator="TipoCotizaacion_gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
