package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.persistencia.demeter.implementacion.mantenimiento.PerObjetoManetenimiento;


@Audited
@AuditTable(schema="auditoria",value="tbl_dem_Actividad_aud")
@Entity
@Table(name="tbl_dem_Actividad" ,schema="mantenimiento")
public class Actividad implements Serializable{// actividades asiganada a la maquinaria 
	
	
	private 	Integer 		   id;
	private 	String			   descripcion;
	private 	List<Periodicidad> periodicidad = new ArrayList<Periodicidad>();
	private 	PlanMantenimiento planMantenimiento; // plan de mantenimiento de una parte especifica de la maquinaria 
	private List<DetalleGarantina> detallesGarantia = new ArrayList<DetalleGarantina>();
	 
	
	@Column(name="seq_idActividad")
	@SequenceGenerator(name="Actividad_Gen", sequenceName="mantenimiento.tbl_dem_Actividad_seq",  allocationSize=1 )
	@Id
	@GeneratedValue( generator="Actividad_Gen")
	public Integer getId() {
		return id;
	}

	@Column
	public String getDescripcion() {
		return descripcion;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	/*
	@ElementCollection
	@JoinTable(name="tbl_dem_Activacion_Periodicidad",schema="mantenimiento",joinColumns={@JoinColumn(name="int_Activacion")})
	@AuditJoinTable(name="tbl_dem_Activacion_Periodicidad_aud",schema="auditoria")
	@Column(name="int_Periodicidad")
	public List<Integer> getPeriodicidad() {
		return periodicidad;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
*/ 


	@ManyToOne
	@JoinColumn(name="int_PlanMantenimeinto")
	public PlanMantenimiento getPlanMantenimiento() {
		return planMantenimiento;
	}

	public void setPlanMantenimiento(PlanMantenimiento planMantenimiento) {
		this.planMantenimiento = planMantenimiento;
	}

	

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToMany(targetEntity=Periodicidad.class,fetch=FetchType.EAGER,mappedBy="actividad")
	@Cascade(value={org.hibernate.annotations.CascadeType.REMOVE,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	/*@JoinTable(name="tbl_dem_Periodicidad",schema="mantenimiento",
			joinColumns={@JoinColumn(columnDefinition="actividad",name="actividad")}
			,inverseJoinColumns={@JoinColumn(columnDefinition="seq_idperiodicidad",name="seq_idperiodicidad")})*/
	public List<Periodicidad> getPeriodicidad() {
		return periodicidad;
	}
	
	@Transient
	public String getPeriodicidadM() {
		String peri = new String();
		for(Periodicidad p : periodicidad)
		{
			peri +=(p.getLisPeriosidad()[p.getTipo()]+" -- "+p.getMedida().toString()+" ");
		}
		return peri.toString();
	}
	
	
	public void setPeriodicidad(List<Periodicidad> periodicidad) {
		this.periodicidad = periodicidad;
	}
	  
	@ManyToMany(targetEntity=DetalleGarantina.class,cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@AuditJoinTable(name="tbl_dem_Actividad_DetalleGarantia_aud",schema="auditoria")
	@JoinTable(name="tbl_dem_Actividad_DetalleGarantia",schema="mantenimiento",
	joinColumns={@JoinColumn(name="id_actividad")},
	inverseJoinColumns={@JoinColumn(name="id_DetalleGarantia")})
	public List<DetalleGarantina> getDetallesGarantia() {
		return detallesGarantia ;
	}
	public void setDetallesGarantia(List<DetalleGarantina> detallesGarantia) {
		this.detallesGarantia = detallesGarantia;
	} 
	
	
	
	
	 }
