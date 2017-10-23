package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable

public class HerramientaEquivalentePK implements Serializable{
	




	
/**
	 * 
	 */
	private static final long serialVersionUID = 8127410278027876999L;


//private Repuesto repuestoOriginal;

	
//private Repuesto repuestoEquivalente;
	
private Integer  idherramientaOriginal;

	
private Integer idherramientaEquivalente;
	
	
	public HerramientaEquivalentePK() {
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
	@Column(name="int_idherramientaoriginal")
	public Integer getIdHerramientaOriginal() {
		return idherramientaOriginal;
	}



	public void setIdHerramientaOriginal(Integer idherramientaOriginal) {
		this.idherramientaOriginal = idherramientaOriginal;
	}


	@Basic          
	@Column(name="int_idherramientaequivalente")
	public Integer getIdHerramientaEquivalente() {
		return idherramientaEquivalente;
	}

	public void setIdHerramientaEquivalente(Integer idherramientaEquivalente) {
		this.idherramientaEquivalente = idherramientaEquivalente;
	}



	

	
	
	
	public  HerramientaEquivalentePK(Integer repuestoEquivalente,Integer repuestoOriginal) {
		super();
		this.idherramientaOriginal = repuestoOriginal;
		this.idherramientaEquivalente = repuestoEquivalente;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof HerramientaEquivalentePK)) {
			return false;
		}
		HerramientaEquivalentePK other = (HerramientaEquivalentePK) o;
		return true
//		&& (getRepuestoOriginal().equals(other.getRepuestoOriginal()) && getRepuestoEquivalente().equals(other.getRepuestoEquivalente()));
				&& (getIdHerramientaOriginal().equals(other.getIdHerramientaOriginal()) && getIdHerramientaEquivalente().equals(other.getIdHerramientaEquivalente()));
	}
	

	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + (getIdHerramientaOriginal() == null ? 0 : getIdHerramientaOriginal().hashCode());
		result = prime * result + (getIdHerramientaEquivalente() == null ? 0 : getIdHerramientaEquivalente().hashCode());
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