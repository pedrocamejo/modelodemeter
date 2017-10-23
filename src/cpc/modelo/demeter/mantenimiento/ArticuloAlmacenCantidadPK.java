package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable

public class ArticuloAlmacenCantidadPK implements Serializable{
	



	/**
	 * 
	 */
	private static final long serialVersionUID = -6228448271586747589L;


	/**
	 * RepuestoEquivalente()
	 */
	



	

	
private Integer  idalmacen;

	
private Integer idobjeto;
	
	
	public ArticuloAlmacenCantidadPK() {
		super();
	}
	
	
	/*
	@JoinTable (name = "tbl_dem_repuesto",schema="mantenimiento",joinColumns = {
			@JoinColumn(referencedColumnName="int_idrepuestooriginal", name="int_idrepuesto"),
			@ JoinColumn(referencedColumnName="int_idrepuestoequivalente", name="int_idrepuesto")
			
	}
	, inverseJoinColumns = { 	@JoinColumn(referencedColumnName="int_idrepuestooriginal", name="int_idrepuesto"),
			@JoinColumn(referencedColumnName="int_idrepuestoequivalente", name="int_idrepuesto")}
	)
	*/


	/*
	@Basic
	@Column(name="int_idalmacen")
public Integer getIdAlmacen() {
		return idalmacen;
	}

	public void setIdalmacen(Integer idalmacen) {
		this.idalmacen = idalmacen;
	}


	@Basic          
	@Column(name="int_idrepuestoequivalente")
	public Integer getIdObjeto() {
		return idobjeto;
	}
	public void setIdObjeto(Integer idobjeto) {
		this.idobjeto = idobjeto;
	}

*/

	@Basic
	@Column(name="int_idalmacen")
	public Integer getIdalmacen() {
		return idalmacen;
	}


	public void setIdalmacen(Integer idalmacen) {
		this.idalmacen = idalmacen;
	}


	@Basic          
	@Column(name="int_idarticulo")
	public Integer getIdobjeto() {
		return idobjeto;
	}


	public void setIdobjeto(Integer idobjeto) {
		this.idobjeto = idobjeto;
	}
	
	
	
	public  ArticuloAlmacenCantidadPK(Integer idalmacen,Integer idobjeto) {
		super();
		this.idalmacen = idalmacen;
		this.idobjeto = idobjeto;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ArticuloAlmacenCantidadPK)) {
			return false;
		}
		ArticuloAlmacenCantidadPK other = (ArticuloAlmacenCantidadPK) o;
		return true
//		&& (getRepuestoOriginal().equals(other.getRepuestoOriginal()) && getRepuestoEquivalente().equals(other.getRepuestoEquivalente()));
				&& (getIdalmacen().equals(other.getIdalmacen()) && getIdobjeto().equals(other.getIdobjeto()));
	}
	

	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + (getIdalmacen() == null ? 0 : getIdalmacen().hashCode());
		result = prime * result + (getIdobjeto() == null ? 0 : getIdobjeto().hashCode());
		return result;
	}

	/*
	@Transient
	repuestoEquivalente
	@JoinColumn( table="mantenimiento.tbl_dem_repuesto", referencedColumnName="int_idrepuestooriginal", name="int_idrepuesto")
	

		
		
		
	
		



		public Repuesto getRepuestoOriginal() {
			return repuestoOriginal;
		}



		public void setRepuestoOriginal(Repuesto repuestoOriginal) {
			this.repuestoOriginal = repuestoOriginal;
		}

		@Transient
		@JoinColumn(table="mantenimiento.tbl_dem_repuesto", referencedColumnName="int_idrepuestoequivalente", name="int_idrepuesto")
		public Repuesto getRepuestoEquivalente() {
			return repuestoEquivalente;
		}



		public void setRepuestoEquivalente(Repuesto repuestoEquivalente) {
			this.repuestoEquivalente = repuestoEquivalente;
		}
	
	*/
	
	

}