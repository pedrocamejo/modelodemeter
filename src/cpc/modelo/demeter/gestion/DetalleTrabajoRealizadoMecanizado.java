package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.Trabajador;


@Audited @Entity
@Table(name="tbl_dem_detalle_trabajo_mecanizado", schema="gestion")
public class DetalleTrabajoRealizadoMecanizado implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2860808100767148218L;
	private Integer						id;
	private TrabajoRealizadoMecanizado	trabajo;
	private MaquinariaUnidad			equipo;
	private ImplementoUnidad			implemento;
	private Trabajador					operador;
	private Double 						horometroInicio;
	private Double 						horometroFinal;
	private Double 						horometroEfectivoInicio;
	private Double 						horometroEfectivoFinal;
	
	@Id @Column(name="seq_iddetalletrabajo")
	@SequenceGenerator(name="seqDetalleTrabajoMecanizado", sequenceName="gestion.tbl_dem_detalle_trabajo_mecanizado_seq_iddetalletrabajo_seq", allocationSize=1)
	@GeneratedValue(generator="seqDetalleTrabajoMecanizado")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

	@ManyToOne
	@JoinColumn(name="int_idtrabajoorden")
	public TrabajoRealizadoMecanizado getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(TrabajoRealizadoMecanizado trabajo) {
		this.trabajo = trabajo;
	}
	

	@ManyToOne
	@JoinColumn(name="int_idmaquinaria")
	public MaquinariaUnidad getEquipo() {
		return equipo;
	}
	public void setEquipo(MaquinariaUnidad equipo) {
		this.equipo = equipo;
	}
	

	@ManyToOne
	@JoinColumn(name="int_idimplemento")
	public ImplementoUnidad getImplemento() {
		return implemento;
	}
	public void setImplemento(ImplementoUnidad implemento) {
		this.implemento = implemento;
	}

	@ManyToOne
	@JoinColumn(name="int_idoperador")
	public Trabajador getOperador() {
		return operador;
	}
	public void setOperador(Trabajador operador) {
		this.operador = operador;
	}
	
	@Column(name="dbl_horometroinicio")
	public Double getHorometroInicio() {
		return horometroInicio;
	}
	public void setHorometroInicio(Double horometroInicio) {
		this.horometroInicio = horometroInicio;
	}
	
	@Column(name="dbl_horometrofinal")
	public Double getHorometroFinal() {
		return horometroFinal;
	}
	public void setHorometroFinal(Double horometroFinal) {
		this.horometroFinal = horometroFinal;
	}
	
	@Column(name="dbl_horometroefectivoinicio")
	public Double getHorometroEfectivoInicio() {
		return horometroEfectivoInicio;
	}
	public void setHorometroEfectivoInicio(Double horometroEfectivoInicio) {
		this.horometroEfectivoInicio = horometroEfectivoInicio;
	}
	
	@Column(name="dbl_horometroefectivofinal")
	public Double getHorometroEfectivoFinal() {
		return horometroEfectivoFinal;
	}
	public void setHorometroEfectivoFinal(Double horometroEfectivoFinal) {
		this.horometroEfectivoFinal = horometroEfectivoFinal;
	}
	
	@Transient
	public double getTotalHorometro(){
		return horometroFinal-	horometroInicio;
	}
	
	
	@Transient
	public double getHorometroEfectivo(){
		return horometroEfectivoFinal -horometroEfectivoInicio;	
	}
	
}
