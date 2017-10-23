package cpc.modelo.demeter.sincronizacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;




@Entity
@Table(name = "tbl_dem_importart",schema = "sincronizacion")
public class Importar implements Serializable{

	 private Integer 			 id;
	 private SedeDemeter     	 sedeDemeter;
	 private String 			 md5; //tamaño del archivo 
	 private String 			 cedula; //cedula del mrs :_D 
	 private List<DetalleImportar> detalles = new ArrayList<DetalleImportar>(); 
	 private List<DetalleImportarError> detalleserror = new ArrayList<DetalleImportarError>();
	 
	@SequenceGenerator(name="SeqImportar", sequenceName="sincronizacion.tbl_dem_importart_id_seq", allocationSize=1)
	@Id
	@Column(name="id")
	@GeneratedValue(generator="SeqImportar")
	public Integer getId() {
		return id;
	}
	
 
	@Column
	public String getMd5() {
		return md5;
	}

	@Column
	public String getCedula() {
		return cedula;
	}

	@OneToMany(targetEntity=DetalleImportar.class,mappedBy="importar",cascade= CascadeType.ALL)
	public List<DetalleImportar> getDetalles() {
		return detalles;
	}
	@OneToMany(targetEntity=DetalleImportarError.class,mappedBy="importar",cascade=CascadeType.ALL)
	public List<DetalleImportarError> getDetalleserror() {
		return detalleserror;
	}


	public void setDetalleserror(List<DetalleImportarError> detalleserror) {
		this.detalleserror = detalleserror;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	 
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public void setDetalles(List<DetalleImportar> detalles) {
		this.detalles = detalles;
	}
	@OneToOne
	public SedeDemeter getSedeDemeter() {
		return sedeDemeter;
	}

	public void setSedeDemeter(SedeDemeter sedeDemeter) {
		this.sedeDemeter = sedeDemeter;
	}


	public void generarActualizadorid() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File salida = new File("actualizadoId.obj");
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(salida));
		o.writeObject(this);
		o.close();
	}
/*

	public static Importar ActualizadordeId(String ruta) throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		File entrada = new File(ruta);
		ObjectInputStream ios = new ObjectInputStream(new FileInputStream(entrada));
		Object obj = ios.readObject();
		Importar importar = (Importar) obj;
		return importar;
		
		
	}*/
}
