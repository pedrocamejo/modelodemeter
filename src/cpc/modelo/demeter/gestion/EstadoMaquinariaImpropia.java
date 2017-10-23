package cpc.modelo.demeter.gestion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import cpc.modelo.sigesp.basico.Almacen;

@Audited 
@Entity
@Table(name="tbl_dem_estadoMaquinariaImpropia", schema="gestion")
public class EstadoMaquinariaImpropia {

	/*
	 * Crear Objeto EstadoMaquinaImpropia con los valores:
	 * 	 	Ingresado (maquinaria cargada por la sede ). 
	 * 		Verificado (maquinaria cargada por la sede, Aprobado para su uso pero no sera convertida en bien Nacional ). 
	 * 		Migrado (Transformada en Bien Nacional).
	 * 		Desactivado(Esta maquinaria No puede usarse Segun Bienes Nacionales).
	 * 		Desincorporado Maquinaria inutilizable.
	 * */ 

	public static Integer INGRESADO = 1;
	/* Verificado 
	 * con el estado verificado se va a crear un objeto de tipo MaquinariaUnidad
	 * donde se va a establecer que esa maquinaria es de esta sede y se puede usar 
	 * en las ordenes  puesto que operativio es true 
	 *  
	 * */
	public static Integer VERIFICADO = 2; 
	/* Migrado 
	 * convertida a bien nacional lo cual quiere decir que si hay un objeto MaquinariaUnidad
	 * este va a tener el activo y la maquinaria unidad :-D 
	 * */
	public static Integer MIGRADO = 3;
	/* desactivado 
	 * si hay un objeto MaquinariaUnidad se pone en Operativo = false 
	 * */
	public static Integer DESACTIVADO = 4;
	/* desincorporado 
	 * si hay un objeto MaquinariaUnidad se pone en Operativo = false 
	 * */
	public static Integer DESINCORPORADO = 5;
	
	private Integer id;
	private String descripcion;

	@SequenceGenerator(name="SeqEstadoMaquinariaImpropia",
	sequenceName="gestion.estadoMaquinariaImpropia_seq", allocationSize=1)
 	@Id 
	@GeneratedValue(generator="SeqEstadoMaquinariaImpropia")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable=false,length=30)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoMaquinariaImpropia other = (EstadoMaquinariaImpropia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

/*  
  
 	insert into gestion.tbl_dem_estadoMaquinariaImpropia(id,descripcion)
 	values (1,'INGRESADO');

 	insert into gestion.tbl_dem_estadoMaquinariaImpropia(id,descripcion)
 	values (2,'VERIFICADO');

 	insert into gestion.tbl_dem_estadoMaquinariaImpropia(id,descripcion)
 	values (3,'MIGRADO');

 	insert into gestion.tbl_dem_estadoMaquinariaImpropia(id,descripcion)
 	values (4,'DESACTIVADO');

 	insert into gestion.tbl_dem_estadoMaquinariaImpropia(id,descripcion)
 	values (5,'DESINCORPORADO');
 

  
 */
