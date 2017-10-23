package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipoplan")
public class TipoPlanServicio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8205899313666587276L;
	private Integer			id;
	private String		nombre;
	private boolean		ordinario;
	
	public TipoPlanServicio() {
		super();
	}
	
	public TipoPlanServicio(int id, String nombre, boolean ordinario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ordinario = ordinario;
	}
	@SequenceGenerator(name="SeqTipoPlan", sequenceName="tbl_dem_tipoplan_seq_idtipoplan_seq", initialValue=1)
	@Column(name="seq_idtipoplan")
	@Id @GeneratedValue(generator="SeqTipoPlan")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="bol_ordinal")
	public boolean isOrdinario() {
		return ordinario;
	}
	public void setOrdinario(boolean ordinario) {
		this.ordinario = ordinario;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoPlanServicio cuenta = (TipoPlanServicio) objeto;
			if (cuenta.getId().equals(this.getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
	
}
