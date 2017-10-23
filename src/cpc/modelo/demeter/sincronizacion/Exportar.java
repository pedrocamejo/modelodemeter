package cpc.modelo.demeter.sincronizacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.Audited;
 
import com.sun.org.apache.regexp.internal.recompile;

import cpc.modelo.ministerio.dimension.UbicacionSector;

@XmlRootElement
@Entity
@Table(name = "tbl_dem_exportar",schema = "sincronizacion")
public class Exportar implements Serializable{

	 private Integer 			 id;

	 private SedeDemeter		 sede;
	 private String 			 md5; //tamaño del archivo 
	 private String 			 cedula; //cedula del mrs :_D 
	 private List<DetalleExport> detalles = new ArrayList<DetalleExport>();;
	 
	@SequenceGenerator(name="Seqexport", sequenceName="sincronizacion.tbl_dem_exportar_id_seq", allocationSize=1)
	@Id
	@Column(name="id")
	@GeneratedValue(generator="Seqexport")
	public Integer getId() {
		return id;
	}
	
	@OneToOne
	public SedeDemeter getSede() {
		return sede;
	}

	@Column
	public String getMd5() {
		return md5;
	}

	@Column
	public String getCedula() {
		return cedula;
	}

	@OneToMany(targetEntity=DetalleExport.class,mappedBy="exportar",cascade=CascadeType.ALL)
	public List<DetalleExport> getDetalles() {
		return detalles;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setSede(SedeDemeter sede) {
		this.sede = sede;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public void setDetalles(List<DetalleExport> detalles) {
		this.detalles = detalles;
	}
	/*
	@Transient
	public DetalleExport buscar(String idsede, String nombreclass) 
	{
		for(DetalleExport detalle : detalles)
		{
			if(detalle.getIdsede().trim().contains(idsede.trim()) && detalle.getNameclass().trim().contains(nombreclass.trim()))
			{
				return detalle;
			}
		}
		return null;
	}
	*/
/*
	public void marcaTrabajo(String idsede, String nombre,Object obj)
	{
		for(DetalleExport detalle : detalles)
		{
			if(detalle.getIdsede().trim().contains(idsede.trim()) && detalle.getNameclass().trim().contains(nombre.trim()))
			{
				obj = detalle.getObjeto();
				detalle.setTrabajado(true);
			}
		}
		obj = null;
	}
*/

 

}

