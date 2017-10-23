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
@Table(name="tbl_dem_herramienta_equivalente", schema="mantenimiento")
public class HerramientaEquivalente  implements Serializable{
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

	private Herramienta herramientaOri;

	
	private Herramienta herramientaEq;
		
	
	
	private HerramientaEquivalentePK id;
	
	//private Repuesto original,equivalente;

//	private String CodigoSIGESPEq,CodigoFabricanteEq,CodigoCCNUEq,DenominacionFabricanteEq; 
	


	
	@ManyToOne
	@Basic

	@JoinColumn(  referencedColumnName="seq_idherramienta" , name="int_idherramientaori")
	public Herramienta getHerramientaOri() {
			return herramientaOri;
		}



		public void setHerramientaOri(Herramienta herramientaOriginal) {
	//		if ((herramientaOriginal.getId()!=null)&((herramientaEquivalente.getId()!=null)))
		//	this.id.setIdherramientaOriginal(herramientaOriginal.getId());
			this.herramientaOri = herramientaOriginal;
		}

		
		@ManyToOne
		@Basic       
	
		@JoinColumn( referencedColumnName="seq_idherramienta", name= "int_idherramientaeq")
		public Herramienta getHerramientaEq() {
			return herramientaEq;
		}



		public  void setHerramientaEq(Herramienta herramientaEquivalente) {
		//	if ((herramientaOriginal.getId()!=null)&((herramientaEquivalente.getId()!=null)))
			//this.id.setIdherramientaEquivalente(1);
			this.herramientaEq = herramientaEquivalente;
		}
	
	
	
	

		@EmbeddedId
		public HerramientaEquivalentePK getId() {
			return id;
		}

		public void setId(HerramientaEquivalentePK id) {
			this.id = id;
		}
		

		@Transient
		public String getDenominacionFabricanteEq(){
			return getHerramientaEq().getArticuloVenta().getDenominacionFabricante();
					
		}

		@Transient
		public String getCodigoCCNUEq(){
			return getHerramientaEq().getArticuloVenta().getCodigoCCNU();
					
		}

		@Transient
		public String getCodigoSIGESPEq(){
			return getHerramientaEq().getArticuloVenta().getCodigoSIGESP();
					
		}

		@Transient
		public String getCodigoFabricanteEq(){
			return getHerramientaEq().getArticuloVenta().getCodigoFabricante();
					
		}



		@Transient
		public String toString() {
			return getCodigoFabricanteEq() +" "+getCodigoSIGESPEq();
		}

	

}