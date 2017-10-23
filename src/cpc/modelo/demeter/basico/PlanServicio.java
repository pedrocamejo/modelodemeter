package cpc.modelo.demeter.basico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Audited @Entity
@Table(name="tbl_dem_plan", schema="basico")
public class PlanServicio implements Serializable{
	
	private static final long serialVersionUID = -628340483395986038L;
	private int 				id;
	private String				nombre;
	private TipoPlanServicio	tipo;
	
	public PlanServicio() {
		super();
	}
	
	public PlanServicio(int id, String nombre, TipoPlanServicio	tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre = nombre;
	}
	
	@SequenceGenerator(name="seqPlan", sequenceName="tbl_dem_plan_seq_idplan_seq", allocationSize=1)
	@Id	@GeneratedValue(generator="seqPlan")
	@Column(name="seq_idplan")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="str_nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToOne
	@JoinColumn(name="int_tipoplan")
	public TipoPlanServicio getTipo() {
		return tipo;
	}

	public void setTipo(TipoPlanServicio tipo) {
		this.tipo = tipo;
	}
	
	public String toString(){
		return nombre;
	}
	
	public boolean equals(Object objeto){
		try{
			PlanServicio cuenta = (PlanServicio) objeto;
			if (cuenta.getId()==getId())
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	}
}
