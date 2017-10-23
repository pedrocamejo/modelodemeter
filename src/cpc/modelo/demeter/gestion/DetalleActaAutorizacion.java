package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;

@Audited @Entity
@Table(name="saf_detalle_acta_autorizacion", schema = "sigesp")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="int_idactaautori")
public class DetalleActaAutorizacion implements Serializable{

	private static final long serialVersionUID = 1789789687087373677L;
	private Integer 				idDetalleActaAutorizacion;
	private ActaAutorizacion 		actaAutorizacion;
	private Activo 					activo;
	private UnidadAdministrativa 	unidadAdministrativa;
	private Boolean 				usadoEnMovimiento;
	
	public DetalleActaAutorizacion() {
		super();
	}
	
	public DetalleActaAutorizacion(Integer idDetalleActaAutorizacion,
			ActaAutorizacion actaAutorizacion, Activo activo) {
		super();
		this.idDetalleActaAutorizacion = idDetalleActaAutorizacion;
		this.actaAutorizacion = actaAutorizacion;
		this.activo = activo;
	}
	
	@SequenceGenerator(name="SeqDetalleActaAutorizacion", sequenceName = "sigesp.saf_detalle_acta_autorizacion_seq_big_dtacta_seq", allocationSize = 1)
	@Id @GeneratedValue(generator= "SeqDetalleActaAutorizacion")
	@Column(name = "seq_big_dtacta")
	public Integer getIdDetalleActaAutorizacion() {
		return idDetalleActaAutorizacion;
	}
	public void setIdDetalleActaAutorizacion(Integer idDetalleActaAutorizacion) {
		this.idDetalleActaAutorizacion = idDetalleActaAutorizacion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idactaautori")
	public ActaAutorizacion getActaAutorizacion() {
		return actaAutorizacion;
	}
	public void setActaAutorizacion(ActaAutorizacion actaAutorizacion) {
		this.actaAutorizacion = actaAutorizacion;
	}
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp", insertable = false, updatable = false),
		@JoinColumn(name="str_codact",referencedColumnName="codact",insertable = false, updatable = false),
		@JoinColumn(name="str_ideact",referencedColumnName="ideact",insertable = false, updatable = false)
	})
	public Activo getActivo() {
		return activo;
	}
	public void setActivo(Activo activo) {
		this.activo = activo;
	}
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp",insertable = false, updatable = false),
		@JoinColumn(name="str_coduniadm",referencedColumnName="coduniadm",insertable = false, updatable = false)
	})
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	@Column(name="bol_usado")
	public Boolean isUsadoEnMovimiento() {
		return usadoEnMovimiento;
	}

	public void setUsadoEnMovimiento(Boolean usadoEnMovimiento) {
		this.usadoEnMovimiento = usadoEnMovimiento;
	}

	public boolean equals(Object objeto){
		try{
			DetalleActaAutorizacion cuenta = (DetalleActaAutorizacion) objeto;
			if (cuenta.getIdDetalleActaAutorizacion().equals(idDetalleActaAutorizacion))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}
