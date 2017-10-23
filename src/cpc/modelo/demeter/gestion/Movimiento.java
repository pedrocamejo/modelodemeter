package cpc.modelo.demeter.gestion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import cpc.modelo.sigesp.basico.UnidadAdministrativa;
import cva.pc.demeter.utilidades.Fecha;
import cva.pc.demeter.utilidades.Formateador;

@Audited @Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="saf_movimientoactivos",schema="sigesp")
public class Movimiento implements Serializable{

	private static final long serialVersionUID = -9134976655062736747L;
	private Integer						idmovimiento;
	private Date    					fecha;
	private String						prefijo;
	private int 						numeroControl;
	private TipoMovimiento 				tipomovimiento;
	private UnidadAdministrativa    	unidadAdministrativa;
	private EstadoMovimientoActivo      estado;
	private String						usuario;
	private String 						motivoAnulacion;
	private String 						anuladoPor;
	private Date 						fechaAnulacion;
	private List<EntradaActivo> 		entradas ;
	private List<SalidaActivo> 			salidas ;
	private List<TransferenciaActivo> 	transferencias;
	private List<PrestamoActivo>		prestamos;
	
	public Movimiento() {
		super();
	}
	
	public Movimiento(Date fecha,
			TipoMovimiento tipomovimiento, UnidadAdministrativa unidadAdministrativa, String usuario) {
		super();
		this.fecha = fecha;
		this.tipomovimiento = tipomovimiento;
		this.unidadAdministrativa = unidadAdministrativa;
		this.usuario = usuario;
	}
	
	public Movimiento(Date fecha,
			TipoMovimiento tipomovimiento, String usuario) {
		super();
		this.fecha = fecha;
		this.tipomovimiento = tipomovimiento;
		this.usuario = usuario;
	}
	
	@SequenceGenerator(name="SeqMovimiento", sequenceName="sigesp.saf_movimientoactivos_seq_ser_idmoviacti_seq", allocationSize=1)
	@Column(name="seq_ser_idmoviacti")
	@Id @GeneratedValue(generator="SeqMovimiento")
	public Integer getIdmovimiento() {
		return idmovimiento;
	}
	public void setIdmovimiento(Integer idmovimiento) {
		this.idmovimiento = idmovimiento;
	}
	
	@Column(name="tmwtz_fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="str_prefijo")
	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	@Column(name="int_nrocontrol")
	public int getNumeroControl() {
		return numeroControl;
	}

	public void setNumeroControl(int numeroControl) {
		this.numeroControl = numeroControl;
	}

	@ManyToOne
	@JoinColumn(name="int_idtipomovimiento")
	public TipoMovimiento getTipomovimiento() {
		return tipomovimiento;
	}
	public void setTipomovimiento(TipoMovimiento tipomovimiento) {
		this.tipomovimiento = tipomovimiento;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="str_codemp",referencedColumnName="codemp"),
		@JoinColumn(name="str_coduniadm",referencedColumnName="coduniadm")
	})
	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	@ManyToOne
	@JoinColumn(name="int_idestadomovimiento")
	public EstadoMovimientoActivo getEstado() {
		return estado;
	}

	public void setEstado(EstadoMovimientoActivo estado) {
		this.estado = estado;
	}

	@Column(name="str_registradopor")
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name="str_observanulacion")
	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}

	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}

	@Column(name="str_anuladopor")
	public String getAnuladoPor() {
		return anuladoPor;
	}

	public void setAnuladoPor(String anuladoPor) {
		this.anuladoPor = anuladoPor;
	}

	@Column(name = "tmwtz_fechaanulacion")
	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="movimiento", targetEntity=DetalleMovimiento.class)
	public List<EntradaActivo> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<EntradaActivo> entradas) {
		this.entradas = entradas;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="movimiento", targetEntity=DetalleMovimiento.class)
	public List<SalidaActivo> getSalidas() {
		return salidas;
	}

	public void setSalidas(List<SalidaActivo> salidas) {
		this.salidas = salidas;
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="movimiento", targetEntity=DetalleMovimiento.class)
	public List<TransferenciaActivo>  getTransferencias() {
		return transferencias;
	}

	public void setTransferencias(List<TransferenciaActivo>  transferencias) {
		this.transferencias = transferencias;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="movimiento", targetEntity=DetalleMovimiento.class)
	public List<PrestamoActivo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<PrestamoActivo> prestamos) {
		this.prestamos = prestamos;
	}

	@Transient
	public String getidstring(){
		return ""+getIdmovimiento();
	}
	
	@Transient
	public String getfechastring(){
		return Fecha.obtenerFecha(fecha);
	}
	
	@Transient
	public String getControl(){
		return prefijo+"-"+Formateador.rellenarNumero(numeroControl, "000000");
	}
	
	@Transient
	public String getNombreEstado(){
		return estado.getDescripcion();
	}
	
	@Transient
	public String getFechaAnulacionString(){
		return Fecha.obtenerFecha(fechaAnulacion);
	}
	
	@Transient
	public String getMesFechaProceso(){
		return Fecha.obtenerMesEnLetra(getFecha());
	}
	
	public boolean equals(Object objeto){
		try{
			Movimiento cuenta = (Movimiento) objeto;
			if (cuenta.getIdmovimiento().equals(idmovimiento))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
