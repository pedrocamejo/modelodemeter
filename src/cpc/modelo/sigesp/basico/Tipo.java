package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Audited @Entity
@Table (name="siv_tipo", schema = "sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"str_idcategoria","str_idtipo"})})
public class Tipo implements Serializable{

	private static final long serialVersionUID = -6597944244916647427L;
	private String 		codigoTipo;
	private Categoria 	categoria;
	private String 		descripcionTipo;
	
	
	public Tipo() {
		super();
	}
	
	@Id
	@Column(name = "str_idtipo")
	public String getCodigoTipo() {
		return codigoTipo;
	}
	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}
	
	@OneToOne
	@JoinColumn(name="str_idcategoria")
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcionTipo() {
		return descripcionTipo;
	}
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	
	public boolean equals(Object objeto) {
		try{
			Tipo tipo = (Tipo) objeto;
			if (tipo.getCodigoTipo().equals(codigoTipo))
				return true;
			else
				return false;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String toString() {
		return descripcionTipo.toUpperCase();
	}
}
