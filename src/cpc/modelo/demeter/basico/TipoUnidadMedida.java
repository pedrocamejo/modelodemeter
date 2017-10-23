package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipo_unidad_medida")
public class TipoUnidadMedida implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -646633966192857092L;
	
	private Integer	id;
	private String 	descripcion;
	private boolean activa;
	private boolean compuesto;

	public TipoUnidadMedida() {
		super();
	}
	
	public TipoUnidadMedida(int id, String descripcion, boolean activa,
			boolean compuesto) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.activa = activa;
		this.compuesto = compuesto;
	}

	@SequenceGenerator(name="SeqTipoUnidadMedida", sequenceName="tbl_dem_tipo_unidad_medida_seq_idtipounidadmedida_seq", allocationSize = 1)
	@Column(name="seq_idtipounidadmedida")
	@Id @GeneratedValue(generator="SeqTipoUnidadMedida")
	public Integer getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name="bol_activo")
	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Column(name="bol_compuesto")
	public boolean isCompuesto() {
		return compuesto;
	}

	public void setCompuesto(boolean compuesto) {
		this.compuesto = compuesto;
	}
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoUnidadMedida cuenta = (TipoUnidadMedida) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
	
}
