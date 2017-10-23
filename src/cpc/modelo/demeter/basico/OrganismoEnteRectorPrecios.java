package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tipo_precio")
public class OrganismoEnteRectorPrecios implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6162908483437825453L;
	private Integer		id;
	private String 		nombre;

	
	public OrganismoEnteRectorPrecios() {
		super();
	
	}
	
	public OrganismoEnteRectorPrecios(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	@SequenceGenerator(name="seqOrgRecPrecio", sequenceName="seq_tbl_dem_tipo_precio", allocationSize=1)
	@Column(name="seq_idtipoprecio")
	@Id @GeneratedValue(generator="seqOrgRecPrecio")
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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {		
		return this.nombre;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof OrganismoEnteRectorPrecios)) {
			return false;
		}
		OrganismoEnteRectorPrecios other = (OrganismoEnteRectorPrecios) o;
		return true && other.getId().equals(this.getId());
	}
}
