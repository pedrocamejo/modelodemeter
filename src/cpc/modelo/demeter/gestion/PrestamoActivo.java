package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import cpc.modelo.ministerio.gestion.ProductorJuridico;
import cpc.modelo.sigesp.basico.Almacen;


@Audited @Entity
@PrimaryKeyJoinColumn(name="int_iddetamovi")
@Table(name="saf_prestamos", schema="sigesp")
public class PrestamoActivo extends DetalleMovimiento implements Serializable{

	private static final long serialVersionUID = 1118562167513786695L;
	private ProductorJuridico 	enteJuridico;
	private Almacen				almacen;
	
	@ManyToOne
	@JoinColumn(name="int_idproductor")	
	public ProductorJuridico getEnteJuridico() {
		return enteJuridico;
	}
	public void setEnteJuridico(ProductorJuridico enteJuridico) {
		this.enteJuridico = enteJuridico;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idalmacen")
	public Almacen getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	
	@Transient
	public String getNombreEnteJuridico(){
		return getEnteJuridico().getNombres();
	}
	
	@Transient
	public String getNombreAlmacen(){
		return getAlmacen().getNombre();
	}
	
	public boolean equals(Object objeto){
		try{
			PrestamoActivo cuenta = (PrestamoActivo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
