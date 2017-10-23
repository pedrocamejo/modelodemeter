package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import cpc.modelo.sigesp.indice.BancoPK;


@Audited @Entity
@Table(name="scb_banco", schema="sigesp")
@IdClass(BancoPK.class)

public class Banco implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4779205144954962734L;
	private String codigoEmpresa;
	private String id;
	private String descripcion;
	
	
	public Banco(){
		super();
	}
	
	public Banco(String codigoEmpresa, String id, String descripcion){
		super();
		this.codigoEmpresa = codigoEmpresa;
		this.id = id;
		this.descripcion=descripcion;
	}
	
	@Id
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}
	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@Column(name="nomban")	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public String toString(){
		return descripcion;
	}
	
	public boolean equals(Object objeto){
		try{
			Banco banco = (Banco) objeto;
			if (banco.getId().equals(id) && banco.getCodigoEmpresa().equals(codigoEmpresa))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
