package cpc.modelo.demeter.interfaz;

import java.io.Serializable;
import java.util.List;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public interface IDaoGenerico <T, PK> {  
	   
	public List<T> getAll() throws ExcFiltroExcepcion;  
	public  T buscarId(Serializable id);  
	public  void guardar(T object, PK id) throws Exception;
	public  void borrar(T object) throws Exception;   

}

