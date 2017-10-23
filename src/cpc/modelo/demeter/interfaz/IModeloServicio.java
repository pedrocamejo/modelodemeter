package cpc.modelo.demeter.interfaz;

import java.util.List;

public interface IModeloServicio<T> {
	public List<T> getAll();
	public void grabar(T objeto);
	public void borrar(T objeto);
	public void buscar(T objeto);
	public void buscar(int id);
}
