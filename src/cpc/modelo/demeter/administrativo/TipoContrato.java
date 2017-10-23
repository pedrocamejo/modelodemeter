package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipocontrato", schema="administracion")
public class TipoContrato implements Serializable{
	
	private static final long serialVersionUID = -8638323658589147982L;
	private Integer	id;
	private String	nombre;
	private String	clausula;
	
	public TipoContrato() {
		super();
	}
	
	
	public TipoContrato(int id, String nombre, String clausula) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.clausula = clausula;
	}
	
	@SequenceGenerator(name="Seq", sequenceName="administracion.tbl_dem_tipocontrato_seq_idtipocontrato_seq", allocationSize=1)
	@Id @GeneratedValue(generator="Seq")
	@Column(name="seq_idtipocontrato")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String descripcion) {
		this.nombre = descripcion;
	}
	
	@Column(name="str_clausula")
	public String getClausula() {
		return clausula;
	}
	public void setClausula(String clausula) {
		this.clausula = clausula;
	}
	@Override
	public String toString() {		
		return this.nombre;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TipoContrato)) {
			return false;
		}
		TipoContrato other = (TipoContrato) o;
		return true && other.getId().equals(this.getId());
	}

}
