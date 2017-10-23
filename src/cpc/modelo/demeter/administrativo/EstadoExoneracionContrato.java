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
@Table(name="tbl_dem_estado_exoneracion_contrato", schema="administracion")
public class EstadoExoneracionContrato  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1450593165576789208L;
	private Integer id;
	private String descripcion;
	private boolean gravado;
	
	public EstadoExoneracionContrato() {
		super();
	}
	
	public EstadoExoneracionContrato(Integer id,String descripcion,boolean gravado) {
		// TODO Auto-generated constructor stub
		super();
		this.id=id;
		this.descripcion=descripcion;
		this.gravado=gravado;
		
	}
	@SequenceGenerator(name="seq_idestadoexoneracioncontrato", sequenceName="", allocationSize=1)
	@Column(name="seq_idestadoexoneracioncontrato")
	@Id @GeneratedValue(generator="seq_idestadoexoneracioncontrato")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Column(name="bol_gravado")
	public boolean isGravado() {
		return gravado;
	}
	public void setGravado(boolean gravado) {
		this.gravado = gravado;
	}
	
}
