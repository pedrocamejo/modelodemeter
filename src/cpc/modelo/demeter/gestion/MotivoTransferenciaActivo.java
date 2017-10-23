package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="saf_motivotransferencia", schema="sigesp")
public class MotivoTransferenciaActivo implements Serializable{

	private static final long serialVersionUID = 8983711635038185941L;
	private Integer id;
	private String 	descripcion;
	
	public MotivoTransferenciaActivo() {
		super();
	}
	
	public MotivoTransferenciaActivo(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	
	@SequenceGenerator(name="Seq_idMotivoTransferencia", sequenceName="sigesp.saf_motivotransferencia_seq_ser_idmotivo_seq", allocationSize=1)
	@Column(name="seq_ser_idmotivo")
	@Id @GeneratedValue(generator="Seq_idMotivoTransferencia")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String toString() {
		return getDescripcion();
	}
	
	public boolean equals(Object objeto){
		try{
			MotivoTransferenciaActivo cuenta = (MotivoTransferenciaActivo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}
