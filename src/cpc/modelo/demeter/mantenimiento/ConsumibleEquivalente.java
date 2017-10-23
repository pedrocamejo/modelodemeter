package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
@Audited
@Entity
@Table(name="tbl_dem_consumible_equivalente", schema="mantenimiento")
public class ConsumibleEquivalente  implements Serializable{
	/**
	 * 
	 */
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2698512842253867883L;


	/**
	 * 
	 */

	private Consumible consumibleOri;

	
	private Consumible consumibleEq;
		
	
	
	private ConsumibleEquivalentePK id;
	
	//private Repuesto original,equivalente;

//	private String CodigoSIGESPEq,CodigoFabricanteEq,CodigoCCNUEq,DenominacionFabricanteEq; 
	


	
	@ManyToOne
	@Basic

	@JoinColumn(  referencedColumnName="seq_idconsumible" , name="int_idconsumibleori")
	public Consumible getConsumibleOri() {
			return consumibleOri;
		}



		public void setConsumibleOri(Consumible consumibleOriginal) {
	//		if ((consumibleOriginal.getId()!=null)&((consumibleEquivalente.getId()!=null)))
		//	this.id.setIdConsumibleOriginal(consumibleOriginal.getId());
			this.consumibleOri = consumibleOriginal;
		}

		
		@ManyToOne
		@Basic       
	
		@JoinColumn( referencedColumnName="seq_idconsumible", name= "int_idconsumibleeq")
		public Consumible getConsumibleEq() {
			return consumibleEq;
		}



		public  void setConsumibleEq(Consumible consumibleEquivalente) {
		//	if ((consumibleOriginal.getId()!=null)&((consumibleEquivalente.getId()!=null)))
			//this.id.setIdConsumibleEquivalente(1);
			this.consumibleEq = consumibleEquivalente;
		}
	
	
	
	

		@EmbeddedId
		public ConsumibleEquivalentePK getId() {
			return id;
		}

		public void setId(ConsumibleEquivalentePK id) {
			this.id = id;
		}
		

		@Transient
		public String getDenominacionFabricanteEq(){
			return getConsumibleEq().getArticuloVenta().getDenominacionFabricante();
					
		}

		@Transient
		public String getCodigoCCNUEq(){
			return getConsumibleEq().getArticuloVenta().getCodigoCCNU();
					
		}

		@Transient
		public String getCodigoSIGESPEq(){
			return getConsumibleEq().getArticuloVenta().getCodigoSIGESP();
					
		}

		@Transient
		public String getCodigoFabricanteEq(){
			return getConsumibleEq().getArticuloVenta().getCodigoFabricante();
					
		}



		@Transient
		public String toString() {
			return getCodigoFabricanteEq() +" "+getCodigoSIGESPEq();
		}

	

}