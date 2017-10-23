package cpc.modelo.demeter.solicitud;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import cpc.modelo.sigesp.basico.Sede;


@Audited @Entity
@Table(name="tbl_dem_aprobacion", schema="solicitud")
public class Aprobacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8334428630571461813L;
	private int			id;
	private Calendar	fecha;
	private String		nroAsamblea;
	private Sede		sede;
	private boolean		activo;
	
	public Aprobacion() {
		super();
	}
	
	public Aprobacion(int id, Calendar fecha, String nroAsamblea, Sede sede,
			boolean activo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.nroAsamblea = nroAsamblea;
		this.sede = sede;
		this.activo = activo;
	}
	
	
	@SequenceGenerator(name="SeqAprobacion", sequenceName="solicitud.tbl_dem_aprobacion_seq_idaprobacion_seq", allocationSize=1 )
	@Column(name="seq_idaprobacion")
	@Id @GeneratedValue(generator="SeqAprobacion")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fechaprobacion")
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="str_nroasamblea")
	public String getNroAsamblea() {
		return nroAsamblea;
	}
	public void setNroAsamblea(String nroAsamblea) {
		this.nroAsamblea = nroAsamblea;
	}
	
	
	@OneToOne
	@JoinColumns({
	    @JoinColumn(name="str_idsede", referencedColumnName="codubifis", insertable=false, updatable=false),
	    @JoinColumn(name="codemp", referencedColumnName="codemp", insertable=false, updatable=false),
	})	
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	
	@Column(name="bol_activo")
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
	public boolean equals(Object objeto){
		try{
			Aprobacion cuenta = (Aprobacion) objeto;
			if (cuenta.getId()==getId())
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
