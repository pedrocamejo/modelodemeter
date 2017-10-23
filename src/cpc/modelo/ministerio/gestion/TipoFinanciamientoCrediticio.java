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
@Table(name ="tbl_dem_tipofinanciamiento", schema = "ministerio")
public class TipoFinanciamientoCrediticio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4629138375323809845L;
	private Integer						id;
	private String 						denotacion;
	private List<InstitucionCrediticia>	instituciones;
	
	
	@Id
	@Column(name="seq_idtipofinanciamiento")
	@SequenceGenerator(name="seqTipoFinanciamiento", sequenceName="ministerio.tbl_dem_tipofinanciamiento_seq_idtipofinanciamiento_seq", allocationSize=1)
	@GeneratedValue(generator="seqTipoFinanciamiento")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDenotacion() {
		return denotacion;
	}
	public void setDenotacion(String denotacion) {
		this.denotacion = denotacion;
	}
	
	@OneToMany(mappedBy="tipoFinanciamiento", targetEntity=InstitucionCrediticia.class)
	public List<InstitucionCrediticia> getInstituciones() {
		return instituciones;
	}
	public void setInstituciones(List<InstitucionCrediticia> instituciones) {
		this.instituciones = instituciones;
	}

	public String toString(){
		return getDenotacion();
	}
	
	public boolean equals(Object objeto){
		try{
			TipoFinanciamientoCrediticio cuenta = (TipoFinanciamientoCrediticio) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
