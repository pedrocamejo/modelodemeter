package cpc.modelo.ministerio.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_organizacion", schema ="ministerio")
public class Organizacion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7631214038966770047L;
	private Integer				id;
	private String				denotacion;
	private TipoOrganizacion	tipoOrganizacion;
	
	
	@Id
	@Column(name="seq_idorganizacion")
	@SequenceGenerator(name="SeqOrganizacion", sequenceName="ministerio.tbl_dem_organizacion_seq_idorganizacion_seq", allocationSize=1)
	@GeneratedValue(generator="SeqOrganizacion")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_nombre")
	public String getDenotacion() {
		return denotacion;
	}
	public void setDenotacion(String denotacion) {
		this.denotacion = denotacion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipoorganizacion")
	public TipoOrganizacion getTipoOrganizacion() {
		return tipoOrganizacion;
	}
	public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
		this.tipoOrganizacion = tipoOrganizacion;
	}
	
	public String toString(){
		return getDenotacion();
	}
	
	@Transient
	public String getStrTipoOrganizacion() {
		if (tipoOrganizacion == null)
			return null;
		else
			return tipoOrganizacion.getDenotacion();
	}
	
	public boolean equals(Object objeto){
		try{
			Organizacion cuenta = (Organizacion) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
