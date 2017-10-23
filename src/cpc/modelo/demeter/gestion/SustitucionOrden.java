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

@Audited @Entity
@Table(name="tbl_dem_sustitucion_ordentrabajo", schema = "gestion")
public class SustitucionOrden implements Serializable{
	
	private static final long serialVersionUID = 6504439416785033934L;
	private Integer 			id;
	private Sustitucion sustitucion;
	private OrdenTrabajo ordenTrabajo;
	
	@Column(name="seq_idsustorden")
	@SequenceGenerator(name="SustitucionOrden_Gen", sequenceName="gestion.tbl_dem_sustitucion_ordentrabajo_seq_idsustorden_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="SustitucionOrden_Gen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="id_idsustitucion")
	public Sustitucion getSustitucion() {
		return sustitucion;
	}
	public void setSustitucion(Sustitucion sustitucion) {
		this.sustitucion = sustitucion;
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
			SustitucionOrden obj = (SustitucionOrden) objeto;
			return (obj.getId().equals(id));
				
		}catch (Exception e) {
			return false;
		}
	}
	
	
}
