package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable

public class ConsumibleEquivalentePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -330058860209082207L;


	/**
	 * 
	 */



	
//private Repuesto repuestoOriginal;

	
//private Repuesto repuestoEquivalente;
	
private Integer  idConsumibleOriginal;

	
private Integer idConsumibleEquivalente;
	
	
	public ConsumibleEquivalentePK() {
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
	@Column(name="int_idconsumibleoriginal")
	public Integer getIdConsumibleOriginal() {
		return idConsumibleOriginal;
	}



	public void setIdConsumibleOriginal(Integer idConsumibleOriginal) {
		this.idConsumibleOriginal = idConsumibleOriginal;
	}


	@Basic          
	@Column(name="int_idconsumibleequivalente")
	public Integer getIdConsumibleEquivalente() {
		return idConsumibleEquivalente;
	}

	public void setIdConsumibleEquivalente(Integer idConsumibleEquivalente) {
		this.idConsumibleEquivalente = idConsumibleEquivalente;
	}



	

	
	
	
	public  ConsumibleEquivalentePK(Integer repuestoEquivalente,Integer repuestoOriginal) {
		super();
		this.idConsumibleOriginal = repuestoOriginal;
		this.idConsumibleEquivalente = repuestoEquivalente;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ConsumibleEquivalentePK)) {
			return false;
		}
		ConsumibleEquivalentePK other = (ConsumibleEquivalentePK) o;
		return true
//		&& (getRepuestoOriginal().equals(other.getRepuestoOriginal()) && getRepuestoEquivalente().equals(other.getRepuestoEquivalente()));
				&& (getIdConsumibleOriginal().equals(other.getIdConsumibleOriginal()) && getIdConsumibleEquivalente().equals(other.getIdConsumibleEquivalente()));
	}
	

	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + (getIdConsumibleOriginal() == null ? 0 : getIdConsumibleOriginal().hashCode());
		result = prime * result + (getIdConsumibleEquivalente() == null ? 0 : getIdConsumibleEquivalente().hashCode());
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