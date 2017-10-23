package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Audited @Entity
@Table(name="tbl_dem_cod_telefono")
public class CodigoArea implements Serializable{
	
	private static final long serialVersionUID = -8153419097222161027L;
	
	public static final String[] 	TIPOS = {"Movil", "Fijo", "TeleFax"};	
	private Integer 	id;
	private Integer 	tipo;
	private String 		codigoArea;
	private String 		descripcion;
	
	public CodigoArea(){
		
	}
	
	public CodigoArea(int id, int tipo, String codigoArea, String descripcion){
		this.id = id;
		this.tipo = tipo;
		this.codigoArea = codigoArea;
		this.descripcion = descripcion;
		
	}
	
	@SequenceGenerator(name="codigoArea_Gen", sequenceName="tbl_dem_codtelefono_seq_idcodare_seq", initialValue=1, allocationSize=1 )
	@Id @GeneratedValue( generator="codigoArea_Gen")
	@Column(name="seq_idcodare", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="int_tiptel")
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	@Column(name="str_codare",  nullable=false)
	public String getCodigoArea() {
		return codigoArea;
	}
	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}
	
	@Column(name="str_descodare",  nullable=true)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Transient
	public String getStrTipo(){
		return TIPOS[tipo];
	}
	
	public boolean equals(Object objeto){
		try{
			CodigoArea cuenta = (CodigoArea) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 

	public String toString(){
		return codigoArea;
	}

}
