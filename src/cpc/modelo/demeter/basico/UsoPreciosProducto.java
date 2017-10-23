package cpc.modelo.demeter.basico;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import cpc.modelo.demeter.administrativo.Impuesto;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.basico.TipoProductor;
import cva.pc.demeter.utilidades.Formateador;

public class UsoPreciosProducto {
	

	private List<TipoProductor>	  	tiposProductores;
	private double					impuesto, iva, precio, total;
	private IProducto				produto;
	
	
	public UsoPreciosProducto() {
		super();
		tiposProductores = new ArrayList<TipoProductor>();
	}

	
	public UsoPreciosProducto(double impuesto) {
		super();
		this.impuesto = impuesto;
		tiposProductores = new ArrayList<TipoProductor>();
	}

	public UsoPreciosProducto(Impuesto impuesto) {
		super();
		tiposProductores = new ArrayList<TipoProductor>();
		this.impuesto = impuesto.getPorcentaje();
	}
	
	public String getStrTipoProductores(){
		StringBuilder salida = new StringBuilder();
		for(TipoProductor item :tiposProductores){
			salida.append(String.format("%s, ", item.getDescripcion()));
		}
		salida.delete(salida.length()-2,salida.length());
		return salida.toString();
	}
	
	public List<TipoProductor> getTiposProductores() {
		return tiposProductores;
	}

	public void setTiposProductores(List<TipoProductor> tiposProductores) {
		this.tiposProductores = tiposProductores;
	}

	public double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
		iva = precio * impuesto/100;
		total = precio+iva;
	}

	public double getIva() {
		return iva;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
		iva = Math.round((precio * impuesto/100.00)*10000.00)/10000.00;
		total = Math.round((precio+iva)*10000.00)/10000.00;
	}

	public double getTotal() {
		return total;
	}
	
	public void addTipoProductor(TipoProductor tipo) {
		tiposProductores.add(tipo);
	}
	
	public String getStrIva() {
		return Formateador.formatearMoneda(iva);
	}
	
	public String getStrTotal() {
		return Formateador.formatearMoneda(total);
	}
	
	public static List<UsoPreciosProducto> getListaPrecios(Set<PrecioProducto> mapa, IProducto producto){
		List<UsoPreciosProducto> lista = getListaPrecios(mapa,producto.getImpuesto());
		for (UsoPreciosProducto item : lista) {
			item.setProduto(producto);
		}
		return lista;
	}
	
	
	public static List<UsoPreciosProducto> getListaPrecios(Set<PrecioProducto> mapa, Impuesto impuesto){
		if (mapa == null) return null;
		Set<TipoProductor> productores;
		UsoPreciosProducto detalle;
		List<UsoPreciosProducto> salida = new ArrayList<UsoPreciosProducto>();
		Map<Double, Set<TipoProductor>> salidaTmp = new HashMap<Double, Set<TipoProductor>>();
		for (PrecioProducto dato: mapa) {
			if (salidaTmp.containsKey(dato.getPrecio())){
				productores = salidaTmp.get(dato.getPrecio());
				productores.add(dato.getTipoCliente());
			}else{
				productores = new HashSet<TipoProductor>();
				productores.add(dato.getTipoCliente());
				salidaTmp.put(dato.getPrecio(), productores);
			}
		}
		Set<Double> precios = salidaTmp.keySet();
		for (Double precio : precios) {
			detalle = new UsoPreciosProducto(impuesto);
			detalle.setPrecio(precio);
			for(TipoProductor tipoProductor: salidaTmp.get(precio)){
				if (tipoProductor != null)
					detalle.addTipoProductor(tipoProductor);
			}
			salida.add(detalle);
		}
		return salida;
	}
	
	public static void getMapaPrecios(Set<PrecioProducto> mapa, List<UsoPreciosProducto> lista, Producto producto){
		if (mapa == null)
			mapa = new HashSet<PrecioProducto>();
		Map<String, PrecioProducto> mapaTmp = new HashMap<String, PrecioProducto>();
		for (PrecioProducto item : mapa) {
			mapaTmp.put(item.getIdCompuesto(), item);
			System.out.printf("Guardando Mapa-%s--%d--%d--%d\n", item.getIdCompuesto(), item.getId(), item.getProducto().getId(), item.getTipoCliente().getId());
		}
		String id;
		PrecioProducto dato;
		for (UsoPreciosProducto item : lista) {
			for (TipoProductor itemProductor : item.getTiposProductores()) {
				id = String.format("%d-%d", producto.getId(), itemProductor.getId());
				System.out.printf("Accesando Mapa-%sd--%d--%d\n", id.hashCode(),  producto.getId(), itemProductor.getId());
				if (!mapaTmp.containsKey(id)){
					dato = new PrecioProducto();
					dato.setProducto(producto);
					dato.setTipoCliente(itemProductor);
					dato.setPrecio(item.getPrecio());
					mapaTmp.put(id, dato);
				}else{
					dato = mapaTmp.get(id);
					dato.setPrecio(item.getPrecio());
				}
			}
		}
		Set<String> indices = mapaTmp.keySet(); 
		for (String item : indices) {
			if (!mapa.contains(mapaTmp.get(item))){
				System.out.printf("%s\n",mapaTmp.get(item).getTipoCliente().getId());
				mapa.add(mapaTmp.get(item));
			}
		}
		producto.setPrecios(mapa);
	}
	
	public static boolean verificaTodosTiposProductor(List<TipoProductor> tiposProductor, List<UsoPreciosProducto> lista){
		
		List<TipoProductor> tipopro2; 
		for (UsoPreciosProducto itemExt: lista){
			tipopro2 = itemExt.getTiposProductores();
			for (TipoProductor item: tipopro2){
				tiposProductor.remove(item);
			}
		}
		return true && (tiposProductor.size()== 0) ;
	}


	public IProducto getProduto() {
		return produto;
	}


	public void setProduto(IProducto produto) {
		this.produto = produto;
	}
	
	
	public String getStrUnidadCobro() {
		if (produto == null) return "";
		if (produto instanceof Labor) {
			Labor labor = (Labor) produto;
			return labor.getStrUnidadMedidaCobro();
		}
		return "";
	}

	public String getStrUnidadGestion() {
		if (produto == null) return "";
		if (produto instanceof Labor) {
			Labor labor = (Labor) produto;
			return labor.getStrUnidadMedidagestion();
		}
		return "";
	}

	

}
