package cpc.negocio.demeter.gestion;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
 
import cpc.modelo.demeter.gestion.EstadoMaquinariaImpropia;
import cpc.modelo.demeter.gestion.ImplementoImpropio;
import cpc.modelo.demeter.gestion.ImplementoUnidad;
import cpc.modelo.demeter.gestion.MaquinariaImpropia;
import cpc.modelo.demeter.gestion.MaquinariaUnidad;
import cpc.modelo.ministerio.dimension.UnidadFuncional;
import cpc.modelo.sigesp.basico.Almacen;
import cpc.modelo.sigesp.basico.Categoria;
import cpc.modelo.sigesp.basico.Marca;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Tipo;
import cpc.negocio.ministerio.basico.NegocioUnidadFuncional;
import cpc.negocio.sigesp.NegocioAlmacen;

import cva.pc.demeter.excepciones.ExcFiltroExcepcion;


public class NegocioImplementacionTest {
	
	private NegocioImplementoImpropio negocioImplementoImpropio = NegocioImplementoImpropio.getInstance();
	private NegocioAlmacen negocioAlmacen = NegocioAlmacen.getInstance();
	private NegocioUnidadFuncional  negocioUnidadFuncional = NegocioUnidadFuncional.getInstance();
	
	
	@Before
	public void beforeToRun()
	{
		
	}

	/*@Test
	public void listado_vacio_Test() throws ExcFiltroExcepcion {
		assertEquals(0,negocioImplementoImpropio.getTodos().size());
	}
*/
	@Test
	public void agregarImplemento() throws Exception {
		Integer total = negocioImplementoImpropio.getTodos().size();
		
		List<Almacen> almacenes = negocioAlmacen.getAlmacenes();
		List<Marca> marcas = negocioImplementoImpropio.marcas();
		List<Modelo> modelos = negocioImplementoImpropio.modelos(marcas.get(0));
		List<Categoria> categorias = negocioImplementoImpropio.categorias();
		List<Tipo> tipos = negocioImplementoImpropio.tipos(categorias.get(0));
		List<UnidadFuncional> unidades = negocioUnidadFuncional.getTodos();
		
		
		assertNotSame("No hay almacenes Registrados. ",0, almacenes);
		assertNotSame("No hay marcas Registrados. ",0, marcas);
		assertNotSame("No hay modelos Registrados. ",0, modelos);
		assertNotSame("No hay categorias Registrados. ",0, categorias);
		assertNotSame("No hay tipos Registrados. ",0, tipos);

		ImplementoImpropio implemento = new ImplementoImpropio();
		implemento.setAlmacen(almacenes.get(0));
		implemento.setModelo(modelos.get(0));
		implemento.setMarca(marcas.get(0));
		implemento.setDesincorporado(false);
		implemento.setCategoria(categorias.get(0));
		implemento.setTipo(tipos.get(0));
		implemento.setSerialChasis("NMDIEN902J49");
		implemento.setSerialOtro("JCNRIN39NR9C");
		implemento.setProcedencia("JINDEIA923NJR89");
		implemento.setNombre("NO SE UN IMPLEMENTO XXX ");
		implemento.setTrasladado(true);
		implemento.setObservacion("sin observacion.");

		ImplementoUnidad implementoUnidad =new ImplementoUnidad();
		implementoUnidad.setOperativo(false);
		implementoUnidad.setImplementoImpropio(implemento);
		implemento.setImplementoUnidad(implementoUnidad);
		implemento.getImplementoUnidad().setUnidad(unidades.get(0));

		negocioImplementoImpropio.guardar(implemento);

		assertEquals(total+1,negocioImplementoImpropio.getTodos().size());
	}
	
	
	
	
	
}
