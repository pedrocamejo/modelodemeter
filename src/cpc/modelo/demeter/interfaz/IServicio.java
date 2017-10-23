package cpc.modelo.demeter.interfaz;


public interface IServicio {
	public int 		getIdServicio();
	public String 	getNombreServicio();   
	public int 		getIdUnidadMedida();
	public double 	getPrecio();
	public double 	getPrecioServicio();
	public int 		getIdTipoServicio();
	public int 		getCantidadPasesSolcicitado();
	public double 	getMedida();
	
	public void 	setIdServicio(int id);
	public void 	setNombreServicio(String nombreServicio);   
	public void 	setIdUnidadMedida(int unidadMedida);
	public void 	setPrecio(double precio);
	public void 	setCantidadPaseSolicitado(int cantidad);
	public void 	setMedida(double medida);
}
