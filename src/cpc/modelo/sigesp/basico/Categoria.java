package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Audited @Entity
@Table (name="siv_categoria", schema = "sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"str_idcategoria"})})
public class Categoria implements Serializable{

	private static final long serialVersionUID = -7790026423775243620L;
	private String 	codigoCategoria;
	private String 	descripcionCategoria;
	private boolean estatus;
	private boolean implemento;
	private boolean maquinaria;
	
	private List<Activo> activos;
		
	public Categoria() {
		super();
	}
	
	@Column(name="str_idcategoria")
	@Id
	public String getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(String codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}
	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
	}
	
	@Column(name="bol_estatus")
	public boolean isEstatus() {
		return estatus;
	}
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	@Column(name="bol_esimplemento")
	public boolean isImplemento() {
		return implemento;
	}

	public void setImplemento(boolean implemento) {
		this.implemento = implemento;
	}

	@Column(name="bol_esmaquinaria")
	public boolean isMaquinaria() {
		return maquinaria;
	}

	public void setMaquinaria(boolean maquinaria) {
		this.maquinaria = maquinaria;
	}
	
	@OneToMany(targetEntity=Activo.class, mappedBy="categoria")
	public List<Activo> getActivos() {
		return activos;
	}
	public void setActivos(List<Activo> activos) {
		this.activos = activos;
	}

	public boolean equals(Object objeto) {
		try {
			Categoria categoria = (Categoria) objeto;
			if (categoria.getCodigoCategoria().equals(categoria))
				return true;
			else
				return false;
		} catch (Exception e){
			return false;
		}
	}
	
	@Override
	public String toString() {		
		return descripcionCategoria.toUpperCase();
	}


}
