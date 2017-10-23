package cpc.modelo.demeter.administrativo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_frecuenciapago", schema="administracion")
public class FrecuenciaDePago implements Serializable{
	
	private static final long serialVersionUID = -1555174803272047329L;
	private Integer	 	id;
	private String 		descripcion;
	private String 		peridoTiempo;
	private int 		diasDelPeriodo;
	
	public FrecuenciaDePago() {
	
	}
	
	@Column(name="seq_idfrecuencia")
	@SequenceGenerator(name="DefFrecuencia_Gen", sequenceName="tbl_dem_frecuenciapago_seq_idfrecuencia_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="DefFrecuencia_Gen")
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
	@Column(name="str_intervalo")
	public String getPeridoTiempo() {
		return peridoTiempo;
	}

	public void setPeridoTiempo(String peridoTiempo) {
		this.peridoTiempo = peridoTiempo;
	}
	@Column(name="int_diasdelperiodo")
	public int getDiasDelPeriodo() {
		return diasDelPeriodo;
	}

	public void setDiasDelPeriodo(int diasDelPeriodo) {
		this.diasDelPeriodo = diasDelPeriodo;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof FrecuenciaDePago)) {
			return false;
		}
		FrecuenciaDePago other = (FrecuenciaDePago) o;
		return true && other.getId().equals(this.getId());
	}

}
