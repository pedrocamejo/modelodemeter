package cpc.modelo.demeter.basico;

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
@Table(name="tbl_dem_clasearticulo", schema="basico")
public class ClaseArticulo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6813830724788690288L;
	private Integer			id;
	private String			descripcion;
	private TipoArticulo	tipoArticulo;
	
	@Id
	@Column(name="seq_idclasearticulo")
	@SequenceGenerator(name="GenClaseArticulo", sequenceName="basico.tbl_dem_clasearticulo_seq_idclasearticulo_seq", allocationSize=1)
	@GeneratedValue(generator="GenClaseArticulo")
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
	
	@ManyToOne
	@JoinColumn(name="int_idtipoarticulo")
	public TipoArticulo getTipoArticulo() {
		return tipoArticulo;
	}
	public void setTipoArticulo(TipoArticulo tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}
	
	@Transient 
	public String getStrTipoArticulo(){
		if (tipoArticulo == null)
			return null;
		else
			return tipoArticulo.getDescripcion();
	}
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			ClaseArticulo cuenta = (ClaseArticulo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}
