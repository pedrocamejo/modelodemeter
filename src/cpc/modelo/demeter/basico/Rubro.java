package cpc.modelo.demeter.basico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity; import org.hibernate.envers.Audited;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Audited @Entity
@Table(name="tbl_dem_rubro", schema="basico")
public class Rubro implements Serializable{
	
	private static final long 	serialVersionUID = -8039458778170835247L;
	private Integer				id;
	private String 				descripcion;
	private TipoRubro			tipoRubro;
	
	public Rubro(int idRubro, String descripcion, TipoRubro tipo) {
		super();
		this.id = idRubro;
		this.descripcion = descripcion;
		this.tipoRubro = tipo;
		
	}
	
	public Rubro() {
		super();
	}
	
	@Id
	@SequenceGenerator(name="SeqRubro", sequenceName="basico.tbl_dem_rubro_seq_idrubro_seq")
	@GeneratedValue(generator="SeqRubro")
	@Column(name="seq_idrubro")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne
	@JoinColumn(name="int_idtipo")
	public TipoRubro getTipoRubro() {
		return tipoRubro;
	}

	public void setTipoRubro(TipoRubro tipoRubro) {
		this.tipoRubro = tipoRubro;
	}
	
	@Transient
	public String getStrNombreRubro() {
		return this.tipoRubro.getNombre();
	}
	
	@Transient
	public static int buscarIndice(List<Rubro> lista, int idRubro ){
		int i = 0;
		for (Rubro codigo : lista) {			
			if (codigo.id== idRubro)
				return i;
			i++;
		}
		return -1;
	}
	
	public String toString(){
		return descripcion;
	}
	
	
	public boolean equals(Object objeto){
		try{
			Rubro cuenta = (Rubro) objeto;
			if (cuenta.getId().equals(id))
				return true;
			else
				return false;
		}catch (Exception e) {
			return false;
		}
	} 
}
