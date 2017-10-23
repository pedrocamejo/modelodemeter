package prueba;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkex.zul.Jasperreport;

import cpc.ares.interfaz.IMenu;
import cpc.ares.modelo.Sede;
import cpc.ares.modelo.UnidadFuncional;
import cpc.ares.modelo.Usuario;
import cpc.zk.componente.interfaz.IZkAplicacion;
import cpc.zk.componente.ventanas.CompLogin;


public class AuditorUsuario implements IZkAplicacion {

	@Override
	public void mostrarError(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarInformacion(String informacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarConfirmacion(String confirmacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarImpresion(String pregunta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getContextProperty(String caracteristica) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void limpiarEscritorio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarInfUsuario(String usuario, Sede sede) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarInfUsuario(String usuario, Sede sede,
			List<UnidadFuncional> unidades) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarInfUsuario(String usuario, Sede sede, String foto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reiniciarInfUsaurio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sede getSede() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getdatosUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cambiarFuete(int tamano) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configurarEscritorio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restablecerEscritorio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario getUsuario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSede(Sede sede) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean conAccesoMultisede(int idUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void agregar(Component arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agregarAEscritorio(Component arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agregarHija(Component arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agregarMenu(Component arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void agregarReporte() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doAfterCompose(Component arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIp() {
		// TODO Auto-generated method stub
		return "localChos :-D";
	}

	@Override
	public CompLogin getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNombreUsuario() {
		// TODO Auto-generated method stub
		return "Prueba";
	}

	@Override
	public Jasperreport getReporte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getSelf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getXLSParameters(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEscritorio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogin(CompLogin arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMenuIni(IMenu arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReporte(Jasperreport arg0) {
		// TODO Auto-generated method stub
		
	}

}
