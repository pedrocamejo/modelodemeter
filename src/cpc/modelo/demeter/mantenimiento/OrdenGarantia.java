package cpc.modelo.demeter.mantenimiento;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;


@Entity
@Audited
@AuditTable(schema="auditoria",value="tbl_dem_OrdenGarantia_aud")
@Table(name = "tbl_dem_OrdenGarantia", schema = "mantenimiento")
public class OrdenGarantia { // Orden de Trabajo 
	
	private 	Integer 			id;
	private 	String				encargado;
	private 	Date				fecha;
	private 	Integer				km;
	private 	List<Actividad> 	actividades = new ArrayList<Actividad>();;
	private 	MaquinariaExterna	maquinaria;
	private 	DetalleGarantina 	detalleGarantia;
	private 	Integer 			estatus;
	private 	String				nota;
	private 	static String[]     estados =  new String[]{"NUEVA","ACTIVA","FINALIZADA","ANULADA"};
	
	
	@Column(name="seq_idOrdenGarantia")
	@SequenceGenerator(name="OrdenGarantia_Gen", sequenceName="mantenimiento.tbl_dem_OrdenGarantia_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="OrdenGarantia_Gen")
	public Integer getId() {
		return id;
	}
	
	@Column
	public String getEncargado() {
		return encargado;
	}

	@Column
	public Date getFecha() {
		return fecha;
	}

	@Column
	public Integer getKm() {
		return km;
	}
	
	
	@AuditJoinTable(name="tbl_dem_OrdenGarantia_Actividad_aud",schema="auditoria")
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,targetEntity=Actividad.class)
	@JoinTable(name="tbl_dem_OrdenGarantia_Actividad",schema="mantenimiento", 
			joinColumns={@JoinColumn(name="int_OrdenTrabajo")},
			inverseJoinColumns={@JoinColumn(name="int_idActividad")})

	public List<Actividad> getActividades() {
		return actividades;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	public MaquinariaExterna getMaquinaria() {
		return maquinaria;
	}
	
	@Transient
	public String getDetalleGarantiaSTR() {
		return getDetalleGarantia().getDescripcion();
	}

	@OneToOne(fetch= FetchType.EAGER)
	public DetalleGarantina getDetalleGarantia() {
		return detalleGarantia;
	}
	
	@Column(nullable=false)
	public Integer getEstatus() {
		return estatus;
	}
	
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setKm(Integer km) {
		this.km = km;
	}
	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}
	public void setMaquinaria(MaquinariaExterna maquinaria) {
		this.maquinaria = maquinaria;
	}
	public void setDetalleGarantia(DetalleGarantina detalleGarantia) {
		this.detalleGarantia = detalleGarantia;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	
	@Transient
	public String getSerialcarroceria()
	{
		return maquinaria.getSerialcarroceria();
	}
	@Transient
	 public JRDataSource getActividadDS()   
	    { 
		    return new JRBeanCollectionDataSource(actividades);
	    }
	 
	@Transient
	public String getEstatusStr()
	{
		return estados[estatus];
	}

	@Column
	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	 
}
