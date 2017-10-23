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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import cpc.modelo.demeter.basico.Trabajador;

@Audited @Entity
@Table(name="tbl_dem_falla_bienproduccion", schema = "bien_produccion")
public class Falla implements Serializable{

	private static final long serialVersionUID = -6911516668287710031L;
	private Integer 		id;	 
	private TipoFalla   	tipoFalla;
	private MomentoFalla 	momentoFalla;
	private ObjetoMantenimiento  bienProduccion;
	private Date 			fechaRegistro;
	private Date 			fechaFalla;
	private Trabajador 		trabajadorReportaFalla;
	
	
	public Falla(){
	}
	
	@Column(name="seq_idfallabien")
	@SequenceGenerator(name="FallaBien_Gen", sequenceName="bien_produccion.tbl_dem_falla_bienproduccion_seq_idfallabien_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="FallaBien_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipofalla")
	public TipoFalla getTipoFalla() {
		return tipoFalla;
	}

	public void setTipoFalla(TipoFalla tipoFalla) {
		this.tipoFalla = tipoFalla;
	}

	@ManyToOne
	@JoinColumn(name="int_idmomentofalla")
	public MomentoFalla getMomentoFalla() {
		return momentoFalla;
	}

	public void setMomentoFalla(MomentoFalla momentoFalla) {
		this.momentoFalla = momentoFalla;
	}

	@ManyToOne
	@JoinColumn(name="int_idbien")
	public ObjetoMantenimiento getBienProduccion() {
		return bienProduccion;
	}

	public void setBienProduccion(ObjetoMantenimiento bienProduccion) {
		this.bienProduccion = bienProduccion;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dtm_fecharegistro")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dtm_fechafalla")
	public Date getFechaFalla() {
		return fechaFalla;
	}

	public void setFechaFalla(Date fechaFalla) {
		this.fechaFalla = fechaFalla;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtrabajadoreporta")
	public Trabajador getTrabajadorReportaFalla() {
		return trabajadorReportaFalla;
	}

	public void setTrabajadorReportaFalla(Trabajador trabajadorReportaFalla) {
		this.trabajadorReportaFalla = trabajadorReportaFalla;
	}

	public boolean equals(Object objeto){
		try{
			Falla obj = (Falla) objeto;
			if (obj.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}