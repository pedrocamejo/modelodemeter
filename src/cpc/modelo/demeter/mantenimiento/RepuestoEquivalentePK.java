package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable

public class RepuestoEquivalentePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5204474845388869583L;


	/**
	 * 
	 */


	/**
	 * 
	 */



	
//private Repuesto repuestoOriginal;

	
//private Repuesto repuestoEquivalente;
	
private Integer  idrepuestoOriginal;

	
private Integer idrepuestoEquivalente;
	
	
	public RepuestoEquivalentePK() {
		super();
	}
	
	
	/*
	@JoinTable (name = "tbl_dem_repuesto",schema="mantenimiento",joinColumns = {
			@JoinColumn(referencedColumnName="int_idrepuestooriginal", name="int_idrepuesto"),
			@ JoinColumn(referencedColumnName="int_idrepuestoequivalente", name="int_idrepuesto")
			
	}
	, inverseJoinColumns = { 	@JoinColumn(referencedColumnName="int_idrepuestooriginal", name="int_idrepuesto"),
			@JoinColumn(referencedColumnName="int_idrepuestoequivalente", name="int_idrepuesto")}
	)
	*/
	@Basic
	@Column(name="int_idrepuestooriginal")
	public Integer getIdRepuestoOriginal() {
		return idrepuestoOriginal;
	}



	public void setIdRepuestoOriginal(Integer idrepuestoOriginal) {
		this.idrepuestoOriginal = idrepuestoOriginal;
	}


	@Basic          
	@Column(name="int_idrepuestoequivalente")
	public Integer getIdRepuestoEquivalente() {
		return idrepuestoEquivalente;
	}

	public void setIdRepuestoEquivalente(Integer idrepuestoEquivalente) {
		this.idrepuestoEquivalente = idrepuestoEquivalente;
	}



	

	
	
	
	public  RepuestoEquivalentePK(Integer repuestoEquivalente,Integer repuestoOriginal) {
		super();
		this.idrepuestoOriginal = repuestoOriginal;
		this.idrepuestoEquivalente = repuestoEquivalente;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof RepuestoEquivalentePK)) {
			return false;
		}
		RepuestoEquivalentePK other = (RepuestoEquivalentePK) o;
		return true
//		&& (getRepuestoOriginal().equals(other.getRepuestoOriginal()) && getRepuestoEquivalente().equals(other.getRepuestoEquivalente()));
				&& (getIdRepuestoOriginal().equals(other.getIdRepuestoOriginal()) && getIdRepuestoEquivalente().equals(other.getIdRepuestoEquivalente()));
	}
	

	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + (getIdRepuestoOriginal() == null ? 0 : getIdRepuestoOriginal().hashCode());
		result = prime * result + (getIdRepuestoEquivalente() == null ? 0 : getIdRepuestoEquivalente().hashCode());
		return result;
	}

	/*
	@Transient
	repuestoEquivalente
	@JoinColumn( table="mantenimiento.tbl_dem_repuesto", referencedColumnName="int_idrepuestooriginal", name="int_idrepuesto")
	

		
		
		
	
		



		public Repuesto getRepuestoOriginal() {
			return repuestoOriginal;
		}



		public void setRepuestoOriginal(Repuesto repuestoOriginal) {
			this.repuestoOriginal = repuestoOriginal;
		}

		@Transient
		@JoinColumn(table="mantenimiento.tbl_dem_repuesto", referencedColumnName="int_idrepuestoequivalente", name="int_idrepuesto")
		public Repuesto getRepuestoEquivalente() {
			return repuestoEquivalente;
		}



		public void setRepuestoEquivalente(Repuesto repuestoEquivalente) {
			this.repuestoEquivalente = repuestoEquivalente;
		}
	
	*/
	
	

}
