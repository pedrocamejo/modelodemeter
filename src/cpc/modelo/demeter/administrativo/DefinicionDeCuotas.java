package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_plan_financiamiento_cuota", schema="administracion")
public class DefinicionDeCuotas implements Serializable{
	
	
	private static final long serialVersionUID = -7442187753006164670L;
	private Integer				 	id;	
	private PlanFinanciamiento 		planFinanciamiento;
	private FrecuenciaDePago  		frecuenciaPago;
	private int 					diasDeEsperaParaPago;
	private int 					cantidadCuotas;
	
	
	public DefinicionDeCuotas() {
	
	}
	
	@Column(name="seq_idplanfinanciamiento_cuota")
	@SequenceGenerator(name="DefCuota_Gen", sequenceName="tbl_dem_plan_financiamiento_cuota_seq_idplanfinanciamiento_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="DefCuota_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@OneToOne
	@JoinColumn(name="int_idplanfinanciamiento")	
	public PlanFinanciamiento getPlanFinanciamiento() {
		return planFinanciamiento;
	}
	public void setPlanFinanciamiento(PlanFinanciamiento planFinanciamiento) {
		this.planFinanciamiento = planFinanciamiento;
	}
	
	@OneToOne
	@JoinColumn(name="int_idfrecuencia")
	public FrecuenciaDePago getFrecuenciaPago() {
		return frecuenciaPago;
	}
	public void setFrecuenciaPago(FrecuenciaDePago frecuenciaPago) {
		this.frecuenciaPago = frecuenciaPago;
	}
	
	@Column(name="int_numedias")
	public int getDiasDeEsperaParaPago() {
		return diasDeEsperaParaPago;
	}
	public void setDiasDeEsperaParaPago(int diasDeEsperaParaPago) {
		this.diasDeEsperaParaPago = diasDeEsperaParaPago;
	}
	@Column(name="int_numecuota")
	public int getCantidadCuotas() {
		return cantidadCuotas;
	}
	public void setCantidadCuotas(int cantidadCuotas) {
		this.cantidadCuotas = cantidadCuotas;
	}
	
	public boolean equals(Object objeto){
		try{
			DefinicionDeCuotas cuenta = (DefinicionDeCuotas) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}  
	

}
