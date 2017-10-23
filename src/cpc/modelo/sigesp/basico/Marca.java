package cpc.modelo.sigesp.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Audited @Entity
@Table (name="siv_marca", schema = "sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"str_idmarca"})})
public class Marca implements Serializable{

	private static final long serialVersionUID = 4212338147148092316L;
	private String 	codigoMarca;
	private String 	descripcion;
	private boolean estado;
	
	public Marca() {
		super();
	}
			
	@Column(name="str_idmarca")
	@Id
	public String getCodigoMarca() {
		return codigoMarca;
	}
	public void setCodigoMarca(String codigoMarca) {
		this.codigoMarca = codigoMarca;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name="bol_estatus")
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public boolean equals(Object objeto){
		try{
			Marca marca = (Marca) objeto;
			if (marca.getCodigoMarca().equals(codigoMarca))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	
	public String toString() {
		return descripcion.toUpperCase();
	}
}
