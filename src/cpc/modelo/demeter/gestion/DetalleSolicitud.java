package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import org.hibernate.envers.Audited;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import cpc.modelo.demeter.basico.Producto;
import cpc.modelo.demeter.interfaz.IProducto;

@Audited @Entity
@Table(name = "tbl_dem_solicituddetalle", schema = "gestion")
public class DetalleSolicitud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3710161042892297413L;

	private Integer id;
	private Solicitud solicitud;
	private IProducto producto;
	private Double cantidad;
	private Double pases;
	private Boolean prestado;
	private List<UnidadSolicitada> solicitado;

	@Column(name = "seq_idrenglon")
	@SequenceGenerator(name = "SeqDetalleSolicitud", sequenceName = "gestion.tbl_dem_solicituddetalle_seq_idrenglon_seq", allocationSize = 1)
	@Id
	@GeneratedValue(generator = "SeqDetalleSolicitud")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "int_idsolicitud")
	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	@ManyToOne(targetEntity = Producto.class)
	@JoinColumn(name = "int_idproducto")
	public IProducto getProducto() {
		return producto;
	}

	public void setProducto(IProducto producto) {
		this.producto = producto;
	}

	@Column(name = "dbl_cantidad")
	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	@Column(name = "dbl_pase")
	public Double getPases() {
		return pases;
	}

	public void setPases(Double pases) {
		this.pases = pases;
	}

	
	
	//@JoinColumn(name="int_idrenglon")
	//@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	//@JoinTable(schema="gestion", name = "tbl_dem_solicituddetalleunidad", 
	//joinColumns = @JoinColumn(name = "int_idrenglon"), 
	//inverseJoinColumns = @JoinColumn(name = "seq_idrenglonunidad"))

	//	@OneToMany(mappedBy = "detalleSolicitud", targetEntity = UnidadSolicitada.class, cascade=CascadeType.ALL)
	//@Cascade({org.hibernate.annotations.CascadeType.DELETE})
	@OneToMany( targetEntity = UnidadSolicitada.class, cascade  = CascadeType.REFRESH ,fetch=FetchType.EAGER,mappedBy="detalleSolicitud")
	public List<UnidadSolicitada> getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(List<UnidadSolicitada> solicitado) {
		this.solicitado = solicitado;
	}

	@Column(name = "bol_prestado")
	public Boolean getPrestado() {
		return prestado;
	}

	public void setPrestado(Boolean prestado) {
		this.prestado = prestado;
	}

	@Transient
	public String getStrUnidadMedida() {
		Iterator<UnidadSolicitada> it = solicitado.iterator();
		while (it.hasNext()) {
			UnidadSolicitada usTemp = it.next();
			if (usTemp.getId().equals(id))
				return usTemp.getStrUnidad() + " ["
						+ usTemp.getUnidad().getAbreviatura().trim() + "]";
		}
		return "";
	}

	@Transient
	public Double getCantidadSolicitada() {
		Iterator<UnidadSolicitada> it = solicitado.iterator();
		while (it.hasNext()) {
			UnidadSolicitada usTemp = it.next();
			if (usTemp.getId().equals(id))
				return usTemp.getCantidad();
		}
		return Double.valueOf(0);
	}

	public boolean equal(Object a) {
		try {
			DetalleSolicitud obj = (DetalleSolicitud) a;
			if (obj.getId().equals(this.getId()))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cantidad == null) ? 0 : cantidad.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pases == null) ? 0 : pases.hashCode());
		result = prime * result
				+ ((prestado == null) ? 0 : prestado.hashCode());
		result = prime * result
				+ ((producto == null) ? 0 : producto.hashCode());
		result = prime * result
				+ ((solicitado == null) ? 0 : solicitado.hashCode());
		result = prime * result
				+ ((solicitud == null) ? 0 : solicitud.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleSolicitud other = (DetalleSolicitud) obj;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pases == null) {
			if (other.pases != null)
				return false;
		} else if (!pases.equals(other.pases))
			return false;
		if (prestado == null) {
			if (other.prestado != null)
				return false;
		} else if (!prestado.equals(other.prestado))
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (solicitado == null) {
			if (other.solicitado != null)
				return false;
		} else if (!solicitado.equals(other.solicitado))
			return false;
		if (solicitud == null) {
			if (other.solicitud != null)
				return false;
		} else if (!solicitud.equals(other.solicitud))
			return false;
		return true;
	}
	
//	@Transient
//	public void remove(Group group){
//	    group.setUserAccount(null);
//	    groups.remove(group);
//	}


}
