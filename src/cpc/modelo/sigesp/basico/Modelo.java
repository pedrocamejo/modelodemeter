package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;


@Audited @Entity
@Table(name="siv_modelo", schema = "sigesp", uniqueConstraints={@UniqueConstraint(columnNames={"str_idmarca","str_idmodelo"})})
public class Modelo implements Serializable{

	private static final long serialVersionUID = -3578434823029918079L;
	private int 	idModelo;
	private String 	codigoModelo;
	private Marca 	marca;
	private String 	descripcionModelo;
	
	public Modelo(){
		super();
	}
	
	@Column(name="id_modelo")
	@Id
	public int getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(int idModelo) {
		this.idModelo = idModelo;
	}
	
	@Column(name="str_idmodelo")
	public String getCodigoModelo() {
		return codigoModelo;
	}
	public void setCodigoModelo(String codigoModelo) {
		this.codigoModelo = codigoModelo;
	}
	
	@ManyToOne
	@JoinColumn(name="str_idmarca")
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcionModelo() {
		return descripcionModelo;
	}
	public void setDescripcionModelo(String descripcionModelo) {
		this.descripcionModelo = descripcionModelo;
	}
	
	@Transient
	public String getDescripcionMarca(){
		return getMarca().getDescripcion();
	}
	
	public boolean equals(Object objeto) {
		try {
			Modelo modelo = (Modelo) objeto;
			if (modelo.getCodigoModelo().equals(codigoModelo))
				return true;
			else
				return false;
		} catch (Exception e){
			return false;
		}
	}
	
	
	public String toString() {	
		return descripcionModelo.toUpperCase()+" - " +marca.getDescripcion();
	}
}
