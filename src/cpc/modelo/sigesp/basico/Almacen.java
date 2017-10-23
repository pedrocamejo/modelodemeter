package cpc.modelo.sigesp.basico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import cpc.modelo.demeter.basico.Trabajador;
import cpc.modelo.ministerio.dimension.UbicacionDireccion;
import cpc.modelo.ministerio.dimension.UnidadFuncional;

@Audited @Entity
@Table(name="saf_almacen",schema="sigesp")
public class Almacen implements Serializable{

	private static final long serialVersionUID = 236431827212872524L;
	private Integer					id;
	private UnidadAdministrativa	unidadAdministrativa;
	private String					nombre, descripcion;
	private TipoAlmacen				tipoAlmacen;
	private UbicacionDireccion 		ubicacionGeografica;
	private Trabajador				trabajador;
	private String					localidad, telefono;
	private boolean					estadoAlmacen;
	private UnidadFuncional 		unidadFuncional;
	
	public Almacen() {
		super();
	}
	
	public Almacen(Integer id, UnidadAdministrativa unidadAdministrativa,
			String nombre, String descripcion, TipoAlmacen tipoAlmacen,
			UbicacionDireccion ubicacionGeografica, Trabajador trabajador,
			String localidad, String telefono, boolean estadoAlmacen) {
		super();
		this.id = id;
		this.unidadAdministrativa = unidadAdministrativa;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipoAlmacen = tipoAlmacen;
		this.ubicacionGeografica = ubicacionGeografica;
		this.trabajador = trabajador;
		this.localidad = localidad;
		this.telefono = telefono;
		this.estadoAlmacen = estadoAlmacen;
	}
	
	@SequenceGenerator(name="SeqAlmacen", sequenceName="sigesp.saf_almacen_ser_idalmacen_seq", allocationSize=1)
	@Id @GeneratedValue(generator="SeqAlmacen")
	@Column(name="ser_idalmacen")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_idunieje",referencedColumnName="coduniadm")
	})
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
	@Column(name="str_nombre", nullable=false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="str_descripcion", nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtipalmacen")
	public TipoAlmacen getTipoAlmacen() {
		return tipoAlmacen;
	}
	public void setTipoAlmacen(TipoAlmacen tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}
	
	@ManyToOne
	@JoinColumn(name="int_iddireccion")
	public UbicacionDireccion getUbicacionGeografica() {
		return ubicacionGeografica;
	}
	public void setUbicacionGeografica(UbicacionDireccion ubicacionGeografica) {
		this.ubicacionGeografica = ubicacionGeografica;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idtrabajador")
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	
	@Column(name="str_localidad")
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	@Column(name="str_telefono")
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Column(name="bol_estado")
	public boolean isEstadoAlmacen() {
		return estadoAlmacen;
	}
	public void setEstadoAlmacen(boolean estadoAlmacen) {
		this.estadoAlmacen = estadoAlmacen;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idunidadfuncional")
	public UnidadFuncional getUnidadFuncional() {
		return unidadFuncional;
	}

	public void setUnidadFuncional(UnidadFuncional unidadFuncional) {
		this.unidadFuncional = unidadFuncional;
	}

	@Transient
	public String getNombreTipoAlmacen(){
		return getTipoAlmacen().getNombre();
	}
	
	@Transient
	public String getResponsable(){
		return getTrabajador().getNombre();
	}
	
	@Transient
	public String getNombreUnidadFuncional(){
		return getUnidadFuncional().getDescripcion();
	}
	
	public String toString() {
		
		return getNombre();
	}
	
	public boolean equals(Object objeto){
		try{
			Almacen almacen = (Almacen) objeto;
			if (almacen.getId().equals(getId()))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
