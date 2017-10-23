package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cpc.modelo.demeter.basico.Trabajador;


//@Audited @Entity
@Table(name = "tbl_dem_bien_operador", schema = "mantenimiento")
@NamedQueries({ 
				@NamedQuery(name = "OperadorBienProduccion.findAll", 
							query = "SELECT s FROM OperadorBienProduccion s"),
			})
			
public class OperadorBienProduccion implements Serializable {
	
	private static final long serialVersionUID = 3253495910298394136L;
	
	private Integer			idAsignacion; 	//seq_idasignacion
	private int 			idBien;			//int_idbien
	private Trabajador		trabajadores;
	private Calendar 		fechaAsignacion;//dtm_fechaasignacion
	
	public OperadorBienProduccion() {
		super();
	}

	@Column(name="seq_idasignacion")
	@SequenceGenerator(name="OperadorBienProduccion_Gen", sequenceName="tbl_dem_bien_operador_seq_idasignacion_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="OperadorBienProduccion_Gen")
	public Integer getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(Integer idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	@Column(name="int_idbien")
	public int getIdBien() {
		return idBien;
	}

	public void setIdBien(int intIdbien) {
		idBien = intIdbien;
	}

	@OneToOne
	@JoinColumn(name="int_idtrabajador")
	public Trabajador getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(Trabajador trabajadores) {
		this.trabajadores = trabajadores;
	}

	@Column(name="dtm_fechaasignacion")
	public Calendar getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Calendar fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	
	public boolean equals(Object objeto){
		try{
			OperadorBienProduccion cuenta = (OperadorBienProduccion) objeto;
			if (cuenta.getIdAsignacion().equals(idAsignacion))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}