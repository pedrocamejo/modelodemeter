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
@Table(name="saf_transferencias", schema="sigesp")
public class TransferenciaActivo extends DetalleMovimiento implements Serializable{

	private static final long serialVersionUID = -8773904495801160932L;

	private Almacen						almacenAnterior, almacenActual;
	
	@OneToOne
	@JoinColumn(name="int_almacenanterior")
	public Almacen getAlmacenAnterior() {
		return almacenAnterior;
	}
	public void setAlmacenAnterior(Almacen almacenAnterior) {
		this.almacenAnterior = almacenAnterior;
	}
	
	@OneToOne
	@JoinColumn(name="int_almacenactual")
	public Almacen getAlmacenActual() {
		return almacenActual;
	}
	public void setAlmacenActual(Almacen almacenActual) {
		this.almacenActual = almacenActual;
	}
	
	@Transient
	public String getNombreAlmacenActual(){
		return getAlmacenActual().getNombre();
	}
	
	@Transient
	public String getNombreAlmacenAnterior (){
		return getAlmacenAnterior().getNombre();
	}
	
	public boolean equals(Object objeto){
		try{
			TransferenciaActivo cuenta = (TransferenciaActivo) objeto;
			if (cuenta.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
