package cpc.modelo.demeter.mantenimiento;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.Modelo;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_MantenimientoMaquinaria_aud")
@Entity
@Table(name="tbl_dem_MantenimientoMaquinaria" ,schema="mantenimiento")
public class MantenimientoMaquinaria 
{

	private	  Integer			 		id;
	private   TipoGarantia 				tipogarantia;// tipo de Maquinaria q esten aka y alla XD 
	private   PlanMantenimiento			planMaquinaria;
	  
	
	public MantenimientoMaquinaria(Integer id, 	PlanMantenimiento planMaquinaria) {
		super();
		this.id = id; 
		this.planMaquinaria = planMaquinaria;
	}
	
	 
	public MantenimientoMaquinaria() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SequenceGenerator(name="seq_MantenimientoMaquinaria",sequenceName="mantenimiento.tbl_dem_MantenimientoMaquinaria_seq",allocationSize=1)
	@Id
	@GeneratedValue(generator="seq_MantenimientoMaquinaria")
	public Integer getId() {
		return id;
	} 
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(unique=true,name="int_PlanMantenimiento")
	public PlanMantenimiento getPlanMaquinaria() {
		return planMaquinaria;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	 
	public void setPlanMaquinaria(PlanMantenimiento planMaquinaria) {
		this.planMaquinaria = planMaquinaria;
	}

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(unique=true,name="int_Tipogarantia")
	public TipoGarantia getTipogarantia() {
		return tipogarantia;
	}


	public void setTipogarantia(TipoGarantia tipogarantia) {
		this.tipogarantia = tipogarantia;
	}
	
	@Transient
	public  String getModeloMaquinaria()
	{
		if (this.tipogarantia != null)
		{
			return this.tipogarantia.getModeloSTR();
		}
		return "No Definido ";
	}
	
	@Transient
	public  String  getMarcaMaquinaria()
	{
		if (this.tipogarantia != null)
		{
			return this.tipogarantia.getMarcaSTR();
		}
		return "No Definido ";
	}

}
