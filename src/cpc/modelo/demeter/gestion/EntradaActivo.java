package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.sigesp.basico.Almacen;


@Audited @Entity
@PrimaryKeyJoinColumn(name="int_iddetamovi")
@Table(name="saf_entradas", schema="sigesp")
public class EntradaActivo extends DetalleMovimiento implements Serializable{

	private static final long serialVersionUID = 8671268198714353483L;
	private Almacen			almacen;
	
	
	
	@OneToOne
	@JoinColumn(name="int_idalmacen")
	public Almacen getAlmacen() {
		return almacen;
	}
	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}
	
	@Transient
	public String getNombreAlmacen(){
		return getAlmacen().getNombre();
	}
	
	public boolean equals(Object objeto){
		try{
			EntradaActivo cuenta = (EntradaActivo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}

}
