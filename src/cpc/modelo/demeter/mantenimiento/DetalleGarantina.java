package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_DetalleGarantia_aud")
@Entity
@Table(name="tbl_dem_DetalleGarantia" ,schema="mantenimiento")
public class DetalleGarantina implements Serializable {
	
	private  Integer	 id;
	private  String 	 descripcion;
	private  TipoGarantia tipoGarantia;
	 
	/**
	 * 
	 */
	public DetalleGarantina() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DetalleGarantina(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	
	
	/**
	 * @param descripcion
	 */
	public DetalleGarantina(String descripcion) {
		super();
		this.descripcion = descripcion;
	}


	@SequenceGenerator(name="DetalleGarantia_Gen", sequenceName="mantenimiento.tbl_dem_DetalleGarantina_seq",  allocationSize=1)
	@Id
	@Column(name="seq_idDetalleGarantia")
	@GeneratedValue(generator="DetalleGarantia_Gen")
	public Integer getId() {
		return id;
	}
	
	@Column(name="descripcion",length=250) 
	public String getDescripcion() {
		return descripcion;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToOne
	@JoinColumn(name="id_tipoGarantia")
	public TipoGarantia getTipoGarantia() {
		return tipoGarantia;
	}


	public void setTipoGarantia(TipoGarantia tipoGarantia) {
		this.tipoGarantia = tipoGarantia;
	}
}
