package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;


@Audited @Entity
@PrimaryKeyJoinColumn(name="int_iddetamovi")
@Table(name="saf_salidas", schema="sigesp")
public class SalidaActivo extends DetalleMovimiento implements Serializable{

	private static final long serialVersionUID = 1118562167513786695L;
	private UnidadAdministrativa 	unidadAdministrativa;
	private Almacen					almacen;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_coduniadm",referencedColumnName="coduniadm")
	})
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
	@OneToOne
	@JoinColumn(name="int_idalmacen")
	public Almacen getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	
	@Transient
	public String getNombreUnidadAdministrativa(){
		return getUnidadAdministrativa().getNombre();
	}
	
	@Transient
	public String getNombreAlmacen(){
		return getAlmacen().getNombre();
	}
	public boolean equals(Object objeto){
		try{
			SalidaActivo cuenta = (SalidaActivo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
