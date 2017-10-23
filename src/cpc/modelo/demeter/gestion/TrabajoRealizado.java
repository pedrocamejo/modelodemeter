package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cpc.modelo.demeter.basico.Labor;

@Audited @Entity
@Table(name="tbl_dem_trabajo_orden_servicio", schema="gestion")
@Inheritance(strategy = InheritanceType.JOINED)
public class TrabajoRealizado implements Serializable {
	
	private static final long serialVersionUID = 2581623338668886475L;
	
	private Long				id;
	private Date				fecha;
	private Date				horaInicio;
	private Date				horaFinal;
	private Labor				labor;
	private OrdenTrabajo		ordenTrabajo;
	
	
	public TrabajoRealizado() {
		super();
	}

	@Id @Column(name="seq_idtrabajoorden")
	@SequenceGenerator(name="seqTrabajoRealizado", sequenceName="gestion.tbl_dem_trabajo_orden_servicio_seq_idtrabajoorden_seq", allocationSize=1)
	@GeneratedValue(generator="seqTrabajoRealizado")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}



	
	@Temporal(TemporalType.DATE)
	@Column(name="dte_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Temporal(TemporalType.TIME)
	@Column(name="tm_inicio")
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	@Temporal(TemporalType.TIME)
	@Column(name="tm_fin")
	public Date getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}

	@ManyToOne
	@JoinColumn(name="int_idlabor")
	public Labor getLabor() {
		return labor;
	}
	public void setLabor(Labor labor) {
		this.labor = labor;
	}


	@ManyToOne
	@JoinColumn(name="int_idordenservicio")
	public OrdenTrabajo getOrdenTrabajo() {
		return ordenTrabajo;
	}

	public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
	
	public boolean equals(Object objeto){
		try{
			TrabajoRealizado cuenta = (TrabajoRealizado) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}



}