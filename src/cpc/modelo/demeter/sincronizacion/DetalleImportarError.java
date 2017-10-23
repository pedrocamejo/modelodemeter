package cpc.modelo.demeter.sincronizacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_dem_importardetalleerror",schema = "sincronizacion")
public class DetalleImportarError {

	private Integer  id;
	private byte[] idorigen;

	private String   nameclass;
//(	private boolean  trabajado = false;
//	private Object 	 objeto;
	private byte[]  objeto;
	private Importar importar;
	private byte[]   excepcion;
	private boolean isupdate;
	private String   StrError;
	

	@SequenceGenerator(name="SeqdetalleImportarerror", sequenceName="sincronizacion.tbl_dem_importardetalleerror_id_seq", allocationSize=1)
	@Id
	@Column(name="id")
	@GeneratedValue(generator="SeqdetalleImportarerror")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column
	public byte[] getIdorigen() {
		return idorigen;
	}
	public void setIdorigen(byte[] idorigen) {
		this.idorigen = idorigen;
	}
	@Column
	public String getNameclass() {
		return nameclass;
	}
	public void setNameclass(String nameclass) {
		this.nameclass = nameclass;
	}
	@Column
	public byte[] getObjeto() {
		return objeto;
	}
	public void setObjeto(byte[] objeto) {
		this.objeto = objeto;
	}
	@OneToOne
	public Importar getImportar() {
		return importar;
	}
	public void setImportar(Importar importar) {
		this.importar = importar;
	}
	@Column
	public byte[] getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(byte[] excepcion) {
		this.excepcion = excepcion;
	}
	@Column
	public boolean isIsupdate() {
		return isupdate;
	}
	public void setIsupdate(boolean isupdate) {
		this.isupdate = isupdate;
	}
	
	@Column(columnDefinition="TEXT")
	public String getStrError() {
		return StrError;
	}
	public void setStrError(String strError) {
		StrError = strError;
	}

}
