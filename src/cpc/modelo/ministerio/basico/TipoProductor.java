package cpc.modelo.ministerio.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_tipo_clientes")
public class TipoProductor implements Serializable, Comparable<TipoProductor>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3628008198981227797L;
	private Integer 	id;
	private String		descripcion;
	private boolean		juridico;
	
	
	
	@Id 
	@SequenceGenerator(name="tipoProductor_Gen", sequenceName="tbl_dem_tipoclientes_seq_idtipocliente_seq",  allocationSize=1 )
	@GeneratedValue( generator="tipoProductor_Gen")
	@Column(name="seq_idtipocliente", unique=true, nullable=false)
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
	
	@Column(name="bol_juridico")
	public boolean isJuridico() {
		return juridico;
	}
	public void setJuridico(boolean juridico) {
		this.juridico = juridico;
	}
	
	public String toString(){
		return descripcion;
	}
	
	@Transient
	public String getStrJuridico() {
		return juridico ? "Juridico" : "Natural";
	}
	
	public int compareTo(TipoProductor o) {
		return o.getId()-this.getId();
	}
	
	public boolean equals(Object obj){ 
		if (obj== this)
			return true;
		if (!(obj instanceof TipoProductor)) 
			return false;
		TipoProductor objeto = (TipoProductor) obj;
		return true && objeto.getId().equals(this.getId()); 
	}
	
}
