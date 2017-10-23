package cpc.modelo.demeter.sincronizacion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;



@Entity
@Table(name = "tbl_dem_importarDetalle",schema = "sincronizacion")
public class DetalleImportar implements Serializable {

	
	private Integer  id;
	private byte[] idorigen;
	private byte[] iddestino;
	private String   nameclass;
	private byte[]  objeto;
//	private SedeDemeter     sedeDemeter;
	private Importar 		importar;
//	private Integer			operacion;
	private boolean isupdate;
	private boolean completado;
	
	
	
	
	@SequenceGenerator(name="Seqimportdetalle", sequenceName="sincronizacion.tbl_dem_importardetalle_id_seq", allocationSize=1)
	@Id
	@Column(name="id")
	@GeneratedValue(generator="Seqimportdetalle")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id =id;
	}


@Column
	public byte[] getIdorigen() {
		return idorigen;
	}




	public void setIdorigen(byte[] idorigen) {
		this.idorigen = idorigen;
	}



	@Column
	public byte[] getIddestino() {
		return iddestino;
	}




	public void setIddestino(byte[] iddestino) {
		this.iddestino = iddestino;
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



	/*
	@OneToOne
	public SedeDemeter getSedeDemeter() {
		return sedeDemeter;
	}




	public void setSedeDemeter(SedeDemeter sedeDemeter) {
		this.sedeDemeter = sedeDemeter;
	}

*/

	
	@OneToOne
	public Importar getImportar() {
		return importar;
	}




	public void setImportar(Importar importar) {
		this.importar = importar;
	}



	@Column
	public boolean isIsupdate() {
		return isupdate;
	}




	public void setIsupdate(boolean isupdate) {
		this.isupdate = isupdate;
	}



	@Column
	public boolean isCompletado() {
		return completado;
	}




	public void setCompletado(boolean completado) {
		this.completado = completado;
	}
	


	
	
	
	
	
}
