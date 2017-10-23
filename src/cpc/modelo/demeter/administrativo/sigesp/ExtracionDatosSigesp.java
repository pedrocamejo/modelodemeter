package cpc.modelo.demeter.administrativo.sigesp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;


@Entity
@Table(name="tbl_dem_extracionDatosSigesp")
@Audited
public class ExtracionDatosSigesp {

	
	public static String RUTA = "/home/rchirinos/DemeterFile/ExtracionDatosSigesp/";
	private Integer		id;
	private String 		nombre;
	private Integer		mes;
	private Integer		ano;
	private String 		tamano;
	private String 		usuario;
	private String 		nombreSede;
	
	
	@SequenceGenerator(name="seqExtracionDatosSigesp", sequenceName="seqExtracionDatosSigesp", allocationSize=1)
	@Id @GeneratedValue(generator="seqExtracionDatosSigesp")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable=false,unique=true)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(nullable=false)
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}

	@Column(nullable=false)
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	@Column(nullable=false)
	public String getTamano() {
		return tamano;
	}
	public void setTamano(String tamano) {
		this.tamano = tamano;
	}
	
	@Column(nullable=false)
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Transient
	public String getFilename() {
		// TODO Auto-generated method stub
		return (RUTA+nombre).trim();
	}

	@Column(nullable=false)
	public String getNombreSede() {
		return nombreSede;
	}
	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

}
