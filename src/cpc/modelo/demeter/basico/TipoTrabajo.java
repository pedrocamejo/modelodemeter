package cpc.modelo.demeter.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipotrabajo", schema = "basico")
public class TipoTrabajo implements Serializable{

	private static final long serialVersionUID = 4228223117483236021L;
	private Integer 		id;
	private String 			descripcion;
	private TipoSolicitud	tipoSolicitud;
	
	public TipoTrabajo(){
		
	}
	
	public TipoTrabajo(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	@Column(name="seq_idtipotrabajo")
	@SequenceGenerator(name="seqTipotrabajo", sequenceName="basico.tbl_dem_tipotrabajo_seq_idtipotrabajo_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="seqTipotrabajo")
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


	@OneToOne
	@JoinColumn(name="int_idtiposolictud")
	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoTrabajo cuenta = (TipoTrabajo) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}