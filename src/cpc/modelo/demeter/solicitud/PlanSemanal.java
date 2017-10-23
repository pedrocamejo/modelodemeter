package cpc.modelo.demeter.solicitud;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_plan_semanal", schema="solicitud")
@NamedQueries({ 
	@NamedQuery(name = "PlanSemanal.findAll", query = "SELECT s FROM PlanSemanal s"),
})
public class PlanSemanal implements Serializable{

	private static final long serialVersionUID = 2771592040992727522L;
	private long		idPlanSemanal; //seq_idplansema
	private String		descripcion; //str_descripcion
	private Calendar 	fechaPlan; //dtm_fecha_plan
	private Calendar 	fechaInicio; //dtm_fecha_inicio
	private Calendar 	fechaFin; //dtm_fecha_fin
	private String		idSede; //str_idsede
	private int			criterioPrioridad; //int_criterio_prioridad
	private boolean		activo; //bol_activo
	private int			idAprobacion; //int_idaprobacion
	
	
	public PlanSemanal() {
		super();
	}


	@Column(name="seq_idplansema")
	@SequenceGenerator(name="PlanSemanal_Gen", sequenceName="tbl_dem_plan_semanal_seq_idplansema_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="PlanSemanal_Gen")
	public long getIdPlanSemanal() {
		return idPlanSemanal;
	}


	public void setIdPlanSemanal(long idPlanSemanal) {
		this.idPlanSemanal = idPlanSemanal;
	}

	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Column(name="dtm_fecha_plan")
	public Calendar getFechaPlan() {
		return fechaPlan;
	}


	public void setFechaPlan(Calendar fechaPlan) {
		this.fechaPlan = fechaPlan;
	}


	@Column(name="dtm_fecha_inicio")
	public Calendar getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name="dtm_fecha_fin")
	public Calendar getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}


	@Column(name="str_idsede")
	public String getIdSede() {
		return idSede;
	}


	public void setIdSede(String idSede) {
		this.idSede = idSede;
	}


	@Column(name="int_criterio_prioridad")
	public int getCriterioPrioridad() {
		return criterioPrioridad;
	}


	public void setCriterioPrioridad(int criterioPrioridad) {
		this.criterioPrioridad = criterioPrioridad;
	}

	@Column(name="bol_activo")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name="int_idaprobacion")
	public int getIdAprobacion() {
		return idAprobacion;
	}

	public void setIdAprobacion(int idAprobacion) {
		this.idAprobacion = idAprobacion;
	}
	

}