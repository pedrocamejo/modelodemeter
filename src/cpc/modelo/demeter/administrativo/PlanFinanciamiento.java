package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Audited @Entity
@Table(name="tbl_dem_plan_financiamiento", schema="administracion")
public class PlanFinanciamiento implements Serializable{
	/**
	 * 
	 */
	
	//private static final String OBJETOS[] = {"Venta","Refinancimiento"};
	//private static final String TIPOSFINANCIAMIENTO[] = {"Pago con Servicio", "Por Tiempo"};
	
	
	private static final long 		serialVersionUID = -7085331813366664069L;
	
	private Integer					id;
	private String					descripcion;
	private boolean 				objetoVenta;
	private TipoFinanciamiento		tipoFinanciamiento;
	private Calendar				fecha;
	private double					porcentajeDescuento;
	private double					porcentajeInicial;
	private DefinicionDeCuotas      defCuotas;
	

	public PlanFinanciamiento() {
		super();
		
	}
	
	
	
	public PlanFinanciamiento(int id, String descripcion, boolean objetoVenta,
			TipoFinanciamiento tipoFinanciamiento, Calendar fecha,
			double porcentajeDescuento, double porcentajeInicial) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.objetoVenta = objetoVenta;
		this.tipoFinanciamiento = tipoFinanciamiento;
		this.fecha = fecha;
		this.porcentajeDescuento = porcentajeDescuento;
		this.porcentajeInicial = porcentajeInicial;
	}



	public PlanFinanciamiento(boolean tipoGeneral) {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name="seq_idplanfinanciamiento")
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion.toUpperCase();
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToOne
	@JoinColumn(name="int_idtipofinanciamiento")
	public TipoFinanciamiento getTipoFinanciamiento() {
		return tipoFinanciamiento;
	}

	public void setTipoFinanciamiento(TipoFinanciamiento tipoFinanciamiento) {
		this.tipoFinanciamiento = tipoFinanciamiento;
	}
	
	@Column(name="dbl_porcinic", nullable=false)
	public double getPorcentajeInicial() {
		return porcentajeInicial;
	}
	public void setPorcentajeInicial(double porcentajeInicial) {
		this.porcentajeInicial = porcentajeInicial;
	}
	
	@Column(name="bol_objetoventa")
	public boolean isObjetoVenta() {
		return objetoVenta;
	}

	public void setObjetoVenta(boolean objetoVenta) {
		this.objetoVenta = objetoVenta;
	}
	
	@Transient
	public String getObjetoVenta(){
		return (isObjetoVenta()? "VENTA": "REFINANCIAMIENTO");
	}

	@Temporal(TemporalType.DATE)
	@Column(name="dtm_fecha")
	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
	@Column(name="dbl_porcdesc", nullable=false)
	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}


	@OneToOne(cascade=CascadeType.ALL, mappedBy="planFinanciamiento", targetEntity=DefinicionDeCuotas.class)
	public DefinicionDeCuotas getDefCuotas() {
		return defCuotas;
	}



	public void setDefCuotas(DefinicionDeCuotas defCuotas) {
		this.defCuotas = defCuotas;
	}

	/*@Transient
	public static String[] getOBJETOS() {
		return OBJETOS;
	}

	@Transient
	public static String[] getTIPOSFINANCIAMIENTO() {
		return TIPOSFINANCIAMIENTO;
	}
	
	*/
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PlanFinanciamiento)) {
			return false;
		}
		PlanFinanciamiento other = (PlanFinanciamiento) o;
		return true && other.getId().equals(this.getId());
	}
	
	public String toString(){
		return descripcion;
	}
	
}
