package cpc.modelo.negocio.demeter.cotizacion;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cpc.modelo.demeter.administrativo.ClienteAdministrativo;
import cpc.modelo.demeter.administrativo.CotizacionVialidad;
import cpc.modelo.demeter.administrativo.DetalleContrato;
import cpc.modelo.demeter.administrativo.EstadoContrato;
import cpc.modelo.demeter.administrativo.TipoContrato;
import cpc.modelo.demeter.interfaz.IProducto;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Sede;
import cpc.negocio.demeter.administrativo.NegocioCotizacion;
import cpc.negocio.demeter.administrativo.NegocioCotizacionVialidad;
import cpc.negocio.sigesp.NegocioSede;
import cpc.zk.componente.controlador.ContCatalogo;
import cpc.zk.componente.excepciones.ExcDatosInvalidos;
import cva.pc.demeter.excepciones.ExcFiltroExcepcion;

public class CotizacionTest {

	
	private NegocioCotizacionVialidad ng =NegocioCotizacionVialidad.getInstance();
	private NegocioSede 		ns = NegocioSede.getInstance();

	@Test
	public void test() throws SQLException, Exception {
		
		CotizacionVialidad vialidad = new CotizacionVialidad();
		Sede sede = ns.getTodos().get(4);

		List<ClienteAdministrativo> clientes = ng.getClientesAdministrativos();
		List<UbicacionDireccion> ubicaciones = ng.getUbicaciones();
		List<TipoContrato> tipoContratos= ng.getTiposContrato();
		vialidad.setPagador(clientes.get(1).getCliente());
		vialidad.setBeneficiario(clientes.get(1).getCliente());
		vialidad.setUbicacion(ubicaciones.get(0));
		vialidad.setTipoContrato(tipoContratos.get(0));
		vialidad.setDiasVigencia(22);
		vialidad.setSede(sede);
		ng.guardar(vialidad);
		
		
		
	}

}
