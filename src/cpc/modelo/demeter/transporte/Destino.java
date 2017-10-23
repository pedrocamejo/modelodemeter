package cpc.modelo.demeter.transporte;

 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.dimension.UbicacionSector;

@Audited
@AuditTable(schema="auditoria",value="tbl_dem_destino_aud")
@Entity
@Table(name="tbl_dem_destino", schema="transporte")
public class Destino implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String descripcion;
	private UbicacionSector sector;

	@Id
	@Column(name="seq_idDestino")
	@SequenceGenerator(name="seqDestino", sequenceName="transporte.tbl_dem_sector_seq_idDestino_seq", allocationSize=1)
	@GeneratedValue(generator="seqDestino")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne (fetch=FetchType.EAGER,targetEntity=UbicacionSector.class)
	public UbicacionSector getSector() {
		return sector;
	}
	public void setSector(UbicacionSector sector) {
		this.sector = sector;
	}
	
	@Transient
	public String getdestinocompelto()
	{
		String pais = sector.getStrPais();
		String estado = sector.getStrEstado();
		String municipio = sector.getStrMunicipio();
		String parroquia = sector.getStrParroquia();
		return  pais + " -- "+estado+ " -- "+municipio+" -- "+ parroquia + " -- " + sector.getNombre() + this.getDescripcion();
	}

}
