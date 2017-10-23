package cpc.modelo.sigesp.basico;

import java.io.Serializable;

public class ConfiguracionSede implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6886642054389862792L;
	private Integer		idControl;
	private Integer		idUnidadFuncional;
	private String		codigoEmpresa;
	private String		codigoSede;
	private String		codigoUnidadAdministrativa;
	
	public ConfiguracionSede(){
		super();
	}
	
	public Integer getIdControl() {
		return idControl;
	}
	public void setIdControl(Integer idControl) {
		this.idControl = idControl;
	}
	public Integer getIdUnidadFuncional() {
		return idUnidadFuncional;
	}
	public void setIdUnidadFuncional(Integer idUnidadFuncional) {
		this.idUnidadFuncional = idUnidadFuncional;
	}
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}
	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}
	public String getCodigoSede() {
		return codigoSede;
	}
	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
	}
	public String getCodigoUnidadAdministrativa() {
		return codigoUnidadAdministrativa;
	}
	public void setCodigoUnidadAdministrativa(String codigoU) {
		this.codigoUnidadAdministrativa = codigoU;
	}
	
}
