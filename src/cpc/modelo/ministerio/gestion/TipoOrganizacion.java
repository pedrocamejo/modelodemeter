package cpc.modelo.ministerio.gestion;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_tipoorganizacion", schema="ministerio")
public class TipoOrganizacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4901672013057739898L;
	private Integer				id;
	private String				denotacion;
	private List<Organizacion>	organizaciones;
	
	@Id
	@Column(name="seq_idtipoorganizacion")
	@SequenceGenerator(name="SeqTipoOrganizacion", sequenceName="ministerio.tbl_dem_tipoorganizacion_seq_idtipoorganizacion_seq")
	@GeneratedValue(generator="SeqTipoOrganizacion")
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
	
	@OneToMany(mappedBy="tipoOrganizacion", targetEntity=Organizacion.class)
	public List<Organizacion> getOrganizaciones() {
		return organizaciones;
	}
	public void setOrganizaciones(List<Organizacion> organizaciones) {
		this.organizaciones = organizaciones;
	}
	
	public String toString(){
		return getDenotacion();
	}
	
	public boolean equals(Object objeto){
		try{
			TipoOrganizacion cuenta = (TipoOrganizacion) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
