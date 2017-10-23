package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_cargo_trabajador", schema="basico")
public class CargoTrabajador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 24818979446546865L;
	private Integer		id;
	private String		descripcion;
	
	@Id
	@Column(name="seq_idcargotraba")
	@SequenceGenerator(name="SeqCargoTrabajador", sequenceName="basico.tbl_dem_cargo_trabajador_seq_idcargotraba_seq", allocationSize=1)
	@GeneratedValue(generator="SeqCargoTrabajador")
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
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equal(Object objeto){
		CargoTrabajador obj = (CargoTrabajador) objeto;
		try{
			if (obj.getId() == this.id)
				return true;
			else 
				return false;
		}catch (Exception e) {
			return false;
		}
	}

	
	
}
