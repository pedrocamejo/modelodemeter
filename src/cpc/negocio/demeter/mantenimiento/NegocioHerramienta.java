package cpc.negocio.demeter.mantenimiento;

import java.util.List;

import org.hibernate.cache.ReadWriteCache.Item;

import cpc.modelo.demeter.administrativo.DetalleDocumentoFiscal;
import cpc.modelo.demeter.administrativo.DocumentoFiscal;
import cpc.modelo.demeter.basico.ArticuloVenta;
import cpc.modelo.demeter.mantenimiento.Herramienta;
import cpc.modelo.demeter.mantenimiento.HerramientaEquivalente;
import cpc.modelo.demeter.mantenimiento.HerramientaEquivalentePK;
import cpc.modelo.demeter.mantenimiento.EstadoFuncional;
import cpc.negocio.demeter.administrativo.NegocioFactura;
import cpc.persistencia.demeter.implementacion.administrativo.PerFactura;
import cpc.persistencia.demeter.implementacion.basico.PerArticuloVenta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerHerramienta;
import cpc.persistencia.demeter.implementacion.mantenimiento.PerEstadoFuncional;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class NegocioHerramienta {
	private static NegocioHerramienta 	negocio;
	private PerHerramienta			perherramienta;
	private List<Herramienta>			    herramientas;
	private Herramienta herramienta       ;
	
	
	
	private NegocioHerramienta(){
		/*SessionDao dao = SessionDao.getInstance();
		dao.test();
		dao.newDaoGenerico(new PerFactura());*/ 
		perherramienta = new PerHerramienta(); 
	}
	
	public static NegocioHerramienta getInstance() {
		if (negocio == null)
			negocio = new NegocioHerramienta();
		return negocio;
		
	}

	public PerHerramienta getPerHerramienta() {
		return perherramienta;
	}
	public void setPerHerramienta(PerHerramienta perherramienta) {
		this.perherramienta = perherramienta;
	}

	public List<Herramienta> getTodos() throws ExcFiltroExcepcion {
		
		return new PerHerramienta().getAll();
	}
	public void setHerramientas(List<Herramienta> herramientas) {
		this.herramientas = herramientas;
	}
	public Herramienta getHerramienta() {
		return herramienta;
	}
	public void setHerramienta(Herramienta herramienta) {
		this.herramienta = herramienta;
	}
  	
	public List<HerramientaEquivalente> getHerramientaEquivalentes(Herramienta herramienta) {
		return new PerHerramienta().getEquivalente(herramienta);
	}
	
	public void guardar(Herramienta herramienta) throws Exception{
	//	boolean a = herramienta.getherramientaEquivalente().isEmpty();
	//	boolean b = (herramienta.getherramientaEquivalente().size()==0);
		boolean c = herramienta.getHerramientaEquivalente()==null;
		
		
		if (!c){
			for (HerramientaEquivalente item :herramienta.getHerramientaEquivalente()){
				item.setHerramientaOri(herramienta);
				item.setId(new HerramientaEquivalentePK(item.getHerramientaEq().getId(), item.getHerramientaOri().getId()));
				}
			perherramienta.guardar(herramienta );
		}else
			perherramienta.guardar(herramienta );
	}
	
	public List<ArticuloVenta> getArticulosherramienta() throws ExcFiltroExcepcion {
		return new PerArticuloVenta().getAllArticuloHerramienta();
	}
	
	public List<ArticuloVenta> getArticulosSinherramienta() throws ExcFiltroExcepcion {
		return new PerArticuloVenta().getAll();
	}
	
	public Herramienta getherramientaArticulo(ArticuloVenta articulo) throws ExcFiltroExcepcion {
		return perherramienta.getHerramientaarticulo(articulo);
	}
	
	
}