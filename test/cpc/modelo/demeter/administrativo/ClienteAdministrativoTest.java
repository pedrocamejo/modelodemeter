package cpc.modelo.demeter.administrativo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cpc.modelo.demeter.vistas.ClienteAdministrativoView;
import cpc.negocio.demeter.administrativo.NegocioClienteAdministrativo;

public class ClienteAdministrativoTest {

	@Test
	public void test() {
		NegocioClienteAdministrativo servicios = NegocioClienteAdministrativo.getInstance();
		List<ClienteAdministrativoView> clientes=  servicios.getTodosView();
		assertNotEquals(clientes.size(),0);
		ClienteAdministrativo cliente = servicios.findById(clientes.get(0).getId());
		assertEquals(cliente.getId(),clientes.get(0).getId());
		assertEquals(servicios.findByIdView(cliente.getId()).getId(),clientes.get(0).getId());
	}
	 
}
