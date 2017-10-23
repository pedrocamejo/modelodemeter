package cpc.modelo.demeter.basico;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipo_consumo")
public class TipoConsumo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1000884547477553872L;
	private Integer	id;
	private String 	nombre;
	private Boolean abono;
	
	public TipoConsumo(){
		
	}
	
	
	public TipoConsumo(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


	@Column(name="seq_idtipcon")
	@SequenceGenerator(name="seqTipoConsumo", sequenceName="tbl_dem_tipo_consumo_seq_idtipcon_seq", allocationSize =1)
	@Id @GeneratedValue(generator="seqTipoConsumo")
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
	
	@Column(name="bol_abono")
	public Boolean isAbono() {
		return abono;
	}

	public void setAbono(Boolean abono) {
		this.abono = abono;
	}

	
	@Override
	public String toString() {
		return this.nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoConsumo cuenta = (TipoConsumo) objeto;
			if (cuenta.getId().equals(this.id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}
