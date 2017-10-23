package cpc.modelo.demeter.mantenimiento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name="tbl_dem_PlantaDistribuidora" ,schema="mantenimiento")
public class PlantaDistribuidora {
	
	 private	 Integer id;
	 private	 String	 nombre;
	 private	 String documento;
	 private 	 String direccion;
	 private 	 String telefonos;
	 private 	 Integer tipo; // 0- Pauny O 1 -Don roque  para datos que se van a mostrar en el Reporte 
	 private 	 List<TipoGarantia> productos = new ArrayList<TipoGarantia>();
	 
	 
	@Column(name="seq_idPlantaDistribuidora")
	@SequenceGenerator(name="PlantaDistribuidora_Gen", sequenceName="mantenimiento.tbl_dem_PlantaDistribuidora_seq",  allocationSize=1 )
	@Id	@GeneratedValue( generator="PlantaDistribuidora_Gen") 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	
	@Column
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Column
	public String getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
	
	
	@AuditJoinTable
	@ManyToMany(targetEntity=TipoGarantia.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="tbl_dem_tipoGarantiaplanta",joinColumns={
    		@JoinColumn(name="int_idPlantaDistribuidora")},
    		inverseJoinColumns={@JoinColumn(name="int_idTipoGarantia")},
    		schema="mantenimiento")
	public List<TipoGarantia> getProductos() {
		return productos;
	}
	public void setProductos(List<TipoGarantia> productos) {
		this.productos = productos;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlantaDistribuidora other = (PlantaDistribuidora) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Column(name="int_tipo")
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	@Transient
	public String getStrTipo() {
		return (tipo.equals(0)?"PAUNY":"DON ROQUE");
	}
	
	
}
