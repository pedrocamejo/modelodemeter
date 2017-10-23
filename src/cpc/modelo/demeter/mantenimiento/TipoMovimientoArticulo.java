package cpc.modelo.demeter.mantenimiento;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
@Audited
@Entity
@Table(name="tbl_dem_tipo_movimiento_articulo",schema="mantenimiento")

public class TipoMovimientoArticulo implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4732670229966983229L;
	private Integer		id;
    private String		descripcion;
    
    
    public TipoMovimientoArticulo() {
		super();
	}
    
	public TipoMovimientoArticulo(int idtipomovimiento, String descripcion) {
		super();
		this.id = idtipomovimiento;
		this.descripcion = descripcion;
	}
	
	@SequenceGenerator(name="Seq_idtipomovimientoacticulo", sequenceName="mantenimiento.tbl_dem_tipo_movimiento_articu_seq_idtipomovimientoarticulo_seq", allocationSize=1)
	@Column(name="seq_idtipomovimientoarticulo")
	@Id @GeneratedValue(generator="Seq_idtipomovimientoacticulo")
	public Integer getId() {
		return id;
	}
	public void setId(Integer idtipomovimiento) {
		this.id = idtipomovimiento;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    



	

	
}
