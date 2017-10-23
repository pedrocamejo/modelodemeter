package cpc.modelo.demeter.gestion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Audited @Entity
@Table(name="saf_tipomovimiento",schema="sigesp")

public class TipoMovimiento implements Serializable {

	private static final long serialVersionUID = 2010054479353861029L;
    private Integer		idtipomovimiento;
    private String		descripcion;
    private boolean 	salida, traslado, entradaIncial, prestamo;
    
    public TipoMovimiento() {
		super();
	}
    
	public TipoMovimiento(int idtipomovimiento, String descripcion) {
		super();
		this.idtipomovimiento = idtipomovimiento;
		this.descripcion = descripcion;
	}
	
	@SequenceGenerator(name="Seq_idtipomovimiento", sequenceName="sigesp.saf_tipomovimientos_seq_ser_idtipomovimiento_seq", allocationSize=1)
	@Column(name="seq_ser_idtipomovimiento")
	@Id @GeneratedValue(generator="Seq_idtipomovimiento")
	public Integer getIdtipomovimiento() {
		return idtipomovimiento;
	}
	public void setIdtipomovimiento(Integer idtipomovimiento) {
		this.idtipomovimiento = idtipomovimiento;
	}
	
	@Column(name="str_nombre")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
	@Column(name = "bol_salida")
	public boolean isSalida() {
		return salida;
	}

	public void setSalida(boolean salida) {
		this.salida = salida;
	}

	@Column(name = "bol_traslado")
	public boolean isTraslado() {
		return traslado;
	}

	public void setTraslado(boolean traslado) {
		this.traslado = traslado;
	}

	@Column(name= "bol_entradainicial")
	public boolean isEntradaIncial() {
		return entradaIncial;
	}

	public void setEntradaIncial(boolean entradaIncial) {
		this.entradaIncial = entradaIncial;
	}

	@Column(name="bol_prestamo")
	public boolean isPrestamo() {
		return prestamo;
	}

	public void setPrestamo(boolean prestamo) {
		this.prestamo = prestamo;
	}

	public String toString(){
		return getDescripcion();
	}
	public boolean equals(Object objeto){
		try{
			TipoMovimiento cuenta = (TipoMovimiento) objeto;
			if (cuenta.getIdtipomovimiento().equals(idtipomovimiento))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
