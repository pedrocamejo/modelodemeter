package cpc.modelo.demeter.administrativo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;

import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import cpc.modelo.demeter.basico.TipoServicio;
import cpc.modelo.sigesp.basico.Sede;




@Audited @Entity
@Table(name = "tbl_dem_cuentastiposervicio", schema = "administracion")

public class CuentasTipoServicio implements Serializable {	
	
	private static final long serialVersionUID = -3630834357658615178L;
	private Integer			id;
	private Sede 			sede;
	private TipoServicio 	tipoServicio;	
	private String			cuentaPresupuestariaIngresosSede;	
	private String			cuentaContableIngresosSede;
	
	@SequenceGenerator(name="seqId", sequenceName="administracion.tbl_dem_cuentastiposervicio_seq_id_seq", allocationSize=1)
	@Id @GeneratedValue(generator="seqId")	
	@Column(name="seq_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumns({
	    @JoinColumn(name="str_codemp", referencedColumnName="codemp", insertable=true, updatable=false),
	    @JoinColumn(name="str_codsede", referencedColumnName="codubifis", insertable=true, updatable=false),
	})
	public Sede getSede() {
		return sede;
	}
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	@OneToOne
	@JoinColumn(name="int_idtiposervicio")
	public TipoServicio getTipoServicio(){
		return this.tipoServicio;
	}

	public void setTipoServicio(TipoServicio nuevoTipoServicio){
		this.tipoServicio = nuevoTipoServicio;
	}

	@Column(name="str_cuenta_presupuesto_ingreso")
	public String getCuentaPresupuestariaIngresosSede() {
		return cuentaPresupuestariaIngresosSede;
	}
	public void setCuentaPresupuestariaIngresosSede(
			String cuentaPresupuestariaIngresosSede) {
		this.cuentaPresupuestariaIngresosSede = cuentaPresupuestariaIngresosSede;
	}
	
	@Column(name="str_cuenta_contable_ingreso")
	public String getCuentaContableIngresosSede() {
		return cuentaContableIngresosSede;
	}
	public void setCuentaContableIngresosSede(String cuentaContableIngresosSede) {
		this.cuentaContableIngresosSede = cuentaContableIngresosSede;
	}

		
	@Transient
	public String getStrSede(){
		return sede.getNombre();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CuentasTipoServicio)) {
			return false;
		}
		CuentasTipoServicio other = (CuentasTipoServicio) o;
		return true && other.getId().equals(this.getId());
	}
}
	