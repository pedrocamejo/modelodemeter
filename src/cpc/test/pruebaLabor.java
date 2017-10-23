package cpc.test;


import java.util.List;
import cpc.modelo.demeter.basico.Labor;
import cpc.modelo.demeter.basico.UsoPreciosProducto;
import cpc.negocio.demeter.basico.NegocioLabor;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class pruebaLabor {

	public static void main(String[] args) throws ExcFiltroExcepcion {
		
		NegocioLabor negocio = NegocioLabor.getInstance();
		List<Labor> lista = negocio.getTodos();
		try{
			List<UsoPreciosProducto> precios = negocio.getPreciosDetallados(lista);
			for (UsoPreciosProducto item : precios) {
				System.out.printf("\n\n%s %s %s \n", item.getProduto().getDescripcion(), item.getProduto().getStrClase(), item.getProduto().getStrTipo());
				System.out.printf("%s %.4f %.2f %.4f \n", item.getStrTipoProductores(), item.getPrecio(),item.getImpuesto(), item.getTotal());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}