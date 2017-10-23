package cpc.negocio.demeter.mantenimiento;

import java.util.List;
import cpc.modelo.demeter.basico.UnidadMedida;
import cpc.modelo.demeter.mantenimiento.RepuestoEInsumo;
import cpc.modelo.sigesp.basico.Articulo;
import cpc.modelo.sigesp.basico.ClaseCCNU;
import cpc.modelo.sigesp.basico.FamiliaCCNU;
import cpc.modelo.sigesp.basico.ProductoCCNU;
import cpc.modelo.sigesp.basico.SegmentoCCNU;
import cpc.persistencia.demeter.implementacion.PerUnidadMedida;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerRepuestoEInsumo;
import cpc.persistencia.sigesp.implementacion.PerArticulo;
import cpc.persistencia.sigesp.implementacion.PerClaseCCNU;
import cpc.persistencia.sigesp.implementacion.PerFamiliaCCNU;
import cpc.persistencia.sigesp.implementacion.PerProductoCCNU;
import cpc.persistencia.sigesp.implementacion.PerSegmentoCCNU;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioRepuestoEInsumo{
	
	private static NegocioRepuestoEInsumo 	negocio;
	
	
	private PerRepuestoEInsumo			persistencia;
	private List<RepuestoEInsumo>		repuestos;
	private RepuestoEInsumo				repuesto;
	
	private NegocioRepuestoEInsumo(){
		persistencia = new PerRepuestoEInsumo(); 
	}

	public  static synchronized NegocioRepuestoEInsumo getInstance() {
		if (negocio == null)
			negocio = new NegocioRepuestoEInsumo();
		return negocio;
	}

	public List<RepuestoEInsumo> getTodos() throws ExcFiltroExcepcion{
		List<RepuestoEInsumo> maquinarias = null;
		try {
			maquinarias = persistencia.getAll();
		} catch (ExcFiltroExcepcion e) {
			e.printStackTrace();
		}
		return maquinarias;
	}

	public static NegocioRepuestoEInsumo getNegocio() {
		return negocio;
	}

	public static void setNegocio(NegocioRepuestoEInsumo negocio) {
		NegocioRepuestoEInsumo.negocio = negocio;
	}

	public PerRepuestoEInsumo getPerMaquinaria() {
		return persistencia;
	}

	public void setPerRepuestoEInsumo(PerRepuestoEInsumo perMaquinaria) {
		this.persistencia = perMaquinaria;
	}
	public List<RepuestoEInsumo> getRepuestosEInsumos() {
		return repuestos;
	}

	public void setRepuestoEInsumo(List<RepuestoEInsumo> maquinarias) {
		this.repuestos = maquinarias;
	}
	public RepuestoEInsumo getRepuestoEInsumo(Integer indice) {
		return persistencia.buscarId(indice);
	}
	
	/*public void setRepuestoEInsumo(RepuestoEInsumo maquinaria) {
		this.repuesto = maquinaria;
	}*/
	
	public void guardar() throws Exception{
		persistencia.guardar(repuesto, repuesto.getId());
	}
	
	public void eliminar() throws Exception{
		persistencia.borrar(repuesto);
	}
	
	public List<Articulo> getArticulos() throws ExcFiltroExcepcion{
		return new PerArticulo().getAll();
	}
	
	public List<ProductoCCNU> getProductosCCNU() throws ExcFiltroExcepcion{
		return new PerProductoCCNU().getAllProductosCCNUUso();
	}
	
	public List<ClaseCCNU> getClasesCCNU(FamiliaCCNU familia){
		return new PerFamiliaCCNU().getClases(familia);
	}
	
	public List<SegmentoCCNU> getSegmentosCCNU() throws ExcFiltroExcepcion{
		return new PerSegmentoCCNU().getAllArticulosUso();
	}
	
	public List<ProductoCCNU> getProductosCCNU(ClaseCCNU clase){
		return new PerClaseCCNU().getProductos(clase);
	}
	
	public List<UnidadMedida> getUnidadesMedida() throws ExcFiltroExcepcion{
		return new PerUnidadMedida().getAll();
	}
	

	public List<FamiliaCCNU> getFamiliasCCNU(SegmentoCCNU segmento) {
		return new PerSegmentoCCNU().getFamilias(segmento);
	}
	
	public List<Articulo> getArticulosCCNU(ProductoCCNU producto) {
		return new PerProductoCCNU().getArticulos(producto);
	}
	
	
}
