package cpc.modelo.demeter.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="tbl_dem_tipodocumentotierra", schema = "basico")
public class TipoDocumentoTierra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3910074262963092082L;
	private Integer 		id;
	private String 			descripcion;
	private boolean			provisional;
	
	public TipoDocumentoTierra(){
		
	}
	
	public TipoDocumentoTierra(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	@Column(name="seq_idtipodocumento")
	@SequenceGenerator(name="seqTipoDocumentoTierra", sequenceName="basico.tbl_dem_tipodocumentotierra_seq_idtipodocumento_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="seqTipoDocumentoTierra")
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

	@Column(name="bol_provicional")
	public boolean isProvisional() {
		return provisional;
	}

	public void setProvisional(boolean provisional) {
		this.provisional = provisional;
	} 

	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			TipoDocumentoTierra cuenta = (TipoDocumentoTierra) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}


}