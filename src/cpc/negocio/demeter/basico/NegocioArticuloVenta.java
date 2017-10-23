package cpc.negocio.demeter.basico;


import java.util.List;
import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.basico.ClaseArticulo;
import cpc.modelo.sigesp.basico.TipoArticuloSIGESP;
import cpc.modelo.sigesp.basico.UnidadMedidaSIGESP;
import cpc.negocio.ministerio.NegocioGenerico;
import cpc.persistencia.demeter.implementacion.PerIva;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.basico.PerClaseArticulo;
import cpc.persistencia.demeter.implementacion.basico.PerTipoProducto;
import cpc.persistencia.sigesp.implementacion.PerTipoArticulo;
import cpc.persistencia.sigesp.implementacion.PerUnidadMedida;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioArticuloVenta extends NegocioGenerico<ArticuloVenta, PerArticuloVenta, Integer>{

	
	private static final long serialVersionUID = -8312962389466752174L;
	private static NegocioArticuloVenta 				negocio;
	
	private NegocioArticuloVenta(){
		setPersistencia(new PerArticuloVenta());
	}
	
	public  static synchronized NegocioArticuloVenta getInstance() {
		if (negocio == null)
			negocio = new NegocioArticuloVenta();
		return negocio;
	}
	
	public void nuevo() throws ExcFiltroExcepcion{
		setModelo(new ArticuloVenta()); 
		getModelo().setTipoProducto(new PerTipoProducto().buscarId(1)); 
		getModelo().setActivo(true);
	}
	
	public List<ClaseArticulo> getClases() throws ExcFiltroExcepcion{
		return new PerClaseArticulo().getAll(); 
	}
	
	public void setArticulo(ArticuloVenta labor){
		super.setModelo((ArticuloVenta)getPersistencia().getDato(labor));
	}	
	

	public List<TipoArticuloSIGESP> getTipoArticulos() throws ExcFiltroExcepcion{
		return new PerTipoArticulo().getAll(); 
	}
	
	public List<UnidadMedidaSIGESP> getUnidades() throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getTodos(); 
	}
	
	public List<Impuesto> getImpuestos() throws ExcFiltroExcepcion{
		return new PerIva().getAll(); 
	}
	
	
	
/*	public void setPrecio(Double precio) throws ExcFiltroExcepcion {
		Set<PrecioProducto> precios;
		if (getModelo().getPrecios() == null){
			precios = new HashSet<PrecioProducto>();
			PrecioProducto item;
			List<TipoProductor> productores =new PerTipoProductor().getAll();
			for (TipoProductor itemCliente : productores) {
				item = new PrecioProducto();
				item.setProducto(getModelo());
				item.setTipoCliente(itemCliente);
				item.setPrecio(precio);
				precios.add(item);
			}
		}else{
			precios = getModelo().getPrecios(); 
			for (PrecioProducto itemCliente : precios) {
				itemCliente.setPrecio(precio);
			}
		}
		getModelo().setPrecios(precios);
	}*/
}
