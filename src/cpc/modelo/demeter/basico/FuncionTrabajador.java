package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; 

import org.hibernate.envers.Audited;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Audited @Entity
@Table(name="tbl_dem_funcion_trabajador", schema="basico")
public class FuncionTrabajador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1405672892035413325L;
    private Integer  		id;
    private String   		descripcion;
    
    private List<Trabajador> trabajadores;

    
    @Id
	@Column(name="seq_idfunciontraba")
	@SequenceGenerator(name="SeqFuncionTrabajador", sequenceName="basico.tbl_dem_funcion_trabajador_seq_idfunciontraba_seq", allocationSize=1)
	@GeneratedValue(generator="SeqFuncionTrabajador")
	
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
	


	/*@ManyToMany(targetEntity=Trabajador.class)
	@JoinColumn(name="int_idtrabajador",referencedColumnName="seq_idtrabajador")*/
	
	/*@ManyToMany(mappedBy = "funciones", targetEntity=Trabajador.class,fetch=FetchType.EAGER)*/
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="tbl_dem_trabajador_funcion", schema="basico", 
	joinColumns={@JoinColumn(name="int_idfunciontraba")},
	inverseJoinColumns={@JoinColumn(name="int_idtrabajador")})
    
	public List<Trabajador> getTrabajadores() {
		return trabajadores;
	}
	public void setTrabajadores(List<Trabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}
       
	public String toString(){
		return descripcion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((trabajadores == null) ? 0 : trabajadores.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuncionTrabajador other = (FuncionTrabajador) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (trabajadores == null) {
			if (other.trabajadores != null)
				return false;
		} else if (!trabajadores.equals(other.trabajadores))
			return false;
		return true;
	}

}
