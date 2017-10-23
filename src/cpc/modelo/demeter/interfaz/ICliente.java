package cpc.modelo.demeter.interfaz;

import java.util.List;

import cpc.modelo.ministerio.basico.Telefono;





public interface ICliente extends IEnte{
	public void agregarDireccion(String direccion);
	public void eliminarDireccion();
	public void editarDireccion(String codDireccion);
	public void agregarTelefono(Telefono telefono);
	public void editarTelefono(Telefono telefono);
	public void eliminarTelefono();
	public List<String> getDireccion();
	public List<Telefono> getTelefono();
	public void setIndiceDireccion(int indiceDireccion);
	public void setIndiceTelefono(int indiceTelefono);
}
