package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import cpc.modelo.ministerio.gestion.Cliente;
import cpc.modelo.sigesp.basico.Activo;
import cpc.modelo.sigesp.basico.Modelo;
import cpc.modelo.sigesp.basico.Tipo;

@Audited @Entity
@Table(name = "tbl_dem_objetomantenimiento", schema = "mantenimiento")
@Inheritance(strategy = InheritanceType.JOINED)
			
public class ObjetoMantenimiento implements Serializable {

	private static final long serialVersionUID = 4561395414475500776L;

	private Long					id; 
	private String					codigo;
	private String					nombre;
	private String 					serie;
	private Boolean					propio;
	private Activo					activo;
	private Fabricante				fabricante;
	private int						anoFabricacion;	
	private Tipo					tipo;
	private Modelo					modelo;
	private String					color, colorRGB, numeroColor;
	private Byte					foto;	 
	private Lote					lote;
	private EstadoFuncional			estado;
	private Cliente                 propietario; 
	private Boolean 				objetoSimple; 	
	private Integer  				idSistemaMantenimiento; 
	private List<ObjetoMantenimiento> componentes; 
	
     
		
	public ObjetoMantenimiento() {
		super();
	}

	@Column(name="seq_idobjeto")
	@SequenceGenerator(name="ObjetoMantenimiento_Gen", sequenceName="mantenimiento.tbl_dem_objetomantenimiento_seq_idobjeto_seq",  allocationSize=1 )
	@Id @GeneratedValue( generator="ObjetoMantenimiento_Gen")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="str_codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name="str_serie")
	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
 
	

	@OneToOne
	@JoinColumns({ 
	    @JoinColumn(name="str_idcategoria", referencedColumnName="str_idcategoria", insertable=true, updatable=true), 
	    @JoinColumn(name="str_idtipo", referencedColumnName="str_idtipo", insertable=true, updatable=true),  
	})
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	
	
	@OneToOne
	@JoinColumns({ 
	    @JoinColumn(name="str_idmarca", referencedColumnName="str_idmarca", insertable=true, updatable=true), 
	    @JoinColumn(name="str_idmodelo", referencedColumnName="str_idmodelo", insertable=true, updatable=true),  
	})
	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	@Column(name="str_color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name="str_rgbcolor")
	public String getColorRGB() {
		return colorRGB;
	}

	public void setColorRGB(String colorRGB) {
		this.colorRGB = colorRGB;
	}

	@Column(name="str_numcolor")
	public String getNumeroColor() {
		return numeroColor;
	}

	public void setNumeroColor(String numeroColor) {
		this.numeroColor = numeroColor;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idfabricante")
	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	@Column(name="int_anofab")
	public int getAnoFabricacion() {
		return anoFabricacion;
	}

	public void setAnoFabricacion(int anoFabricacion) {
		this.anoFabricacion = anoFabricacion;
	}
	@Transient
	//@Column(name="byt_foto")
	public Byte getFoto() {
		return foto;
	}

	public void setFoto(Byte foto) {
		this.foto = foto;
	}	
	
	@Column(name="bol_propio")
	public Boolean getPropio() {
		return propio;
	}

	public void setPropio(Boolean propio) {
		this.propio = propio;
	}

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_codact",referencedColumnName="codact"),
		@JoinColumn(name="str_ideact",referencedColumnName="ideact")
	})
	public Activo getActivo() {
		return activo;
	}

	public void setActivo(Activo activo) {
		this.activo = activo;
	}
	
	@OneToOne
	@JoinColumn(name="int_idlote")
	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	@OneToOne
	@JoinColumn(name="int_idestado ")
	public EstadoFuncional getEstado() {
		return estado;
	}
	
	@ManyToOne
	@JoinColumn(name="int_idpropietario")
	public Cliente getPropietario() {
		return propietario;
	}

	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}
	
	@Column(name="bol_atomico")
	public Boolean getObjetoSimple() {
		return objetoSimple;
	}
	
	@Transient
	public Boolean esObjetoSimple() {
		return getObjetoSimple();
	}
	
	public void setObjetoSimple(Boolean objetoSimple) {
		this.objetoSimple = objetoSimple;
	}
	
	@Column(name="int_idsismantenimiento")
	public Integer getIdSistemaMantenimiento() {
		return idSistemaMantenimiento;
	}

	public void setIdSistemaMantenimiento(Integer idSistemaMantenimiento) {
		this.idSistemaMantenimiento = idSistemaMantenimiento;
	}
	
	@Transient
	public List<ObjetoMantenimiento> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<ObjetoMantenimiento> componentes) {
		this.componentes = componentes;
	}

	public void setEstado(EstadoFuncional estado) {
		this.estado = estado;
	}
	

	public boolean equals(Object objeto){
		try{
			ObjetoMantenimiento cuenta = (ObjetoMantenimiento) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nombre.toUpperCase();
	}
}