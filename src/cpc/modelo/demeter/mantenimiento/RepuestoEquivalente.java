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
@Table(name="tbl_dem_repuesto_equivalente", schema="mantenimiento")
public class RepuestoEquivalente  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7644393903186459012L;


	/**
	 * 
	 */
	

	/**
	 * 
	 */
	


	/**
	 * 
	 */

	private Repuesto repuestoOri;

	
	private Repuesto repuestoEq;
		
	
	
	private RepuestoEquivalentePK id;
	
	//private Repuesto original,equivalente;

//	private String CodigoSIGESPEq,CodigoFabricanteEq,CodigoCCNUEq,DenominacionFabricanteEq; 
	


	
	@ManyToOne
	@Basic

	@JoinColumn(  referencedColumnName="seq_idrepuesto" , name="int_idrepuestoori")
	public Repuesto getRepuestoOri() {
			return repuestoOri;
		}



		public void setRepuestoOri(Repuesto repuestoOriginal) {
	//		if ((repuestoOriginal.getId()!=null)&((repuestoEquivalente.getId()!=null)))
		//	this.id.setIdrepuestoOriginal(repuestoOriginal.getId());
			this.repuestoOri = repuestoOriginal;
		}

		
		@ManyToOne
		@Basic       
	
		@JoinColumn( referencedColumnName="seq_idrepuesto", name= "int_idrepuestoeq")
		public Repuesto getRepuestoEq() {
			return repuestoEq;
		}



		public  void setRepuestoEq(Repuesto repuestoEquivalente) {
		//	if ((repuestoOriginal.getId()!=null)&((repuestoEquivalente.getId()!=null)))
			//this.id.setIdrepuestoEquivalente(1);
			this.repuestoEq = repuestoEquivalente;
		}
	
	
	
	

		@EmbeddedId
		public RepuestoEquivalentePK getId() {
			return id;
		}

		public void setId(RepuestoEquivalentePK id) {
			this.id = id;
		}
		

		@Transient
		public String getDenominacionFabricanteEq(){
			return getRepuestoEq().getArticuloVenta().getDenominacionFabricante();
					
		}

		@Transient
		public String getCodigoCCNUEq(){
			return getRepuestoEq().getArticuloVenta().getCodigoCCNU();
					
		}

		@Transient
		public String getCodigoSIGESPEq(){
			return getRepuestoEq().getArticuloVenta().getCodigoSIGESP();
					
		}

		@Transient
		public String getCodigoFabricanteEq(){
			return getRepuestoEq().getArticuloVenta().getCodigoFabricante();
					
		}



		@Transient
		public String toString() {
			return getCodigoFabricanteEq() +" "+getCodigoSIGESPEq();
		}

	

}
