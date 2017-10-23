package cpc.modelo.demeter.mantenimiento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_Periodicidad_aud")
@Entity
@Table(name="tbl_dem_Periodicidad" ,schema="mantenimiento")
public class Periodicidad {
	
	private  Integer 	id;
	private  Integer	tipo;// tipo de periodicidad :-D
	private  Integer	medida;
	private  Actividad  actividad;
	
	private 	static String[]		lisPeriosidad = new String[]{"MESES","SEMANAS","HORAS","KM"};

	@Column(name="seq_idperiodicidad")
	@SequenceGenerator(name="periodicidad_Gen", sequenceName="mantenimiento.tbl_dem_periodicidad_seq",  allocationSize=1 )
	@Id
	@GeneratedValue( generator="periodicidad_Gen")
	public Integer getId() {
		return id;
	}

	@Column(nullable=false)
	public Integer getTipo() {
		return tipo;
	}
	
	@Column(nullable=false)
	public Integer getMedida() {
		return medida;
	}
	@Transient
	public static String[] getLisPeriosidad() {
		return lisPeriosidad;
	}
	

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setMedida(Integer medida) {
		this.medida = medida;
	}

	public static void setLisPeriosidad(String[] lisPeriosidad) {
		Periodicidad.lisPeriosidad = lisPeriosidad;
	}
	
	
	@OneToOne
	@JoinColumn(name="actividad")
	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	
	}
