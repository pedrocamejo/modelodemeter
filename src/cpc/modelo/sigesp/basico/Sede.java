package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cpc.modelo.sigesp.indice.SedePK;


@Audited
@Entity
@Table(name="sno_ubicacion_fisica", schema="sigesp" ,
	uniqueConstraints = {@UniqueConstraint(columnNames={"codubifis"})})
public class Sede  implements Serializable, Comparable<Sede> {
	
	private static final long serialVersionUID = 2424656128738749910L;
	
	private SedePK 			id;
	private String 			nombre;	
	private boolean 		esSede;
	
	public Sede() {
		super();
	}
	
	
	public Sede(String codigoEmpresa, String idSede, String nombre, EstadoGeografico estado, boolean esSede) {
		super();
		this.id = new SedePK(codigoEmpresa, idSede);
		this.nombre = nombre;		
		this.esSede = esSede;
	}
	
	@EmbeddedId
	public SedePK getId() {
		return id;
	}

	public void setId(SedePK id) {
		this.id = id;
	}
	
	@Column(name="desubifis")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="str_essede")
	public boolean isEsSede() {
		return esSede;
	}

	public void setEsSede(boolean esSede) {
		this.esSede = esSede;
	}

	
	/*@OneToOne
	@JoinColumns({
	    @JoinColumn(name="codpai", referencedColumnName="codpai", insertable=false, updatable=false),
	    @JoinColumn(name="codest", referencedColumnName="codest", insertable=false, updatable=false),
	})	
	
	public EstadoGeografico getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoGeografico estado) {
		this.estado = estado;
	}*/

	public String toString(){
		return nombre;
	}


	public int compareTo(Sede arg0) {
		return this.getNombre().compareTo(arg0.getNombre());
	}
	
	public boolean 	equals(Sede obj){
		try{
			Sede sede = (Sede) obj;
			if (sede.getId().equals(id) )
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}