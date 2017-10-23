package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.modelo.demeter.mantenimiento.Fabricante;
import cpc.modelo.demeter.mantenimiento.Lote;
import cpc.modelo.demeter.mantenimiento.ObjetoMantenimiento;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Tipo;
import cpc.persistencia.demeter.implementacion.PerCliente;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerLote;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEstadoFuncional;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerObjetoManetenimiento;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerFabricante;
import cpc.persistencia.sigesp.implementacion.PerActivo;
import cpc.persistencia.sigesp.implementacion.PerCategoria;
import cpc.persistencia.sigesp.implementacion.PerMarca;
import cpc.persistencia.sigesp.implementacion.PerModelo;
import cpc.persistencia.sigesp.implementacion.PerTipo;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioObjetoMantenimiento{
	
	
	private static NegocioObjetoMantenimiento 	negocio;
	private PerObjetoManetenimiento				persistencia;
	private List<ObjetoMantenimiento>			objetosMantenimiento;
	private ObjetoMantenimiento					objetoMantenimiento;
	
	private NegocioObjetoMantenimiento(){
		persistencia = new PerObjetoManetenimiento(); 
	}

	public  static synchronized NegocioObjetoMantenimiento getInstance() {
		if (negocio == null)
			negocio = new NegocioObjetoMantenimiento();
		return negocio;
	}

	public List<ObjetoMantenimiento> getTodos() throws ExcFiltroExcepcion{
		List<ObjetoMantenimiento> bien = null;
		try {
			bien = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return bien;
	}

	public static NegocioObjetoMantenimiento getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioObjetoMantenimiento negocio) {
		NegocioObjetoMantenimiento.negocio = negocio;
	}

	public PerObjetoManetenimiento getPerObjetoManetenimiento() {
		return persistencia;
	}

	public void setPerObjetoManetenimiento(PerObjetoManetenimiento perBienProduccion) {
		this.persistencia = perBienProduccion;
	}

	public List<ObjetoMantenimiento> getObjetosMantenimiento() throws ExcFiltroExcepcion {
		this.objetosMantenimiento = this.persistencia.getAll();
		return objetosMantenimiento;
	}

	public void setObjetosMantenimiento(List<ObjetoMantenimiento> objetosMantenimiento) {
		this.objetosMantenimiento = objetosMantenimiento;
	}	
	
	public ObjetoMantenimiento getObjetoMantenimiento(Long indice) {
		return persistencia.buscarId(indice);
	}
	
	public void setBienProduccion(ObjetoMantenimiento bienProduccion) {
		this.objetoMantenimiento = bienProduccion;
	}
	public void guardar() throws Exception{
		persistencia.guardar(objetoMantenimiento, objetoMantenimiento.getId());
	}
	
	public void eliminar() throws Exception{
		persistencia.borrar(objetoMantenimiento);
	}
	
	public List<Activo> getActivosAlmacenados(Modelo modelo){
		return new PerActivo().getPorModeloConAlmacen(modelo);		 	
	}
	
	public List<Categoria> getCategorias() throws ExcFiltroExcepcion{
		return new PerCategoria().getAll();
	}
	
	public List<Tipo> getTiposPorCategoria(Categoria categoria) throws ExcFiltroExcepcion{
		return new PerTipo().getPorCategoria(categoria);
	}
	
	public List<Marca> getMarcas() throws ExcFiltroExcepcion{
		return new PerMarca().getTodosOrdenadoPorNombre();
	}
	
	public List<Modelo> getModelosPorMarca(Marca marca) throws ExcFiltroExcepcion{
		return new PerModelo().getPorMarca(marca);
	}
	
	public List<Lote> getLotes() throws ExcFiltroExcepcion{
		return new PerLote().getAll();
	}
	
	public List<Fabricante> getFabricantes() throws ExcFiltroExcepcion{
		return new PerFabricante().getAll();
	}
	
	public List<EstadoFuncional> getEstadoFuncional() throws ExcFiltroExcepcion{
		return new PerEstadoFuncional().getAll();
	}
	
	public List<Cliente> getClientes() throws ExcFiltroExcepcion{
		return new PerCliente().getAll();
	}
	
	public List<ObjetoMantenimiento> getObjetosMantenimientos() throws ExcFiltroExcepcion{
		return this.persistencia.getAll();
	}
	
	
}
