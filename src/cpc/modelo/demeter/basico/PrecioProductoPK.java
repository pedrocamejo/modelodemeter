package cpc.modelo.demeter.basico;

import java.io.Serializable;

import cpc.modelo.ministerio.basico.TipoProductor;

public class PrecioProductoPK implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7391927865925431430L;
	private Producto		producto;
	private TipoProductor	tipoCliente;
        
    public PrecioProductoPK() {
		super();
	}
    
	public PrecioProductoPK(Producto producto, TipoProductor tipoCliente) {
		super();
		this.producto = producto;
		this.tipoCliente = tipoCliente;
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PrecioProductoPK)) {
			return false;
		}
		PrecioProductoPK other = (PrecioProductoPK) o;
		return true
		&& (getProducto() == other.getProducto() && getTipoCliente() == other.getTipoCliente());
	}
	

	public int hashCode() {
		final int prime = 17;
		int result = 1;
		result = prime * result + (getProducto() == null ? 0 : getProducto().getId());
		result = prime * result + (getTipoCliente() == null ? 0 : getTipoCliente().getId());
		return result;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public TipoProductor getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoProductor tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
	
}
