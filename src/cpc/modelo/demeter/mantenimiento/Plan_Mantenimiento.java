package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
 

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
 
@Audited
@AuditTable(schema="auditoria",value="tbl_dem_Plan_Mantenimiento_aud")
@Entity
@Table(name="tbl_dem_Plan_Mantenimiento" ,schema="mantenimiento")
public class Plan_Mantenimiento implements Serializable{ // plan de mantenimiento de una parte especifica de la maquinaria ejemplo Motor el motor tiene actividades como cambio de aceite  o cambio de filtro 

	private 	Integer				 		id;	
	private 	String 						descripcion;
	private 	Plan_Mantenimiento			padre;
//	private 	MantenimientoMaquinaria		mantenimientoMaquinaria;
	private 	List<Plan_Mantenimiento>  	piezas = new ArrayList<Plan_Mantenimiento>();
	private 	List<Actividad>     		actividades = new ArrayList<Actividad>(); // actividades de mantenimiento
	
	
	@SequenceGenerator(name="seq_Plan_Mantenimiento",sequenceName="mantenimiento.seq_Plan_Mantenimiento",allocationSize=1)
	@Id
	@GeneratedValue(generator="seq_Plan_Mantenimiento")
	public Integer getId() {
		return id;
	}
	
	@Column
	public String getDescripcion() {
		return descripcion;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="int_idPadre")
	public Plan_Mantenimiento getPadre() {
		 
		return padre;
	}
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@AuditJoinTable(name="tbl_dem_PlanMantenimiento_Mtnto_aud",schema="auditoria")
	@JoinTable(name="tbl_dem_PlanMantenimiento_Mtnto",schema="mantenimiento",joinColumns={@JoinColumn(name="int_idpadre")},inverseJoinColumns={@JoinColumn(name="int_idhijo")})
	public List<Plan_Mantenimiento> getPiezas() {
		return piezas;
	}
	 
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	public void setPadre(Plan_Mantenimiento padre) {
		this.padre = padre;
	} 
	public void setPiezas(List<Plan_Mantenimiento> piezas) {
		this.piezas = piezas;
	}


	@OneToMany(targetEntity=Actividad.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="planMantenimiento")
	 
	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}
	
	/*
	@OneToOne
	@JoinColumn(name="mantenimientoMaquinaria")
	public MantenimientoMaquinaria getMantenimientoMaquinaria() {
		return mantenimientoMaquinaria;
	}

	public void setMantenimientoMaquinaria(MantenimientoMaquinaria mantenimientoMaquinaria) {
		this.mantenimientoMaquinaria = mantenimientoMaquinaria;
	}
	*/
	
	@Transient
	public String getStrDescripcionPadre() //desde el hijo hasta el Primer humano en poner pie en la paz del planeta (Adan ps )
	{
		return (this.padre != null? this.padre.getStrDescripcionPadre():"")+ "-"+(this.descripcion.length()> 20 ? this.descripcion.substring(0,20):this.descripcion);
	}


}
