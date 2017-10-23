package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipocoordenada", schema="ministerio")
public class TipoCoordenadaGeografica implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8489689384338409512L;
	private Integer			id;
	private String			nombre;
	private boolean			UTM;
	
	@Id
	@Column(name="seq_idtipocoordenada")
	@SequenceGenerator(name="SeqTipoCoordenada", sequenceName="ministerio.tbl_dem_tipocoordenada_seq_idtipocoordenada_seq")
	@GeneratedValue(generator="SeqTipoCoordenada")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="bol_geografica")
	public boolean isUTM() {
		return UTM;
	}
	public void setUTM(boolean uTM) {
		UTM = uTM;
	}
	
	public String toString(){
		return nombre;
	}

	public boolean equals(Object objeto){
		try{
			TipoCoordenadaGeografica cuenta = (TipoCoordenadaGeografica) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
