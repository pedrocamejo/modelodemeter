package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="saf_estados_documentos",schema="sigesp")
public class EstadoMovimientoActivo implements Serializable{

	private static final long serialVersionUID = -5678399924241204848L;
	private Integer 	idEstado;
	private String 	descripcion;
	private boolean anulado;
	
	public EstadoMovimientoActivo() {
		super();
	}
	
	public EstadoMovimientoActivo(Integer idEstado, String descripcion,
			boolean anulado) {
		super();
		this.idEstado = idEstado;
		this.descripcion = descripcion;
		this.anulado = anulado;
	}
	
	@SequenceGenerator(name="Seq_idEstado", sequenceName="sigesp.saf_estados_documentos_seq_ser_idestado_seq", allocationSize=1)
	@Column(name="seq_ser_idestado")
	@Id @GeneratedValue(generator="Seq_idEstado")
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	
	@Column(name="str_nombre")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_anulado")
	public boolean isAnulado() {
		return anulado;
	}
	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}
	
	public boolean equals(Object objeto){
		try{
			EstadoMovimientoActivo cuenta = (EstadoMovimientoActivo) objeto;
			if (cuenta.getIdEstado().equals(idEstado))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
