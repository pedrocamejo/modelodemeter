package cpc.negocio.ministerio;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.interfaz.IDaoGenerico;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public abstract class  NegocioGenerico<T, K extends IDaoGenerico<T, PK>, PK> implements INegocioGenerico<T, K , PK>, Serializable{
	

	private static final long serialVersionUID = -8511433673900100326L;
	
	private K					persistencia;
	private T					modelo;
	private List<T>				modelos;
		

	public K getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(K persistencia) {
		this.persistencia = persistencia;
	}

	public T getModelo() {
		return modelo;
	}

	public void setModelo(T modelo) {
		this.modelo = modelo;
	}

	public List<T> getModelos() {
		return modelos;
	}

	public void setModelos(List<T> modelos) {
		this.modelos = modelos;
	}

	public void borrar()  throws Exception{
		persistencia.borrar(modelo);
		
	}

	public T buscarId(Serializable id) {
		return persistencia.buscarId(id);
	}

	public List<T> getTodos() throws ExcFiltroExcepcion {
		return persistencia.getAll();
	}

	public void guardar(PK dato) throws Exception {
		persistencia.guardar(modelo, dato);
		
	}

	public Object clone() throws CloneNotSupportedException {
    	throw new CloneNotSupportedException(); 
	}

}
