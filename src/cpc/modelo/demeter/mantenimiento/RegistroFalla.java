package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_fallaobjeto", schema = "mantenimiento")
public class RegistroFalla implements Serializable{	
	private static final long serialVersionUID = -4480944276455053138L;
	private Long id; 
	private ObjetoMantenimiento objetoMantenimiento;
	private TipoFalla tipoFalla;
	private MomentoFalla momentoFalla;
	private Date fechaRegistro;
	private Date fechaFalla;
	private Trabajador trabajadorReporta;
	
	
	@Column(name="seq_idfallaobjeto")
	@SequenceGenerator(name="FallaObjeto_Gen", sequenceName="mantenimiento.tbl_dem_fallaobjeto_seq_idfallaobjeto_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="FallaObjeto_Gen")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="int_idobjeto")
	public ObjetoMantenimiento getObjetoMantenimiento() {
		return objetoMantenimiento;
	}
	public void setObjetoMantenimiento(ObjetoMantenimiento objetoMantenimiento) {
		this.objetoMantenimiento = objetoMantenimiento;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtipofalla")
	public TipoFalla getTipoFalla() {
		return tipoFalla;
	}
	public void setTipoFalla(TipoFalla tipoFalla) {
		this.tipoFalla = tipoFalla;
	}
	
	@OneToOne
	@JoinColumn(name="int_idmomentofalla")
	public MomentoFalla getMomentoFalla() {
		return momentoFalla;
	}
	public void setMomentoFalla(MomentoFalla momentoFalla) {
		this.momentoFalla = momentoFalla;
	}
	
	@Column(name="dtm_fecharegistro")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@Column(name="dtm_fechafalla")
	public Date getFechaFalla() {
		return fechaFalla;
	}
	public void setFechaFalla(Date fechaFalla) {
		this.fechaFalla = fechaFalla;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtrabajadoreporta")
	public Trabajador getTrabajadorReporta() {
		return trabajadorReporta;
	}
	public void setTrabajadorReporta(Trabajador trabajadorReporta) {
		this.trabajadorReporta = trabajadorReporta;
	}	

}
