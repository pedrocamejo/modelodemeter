package cpc.negocio.ministerio;

import java.io.Serializable;
import java.util.List;

import cpc.modelo.demeter.interfaz.IDaoGenerico;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public interface INegocioGenerico <T, K extends IDaoGenerico<T, PK>, PK> {  
	   
	public List<T> getTodos() throws ExcFiltroExcepcion;  
	public  T buscarId(Serializable id); 
	public  K getPersistencia();
	public  T getModelo();
	public  void setModelo(T modelo);
	public  void guardar(PK dato)  throws Exception;
	public  void borrar()  throws Exception;  

}

